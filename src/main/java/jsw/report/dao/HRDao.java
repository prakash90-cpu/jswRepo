package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface HRDao {
	

	  public List getNumberOfCasesHRById(int id,int bucket, String functionName) throws SQLException;
		
	    

      public List getNumberOfCasesCreatedHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getNumberOfCasesCreatedHR(String columnValue) throws SQLException, JSONException;
	    public void getNumberOfCasesCreatedExcelHR(String columnValue) throws SQLException, JSONException;

	    
	    public List getCasesCreatedCompletedHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCaseCreatedCompanyCompletedHR(String columnValue) throws SQLException, JSONException;
	    public void getCaseCreatedCompanyCompletedExcelHR(String columnValue) throws SQLException, JSONException;
		
	    
	    
	    public List getCasesProcessedHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesProcessedHR(String columnValue) throws SQLException, JSONException;
	    public void getCasesProcessedExcelHR(String columnValue) throws SQLException, JSONException;
		  
	    
	    
	    public List getRoleWiseTimeTakenHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getRoleWiseTimeTakenHR(String columnValue) throws SQLException, JSONException;
	    public void getRoleWiseTimeTakenExcelHR(String columnValue) throws SQLException, JSONException;
		
	    
	    
	    public List getTotalCycleTimeHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getTotalCycleTimeHR(String columnValue) throws SQLException, JSONException;
	    public void getTotalCycleTimeExcelHR(String columnValue) throws SQLException, JSONException;
		
	  
	    public List getCasesPendingHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
	    public String getCasesPendingHR(String columnValue) throws SQLException, JSONException;
	    public void getCasesPendingExcelHR(String columnValue) throws SQLException, JSONException;


}
