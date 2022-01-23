package gui;

import models.product;
import models.order;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Checkbox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class orderWindow {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTextField taxField;
	private JTextField totalField;
	DefaultTableModel df, dfCart;
	private Connection con;
    PreparedStatement pst;
    private JComboBox comboBox_1;
    private product obj, cartRem;
    private order ord;
    private static int dept_id;
    private JButton btnRemove, btnPlaceOrder;

	/**
	 * Launch the application.
	 */
	public void supplierFill(String cat)
	{
		try {
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select supplier_id from supplier where category = ?";    
			pst = con.prepareStatement(query);
			pst.setString(1, cat);
            ResultSet rs = pst.executeQuery();
//			ResultSetMetaData rd = (ResultSetMetaData)rs.getMetaData();
//			df = (DefaultTableModel)table.getModel();
//			df.setRowCount(0);
			while(rs.next()) {
				comboBox_1.addItem(rs.getInt("supplier_id"));
			}
			pst.close();
            con.close();
			
		} catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public void tableLoad()
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select A.product_id, A.name, A.price from product as A, supplier_product as B where A.product_id = B.product_id and B.supplier_id = ?";    
			pst = con.prepareStatement(query);
			pst.setInt(1, ord.getSupplier_id());
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
					v.add(rs.getFloat("price"));
					v.add(0);
				}
				df.addRow(v);
			}
			
			dfCart = (DefaultTableModel)table_1.getModel();
			dfCart.setRowCount(0);
			
			if(ord != null)
			{
				float amount = 0;
				for(product j: ord.prodList) {
					
					if(j.qty <= 0)
						continue;
					Vector v = new Vector();
					v.add(j.id);
					v.add(j.name);
					v.add(j.qty);
					v.add((j.qty* j.price));
					dfCart.addRow(v);
					amount = amount + (j.qty * j.price);
				}
				ord.setAmount(amount);
				ord.setTax(amount * 0.18);
				ord.setTotal(amount * 1.18);
				taxField.setText(String.valueOf(amount * 0.18));
				totalField.setText(String.valueOf(amount * 1.18));
			}	
			pst.close();
            con.close();
			
		} catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public void addProduct()
	{
		ord.prodList.add(obj);
	}
	
	public void removeProduct()
	{
		int i = 0;
		for(product j: ord.prodList) {
			if(j.id == cartRem.id)
			{
				ord.prodList.remove(i);
				return;
			}
			i++;
		}
			
	}
	
	public static void main(String[] args, int dept_id) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					orderWindow window = new orderWindow(dept_id);
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
	public orderWindow(int dept_id) {
		this.dept_id = dept_id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 909, 635);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel.setBounds(10, 10, 875, 74);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Order");
		lblNewLabel.setBounds(389, 20, 100, 44);
		lblNewLabel.setFont(new Font("Modern No. 20", Font.BOLD, 40));
		panel.add(lblNewLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnBack.setBounds(747, 10, 118, 41);
		panel.add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1.setBounds(10, 89, 436, 499);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnAddToCart = new JButton("Add ");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProduct();
				tableLoad();
				btnAddToCart.setEnabled(false);
				//System.out.println(ord.prodList);
				btnPlaceOrder.setEnabled(true);
			}
		});
		btnAddToCart.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnAddToCart.setBounds(155, 428, 112, 48);
		btnAddToCart.setEnabled(false);
		panel_1.add(btnAddToCart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 131, 368, 287);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selected = table.getSelectedRow();
				if(Integer.parseInt(table.getValueAt(selected, 3).toString()) > 0) {
				obj = new product(Integer.parseInt(table.getValueAt(selected, 0).toString()), Integer.parseInt(table.getValueAt(selected, 3).toString()));
				btnAddToCart.setEnabled(true);
				}
			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null},
			},
			new String[] {
				"Product ID", "Product Name", "Rate", "Quantity"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JLabel lblSelectCategory = new JLabel("Select Category : ");
		lblSelectCategory.setBounds(21, 30, 147, 44);
		panel_1.add(lblSelectCategory);
		lblSelectCategory.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierFill(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Vegetables", "Fruits", "Cutlery", "Grocery"}));
		comboBox.setMaximumRowCount(4);
		comboBox.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		comboBox.setBounds(178, 42, 161, 21);
		panel_1.add(comboBox);
		
		JLabel lblSelectSupplier = new JLabel("Select Supplier : ");
		lblSelectSupplier.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		lblSelectSupplier.setBounds(21, 72, 147, 44);
		panel_1.add(lblSelectSupplier);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ord = new order();
				ord.setSupplier_id(Integer.parseInt(comboBox_1.getSelectedItem().toString()));
				ord.setDept_id(dept_id);
				tableLoad(); 
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Select"}));
		comboBox_1.setMaximumRowCount(30);
		comboBox_1.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		comboBox_1.setBounds(178, 85, 161, 21);
		panel_1.add(comboBox_1);
		
		
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1_1.setBounds(449, 89, 436, 499);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblCart = new JLabel("Cart");
		lblCart.setFont(new Font("Modern No. 20", Font.PLAIN, 30));
		lblCart.setBounds(184, 28, 72, 44);
		panel_1_1.add(lblCart);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 67, 391, 201);
		panel_1_1.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selected = table_1.getSelectedRow();
				cartRem = new product(Integer.parseInt(table_1.getValueAt(selected, 0).toString()), Integer.parseInt(table_1.getValueAt(selected, 2).toString()));
				btnRemove.setEnabled(true);
			}
		});
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Product ID", "Product Name", "Quantity", "Amount"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeProduct();
				//addProduct();
				tableLoad();
				btnRemove.setEnabled(false);
			}
		});
		btnRemove.setFont(new Font("Modern No. 20", Font.BOLD, 18));
		btnRemove.setBounds(23, 278, 105, 41);
		panel_1_1.add(btnRemove);
		btnRemove.setEnabled(false);
		
		btnPlaceOrder = new JButton("Place Order");
		btnPlaceOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mode change;
				ord.insert();
				taxField.setText("");
				totalField.setText("");
				ord.prodList.removeAll(ord.prodList);
				btnPlaceOrder.setEnabled(false);
				ord.setAmount(0);
				ord.setTax(0);
				ord.setTotal(0);
				tableLoad();
			}
		});
		btnPlaceOrder.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnPlaceOrder.setBounds(107, 389, 215, 48);
		panel_1_1.add(btnPlaceOrder);
		
		JLabel lblTotalAmount = new JLabel("Total Amount : Rs.");
		lblTotalAmount.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		lblTotalAmount.setBounds(149, 322, 166, 34);
		panel_1_1.add(lblTotalAmount);
		
		JLabel lblTotalTax = new JLabel("Total Tax : Rs. ");
		lblTotalTax.setFont(new Font("Modern No. 20", Font.PLAIN, 20));
		lblTotalTax.setBounds(184, 282, 131, 34);
		panel_1_1.add(lblTotalTax);
		
		taxField = new JTextField();
		taxField.setBackground(new Color(255, 255, 255));
		taxField.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		taxField.setEditable(false);
		taxField.setColumns(10);
		taxField.setBounds(309, 289, 98, 19);
		panel_1_1.add(taxField);
		
		totalField = new JTextField();
		totalField.setBackground(Color.WHITE);
		totalField.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		totalField.setEditable(false);
		totalField.setColumns(10);
		totalField.setBounds(309, 331, 98, 19);
		panel_1_1.add(totalField);
		
		frame.setLocationRelativeTo(null);
	}
}
