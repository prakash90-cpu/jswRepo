package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.ApService;
import jsw.report.service.HRService;
import jsw.report.viewBean.FunctionAppBean;

public class HRDelegate {
	private HRService hrService;
	
	
	   public HRService getHrService() {
		return hrService;
	}
	public void setHrService(HRService hrService) {
		this.hrService = hrService;
	}
	
	
	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return hrService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	
	
	
	
	public List getNumberOfCasesHRById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return hrService.getNumberOfCasesHRById(id,bucket,functionString);
	}
	
	
	public List getNumberOfCasesCreatedHR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getNumberOfCasesCreatedHR(functionAppBean);
	}

	public String getNumberOfCasesCreatedHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getNumberOfCasesCreatedHR(columnValue);
	}

	public void getNumberOfCasesCreatedExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 hrService.getNumberOfCasesCreatedExcelHR(columnValue);
	}								
	
	public List getCasesCreatedCompletedHR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCasesCreatedCompletedHR(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCaseCreatedCompanyCompletedHR(columnValue);
	}
	public void getCaseCreatedCompanyCompletedExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 hrService.getCaseCreatedCompanyCompletedExcelHR(columnValue);
	}

	public List getRoleWiseTimeTakenHR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getRoleWiseTimeTakenHR(functionAppBean);
	}

	public String getRoleWiseTimeTakenHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getRoleWiseTimeTakenHR(columnValue);
	}
	
	public void getRoleWiseTimeTakenExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		hrService.getRoleWiseTimeTakenExcelHR(columnValue);
	}

	public List getTotalCycleTimeHR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getTotalCycleTimeHR(functionAppBean);
	}

	public String getTotalCycleTimeHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getTotalCycleTimeHR(columnValue);
	}
	public void getTotalCycleTimeExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 hrService.getTotalCycleTimeExcelHR(columnValue);
	}
	

	public List getCasesProcessedHR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCasesProcessedHR(functionAppBean);
	}

	public String getCasesProcessedHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCasesProcessedHR(columnValue);
	}
	public void getCasesProcessedExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		hrService.getCasesProcessedExcelHR(columnValue);
	}

	public List getCasesPendingHR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCasesPendingHR(functionAppBean);
	}

	public String getCasesPendingHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return hrService.getCasesPendingHR(columnValue);
	}

	public void getCasesPendingExcelHR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 hrService.getCasesPendingExcelHR(columnValue);
	}




}
