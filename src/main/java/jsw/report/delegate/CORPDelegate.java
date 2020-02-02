package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.ApService;
import jsw.report.service.CORPService;
import jsw.report.viewBean.FunctionAppBean;

public class CORPDelegate {
	private CORPService corpService;
	
	
	
	
	public CORPService getCorpService() {
		return corpService;
	}



	public void setCorpService(CORPService corpService) {
		this.corpService = corpService;
	}



	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return corpService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	public List getNumberOfCasesById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return corpService.getNumberOfCasesById(id,bucket,functionString);
	}  	
	
	
	// CORP Report Service

	public List getNumberOfCasesCreatedCORP(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getNumberOfCasesCreatedCORP(functionAppBean);
		}

		public String getNumberOfCasesCreatedCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getNumberOfCasesCreatedCORP(columnValue);
		}

		public List getCasesCreatedCompletedCORP(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCasesCreatedCompletedCORP(functionAppBean);
		}

		public String getCaseCreatedCompanyCompletedCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCaseCreatedCompanyCompletedCORP(columnValue);
		}

		public List getRoleWiseTimeTakenCORP(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getRoleWiseTimeTakenCORP(functionAppBean);
		}

		public String getRoleWiseTimeTakenCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getRoleWiseTimeTakenCORP(columnValue);
		}

		public List getTotalCycleTimeCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getTotalCycleTimeCORP(functionAppBean);
		}

		public String getTotalCycleTimeCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getTotalCycleTimeCORP(columnValue);
		}

		public List getCasesProcessedCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCasesProcessedCORP(functionAppBean);
		}

		public String getCasesProcessedCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCasesProcessedCORP(columnValue);
		}

		public List getCasesPendingCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCasesPendingCORP(functionAppBean);
		}

		public String getCasesPendingCORP(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpService.getCasesPendingCORP(columnValue);
		}

}
