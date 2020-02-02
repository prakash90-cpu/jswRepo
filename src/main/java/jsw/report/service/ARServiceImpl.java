package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ARDao;
import jsw.report.dao.ApDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class ARServiceImpl implements ARService {
	private ARDao arDao;

	private SimpleDateFormatte date=new  SimpleDateFormatte();	
	public ARDao getArDao() {
		return arDao;
	}


	public void setArDao(ARDao arDao) {
		this.arDao = arDao;
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
	
	
	public List getNumberOfCasesARById(int id,int bucket,String functionString) throws SQLException	{
		// TODO Auto-generated method stub
		return arDao.getNumberOfCasesARById(id,bucket,functionString);
	}
	
	
	
	public List getNumberOfCasesCreatedAR(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return arDao.getNumberOfCasesCreatedAR(functionAppBean);
		}
	
	 
	    public String getNumberOfCasesCreatedAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getNumberOfCasesCreatedAR(columnValue);
		}
	 public void getNumberOfCasesCreatedExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			arDao.getNumberOfCasesCreatedExcelAR(columnValue);
		}

	  public List getCasesCreatedCompletedAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCasesCreatedCompletedAR(functionAppBean);
		}
	    public String getCaseCreatedCompanyCompletedAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCaseCreatedCompanyCompletedAR(columnValue);
		}
		
	 public void getCaseCreatedCompanyCompletedExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 arDao.getCaseCreatedCompanyCompletedExcelAR(columnValue);
		}
	    
	    public List getRoleWiseTimeTakenAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getRoleWiseTimeTakenAR(functionAppBean);
		}
	    public String getRoleWiseTimeTakenAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getRoleWiseTimeTakenAR(columnValue);
		}
	    
	 public void getRoleWiseTimeTakenExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 arDao.getRoleWiseTimeTakenExcelAR(columnValue);
		}
		
	    public List getTotalCycleTimeAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getTotalCycleTimeAR(functionAppBean);
		}
	    public String getTotalCycleTimeAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getTotalCycleTimeAR(columnValue);
		}
	    
	 public void getTotalCycleTimeExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			arDao.getTotalCycleTimeExcelAR(columnValue);
		}
	
	    public List getCasesProcessedAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCasesProcessedAR(functionAppBean);
		}
	    public String getCasesProcessedAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCasesProcessedAR(columnValue);
		}
	 public void getCasesProcessedExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			 arDao.getCasesProcessedExcelAR(columnValue);
		}
	  
	    public List getCasesPendingAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCasesPendingAR(functionAppBean);
		}
	    public String getCasesPendingAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return arDao.getCasesPendingAR(columnValue);
		}
	 public void getCasesPendingExcelAR(String columnValue) throws SQLException, JSONException{
			// TODO Auto-generated method stub
		 arDao.getCasesPendingExcelAR(columnValue);
		}
	   public List getCasesPendingForProcesssingAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
		// TODO Auto-generated method stub
		return arDao.getCasesPendingForProcesssingAR(functionAppBean);
	}
    public String getCasesPendingForProcesssingAR(String columnValue) throws SQLException, JSONException{
		// TODO Auto-generated method stub
		return arDao.getCasesPendingForProcesssingAR(columnValue);
	}
	
    public void getCasesPendingForProcesssingExcelAR(String columnValue) throws SQLException, JSONException{
		// TODO Auto-generated method stub
		 arDao.getCasesPendingForProcesssingExcelAR(columnValue);
	}
    
    public List getCasesCompletedCumulativelyAR(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
		// TODO Auto-generated method stub
		 return arDao.getCasesCompletedCumulativelyAR(functionAppBean);
	}
    public String getCasesCompletedCumulativelyAR(String columnValue) throws SQLException, JSONException{
		// TODO Auto-generated method stub
		return arDao.getCasesCompletedCumulativelyAR(columnValue);
	}

    public void getCasesCompletedCumulativelyExcelAR(String columnValue) throws SQLException, JSONException{
		// TODO Auto-generated method stub
		 arDao.getCasesCompletedCumulativelyExcelAR(columnValue);
	}
	

}
