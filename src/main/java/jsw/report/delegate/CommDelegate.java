package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import jsw.report.service.CommService;

import jsw.report.viewBean.FunctionAppBean;

public class CommDelegate {
	private CommService commService;

	
	
	
	 public CommService getCommService() {
		return commService;
	}


	public void setCommService(CommService commService) {
		this.commService = commService;
	}


	public List getNumberOfCasesCOMMById(int id,int bucket) throws SQLException{
			// TODO Auto-generated method stub
			return commService.getNumberOfCasesCOMMById(id, bucket);
		}    

	 
	 public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {
			// TODO Auto-generated method stub
			return commService.getAllPeriodDisplay(selectedDate, periodFormat);
		}
		
	
	 public List getNumberOfCasesCreatedCOMM(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getNumberOfCasesCreatedCOMM(functionAppBean);
		}

		public String getNumberOfCasesCreatedCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getNumberOfCasesCreatedCOMM(columnValue);
		}

		public void getNumberOfCasesCreatedExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			 commService.getNumberOfCasesCreatedExcelCOMM(columnValue);
		}

		
		public List getCasesCreatedCompletedCOMM(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCasesCreatedCompletedCOMM(functionAppBean);
		}

		public String getCaseCreatedCompanyCompletedCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCaseCreatedCompanyCompletedCOMM(columnValue);
		}
		
		public void getCaseCreatedCompanyCompletedExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			 commService.getCaseCreatedCompanyCompletedExcelCOMM(columnValue);
		}

		public List getRoleWiseTimeTakenCOMM(FunctionAppBean functionAppBean)
				throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getRoleWiseTimeTakenCOMM(functionAppBean);
		}

		public String getRoleWiseTimeTakenCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getRoleWiseTimeTakenCOMM(columnValue);
		}

		public void getRoleWiseTimeTakenExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			 commService.getRoleWiseTimeTakenExcelCOMM(columnValue);
		}
		
		public List getTotalCycleTimeCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getTotalCycleTimeCOMM(functionAppBean);
		}

		public String getTotalCycleTimeCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getTotalCycleTimeCOMM(columnValue);
		}
		public void getTotalCycleTimeExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			 commService.getTotalCycleTimeExcelCOMM(columnValue);
		}

		public List getCasesProcessedCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCasesProcessedCOMM(functionAppBean);
		}

		public String getCasesProcessedCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCasesProcessedCOMM(columnValue);
		}
		public void getCasesProcessedExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			commService.getCasesProcessedExcelCOMM(columnValue);
		}

		public List getCasesPendingCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCasesPendingCOMM(functionAppBean);
		}

		public String getCasesPendingCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			return commService.getCasesPendingCOMM(columnValue);
		}
		public void getCasesPendingExcelCOMM(String columnValue) throws SQLException, JSONException {
			// TODO Auto-generated method stub
			commService.getCasesPendingExcelCOMM(columnValue);
		}

		   public List getCasesPendingForProcesssingCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commService.getCasesPendingForProcesssingCOMM(functionAppBean);
			}
		    public String getCasesPendingForProcesssingCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commService.getCasesPendingForProcesssingCOMM(columnValue);
			}
		    
		    public void getCasesPendingForProcesssingExcelCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				 commService.getCasesPendingForProcesssingExcelCOMM(columnValue);
			}
			
		    public List getCasesCompletedCumulativelyCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commService.getCasesCompletedCumulativelyCOMM(functionAppBean);
			}
		    public String getCasesCompletedCumulativelyCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				return commService.getCasesCompletedCumulativelyCOMM(columnValue);
			}
		    public void getCasesCompletedCumulativelyExcelCOMM(String columnValue) throws SQLException, JSONException{
				// TODO Auto-generated method stub
				commService.getCasesCompletedCumulativelyExcelCOMM(columnValue);
			}
	

		    
			public List getCasesPendingWithPRTOPOCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
				// TODO Auto-generated method stub
				return commService.getCasesPendingWithPRTOPOCOMM(functionAppBean);
			}

			public String getCasesPendingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {
				// TODO Auto-generated method stub
				return commService.getCasesPendingWithPRTOPOCOMM(columnValue);
			}
			public void getCasesPendingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {
				// TODO Auto-generated method stub
				commService.getCasesPendingExcelWithPRTOPOCOMM(columnValue);
			}

			   public List getCasesPendingForProcesssingWithPRTOPOCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
					// TODO Auto-generated method stub
					return commService.getCasesPendingForProcesssingWithPRTOPOCOMM(functionAppBean);
				}
			    public String getCasesPendingForProcesssingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException{
					// TODO Auto-generated method stub
					return commService.getCasesPendingForProcesssingWithPRTOPOCOMM(columnValue);
				}
			    
			    public void getCasesPendingForProcesssingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException{
					// TODO Auto-generated method stub
					 commService.getCasesPendingForProcesssingExcelWithPRTOPOCOMM(columnValue);
				}

}
