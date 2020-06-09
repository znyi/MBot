package ku.opensrcsw.MBot;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.keyboard.SwingKeyAdapter;

public class NativeKeyboardListener extends SwingKeyAdapter implements NativeKeyListener {

	UI frame;
	
	public NativeKeyboardListener(UI frame) {
		this.frame = frame;
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
				out = new BufferedWriter(new FileWriter(frame.filepath, true));
				//the key codes of NativeKeyEvent and KeyEvent are different
				//since Robot only recognizes KeyEvent, we call getJavaKeyEvent() to get corresponding KeyEvent of the native event
				out.write("key press "+this.getJavaKeyEvent(e).getKeyCode()+" "+KeyEvent.getKeyText(this.getJavaKeyEvent(e).getKeyCode())+"\n");
				System.out.println(e.getKeyCode()+" "+NativeKeyEvent.getKeyText(e.getKeyCode()));
				out.write("delay "+Long.toUnsignedString(TimeTracker.getTime())+"\n");
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_F1) { //end recording
			return; // do nothing
		}
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(frame.filepath, true));
			out.write("key release "+this.getJavaKeyEvent(e).getKeyCode()+" "+KeyEvent.getKeyText(this.getJavaKeyEvent(e).getKeyCode())+"\n");
			out.write("delay "+Long.toUnsignedString(TimeTracker.getTime())+"\n");
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected KeyEvent getJavaKeyEvent(NativeKeyEvent nativeEvent) {
		// TODO Auto-generated method stub
		return super.getJavaKeyEvent(nativeEvent);
	}

	
}
