package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface RTRDao {
	
	
	
	public List getNumberOfCasesRTRById(int id,int bucket, String functionName) throws SQLException;

	
	 public List getNumberOfCasesCreatedRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getNumberOfCasesCreatedRTR(String columnValue) throws SQLException, JSONException;
	    public void getNumberOfCasesCreatedExcelRTR(String columnValue) throws SQLException, JSONException;

	    
	    public List getCasesCreatedCompletedRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCaseCreatedCompanyCompletedRTR(String columnValue) throws SQLException, JSONException;
	    public void getCaseCreatedCompanyCompletedExcelRTR(String columnValue) throws SQLException, JSONException;
		
	    
	    
	    public List getCasesProcessedRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesProcessedRTR(String columnValue) throws SQLException, JSONException;
	    public void getCasesProcessedExcelRTR(String columnValue) throws SQLException, JSONException;
		  
	    
	    
	    public List getRoleWiseTimeTakenRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getRoleWiseTimeTakenRTR(String columnValue) throws SQLException, JSONException;
	    public void getRoleWiseTimeTakenExcelRTR(String columnValue) throws SQLException, JSONException;
		
	    
	    
	    public List getTotalCycleTimeRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getTotalCycleTimeRTR(String columnValue) throws SQLException, JSONException;
	    public void getTotalCycleTimeExcelRTR(String columnValue) throws SQLException, JSONException;
		
	  
	    public List getCasesPendingRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingRTR(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingExcelRTR(String columnValue) throws SQLException, JSONException;   

}
