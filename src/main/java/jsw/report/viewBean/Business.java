package jsw.report.viewBean;



public class Business {
	private int id=0;
	private String businessName;
	private String shortName;
	private String description;
    private String isActive;
	public Business() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Business(int id, String businessName, String shortName, String description, String isActive) {
		super();
		this.id = id;
		this.businessName = businessName;
		this.shortName = shortName;
		this.description = description;
		this.isActive = isActive;
	}


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
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
 
}
