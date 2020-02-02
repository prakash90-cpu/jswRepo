package jsw.report.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;


import com.jsw.domain.object.ManualExtTriggerDO;
//import com.jsw.domain.object.ExtProcTriggeredDO;
import com.jsw.transfer.object.ExtractionProcessTO;

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

public interface VwDao {
	
	
	
	
	 public List getFunctionManagement() throws SQLException;
	
	
		 
		 
		 //************External System***************
		 
		 
		// public List getExtractionSystem() throws SQLException;
		 public boolean addExtractionProcess(ExtractionProcessTO extractionProcess) throws SQLException;
		 public ManualExtTriggerDO saveExtTrigger(ExtractionProcessTO extractionProcess) throws SQLException;
		 
		 public List getProgressresult() throws SQLException;
		 
		 public boolean deleteExtraction(ExtractionProcessTO process) throws SQLException;
		
		
		 
		 //***************End***************

			public List<RegistrationBean> getAllUsers() throws SQLException;

			public JSONArray getMenuTree() throws Exception;

			public List<String> getUserName() throws SQLException;

			public boolean updateScreenID(RegistrationBean screenID) throws SQLException;
			/*public boolean updateDashboard(RegistrationBean screenID) throws SQLException;
	*/

			public boolean addUser(RegistrationBean regis) throws SQLException;

			// Role
			 public List getRole() throws SQLException;
				public boolean addRole(Role role) throws SQLException;
				public Role editRole(int id) throws SQLException;
				public boolean deleteRole(int id) throws SQLException;
				
				
			// **********************file Uploading**********
			public List getFileList() throws SQLException;

			public String getFileById(int id) throws SQLException;

			public boolean addFileName(String file,FileList f) throws SQLException;

			public boolean deleteFileList(int id) throws SQLException;
			// *******************End************

			public boolean updateUser(RegistrationBean regis) throws SQLException;

			public RegistrationBean editUser(int id) throws SQLException;

			public boolean deleteUser(int id) throws SQLException;

			
			public String saveFileToDatabase(String fileName) throws SQLException,IOException;
			public boolean updatefileStatus(int id) throws SQLException;
	
		
		 		    
		
	//Display By
	public boolean insertSetting(ApplicationSetting appSetting);
	
	
	
	
		    
	// user filter DAO
	public List getProcessAccessList() throws SQLException;

	public boolean submitUserFilter(FunctionAppBean functionAppBean) throws SQLException, JSONException;

	public List getUserFilterList() throws SQLException;

	public boolean updateUserFilter(UserFilter userFilter) throws SQLException;

	public List editUserFilter(int id) throws SQLException;

	public boolean deleteUserFilter(int id) throws SQLException;

	public UserFilter edit_User_Filter(int id) throws SQLException;

	
	

	// WebService Dropdown
	public JSONArray getRoleAndCasetype(String function) throws SQLException;

	public JSONArray getBusinessAndLocationByGrp(String function, int id) throws SQLException;

	public JSONArray getBusiByLoc(String function, int id) throws SQLException;	 
			
	  public JSONArray getPersoneelAndPayrollByGrp(String function, int id) throws SQLException;
		public JSONArray getPersoneelAndPayrollByBusiness(String busiGrp,String busi,int user_id) throws SQLException;

		public JSONArray getPayrollByPersonnel(String function, int id) throws SQLException;	 
		
	//Login
	
	
			 
}
