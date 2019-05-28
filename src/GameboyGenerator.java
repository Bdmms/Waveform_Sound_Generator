import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Sean Rannie
 * January 16, 2018
 * 
 * This is an example of an 8-bit sound generator. The waveform data is represented
 * in bytes. The generator outputs mono sound in a stereo file. Duty cycles are not
 * treated as fractions of 255, but instead consist of 3 modes: 12.5%, 25%, 50%, 75%
 */

public class GameboyGenerator extends SoundGenerator
{
	//constants for duty cycle
	public static final byte DUTY_12_5 = 0;
	public static final byte DUTY_25 = 1;
	public static final byte DUTY_50 = 2;
	public static final byte DUTY_75 = 3;
	
	private byte[][] data; //The waveform data
	
	public GameboyGenerator(int sample, int sz)
	{
		super(sample, sz);
		
		bitsPerSample = 8;
		mode = CHANNEL.PULSE2;
		data = new byte[size][numberOfChannels];
	}
	
	public void generateNote(int length, double width, short volume, int note) 
	{
		byte duty = 1;
		generatePulse(length, duty, (byte)((volume/2 * 0xFF) / 0xFFFF), note);
	}
	
	public void generatePulse(int length, byte duty, byte volume, int note)
	{
		generatePulse(length, duty, volume, noteScale[note]);
	}
	
	public void generatePulse(int length, byte duty, byte volume, double frequency)
	{
		switch(mode)
		{
			case PULSE1:
				generatePulse2(marker, length, duty, volume, frequency);
				break;
			case SQUARE:
			case TRIANGLE:
			case PULSE2:
				generatePulse2(marker, length, duty, volume, frequency);
				break;
			case WAVE:
				generateWave(marker, length, volume, frequency);
				break;
			case NOISE:
				break;
			default:
				break;
		}
		
		pushMarker(length, true);
	}
	
	//$FF10 0PPPSSSS (Frequency Sweep Period = 0 - 7) | (Frequency Sweep Rate = -8 - 7)
	//$FF11 DDLLLLLL (Duty cycle = 12.5%, 25%, 50%, 75%) | (Note Length = 0 - 63)
	//$FF12 VVVVvvvv (Volume = 0 - 15) | (Volume Fade = -8 - 7)
	//$FF13 FFFFFFFF (Frequency Value = 0 - 2047)
	//$FF14 TL000FFF (Trigger Switch) | (Length Switch)
	public void generatePulse1(int length, short information, double frequency, byte sweep)
	{
		//TODO
	}
	
	//$FF16 DDLLLLLL (Duty cycle = 12.5%, 25%, 50%, 75%) | (Note Length = 0 - 63)
	//$FF17 VVVVvvvv (Volume = 0 - 15) | (Volume Fade = -8 - 7)
	//$FF18 FFFFFFFF (Frequency Value = 0 - 2047)
	//$FF19 TL000FFF (Trigger Switch) | (Length Switch)
	public void generatePulse2(int offset, int length, byte duty, byte volume, double frequency)
	{
		double period = sampleRate/frequency;
		double dutyCycle = duty*0.25;
		
		if(dutyCycle == 0)
			dutyCycle = 0.125;
		
		for(int i = 0; i < length && i + offset < size; i++)
		{
			if(i%period < period*dutyCycle)
			{
				data[i + offset][CHANNEL_LEFT] += volume;
				data[i + offset][CHANNEL_RIGHT] += volume;
			}
		}
	}
	
	//$FF1B LLLLLLLL (Note Length = 0 - 255)
	//$FF1C 0VV00000 (Volume = 0 - 3)
	//$FF1D FFFFFFFF (Frequency Value = 0 - 2047)
	//$FF1E TL000FFF (Trigger Switch) | (Length Switch)
	//$FF30: waveData
	public void generateWave(int offset, int length, byte volume, double frequency)
	{
		double period = sampleRate/frequency;
		
		for(int i = 0; i < length && i + offset < size; i++)
		{
			data[i + offset][CHANNEL_LEFT] += volume + volume*Math.sin(2*Math.PI/period*i);
			data[i + offset][CHANNEL_RIGHT] += volume + volume*Math.sin(2*Math.PI/period*i);
		}
	}
	
	//NOISE CHANNEL
	public void generateNoise(int length, short frequency, byte[] noiseData)
	{
		//TODO
	}

	public void applyFilter() 
	{
		//TODO
		for(int d = 0; d < size; d++)
		{
			//data[d][CHANNEL_LEFT] = (byte) (data[d][CHANNEL_LEFT]*Math.sin(d)*50);
			//data[d][CHANNEL_RIGHT] = data[d][CHANNEL_LEFT];
		}
	}
	
	//Method for writing waveform bytes in stereo sound
	public void writeData(FileWriter write) throws IOException
	{
		for(int d = 0; d < size; d++)
		{
			write.write(data[d][CHANNEL_LEFT]);
			write.write(data[d][CHANNEL_RIGHT]);
		}
	}
	
	//Basic method for drawing waveforms on the JPanel
	public void drawData(Graphics g, int offset)
	{
		g.setColor(Color.BLACK);
		byte scale = 2;
		int lastY = 0;
		int x = 0;
		int y = Main.RESOLUTION_Y/2;
		
		for(int i = 0; i < Main.RESOLUTION_X; i++)
		{
			lastY = y;
			x = i;
			
			if(i*scale + offset < size)
				y = Main.RESOLUTION_Y/2 - data[i*scale + offset][CHANNEL_LEFT];
			else
				y = Main.RESOLUTION_Y/2;
			g.drawLine(x,lastY,x,y);
		}
	}
	
	public void deleteData() {
		for(int d = 0; d < size; d++)
		{
			data[d][CHANNEL_LEFT] = 0;
			data[d][CHANNEL_RIGHT] = 0;
		}
	}
}
