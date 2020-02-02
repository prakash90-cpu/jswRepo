package jsw.report.viewBean;



public class Function {
	private int id;
	private String functionName;
	private String shortName;
	private String description;
    private String isActive;
	public Function() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Function(int id, String functionname, String shortname, String description, String isActive) {
		super();
		this.id = id;
		this.functionName = functionname;
		this.shortName = shortname;
		this.description = description;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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
	
 
}
