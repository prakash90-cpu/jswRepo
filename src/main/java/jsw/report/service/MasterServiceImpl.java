package jsw.report.service;

import java.sql.SQLException;
import java.util.List;

import jsw.report.dao.ApDao;
import jsw.report.dao.MasterDao;
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

public class MasterServiceImpl implements MasterService {
	private MasterDao masterDao;

	public MasterDao getMasterDao() {
		return masterDao;
	}

	public void setMasterDao(MasterDao masterDao) {
		this.masterDao = masterDao;
	}
	
	
	
	//*******************Master***********************
	
		public List getFunctionManagement() throws SQLException{
			return masterDao.getFunctionManagement();
		}
		public boolean addFunction(Function function) throws SQLException{
			return masterDao.addFunction(function);
		}
		public boolean deleteFunction(int id) throws SQLException{
			return masterDao.deleteFunction(id);
		}
		public Function editFunction(int id) throws SQLException{
			return masterDao.editFunction(id);
		}
		public boolean updateFunction(Function function) throws SQLException{
			return masterDao.updateFunction(function);
		}
		
		
		
		public List getBusiness() throws SQLException{
			return masterDao.getBusiness();
		}
		public boolean addBusiness(Business business) throws SQLException {
			return masterDao.addBusiness(business);
		}
		public boolean deleteBusiness(int id) throws SQLException{
			return masterDao.deleteBusiness(id);
		}
		public Business editBusiness(int id) throws SQLException{
			return masterDao.editBusiness(id);
		}
		public boolean updateBusiness(Business business) throws SQLException{
			return masterDao.updateBusiness(business);
		}
		
		
		
		
		 public List getBusinessGroupList() throws SQLException{
			 return masterDao.getBusinessGroupList();
		 }
		 public boolean addBusinessGroup(BusinessGroup businessGroup) throws SQLException {
			 return masterDao.addBusinessGroup(businessGroup);
		 }
		 public boolean deleteBusinessGroup(int id) throws SQLException{
			 return masterDao.deleteBusinessGroup(id)	;
		 }
		 public BusinessGroup editBusinessGroup(int id) throws SQLException{
			 return masterDao.editBusinessGroup(id);
		 }
		 public boolean updateBusinessGroup(BusinessGroup businessGroup) throws SQLException{
			 return masterDao.updateBusinessGroup(businessGroup);
		 }
		
		
		 
		 
		
		 public List getBusinessGroupLoc() throws SQLException{
			 return masterDao.getBusinessGroupLoc();
		 }
		 public int getLocationBusinessId(String bgid, int locid) throws SQLException{
			 return masterDao.getLocationBusinessId(bgid,locid);
		 }
		 public int getBusinessGroupId(int bgid, String locid) throws SQLException{
			 return masterDao.getBusinessGroupId(bgid,locid);
		 }
		 public boolean addBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException {
			 return masterDao.addBusinessGroupLoc(businessGroupLoc);
		 }
		 public boolean deleteBusinessGroupLoc(int bgid, String locid) throws SQLException{
			 return masterDao.deleteBusinessGroupLoc(bgid,locid);
		 }
		 public BusinessGroupLoc editBusinessGroupLoc(int bgid,String locid) throws SQLException{
			 return masterDao.editBusinessGroupLoc(bgid,locid);
		 }
		 public boolean updateBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException{
			 return masterDao.updateBusinessGroupLoc(businessGroupLoc);
		 }
		 
		 
		 
		 public List getBusinessLoc() throws SQLException{
			 return masterDao.getBusinessLoc();
		 }
		 public boolean addBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException {
			 return masterDao.addBusinessLoc(businessGroupLoc);
		 }
		 public boolean deleteBusinessLoc(String bgid, int locid) throws SQLException{
			 return masterDao.deleteBusinessLoc(bgid,locid);
		 }
		 public LocationBusiness editBusinessLoc(String bgid, int locid) throws SQLException{
			 return masterDao.editBusinessLoc(bgid,locid);
		 }
		 public boolean updateBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException{
			 return masterDao.updateBusinessLoc(businessGroupLoc);
		 }
		 
		 
		 
		 
		
		
		public List getLocation() throws SQLException{
			 return masterDao.getLocation();
			 
		 }
		 public boolean addLocation(Location location) throws SQLException {
			 return masterDao.addLocation(location);
			 
		 }
		 public Location editLocation(int id) throws SQLException{
			 return masterDao.editLocation(id);
			 
		 }
		 public boolean deleteLocation(int id) throws SQLException{
			 return masterDao.deleteLocation(id);
			 
		 }
		 public boolean updateLocation(Location location) throws SQLException{
			 return masterDao.updateLocation(location);
			 
		 }
		
		 
		 
