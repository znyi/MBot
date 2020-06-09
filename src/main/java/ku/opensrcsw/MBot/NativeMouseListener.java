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
			out.write("mouse press "+e.getButton()+"\n");
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
			out.write("mouse release "+e.getButton()+"\n");
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
