package jsw.report.viewBean;



public class Stages {
	private int id=0;
	private String function;
	private String process;
	private String stageName;
    private String queName;
    private String stepName;
    private String response;
    private String color;
    private String aliasName;
    private String stage;
    
    
    
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Stages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getQueName() {
		return queName;
	}
	public void setQueName(String queName) {
		this.queName = queName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	@Override
	public String toString() {
		return "Stages [id=" + id + ", function=" + function + ", process=" + process + ", stageName=" + stageName
				+ ", queName=" + queName + ", stepName=" + stepName + ", color=" + color + ", aliasName=" + aliasName
				+ "]";
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
	
	
	
	
    
    
	
 
}
