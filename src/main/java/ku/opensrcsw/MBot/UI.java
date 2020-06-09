package ku.opensrcsw.MBot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class UI extends JFrame implements WindowListener{
	
	String filename = "output";
	String filepath = "src/main/resources/" + filename + ".txt";
	

	NativeMouseListener mouseListener;
	NativeKeyboardListener keyboardListener;
	NativeWheelListener wheelListener;
	
	NativeKeyListener outerKeyListener;
	
	EventPlayer player;
	
	Container con;
	
	JPanel titleBar = new JPanel();
	JLabel titleLabel = new JLabel(" MBot");
	ImageIcon logoIcon = new ImageIcon("src/main/resources/icons8-music-robot-96.png");
	JLabel logoLabel = new JLabel(logoIcon);
	
	JPanel buttonBar = new JPanel();

	JLabel recording = new JLabel("Recording...");
	JLabel playing = new JLabel("Playing...");
	boolean isRec = false;
	boolean isPlay = false;
	boolean isStop = true;

	String startRec = "Start Recording (F1)";
	String endRec = "End Recording (F2)";
	String play = "Play (F3)";
	String cont = "Continue (F3)";
	String pause = "Pause (F4)";
	JButton recBtn = new JButton(isRec? endRec : startRec);
	JButton playBtn = new JButton(isPlay? pause : play);
	JButton stopBtn = new JButton("Stop (esc)");
	
	JSpinner numSpinner = new JSpinner(new SpinnerNumberModel());
	
	JCheckBox endlessCheck = new JCheckBox("Run for an eternity");
	boolean isEndless = false;
	
	Toolkit kit;
	
	public UI() {
		super();
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		this.initUI();
	}
	
	private void initUI() {
		con = this.getContentPane();
		kit = con.getToolkit();
		this.setIconImage(kit.getImage("src/main/resources/icons8-music-robot-48.png"));
		this.setTitle("MBot");
		this.setMinimumSize(new Dimension(500, 650));
		this.setSize(new Dimension(500,550));
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
	}
	
	private void initComponents() {
		con.add(titleBar, BorderLayout.NORTH);
		
		titleLabel.setFont(new Font("Sans", Font.BOLD, 50));
		titleBar.setBorder(new EmptyBorder(5, 30, 5, 30));
		titleBar.setBackground(new Color(31, 51, 71));
		titleBar.add(logoLabel);
		titleBar.add(titleLabel);
		
		buttonBar.setLayout(new GridLayout(5,1,10,10));
		buttonBar.setBorder(new EmptyBorder(35, 65, 30 , 65));
		buttonBar.add(recBtn);
		buttonBar.add(playBtn);
		buttonBar.add(stopBtn);
		stopBtn.setEnabled(false);
		
		Component numSpinnerEditor = numSpinner.getEditor();
		JFormattedTextField formatTF = ((JSpinner.DefaultEditor) numSpinnerEditor).getTextField();
		formatTF.setColumns(4);
		numSpinner.setValue(1);
		buttonBar.add(numSpinner);
		
		buttonBar.add(endlessCheck);
		
		con.add(buttonBar, BorderLayout.CENTER);
		
		initListener();
	}
	
	private void initListener() {
		mouseListener = new NativeMouseListener(this);
		keyboardListener = new NativeKeyboardListener(this);
		wheelListener = new NativeWheelListener(this);
		outerKeyListener = new NativeKeyListener() {

			@Override
			public void nativeKeyTyped(NativeKeyEvent e) {
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				if(e.getKeyCode() == NativeKeyEvent.VC_F1) { //start recording
					isRec = true;
					recBtn.setText(endRec);
					playBtn.setEnabled(false);
					stopBtn.setEnabled(false);
					
					//as a reference in case we want to save file using current date and time
					//filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")); 
					
					File file = new File(filepath);
					try {
						boolean result = Files.deleteIfExists(file.toPath()); 
						if(GlobalScreen.isNativeHookRegistered()) {
							GlobalScreen.removeNativeKeyListener(outerKeyListener);
						} else {
							GlobalScreen.registerNativeHook();
						}
						
						TimeTracker.initTime(); //set starting time
						//change native listeners for recording
						GlobalScreen.addNativeMouseListener(mouseListener);
						GlobalScreen.addNativeMouseMotionListener(mouseListener);
						GlobalScreen.addNativeKeyListener(keyboardListener);
						GlobalScreen.addNativeMouseWheelListener(wheelListener);
						
						//output is generated through appending repeatedly until closing of file 
						//need to delete existing output file to make it appear like the program is overwriting the output file
						
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NativeHookException e1) {
						e1.printStackTrace();
					}
					
				} else if (e.getKeyCode() == NativeKeyEvent.VC_F3) { //start playing or continue playing
					File file = new File(filepath);
					if(!file.exists()) {
						JOptionPane.showMessageDialog(UI.this, "No macro to be played.\nPlease record one.", "File Not Found", JOptionPane.WARNING_MESSAGE);
						return;
					}
					isPlay = true;               
					playBtn.setText(pause);
					stopBtn.setEnabled(true);    
					recBtn.setEnabled(false);    
					if(isStop) {
						player = new EventPlayer(UI.this);
						player.play();	
					} else {
						System.out.println("continue loop "+EventPlayer.count);
					}
				} else if (e.getKeyCode() == NativeKeyEvent.VC_F4) { //pause
					isPlay = false;
					playBtn.setText(cont);
					stopBtn.setEnabled(true);
					recBtn.setEnabled(false);
					System.out.println("pause at loop "+EventPlayer.count);
				} else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) { //end playing
					isStop = true;
					isPlay = false;
					playBtn.setText(play);
					stopBtn.setEnabled(false);
					recBtn.setEnabled(true);
				} 
			}

			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
			}
			
		};
		
		GlobalScreen.addNativeKeyListener(outerKeyListener);

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e2) {
			e2.printStackTrace();
		}
		
		recBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isRec) {
					JOptionPane.showMessageDialog(UI.this, "Press F1 to start recording.", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		
		playBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPlay) {
					JOptionPane.showMessageDialog(UI.this, "Press F3 to start playing the macro.", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isStop) {
					JOptionPane.showMessageDialog(UI.this, "Press esc to stop the macro.", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});

		numSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if((Integer)(numSpinner.getValue()) <= 0) {
					//JOptionPane.showMessageDialog(UI.this, "The number box must not contain\nnegative value. Reset as 1.", "Info", JOptionPane.INFORMATION_MESSAGE);
					numSpinner.setValue(1); // set any value larger than 0 to keep the loop going in EventPlayer
				}
			}
			
		});
		
		endlessCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED) {
					isEndless = true;
				} else {
					isEndless = false;
				}
			}
			
		});
		
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				Dimension curSize = UI.this.getSize();
				Dimension minSize = UI.this.getMinimumSize();
	            if(curSize.width >= minSize.width && curSize.height >= minSize.height) {// limit the size of the frame
	            	super.componentResized(e);
	            }
			}
			
		});
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		GlobalScreen.removeNativeMouseListener(mouseListener);
		GlobalScreen.removeNativeMouseMotionListener(mouseListener);
		GlobalScreen.removeNativeKeyListener(keyboardListener);
		GlobalScreen.removeNativeMouseWheelListener(wheelListener);
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException nhe) {
			nhe.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
	
	
}
