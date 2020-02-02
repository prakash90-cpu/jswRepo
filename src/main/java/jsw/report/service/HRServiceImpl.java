package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ApDao;
import jsw.report.dao.HRDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class HRServiceImpl implements HRService {
	private HRDao hrDao;
	
	private SimpleDateFormatte date=new  SimpleDateFormatte();
	
	public HRDao getHrDao() {
		return hrDao;
	}

	public void setHrDao(HRDao hrDao) {
		this.hrDao = hrDao;
	}

	
	
	public SimpleDateFormatte getDate() {
		return date;
	}

	public void setDate(SimpleDateFormatte date) {
		this.date = date;
	}
	
	public List getNumberOfCasesHRById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return hrDao.getNumberOfCasesHRById(id,bucket,functionString);
	}
	
	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
		// TODO Auto-generated method stub
		return date.getAllPeriodDisplay(selectedDate, periodFormat);
	}
	
	

	
	
	//HR
	

	 public List getNumberOfCasesCreatedHR(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return hrDao.getNumberOfCasesCreatedHR(functionAppBean);
		}
	
	 
	    public String getNumberOfCasesCreatedHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getNumberOfCasesCreatedHR(columnValue);
		}
	 public void getNumberOfCasesCreatedExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			hrDao.getNumberOfCasesCreatedExcelHR(columnValue);
		}

	  public List getCasesCreatedCompletedHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCasesCreatedCompletedHR(functionAppBean);
		}
	    public String getCaseCreatedCompanyCompletedHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCaseCreatedCompanyCompletedHR(columnValue);
		}
		
	 public void getCaseCreatedCompanyCompletedExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 hrDao.getCaseCreatedCompanyCompletedExcelHR(columnValue);
		}
	    
	    public List getRoleWiseTimeTakenHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getRoleWiseTimeTakenHR(functionAppBean);
		}
	    public String getRoleWiseTimeTakenHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getRoleWiseTimeTakenHR(columnValue);
		}
	    
	 public void getRoleWiseTimeTakenExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 hrDao.getRoleWiseTimeTakenExcelHR(columnValue);
		}
		
	    public List getTotalCycleTimeHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getTotalCycleTimeHR(functionAppBean);
		}
	    public String getTotalCycleTimeHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getTotalCycleTimeHR(columnValue);
		}
	    
	 public void getTotalCycleTimeExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			hrDao.getTotalCycleTimeExcelHR(columnValue);
		}
	
	    public List getCasesProcessedHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCasesProcessedHR(functionAppBean);
		}
	    public String getCasesProcessedHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCasesProcessedHR(columnValue);
		}
	 public void getCasesProcessedExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 hrDao.getCasesProcessedExcelHR(columnValue);
		}
	  
	    public List getCasesPendingHR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCasesPendingHR(functionAppBean);
		}
	    public String getCasesPendingHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return hrDao.getCasesPendingHR(columnValue);
		}
	 public void getCasesPendingExcelHR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
		 hrDao.getCasesPendingExcelHR(columnValue);
		}
	   
	   

}
