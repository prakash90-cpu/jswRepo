package jsw.report.viewBean;

public class BusinessUser {
	int id;
	String user;
	String colorName;
	String alias;
	
	
	
	
	public BusinessUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "BusinessUser [id=" + id + ", user=" + user + ", colorName=" + colorName + ", alias=" + alias + "]";
	}
	
	

}
