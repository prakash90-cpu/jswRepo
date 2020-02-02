package jsw.report.viewBean;

import java.util.ArrayList;

public class FunctionApplication {

	
	private String name,selectedValue="n";
	String alias;
	
	public ArrayList value=new ArrayList();
	public ArrayList columnValue=new ArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList getValue() {
		return value;
	}

	public void setValue(ArrayList value) {
		this.value = value;
	}

	public ArrayList getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(ArrayList columnValue) {
		this.columnValue = columnValue;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	
	
	
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "FunctionApplication [name=" + name + ", selectedValue="
				+ selectedValue + ", value=" + value + ", columnValue="
				+ columnValue + "]";
	}
	
		
	
}
