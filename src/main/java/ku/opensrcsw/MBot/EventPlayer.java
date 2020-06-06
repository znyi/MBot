package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EventPlayer {

	String path;
	Robot bot;

	public EventPlayer(String path) {
		this.path = path;
		try {
			bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void play(Boolean isPlay, Boolean isEndless, Integer repetition, Boolean isStop) {
		try {
			if(isEndless) repetition = 1;
			for(int i = 0; i < repetition; i++) {
				BufferedReader reader = Files.newBufferedReader(Paths.get(path));
				while(isEndless) {
					if(isStop) {
						if(reader!=null) reader.close();
						return;
					}
					String line = reader.readLine();
					while (line != null) {
						if(isPlay) {
							String[] command = line.split(" ");
			            	switch (command[0]) {
			            	case "mouse":
			            	{
			            		int button = Integer.parseInt(command[2]);
			            		if(command[1].equals("press")) {
			            			bot.mousePress(MouseEvent.getMaskForButton(button));
			            		} 
			            		else if (command[1].equals("release")) {
			            			bot.mouseRelease(MouseEvent.getMaskForButton(button));
			            		}
			            		break;
			            	}
			            	case "key":
			            	{
			            		int keycode = Integer.parseInt(command[1]);
			            		if(command[1].equals("press")) {
			            			bot.keyPress(keycode);
			            		} 
			            		else if (command[1].equals("release")) {
			            			bot.keyRelease(keycode);
			            		}
			            		break;
			            	}
			            	case "wheel":
			            	{
			            		int unitScroll = Integer.parseInt(command[1]);
			            		bot.mouseWheel(unitScroll);
			            		break;
			            	}
			            	case "move":
			            	{
			            		int x = Integer.parseInt(command[1]);
			            		int y = Integer.parseInt(command[2]);
			            		bot.mouseMove(x, y);
			            		break;
			            	}
			            	}
			                line = reader.readLine();
						} else {
							while(!isPlay) {
								// do nothing until isPlay == true again
							}
						}
		            }
				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
