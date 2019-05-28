import java.io.FileWriter;
import java.io.IOException;

/*
 * Sean Rannie
 * January 16, 2018
 * 
 * This is an example of an 16-bit sound generator. The waveform data is represented
 * in shorts. The generator outputs mono sound in a stereo file.
 */

public class BasicGenerator extends SoundGenerator
{
	private short[][] data; //The waveform data
	
	public BasicGenerator(int sample, int sz)
	{
		super(sample, sz);
		
		bitsPerSample = 16;
		mode = CHANNEL.SQUARE;
		data = new short[size][numberOfChannels];
	}
	
	public void generatePulse(int length, byte duty, byte volume, double frequency)
	{
		generateSquareWave(marker, length, 0.5, (short)(volume * 0xFFFF / 0xFF), frequency);
		pushMarker(length, true);
	}
	
	public void generatePulse(int length, byte duty, byte volume, int note)
	{
		generateSquareWave(marker, length, 0.5, (short)(volume * 0xFFFF / 0xFF), noteScale[note]);
		pushMarker(length, true);
	}
	
	public void generateNote(int length, double width, short volume, int note)
	{
		switch(mode)
		{
			case PULSE1:
			case PULSE2:
			case SQUARE:
				generateSquareWave(marker, length, width, volume, noteScale[note%128]);
				break;
			case TRIANGLE:
				generateTriangleWave(marker, length, width, volume, noteScale[note%128]);
				break;
			default:
				break;
		}
		
		pushMarker(length, true);
	}
	
	//Generates square pulse waves for specified number of samples
	public void generateSquareWave(int offset, int length, double width, short volume, double frequency)
	{
		double period = sampleRate/frequency;
		
		for(int i = 0; i < length && i + offset < size; i++)
		{
			if(i%period < period*width)
			{
				data[i + offset][CHANNEL_LEFT] += volume;
				data[i + offset][CHANNEL_RIGHT] += volume;
			}
		}
	}
	
	//Generates triangle pulse waves for specified number of samples
	public void generateTriangleWave(int offset, int length, double width, short volume, double frequency)
	{
		double period = sampleRate/frequency;
		double slope = (double)volume/(period*width);
		for(int i = 0; i < length && i + offset < size; i++)
		{
			if(i%period < period*width)
			{
				data[i + offset][CHANNEL_LEFT] += (short) (volume - slope*(i%period));
				data[i + offset][CHANNEL_RIGHT] += (short) (volume - slope*(i%period));
			}
		}
	}
	
	//Generates waveform using a repeating sample
	public void generateFreeWave(int offset, int length, float volume, double frequency, double[] wave)
	{
		double period = sampleRate/frequency;
		double rate = wave.length/period;
		for(int i = 0; i < length && i + offset < size; i++)
		{
			data[i + offset][CHANNEL_LEFT] += (short) (volume*wave[(int)((i*rate)%wave.length)]*0xFFFF);
			data[i + offset][CHANNEL_RIGHT] += data[i + offset][CHANNEL_LEFT];
		}
	}
	
	public void generateFreeWaveNoFrequency(int offset, int length, float volume, double[] wave)
	{
		for(int i = 0; i < length && i + offset < size; i++)
		{
			data[i + offset][CHANNEL_LEFT] += (short) (volume*wave[i%wave.length]*0xFFFF);
			data[i + offset][CHANNEL_RIGHT] += data[i + offset][CHANNEL_LEFT];
		}
	}
	
	public void applyFilter()
	{
		//Insert filter here
	}
	
	//Method for writing waveform shorts in stereo sound
	public void writeData(FileWriter write) throws IOException
	{
		// 00 		00 		00 		00
		// LEFT 1	LEFT 2	RIGHT 1	RIGHT 2 
		//*(FOR 16 BIT)
		
		for(int d = 0; d < size; d++)
		{
			writeShort(write, data[d][CHANNEL_LEFT]);
			writeShort(write, data[d][CHANNEL_RIGHT]);
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
