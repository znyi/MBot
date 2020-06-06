package ku.opensrcsw.MBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class NativeKeyboardListener implements NativeKeyListener {

	UI frame;
	static String path;
	
	public NativeKeyboardListener(UI frame, String filepath) {
		this.frame = frame;
		NativeKeyboardListener.path = filepath;
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) { //end recording
			frame.recBtn.setText(frame.startRec);
			frame.playBtn.setEnabled(true);
			frame.stopBtn.setEnabled(false);
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException nhe) {
				nhe.printStackTrace();
			}
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
