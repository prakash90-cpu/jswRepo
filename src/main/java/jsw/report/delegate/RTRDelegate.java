package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.RTRService;

import jsw.report.viewBean.FunctionAppBean;

public class RTRDelegate {
	
	private RTRService rtrService;
	
	

	
	
	public RTRService getRtrService() {
		return rtrService;
	}


	public void setRtrService(RTRService rtrService) {
		this.rtrService = rtrService;
	}


	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return rtrService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	

	public List getNumberOfCasesRTRById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return rtrService.getNumberOfCasesRTRById(id,bucket,functionString);
	}   	
	
	
	// RTR Report Service

	public List getNumberOfCasesCreatedRTR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getNumberOfCasesCreatedRTR(functionAppBean);
	}

	public String getNumberOfCasesCreatedRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getNumberOfCasesCreatedRTR(columnValue);
	}

	public void getNumberOfCasesCreatedExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 rtrService.getNumberOfCasesCreatedExcelRTR(columnValue);
	}								
	
	public List getCasesCreatedCompletedRTR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCasesCreatedCompletedRTR(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCaseCreatedCompanyCompletedRTR(columnValue);
	}
	public void getCaseCreatedCompanyCompletedExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 rtrService.getCaseCreatedCompanyCompletedExcelRTR(columnValue);
	}

	public List getRoleWiseTimeTakenRTR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getRoleWiseTimeTakenRTR(functionAppBean);
	}

	public String getRoleWiseTimeTakenRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getRoleWiseTimeTakenRTR(columnValue);
	}
	
	public void getRoleWiseTimeTakenExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		rtrService.getRoleWiseTimeTakenExcelRTR(columnValue);
	}

	public List getTotalCycleTimeRTR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getTotalCycleTimeRTR(functionAppBean);
	}

	public String getTotalCycleTimeRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getTotalCycleTimeRTR(columnValue);
	}
	public void getTotalCycleTimeExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 rtrService.getTotalCycleTimeExcelRTR(columnValue);
	}
	

	public List getCasesProcessedRTR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCasesProcessedRTR(functionAppBean);
	}

	public String getCasesProcessedRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCasesProcessedRTR(columnValue);
	}
	public void getCasesProcessedExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		rtrService.getCasesProcessedExcelRTR(columnValue);
	}

	public List getCasesPendingRTR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCasesPendingRTR(functionAppBean);
	}

	public String getCasesPendingRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return rtrService.getCasesPendingRTR(columnValue);
	}

	public void getCasesPendingExcelRTR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 rtrService.getCasesPendingExcelRTR(columnValue);
	}
}
