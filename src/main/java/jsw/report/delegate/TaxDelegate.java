package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.ApService;
import jsw.report.service.TaxService;
import jsw.report.viewBean.FunctionAppBean;

public class TaxDelegate {
	
	
private TaxService taxService;
	

	
	
	
	public TaxService getTaxService() {
	return taxService;
}




public void setTaxService(TaxService taxService) {
	this.taxService = taxService;
}




	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return taxService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	
	
	
    // Tax Report Delegate
	  
    public List getNumberOfCasesTaxById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return taxService.getNumberOfCasesTaxById(id,bucket,functionString);
	} 
	
    
	public List getNumberOfCasesCreatedTax(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getNumberOfCasesCreatedTax(functionAppBean);
	}

	public String getNumberOfCasesCreatedTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getNumberOfCasesCreatedTax(columnValue);
	}

	public void getNumberOfCasesCreatedExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 taxService.getNumberOfCasesCreatedExcelTax(columnValue);
	}								
	
	public List getCasesCreatedCompletedTax(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCasesCreatedCompletedTax(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCaseCreatedCompanyCompletedTax(columnValue);
	}
	public void getCaseCreatedCompanyCompletedExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 taxService.getCaseCreatedCompanyCompletedExcelTax(columnValue);
	}

	public List getRoleWiseTimeTakenTax(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getRoleWiseTimeTakenTax(functionAppBean);
	}

	public String getRoleWiseTimeTakenTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getRoleWiseTimeTakenTax(columnValue);
	}
	
	public void getRoleWiseTimeTakenExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		taxService.getRoleWiseTimeTakenExcelTax(columnValue);
	}

	public List getTotalCycleTimeTax(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getTotalCycleTimeTax(functionAppBean);
	}

	public String getTotalCycleTimeTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getTotalCycleTimeTax(columnValue);
	}
	public void getTotalCycleTimeExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 taxService.getTotalCycleTimeExcelTax(columnValue);
	}
	

	public List getCasesProcessedTax(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCasesProcessedTax(functionAppBean);
	}

	public String getCasesProcessedTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCasesProcessedTax(columnValue);
	}
	public void getCasesProcessedExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		taxService.getCasesProcessedExcelTax(columnValue);
	}

	public List getCasesPendingTax(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCasesPendingTax(functionAppBean);
	}

	public String getCasesPendingTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return taxService.getCasesPendingTax(columnValue);
	}

	public void getCasesPendingExcelTax(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 taxService.getCasesPendingExcelTax(columnValue);
	}



	

}
