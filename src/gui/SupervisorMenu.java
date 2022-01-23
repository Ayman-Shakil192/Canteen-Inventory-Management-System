package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class SupervisorMenu {

	private JFrame frame;
	private JLabel title;
	private JPanel panel_1;
	private JButton orderProducts;
	private int dept_id;

	/**
	 * Launch the application.
	 */
	public void call() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupervisorMenu window = new SupervisorMenu(dept_id);
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
	public SupervisorMenu(int dept_id) {
		this.dept_id = dept_id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Menu");
		frame.setBounds(100, 100, 728, 743);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel.setBounds(10, 10, 694, 137);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon logo1 = new ImageIcon(EmployeeMenu.class.getResource("/Photo/logo4.png"));
		Image resize = logo1.getImage();
		logo.setIcon(new ImageIcon(resize.getScaledInstance(151, 74, java.awt.Image.SCALE_SMOOTH)));
		logo.setBounds(271, 10, 151, 74);
		panel.add(logo);
		
		title = new JLabel("Canteen Inventory Management");
		title.setFont(new Font("Modern No. 20", Font.BOLD, 32));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setBounds(102, 94, 525, 46);
		panel.add(title);
	
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1.setBounds(149, 157, 416, 512);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton employee = new JButton("Roster               ");
		employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empRegister.main(null);
			}
		});
		ImageIcon logo2 = new ImageIcon(EmployeeMenu.class.getResource("/Photo/logo3.png"));
		resize = logo2.getImage();
		employee.setIcon(new ImageIcon(resize.getScaledInstance(46, 44, java.awt.Image.SCALE_SMOOTH)));
		employee.setVerticalAlignment(SwingConstants.TOP);
		employee.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		employee.setBounds(61, 46, 294, 44);
		employee.setFocusable(false);
		panel_1.add(employee);
		
		JButton viewStocks = new JButton("View Stocks     ");
		viewStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productView.main(null);
			}
		});
		viewStocks.setIcon(new ImageIcon(resize.getScaledInstance(46, 44, java.awt.Image.SCALE_SMOOTH)));
		viewStocks.setVerticalAlignment(SwingConstants.TOP);
		viewStocks.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		viewStocks.setBounds(61, 113, 294, 44);
		viewStocks.setFocusable(false);
		panel_1.add(viewStocks);
		
		JButton updateStocks = new JButton("Update Stocks  ");
		updateStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateInventory.main(null);
			}
		});
		updateStocks.setIcon(new ImageIcon(resize.getScaledInstance(46, 44, java.awt.Image.SCALE_SMOOTH)));
		updateStocks.setVerticalAlignment(SwingConstants.TOP);
		updateStocks.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		updateStocks.setBounds(61, 180, 294, 44);
		updateStocks.setFocusable(false);
		panel_1.add(updateStocks);
		
		orderProducts = new JButton("Order products");
		orderProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderWindow.main(null, dept_id);
			}
		});
		orderProducts.setVerticalAlignment(SwingConstants.TOP);
		orderProducts.setIcon(new ImageIcon(resize.getScaledInstance(46, 44, java.awt.Image.SCALE_SMOOTH)));
		orderProducts.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		orderProducts.setBounds(61, 247, 294, 44);
		orderProducts.setFocusable(false);
		panel_1.add(orderProducts);
		
		JButton orderDetails = new JButton("Order Details   ");
		orderDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderDetail.main(null);
			}
		});
		orderDetails.setIcon(new ImageIcon(resize.getScaledInstance(46, 44, java.awt.Image.SCALE_SMOOTH)));
		orderDetails.setVerticalAlignment(SwingConstants.TOP);
		orderDetails.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		orderDetails.setFocusable(false);
		orderDetails.setBounds(61, 314, 294, 44);
		panel_1.add(orderDetails);
		
		JButton Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		Logout.setVerticalAlignment(SwingConstants.TOP);
		Logout.setFont(new Font("Palatino Linotype", Font.PLAIN, 29));
		Logout.setBounds(139, 415, 134, 44);
		Logout.setFocusable(false);
		panel_1.add(Logout);
		frame.setLocationRelativeTo(null);
	}
}
