package ku.opensrcsw.MBot;

import javax.swing.JFrame;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class NativeKeyboardListener implements NativeKeyListener {

	UI frame;
	
	public NativeKeyboardListener(UI frame) {
		this.frame = frame;
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			frame.recBtn.setText(frame.startRec);
			frame.playBtn.setEnabled(true);
			frame.stopBtn.setEnabled(false);
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException nhe) {
				nhe.printStackTrace();
			}
		} else {
			System.out.println("press key - keycode : "+e.getKeyCode()+", keytext : "+NativeKeyEvent.getKeyText(e.getKeyCode()));
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("release key - keycode : "+e.getKeyCode()+", keytext : "+NativeKeyEvent.getKeyText(e.getKeyCode()));
		
	}

}
