package jsw.report.viewBean;



public class CaseType {
	private int id=0;
	private String caseType;
	
	private String caseName;
	private String description;
	private String functions;
    private String isActive;
	public CaseType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getFunctions() {
		return functions;
	}



	public void setFunctions(String functions) {
		this.functions = functions;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
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
	public CaseType(int id, String caseType, String caseName, String description, String isActive) {
		super();
		this.id = id;
		this.caseType = caseType;
		this.caseName = caseName;
		this.description = description;
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "CaseType [id=" + id + ", caseType=" + caseType + ", caseName=" + caseName + ", description="
				+ description + ", isActive=" + isActive + "]";
	}
 
}
