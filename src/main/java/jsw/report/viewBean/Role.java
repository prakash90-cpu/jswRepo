package jsw.report.viewBean;
// default package
// Generated Dec 30, 2016 2:58:05 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "volkswagen")
public class Role implements java.io.Serializable {

	private Integer id=0;
	private String roleName;
	private String roleDescription;
	private String isActive;
	private String screenId="null";

	public Role() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public Role(Integer id, String roleName, String roleDescription, String isActive, String screenId) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.isActive = isActive;
		this.screenId = screenId;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription + ", isActive="
				+ isActive + ", screenId=" + screenId + "]";
	}

	

}
