package ku.opensrcsw.MBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class NativeKeyboardListener implements NativeKeyListener {

	UI frame;
	static String path; //need some clean up afterwards, we dont need this since we already have the frame (the frame contains the file path)
	
	public NativeKeyboardListener(UI frame, String filepath) {
		this.frame = frame;
		NativeKeyboardListener.path = filepath;
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_F2) { //end recording
			frame.isRec = false;
			frame.recBtn.setText(frame.startRec);
			frame.playBtn.setEnabled(true);
			frame.stopBtn.setEnabled(false);
			GlobalScreen.removeNativeKeyListener(frame.keyboardListener);
			GlobalScreen.removeNativeMouseListener(frame.mouseListener);
			GlobalScreen.removeNativeMouseMotionListener(frame.mouseListener);
			GlobalScreen.removeNativeMouseWheelListener(frame.wheelListener);
			GlobalScreen.addNativeKeyListener(frame.outerKeyListener);
		} else {
			BufferedWriter out;
			try {
				out = new BufferedWriter(new FileWriter(path, true));
				out.write("key press "+e.getKeyCode()+" "+NativeKeyEvent.getKeyText(e.getKeyCode())+"\n");
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write("key release "+e.getKeyCode()+" "+NativeKeyEvent.getKeyText(e.getKeyCode())+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
