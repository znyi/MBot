package ku.opensrcsw.MBot;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class NativeMouseListener implements NativeMouseInputListener {

	UI frame;
	
	public NativeMouseListener(UI frame) {
		this.frame = frame;
	}
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println("press mouse button "+e.getButton()+" at ("+e.getX()+","+e.getY()+") "+e.getWhen());
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		System.out.println("release mouse button "+e.getButton()+" at ("+e.getX()+","+e.getY()+") "+e.getWhen());
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		
	}

}
