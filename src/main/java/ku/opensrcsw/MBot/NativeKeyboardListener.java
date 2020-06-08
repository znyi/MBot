package ku.opensrcsw.MBot;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
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
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			//start recording
			frame.isRec = true;
			frame.recBtn.setText(frame.endRec);
			frame.playBtn.setEnabled(false);
			frame.stopBtn.setEnabled(false);
			//filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
			
			/* needed if saving new file is done
			frame.filename = "output";
			frame.filepath = "src/main/resources/" + frame.filename + ".txt";
			*/
			
			File file = new File(frame.filepath);
			try {
				boolean result = Files.deleteIfExists(file.toPath()); 
				if(GlobalScreen.isNativeHookRegistered()) {
					GlobalScreen.unregisterNativeHook();
					GlobalScreen.registerNativeHook();
				}
				//output is generated through appending repeatedly until closing of file 
				//need to delete existing output file to make it appears like the program is overwriting the output file
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
			
			/* needed if saving new file is done
			NativeMouseListener.path = frame.filepath;
			NativeKeyboardListener.path = frame.filepath;
			NativeWheelListener.path = frame.filepath;
			*/
			
		} else if (e.getKeyCode() == NativeKeyEvent.VC_F2) { //end recording
			frame.isRec = false;
			frame.recBtn.setText(frame.startRec);
			frame.playBtn.setEnabled(true);
			frame.stopBtn.setEnabled(false);
		} else if (e.getKeyCode() == NativeKeyEvent.VC_F3) { //start playing
			frame.isPlay = true;
			frame.playBtn.setText(frame.pause);
			frame.stopBtn.setEnabled(true);
			frame.recBtn.setEnabled(false);
			frame.isStop = false;
			frame.player = new EventPlayer(frame,frame.filepath);
			frame.player.play((Integer)frame.numSpinner.getValue());
		} else if (e.getKeyCode() == NativeKeyEvent.VC_F4) { //pause
			frame.isPlay = false;
			frame.playBtn.setText(frame.play);
			frame.stopBtn.setEnabled(true);
			frame.recBtn.setEnabled(false);
		} else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) { //end playing
			frame.isStop = true;
			frame.isPlay = false;
			frame.playBtn.setText(frame.play);
			frame.stopBtn.setEnabled(false);
			frame.recBtn.setEnabled(true);
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
