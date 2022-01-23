package models;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProductExcel {
	
	 public void export() {
	        String jdbcURL = "jdbc:mysql://localhost:3306/canteen";
	        String username = "root";
	        String password = "root";
	        Date date = new Date() ;
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
	        
	        String excelFilePath = "E:\\Eclipse\\canteen-inventory-system\\Reports\\Report on "+dateFormat.format(date)+".xlsx";
	 
	        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
	            String sql = "SELECT * FROM product";
	 
	            Statement statement = connection.createStatement();
	 
	            ResultSet result = statement.executeQuery(sql);
	 
	            XSSFWorkbook workbook = new XSSFWorkbook();
	            XSSFSheet sheet = workbook.createSheet("Product");
	 
	            writeHeaderLine(sheet);
	 
	            writeDataLines(result, workbook, sheet);
	 
	            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	            workbook.write(outputStream);
	            workbook.close();
	 
	            statement.close();
	 
	        } catch (SQLException e) {
	            System.out.println("Datababse error:");
	            e.printStackTrace();
	        } catch (IOException e) {
	            System.out.println("File IO error:");
	            e.printStackTrace();
	        }
	    }
	 
	    private void writeHeaderLine(XSSFSheet sheet) {
	    	Row header = sheet.createRow(0);
	    	Cell headerTitle = header.createCell(3);
	    	
	    	headerTitle.setCellValue("Canteen Inventory");
	 
	        Row headerRow = sheet.createRow(1);
	 
	        Cell headerCell = headerRow.createCell(0);
	        headerCell.setCellValue("Product ID");
	 
	        headerCell = headerRow.createCell(1);
	        headerCell.setCellValue("Product Name");
	 
	        headerCell = headerRow.createCell(2);
	        headerCell.setCellValue("Available Quantity");
	        
	        headerCell = headerRow.createCell(3);
	        headerCell.setCellValue("Price");
	        
	        headerCell = headerRow.createCell(4);
	        headerCell.setCellValue("Fresh Date");
	        
	        headerCell = headerRow.createCell(5);
	        headerCell.setCellValue("Category");
	 
	    }
	 
	    private void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
	        int rowCount = 2;
	 
	        while (result.next()) {
	        	int productId = result.getInt("product_id");
	        	String name = result.getString("name");
	        	int available = result.getInt("available_qty");
	        	float price = result.getFloat("price");
	        	Timestamp timestamp = result.getTimestamp("fresh_date");
	        	String cat = result.getString("category");
	        	
	        	Row row = sheet.createRow(rowCount++);
	        	
	        	int columnCount = 0;
	        	
	        	Cell cell = row.createCell(columnCount++);
	        	cell.setCellValue(productId);
	        	
	        	cell = row.createCell(columnCount++);
	        	cell.setCellValue(name);
	        	
	        	cell = row.createCell(columnCount++);
	        	cell.setCellValue(available);
	        	
	        	cell = row.createCell(columnCount++);
	        	cell.setCellValue(price);
	        	
	        	
	        	cell = row.createCell(columnCount++);
	       	    CellStyle cellStyle = workbook.createCellStyle();
	            CreationHelper creationHelper = workbook.getCreationHelper();
	            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
	            cell.setCellStyle(cellStyle);
	            cell.setCellValue(timestamp);
	            

	        	cell = row.createCell(columnCount++);
	        	cell.setCellValue(cat);
	        	
	            /*String countryName = result.getString("name");
	            int countryCode = result.getInt("code");
	            int customerID = result.getInt("customerid");
	            
	 
	            Row row = sheet.createRow(rowCount++);
	 
	            int columnCount = 0;
	            Cell cell = row.createCell(columnCount++);
	            cell.setCellValue(countryName);
	 
	            cell = row.createCell(columnCount++);
	            cell.setCellValue(countryCode);
	 
	            /*cell = row.createCell(columnCount++);
	 
	            CellStyle cellStyle = workbook.createCellStyle();
	            CreationHelper creationHelper = workbook.getCreationHelper();
	            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
	            cell.setCellStyle(cellStyle);
	 
	            cell.setCellValue(timestamp);*/
	 
//	            cell = row.createCell(columnCount++);
//	            cell.setCellValue(customerID);
	 
	            /*cell = row.createCell(columnCount);
	            cell.setCellValue(comment);*/
	 
	        }
	    }
}
