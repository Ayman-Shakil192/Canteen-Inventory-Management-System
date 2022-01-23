package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class OrderDetail {

	private JFrame frame;
	private Connection con;
	private JTextField textField;
	private JPanel panel;
	private JLabel lblNewLabel_1;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderDetail window = new OrderDetail();
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
	public OrderDetail() {
		initialize();
		connect();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	private void load(int a) {
		
		
		panel.removeAll();
		panel.repaint();
		panel.revalidate();
		
		
		
		try {
			HashMap<String, Object> p = new HashMap<>();
			p.put("invo", a);
			
			JasperDesign jdesign = JRXmlLoader.load("E:\\Eclipse\\canteen-inventory-system\\src\\models\\OrderDetails.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(jdesign);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, p, con);
			JRViewer v = new JRViewer(jprint);
			panel.setLayout(new BorderLayout());
			panel.add(v);
			
		} catch (JRException e1) {
			JOptionPane.showMessageDialog(null, "Database Connection error 1", "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		frame.getContentPane().add(panel);
	}	

	private void initialize() {
		frame = new JFrame("Order Details");
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//	    frame.setBounds(0,0,screenSize.width, screenSize.height);
		frame.setBounds(250, 100, 1303, 700);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel.setBounds(15, 134, 1256, 701);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1.setBounds(10, 10, 1269, 94);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Order ID :");
		lblNewLabel.setBounds(38, 28, 120, 39);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Century", Font.PLAIN, 25));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		
		textField = new JTextField();
		textField.setBounds(168, 24, 83, 39);
		panel_1.add(textField);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Enter numbers only");
		lblNewLabel_1.setBounds(168, 61, 128, 23);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(Color.RED);
		
		JButton btnNewButton = new JButton("Find");
		btnNewButton.setBounds(345, 25, 96, 39);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("Century", Font.PLAIN, 24));
		btnNewButton.setFocusable(false);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnBack.setBounds(1104, 28, 118, 41);
		panel_1.add(btnBack);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					load(Integer.parseInt(textField.getText()));
					lblNewLabel_1.setVisible(false);
				}
				catch(NumberFormatException e1) {
					textField.setText("");
					lblNewLabel_1.setVisible(true);
				}
				
			}
		});
		lblNewLabel_1.setVisible(false);
		
		frame.setLocationRelativeTo(null);
	}
}
