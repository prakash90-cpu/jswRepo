package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface ARService {
	public List getAllPeriodDisplay(String selectedDate, String periodFormat) throws SQLException;
	
	public List getNumberOfCasesARById(int id,int bucket, String functionName) throws SQLException;
	public List getNumberOfCasesCreatedAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getNumberOfCasesCreatedAR(String columnValue) throws SQLException, JSONException;
    public void getNumberOfCasesCreatedExcelAR(String columnValue) throws SQLException, JSONException;

    
    public List getCasesCreatedCompletedAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCaseCreatedCompanyCompletedAR(String columnValue) throws SQLException, JSONException;
    public void getCaseCreatedCompanyCompletedExcelAR(String columnValue) throws SQLException, JSONException;
	
    
    
    public List getCasesProcessedAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesProcessedAR(String columnValue) throws SQLException, JSONException;
    public void getCasesProcessedExcelAR(String columnValue) throws SQLException, JSONException;
	  
    
    
    public List getRoleWiseTimeTakenAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getRoleWiseTimeTakenAR(String columnValue) throws SQLException, JSONException;
    public void getRoleWiseTimeTakenExcelAR(String columnValue) throws SQLException, JSONException;
	
    
    
    public List getTotalCycleTimeAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getTotalCycleTimeAR(String columnValue) throws SQLException, JSONException;
    public void getTotalCycleTimeExcelAR(String columnValue) throws SQLException, JSONException;
	
  
    public List getCasesPendingAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesPendingAR(String columnValue) throws SQLException, JSONException;
    public void getCasesPendingExcelAR(String columnValue) throws SQLException, JSONException;
	   
     
    public List getCasesPendingForProcesssingAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesPendingForProcesssingAR(String columnValue) throws SQLException, JSONException;
    public void getCasesPendingForProcesssingExcelAR(String columnValue) throws SQLException, JSONException;
				     
    public List getCasesCompletedCumulativelyAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesCompletedCumulativelyAR(String columnValue) throws SQLException, JSONException;
    public void getCasesCompletedCumulativelyExcelAR(String columnValue) throws SQLException, JSONException;
	
}
