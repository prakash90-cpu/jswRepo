package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.ApService;

import jsw.report.viewBean.FunctionAppBean;

public class ApDelegate {
	private ApService apService;
	

	
	public ApService getApService() {
		return apService;
	}

	public void setApService(ApService apService) {
		this.apService = apService;
	}

	
	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return apService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	
	
	public List getNumberOfCasesAPById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return apService.getNumberOfCasesAPById(id,bucket,functionString);
	} 
	
	
	
	
	public List getNumberOfCasesCreatedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getNumberOfCasesCreatedAP(functionAppBean);
	}

	public String getNumberOfCasesCreatedAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getNumberOfCasesCreatedAP(columnValue);
	}

	public void getNumberOfCasesCreatedExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 apService.getNumberOfCasesCreatedExcelAP(columnValue);
	}								
	
	public List getCasesCreatedCompletedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCasesCreatedCompletedAP(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCaseCreatedCompanyCompletedAP(columnValue);
	}
	public void getCaseCreatedCompanyCompletedExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 apService.getCaseCreatedCompanyCompletedExcelAP(columnValue);
	}

	public List getRoleWiseTimeTakenAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getRoleWiseTimeTakenAP(functionAppBean);
	}

	public String getRoleWiseTimeTakenAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getRoleWiseTimeTakenAP(columnValue);
	}
	
	public void getRoleWiseTimeTakenExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		apService.getRoleWiseTimeTakenExcelAP(columnValue);
	}

	public List getTotalCycleTimeAP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getTotalCycleTimeAP(functionAppBean);
	}

	public String getTotalCycleTimeAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getTotalCycleTimeAP(columnValue);
	}
	public void getTotalCycleTimeExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 apService.getTotalCycleTimeExcelAP(columnValue);
	}
	

	public List getCasesProcessedAP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCasesProcessedAP(functionAppBean);
	}

	public String getCasesProcessedAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCasesProcessedAP(columnValue);
	}
	public void getCasesProcessedExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		apService.getCasesProcessedExcelAP(columnValue);
	}

	public List getCasesPendingAP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCasesPendingAP(functionAppBean);
	}

	public String getCasesPendingAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return apService.getCasesPendingAP(columnValue);
	}

	public void getCasesPendingExcelAP(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 apService.getCasesPendingExcelAP(columnValue);
	}
	   public List getCasesPendingForProcesssingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getCasesPendingForProcesssingAP(functionAppBean);
		}
	    public String getCasesPendingForProcesssingAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getCasesPendingForProcesssingAP(columnValue);
		}
		
	    public void getCasesPendingForProcesssingExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			apService.getCasesPendingForProcesssingExcelAP(columnValue);
		}
	    
	    public List getCasesCompletedCumulativelyAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getCasesCompletedCumulativelyAP(functionAppBean);
		}
	    public String getCasesCompletedCumulativelyAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getCasesCompletedCumulativelyAP(columnValue);
		}
	    public void getCasesCompletedCumulativelyExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			apService.getCasesCompletedCumulativelyExcelAP(columnValue);
		}
	
	    public List getExceptionAndFirstPassAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getExceptionAndFirstPassAP(functionAppBean);
		}
	    public String getExceptionAndFirstPassAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getExceptionAndFirstPassAP(columnValue);
		}
	    public void getExceptionAndFirstPassExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
	    	apService.getExceptionAndFirstPassExcelAP(columnValue);
		}
		
	    public List getUrgentInvoiceRecievedAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getUrgentInvoiceRecievedAP(functionAppBean);
		}
	    public String getUrgentInvoiceRecievedAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return apService.getUrgentInvoiceRecievedAP(columnValue);
		}
	    public void getUrgentInvoiceRecievedExcelAP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
	    	apService.getUrgentInvoiceRecievedExcelAP(columnValue);
		}
	    
	    
	    
	    
	    
	    public List getAverageTimeTakenFromReceiptToPaymentAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return apService.getAverageTimeTakenFromReceiptToPaymentAP(functionAppBean);
			}
		    public String getAverageTimeTakenFromReceiptToPaymentAP(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return apService.getAverageTimeTakenFromReceiptToPaymentAP(columnValue);
			}
		    public void getAverageTimeTakenFromReceiptToPaymentExcelAP(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
		    	apService.getAverageTimeTakenFromReceiptToPaymentExcelAP(columnValue);
			}
		    
		    
		    public List getTATforScanningAndindexingAP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return apService.getTATforScanningAndindexingAP(functionAppBean);
			}
		    public String getTATforScanningAndindexingAP(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return apService.getTATforScanningAndindexingAP(columnValue);
			}
		    public void getTATforScanningAndindexingExcelAP(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
		    	apService.getTATforScanningAndindexingExcelAP(columnValue);
			}
	    
	    
	    
	    
	    
	    
	    

}
