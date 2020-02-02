package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface TaxService {
	
	

	public List getAllPeriodDisplay(String selectedDate, String periodFormat) throws SQLException;


	public List getNumberOfCasesTaxById(int id,int bucket, String functionName) throws SQLException;

		    
            public List getNumberOfCasesCreatedTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getNumberOfCasesCreatedTax(String columnValue) throws SQLException, JSONException;
		    public void getNumberOfCasesCreatedExcelTax(String columnValue) throws SQLException, JSONException;

		    
		    public List getCasesCreatedCompletedTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getCaseCreatedCompanyCompletedTax(String columnValue) throws SQLException, JSONException;
		    public void getCaseCreatedCompanyCompletedExcelTax(String columnValue) throws SQLException, JSONException;
			
		    
		    
		    public List getCasesProcessedTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getCasesProcessedTax(String columnValue) throws SQLException, JSONException;
		    public void getCasesProcessedExcelTax(String columnValue) throws SQLException, JSONException;
			  
		    
		    
		    public List getRoleWiseTimeTakenTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getRoleWiseTimeTakenTax(String columnValue) throws SQLException, JSONException;
		    public void getRoleWiseTimeTakenExcelTax(String columnValue) throws SQLException, JSONException;
			
		    
		    
		    public List getTotalCycleTimeTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getTotalCycleTimeTax(String columnValue) throws SQLException, JSONException;
		    public void getTotalCycleTimeExcelTax(String columnValue) throws SQLException, JSONException;
			
		  
		    public List getCasesPendingTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
		    public String getCasesPendingTax(String columnValue) throws SQLException, JSONException;
		    public void getCasesPendingExcelTax(String columnValue) throws SQLException, JSONException;
			   
		
}
