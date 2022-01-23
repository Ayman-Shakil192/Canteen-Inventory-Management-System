package gui;

import models.product;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class updateInventory {

	private JFrame frame;
	private JTable table;
	private JButton btnUpdate;
	DefaultTableModel df;
	private Connection con;
    PreparedStatement pst;

	/**
	 * Launch the application.
	 */
	
	public void load(String cat)
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select * from product where category = ?";    
			pst = con.prepareStatement(query);
			pst.setString(1, cat);
            ResultSet rs = pst.executeQuery();
			ResultSetMetaData rd = (ResultSetMetaData)rs.getMetaData();
			df = (DefaultTableModel)table.getModel();
			df.setRowCount(0);
			while(rs.next()) {
				Vector v = new Vector();
				for(int i = 1; i <= 2; i++)
				{
					v.add(rs.getString("product_id"));
					v.add(rs.getString("name"));
					v.add(rs.getDate("fresh_date"));
					v.add(rs.getInt("available_qty"));
					v.add(0);
				}
				df.addRow(v);
			}
			pst.close();
            con.close();
			
		} catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public void add(String cat)
	{
		for(int i = 0; i < table.getRowCount(); i++) {
			int c = Integer.parseInt(table.getValueAt(i, 4).toString());
			if(Integer.parseInt(table.getValueAt(i, 3).toString()) >= c)
			{
				product p = new product(Integer.parseInt(table.getValueAt(i, 0).toString()));
				p.update(c);
			}
		}
		load(cat);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateInventory window = new updateInventory();
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
	public updateInventory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 934, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_3.setBounds(10, 10, 903, 116);
		frame.getContentPane().add(panel_3);
		
		JLabel lblInventory = new JLabel("Update Inventory");
		lblInventory.setHorizontalAlignment(SwingConstants.CENTER);
		lblInventory.setFont(new Font("Leelawadee UI", Font.BOLD, 44));
		lblInventory.setBackground(Color.LIGHT_GRAY);
		lblInventory.setBounds(253, 24, 417, 64);
		panel_3.add(lblInventory);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel.setBounds(13, 136, 900, 517);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Category :");
		lblNewLabel_1_4_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_4_1.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_4_1.setBounds(67, 22, 134, 34);
		panel.add(lblNewLabel_1_4_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Vegetables", "Fruits", "Cutlery", "Grocery"}));
		comboBox.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		comboBox.setBounds(211, 31, 155, 25);
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 66, 761, 375);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Modern No. 20", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Product ID", "Product Name", "Fresh Date", "Availability", "Consumption"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(comboBox.getSelectedItem().toString());
			}
		});
		btnUpdate.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnUpdate.setBounds(393, 451, 155, 34);
		panel.add(btnUpdate);
		btnUpdate.setEnabled(true);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnBack.setBounds(719, 17, 141, 41);
		panel.add(btnBack);
		
		frame.setLocationRelativeTo(null);
		
	}
}
