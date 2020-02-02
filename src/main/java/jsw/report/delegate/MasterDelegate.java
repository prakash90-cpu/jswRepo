package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import jsw.report.service.ApService;
import jsw.report.service.MasterService;
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

public class MasterDelegate {
	
	private MasterService masterService;
	
	
	
	public MasterService getMasterService() {
		return masterService;
	}
	public void setMasterService(MasterService masterService) {
		this.masterService = masterService;
	}
	public List getFunctionManagement() throws SQLException{
		// TODO Auto-generated method stub
		return masterService.getFunctionManagement();
	}
	public boolean addFunction(Function function) throws SQLException{
		return masterService. addFunction(function);
	}
	public boolean deleteFunction(int id) throws SQLException {
		// TODO Auto-generated method stub
		return masterService.deleteFunction(id);
	}
	public Function editFunction(int id) throws SQLException{
		return masterService.editFunction(id);
	}
	public boolean updateFunction(Function function) throws SQLException{
		return masterService.updateFunction(function);
	}
	
	
	public List getBusiness() throws SQLException{
		return masterService.getBusiness();
	}
	public boolean addBusiness(Business business) throws SQLException {
		return masterService.addBusiness(business);
	}
	public boolean deleteBusiness(int id) throws SQLException{
		return masterService.deleteBusiness(id);
	}
	public Business editBusiness(int id) throws SQLException{
		return masterService.editBusiness(id);
	}
	public boolean updateBusiness(Business business) throws SQLException{
		return masterService.updateBusiness(business);
	}
	
	
	
	 public List getBusinessGroupList() throws SQLException{
		 return masterService.getBusinessGroupList();
	 }
	 public boolean addBusinessGroup(BusinessGroup businessGroup) throws SQLException {
		return masterService.addBusinessGroup(businessGroup);
		 
	 }
	 public boolean deleteBusinessGroup(int id) throws SQLException{
		return masterService.deleteBusinessGroup(id);
		 
	 }
	 public BusinessGroup editBusinessGroup(int id) throws SQLException{
		return masterService.editBusinessGroup(id);
		 
	 }
	 public boolean updateBusinessGroup(BusinessGroup businessGroup) throws SQLException{
		 return masterService.updateBusinessGroup(businessGroup); 
	 }
	
	
	
	 
	 public List getBusinessGroupLoc() throws SQLException{
		 return masterService.getBusinessGroupLoc();
	 }
	 public int getBusinessGroupId(int bgid, String locid) throws SQLException{
		 return masterService.getBusinessGroupId(bgid,locid);
	 }
	 public boolean addBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException {
		 return masterService.addBusinessGroupLoc(businessGroupLoc);
	 }
	 public boolean deleteBusinessGroupLoc(int bgid,String locid) throws SQLException{
		 return masterService.deleteBusinessGroupLoc(bgid,locid);
	 }
	 public BusinessGroupLoc editBusinessGroupLoc(int bgid, String locid) throws SQLException{
		 return masterService.editBusinessGroupLoc(bgid,locid);
	 }
	 public boolean updateBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException{
		 return masterService.updateBusinessGroupLoc(businessGroupLoc);
	 }
	 
	 
	 
	 public List getBusinessLoc() throws SQLException{
		 return masterService.getBusinessLoc();
	 }
	 public int getLocationBusinessId(String bgid, int locid) throws SQLException{
		 return masterService.getLocationBusinessId(bgid,locid);
	 }
	 public boolean addBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException {
		 return masterService.addBusinessLoc(businessGroupLoc);
	 }
	 public boolean deleteBusinessLoc(String bgid, int locid) throws SQLException{
		 return masterService.deleteBusinessLoc(bgid,locid);
	 }
	 public LocationBusiness editBusinessLoc(String bgid,int locid) throws SQLException{
		 return masterService.editBusinessLoc(bgid,locid);
	 }
	 public boolean updateBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException{
		 return masterService.updateBusinessLoc(businessGroupLoc);
	 }
	 
	 
	 
	
	
	 public List getLocation() throws SQLException{
		 return masterService.getLocation();
		 
	 }
	 public boolean addLocation(Location location) throws SQLException {
		 return masterService.addLocation(location);
		 
	 }
	 public Location editLocation(int id) throws SQLException{
		 return masterService.editLocation(id);
		 
	 }
	 public boolean deleteLocation(int id) throws SQLException{
		 return masterService.deleteLocation(id);
		 
	 }
	 public boolean updateLocation(Location location) throws SQLException{
		 return masterService.updateLocation(location);
		 
	 }
	
	 
	 
