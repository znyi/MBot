package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;

public class EventPlayer {

	Robot bot;

	public EventPlayer() {
		try {
			bot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
