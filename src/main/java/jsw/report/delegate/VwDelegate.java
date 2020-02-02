package jsw.report.delegate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

//import com.jsw.domain.object.ExtProcTriggeredDO;
import com.jsw.domain.object.ManualExtTriggerDO;
/*import com.jsw.domain.object.ExtProcTriggeredDO;*/
import com.jsw.transfer.object.ExtractionProcessTO;

import jsw.report.service.VwService;
import jsw.report.viewBean.ApplicationSetting;
import jsw.report.viewBean.Business;
import jsw.report.viewBean.BusinessGroup;
import jsw.report.viewBean.BusinessGroupLoc;
import jsw.report.viewBean.BusinessRoles;
import jsw.report.viewBean.CaseType;
import jsw.report.viewBean.DocumentType;
import jsw.report.viewBean.FileList;
import jsw.report.viewBean.Function;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.Location;
import jsw.report.viewBean.LocationBusiness;
import jsw.report.viewBean.RegistrationBean;
import jsw.report.viewBean.Role;
import jsw.report.viewBean.Stages;
import jsw.report.viewBean.Steps;
import jsw.report.viewBean.UserFilter;


public class VwDelegate {

	private VwService vwService;

	public VwService getVwService() {
		return vwService;
	}

	public void setVwService(VwService vwService) {
		this.vwService = vwService;
	}

	
	public List getFunctionManagement() throws SQLException{
		// TODO Auto-generated method stub
		return vwService.getFunctionManagement();
	}
	
	
	
	
	 public List getProcessAccessList() throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getProcessAccessList();
		}

		 
	 
	 public boolean submitUserFilter(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return vwService.submitUserFilter(functionAppBean);
		}
	 
	 public boolean deleteUserFilter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.deleteUserFilter(id);
		}
		 
	 
	 public UserFilter edit_User_Filter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.edit_User_Filter(id);
		}
	 
	 
	 public List editUserFilter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.editUserFilter(id);
		}
	 
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	 public List getUserFilterList() throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getUserFilterList();
		}
				public RegistrationBean editUser(int id) throws SQLException {
					// TODO Auto-generated method stub
					return vwService.editUser(id);
				}

				public boolean deleteUser(int id) throws SQLException {
					// TODO Auto-generated method stub
					return vwService.deleteUser(id);
				}

				public boolean updateUser(RegistrationBean registrationBean) throws SQLException {
					// TODO Auto-generated method stub
					return vwService.updateUser(registrationBean);
				}

				public boolean addUser(RegistrationBean regis) throws SQLException {
					return vwService.addUser(regis);
				}
			 
				
				public List<RegistrationBean> getAllUsers() throws SQLException {
					return vwService.getAllUsers();
				}
				public JSONArray getMenuTree() throws Exception {

					return vwService.getMenuTree();

				}
				
				
				
				
				public boolean updateScreenID(RegistrationBean screenID) throws SQLException {
					// TODO Auto-generated method stub
					return vwService.updateScreenID(screenID);
				}
				
				

				public List<String> getUserName() throws SQLException {
					return vwService.getUserName();
				}
				
				
				
				public List getRole() throws SQLException {
					return vwService.getRole();
				}	
				public boolean addRole(Role role) throws SQLException {
					// TODO Auto-generated method stub
					return vwService.addRole(role);
				}
				public Role editRole(int id) throws SQLException{
					return vwService.editRole(id);
				}
				public boolean deleteRole(int id) throws SQLException{
					return vwService.deleteRole(id);
				}
				
				// *************File Uploading Importing******

				public boolean addFileName(String stg,FileList f) throws SQLException {
					return vwService.addFileName(stg,f);

				}

				public List getFileList() throws SQLException {
					return vwService.getFileList();

				}

				public String getFileById(int id) throws SQLException {
					return vwService.getFileById(id);
				}

				public boolean deleteFileList(int id) throws SQLException {
					return vwService.deleteFileList(id);
				}
				// ************End*************
			 
			 
				public String saveFileToDatabase(String fileName) throws SQLException,IOException{
					// TODO Auto-generated method stub
					return vwService.saveFileToDatabase(fileName);
				}
				
				public boolean updatefileStatus(int id) throws SQLException{
					// TODO Auto-generated method stub
					return vwService.updatefileStatus(id);
				}
				
				
			///*****************************DMS EXTARCTION*******
				public ManualExtTriggerDO addExtractionProcess(ExtractionProcessTO extractionProcess) throws SQLException{
					
					return vwService.addExtractionProcess(extractionProcess);
					
				 }
				
				public List getProgressresult() throws SQLException{
					return vwService.getProgressresult();
				}
				
				public boolean deleteExtraction(ExtractionProcessTO process) throws SQLException{
					return vwService.deleteExtraction(process);
				}
				
				
				//******************END*******************
		
		
		
		
		
		// WebService Dropdown
		public JSONArray getRoleAndCasetype(String function) throws SQLException {
			// TODO Auto-generated method stub
			return vwService.getRoleAndCasetype(function);
		}

		public JSONArray getBusinessAndLocationByGrp(String function, int id) throws SQLException {
			// TODO Auto-generated method stub
			return vwService.getBusinessAndLocationByGrp(function, id);
		}

		public JSONArray getBusiByLoc(String function, int id) throws SQLException {
			// TODO Auto-generated method stub
			return vwService.getBusiByLoc(function, id);
		}
		public JSONArray getPersoneelAndPayrollByGrp(String function, int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getPersoneelAndPayrollByGrp(function, id);
		}
							
		public JSONArray getPersoneelAndPayrollByBusiness(String busiGrp,String busi,int user_id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getPersoneelAndPayrollByBusiness( busiGrp, busi, user_id);
		}

		public JSONArray getPayrollByPersonnel(String function, int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getPayrollByPersonnel( function, id);
		} 
	 
							
		
	/*	
		public JSONArray getCaseCreatedAPGraph(FunctionAppBean functionAppBean)  throws SQLException{
			// TODO Auto-generated method stub
			return vwService.getCaseCreatedAPGraph(functionAppBean);
		}*/
		
							
		//Display By
				public boolean insertSetting(ApplicationSetting appSetting) {
					// TODO Auto-generated method stub
					return vwService.insertSetting(appSetting);
				}	 
				 
	
				
				
}
