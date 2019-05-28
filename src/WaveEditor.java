import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WaveEditor extends JPanel implements MouseMotionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private static final byte ADJUST_X = -3;
	private static final byte ADJUST_Y = -26;
	private static final byte WINDOWX_ADJUST = 7;
	private static final byte WINDOWY_ADJUST = 29;
	
	
	public static final int RESOLUTION_HEIGHT = 400;
	public static final int RESOLUTION_WIDTHPERNODE = 2;
	public static final int AXIS_SYMMETRY = 200;
	public static final int DEFAULT_SIZE = 320;
	
	private JFrame frame = new JFrame("Waveform");
	private double[] waveform;
	private int resolutionWidth;
	
	int sampleRate = 44100; //44100 = CD-QUALITY (# of samples per second)
	int size = 0xFFFFF; //2^32 samples used in a single file
	FileWriter write;
	File file = null;
	Clip clip = null;
	BasicGenerator gen = new BasicGenerator(sampleRate, size);
	
	public static void main(String[] args) {
		new WaveEditor();
	}
	
	public WaveEditor() {
		waveform = new double[DEFAULT_SIZE];
		resolutionWidth = DEFAULT_SIZE * RESOLUTION_WIDTHPERNODE;
		
		//Random sample
		for(int i = 0; i < waveform.length; i++)
			waveform[i] = Math.random() - 0.5;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(RESOLUTION_WIDTHPERNODE*waveform.length + WINDOWX_ADJUST, RESOLUTION_HEIGHT + WINDOWY_ADJUST));
        frame.setLocation(0, 0);
        frame.setBackground(Color.WHITE);
        frame.add(this);
        frame.addMouseMotionListener(this);
        frame.addKeyListener(this);
        
        try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {}
        
		while(true)
        {
        	this.repaint();
        	try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
        }
	}
	
	public void paintComponent(Graphics g) 
    {  
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, resolutionWidth, RESOLUTION_HEIGHT);
		
		//AXIS
		g.setColor(Color.black);
		g.drawLine(0, AXIS_SYMMETRY, resolutionWidth, AXIS_SYMMETRY);
		g.drawLine(0, 0, 0, RESOLUTION_HEIGHT);
	
		//WAVEFORM
		g.setColor(Color.blue);
		
		int prev = (int)(AXIS_SYMMETRY - waveform[waveform.length - 1]*AXIS_SYMMETRY);
		int value;
		for(int i = 0; i < waveform.length; i++)
		{
			value = (int)(AXIS_SYMMETRY - waveform[i]*AXIS_SYMMETRY);
			
			g.drawLine(i*RESOLUTION_WIDTHPERNODE, prev, i*RESOLUTION_WIDTHPERNODE, value);
			g.drawLine(i*RESOLUTION_WIDTHPERNODE, value, (i+1)*RESOLUTION_WIDTHPERNODE - 1, value);

			prev = value;
		}
    }

	public void mouseDragged(MouseEvent e) {
		if(e.getX() + ADJUST_X < resolutionWidth && e.getX() + ADJUST_X >= 0)
			waveform[(e.getX() + ADJUST_X)/RESOLUTION_WIDTHPERNODE] = (double)(-ADJUST_Y - e.getY() + AXIS_SYMMETRY)/AXIS_SYMMETRY;
	}

	public void mouseMoved(MouseEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			try {
				clip.stop();
				gen.deleteData();
				
				file = new File("demo2.wav");
				write = new FileWriter(file);
				clip = AudioSystem.getClip();
				
				for(int i = 0; i < 12; i++)
					gen.generateFreeWave(10000*i, 10000, 0.5f, SoundGenerator.noteScale[SoundGenerator.C5 + i], waveform);

					//gen.generateFreeWaveNoFrequency(5000*i, 5000, 0.5f, waveform);
					
				gen.writeHeader(write);
				clip.open(AudioSystem.getAudioInputStream(file));
		        clip.start();
		        write.close();
			} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {}
		}
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			for(int i = 0; i < waveform.length; i++)
				waveform[i] = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			//for(int i = 0; i < waveform.length; i++)
				//waveform[i] = Math.sin(2*Math.PI/waveform.length*i)/2;
			
			for(int i = 0; i < waveform.length; i++)
			{
				if(i < 20)
					waveform[i] = (float)i/40 + Math.sin(i*128*Math.PI/waveform.length)/12;
				else if(i > 580)
					waveform[i] = 0.45 + (float)(580 - i)/120 + Math.sin(i*64*Math.PI/waveform.length)/12;
				else
					waveform[i] = 0;
				
				/*
				if(i%(waveform.length/2) < waveform.length/4)
					waveform[i] = 0.5 + Math.sin(12*Math.PI/waveform.length*i)/16;
				else
					waveform[i] = Math.sin(12*Math.PI/waveform.length*i)/16;*/
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
}
