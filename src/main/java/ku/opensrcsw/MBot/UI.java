package ku.opensrcsw.MBot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	JButton recBtn = new JButton(isRec? startRec : endRec);
	JButton playBtn = new JButton(isPlay? pause : play);
	JButton stopBtn = new JButton("Stop");
	
	
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
		con.add(buttonBar, BorderLayout.CENTER);
		
	}
}
