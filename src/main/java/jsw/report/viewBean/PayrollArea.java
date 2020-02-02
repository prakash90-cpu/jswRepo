package jsw.report.viewBean;

public class PayrollArea {

	private int id=0;
	private String payrollAreaName;
	private String shortName;
	private String businessgroupId;
	private String businessId;
	private String personnelAreaId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getBusinessgroupId() {
		return businessgroupId;
	}
	public void setBusinessgroupId(String businessgroupId) {
		this.businessgroupId = businessgroupId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	@Override
	public String toString() {
		return "PayrollArea [id=" + id + ", payrollAreaName="
				+ payrollAreaName + ", shortName=" + shortName
				+ ", businessgroupId=" + businessgroupId + ", businessId="
				+ businessId + "]";
	}
	public String getPayrollAreaName() {
		return payrollAreaName;
	}
	public void setPayrollAreaName(String payrollAreaName) {
		this.payrollAreaName = payrollAreaName;
	}
	public String getPersonnelAreaId() {
		return personnelAreaId;
	}
	public void setPersonnelAreaId(String personnelAreaId) {
		this.personnelAreaId = personnelAreaId;
	}
	
	
}
