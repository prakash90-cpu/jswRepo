package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ApDao;
import jsw.report.dao.CommDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class CommServiceImpl implements CommService {
	private CommDao commDao;
	private SimpleDateFormatte date=new  SimpleDateFormatte();

	public CommDao getCommDao() {
		return commDao;
	}

	public void setCommDao(CommDao commDao) {
		this.commDao = commDao;
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
	
	
	
	
	public List getNumberOfCasesCOMMById(int id,int bucket) throws SQLException{
		// TODO Auto-generated method stub
		return commDao.getNumberOfCasesCOMMById(id,bucket);
	}
	public List getNumberOfCasesCreatedCOMM(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getNumberOfCasesCreatedCOMM(functionAppBean);
	}

	public String getNumberOfCasesCreatedCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getNumberOfCasesCreatedCOMM(columnValue);
	}

	public void getNumberOfCasesCreatedExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 commDao.getNumberOfCasesCreatedExcelCOMM(columnValue);
	}

	
	public List getCasesCreatedCompletedCOMM(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCasesCreatedCompletedCOMM(functionAppBean);
	}

	public String getCaseCreatedCompanyCompletedCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCaseCreatedCompanyCompletedCOMM(columnValue);
	}
	
	public void getCaseCreatedCompanyCompletedExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 commDao.getCaseCreatedCompanyCompletedExcelCOMM(columnValue);
	}

	public List getRoleWiseTimeTakenCOMM(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getRoleWiseTimeTakenCOMM(functionAppBean);
	}

	public String getRoleWiseTimeTakenCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getRoleWiseTimeTakenCOMM(columnValue);
	}

	public void getRoleWiseTimeTakenExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 commDao.getRoleWiseTimeTakenExcelCOMM(columnValue);
	}
	
	public List getTotalCycleTimeCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getTotalCycleTimeCOMM(functionAppBean);
	}

	public String getTotalCycleTimeCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getTotalCycleTimeCOMM(columnValue);
	}
	public void getTotalCycleTimeExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		 commDao.getTotalCycleTimeExcelCOMM(columnValue);
	}

	public List getCasesProcessedCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCasesProcessedCOMM(functionAppBean);
	}

	public String getCasesProcessedCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCasesProcessedCOMM(columnValue);
	}
	public void getCasesProcessedExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		commDao.getCasesProcessedExcelCOMM(columnValue);
	}

	public List getCasesPendingCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCasesPendingCOMM(functionAppBean);
	}

	public String getCasesPendingCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		return commDao.getCasesPendingCOMM(columnValue);
	}
	public void getCasesPendingExcelCOMM(String columnValue) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		commDao.getCasesPendingExcelCOMM(columnValue);
	}

	   public List getCasesPendingForProcesssingCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return commDao.getCasesPendingForProcesssingCOMM(functionAppBean);
		}
	    public String getCasesPendingForProcesssingCOMM(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return commDao.getCasesPendingForProcesssingCOMM(columnValue);
		}
	    
	    public void getCasesPendingForProcesssingExcelCOMM(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 commDao.getCasesPendingForProcesssingExcelCOMM(columnValue);
		}
		
	    public List getCasesCompletedCumulativelyCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return commDao.getCasesCompletedCumulativelyCOMM(functionAppBean);
		}
	    public String getCasesCompletedCumulativelyCOMM(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return commDao.getCasesCompletedCumulativelyCOMM(columnValue);
		}
	    public void getCasesCompletedCumulativelyExcelCOMM(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			commDao.getCasesCompletedCumulativelyExcelCOMM(columnValue);
		}   	  

		
	

	    public List getCasesPendingWithPRTOPOCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commDao.getCasesPendingWithPRTOPOCOMM(functionAppBean);
		}

		public String getCasesPendingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commDao.getCasesPendingWithPRTOPOCOMM(columnValue);
		}
		public void getCasesPendingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			commDao.getCasesPendingExcelWithPRTOPOCOMM(columnValue);
		}

		   public List getCasesPendingForProcesssingWithPRTOPOCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commDao.getCasesPendingForProcesssingWithPRTOPOCOMM(functionAppBean);
			}
		    public String getCasesPendingForProcesssingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commDao.getCasesPendingForProcesssingWithPRTOPOCOMM(columnValue);
			}
		    
		    public void getCasesPendingForProcesssingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				 commDao.getCasesPendingForProcesssingExcelWithPRTOPOCOMM(columnValue);
			}
			

	
}
