package jsw.report.excelUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcellGenerator {
	DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public void generateExcel(String query){
		Connection conn=null;
		PreparedStatement pstmt = null;
		final String FILE_NAME = "caseDetailExcelSheet.xlsx";

	    
		try {
			conn = dataSource.getConnection();
		 pstmt = conn.prepareStatement(query);

		 XSSFWorkbook workbook = new XSSFWorkbook();
	     XSSFSheet sheet = workbook.createSheet("Case details");

	     int colNum = 0;
	 
	      	 
			ResultSet resultSet = pstmt.executeQuery();

			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			    Row row = sheet.createRow(0);
			    CellStyle style = workbook.createCellStyle();
			   // style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			    style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			    XSSFFont fontBold = workbook.createFont();
			    fontBold.setFontHeightInPoints((short)11);
			    fontBold.setFontName("Times New Roman");
			    fontBold.setBold(true);
			    style.setFont(fontBold);
			    
			for (int colCount = 1; colCount <= columnCount; colCount++) {
				Cell cell = row.createCell(colCount-1);
				cell.setCellStyle(style);
				cell.setCellValue((String) rsmd.getColumnName(colCount));
			
			}
				
			 int countTd=1;
			while (resultSet.next()) {
				   row = sheet.createRow(countTd);
					for (int colCount = 1; colCount <= columnCount; colCount++) {
						Cell cell = row.createCell(colCount - 1);
						cell.setCellValue((String) resultSet.getString(colCount));
					}
					countTd++;
			}
			
			 try {
		            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
		            workbook.write(outputStream);
		            workbook.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

			
		pstmt.close();
		conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pstmt=null;
			conn=null;	
		}
		
		
		
	}
	
	
	public String getTableStringFromQuery(String query) throws SQLException{
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pstmt = conn.prepareStatement(query);

		ResultSet resultSet = pstmt.executeQuery();

		String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";
		
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnCount = rsmd.getColumnCount();
		 tableString+="<thead> <tr style='background-color:blue;color:white'> ";
		 
		
		 
		// The columnCount starts from 1
		for (int colCount = 1; colCount <= columnCount; colCount++) {
			
					 tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
			
		}
		 tableString+="</tr></thead><tbody>";
		

		while (resultSet.next()) {
			
			tableString+="<tr>";
			
			for (int colCount = 1; colCount <= columnCount; colCount++) {
				//innerJson.put(resultSet.getString(colCount));
				  tableString+="<td>"+resultSet.getString(colCount)+"</td>";
				
				// Do stuff with name
			}
			  tableString+="</tr>";
			

		}
		tableString+="</tbody></table>";
		
		//System.out.println("JsonData" + list);
		pstmt.close();
		conn.close();
		return tableString;
		
		
		
	}
	

}
