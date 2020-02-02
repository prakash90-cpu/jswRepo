package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.viewBean.FunctionAppBean;

public interface ApDao {
	
	//AP	
	
	public List getNumberOfCasesAPById(int id,int bucket, String functionName) throws SQLException;
	//public List getAllPeriodDisplay(String selectedDate, String periodFormat) throws SQLException;
	
	//public List getNumberOfCases() throws SQLException;

    public List getNumberOfCasesCreatedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getNumberOfCasesCreatedAP(String columnValue) throws SQLException, JSONException;
    public void getNumberOfCasesCreatedExcelAP(String columnValue) throws SQLException, JSONException;

    
    public List getCasesCreatedCompletedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCaseCreatedCompanyCompletedAP(String columnValue) throws SQLException, JSONException;
    public void getCaseCreatedCompanyCompletedExcelAP(String columnValue) throws SQLException, JSONException;
	
    
    
    public List getCasesProcessedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesProcessedAP(String columnValue) throws SQLException, JSONException;
    public void getCasesProcessedExcelAP(String columnValue) throws SQLException, JSONException;
	  
    
    
    public List getRoleWiseTimeTakenAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getRoleWiseTimeTakenAP(String columnValue) throws SQLException, JSONException;
    public void getRoleWiseTimeTakenExcelAP(String columnValue) throws SQLException, JSONException;
	
    
    
    public List getTotalCycleTimeAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getTotalCycleTimeAP(String columnValue) throws SQLException, JSONException;
    public void getTotalCycleTimeExcelAP(String columnValue) throws SQLException, JSONException;
	
  
    public List getCasesPendingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesPendingAP(String columnValue) throws SQLException, JSONException;
    public void getCasesPendingExcelAP(String columnValue) throws SQLException, JSONException;
	   
     
    public List getCasesPendingForProcesssingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesPendingForProcesssingAP(String columnValue) throws SQLException, JSONException;
    public void getCasesPendingForProcesssingExcelAP(String columnValue) throws SQLException, JSONException;
				     
    public List getCasesCompletedCumulativelyAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getCasesCompletedCumulativelyAP(String columnValue) throws SQLException, JSONException;
    public void getCasesCompletedCumulativelyExcelAP(String columnValue) throws SQLException, JSONException;

    
    public List getExceptionAndFirstPassAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getExceptionAndFirstPassAP(String columnValue) throws SQLException, JSONException;
    public void getExceptionAndFirstPassExcelAP(String columnValue) throws SQLException, JSONException;
	
    public List getUrgentInvoiceRecievedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getUrgentInvoiceRecievedAP(String columnValue) throws SQLException, JSONException;
    public void getUrgentInvoiceRecievedExcelAP(String columnValue) throws SQLException, JSONException;

    public List getAverageTimeTakenFromReceiptToPaymentAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getAverageTimeTakenFromReceiptToPaymentAP(String columnValue) throws SQLException, JSONException;
    public void getAverageTimeTakenFromReceiptToPaymentExcelAP(String columnValue) throws SQLException, JSONException;

    public List getTATforScanningAndindexingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException;
    public String getTATforScanningAndindexingAP(String columnValue) throws SQLException, JSONException;
    public void getTATforScanningAndindexingExcelAP(String columnValue) throws SQLException, JSONException;

    
}
