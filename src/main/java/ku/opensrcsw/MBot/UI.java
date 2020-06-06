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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;

public class UI extends JFrame {

	String filename;
	String filepath;
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

	String startRec = "Start Recording";
	String endRec = "End Recording";
	String play = "Play";
	String pause = "Pause";
	JButton recBtn = new JButton(isRec? endRec : startRec);
	JButton playBtn = new JButton(isPlay? pause : play);
	JButton stopBtn = new JButton("Stop");
	
	JSpinner numSpinner = new JSpinner(new SpinnerNumberModel());
	
	JCheckBox endlessCheck = new JCheckBox("Run for an eternity");
	boolean isEndless = false;
	
	public UI() {
		super();
		filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
		filepath = "src/main/resources/" + filename + ".txt";
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		this.initUI();
	}
	
	private void initUI() {
		con = this.getContentPane();
		this.setTitle("MBot");
		this.setSize(size);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
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
		NativeMouseListener mouseListener = new NativeMouseListener(this, filepath);
		NativeKeyboardListener keyboardListener = new NativeKeyboardListener(this, filepath);
		NativeWheelListener wheelListener = new NativeWheelListener(this, filepath);
		
		GlobalScreen.addNativeMouseListener(mouseListener);
		GlobalScreen.addNativeMouseMotionListener(mouseListener);
		GlobalScreen.addNativeKeyListener(keyboardListener);
		GlobalScreen.addNativeMouseWheelListener(wheelListener);
		
		recBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isRec = !isRec;
				if(isRec) {
					recBtn.setText(endRec);
					playBtn.setEnabled(false);
					stopBtn.setEnabled(false);
					//filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
					filename = "output";
					filepath = "src/main/resources/" + filename + ".txt";
					File file = new File(filepath);
					try {
						boolean result = Files.deleteIfExists(file.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						NativeMouseListener.path = filepath;
						NativeKeyboardListener.path = filepath;
						NativeWheelListener.path = filepath;
						GlobalScreen.registerNativeHook();
					} catch (NativeHookException nhe) {
						nhe.printStackTrace();
					}
				} else {
					recBtn.setText(startRec);
					playBtn.setEnabled(true);
					stopBtn.setEnabled(false);
					try {
						GlobalScreen.unregisterNativeHook();
					} catch (NativeHookException nhe) {
						nhe.printStackTrace();
					}
				}
			}
			
		});
		
		playBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isPlay = !isPlay;
				if(isPlay) {
					playBtn.setText(pause);
					stopBtn.setEnabled(true);
					recBtn.setEnabled(false);
					isStop = false;
					player = new EventPlayer(filepath);
					player.play(isPlay, isEndless, (Integer)numSpinner.getValue(), isStop);
				} else {
					playBtn.setText(play);
					stopBtn.setEnabled(true);
					recBtn.setEnabled(false);
				}
			}
			
		});
		
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isPlay = !isPlay;
				isStop = true;
				playBtn.setText(play);
				stopBtn.setEnabled(false);
				recBtn.setEnabled(true);
			}
			
		});

		numSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(numSpinner.getValue().equals(0)) {
					numSpinner.setValue(1);
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
	
	
}
