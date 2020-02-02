package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface CommDao  {
	
	
	public List getNumberOfCasesCOMMById(int id, int bucket) throws SQLException;
	
	//public List getAllPeriodDisplay(String selectedDate, String periodFormat) throws SQLException;

	// COMM Report DAO
	 public List getNumberOfCasesCreatedCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getNumberOfCasesCreatedCOMM(String columnValue) throws SQLException, JSONException;
	    public void getNumberOfCasesCreatedExcelCOMM(String columnValue) throws SQLException, JSONException;
	    
	    public List getCasesCreatedCompletedCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCaseCreatedCompanyCompletedCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCaseCreatedCompanyCompletedExcelCOMM(String columnValue) throws SQLException, JSONException;
		
	    public List getCasesProcessedCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesProcessedCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesProcessedExcelCOMM(String columnValue) throws SQLException, JSONException;
		  
	    
	    
	    public List getRoleWiseTimeTakenCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getRoleWiseTimeTakenCOMM(String columnValue) throws SQLException, JSONException;
	    public void getRoleWiseTimeTakenExcelCOMM(String columnValue) throws SQLException, JSONException;
		
	    
	    
	    public List getTotalCycleTimeCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getTotalCycleTimeCOMM(String columnValue) throws SQLException, JSONException;
	    public void getTotalCycleTimeExcelCOMM(String columnValue) throws SQLException, JSONException;
		
	  
	    public List getCasesPendingCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingExcelCOMM(String columnValue) throws SQLException, JSONException;
		   
	    
	    
	    public List getCasesPendingForProcesssingCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingForProcesssingCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingForProcesssingExcelCOMM(String columnValue) throws SQLException, JSONException;
		
	    
	    public List getCasesCompletedCumulativelyCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesCompletedCumulativelyCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesCompletedCumulativelyExcelCOMM(String columnValue) throws SQLException, JSONException;

	    public List getCasesPendingWithPRTOPOCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException;
		
	    public List getCasesPendingForProcesssingWithPRTOPOCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingForProcesssingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingForProcesssingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException;
		

}
