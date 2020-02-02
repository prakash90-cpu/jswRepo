package jsw.report.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;

import jsw.report.viewBean.RegistrationBean;

/**
 * @author CENTAUR This interface will be used to communicate with the Database
 */
public interface UserDao {
	public String isValidUser(String username) throws SQLException;

	public boolean insertUser(String usename, String password, String name, String email, String gender,
			String nationality, String passportNo, String issueDate, String expiryDate, String actor)
			throws SQLException;


	public RegistrationBean getProfile(String username) throws SQLException;

	public String getScreenID(String role) throws SQLException;

	public List getMenuNames(String role) throws SQLException;
	

	 
	 
	 public List getBusinessGroup(int id) throws SQLException;
	 
	 public int getSettingValue() throws SQLException;
	
	 public String getUserPassword(String userName) throws SQLException;
	 
	 
}
