package ku.opensrcsw.MBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class NativeWheelListener implements NativeMouseWheelListener {
	
	UI frame;
	
	public NativeWheelListener(UI frame) {
		this.frame = frame;
	}
	
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(frame.filepath, true));
			out.write("wheel " + e.getWheelRotation()+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
