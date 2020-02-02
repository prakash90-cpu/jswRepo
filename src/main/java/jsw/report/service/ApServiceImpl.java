package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ApDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class ApServiceImpl implements ApService {
	
	
	private ApDao apDao;
	private SimpleDateFormatte date=new  SimpleDateFormatte();

	public ApDao getApDao() {
		return apDao;
	}

	public void setApDao(ApDao apDao) {
		this.apDao = apDao;
	}
	
	
	
	
	
	public SimpleDateFormatte getDate() {
		return date;
	}

	public void setDate(SimpleDateFormatte date) {
		this.date = date;
	}

	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return date.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	
	public List getNumberOfCasesAPById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return apDao.getNumberOfCasesAPById(id,bucket,functionString);
	}
	
	
	

	   public List getNumberOfCasesCreatedAP(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
   			// TODO Auto-generated method stub
   			return apDao.getNumberOfCasesCreatedAP(functionAppBean);
   		}
   	
   	 
   	    public String getNumberOfCasesCreatedAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getNumberOfCasesCreatedAP(columnValue);
   		}
   	 public void getNumberOfCasesCreatedExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			apDao.getNumberOfCasesCreatedExcelAP(columnValue);
   		}

   	  public List getCasesCreatedCompletedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCasesCreatedCompletedAP(functionAppBean);
   		}
   	    public String getCaseCreatedCompanyCompletedAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCaseCreatedCompanyCompletedAP(columnValue);
   		}
   		
   	 public void getCaseCreatedCompanyCompletedExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			 apDao.getCaseCreatedCompanyCompletedExcelAP(columnValue);
   		}
   	    
   	    public List getRoleWiseTimeTakenAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getRoleWiseTimeTakenAP(functionAppBean);
   		}
   	    public String getRoleWiseTimeTakenAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getRoleWiseTimeTakenAP(columnValue);
   		}
   	    
   	 public void getRoleWiseTimeTakenExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			 apDao.getRoleWiseTimeTakenExcelAP(columnValue);
   		}
   		
   	    public List getTotalCycleTimeAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getTotalCycleTimeAP(functionAppBean);
   		}
   	    public String getTotalCycleTimeAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getTotalCycleTimeAP(columnValue);
   		}
   	    
   	 public void getTotalCycleTimeExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			apDao.getTotalCycleTimeExcelAP(columnValue);
   		}
   	
   	    public List getCasesProcessedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCasesProcessedAP(functionAppBean);
   		}
   	    public String getCasesProcessedAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCasesProcessedAP(columnValue);
   		}
   	 public void getCasesProcessedExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			 apDao.getCasesProcessedExcelAP(columnValue);
   		}
   	  
   	    public List getCasesPendingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCasesPendingAP(functionAppBean);
   		}
   	    public String getCasesPendingAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   			return apDao.getCasesPendingAP(columnValue);
   		}
   	 public void getCasesPendingExcelAP(String columnValue) throws SQLException, JSONException{
   			// TODO Auto-generated method stub
   		 apDao.getCasesPendingExcelAP(columnValue);
   		}
   	   public List getCasesPendingForProcesssingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getCasesPendingForProcesssingAP(functionAppBean);
		}
	    public String getCasesPendingForProcesssingAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getCasesPendingForProcesssingAP(columnValue);
		}
		
	    public void getCasesPendingForProcesssingExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getCasesPendingForProcesssingExcelAP(columnValue);
		}
	    
	    public List getCasesCompletedCumulativelyAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 return apDao.getCasesCompletedCumulativelyAP(functionAppBean);
		}
	    public String getCasesCompletedCumulativelyAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getCasesCompletedCumulativelyAP(columnValue);
		}
	
	    public void getCasesCompletedCumulativelyExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getCasesCompletedCumulativelyExcelAP(columnValue);
		}

	    public List getExceptionAndFirstPassAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getExceptionAndFirstPassAP(functionAppBean);
		}
	    public String getExceptionAndFirstPassAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getExceptionAndFirstPassAP(columnValue);
		}
	    public void getExceptionAndFirstPassExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getExceptionAndFirstPassExcelAP(columnValue);
		}
		
	    public List getUrgentInvoiceRecievedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getUrgentInvoiceRecievedAP(functionAppBean);
		}
	    public String getUrgentInvoiceRecievedAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getUrgentInvoiceRecievedAP(columnValue);
		}
	    public void getUrgentInvoiceRecievedExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getUrgentInvoiceRecievedExcelAP(columnValue);
		}

	    
	    public List getAverageTimeTakenFromReceiptToPaymentAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getAverageTimeTakenFromReceiptToPaymentAP(functionAppBean);
		}
	    public String getAverageTimeTakenFromReceiptToPaymentAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getAverageTimeTakenFromReceiptToPaymentAP(columnValue);
		}
	    public void getAverageTimeTakenFromReceiptToPaymentExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getAverageTimeTakenFromReceiptToPaymentExcelAP(columnValue);
		}
	    
	    
	    public List getTATforScanningAndindexingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getTATforScanningAndindexingAP(functionAppBean);
		}
	    public String getTATforScanningAndindexingAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apDao.getTATforScanningAndindexingAP(columnValue);
		}
	    public void getTATforScanningAndindexingExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 apDao.getTATforScanningAndindexingExcelAP(columnValue);
		}
	    
		
}
