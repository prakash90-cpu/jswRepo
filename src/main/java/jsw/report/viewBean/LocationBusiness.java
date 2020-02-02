package jsw.report.viewBean;

public class LocationBusiness {
	int id;
	int locationId;
	String businessId;
	String location,business;
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "LocationBusiness [id=" + id + ", locationId=" + locationId + ", businessId=" + businessId
				+ ", location=" + location + ", business=" + business + "]";
	}
	
	
	
	
	
	

}
