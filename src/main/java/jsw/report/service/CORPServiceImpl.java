package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ApDao;
import jsw.report.dao.CORPDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class CORPServiceImpl implements CORPService {
	private CORPDao corpDao;
	private SimpleDateFormatte date=new  SimpleDateFormatte();
	
	
	public CORPDao getCorpDao() {
		return corpDao;
	}



	public void setCorpDao(CORPDao corpDao) {
		this.corpDao = corpDao;
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
	
	
	public List getNumberOfCasesById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return corpDao.getNumberOfCasesById(id,bucket,functionString);
	}
	

	 public List getNumberOfCasesCreatedCORP(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return corpDao.getNumberOfCasesCreatedCORP(functionAppBean);
		}
	
	 
	    public String getNumberOfCasesCreatedCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getNumberOfCasesCreatedCORP(columnValue);
		}

	    public List getCasesCreatedCompletedCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCasesCreatedCompletedCORP(functionAppBean);
		}
	    public String getCaseCreatedCompanyCompletedCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCaseCreatedCompanyCompletedCORP(columnValue);
		}
		
	    public List getRoleWiseTimeTakenCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getRoleWiseTimeTakenCORP(functionAppBean);
		}
	    public String getRoleWiseTimeTakenCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getRoleWiseTimeTakenCORP(columnValue);
		}
		
	    public List getTotalCycleTimeCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getTotalCycleTimeCORP(functionAppBean);
		}
	    public String getTotalCycleTimeCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getTotalCycleTimeCORP(columnValue);
		}
	
	    public List getCasesProcessedCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCasesProcessedCORP(functionAppBean);
		}
	    public String getCasesProcessedCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCasesProcessedCORP(columnValue);
		}
	  
	    public List getCasesPendingCORP(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCasesPendingCORP(functionAppBean);
		}
	    public String getCasesPendingCORP(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return corpDao.getCasesPendingCORP(columnValue);
		}
	    
	    
	
	
	
}
