package jsw.report.viewBean;

import java.io.Serializable;
import java.sql.Date;

public class FunctionAppBean implements Serializable {

	private int id;
	private String Functions,businessGroup,Business="null",Location="null",DisplayBy,Roles,Users,CaseType,DocType,PersonnelArea="null",PayrollArea="null";
	private String fromDate,toDate,CaseStatus,barCode,PrStatus,AgingBy,usergroup,type;
	private String userId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public String getFunctions() {
		return Functions;
	}
	public void setFunctions(String function) {
		Functions = function;
	}
	public String getBusiness() {
		return Business;
	}
	public void setBusiness(String business) {
		Business = business;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getDisplayBy() {
		return DisplayBy;
	}
	public void setDisplayBy(String displayBy) {
		DisplayBy = displayBy;
	}
	public String getRoles() {
		return Roles;
	}
	public void setRoles(String roles) {
		Roles = roles;
	}
	public String getUsers() {
		return Users;
	}
	public void setUsers(String users) {
		Users = users;
	}
	public String getCaseType() {
		return CaseType;
	}
	public void setCaseType(String caseType) {
		CaseType = caseType;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPersonnelArea() {
		return PersonnelArea;
	}
	public void setPersonnelArea(String personnelArea) {
		PersonnelArea = personnelArea;
	}
	public String getPayrollArea() {
		return PayrollArea;
	}
	public void setPayrollArea(String payrollArea) {
		PayrollArea = payrollArea;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusinessGroup() {
		return businessGroup;
	}
	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}
	public String getCaseStatus() {
		return CaseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		CaseStatus = caseStatus;
	}
	
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getPrStatus() {
		return PrStatus;
	}
	public void setPrStatus(String prStatus) {
		PrStatus = prStatus;
	}

	public String getAgingBy() {
		return AgingBy;
	}
	public void setAgingBy(String agingBy) {
		AgingBy = agingBy;
	}
	@Override
	public String toString() {
		return "FunctionAppBean [id=" + id + ", Functions=" + Functions
				+ ", businessGroup=" + businessGroup + ", Business=" + Business
				+ ", Location=" + Location + ", DisplayBy=" + DisplayBy
				+ ", Roles=" + Roles + ", Users=" + Users + ", CaseType="
				+ CaseType + ", DocType=" + DocType + ", PersonnelArea="
				+ PersonnelArea + ", PayrollArea=" + PayrollArea
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", CaseStatus=" + CaseStatus + ", barCode=" + barCode
				+ ", PrStatus=" + PrStatus + ", AgingBy=" + AgingBy
				+ ", userId=" + userId + "]";
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	


	
	
	
	
}
