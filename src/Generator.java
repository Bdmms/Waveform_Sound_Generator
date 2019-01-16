import java.io.FileWriter;
import java.io.IOException;

/*
 * Sean Rannie
 * January 16, 2018
 * 
 * This is the interface for the sound generator object, it contains useful
 * constants for the .wav file format and outlines the minimum functionality
 * required to implement the generator.
 */

public interface Generator 
{
	//Useful note values to use for generating notes
	public static final byte C0 = 0;
	public static final byte C1 = 12;
	public static final byte C2 = 24;
	public static final byte C3 = 36;
	public static final byte C4 = 48;
	public static final byte C5 = 60;
	public static final byte C6 = 72;
	public static final byte C7 = 84;
	public static final byte C8 = 96;
	public static final byte C9 = 108;
	public static final double A4_FREQUENCY = 440;
	
	public static final byte CHANNEL_LEFT = 0;	//constants to indicate the audio channel of the data
	public static final byte CHANNEL_RIGHT = 1;
	
	final char[] chunkID = {'R','I','F','F'};
	final char[] format = {'W','A','V','E'};
	final char[] subChunk1ID = {'f','m','t', ' '};
	final char[] subChunk2ID = {'d','a','t','a'};
	final int subChunk1Size = 16; //PCM standard
	final short audioFormat = 1; //Linear quantization
	final short numChannels = 2; //Stereo
	
	final double[] noteScale = new double[128]; //Defined from A4 (12 notes in an octave)
	
	//The class must contain a function to play a specified note value
	//length = length of the repeating waveform in number of samples
	//width = length of the repeating waveform during the period
	//volume = base volume of the waveform (16-bit)
	//note = the note value [0 (C0) to 120 (C10)]
	public void generateNote(int length, double width, short volume, int note);
	
	//The class must contain a function to play a pulse at a specified note value
	//length = length of the pulse note in number of samples
	//duty = duty cycle of the repeating waveform during the period (8-bit))
	//volume = base volume of the waveform (8-bit)
	//note = the note value [0 (C0) to 120 (C10)]
	public void generatePulse(int length, byte duty, byte volume, int note);
	
	//The class must contain a function to play a pulse at a specified frequency
	//length = length of the pulse frequency in number of samples
	//duty = duty cycle of the repeating waveform during the period (8-bit))
	//volume = base volume of the waveform (8-bit)
	//frequency = the frequency of the pulse in Hz
	public void generatePulse(int length, byte duty, byte volume, double frequency);
	
	//There must be a method to apply a filter to the waveform data
	public void applyFilter();
	
	//There must be a method to reset the generator marker
	public void resetMarker();
	
	//There must be a method to push the generator marker forward by a specified amount
	//length = number of samples to push marker
	public void pushMarker(int length);
	
	//There must be a method to write the header of the .wav file
	public void writeHeader(FileWriter writer) throws IOException;
	
	//There must be a method to write the waveform data within the .wav file
	public void writeData(FileWriter write) throws IOException;
	
	//There must be a method to return the size of the waveform data
	public int getLength();
	
	//There must be a method to indicate that the marker has reached the end of the data
	public boolean isAtEnd();
}
