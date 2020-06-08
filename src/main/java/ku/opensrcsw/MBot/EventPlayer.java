package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EventPlayer implements Runnable {

	UI frame;
	String path;
	Robot bot;
	Integer repetition;
	
	public EventPlayer(UI frame, String path) {
		this.frame = frame;
		this.path = path;
	}
	
	public void play(Integer num) {
		repetition = num;
		Thread t = new Thread(this);
		t.start();
		//this.run();
	}

	@Override
	public void run() {
		try {
			bot = new Robot();
			bot.setAutoDelay(50);
			bot.setAutoWaitForIdle(true);
			if(frame.isEndless) repetition = 1;
			for(int i = 0; i < repetition; i++) {
				//BufferedReader reader = Files.newBufferedReader(Paths.get(path));
				BufferedReader reader;
				do {
					reader = new BufferedReader(new FileReader(path));
					if(frame.isStop) {
						if(reader!=null) reader.close();
						return;
					}
					String line = reader.readLine();
					while (line != null) {
						if(frame.isPlay) {
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
			            		int keycode = Integer.parseInt(command[2]);
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
							while(!frame.isPlay) {
								// do nothing until isPlay == true again
							}
						}
		            }
					reader.close();
				} while(frame.isEndless);
			}
			frame.isStop = true;
			frame.isPlay = false;
			frame.playBtn.setText(frame.play);
			frame.stopBtn.setEnabled(false);
			frame.recBtn.setEnabled(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}
}