		 public List getCaseType() throws SQLException{
			 return masterDao.getCaseType();
		 }
		 public boolean addCaseType(CaseType caseType)throws SQLException {
			 return masterDao.addCaseType(caseType);
		 }
		 public CaseType editCaseType(int id) throws SQLException{
			 return masterDao.editCaseType(id);
		 }
		 public boolean deleteCaseType(int id) throws SQLException{
			 return masterDao.deleteCaseType(id);
		 }
		 public boolean updateCaseType(CaseType caseType) throws SQLException{
			 return masterDao.updateCaseType(caseType);
		 }
		 
		 
		 
		 
		 public List getDocumentType() throws SQLException{
			 return masterDao.getDocumentType();
		 }
		 public boolean addDocumentType(DocumentType documentType)throws SQLException {
			 return masterDao.addDocumentType(documentType);
		 }
		 public DocumentType editDocumentType(int id) throws SQLException{
			 return masterDao.editDocumentType(id);
		 }
		 public boolean deleteDocumentType(int id) throws SQLException{
			 return masterDao.deleteDocumentType(id);
		 }
		 public boolean updateDocumentType(DocumentType documentType) throws SQLException{
			 return masterDao.updateDocumentType(documentType);
		 }
		
		 public List getStageList() throws SQLException{
			 return masterDao.getStageList();
		 }
		 
		 public boolean deleteStages(int stageId) throws SQLException{
			 return masterDao.deleteStages(stageId);
		 }
		 public Stages editStages(int stageId) throws SQLException{
			 return masterDao.editStages(stageId);
		 }
		 public boolean updateStages(Stages stage) throws SQLException{
			 return masterDao.updateStages(stage);
		 }
		 
		 
		 public List getStepList() throws SQLException{
			 return masterDao.getStepList();
		 }
		 public boolean deleteSteps(int stepId) throws SQLException{
			 return masterDao.deleteSteps(stepId);
		 }
		 public Steps editSteps(int stepId) throws SQLException{
			 return masterDao.editSteps(stepId);
		 }
		 public boolean updateSteps(Steps step) throws SQLException{
			 return masterDao.updateSteps(step);
		 }
		 
		 
		 
		 public List getBusinessRolesList() throws SQLException{
			 return masterDao.getBusinessRolesList();
		 }
		 public boolean deleteBusiRole(int busiRoleId) throws SQLException{
			 return masterDao.deleteBusiRole(busiRoleId);
		 }
		 public BusinessRoles editBusiRole(int busiRoleId) throws SQLException{
			 return masterDao.editBusiRole(busiRoleId);
		 }
		 public boolean updateBusiRole(BusinessRoles busiRole) throws SQLException{
			 return masterDao.updateBusiRole(busiRole);
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 

			public List getMenuTable() throws SQLException{
				return masterDao.getMenuTable();
			}
			
			public boolean deleteMenuTable(int id) throws SQLException{
				return masterDao.deleteMenuTable(id);
			}
			public Menutable editMenuTable(int id) throws SQLException{
				return masterDao.editMenuTable(id);
			}
			public boolean updateMenuTable(Menutable menus) throws SQLException{
				return masterDao.updateMenuTable(menus);
			}
			
			
			
			
			 public List getPersonnelArea() throws SQLException{
					return masterDao.getPersonnelArea();
				}
				public boolean deletePersonnelArea(int id) throws SQLException{
					return masterDao.deletePersonnelArea(id);
				}
				public PersonnelArea editPersonnelArea(int id) throws SQLException{
					return masterDao.editPersonnelArea(id);
				}
				public boolean updatePersonnelArea(PersonnelArea personnelarea) throws SQLException{
					return masterDao.updatePersonnelArea(personnelarea);
				}
				
				
				public List getPayrollArea() throws SQLException{
					return masterDao.getPayrollArea();
				}
					public boolean deletePayrollArea(int id) throws SQLException{
						return masterDao.deletePayrollArea(id);
					}
					public PayrollArea editPayrollArea(int id) throws SQLException{
						return masterDao.editPayrollArea(id);
					}
					public boolean updatePayrollArea(PayrollArea payroll) throws SQLException{
						return masterDao.updatePayrollArea(payroll);
					}

					public List getBusinessGroupBusiness() throws SQLException{
						return masterDao.getBusinessGroupBusiness();
					} 
					 public int getBusinessGroupByBusinessId(int bgid, String Businessid) throws SQLException{
							return masterDao.getBusinessGroupByBusinessId(bgid,Businessid);
						}
					 public boolean addBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException {
							return masterDao.addBusinessGroupBusiness(businessGroupLoc);
						}
					 public boolean deleteBusinessGroupBusiness(int bgid, String locid) throws SQLException{
							return masterDao.deleteBusinessGroupBusiness(bgid,locid);
						}
					 public BusinessGroupLoc editBusinessGroupBusiness(int bgid, String locid) throws SQLException{
							return masterDao.editBusinessGroupBusiness(bgid,locid);
						}
					 public boolean updateBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException{
							return masterDao.updateBusinessGroupBusiness(businessGroupLoc);
						}
						
			
			
			
		 //************************End*******************
	
}
