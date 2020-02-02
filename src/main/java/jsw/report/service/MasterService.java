package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import jsw.report.viewBean.Business;
import jsw.report.viewBean.BusinessGroup;
import jsw.report.viewBean.BusinessGroupLoc;
import jsw.report.viewBean.BusinessRoles;
import jsw.report.viewBean.CaseType;
import jsw.report.viewBean.DocumentType;
import jsw.report.viewBean.Function;
import jsw.report.viewBean.Location;
import jsw.report.viewBean.LocationBusiness;
import jsw.report.viewBean.Menutable;
import jsw.report.viewBean.PayrollArea;
import jsw.report.viewBean.PersonnelArea;
import jsw.report.viewBean.Stages;
import jsw.report.viewBean.Steps;

public interface MasterService {
	
	public List getFunctionManagement() throws SQLException;
	public boolean addFunction(Function function) throws SQLException;
	public boolean deleteFunction(int id) throws SQLException;
	public Function editFunction(int id) throws SQLException;
	public boolean updateFunction(Function function) throws SQLException;
	
	
	public List getBusiness() throws SQLException;
	public boolean addBusiness(Business business) throws SQLException ;
	public boolean deleteBusiness(int id) throws SQLException;
	public Business editBusiness(int id) throws SQLException;
	public boolean updateBusiness(Business business) throws SQLException;
	
	
	
	
	 public List getBusinessGroupList() throws SQLException; 
	 public boolean addBusinessGroup(BusinessGroup businessGroup) throws SQLException ;
	 public boolean deleteBusinessGroup(int id) throws SQLException;
	 public BusinessGroup editBusinessGroup(int id) throws SQLException;
	 public boolean updateBusinessGroup(BusinessGroup businessGroup) throws SQLException;
	
	
	
	 public List getBusinessGroupLoc() throws SQLException; 
	 public int getBusinessGroupId(int bgid, String locid) throws SQLException;
	 public boolean addBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException ;
	 public boolean deleteBusinessGroupLoc(int bgid, String locid) throws SQLException;
	 public BusinessGroupLoc editBusinessGroupLoc(int bgid, String locid) throws SQLException;
	 public boolean updateBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException;
	 
	 
	 
	 public List getBusinessLoc() throws SQLException; 
	 public int getLocationBusinessId(String bgid, int locid) throws SQLException;
	 public boolean addBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException ;
	 public boolean deleteBusinessLoc(String bgid,int locid) throws SQLException;
	 public LocationBusiness editBusinessLoc(String bgid,int locid) throws SQLException;
	 public boolean updateBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException;
	 
	 
	 
	 
	 public List getLocation() throws SQLException;
	 public boolean addLocation(Location location) throws SQLException ;
	 public Location editLocation(int id) throws SQLException;
	 public boolean deleteLocation(int id) throws SQLException;
	 public boolean updateLocation(Location location) throws SQLException;
	 
	 
	 public List getCaseType() throws SQLException;
	 public boolean addCaseType(CaseType caseType)throws SQLException ;
	 public CaseType editCaseType(int id) throws SQLException;
	 public boolean deleteCaseType(int id) throws SQLException;
	 public boolean updateCaseType(CaseType caseType) throws SQLException;
	 
	 
	 
	 public List getDocumentType() throws SQLException;
	 public boolean addDocumentType(DocumentType documentType)throws SQLException ;
	 public DocumentType editDocumentType(int id) throws SQLException;
	 public boolean deleteDocumentType(int id) throws SQLException;
	 public boolean updateDocumentType(DocumentType documentType) throws SQLException;
	 
	 
	 
	 public List getStageList() throws SQLException; 
		
		
	 public boolean deleteStages(int stageId) throws SQLException;
	 public Stages editStages(int stageId) throws SQLException;
	 public boolean updateStages(Stages stage) throws SQLException;
	 
	 
	 
	 public List getStepList() throws SQLException; 
		
		
	 public boolean deleteSteps(int stepId) throws SQLException;
	 public Steps editSteps(int stepId) throws SQLException;
	 public boolean updateSteps(Steps steps) throws SQLException;
	 
	 
	 
	 public List getBusinessRolesList() throws SQLException;
	 public boolean deleteBusiRole(int busiRoleId) throws SQLException;
	 public BusinessRoles editBusiRole(int busiRoleId) throws SQLException;
	 public boolean updateBusiRole(BusinessRoles busiRole) throws SQLException;
	
	 public List getMenuTable() throws SQLException;
		
		public boolean deleteMenuTable(int id) throws SQLException;
		public Menutable editMenuTable(int id) throws SQLException;
		public boolean updateMenuTable(Menutable menus) throws SQLException;
		
		
		
		
		 public List getPersonnelArea() throws SQLException;
			public boolean deletePersonnelArea(int id) throws SQLException;
			public PersonnelArea editPersonnelArea(int id) throws SQLException;
			public boolean updatePersonnelArea(PersonnelArea personnelarea) throws SQLException;
			
			
			public List getPayrollArea() throws SQLException;
				public boolean deletePayrollArea(int id) throws SQLException;
				public PayrollArea editPayrollArea(int id) throws SQLException;
				public boolean updatePayrollArea(PayrollArea payroll) throws SQLException;
				
				
				
				public List getBusinessGroupBusiness() throws SQLException; 
				 public int getBusinessGroupByBusinessId(int bgid, String Businessid) throws SQLException;
				 public boolean addBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException ;
				 public boolean deleteBusinessGroupBusiness(int bgid, String locid) throws SQLException;
				 public BusinessGroupLoc editBusinessGroupBusiness(int bgid, String locid) throws SQLException;
				 public boolean updateBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException;
								 
			

}
