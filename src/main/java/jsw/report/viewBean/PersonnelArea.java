package jsw.report.viewBean;

public class PersonnelArea {
	private int id=0;
	private String personnellAreaName;
	private String shortName;
	private String businessgroupId;
	private String businessId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPersonnellAreaName() {
		return personnellAreaName;
	}
	public void setPersonnellAreaName(String personnellAreaName) {
		this.personnellAreaName = personnellAreaName;
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
		return "PayrollArea [id=" + id + ", personnellAreaName="
				+ personnellAreaName + ", shortName=" + shortName
				+ ", businessgroupId=" + businessgroupId + ", businessId="
				+ businessId + "]";
	}
	
}
