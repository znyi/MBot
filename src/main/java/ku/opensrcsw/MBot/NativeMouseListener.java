package ku.opensrcsw.MBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class NativeMouseListener implements NativeMouseInputListener, NativeMouseMotionListener {

	UI frame;
	static String path;
	
	public NativeMouseListener(UI frame, String filepath) {
		this.frame = frame;
		NativeMouseListener.path = filepath;
	}
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write("mouse press "+e.getButton()+" "+e.getX()+" "+e.getY()+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write("mouse release "+e.getButton()+" "+e.getX()+" "+e.getY()+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write("move " + e.getX() + " " + e.getY()+"\n");
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
