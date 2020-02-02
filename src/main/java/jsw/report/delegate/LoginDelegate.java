package jsw.report.delegate;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;

import jsw.report.service.UserService;
import jsw.report.viewBean.RegistrationBean;

public class LoginDelegate {
	private UserService userService;

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getMenuNames(String role) throws SQLException{
		return userService.getMenuNames(role);
	}
	public String isValidUser(String username) throws SQLException {
		return userService.isValidUser(username);
	}

	public String getScreenID(String role) throws SQLException {
		return userService.getScreenID(role);
	}

	public boolean insertUser(String usename, String password, String name, String email, String gender,
			String nationality, String passportNo, String issueDate, String expiryDate, String actor)
			throws SQLException {
		return userService.insertUser(usename, password, name, email, gender, nationality, passportNo, issueDate,
				expiryDate, actor);
	}

	
	public RegistrationBean getProfile(String username) throws SQLException {
		return userService.getProfile(username);
	}
	


	 
	 
	  public List getBusinessGroup(int id) throws SQLException{
 			// TODO Auto-generated method stub
 			return userService.getBusinessGroup(id);
 		}
	  public int getSettingValue() throws SQLException{
			// TODO Auto-generated method stub
			return userService.getSettingValue();
		}

	  public String getUserPassword(String userName) throws SQLException{
			return userService.getUserPassword(userName);
		}
		
	  
	  
}
