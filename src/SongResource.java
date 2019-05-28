
public class SongResource 
{
	//An example of a song using chords
	//SAMPLE: Zero's Theme - Kirby 64: The Crystal Shards
	public static void song1(SoundGenerator gen, int sampleRate) 
	{
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
		gen.pushMarker(len*256, false);

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
	public static void song2(SoundGenerator gen, int sampleRate)
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
				gen.pushMarker(len*2, false);
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
			
			gen.pushMarker(len*106, false);
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
	
	//SAMPLE: The Legend of Zelda: Link's Awakening
	public static void song3(SoundGenerator gen, int sampleRate)
	{
		byte vol = 12;
		byte duty = GameboyGenerator.DUTY_50;
		int len = sampleRate/16; //1/16 of a second
		gen.setAutoSpace(len);
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		
		//CHANNEL 1: BASS 1
		for(byte a = 0; a < 2; a++)
		{
			byte note = 0;
			for(byte b = 0; b < 8; b++)
			{
				switch(b)
				{
				case 0: note = Generator.C4 - 1; break;
				case 1: note = Generator.C4 - 3; break;
				case 2: note = Generator.C4 - 5; break;
				case 3: note = Generator.C4 - 6; break;
				case 4: note = Generator.C4 - 1; break;
				case 5: note = Generator.C4 - 3; break;
				default: note = Generator.C4 - 2; break;
				}
				
				for(byte c = 0; c < 4; c++)
				{
					if(b == 7 && c == 3)
						note = Generator.C4 - 3;
						
					if(b == 0 && c == 0)
						gen.generatePulse(len, duty, vol, Generator.C5 - 1);
					else
						gen.generatePulse(len, duty, vol, note);
					
					gen.pushMarker(len, true);
					gen.generatePulse(len, duty, vol, note);
					gen.generatePulse(len, duty, vol, note);
				}
			}
		}
		
		gen.resetMarker();
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		vol = 12;
		duty = GameboyGenerator.DUTY_50;
		
		//CHANNEL 2: BASS 2
		for(byte a = 0; a < 2; a++)
		{
			byte note = 0;
			for(byte b = 0; b < 8; b++)
			{
				switch(b)
				{
				case 0: note = Generator.C4 - 5; break;
				case 1: note = Generator.C4 - 6; break;
				case 2: note = Generator.C4 - 8; break;
				case 3: note = Generator.C3 - 1; break;
				case 4: note = Generator.C4 - 5; break;
				case 5: note = Generator.C4 - 6; break;
				case 6: note = Generator.C4 - 7; break;
				case 7: note = Generator.C4 - 8; break;
				}
				
				for(byte c = 0; c < 4; c++)
				{
					if(b == 7 && c == 2)
						note = Generator.C4 - 9;
					
					gen.generatePulse(len, duty, vol, note);
					gen.pushMarker(len, true);
					gen.generatePulse(len, duty, vol, note);
					gen.generatePulse(len, duty, vol, note);
				}
			}
		}
		
		gen.resetMarker();
		gen.setMode(SoundGenerator.CHANNEL.PULSE2);
		vol = 28;
		duty = GameboyGenerator.DUTY_50;
		
		//CHANNEL 3: MELODY
		//1
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		
		gen.generatePulse(len*7, duty, vol, Generator.C5 + 6);//
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 1);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 4);//
		
		//2
		gen.generatePulse(len, duty, vol, Generator.C3 - 6);
		gen.generatePulse(len, duty, vol, Generator.C3 + 1);
		gen.generatePulse(len*3, duty, vol, Generator.C5 + 4);//
		
		gen.generatePulse(len*3, duty, vol, Generator.C5 - 3);//
		gen.generatePulse(len*3, duty, vol, Generator.C6 - 3);//
		
