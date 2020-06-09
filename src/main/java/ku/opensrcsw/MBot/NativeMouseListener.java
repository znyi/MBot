package ku.opensrcsw.MBot;

import java.awt.MouseInfo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class NativeMouseListener implements NativeMouseInputListener, NativeMouseMotionListener {

	UI frame;
	
	public NativeMouseListener(UI frame) {
		this.frame = frame;
	}
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(frame.filepath, true));
			int button = e.getButton();
    		if(button == 2) button = 3; //button 1:left, 2: right, 3: middle, 4 and 5: side 
    		else if (button == 3) button = 2;
			out.write("mouse press "+button+"\n");
			out.write("delay "+Long.toUnsignedString(TimeTracker.getTime())+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(frame.filepath, true));
			int button = e.getButton();
    		if(button == 2) button = 3;
    		else if (button == 3) button = 2;
			out.write("mouse release "+button+"\n");
			out.write("delay "+Long.toUnsignedString(TimeTracker.getTime())+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(frame.filepath, true));
			out.write("move " + MouseInfo.getPointerInfo().getLocation().x + " " + MouseInfo.getPointerInfo().getLocation().y+"\n");
			out.write("delay "+Long.toUnsignedString(TimeTracker.getTime())+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		nativeMouseMoved(e);
	}

}
