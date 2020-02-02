package jsw.report.viewBean;



public class Location {
	private int locationId=0;
	private String locationName;
	private String locationShortName;
	private String description;
    private String isActive;
    String color;
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationShortName() {
		return locationShortName;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
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
	public Location(int locationId, String locationName, String locationShortName, String description, String isActive) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationShortName = locationShortName;
		this.description = description;
		this.isActive = isActive;
	}
	
	
	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", locationShortName=" + locationShortName
				+ ", description=" + description + ", isActive=" + isActive + "]";
	}
	
	
	
	
 
}
