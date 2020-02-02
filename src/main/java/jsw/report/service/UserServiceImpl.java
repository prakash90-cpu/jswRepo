package jsw.report.service;

import java.sql.SQLException;
import java.util.List;
import jsw.report.dao.UserDao;
import jsw.report.service.UserService;
import jsw.report.viewBean.RegistrationBean;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String isValidUser(String username) throws SQLException {
		return userDao.isValidUser(username);
	}

	public String getScreenID(String role) throws SQLException {
		return userDao.getScreenID(role);
	}
	public List getMenuNames(String role) throws SQLException
	{
		return userDao.getMenuNames(role);
		
	}
	public boolean insertUser(String usename, String password, String name, String email, String gender,
			String nationality, String passportNo, String issueDate, String expiryDate, String actor)
			throws SQLException {
		return userDao.insertUser(usename, password, name, email, gender, nationality, passportNo, issueDate,
				expiryDate, actor);
	}


	public RegistrationBean getProfile(String username) throws SQLException {
		return userDao.getProfile(username);
	}
	
	
	
	  public List getBusinessGroup(int id) throws SQLException{
 			// TODO Auto-generated method stub
 			return userDao.getBusinessGroup(id);
 		}
	  
	  public int getSettingValue() throws SQLException{
			// TODO Auto-generated method stub
			return userDao.getSettingValue();
		}

	  public String getUserPassword(String userName) throws SQLException{
			return userDao.getUserPassword(userName);
		}
	  
	  
}
