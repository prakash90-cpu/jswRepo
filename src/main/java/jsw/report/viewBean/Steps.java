package jsw.report.viewBean;

public class Steps {
	int stepId;
	String stepName;
	String functionArea;
	String alias ;
	String color;
	String isActive;
	
	
	
	
	public Steps() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getFunctionArea() {
		return functionArea;
	}
	public void setFunctionArea(String functionArea) {
		this.functionArea = functionArea;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	@Override
	public String toString() {
		return "Steps [stepId=" + stepId + ", stepName=" + stepName + ", functionArea=" + functionArea + ", alias="
				+ alias + ", color=" + color + ", isActive=" + isActive + "]";
	}
	
	
	
	

}
