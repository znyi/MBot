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
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class UI extends JFrame {

	Container con;
	Dimension size = new Dimension(900, 600);
	JPanel titleBar = new JPanel();
	JLabel title = new JLabel("MBot");
	
	JPanel buttonBar = new JPanel();

	JLabel recording = new JLabel("Recording...");
	JLabel playing = new JLabel("Playing...");
	boolean isRec = false;
	boolean isPlay = false;

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
		GlobalScreen.addNativeMouseListener(new NativeMouseListener(this));
		GlobalScreen.addNativeKeyListener(new NativeKeyboardListener(this));
		GlobalScreen.addNativeMouseWheelListener(new NativeWheelListener(this));
		GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {

			@Override
			public void nativeMouseMoved(NativeMouseEvent e) {
				
			}

			@Override
			public void nativeMouseDragged(NativeMouseEvent e) {
				
			}
			
		});
		recBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isRec = !isRec;
				if(isRec) {
					recBtn.setText(endRec);
					playBtn.setEnabled(false);
					stopBtn.setEnabled(false);
					try {
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
