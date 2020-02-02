package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.ARService;
import jsw.report.service.ApService;
import jsw.report.viewBean.FunctionAppBean;

public class ARDelegate {
	
private ARService arService;
	


	
	
	
	public ARService getArService() {
	return arService;
}




public void setArService(ARService arService) {
	this.arService = arService;
}




	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return arService.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	
	
	
	public List getNumberOfCasesARById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return arService.getNumberOfCasesARById(id,bucket,functionString);
	}   	
	
	
	
	public List getNumberOfCasesCreatedAR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getNumberOfCasesCreatedAR(functionAppBean);
	}

	public String getNumberOfCasesCreatedAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getNumberOfCasesCreatedAR(columnValue);
	}

	public void getNumberOfCasesCreatedExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 arService.getNumberOfCasesCreatedExcelAR(columnValue);
	}								
	
	public List getCasesCreatedCompletedAR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCasesCreatedCompletedAR(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCaseCreatedCompanyCompletedAR(columnValue);
	}
	public void getCaseCreatedCompanyCompletedExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 arService.getCaseCreatedCompanyCompletedExcelAR(columnValue);
	}

	public List getRoleWiseTimeTakenAR(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getRoleWiseTimeTakenAR(functionAppBean);
	}

	public String getRoleWiseTimeTakenAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getRoleWiseTimeTakenAR(columnValue);
	}
	
	public void getRoleWiseTimeTakenExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		arService.getRoleWiseTimeTakenExcelAR(columnValue);
	}

	public List getTotalCycleTimeAR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getTotalCycleTimeAR(functionAppBean);
	}

	public String getTotalCycleTimeAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getTotalCycleTimeAR(columnValue);
	}
	public void getTotalCycleTimeExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 arService.getTotalCycleTimeExcelAR(columnValue);
	}
	

	public List getCasesProcessedAR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCasesProcessedAR(functionAppBean);
	}

	public String getCasesProcessedAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCasesProcessedAR(columnValue);
	}
	public void getCasesProcessedExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		arService.getCasesProcessedExcelAR(columnValue);
	}

	public List getCasesPendingAR(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCasesPendingAR(functionAppBean);
	}

	public String getCasesPendingAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return arService.getCasesPendingAR(columnValue);
	}

	public void getCasesPendingExcelAR(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 arService.getCasesPendingExcelAR(columnValue);
	}
	   public List getCasesPendingForProcesssingAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arService.getCasesPendingForProcesssingAR(functionAppBean);
		}
	    public String getCasesPendingForProcesssingAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arService.getCasesPendingForProcesssingAR(columnValue);
		}
		
	    public void getCasesPendingForProcesssingExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			arService.getCasesPendingForProcesssingExcelAR(columnValue);
		}
	    
	    public List getCasesCompletedCumulativelyAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arService.getCasesCompletedCumulativelyAR(functionAppBean);
		}
	    public String getCasesCompletedCumulativelyAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arService.getCasesCompletedCumulativelyAR(columnValue);
		}
	    public void getCasesCompletedCumulativelyExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			arService.getCasesCompletedCumulativelyExcelAR(columnValue);
		}
}
