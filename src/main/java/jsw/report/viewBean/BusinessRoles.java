package jsw.report.viewBean;

public class BusinessRoles {
	int busiRoleId=0;
	String roleName;
	String ldapGroupName;
	String function;
	String aliasName;
	String colorName;
	public BusinessRoles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getBusiRoleId() {
		return busiRoleId;
	}
	public void setBusiRoleId(int busiRoleId) {
		this.busiRoleId = busiRoleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLdapGroupName() {
		return ldapGroupName;
	}
	public void setLdapGroupName(String ldapGroupName) {
		this.ldapGroupName = ldapGroupName;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	@Override
	public String toString() {
		return "BusinessRoles [busiRoleId=" + busiRoleId + ", roleName=" + roleName + ", ldapGroupName=" + ldapGroupName
				+ ", function=" + function + ", aliasName=" + aliasName + ", colorName=" + colorName + "]";
	}
	
	
	
	
	
	
	

}
