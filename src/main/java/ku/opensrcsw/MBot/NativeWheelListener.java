package ku.opensrcsw.MBot;

import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class NativeWheelListener implements NativeMouseWheelListener {
	UI frame;
	
	public NativeWheelListener(UI frame) {
		this.frame = frame;
	}
	
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		System.out.println("wheel : "+e.getWheelRotation()+" unit");
		
	}

}
