package jsw.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import jsw.report.dao.UserDao;
import jsw.report.viewBean.FunctionApplication;
import jsw.report.viewBean.Menutable;
import jsw.report.viewBean.RegistrationBean;
import jsw.report.viewBean.UserFilter;


public class UserDaoImpl implements UserDao {


	DataSource dataSource;
	
	@Autowired
	private  Properties tableConfig;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String isValidUser(String username) throws SQLException {
		String query = "Select role from users where username = ?";
		String myid =null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			//pstmt.setString(2, password);

			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {

				 myid = resultSet.getString(1);
				System.out.println(myid);
				
			} else
				myid= "0";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		pstmt.close();conn.close();
		}
		return myid;
	}

	public String getScreenID(String role) throws SQLException {
		String query = "Select screenId from role where role_name=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		String myid=null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, role);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {

				 myid = resultSet.getString(1);
				System.out.println(myid);
				
			} else
				myid= "0";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		pstmt.close();
		conn.close();
		}
		return myid;
	}
	
	
	public List getMenuNames(String role) throws SQLException{
		String query = "select * from "+ tableConfig.getProperty("menutable_dev") +" where menu_id in ("+role+")";
		List MenuNames = null;
		Connection conn=null;
		PreparedStatement pstmt =null;
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			
			ResultSet resultSet = pstmt.executeQuery();
			MenuNames = new ArrayList();
			while(resultSet.next()){
				Menutable menu=new Menutable();
				menu.setMenuId(resultSet.getInt("menu_id"));
				menu.setMenuName(resultSet.getString("menu_name"));
				menu.setSubMenu(resultSet.getString("sub_menu"));
				menu.setIsleaf(resultSet.getString("isleaf"));
				menu.setAction(resultSet.getString("action"));
				menu.setHref(resultSet.getString("href"));
				MenuNames.add(menu);
				
				
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pstmt.close();
			conn.close();
		}
		
		return MenuNames;
	}
	
	
	public boolean insertUser(String usename, String password, String name, String email, String gender,
			String nationality, String passportNo, String issueDate, String expiryDate, String actor)
			throws SQLException {
		// TODO Auto-generated method stub
		int row = 0;
		String sql = "insert into users(username,password,name,email,Gender,Nationality,Passport_No,Issue_Date,Actor) values(?,?,?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1, usename);
			p.setString(2, password);
			p.setString(3, name);
			p.setString(4, email);
			p.setString(5, gender);
			p.setString(6, nationality);
			p.setString(7, passportNo);
			p.setString(8, issueDate);

			p.setString(10, actor);
			row = p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
finally{
		p.close();
		conn.close();
}
		
		if (row > 0)
			return true;
		else
			return false;

	}

	

	public RegistrationBean getProfile(String username) throws SQLException {
		System.out.println(username);
		String query = "Select * from users where username = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		RegistrationBean lg = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet resultSet = pstmt.executeQuery();
			lg = new RegistrationBean();
			if (resultSet.next()) {
				System.out.println(resultSet.getString("username"));
				lg.setId(resultSet.getInt("id"));
				lg.setUsename(resultSet.getString("username"));
				lg.setName(resultSet.getString("name"));
				lg.setEmail(resultSet.getString("email"));
				lg.setGender(resultSet.getString("Gender"));
				//lg.setNationality(resultSet.getString("Nationality"));
				//lg.setPassportNo(resultSet.getString("Passport_No"));
			//	lg.setIssueDate(resultSet.getString("Issue_Date"));

				//lg.setActor(resultSet.getString("Actor"));
				System.out.println("Regissss" + lg);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
finally{
		pstmt.close();
		conn.close();
}
		return lg;

	}
	
	

	

	
	
	 
	 
	 

	 
	  public List getBusinessGroup(int user_id) throws SQLException{
			 
		  
		  String query = " select business_filter from user_filter where user_id="+user_id+"  ";

			Connection conn = null;
			PreparedStatement pstmt = null;
			List<String> businessList = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				businessList = new ArrayList();

				while (resultSet.next()) {
					
					query = "select distinct businessGroup from business b,businessGroup bg, businessGroup_business bgb where b.id=bgb.Business_id and bg.id=bgb.businessGroup_id and b.id in( '"+resultSet.getString("business_filter").replace(",", "','")+"' ) ";
					PreparedStatement pstmt2 = conn.prepareStatement(query);
					ResultSet resultSet2 = pstmt2.executeQuery();
					
					while (resultSet2.next()) {
						
					businessList.add(resultSet2.getString("businessGroup"));
					
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();conn.close();
			}
			return businessList;
			
		}
	

	  
	  public int getSettingValue() throws SQLException{
			String query = "select * from application_settings where isActive='y'";

			Connection conn = null;
			PreparedStatement pstmt = null;
			int selected = 0;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				selected = 0;

				while (resultSet.next()) {
					


					selected=Integer.parseInt(resultSet.getString("setting_value"));

				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();conn.close();
			}
			return selected;
			
		}
	  
	  
		public String getUserPassword(String userName) throws SQLException{
			String userPass = null;
			String query = "select password from users where username=?";
			System.out.println(dataSource);
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, userName);
				ResultSet resultSet = pstmt.executeQuery();

				
				while (resultSet.next()) {
					userPass=resultSet.getString("password");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
finally{
			pstmt.close();conn.close();
}
			return userPass;

			
			
		}
		
	  
	  
	  
	  
	  
}