package jsw.report.viewBean;

import java.util.List;

public class CompanyRGBean {
	private String companyName;
	private List<CompanyLocationRGBean> locations;

	public List<CompanyLocationRGBean> getLocations() {
		return locations;
	}

	public void setLocations(List<CompanyLocationRGBean> locations) {
		this.locations = locations;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
