package jsw.report.viewBean;

import java.util.Date;

public class ApplicationSetting {

	
	private int id;
	public String settingName;
	public String settingValue;
	public String isActive, fromDate,ToDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	public String getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
	@Override
	public String toString() {
		return "ApplicationSetting [id=" + id + ", settingName=" + settingName + ", settingValue=" + settingValue
				+ ", isActive=" + isActive + ", fromDate=" + fromDate + ", ToDate=" + ToDate + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
