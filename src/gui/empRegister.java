package gui;

import models.employee;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.ListSelectionModel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class empRegister {

	private JFrame frame;
	private JTextField fName;
	private JTextField lName;
	private JTextField contactNum_str;
	private JTextField email;
	private JTextField ageStr;
	DefaultTableModel df;
	private Connection con;
    PreparedStatement pst;
    private JTable table;
    public int dept_id;
    public int emp_id;

	/**
	 * Launch the application.
	 */
	public void load()
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select first_name, emp_id from employee";    
			pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
			ResultSetMetaData rd = (ResultSetMetaData)rs.getMetaData();
			df = (DefaultTableModel)table.getModel();
			df.setRowCount(0);
			while(rs.next()) {
				Vector v = new Vector();
				for(int i = 1; i <= 2; i++)
				{
					v.add(rs.getString("emp_id"));
					v.add(rs.getString("first_name"));
				}
				df.addRow(v);
			}
			pst.close();
            con.close();
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empRegister window = new empRegister();
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
	public empRegister() {
		initialize();
		load();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 947, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 136, 456, 341);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("First Name :");
		lblNewLabel_1.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(33, 32, 134, 46);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Last Name :");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_1.setBounds(33, 80, 134, 34);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Phone no :");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_2.setBounds(33, 111, 134, 40);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("E-Mail  :");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_3.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_3.setBounds(53, 156, 114, 33);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Age :");
		lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_4.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_4.setBounds(53, 199, 114, 34);
		panel.add(lblNewLabel_1_4);
		
		fName = new JTextField();
		fName.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		fName.setBounds(212, 48, 170, 19);
		panel.add(fName);
		fName.setColumns(10);
		
		lName = new JTextField();
		lName.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		lName.setColumns(10);
		lName.setBounds(212, 90, 170, 19);
		panel.add(lName);
		
		contactNum_str = new JTextField();
		contactNum_str.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		contactNum_str.setColumns(10);
		contactNum_str.setBounds(212, 124, 170, 19);
		panel.add(contactNum_str);
		
		email = new JTextField();
		email.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		email.setColumns(10);
		email.setBounds(212, 165, 170, 24);
		panel.add(email);
		
		ageStr = new JTextField();
		ageStr.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		ageStr.setColumns(10);
		ageStr.setBounds(212, 209, 170, 19);
		panel.add(ageStr);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Department :");
		lblNewLabel_1_4_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_4_1.setFont(new Font("Modern No. 20", Font.PLAIN, 23));
		lblNewLabel_1_4_1.setBounds(33, 243, 134, 34);
		panel.add(lblNewLabel_1_4_1);
		
		JComboBox deptSelect = new JComboBox();
		deptSelect.setModel(new DefaultComboBoxModel(new String[] {"Select Dept", "Cooking", "Maintenance", "Serving"}));
		deptSelect.setMaximumRowCount(4);
		deptSelect.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
		deptSelect.setBounds(212, 252, 170, 21);
		panel.add(deptSelect);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(20, 500, 903, 113);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1.setForeground(UIManager.getColor("MenuItem.selectionBackground"));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int dept = deptSelect.getSelectedIndex();
					employee obj = new employee(fName.getText(), lName.getText(), Integer.parseInt(ageStr.getText()), email.getText(), Integer.parseInt(contactNum_str.getText()), dept);
					obj.insert();
					
					fName.setText("");
					lName.setText(""); 
					ageStr.setText("");
					email.setText(""); 
					contactNum_str.setText("");
					deptSelect.setSelectedIndex(0);
					load();
					btnAdd.setEnabled(true);
					
				} catch(Exception o) {
					System.out.println(o);
				}
			}
		});
		btnAdd.setBounds(188, 31, 112, 48);
		btnAdd.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		panel_1.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					int dept = deptSelect.getSelectedIndex();
					employee obj = new employee(fName.getText(), lName.getText(), Integer.parseInt(ageStr.getText()), email.getText(), Integer.parseInt(contactNum_str.getText()), dept);
					obj.update(emp_id);
					
					fName.setText("");
					lName.setText(""); 
					ageStr.setText("");
					email.setText(""); 
					contactNum_str.setText("");
					deptSelect.setSelectedIndex(0);
					load();
					btnAdd.setEnabled(true);
					
				} catch(Exception o) {
					System.out.println(o);
				}
			}
		});
		btnUpdate.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnUpdate.setBounds(315, 31, 135, 48);
		panel_1.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employee e1 = new employee(emp_id);
				e1.delete(emp_id);
				System.out.print(emp_id);
				fName.setText("");
				lName.setText(""); 
				ageStr.setText("");
				email.setText(""); 
				contactNum_str.setText("");
				deptSelect.setSelectedIndex(0);
				load();
				btnAdd.setEnabled(true);
				
			}
		});
		btnDelete.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnDelete.setBounds(470, 31, 122, 48);
		panel_1.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fName.setText("");
				lName.setText(""); 
				ageStr.setText("");
				email.setText(""); 
				contactNum_str.setText("");
				deptSelect.setSelectedIndex(0);
				load();
				btnAdd.setEnabled(true);
				
			}
		});
		btnClear.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnClear.setBounds(612, 31, 122, 48);
		panel_1.add(btnClear);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(20, 10, 903, 116);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee Roster");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 44));
		lblNewLabel.setBounds(280, 26, 417, 64);
		panel_3.add(lblNewLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		btnBack.setBounds(775, 10, 118, 41);
		panel_3.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(501, 136, 422, 341);
		frame.getContentPane().add(scrollPane);
		//scrollPane.setToolTipText("");
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				df = (DefaultTableModel)table.getModel();
				int selected = table.getSelectedRow();
				emp_id = Integer.parseInt(df.getValueAt(selected, 0).toString());
				//System.out.println(id);
				employee e1 = new employee(emp_id);
				
				//System.out.println(e1.contact_number);
				fName.setText(e1.first_name);
				lName.setText(e1.last_name); 
				contactNum_str.setText(Long.toString(e1.contact_number));
				email.setText(e1.email); 
				ageStr.setText(Integer.toString(e1.age));
				deptSelect.setSelectedIndex(e1.dept_id);
				btnAdd.setEnabled(false);
			}
		});
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{" ", " "},
			},
			new String[] {
				"Employee ID", "First Name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(68);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.setFont(new Font("Modern No. 20", Font.PLAIN, 18));
//		scrollPane.add(table.getTableHeader(), BorderLayout.NORTH);
//		scrollPane.add(table, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
		//scrollPane.add(table);
				
		frame.setLocationRelativeTo(null);
	}
}