	 public List getCaseType() throws SQLException{
		 return masterService.getCaseType();
	 }
	 public boolean addCaseType(CaseType caseType)throws SQLException {
		 return masterService.addCaseType(caseType);
	 }
	 public CaseType editCaseType(int id) throws SQLException{
		 return masterService.editCaseType(id);
	 }
	 public boolean deleteCaseType(int id) throws SQLException{
		 return masterService.deleteCaseType(id);
	 }
	 public boolean updateCaseType(CaseType caseType) throws SQLException{
		 return masterService.updateCaseType(caseType);
	 }
	 
	 
	 
	
	 
	 
	 public List getStageList() throws SQLException{
		 return masterService.getStageList();
		 
	 }
		
		
	 public boolean deleteStages(int stageId) throws SQLException{
		 return masterService.deleteStages(stageId);
	 }
	 public Stages editStages(int stageId) throws SQLException{
		 return masterService.editStages(stageId);
	 }
	 public boolean updateStages(Stages stage) throws SQLException{
		 return masterService.updateStages(stage);
	 }
	 
	 public List getStepList() throws SQLException{
	 return masterService.getStepList();
	 } 
		
		
	 public boolean deleteSteps(int stepId) throws SQLException{
		 return masterService.deleteSteps(stepId);
	 }
	 public Steps editSteps(int stepId) throws SQLException{
		 return masterService.editSteps(stepId);
	 }
	 public boolean updateSteps(Steps step) throws SQLException{
		 return masterService.updateSteps(step);
	 }
	 
	 
	 public List getBusinessRolesList() throws SQLException{
		 return masterService.getBusinessRolesList();
	 }
	 public boolean deleteBusiRole(int busiRoleId) throws SQLException{
		 return masterService.deleteBusiRole(busiRoleId);
	 }
	 public BusinessRoles editBusiRole(int busiRoleId) throws SQLException{
		 return masterService.editBusiRole(busiRoleId);
	 }
	 public boolean updateBusiRole(BusinessRoles busiRole) throws SQLException{
		 return masterService.updateBusiRole(busiRole);
	 }
	 
	 public List getDocumentType() throws SQLException{
		 return masterService.getDocumentType();
	 }
	 public boolean addDocumentType(DocumentType documentType)throws SQLException {
		 return masterService.addDocumentType(documentType);
	 }
	 public DocumentType editDocumentType(int id) throws SQLException{
		 return masterService.editDocumentType(id);
	 }
	 public boolean deleteDocumentType(int id) throws SQLException{
		 return masterService.deleteDocumentType(id);
	 }
	 public boolean updateDocumentType(DocumentType documentType) throws SQLException{
		 return masterService.updateDocumentType(documentType);
	 }

	 
		public List getMenuTable() throws SQLException{
			return masterService.getMenuTable();
		}
		
		public boolean deleteMenuTable(int id) throws SQLException{
			return masterService.deleteMenuTable(id);
		}
		public Menutable editMenuTable(int id) throws SQLException{
			return masterService.editMenuTable(id);
		}
		public boolean updateMenuTable(Menutable menus) throws SQLException{
			return masterService.updateMenuTable(menus);	
}

		
		
		
		
		
		 public List getPersonnelArea() throws SQLException{
				return masterService.getPersonnelArea();
			}
			public boolean deletePersonnelArea(int id) throws SQLException{
				return masterService.deletePersonnelArea(id);
			}
			public PersonnelArea editPersonnelArea(int id) throws SQLException{
				return masterService.editPersonnelArea(id);
			}
			public boolean updatePersonnelArea(PersonnelArea personnelarea) throws SQLException{
				return masterService.updatePersonnelArea(personnelarea);
			}
			
			
			public List getPayrollArea() throws SQLException{
				return masterService.getPayrollArea();
			}
				public boolean deletePayrollArea(int id) throws SQLException{
					return masterService.deletePayrollArea(id);
				}
				public PayrollArea editPayrollArea(int id) throws SQLException{
					return masterService.editPayrollArea(id);
				}
				public boolean updatePayrollArea(PayrollArea payroll) throws SQLException{
					return masterService.updatePayrollArea(payroll);
				}

	
		
				public List getBusinessGroupBusiness() throws SQLException{
					return masterService.getBusinessGroupBusiness();
				} 
				 public int getBusinessGroupByBusinessId(int bgid, String Businessid) throws SQLException{
						return masterService.getBusinessGroupByBusinessId(bgid,Businessid);
					}
				 public boolean addBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException {
						return masterService.addBusinessGroupBusiness(businessGroupLoc);
					}
				 public boolean deleteBusinessGroupBusiness(int bgid, String locid) throws SQLException{
						return masterService.deleteBusinessGroupBusiness(bgid,locid);
					}
				 public BusinessGroupLoc editBusinessGroupBusiness(int bgid, String locid) throws SQLException{
						return masterService.editBusinessGroupBusiness(bgid,locid);
					}
				 public boolean updateBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException{
						return masterService.updateBusinessGroupBusiness(businessGroupLoc);
					}
			
		
		
}