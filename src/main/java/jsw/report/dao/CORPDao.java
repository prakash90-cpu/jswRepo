package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface CORPDao {

	
	public List getNumberOfCasesById(int id,int bucket, String functionName) throws SQLException;

	 public List getNumberOfCasesCreatedCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getNumberOfCasesCreatedCORP(String columnValue) throws SQLException, JSONException;

	    
	    public List getCasesCreatedCompletedCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCaseCreatedCompanyCompletedCORP(String columnValue) throws SQLException, JSONException;
		
	    public List getCasesProcessedCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesProcessedCORP(String columnValue) throws SQLException, JSONException;
	  
	    public List getRoleWiseTimeTakenCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getRoleWiseTimeTakenCORP(String columnValue) throws SQLException, JSONException;
		
	    public List getTotalCycleTimeCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getTotalCycleTimeCORP(String columnValue) throws SQLException, JSONException;
		
	  
	    public List getCasesPendingCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingCORP(String columnValue) throws SQLException, JSONException;  
	
}
