package jsw.report.viewBean;


import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class RegistrationBean implements java.io.Serializable {

	private Integer id;
	private String usename;
	private String password;
	private String name;
	private String email;
	private String gender;

	private String role;
private String screenId;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getUsename() {
	return usename;
}
public void setUsename(String usename) {
	this.usename = usename;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getScreenId() {
	return screenId;
}
public void setScreenId(String screenId) {
	this.screenId = screenId;
}
@Override
public String toString() {
	return "RegistrationBean [id=" + id + ", usename=" + usename
			+ ", password=" + password + ", name=" + name + ", email=" + email
			+ ", gender=" + gender + ", role=" + role + ", screenId="
			+ screenId + "]";
}




	

}