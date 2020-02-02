package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import jsw.report.dao.RTRDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

import org.json.JSONException;

public class RTRServiceImpl implements RTRService{
	private RTRDao rtrDao;
	
	private SimpleDateFormatte date=new  SimpleDateFormatte();
	
	
	
	  public RTRDao getRtrDao() {
		return rtrDao;
	}


	public void setRtrDao(RTRDao rtrDao) {
		this.rtrDao = rtrDao;
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
	
	public List getNumberOfCasesRTRById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return rtrDao.getNumberOfCasesRTRById(id,bucket,functionString);
	}
	

	public List getNumberOfCasesCreatedRTR(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return rtrDao.getNumberOfCasesCreatedRTR(functionAppBean);
		}
	
	 
	    public String getNumberOfCasesCreatedRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getNumberOfCasesCreatedRTR(columnValue);
		}
	 public void getNumberOfCasesCreatedExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			rtrDao.getNumberOfCasesCreatedExcelRTR(columnValue);
		}

	  public List getCasesCreatedCompletedRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCasesCreatedCompletedRTR(functionAppBean);
		}
	    public String getCaseCreatedCompanyCompletedRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCaseCreatedCompanyCompletedRTR(columnValue);
		}
		
	 public void getCaseCreatedCompanyCompletedExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 rtrDao.getCaseCreatedCompanyCompletedExcelRTR(columnValue);
		}
	    
	    public List getRoleWiseTimeTakenRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getRoleWiseTimeTakenRTR(functionAppBean);
		}
	    public String getRoleWiseTimeTakenRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getRoleWiseTimeTakenRTR(columnValue);
		}
	    
	 public void getRoleWiseTimeTakenExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 rtrDao.getRoleWiseTimeTakenExcelRTR(columnValue);
		}
		
	    public List getTotalCycleTimeRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getTotalCycleTimeRTR(functionAppBean);
		}
	    public String getTotalCycleTimeRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getTotalCycleTimeRTR(columnValue);
		}
	    
	 public void getTotalCycleTimeExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			rtrDao.getTotalCycleTimeExcelRTR(columnValue);
		}
	
	    public List getCasesProcessedRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCasesProcessedRTR(functionAppBean);
		}
	    public String getCasesProcessedRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCasesProcessedRTR(columnValue);
		}
	 public void getCasesProcessedExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 rtrDao.getCasesProcessedExcelRTR(columnValue);
		}
	  
	    public List getCasesPendingRTR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCasesPendingRTR(functionAppBean);
		}
	    public String getCasesPendingRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return rtrDao.getCasesPendingRTR(columnValue);
		}
	 public void getCasesPendingExcelRTR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
		 rtrDao.getCasesPendingExcelRTR(columnValue);
		}

}
