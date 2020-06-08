package ku.opensrcsw.MBot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

public class UI extends JFrame implements WindowListener{
	
	String filename = "output";
	String filepath = "src/main/resources/" + filename + ".txt";
	

	NativeMouseListener mouseListener;
	NativeKeyboardListener keyboardListener;
	NativeWheelListener wheelListener;
	
	EventPlayer player;
	
	Container con;
	Dimension size = new Dimension(900, 600);
	JPanel titleBar = new JPanel();
	JLabel title = new JLabel("MBot");
	
	JPanel buttonBar = new JPanel();

	JLabel recording = new JLabel("Recording...");
	JLabel playing = new JLabel("Playing...");
	boolean isRec = false;
	boolean isPlay = false;
	boolean isStop = true;

	String startRec = "Start Recording (F1)";
	String endRec = "End Recording (F2)";
	String play = "Play (F3)";
	String pause = "Pause (F4)";
	JButton recBtn = new JButton(isRec? endRec : startRec);
	JButton playBtn = new JButton(isPlay? pause : play);
	JButton stopBtn = new JButton("Stop (esc)");
	
	JSpinner numSpinner = new JSpinner(new SpinnerNumberModel());
	
	JCheckBox endlessCheck = new JCheckBox("Run for an eternity");
	boolean isEndless = false;
	
	public UI() {
		super();
		//filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		//filepath = "src/main/resources/" + filename + ".txt";
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		this.initUI();
	}
	
	private void initUI() {
		con = this.getContentPane();
		this.setTitle("MBot");
		this.setSize(size);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//this.setLocation(0, 0);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
	}
	
	private void initComponents() {
		con.add(titleBar, BorderLayout.NORTH);
		
		title.setFont(new Font("Sans", Font.BOLD, 50));
		titleBar.setBorder(new EmptyBorder(10, 0, 10, 0));
		titleBar.setBackground(new Color(31, 51, 71));
		titleBar.add(title);
		
		buttonBar.setBorder(new EmptyBorder(15, 0, 15, 0));
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
		mouseListener = new NativeMouseListener(this, filepath);
		keyboardListener = new NativeKeyboardListener(this, filepath);
		wheelListener = new NativeWheelListener(this, filepath);
		
		GlobalScreen.addNativeMouseListener(mouseListener);
		GlobalScreen.addNativeMouseMotionListener(mouseListener);
		GlobalScreen.addNativeKeyListener(keyboardListener);
		GlobalScreen.addNativeMouseWheelListener(wheelListener);

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
				
			}
			
		});

		numSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(numSpinner.getValue().equals(0)) {
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
