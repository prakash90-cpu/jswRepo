package jsw.report.viewBean;

public class BusinessGroup {
	int id;
	String businessGroup;
	String description;
	String isActive;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusinessGroup() {
		return businessGroup;
	}
	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "BusinessGroup [id=" + id + ", businessGroup=" + businessGroup + ", description=" + description
				+ ", isActive=" + isActive + "]";
	}

	

}