		gen.generatePulse(len, duty, vol, Generator.C4 + 4);
		gen.generatePulse(len, duty, vol, Generator.C4 + 1);
		gen.generatePulse(len, duty, vol, Generator.C4 - 3);
		gen.generatePulse(len, duty, vol, Generator.C4 - 6);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 4);
		gen.generatePulse(len, duty, vol, Generator.C3 + 1);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3 - 6);
		
		//3
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);
		gen.generatePulse(len, duty, vol, Generator.C2 + 7);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len*7, duty, vol, Generator.C5 + 2);//
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 5);//
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 5);//
		gen.generatePulse(len, duty, vol, Generator.C5 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C5 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 1);//
		
		//4
		gen.generatePulse(len, duty, vol, Generator.C2 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len*3, duty, vol, Generator.C5 + 1);//
		
		gen.generatePulse(len*3, duty, vol, Generator.C4 + 6);//
		gen.generatePulse(len*3, duty, vol, Generator.C5 + 6);//
		
		gen.generatePulse(len, duty, vol, Generator.C4 + 1);
		gen.generatePulse(len, duty, vol, Generator.C4 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 1);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C2 + 6);
		gen.generatePulse(len, duty, vol, Generator.C2 + 2);
		
		//5
		gen.generatePulse(len, duty, vol, Generator.C2 + 7);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		
		gen.generatePulse(len*3, duty, vol, Generator.C5 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 7);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 9);//
		
		gen.generatePulse(len*3, duty, vol, Generator.C6 -1);//
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		gen.generatePulse(len, duty, vol, Generator.C4 - 1);
		gen.generatePulse(len, duty, vol, Generator.C6 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C6 - 5);//
		
		//6
		gen.generatePulse(len, duty, vol, Generator.C6 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C2 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C5 + 7);//
		
		gen.generatePulse(len*3, duty, vol, Generator.C5 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 4);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 2);//
		
		gen.generatePulse(len, duty, vol, Generator.C2 + 2);
		gen.generatePulse(len, duty, vol, Generator.C2 + 6);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len*7, duty, vol, Generator.C5 + 2);//
		
		//7
		gen.generatePulse(len, duty, vol, Generator.C2 + 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3    );
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.generatePulse(len, duty, vol, Generator.C5 + 5);//
		gen.generatePulse(len, duty, vol, Generator.C6 - 2);//
		gen.generatePulse(len, duty, vol, Generator.C6    );//
		gen.generatePulse(len*3, duty, vol, Generator.C6 + 2);//
		
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C2 + 5);
		gen.generatePulse(len, duty, vol, Generator.C3 + 5);
		
		gen.generatePulse(len, duty, vol, Generator.C4 - 3);
		gen.generatePulse(len, duty, vol, Generator.C4 + 5);
		gen.generatePulse(len, duty, vol, Generator.C6    );//
		gen.generatePulse(len, duty, vol, Generator.C6 - 2);//
		
		//8
		gen.generatePulse(len*5, duty, vol, Generator.C6    );//
		gen.generatePulse(len, duty, vol, Generator.C6 + 7);//
		
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C4    );
		gen.generatePulse(len, duty, vol, Generator.C4 + 7);
		
		gen.generatePulse(len*5, duty, vol, Generator.C6 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C7   );//
		
		gen.generatePulse(len, duty, vol, Generator.C2 + 3);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3    );
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		
		//9
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		
		gen.generatePulse(len*7, duty, vol, Generator.C5 + 6);//
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C3 - 5);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		
		gen.pushMarker(len, true);
		gen.generatePulse(len, duty, vol, Generator.C7 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C6 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C7 - 1);//
		
		//10
		gen.generatePulse(len, duty, vol, Generator.C7 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 1);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 4);//
		
		gen.generatePulse(len*3, duty, vol, Generator.C4 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 7);//
		gen.generatePulse(len*7, duty, vol, Generator.C5 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C2 + 6);//
		
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);
		gen.generatePulse(len, duty, vol, Generator.C2 + 1);
		gen.generatePulse(len, duty, vol, Generator.C2 + 6);
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		
		//11
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);//
		gen.generatePulse(len, duty, vol, Generator.C3 + 7);//
		gen.generatePulse(len, duty, vol, Generator.C4 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);//
		
		gen.generatePulse(len*7, duty, vol, Generator.C4 + 2);//
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 7);
		gen.generatePulse(len, duty, vol, Generator.C2 + 4);
		gen.generatePulse(len, duty, vol, Generator.C2 + 7);
		gen.generatePulse(len, duty, vol, Generator.C3 - 1);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 7);//
		gen.generatePulse(len, duty, vol, Generator.C3 + 9);//
		gen.generatePulse(len, duty, vol, Generator.C4 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 1);//
		
		//12
		gen.generatePulse(len, duty, vol, Generator.C3 - 3);
		gen.generatePulse(len, duty, vol, Generator.C3 + 1);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 4);
		
		gen.generatePulse(len*3, duty, vol, Generator.C4 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 4);//
		gen.generatePulse(len*7, duty, vol, Generator.C4 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C4 - 1);//
		
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 7);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 9);//
		
		//13
		gen.generatePulse(len*7, duty, vol, Generator.C5 - 1);//
		
		gen.generatePulse(len, duty, vol, Generator.C2 + 7);
		gen.generatePulse(len, duty, vol, Generator.C2 + 9);
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 7);
		gen.generatePulse(len, duty, vol, Generator.C3 + 9);
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);
		gen.generatePulse(len, duty, vol, Generator.C4 + 6);
		
		gen.generatePulse(len, duty, vol, Generator.C5 - 1);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 1);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 4);//
		
		//14
		gen.generatePulse(len*5, duty, vol, Generator.C5 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C6 - 3);//
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		gen.generatePulse(len, duty, vol, Generator.C4 - 3);
		gen.generatePulse(len, duty, vol, Generator.C4 - 1);
		gen.generatePulse(len, duty, vol, Generator.C4 + 1);
		
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);
		gen.generatePulse(len, duty, vol, Generator.C5 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C5 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C6 - 3);//
		
		gen.generatePulse(len, duty, vol, Generator.C7 - 3);//
		gen.generatePulse(len, duty, vol, Generator.C3 + 6);
		gen.generatePulse(len, duty, vol, Generator.C4 - 3);
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);
		
		//15
		gen.generatePulse(len*5, duty, vol, Generator.C6 - 2);//
		gen.generatePulse(len, duty, vol, Generator.C7 - 2);//
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 5);//
		gen.generatePulse(len, duty, vol, Generator.C5 - 2);//
		gen.generatePulse(len, duty, vol, Generator.C5    );//
		gen.generatePulse(len*7, duty, vol, Generator.C5 + 2);//
		gen.generatePulse(len, duty, vol, Generator.C4 + 2);
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 2);
		gen.generatePulse(len, duty, vol, Generator.C4 + 5);//
		gen.generatePulse(len, duty, vol, Generator.C5    );//
		gen.generatePulse(len, duty, vol, Generator.C5 - 2);//
		
		//16
		gen.generatePulse(len*5, duty, vol, Generator.C5    );//
		gen.generatePulse(len, duty, vol, Generator.C5 + 7);//
		
		gen.generatePulse(len, duty, vol, Generator.C3 + 4);
		gen.generatePulse(len, duty, vol, Generator.C3 + 5);
		gen.generatePulse(len, duty, vol, Generator.C3 + 7);
		gen.generatePulse(len, duty, vol, Generator.C3 + 9);
		
		gen.generatePulse(len*5, duty, vol, Generator.C5 + 6);//
		gen.generatePulse(len, duty, vol, Generator.C6    );//
		
		gen.generatePulse(len, duty, vol, Generator.C4 + 3);
		gen.generatePulse(len, duty, vol, Generator.C4 + 6);
		gen.generatePulse(len, duty, vol, Generator.C4 + 9);
		gen.generatePulse(len, duty, vol, Generator.C5    );
	}
}
