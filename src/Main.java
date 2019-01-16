import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Sean Rannie
 * January 16, 2018
 * 
 * This is an example main that utilizes the sound
 * generators.
 */

public class Main extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final int RESOLUTION_X = 1000;	//Horizontal resolution
	public static final int RESOLUTION_Y = 200;		//Vertical resolution
	
	//THE FOLLOWING ARE OFFSET ADDRESSES FOR THE WAV FILE
	
	//RIFF chunk descriptor
	final int ADDRESS_CHUNK_ID = 0x00; //CONTAINS "RIFF"
	final int ADDRESS_CHUNK_SIZE = 0x04;
	final int ADDRESS_CHUNK_FORMAT = 0x08;
	
	//FMT subchunk
	final int ADDRESS_SUBCHUNK1_ID = 0x0C;
	final int ADDRESS_AUDIO_FORMAT = 0x10;
	final int ADDRESS_NUM_CHANNELS = 0x12;
	final int ADDRESS_SAMPLE_RATE = 0x14;
	final int ADDRESS_BYTE_RATE = 0x18;
	final int ADDRESS_BLOCK_ALIGN = 0x1C;
	final int ADDRESS_BITS_PER_SAMPLE = 0x1E;
	
	//Data subchunk
	final int ADDRESS_SUBCHUNK2_ID = 0x20;
	final int ADDRESS_SUBCHUNK2_SIZE = 0x24;
	final int ADDRESS_DATA = 0x28;
	
	final byte CHANNEL_LEFT = 0;
	final byte CHANNEL_RIGHT = 1;
	
	private JFrame frame = new JFrame("Waveform");

	SoundGenerator gen = null;
	Clip clip = null;
	File file = new File("demo.wav");
	
	//Starting Data
	int sampleRate = 22050; //44100 = CD-QUALITY (# of samples per second)
	int size = 0xFFFFF; //2^40 samples used in a single file
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(RESOLUTION_X,RESOLUTION_Y));
        frame.setLocation(0, 0);
        frame.setBackground(Color.WHITE);
        frame.add(this);
		
		//GENERATE WAVE FILE
		try {
			FileWriter write = new FileWriter(file);
			
			gen = new GameboyGenerator(sampleRate, size);
			
			//song1();
			//song2();
			
			testEffect();
			
			//gen.applyFilter(); (USED TO APPLY FILTER ON COMMAND)
			gen.writeHeader(write);
			
			write.close();
			
			//debugRead(); (USED TO DEBUG THE DATA BEING WRITTEN TO THE FILE)
			
		} catch (IOException e) {System.out.println("ERROR - IO EXCEPTION");}
		
		//PLAY
		try
	    {
	        clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(file));
	        clip.start();
	    }
	    catch (IOException exc){System.out.println("ERROR - FILE CANNOT BE READ");} 
		catch (LineUnavailableException e) {System.out.println("ERROR - AUDIO STREAM BUSY");
		} catch (UnsupportedAudioFileException e) {System.out.println("ERROR - AUDIO PLAYBACK EXCEPTION");}
		
		//DISPLAY
		try {
			Thread.sleep(100);
			
			while(clip.isActive())
			{
				this.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {System.out.println("ERROR - THREAD EXCEPTION");}
	}
	
	//Very primitive GUI for the waveform
	public void paintComponent(Graphics g) 
    {  
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, RESOLUTION_X, RESOLUTION_Y);
		
		g.setColor(Color.BLUE);
		g.drawLine(0, RESOLUTION_Y/2, RESOLUTION_X, RESOLUTION_Y/2);
		
		if(gen != null)
			gen.drawData(g, clip.getFramePosition());
    }
	
	//Debugger for the written data
	public void debugRead() throws IOException
	{
		FileReader reader = new FileReader(file);
		
		int counter = 0;
		int val = 0;
		while(val != -1 && counter < 100) //First 100 bytes of file
		{
			System.out.println(val + " (" + ((char) val) + ")" );
			val = reader.read();
			counter++;
		}
		
		reader.close();
	}
	
	//A sequence of pulses used to create an interesting sound
	public void testEffect() {
		byte vol = 25;
		byte duty = 0;
		
		for(int i = Generator.C1; i < 500; i++, duty++)
		{
			for(duty = 0; duty < 4; duty++)
			{
				gen.generatePulse(100, duty, vol, Generator.C5 + i % 12);
				gen.generatePulse(100, duty, vol, Generator.C2 + 5 + i % 12);
				gen.generatePulse(100, duty, vol, Generator.C1 + i % 12);
			}
		}
	}
	
	//An example of a song using chords
	//SAMPLE: Zero's Theme - Kirby 64: The Crystal Shards
	public void song1() {
		short volume = 0x2400;
		short volume2 = 0x1000;
		int len = sampleRate/12;
		int tri = sampleRate/9;
		double width = 0.08;
		
		//BASS
		while(!gen.isAtEnd())
		{
			for(int i = 0; i < 4; i++)
			{
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 4);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 4);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
			}
			
			for(int i = 0; i < 4; i++)
			{
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 4);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 5);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 4);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 5);
			}
			
			for(int i = 0; i < 4; i++)
			{
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 5);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 5);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
			}
			
			for(int i = 0; i < 4; i++)
			{
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 2);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
				gen.generateNote(tri, width, volume, SoundGenerator.C2 + 2);
				gen.generateNote(tri, width, volume, SoundGenerator.C3 - 3);
			}
		}
		
		gen.resetMarker();
		
		gen.setMode(SoundGenerator.CHANNEL.SQUARE);
		
		//BACK1
		while(!gen.isAtEnd())
		{
			gen.generateNote(len*32, width, volume2, SoundGenerator.C4 - 3);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C4);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C4 - 3);
			gen.generateNote(len*24, width, volume2, SoundGenerator.C4 - 3);
			gen.generateNote(len*8, width, volume2, SoundGenerator.C4 - 1);
			
			gen.generateNote(len*32, width, volume2, SoundGenerator.C4 - 3);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C4);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C4 - 1);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C4 - 3);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C4 - 1);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C4 - 3);
		}
		
		gen.resetMarker();
		
		//BACK2
		while(!gen.isAtEnd())
		{
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 4);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 7);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 5);
			gen.generateNote(len*24, width, volume2, SoundGenerator.C3 + 5);
			gen.generateNote(len*8, width, volume2, SoundGenerator.C3 + 7);
			
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 4);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 7);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 5);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 5);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 5);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 5);
		}
		
		gen.resetMarker();
		
		//BACK3
		while(!gen.isAtEnd())
		{
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 4);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3);
			gen.generateNote(len*24, width, volume2, SoundGenerator.C3 + 2);
			gen.generateNote(len*8, width, volume2, SoundGenerator.C3 + 2);
			
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3);
			gen.generateNote(len*32, width, volume2, SoundGenerator.C3 + 4);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 2);
			gen.generateNote(len*16, width, volume2, SoundGenerator.C3 + 2);
		}
		
		gen.resetMarker();
		gen.pushMarker(len*256);

		//MELODY
		while(!gen.isAtEnd())
		{
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 - 3);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 + 7);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 + 4);
			gen.generateNote(len*7, 0.5, volume, SoundGenerator.C4);
			gen.generateNote(len*1, 0.5, volume, SoundGenerator.C4 + 1);
			gen.generateNote(len*14, 0.5, volume, SoundGenerator.C4 + 2);
			gen.generateNote(len*1, 0.5, volume, SoundGenerator.C4 + 1);
			gen.generateNote(len*1, 0.5, volume, SoundGenerator.C4);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 - 1);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 - 5);
			
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 - 3);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 + 5);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C4 + 4);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 - 3);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 - 1);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 + 2);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 - 1);
			
			gen.generateNote(len/2, 0.5, volume, SoundGenerator.C5);
			gen.generateNote(len/2, 0.5, volume, SoundGenerator.C5 + 2);
			gen.generateNote(len*14, 0.5, volume, SoundGenerator.C5 + 4);
			
			gen.generateNote(len/2, 0.5, volume, SoundGenerator.C5);
			gen.generateNote(len/2, 0.5, volume, SoundGenerator.C5 + 2);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 + 4);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5);
			
			gen.generateNote(len*14, 0.5, volume, SoundGenerator.C5 + 2);
			gen.generateNote(len, 0.5, volume, SoundGenerator.C5 + 1);
			gen.generateNote(len, 0.5, volume, SoundGenerator.C5);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 - 1);
			gen.generateNote(len*8, 0.5, volume, SoundGenerator.C5 - 5);
		}
	}
	
	//An example that imitates Gameboy audio
	//SAMPLE: Battle! Kanto Gym Leader - Pokemon Gold/Silver/Crystal
	public void song2()
	{
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		byte vol = 20;
		byte duty = GameboyGenerator.DUTY_25;
		int len = sampleRate/12;
		
		//MELODY
		while(!gen.isAtEnd())
		{
			for(int i = 0; i < 8; i++)
				for(int c = Generator.C6 - 2 - 2*i; c >= Generator.C6 - 5 - 2*i; c--)
					gen.generatePulse(len, duty, vol, c);
			
			duty = GameboyGenerator.DUTY_75;
			
			for(int i = 0; i < 2; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 6);
				gen.generatePulse(len, duty, vol, Generator.C4 + 4);
				gen.generatePulse(len, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 6);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len, duty, vol, Generator.C5 - 4);
				gen.generatePulse(len, duty, vol, Generator.C5 - 6);
				gen.generatePulse(len, duty, vol, Generator.C5 - 2);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C5 - 1);
				gen.generatePulse(len, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len, duty, vol, Generator.C5 - 4);
				gen.generatePulse(len, duty, vol, Generator.C5 - 2);
				
				if(i == 0) {
					gen.generatePulse(len*2, duty, vol, Generator.C4 + 2);
					gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
				}
				else{
					gen.generatePulse(len*2, duty, vol, Generator.C5 - 4);
					gen.generatePulse(len*2, duty, vol, Generator.C5 - 1);
				}
			}
			
			//OCTAVE CHANGE
			
			for(int i = 0; i < 2; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len, duty, vol, Generator.C5 + 6);
				gen.generatePulse(len, duty, vol, Generator.C5 + 4);
				gen.generatePulse(len, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len, duty, vol, Generator.C5 + 6);
				
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				gen.generatePulse(len, duty, vol, Generator.C6 - 4);
				gen.generatePulse(len, duty, vol, Generator.C6 - 6);
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 3);
				gen.generatePulse(len, duty, vol, Generator.C6 - 1);
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				gen.generatePulse(len, duty, vol, Generator.C6 - 4);
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 2);
				gen.generatePulse(len*2, duty, vol, Generator.C5 + 4);
			}
			
			//MAIN VERSE
			for(int i = 0; i < 2; i++)
			{
				duty = GameboyGenerator.DUTY_50;
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len*4, duty, vol, Generator.C5 - 4);
				gen.generatePulse(len*4, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 1);
				gen.generatePulse(len*4, duty, vol, Generator.C5 + 1);
				gen.generatePulse(len*4, duty, vol, Generator.C5 - 1);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 4);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 6);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 4);
				gen.generatePulse(len*2, duty, vol, Generator.C5 - 2);
				duty = GameboyGenerator.DUTY_25;
				gen.generatePulse(len*8, duty, vol, Generator.C4 + 4);
				gen.generatePulse(len*8, duty, vol, Generator.C5 - 4);
				
				if(i == 0) {
					gen.generatePulse(len*8, duty, vol, Generator.C5 - 1);
					gen.generatePulse(len*8, duty, vol, Generator.C5 - 4);
				}
				else {
					gen.generatePulse(len*10, duty, vol, Generator.C5 - 1);
					gen.generatePulse(len*6, duty, vol, Generator.C5 + 4);
				}
			}
			
			gen.generatePulse(len*20, duty, vol, Generator.C5 + 3);
			for(int i = 0; i < 3; i++)
			{
				gen.pushMarker(len*2);
				gen.generatePulse(len*2, duty, vol, Generator.C6 + 4);
			}
			gen.generatePulse(len*32, duty, vol, Generator.C6 + 3);
			
			//Measure 22
		}
		
		gen.resetMarker();
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		vol = 15;
		duty = GameboyGenerator.DUTY_75;
		len = sampleRate/12;
		
		//BASS
		while(!gen.isAtEnd())
		{
			for(byte i = 0; i < 8; i++)
				gen.generatePulse(len*4, duty, (byte)(vol + i - 8), Generator.C3 - 1);
			
			for(byte i = 0; i < 4; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 2);
				gen.generatePulse(len*4, duty, vol, Generator.C3 + 6);
				
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*4, duty, vol, Generator.C3 - 2);
				
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*4, duty, vol, Generator.C3 - 1);
				
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 1);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 4);
			}
			
			for(byte i = 0; i < 8; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 2);
			}
			
			for(byte i = 0; i < 8; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 4);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 1);
			}
			
			for(byte i = 0; i < 8; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 6);
				gen.generatePulse(len*2, duty, vol, Generator.C3 + 1);
			}
			
			for(byte i = 0; i < 8; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 4);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 1);
			}
			
			for(byte i = 0; i < 4; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 2);
				
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C3 - 1);
				
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C2 + 3);
			}
		}
		
		gen.resetMarker();
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		vol = 18;
		duty = GameboyGenerator.DUTY_12_5;
		len = sampleRate/12;
		
		//BACK
		while(!gen.isAtEnd())
		{
			for(byte i = 0; i < 8; i++)
			{
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				gen.generatePulse(len, duty, vol, Generator.C6 - 4);
				gen.generatePulse(len, duty, vol, Generator.C6 - 2);
				gen.generatePulse(len, duty, vol, Generator.C6 + 3);
			}
			
			gen.pushMarker(len*106);
			gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
			gen.generatePulse(len*2, duty, vol, Generator.C4 - 1);
			gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
			
			for(byte i = 0; i < 2; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				
				gen.generatePulse(len*4, duty, vol, Generator.C4 + 4);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C3 + 3);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C3 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 - 2);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				
				if(i == 0) {
					gen.generatePulse(len*8, duty, vol, Generator.C4 + 4);
					gen.generatePulse(len*8, duty, vol, Generator.C5 - 1);
					gen.generatePulse(len*8, duty, vol, Generator.C5 + 3);
					gen.generatePulse(len*8, duty, vol, Generator.C5 + 4);
				}
				else
				{
					gen.generatePulse(len*8, duty, vol, Generator.C4 + 1);
					gen.generatePulse(len*8, duty, vol, Generator.C4 - 1);
					gen.generatePulse(len*8, duty, vol, Generator.C4 - 4);
					gen.generatePulse(len*8, duty, vol, Generator.C4 - 1);
				}
			}
			
			for(byte i = 0; i < 2; i++)
			{
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 6);
				gen.generatePulse(len, duty, vol, Generator.C4 + 4);
				gen.generatePulse(len, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 6);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 10);
				gen.generatePulse(len, duty, vol, Generator.C4 + 8);
				gen.generatePulse(len, duty, vol, Generator.C4 + 6);
				gen.generatePulse(len, duty, vol, Generator.C4 + 10);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len, duty, vol, Generator.C4 + 11);
				gen.generatePulse(len, duty, vol, Generator.C4 + 10);
				gen.generatePulse(len, duty, vol, Generator.C4 + 8);
				gen.generatePulse(len, duty, vol, Generator.C4 + 10);
				
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 3);
				gen.generatePulse(len*2, duty, vol, Generator.C4 + 4);
			}
		}
	}
}
