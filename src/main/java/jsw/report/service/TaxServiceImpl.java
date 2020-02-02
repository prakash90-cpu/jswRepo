package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.dao.ApDao;
import jsw.report.dao.TaxDao;
import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;

public class TaxServiceImpl implements TaxService{
	
	private TaxDao taxDao;
	private SimpleDateFormatte date=new  SimpleDateFormatte();
	
	
	public TaxDao getTaxDao() {
		return taxDao;
	}


	public void setTaxDao(TaxDao taxDao) {
		this.taxDao = taxDao;
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
	
	
    // Tax Report Services
	    public List getNumberOfCasesTaxById(int id,int bucket,String functionString) throws SQLException	{
			// TODO Auto-generated method stub
			return taxDao.getNumberOfCasesTaxById(id,bucket,functionString);
		}
	    
	    public List getNumberOfCasesCreatedTax(FunctionAppBean functionAppBean)   throws SQLException, JSONException {
  			// TODO Auto-generated method stub
  			return taxDao.getNumberOfCasesCreatedTax(functionAppBean);
  		}
  	
  	 
  	    public String getNumberOfCasesCreatedTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getNumberOfCasesCreatedTax(columnValue);
  		}
  	 public void getNumberOfCasesCreatedExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			taxDao.getNumberOfCasesCreatedExcelTax(columnValue);
  		}

  	  public List getCasesCreatedCompletedTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCasesCreatedCompletedTax(functionAppBean);
  		}
  	    public String getCaseCreatedCompanyCompletedTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCaseCreatedCompanyCompletedTax(columnValue);
  		}
  		
  	 public void getCaseCreatedCompanyCompletedExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			 taxDao.getCaseCreatedCompanyCompletedExcelTax(columnValue);
  		}
  	    
  	    public List getRoleWiseTimeTakenTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getRoleWiseTimeTakenTax(functionAppBean);
  		}
  	    public String getRoleWiseTimeTakenTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getRoleWiseTimeTakenTax(columnValue);
  		}
  	    
  	 public void getRoleWiseTimeTakenExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			 taxDao.getRoleWiseTimeTakenExcelTax(columnValue);
  		}
  		
  	    public List getTotalCycleTimeTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getTotalCycleTimeTax(functionAppBean);
  		}
  	    public String getTotalCycleTimeTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getTotalCycleTimeTax(columnValue);
  		}
  	    
  	 public void getTotalCycleTimeExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			taxDao.getTotalCycleTimeExcelTax(columnValue);
  		}
  	
  	    public List getCasesProcessedTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCasesProcessedTax(functionAppBean);
  		}
  	    public String getCasesProcessedTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCasesProcessedTax(columnValue);
  		}
  	 public void getCasesProcessedExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			 taxDao.getCasesProcessedExcelTax(columnValue);
  		}
  	  
  	    public List getCasesPendingTax(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCasesPendingTax(functionAppBean);
  		}
  	    public String getCasesPendingTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  			return taxDao.getCasesPendingTax(columnValue);
  		}
  	 public void getCasesPendingExcelTax(String columnValue) throws SQLException, JSONException{
  			// TODO Auto-generated method stub
  		 taxDao.getCasesPendingExcelTax(columnValue);
  		}
  	    
		

}
