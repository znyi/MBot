package ku.opensrcsw.MBot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Robot bot;
		try {
			bot = new Robot();
			bot.keyPress(112);
	    	bot.keyRelease(112);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
