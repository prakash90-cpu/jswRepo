package jsw.report.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

//import com.jsw.domain.object.ExtProcTriggeredDO;
import com.jsw.domain.object.ManualExtTriggerDO;
/*import com.jsw.domain.object.ExtProcTriggeredDO;*/
import com.jsw.transfer.object.ExtractionProcessTO;

import jsw.report.dao.VwDao;
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


public class VwServiceImpl implements VwService {
	private VwDao vwDao;

	public VwDao getVwDao() {
		return vwDao;
	}

	public void setVwDao(VwDao vwDao) {
		this.vwDao = vwDao;
	}

	
	//*******************Master***********************
	
	public List getFunctionManagement() throws SQLException{
		return vwDao.getFunctionManagement();
	}
	 
	 
	 
	 
	
	 //************************End*******************
	 
	
	 //**********************External System***********
	/* public List getExtractionSystem() throws SQLException{
		 return vwDao.getExtractionSystem();
	 }*/
	 public  ManualExtTriggerDO addExtractionProcess(ExtractionProcessTO extractionProcess) throws SQLException{
		  //vwDao.addExtractionProcess(extractionProcess);

		 return vwDao.saveExtTrigger(extractionProcess);
		  
		
	 }
	 
	 
	 public List getProgressresult() throws SQLException{
		 return vwDao.getProgressresult();
	 }
	 
	 
	 public boolean deleteExtraction(ExtractionProcessTO process) throws SQLException{
		 return vwDao.deleteExtraction(process);
	 }
	 
	 //****************End*****************
	 
	 
	 
	 public List getProcessAccessList() throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.getProcessAccessList();
		}
	 
	 public boolean submitUserFilter(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
			// TODO Auto-generated method stub
			return vwDao.submitUserFilter(functionAppBean);
		}
	 
	
	 public boolean deleteUserFilter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.deleteUserFilter(id);
		}
	 
	 
	 public UserFilter edit_User_Filter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.edit_User_Filter(id);
		}
	 
	 public List editUserFilter(int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.editUserFilter(id);
		}
	 
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 
	 public List getUserFilterList() throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.getUserFilterList();
		}
	 
	 
			public RegistrationBean editUser(int id) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.editUser(id);
			}

			public boolean deleteUser(int id) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.deleteUser(id);
			}

			public boolean updateUser(RegistrationBean registrationBean) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.updateUser(registrationBean);
			}

			public List<RegistrationBean> getAllUsers() throws SQLException {
				return vwDao.getAllUsers();
			}

			public JSONArray getMenuTree() throws Exception {

				return vwDao.getMenuTree();

			}

			public List<String> getUserName() throws SQLException {
				return vwDao.getUserName();
			}

			public boolean addUser(RegistrationBean regis) throws SQLException {
				return vwDao.addUser(regis);

			}

			public boolean updateScreenID(RegistrationBean screenID) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.updateScreenID(screenID);
			}
			
			
			
			public boolean addRole(Role role) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.addRole(role);
			}

			public List getRole() throws SQLException {
				return vwDao.getRole();
			}
			public Role editRole(int id) throws SQLException{
				return vwDao.editRole(id);
			}
			public boolean deleteRole(int id) throws SQLException{
				return vwDao.deleteRole(id);
			}
			
			
			
			
			// **************File Uploading Importing************
			public boolean addFileName(String file,FileList f) throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.addFileName(file,f);
			}

			public List getFileList() throws SQLException {
				// TODO Auto-generated method stub
				return vwDao.getFileList();
			}

			public String getFileById(int id) throws SQLException {
				return vwDao.getFileById(id);
			}

			public boolean deleteFileList(int id) throws SQLException {
				return vwDao.deleteFileList(id);

			}

			// **********End******************
			
			
			
			public String saveFileToDatabase(String fileName) throws SQLException,IOException{
				// TODO Auto-generated method stub
				return vwDao.saveFileToDatabase(fileName);
			}
			public boolean updatefileStatus(int id) throws SQLException{
				// TODO Auto-generated method stub
				return vwDao.updatefileStatus(id);
			}
		
			
		
		
		
		// WebService Dropdown
		public JSONArray getRoleAndCasetype(String function) throws SQLException {
			// TODO Auto-generated method stub
			return vwDao.getRoleAndCasetype(function);
		}

		public JSONArray getBusinessAndLocationByGrp(String function, int id) throws SQLException {
			// TODO Auto-generated method stub
			return vwDao.getBusinessAndLocationByGrp(function, id);
		}

		public JSONArray getBusiByLoc(String function, int id) throws SQLException {
			// TODO Auto-generated method stub
			return vwDao.getBusiByLoc(function, id);
		}
		
		
		
		  
	    public JSONArray getPersoneelAndPayrollByGrp(String function, int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.getPersoneelAndPayrollByGrp(function, id);
		}
		public JSONArray getPersoneelAndPayrollByBusiness(String busiGrp,String busi,int user_id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.getPersoneelAndPayrollByBusiness( busiGrp, busi, user_id);
		}

		public JSONArray getPayrollByPersonnel(String function, int id) throws SQLException{
			// TODO Auto-generated method stub
			return vwDao.getPayrollByPersonnel( function, id);
		} 
	
		
		
		//Display By
		public boolean insertSetting(ApplicationSetting appSetting) {
			// TODO Auto-generated method stub
			return vwDao.insertSetting(appSetting);
		}

		
		
		
		
		
		
}
