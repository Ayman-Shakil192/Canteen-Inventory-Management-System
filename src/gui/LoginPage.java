package gui;
import models.Auth;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	
	public void call() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(SystemColor.control);
		frame.getContentPane().setBackground(new Color(0xf3f7f7));
		frame.setBounds(100, 100, 481, 336);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Login");
				
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Cambria Math", Font.BOLD, 40));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(171, 10, 125, 43);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 26));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(43, 90, 149, 37);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 26));
		lblNewLabel_2.setBounds(43, 155, 149, 37);
		frame.getContentPane().add(lblNewLabel_2);
		
		txtUser = new JTextField();
		txtUser.setForeground(Color.BLACK);
		txtUser.setBackground(new Color(0xF2F2F2));
		txtUser.setToolTipText("");
		txtUser.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setBounds(218, 90, 217, 37);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(0xF2F2F2));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		passwordField.setBounds(218, 149, 217, 37);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton 
						&& !txtUser.getText().isEmpty() 
						&& !new String(passwordField.getPassword()).isEmpty()) {
					String user = txtUser.getText();
					String pass = new String(passwordField.getPassword());
					
					try {
						Integer.parseInt(user);
						frame.dispose();
						new Auth(user, pass);
					}
					catch(NumberFormatException ne) {
						JOptionPane.showMessageDialog(null, "Enter only integers in username", "Username", JOptionPane.INFORMATION_MESSAGE);
						txtUser.setText(null);
						passwordField.setText(null);
					}
					
				}
			}
		});
		btnNewButton.setBackground(new Color(0xba94c));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		btnNewButton.setBounds(217, 214, 114, 43);
		frame.getContentPane().add(btnNewButton);
		frame.setLocationRelativeTo(null);
	}
}
