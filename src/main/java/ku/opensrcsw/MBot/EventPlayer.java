package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class EventPlayer implements Runnable {

	UI frame;
	String path;
	Robot bot;
	Integer repetition;
	static Integer count;
	
	public EventPlayer(UI frame, String path) {
		this.frame = frame;
		this.path = path;
	}
	
	public void play() {
		repetition = (Integer)frame.numSpinner.getValue();
		Thread t = new Thread(this);
		t.start();
		//this.run();
	}

	@Override
	public void run() {
		try {
			bot = new Robot();
			bot.setAutoDelay(20);
			bot.setAutoWaitForIdle(true);
			if(frame.isEndless) repetition = 1;
			for(int i = 0; i < repetition; i++) {
				System.out.println("starting loop "+i);
				BufferedReader reader;
				do {
					try {
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
				            		//System.out.print(MouseInfo.getPointerInfo().getLocation().x+" ");
				            		int y = Integer.parseInt(command[2]);
				            		//System.out.println(MouseInfo.getPointerInfo().getLocation().y);
				            		bot.mouseMove(x, y);
				            		break;
				            	}
				            	}
				                line = reader.readLine();
				                while(!frame.isPlay) { //pause in the middle of the macro run
									// do nothing until isPlay == true again
								}
							}
			            }
						reader.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame, "No macro to be played.\nPlease record one.", "File Not Found", JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}
					
				} while(frame.isEndless);
				System.out.println("ending loop "+i);
			}
			frame.isStop = true;
			frame.isPlay = false;
			frame.playBtn.setText(frame.play);
			frame.stopBtn.setEnabled(false);
			frame.recBtn.setEnabled(true);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}
}
