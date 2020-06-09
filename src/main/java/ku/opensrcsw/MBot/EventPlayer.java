package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class EventPlayer implements Runnable {

	UI frame;
	Robot bot;
	Integer repetition;
	static Integer count;
	
	public EventPlayer(UI frame) {
		this.frame = frame;
	}
	
	public void play() {
		repetition = (Integer)frame.numSpinner.getValue();
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			bot = new Robot();
			bot.setAutoDelay(20);
			bot.setAutoWaitForIdle(true);
			frame.isStop = false;
			count = 0;
			for(; count < repetition; count++) {
				System.out.println("starting loop "+count);
				BufferedReader reader;
				do {
					try {
						reader = new BufferedReader(new FileReader(frame.filepath));
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
				            			System.out.println("press"+" "+keycode);
				            			bot.keyPress(keycode);//test
				            		} 
				            		else if (command[1].equals("release")) {
				            			System.out.println("release"+" "+keycode);
				            			bot.keyRelease(keycode);//
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
				            	case "delay":
				            	{
				            		int delay = Integer.parseInt(command[1]);
				            		bot.delay(delay);
				            	}
				            	}
				                line = reader.readLine();
							} else {
								bot.delay(1); //pause
							}
			            }
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} while(frame.isEndless);
				System.out.println("ending loop "+count);
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
