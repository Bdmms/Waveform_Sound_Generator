import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Sean Rannie
 * January 16, 2018
 * 
 * This is an abstract class that inherits the Generator interface, it defines
 * important data that should be used within a Generator class. While it is not
 * required, it is recommended that any Generator class created should extend this
 * class.
 */

public abstract class SoundGenerator implements Generator
{
	//Default set of modes
	public static enum CHANNEL {PULSE1, PULSE2, WAVE, NOISE, SQUARE, TRIANGLE};
	
	protected CHANNEL mode = CHANNEL.SQUARE;//The mode used to indicate what type of wave to generate
	protected short bitsPerSample = 16;		//The number of bits present in each sample
	protected int size = 0xFFFFF;			//Size of the data
	protected int marker = 0;				//The generator marker
	protected int sampleRate = 44100;		//The sample rate of the waveform (default = CD quality)
	protected byte numberOfChannels = 2;	//The number of output channels used (NOT to be confused with generating channels)
	protected boolean end = false;			//The indicator for the marker reaching the end
	
	//Default constructor (NOTE: data needs to be initialized in child class)
	public SoundGenerator(int sample, int sz)
	{
		size = sz;
		sampleRate = sample;
		double a = Math.pow(2, (double)1/12);
		
		for(int i = 0; i < noteScale.length; i++)
			noteScale[i] = A4_FREQUENCY*Math.pow(a, i - 57); 
	}
	
	//Required methods to define
	public abstract void generateNote(int length, double width, short volume, int note);
	public abstract void generatePulse(int length, byte duty, byte volume, int note);
	public abstract void generatePulse(int length, byte duty, byte volume, double frequency);
	public abstract void writeData(FileWriter write) throws IOException;
	
	//Method used to write the header of the .wav file. Unless the format 
	//needs to be changed, then it is recommended to use this method
	//write = FileWriter for writing the file data
	public void writeHeader(FileWriter write) throws IOException
	{
		//INTRO DATA
		int byteRate = (sampleRate*numChannels*bitsPerSample)/8;
		short blockAlign = (short) (numChannels*bitsPerSample/8);
		//ADDITIONS DO NOT EXIST WHEN USING PCM
		int subChunk2Size = size * numChannels * bitsPerSample/8;
		int chunkSize = 4 + (8 + subChunk1Size) + (8 + subChunk2Size);
		
		//Writing Data
		write.write(chunkID);
		writeInt(write, chunkSize);
		write.write(format);
		write.write(subChunk1ID);
		writeInt(write, subChunk1Size);
		writeShort(write, audioFormat);
		writeShort(write, numChannels);
		writeInt(write, sampleRate);
		writeInt(write, byteRate);
		writeShort(write, blockAlign);
		writeShort(write, bitsPerSample);
		write.write(subChunk2ID);
		writeInt(write, subChunk2Size);
		
		writeData(write);
	}
	
	//Method for reseting the marker
	public void resetMarker() {
		marker = 0; 
		end = false;
	}
	
	//Method for pushing the marker
	//length = number of samples to push marker by
	public void pushMarker(int length) {
		marker += length;
		if(marker >= size)
			end = true;
	}
	
	//Method for setting mode
	public void setMode(CHANNEL chan) {mode = chan;}
	//Method for retrieving size of the data
	public int getLength() {return size;}
	//Method for determining the end of the marking data
	public boolean isAtEnd() {return end;}
	
	//Method for writing integers as four independent bytes
	protected void writeInt(FileWriter write, int i) throws IOException
	{
		write.write((byte)(i%0xFF));
		write.write((byte)((i >> 8)%0xFF));
		write.write((byte)((i >> 16)%0xFF));
		write.write((byte)(i >> 24));
	}
	
	//Method for writing shorts as two independent bytes
	protected void writeShort(FileWriter write, short i) throws IOException
	{
		write.write((byte)(i%0xFF));
		write.write((byte)(i >> 8));
	}
	
	//Dummy method for drawing waveform on Swing component
	public void drawData(Graphics g, int offset) {}
}
