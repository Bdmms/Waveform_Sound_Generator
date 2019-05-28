import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl.Type;
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
	File file = null;
	
	//Starting Data
	int sampleRate = 44100; //44100 = CD-QUALITY (# of samples per second)
	int size = sampleRate*32; //2^40 samples used in a single file
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		file = new File("demo.wav");
		
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
			
			SongResource.song3(gen, sampleRate);
			
			//testEffect();
			
			gen.applyFilter(); //(USED TO APPLY FILTER ON COMMAND)
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
	        clip.loop(12);
	        
	        System.out.println("---Playing File---");
	        System.out.println("Sample Rate: " + clip.getFrameLength()/(clip.getMicrosecondLength()/1000000) + " samples/sec");
	        System.out.println("Sample Size: " + clip.getFrameLength() + " samples");
	        System.out.println("Time Length: " + clip.getMicrosecondLength()/(double)1000000 + " sec");
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
		
		if(gen != null && clip != null)
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
		byte vol = 5;
		byte duty = 0;
		
		gen.setMode(SoundGenerator.CHANNEL.WAVE);
		for(int i = Generator.C1; i < 5000; i++, duty++)
		{
			gen.generatePulse(500, (byte)0, vol, Generator.C6 + i % 2);
			for(duty = 0; duty < 4; duty++)
			{
				//gen.generatePulse(100, duty, (byte)(vol), Generator.C5 + i % 40);
				//gen.generatePulse(75, duty, (byte)(vol), Generator.C2 + 5 + i % 48);
				//gen.generatePulse(50, duty, (byte)(vol), Generator.C1 + i % 36);
			}
		}
	}
}
