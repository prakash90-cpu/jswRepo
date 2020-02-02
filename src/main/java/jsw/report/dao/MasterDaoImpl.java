package jsw.report.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jsw.report.viewBean.Business;
import jsw.report.viewBean.BusinessGroup;
import jsw.report.viewBean.BusinessGroupLoc;
import jsw.report.viewBean.BusinessRoles;
import jsw.report.viewBean.CaseType;
import jsw.report.viewBean.DocumentType;
import jsw.report.viewBean.Function;
import jsw.report.viewBean.Location;
import jsw.report.viewBean.LocationBusiness;
import jsw.report.viewBean.Menutable;
import jsw.report.viewBean.PayrollArea;
import jsw.report.viewBean.PersonnelArea;
import jsw.report.viewBean.Role;
import jsw.report.viewBean.Stages;
import jsw.report.viewBean.Steps;

public class MasterDaoImpl implements MasterDao {
	
DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	/*@Autowired
	@Qualifier(value = "dbServer")
	private SessionFactory sessionFactory;*/
	@Autowired
	private  Properties tableConfig;
	
	

	
	
	
	
	//************************Master**********************
		public Role editRole(int id) throws SQLException{
			String query = "select * from role where id=?";
			System.out.println(dataSource);
			Connection conn = null;
			PreparedStatement pstmt = null;
			Role role = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet resultSet = pstmt.executeQuery();

				role = new Role();
				while (resultSet.next()) {
					role.setId(resultSet.getInt(1));
					role.setRoleName(resultSet.getString(2));

					role.setRoleDescription(resultSet.getString(3));
					role.setIsActive(resultSet.getString(4));
					role.setScreenId(resultSet.getString(5));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
finally{
			pstmt.close();conn.close();
}
			
			return role;

		}
		public boolean deleteRole(int id) throws SQLException{
			int row = 0;
			String sql = "delete from role where id=?";
			Connection conn = null;
			PreparedStatement p = null;
			try {
				conn = dataSource.getConnection();
				p = conn.prepareStatement(sql);
				p.setInt(1, id);

				row = p.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			p.close();conn.close();
			}
			if (row > 0)
				return true;
			else
				return false;

		}


		public List getFunctionManagement() throws SQLException{

			String query = "select * from  functions";
			Connection conn = null;
			PreparedStatement pstmt = null;
			List<Function> ls = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();


				ls = new ArrayList();

				while (resultSet.next()) {
					Function stg = new Function();

					stg.setId(resultSet.getInt("id"));
					stg.setFunctionName(resultSet.getString("Functions"));
					stg.setShortName(resultSet.getString("Short_Name"));
					stg.setDescription(resultSet.getString("Description"));
					stg.setIsActive(resultSet.getString("isActive"));


					ls.add(stg);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
			return ls;

		}
		public boolean addFunction(Function function) throws SQLException {
			int row = 0;
			System.out.println("comming");
			String sql = "insert into functions(id,Functions,Short_Name,Description,isActive) values(?,?,?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt =null;
			try {
				conn = dataSource.getConnection();
				 pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, function.getId());
				pstmt.setString(2, function.getFunctionName());
				pstmt.setString(3, function.getShortName());
				pstmt.setString(4, function.getDescription());


				pstmt.setString(5, function.getIsActive());
				row =pstmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}

			if (row>0)
				return true;
			else
				return false;
		}
		public boolean deleteFunction(int id) throws SQLException {
			System.out.println("id" + id);
			int row = 0;
			String sql = "delete from functions where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);

				row = pstmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
			if (row > 0)
				return true;
			else
				return false;

		}
		public Function editFunction(int id) throws SQLException {
			String query = "select * from functions where id=?";
			System.out.println(dataSource);
			Connection conn = null;
			PreparedStatement pstmt = null;
			Function function = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet resultSet = pstmt.executeQuery();

				function = new Function();
				while (resultSet.next()) {

					function.setId(resultSet.getInt("id"));
					function.setFunctionName(resultSet.getString("Functions"));
					function.setShortName(resultSet.getString("Short_Name"));
					function.setDescription(resultSet.getString("Description"));
					function.setIsActive(resultSet.getString("isActive"));


				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
			return function;

		}
		public boolean updateFunction(Function function) throws SQLException {

			int row = 0;

			String sql = "update functions set Functions=?,Short_Name=?,Description=?,isActive=? where id=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, function.getFunctionName());
				pstmt.setString(2, function.getShortName());
				pstmt.setString(3, function.getDescription());
				pstmt.setString(4, function.getIsActive());

				pstmt.setInt(5, function.getId());




				row = pstmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
			if (row > 0)
				return true;
			else
				return false;

		}


		//*******************************Business********************
		public List getBusiness() throws SQLException{

			String query = "select * from business";

			Connection conn = null;
			PreparedStatement pstmt = null;
			List<Business> businessList = null;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				businessList = new ArrayList();

				while (resultSet.next()) {
					Business businessLoc = new Business();

					businessLoc.setId(resultSet.getInt("id"));
					businessLoc.setBusinessName(resultSet.getString("CompanyDisplayName"));
					businessLoc.setShortName(resultSet.getString("CompanyValue"));
					businessLoc.setDescription(resultSet.getString("Description"));
					businessLoc.setIsActive(resultSet.getString("isActive"));


					businessList.add(businessLoc);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
			return businessList;

		}
		public boolean addBusiness(Business businessLoc) throws SQLException {
			int row = 0;
			System.out.println("comming");
			String sql = "insert into business(CompanyDisplayName,CompanyValue,Description,isActive) values(?,?,?,?)";
			System.out.println(sql);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

		//	pstmt.setInt(1, businessLoc.getId());
			pstmt.setString(1, businessLoc.getBusinessName());
			pstmt.setString(2, businessLoc.getShortName());
			pstmt.setString(3, businessLoc.getDescription());


			pstmt.setString(4, businessLoc.getIsActive());
			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row>0)
				return true;
			else
				return false;
		}
		public boolean deleteBusiness(int id) throws SQLException {
			System.out.println("id" + id);
			int row = 0;
			String sql = "delete from business where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public Business editBusiness(int id) throws SQLException {
			String query = "select * from business where id=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			Business businessLoc = new Business();
			while (resultSet.next()) {

				businessLoc.setId(resultSet.getInt("id"));
				businessLoc.setBusinessName(resultSet.getString("CompanyDisplayName"));
				businessLoc.setShortName(resultSet.getString("CompanyValue"));
				businessLoc.setDescription(resultSet.getString("Description"));
				businessLoc.setIsActive(resultSet.getString("isActive"));


			}
			pstmt.close();
			conn.close();
			return businessLoc;

		}
		public boolean updateBusiness(Business business) throws SQLException {

			int row = 0;
			System.out.println("kkkk"+business.getBusinessName());

			String sql = "update business set CompanyDisplayName=?,CompanyValue=?,Description=?,isActive=? where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, business.getBusinessName());
			pstmt.setString(2, business.getShortName());
			pstmt.setString(3, business.getDescription());
			pstmt.setString(4, business.getIsActive());

			pstmt.setInt(5, business.getId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;

		}
		//**********************End******************************



		//*********************Business Group*****************

		public List getBusinessGroupList() throws SQLException{
			String query = "select * from businessgroup";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<BusinessGroup> businessGroupList  = new ArrayList();

			while (resultSet.next()) {
				BusinessGroup businessGroup = new BusinessGroup();

				businessGroup.setId(resultSet.getInt("id"));
				businessGroup.setBusinessGroup(resultSet.getString("businessgroup"));
				businessGroup.setDescription(resultSet.getString("description"));
				businessGroup.setIsActive(resultSet.getString("isActive"));



				businessGroupList.add(businessGroup);

			}
			pstmt.close();
			conn.close();

			return businessGroupList;

		}
		public boolean addBusinessGroup(BusinessGroup businessGroup) throws SQLException 
		{
			int row = 0;

			String sql = "insert into businessgroup(businessgroup,description,isActive) values(?,?,?)";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);


			pstmt.setString(1, businessGroup.getBusinessGroup());

			pstmt.setString(2, businessGroup.getDescription());

			pstmt.setString(3, businessGroup.getIsActive());
			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row>0)
				return true;
			else
				return false;

		}
		public boolean deleteBusinessGroup(int id) throws SQLException{
			int row = 0;
			String sql = "delete from businessgroup where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public BusinessGroup editBusinessGroup(int id) throws SQLException{
			String query = "select * from businessgroup where id=?";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			BusinessGroup businessGroup = new BusinessGroup();
			while (resultSet.next()) {

				businessGroup.setId(resultSet.getInt("id"));
				businessGroup.setBusinessGroup(resultSet.getString("businessgroup"));

				businessGroup.setDescription(resultSet.getString("description"));
				businessGroup.setIsActive(resultSet.getString("isActive"));


			}
			pstmt.close();
			conn.close();
			return businessGroup;

		}
		public boolean updateBusinessGroup(BusinessGroup businessGroup) throws SQLException{
			int row = 0;

			String sql = "update businessgroup set businessgroup=?,description=?,isActive=? where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, businessGroup.getBusinessGroup());

			pstmt.setString(2, businessGroup.getDescription());
			pstmt.setString(3, businessGroup.getIsActive());

			pstmt.setInt(4, businessGroup.getId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;

		}

		//**********************End**********************

		//*******************Business Group Loc***************
		public List getBusinessGroupLoc() throws SQLException{

			String query ="select bg.id as bid,bg.businessgroup,l.id as lid,l.locationdisplayname  from location l,businessgroup bg, businessgroup_location bgl where l.id=bgl.location_id and bg.id=bgl.group_id";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<BusinessGroupLoc> businessGroupLOC  = new ArrayList();


			while (resultSet.next()) {
				BusinessGroupLoc businessGroup = new BusinessGroupLoc();


				businessGroup.setBusinessId(resultSet.getInt("bid"));
				businessGroup.setLocId(Integer.toString(resultSet.getInt("lid")));
				businessGroup.setBusinessGroup(resultSet.getString("businessgroup"));
				businessGroup.setLocation(resultSet.getString("LocationDisplayName"));

				//businessGroup.setId(getBusinessGroupId(businessGroup.getBusinessId(), businessGroup.getLocId()));


				businessGroupLOC.add(businessGroup);

			}
			pstmt.close();
			conn.close();

			return businessGroupLOC;

		}

		public int getBusinessGroupId(int bgid, String locid) throws SQLException{
			String []s=locid.split(",");
			BusinessGroupLoc businessGroupLoc = new BusinessGroupLoc();
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String query2 ="select bg_loc_map_id from businessgroup_location where group_id=? and location_id=?";

				Connection conn2=dataSource.getConnection();
				PreparedStatement pstmt2 = conn2.prepareStatement(query2);
				pstmt2.setInt(1, bgid);
				pstmt2.setInt(2, id);
				ResultSet resultSet2 = pstmt2.executeQuery();


				while (resultSet2.next()) {
					System.out.println(resultSet2.getInt("bg_loc_map_id"));


					businessGroupLoc.setId(resultSet2.getInt("bg_loc_map_id"));



				}
				pstmt2.close();

				conn2.close();
			}

			return businessGroupLoc.getId();

		}


		public boolean addBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException {
			int row = 0;
			System.out.println(businessGroupLoc.getLocId());

			String []s=businessGroupLoc.getLocId().split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);

				String sql = "insert into businessgroup_location(group_id,location_id) values(?,?)";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);


				pstmt.setInt(1, businessGroupLoc.getBusinessId());

				pstmt.setInt(2,id);


				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}

			if (row>0)
				return true;
			else
				return false;


		}
		public boolean deleteBusinessGroupLoc(int bgid, String locid) throws SQLException{
			int row = 0;
			String []s=locid.split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String sql = "delete from businessgroup_location where group_id=? and location_id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bgid);
				pstmt.setInt(2, id);

				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}
			if (row > 0)
				return true;
			else
				return false;


		}
		public BusinessGroupLoc editBusinessGroupLoc(int bgid, String locid) throws SQLException{
			int row = 0;
			String []s=locid.split(",");
			BusinessGroupLoc businessGroupLoc = new BusinessGroupLoc();
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String query ="select bg.id as bid,bg.businessgroup,l.id as lid,l.locationdisplayname  from location l,businessgroup bg, businessgroup_location bgl where l.id=? and bg.id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);
				pstmt.setInt(2, bgid);
				ResultSet resultSet = pstmt.executeQuery();


				while (resultSet.next()) {


					businessGroupLoc.setBusinessGroup(resultSet.getString("businessgroup"));
					businessGroupLoc.setLocation(resultSet.getString("LocationDisplayName"));


				}
				businessGroupLoc.setId(getBusinessGroupId( bgid,locid));


				pstmt.close();
				conn.close();






			}
			return businessGroupLoc;


		}
		public boolean updateBusinessGroupLoc(BusinessGroupLoc businessGroupLoc) throws SQLException{
			int row = 0;
			System.out.println(businessGroupLoc.getLocId());
			System.out.println("kkkkkkk"+businessGroupLoc.getId());

			String []s=businessGroupLoc.getLocId().split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);

				String sql = "update  businessgroup_location set group_id=?,location_id=? where bg_loc_map_id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);


				pstmt.setInt(1, businessGroupLoc.getBusinessId());

				pstmt.setInt(2,id);

				pstmt.setInt(3,businessGroupLoc.getId());


				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}

			if (row>0)
				return true;
			else
				return false;


		}
		//*****************End*************************



		//*************************Location Business*******************************

		
		public List getBusinessLoc() throws SQLException {
			String query ="select bg.id as bid,bg.CompanyDisplayName,l.id as lid,l.locationdisplayname  from location l,business bg, location_business bgl where l.id=bgl.location_id and bg.id=bgl.business_id";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<LocationBusiness> businessGroupLOC  = new ArrayList();


			while (resultSet.next()) {
				LocationBusiness businessGroup = new LocationBusiness();


				businessGroup.setBusinessId(Integer.toString(resultSet.getInt("bid")));
				businessGroup.setLocationId(resultSet.getInt("lid"));
				businessGroup.setBusiness(resultSet.getString("CompanyDisplayName"));
				businessGroup.setLocation(resultSet.getString("LocationDisplayName"));




				businessGroupLOC.add(businessGroup);

			}
			pstmt.close();
			conn.close();

			return businessGroupLOC;
		}


		public int getLocationBusinessId(String bgid, int locid) throws SQLException{
			String []s=bgid.split(",");
			LocationBusiness LocationBusiness = new LocationBusiness();
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String query2 ="select loc_mapping_business_Id from location_business where location_id=? and business_id=?";

				Connection conn2=dataSource.getConnection();
				PreparedStatement pstmt2 = conn2.prepareStatement(query2);
				pstmt2.setInt(1, locid);
				pstmt2.setInt(2, id);
				ResultSet resultSet2 = pstmt2.executeQuery();


				while (resultSet2.next()) {
					System.out.println(resultSet2.getInt("loc_mapping_business_Id"));


					LocationBusiness.setId(resultSet2.getInt("loc_mapping_business_Id"));



				}
				pstmt2.close();
				conn2.close();
			}

			return LocationBusiness.getId();

		}


		
		public boolean addBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException {
			int row=0;

			String []s=businessGroupLoc.getBusinessId().split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);


				String sql = "insert into location_business(location_id,business_id) values(?,?)";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, businessGroupLoc.getLocationId());
				pstmt.setInt(2, id);


				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}
			if (row>0)
				return true;
			else
				return false;

		}
		
		public boolean deleteBusinessLoc(String bgid, int locid) throws SQLException {

			int row=0;
			String []s=bgid.split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String sql = "delete from location_business where location_id=? and business_id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, locid);
				pstmt.setInt(2, id);

				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}
			if (row > 0)
				return true;
			else
				return false;
		}
		
		public LocationBusiness editBusinessLoc(String bgid, int locid) throws SQLException {
			int row = 0;
			String []s=bgid.split(",");
			LocationBusiness businessLoc = new LocationBusiness();
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);
				String query="select bg.id as bid,bg.CompanyDisplayName,l.id as lid,l.locationdisplayname  from location l,business bg, location_business bgl where l.id=? and bg.id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, locid);
				pstmt.setString(2, bgid);
				ResultSet resultSet = pstmt.executeQuery();


				while (resultSet.next()) {


					businessLoc.setBusiness(resultSet.getString("CompanyDisplayName"));
					businessLoc.setLocation(resultSet.getString("LocationDisplayName"));


				}
				pstmt.close();
				conn.close();

			}
			return businessLoc;

		}
		
		public boolean updateBusinessLoc(LocationBusiness businessGroupLoc) throws SQLException {
			int row = 0;

			System.out.println("kkkkkkk"+businessGroupLoc.getId());

			String []s=businessGroupLoc.getBusinessId().split(",");
			for(int i=0;i<s.length;i++){
				int id=Integer.parseInt(s[i]);

				String sql = "update  location_business set location_id=?,business_id=? where loc_mapping_business_Id=?";
				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);


				pstmt.setInt(1, businessGroupLoc.getLocationId());

				pstmt.setInt(2,id);

				pstmt.setInt(3,businessGroupLoc.getId());


				row = pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}

			if (row>0)
				return true;
			else
				return false;
		}


		//***************************End**************************************





		//*********************Location**************************

		public List getLocation() throws SQLException{

			String query = "select * from location";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<Location> businessList  = new ArrayList();

			while (resultSet.next()) {
				Location location = new Location();

				location.setLocationId(resultSet.getInt("id"));
				location.setLocationName(resultSet.getString("LocationDisplayName"));
				location.setLocationShortName(resultSet.getString("LocationValue"));
				location.setDescription(resultSet.getString("description"));
				location.setIsActive(resultSet.getString("isActive"));


				businessList.add(location);

			}
			pstmt.close();
			conn.close();

			return businessList;

		}
		public boolean addLocation(Location location) throws SQLException {
			int row = 0;
			System.out.println("comming");
			String sql = "insert into location(locationDisplayName,LocationValue,description,color_name,isActive) values(?,?,?,?,?)";
		System.out.println(sql+"  "+location);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			//pstmt.setInt(1, location.getLocationId());
			pstmt.setString(1, location.getLocationName());
			pstmt.setString(2, location.getLocationShortName());
			pstmt.setString(3, location.getDescription());


			pstmt.setString(4, location.getColor());
			pstmt.setString(5, location.getIsActive());
			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();


			if (row>0)
				return true;
			else
				return false;
		}
		public boolean deleteLocation(int id) throws SQLException {
			System.out.println("id" + id);
			int row = 0;
			String sql = "delete from location where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, id);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public Location editLocation(int id) throws SQLException {
			String query = "select * from location where id=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			Location location = new Location();
			while (resultSet.next()) {

				location.setLocationId(resultSet.getInt("id"));
				location.setLocationName(resultSet.getString("locationDisplayName"));
				location.setLocationShortName(resultSet.getString("LocationValue"));
				location.setDescription(resultSet.getString("description"));
				location.setIsActive(resultSet.getString("isActive"));
				location.setColor(resultSet.getString("color_name"));


			}
			pstmt.close();
			conn.close();
			return location;

		}
		public boolean updateLocation(Location location) throws SQLException {

			int row = 0;

			String sql = "update location set locationDisplayName=?,LocationValue=?,description=?,isActive=?,COLOR_NAME=? where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, location.getLocationName());
			pstmt.setString(2, location.getLocationShortName());
			pstmt.setString(3, location.getDescription());
			pstmt.setString(4, location.getIsActive());
			pstmt.setString(5, location.getColor());

			pstmt.setInt(6, location.getLocationId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;

		}
		//******************End**********************


		//*********************Case Type************************
		public List getCaseType() throws SQLException{

			String query = "select * from casetype";

			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<CaseType> caseTypeList  = new ArrayList();

			while (resultSet.next()) {
				CaseType caseType = new CaseType();

				caseType.setId(resultSet.getInt("id"));
				caseType.setCaseType(resultSet.getString("CaseTypeDisplayName"));
				//caseType.setCaseName(resultSet.getString("CaseIdentifier"));
				caseType.setDescription(resultSet.getString("description"));
				caseType.setFunctions(resultSet.getString("functions"));
				caseType.setIsActive(resultSet.getString("isActive"));


				caseTypeList.add(caseType);

			}
			pstmt.close();
			conn.close();

			return caseTypeList;

		}
		public boolean addCaseType(CaseType caseType) throws SQLException {
			int row = 0;
			System.out.println("comming");
			String sql = "insert into casetype(CaseTypeDisplayName,description,isActive,Functions) values(?,?,?,?)";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			//pstmt.setInt(1, caseType.getId());
			pstmt.setString(1, caseType.getCaseType());
			//pstmt.setString(3, caseType.getCaseName());
		    pstmt.setString(2, caseType.getDescription());


			pstmt.setString(3, caseType.getIsActive());
			pstmt.setString(4, caseType.getFunctions());


			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();


			if (row>0)
				return true;
			else
				return false;
		}
		public boolean deleteCaseType(int id) throws SQLException {
			System.out.println("id" + id);
			int row = 0;
			String sql = "delete from casetype where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, id);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public CaseType editCaseType(int id) throws SQLException {
			String query = "select * from casetype where id=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			CaseType caseType = new CaseType();
			while (resultSet.next()) {

				caseType.setId(resultSet.getInt("id"));
				caseType.setCaseType(resultSet.getString("CaseTypeDisplayName"));
			//	caseType.setCaseName(resultSet.getString("CaseIdentifier"));
				caseType.setDescription(resultSet.getString("description"));
				caseType.setFunctions(resultSet.getString("Functions"));

				caseType.setIsActive(resultSet.getString("isActive"));


			}
			pstmt.close();
			conn.close();
			return caseType;

		}
		public boolean updateCaseType(CaseType caseType) throws SQLException {

			int row = 0;

			String sql = "update casetype set CaseTypeDisplayName=?,description=?,isActive=?,Functions=? where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, caseType.getCaseType());
			//pstmt.setString(2, caseType.getCaseName());
			pstmt.setString(2, caseType.getDescription());
			pstmt.setString(3, caseType.getIsActive());
			pstmt.setString(4, caseType.getFunctions());

			pstmt.setInt(5, caseType.getId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;

		}
		//********************End************************


		public List getStageList() throws SQLException{
			String query = "select * from PROCESS_PERFORMANCE";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<Stages> stageList = new ArrayList();

			while (resultSet.next()) {
				Stages stage = new Stages();

				stage.setId(resultSet.getInt("ID"));
				stage.setFunction(resultSet.getString("FUNCTION"));
				stage.setProcess(resultSet.getString("PROCESS"));
				stage.setStageName(resultSet.getString("STAGENAME"));
				stage.setQueName(resultSet.getString("QUEUENAME"));
				stage.setStepName(resultSet.getString("STEPNAME"));
				stage.setColor(resultSet.getString("COLOR_NAME"));
				stage.setAliasName(resultSet.getString("ALIAS_NAME"));
				stage.setResponse(resultSet.getString("response"));
				stage.setStage(resultSet.getString("stages"));
				stageList.add(stage);

			}
			pstmt.close();
			conn.close();
			return stageList;

		}


		public boolean deleteStages(int stageId) throws SQLException{

			int row = 0;
			String sql = "delete from PROCESS_PERFORMANCE where ID=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, stageId);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public Stages editStages(int stageId) throws SQLException{
			String query = "select * from PROCESS_PERFORMANCE where ID=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, stageId);
			ResultSet resultSet = pstmt.executeQuery();

			Stages stage = new Stages();
			while (resultSet.next()) {
				stage.setId(stageId);
				stage.setFunction(resultSet.getString("FUNCTION"));
				stage.setProcess(resultSet.getString("process"));
				stage.setStageName(resultSet.getString("STAGENAME"));
				stage.setQueName(resultSet.getString("QUEUENAME"));
				stage.setStepName(resultSet.getString("STEPNAME"));
				stage.setColor(resultSet.getString("COLOR_NAME"));
				stage.setAliasName(resultSet.getString("ALIAS_NAME"));
				stage.setResponse(resultSet.getString("response"));
				stage.setStage(resultSet.getString("stages"));

			}
			pstmt.close();
			conn.close();
			return stage;

		}
		public boolean updateStages(Stages stage) throws SQLException{
			System.out.println("comming in dao"+stage.getStageName());
			int row = 0;
			String sql = "";
if(stage.getId()==0)
	sql = "insert into PROCESS_PERFORMANCE (Function,process,stageName,stages,queuename,stepname,response,COLOR_NAME,ALIAS_NAME) values(?,?,?,?,?,?,?,?,?)";

else
 sql = "update PROCESS_PERFORMANCE set Function=?,process=?,stageName=?,stages=?,queuename=?,stepname=?,response=?,COLOR_NAME=?,ALIAS_NAME=? where ID=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, stage.getFunction());
			pstmt.setString(2, stage.getProcess());
			pstmt.setString(3, stage.getStageName());
			pstmt.setString(4, stage.getStage());
			pstmt.setString(5, stage.getQueName());
			pstmt.setString(6, stage.getStepName());
			pstmt.setString(7, stage.getResponse());
			
			pstmt.setString(8, stage.getColor());
			pstmt.setString(9, stage.getAliasName());

			if(!(stage.getId()==0))
			pstmt.setInt(10, stage.getId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;



		}





		public List getStepList() throws SQLException{
			String query = "select * from PROCESS_STEPS";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<Steps> stageList = new ArrayList();

			while (resultSet.next()) {
				Steps step = new Steps();
				step.setStepId(resultSet.getInt("ID"));

				step.setFunctionArea(resultSet.getString("FUNCTION_AREA"));


				step.setAlias(resultSet.getString("ALIAS"));
				step.setStepName(resultSet.getString("STEP_NAME"));
				step.setColor(resultSet.getString("COLOR_NAME"));
				step.setIsActive(resultSet.getString("ISACTIVE"));



				stageList.add(step);

			}
			pstmt.close();
			conn.close();
			return stageList;

		}


		public boolean deleteSteps(int stageId) throws SQLException{

			int row = 0;
			String sql = "delete from PROCESS_STEPS where ID=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, stageId);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public Steps editSteps(int stepId) throws SQLException{
			String query = "select * from PROCESS_STEPS where ID=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, stepId);
			ResultSet resultSet = pstmt.executeQuery();

			Steps steps = new Steps();
			while (resultSet.next()) {
				steps.setStepId(stepId);
				steps.setFunctionArea(resultSet.getString("FUNCTION_AREA"));


				steps.setAlias(resultSet.getString("ALIAS"));
				steps.setStepName(resultSet.getString("STEP_NAME"));
				steps.setColor(resultSet.getString("COLOR_NAME"));
				steps.setIsActive(resultSet.getString("ISACTIVE"));



			}
			pstmt.close();
			conn.close();
			return steps;

		}
		public boolean updateSteps(Steps steps) throws SQLException{
			System.out.println("id is "+steps.getStepId());
			System.out.println("in dao"+steps.getColor());
			int row = 0;

			String sql = "update PROCESS_STEPS set COLOR_NAME=?,ALIAS=? where STEP_NAME=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, steps.getColor());
			pstmt.setString(2, steps.getAlias());

			pstmt.setString(3, steps.getStepName());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;



		}









		//******************Business Roles***************************

		public List getBusinessRolesList() throws SQLException{
			String query = "select * from roles";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<BusinessRoles> busiRoleList = new ArrayList();

			while (resultSet.next()) {
				BusinessRoles busiRole = new BusinessRoles();

				busiRole.setBusiRoleId(resultSet.getInt("ID"));
				busiRole.setRoleName(resultSet.getString("ARROLES"));
				busiRole.setLdapGroupName(resultSet.getString("LDAPGROUPNAME"));
				busiRole.setFunction(resultSet.getString("FUNCTIONS"));
				busiRole.setColorName(resultSet.getString("COLOR_NAME"));
				busiRole.setAliasName(resultSet.getString("ALIAS_NAME"));



				busiRoleList.add(busiRole);

			}
			pstmt.close();
			conn.close();
			return busiRoleList;

		}


		public boolean deleteBusiRole(int busiRoleId) throws SQLException{

			int row = 0;
			String sql = "delete from roles where ID=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, busiRoleId);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public BusinessRoles editBusiRole(int busiRoleId) throws SQLException{
			String query = "select * from roles where ID=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, busiRoleId);
			ResultSet resultSet = pstmt.executeQuery();

			BusinessRoles busiRole = new BusinessRoles();
			while (resultSet.next()) {
				busiRole.setBusiRoleId(resultSet.getInt("ID"));
				busiRole.setRoleName(resultSet.getString("ARROLES"));
				busiRole.setLdapGroupName(resultSet.getString("LDAPGROUPNAME"));
				busiRole.setFunction(resultSet.getString("FUNCTIONS"));
				busiRole.setColorName(resultSet.getString("COLOR_NAME"));
				busiRole.setAliasName(resultSet.getString("ALIAS_NAME"));



			}
			pstmt.close();
			conn.close();
			return busiRole;

		}
		public boolean updateBusiRole(BusinessRoles busiRole) throws SQLException{

			int row = 0;
			String sql ="";
             if(busiRole.getBusiRoleId()==0)
	         sql = "insert into roles (ARROLES,LDAPGROUPNAME,COLOR_NAME,ALIAS_NAME,FUNCTIONS) values(?,?,?,?,?) ";
             else
			 sql = "update roles set ARROLES=?,LDAPGROUPNAME=?,COLOR_NAME=?,ALIAS_NAME=?,FUNCTIONS=? where ID=? ";
			
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, busiRole.getRoleName());
			pstmt.setString(2, busiRole.getLdapGroupName());
			
			pstmt.setString(3, busiRole.getColorName());
			pstmt.setString(4, busiRole.getAliasName());
			
			pstmt.setString(5,busiRole.getFunction());
			 if(!(busiRole.getBusiRoleId()==0))
			pstmt.setInt(6, busiRole.getBusiRoleId());
	
			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;



		}

		
		public List getDocumentType() throws SQLException {
			String query = "select * from docType";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			List<DocumentType> documentTypeList = new ArrayList();

			while (resultSet.next()) {
				DocumentType documentType = new DocumentType();

				documentType.setId(resultSet.getInt("id"));
				documentType.setDocumentType(resultSet.getString("DocType"));
				documentType.setDocumentName(resultSet.getString("docname"));
				documentType.setDescription(resultSet.getString("Description"));
				documentType.setIsActive(resultSet.getString("isActive"));


				documentTypeList.add(documentType);

			}
			pstmt.close();
			conn.close();
			return documentTypeList;

		}

		public boolean addDocumentType(DocumentType documentType) throws SQLException {
			// TODO Auto-generated method stub
			int row = 0;
			String sql = "insert into docType(id,DocType,DocName,Description,isActive) values(?,?,?,?,?)";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, documentType.getId());
			pstmt.setString(2, documentType.getDocumentType());
			pstmt.setString(3, documentType.getDocumentName());
			pstmt.setString(4, documentType.getDescription());
			pstmt.setString(5, documentType.getIsActive());



			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();


			if (row>0)
				return true;
			else
				return false;

		}


		public boolean deleteDocumentType(int id) throws SQLException {
			System.out.println("id" + id);
			int row = 0;
			String sql = "delete from doctype where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement p = conn.prepareStatement(sql);
			p.setInt(1, id);

			row = p.executeUpdate();
			p.close();
			conn.close();
			if (row > 0)
				return true;
			else
				return false;

		}
		public DocumentType editDocumentType(int id) throws SQLException {
			String query = "select * from doctype where id=?";
			System.out.println(dataSource);
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			DocumentType documentType = new DocumentType();
			while (resultSet.next()) {

				documentType.setId(resultSet.getInt("id"));
				documentType.setDocumentType(resultSet.getString("doctype"));
				documentType.setDocumentName(resultSet.getString("docname"));

				documentType.setDescription(resultSet.getString("description"));
				documentType.setIsActive(resultSet.getString("isActive"));


			}
			pstmt.close();
			conn.close();
			return documentType;

		}
		public boolean updateDocumentType(DocumentType documentType) throws SQLException {

			int row = 0;

			String sql = "update doctype set doctype=?,docname=?,description=?,isActive=? where id=?";
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, documentType.getDocumentType());
			pstmt.setString(2, documentType.getDocumentName());
			pstmt.setString(3, documentType.getDescription());
			pstmt.setString(4, documentType.getIsActive());

			pstmt.setInt(5, documentType.getId());




			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();

			if (row > 0)
				return true;
			else
				return false;

		}



		// ***********End******************


		//*******************************MenuTable********************
				public List<Menutable> getMenuTable() throws SQLException{

					String query = "select * from "+ tableConfig.getProperty("menutable_dev");

					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					ResultSet resultSet = pstmt.executeQuery();
System.out.println(query);
					List<Menutable> menusList  = new ArrayList<Menutable>();

					while (resultSet.next()) {
						Menutable menus = new Menutable();

						menus.setMenuId(resultSet.getInt("menu_id"));
						menus.setMenuName(resultSet.getString("menu_name"));
						menus.setSubMenu(resultSet.getString("sub_menu"));
						menus.setIsleaf(resultSet.getString("isleaf"));
						menus.setAction(resultSet.getString("action"));
						menus.setHref(resultSet.getString("href"));

						menusList.add(menus);

					}
					pstmt.close();
					conn.close();

					return menusList;

				}
				
				public boolean deleteMenuTable(int id) throws SQLException {
					System.out.println("id" + id);
					int row = 0;
					String sql = "delete from "+ tableConfig.getProperty("menutable_dev") +" where menu_id=?";
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();
					if (row > 0)
						return true;
					else
						return false;

				}
				public Menutable editMenuTable(int id) throws SQLException {
					String query = "select * from "+ tableConfig.getProperty("menutable_dev") +" where menu_id=?";
					System.out.println(dataSource);
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, id);
					ResultSet resultSet = pstmt.executeQuery();

					Menutable menus = new Menutable();
					while (resultSet.next()) {

						menus.setMenuId(resultSet.getInt("menu_id"));
						menus.setMenuName(resultSet.getString("menu_name"));
						menus.setSubMenu(resultSet.getString("sub_menu"));
						menus.setIsleaf(resultSet.getString("isleaf"));
						menus.setAction(resultSet.getString("action"));
						menus.setHref(resultSet.getString("href"));

					}
					pstmt.close();
					conn.close();
					return menus;

				}
				public boolean updateMenuTable(Menutable menus) throws SQLException {

					int row = 0;
					String sql = "";
                    if(menus.getMenuId()==0)
                	sql = "insert into "+ tableConfig.getProperty("menutable_dev") +" (menu_name,sub_menu,isleaf,action,href) values(?,?,?,?,?)";
                    else
					sql = "update "+ tableConfig.getProperty("menutable_dev") +" set menu_name=?,sub_menu=?,isleaf=?,action=?,href=? where menu_id=?";
				System.out.println(sql+menus);
                    Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, menus.getMenuName());
					pstmt.setString(2, menus.getSubMenu());
					if( menus.getIsleaf()==null)
					pstmt.setString(3, "n");
						else
					pstmt.setString(3, menus.getIsleaf());
					pstmt.setString(4, menus.getAction());
					pstmt.setString(5, menus.getHref());
					if(!(menus.getMenuId()==0))
					pstmt.setInt(6, menus.getMenuId());

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();

					if (row > 0)
						return true;
					else
						return false;

				}
				//**********************End******************************



				
				
				
				
				
				
				//*******************************Personnel********************
				public List<PersonnelArea> getPersonnelArea() throws SQLException{

					String query = "select * from personnelarea ";

					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					ResultSet resultSet = pstmt.executeQuery();
                    System.out.println(query);
					List<PersonnelArea> personnelAreaList  = new ArrayList<PersonnelArea>();

					while (resultSet.next()) {
						PersonnelArea personnelArea = new PersonnelArea();

						personnelArea.setId(resultSet.getInt("id"));
						personnelArea.setPersonnellAreaName(resultSet.getString("personnelAreaDisplayName"));
						personnelArea.setShortName(resultSet.getString("personnelAreaValue"));
						
						
						
						personnelArea.setBusinessgroupId(editBusinessGroup(resultSet.getInt("BusiGrpId")).getBusinessGroup());
						personnelArea.setBusinessId(editBusiness(resultSet.getInt("BusiId")).getShortName());
					
						personnelAreaList.add(personnelArea);

					}
					pstmt.close();
					conn.close();

					return personnelAreaList;

				}
				
				public boolean deletePersonnelArea(int id) throws SQLException {
					System.out.println("id" + id);
					int row = 0;
					String sql = "delete from personnelarea where id=?";
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();
					if (row > 0)
						return true;
					else
						return false;

				}
				public PersonnelArea editPersonnelArea(int id) throws SQLException {
					String query = "select * from personnelarea where id=?";
					System.out.println(dataSource);
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, id);
					ResultSet resultSet = pstmt.executeQuery();

					PersonnelArea personnelArea = new PersonnelArea();
					while (resultSet.next()) {

						personnelArea.setId(resultSet.getInt("id"));
						personnelArea.setPersonnellAreaName(resultSet.getString("personnelAreaDisplayName"));
						personnelArea.setShortName(resultSet.getString("personnelAreaValue"));
						personnelArea.setBusinessgroupId(editBusinessGroup(resultSet.getInt("BusiGrpId")).getBusinessGroup());
						personnelArea.setBusinessId(editBusiness(resultSet.getInt("BusiId")).getBusinessName());
					
						

					}
					pstmt.close();
					conn.close();
					return personnelArea;

				}
				public boolean updatePersonnelArea(PersonnelArea personnel) throws SQLException {

					int row = 0;
					String sql = "";
                    if(personnel.getId()==0)
                	sql = "insert into personnelarea (personnelAreaDisplayName,personnelAreaValue,BusiGrpId,BusiId) values(?,?,?,?)";
                    else
					sql = "update personnelarea set personnelAreaDisplayName=?,personnelAreaValue=?,BusiGrpId=?,BusiId=? where id=?";
				System.out.println(sql+personnel);
                    Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, personnel.getPersonnellAreaName());
					pstmt.setString(2, personnel.getShortName());
					
					pstmt.setInt(3, Integer.parseInt(personnel.getBusinessgroupId()));
						
					pstmt.setInt(4, Integer.parseInt(personnel.getBusinessId()));
				
					if(!(personnel.getId()==0))
					pstmt.setInt(5, personnel.getId());

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();

					if (row > 0)
						return true;
					else
						return false;

				}
				//**********************End******************************


				
				
				
				
				
				
				
				//*******************************Payroll********************
				public List<PayrollArea> getPayrollArea() throws SQLException{

					String query = "select * from PayrollArea";

					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					ResultSet resultSet = pstmt.executeQuery();
System.out.println(query);
					List<PayrollArea> payrollList  = new ArrayList<PayrollArea>();

					while (resultSet.next()) {
						PayrollArea payroll = new PayrollArea();

						payroll.setId(resultSet.getInt("id"));
						payroll.setPayrollAreaName(resultSet.getString("PayrollAreaDisplayName"));
						payroll.setShortName(resultSet.getString("PayrollAreaValue"));
						
						payroll.setBusinessgroupId(editBusinessGroup(resultSet.getInt("BusiGrpId")).getBusinessGroup());
						payroll.setBusinessId(editBusiness(resultSet.getInt("BusiId")).getShortName());
						payroll.setPersonnelAreaId(editPersonnelArea(resultSet.getInt("personnelAreaId")).getPersonnellAreaName());
						
						payrollList.add(payroll);

					}
					pstmt.close();
					conn.close();

					return payrollList;

				}
				
				public boolean deletePayrollArea(int id) throws SQLException {
					System.out.println("id" + id);
					int row = 0;
					String sql = "delete from PayrollArea where id=?";
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();
					if (row > 0)
						return true;
					else
						return false;

				}
				public PayrollArea editPayrollArea(int id) throws SQLException {
					String query = "select * from PayrollArea where id=?";
					System.out.println(dataSource);
					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, id);
					ResultSet resultSet = pstmt.executeQuery();

					PayrollArea payroll = new PayrollArea();
					while (resultSet.next()) {

						payroll.setId(resultSet.getInt("id"));
						payroll.setPayrollAreaName(resultSet.getString("PayrollAreaDisplayName"));
						payroll.setShortName(resultSet.getString("PayrollAreaValue"));
						payroll.setBusinessgroupId(editBusinessGroup(resultSet.getInt("BusiGrpId")).getBusinessGroup());
						payroll.setBusinessId(editBusiness(resultSet.getInt("BusiId")).getBusinessName());
						payroll.setPersonnelAreaId(editPersonnelArea(resultSet.getInt("personnelAreaId")).getPersonnellAreaName());
					
					}
					pstmt.close();
					conn.close();
					return payroll;

				}
				public boolean updatePayrollArea(PayrollArea payroll) throws SQLException {

					int row = 0;
					String sql = "";
                    if(payroll.getId()==0)
                	sql = "insert into PayrollArea (PayrollAreaDisplayName,PayrollAreaValue,BusiGrpId,BusiId,personnelAreaId) values(?,?,?,?,?)";
                    else
					sql = "update PayrollArea set PayrollAreaDisplayName=?,PayrollAreaValue=?,BusiGrpId=?,BusiId=?,personnelAreaId=? where id=?";
			    	System.out.println(sql+payroll);
                    Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, payroll.getPayrollAreaName());
					pstmt.setString(2, payroll.getShortName());
					
					pstmt.setInt(3, Integer.parseInt(payroll.getBusinessgroupId()));
					pstmt.setInt(4,Integer.parseInt(payroll.getBusinessId()));
					pstmt.setInt(5, Integer.parseInt(payroll.getPersonnelAreaId()));
					if(!(payroll.getId()==0))
					pstmt.setInt(6, payroll.getId());

					row = pstmt.executeUpdate();
					pstmt.close();
					conn.close();

					if (row > 0)
						return true;
					else
						return false;

				}
				//**********************End******************************


				
				
				//*******************Business Group Business***************
				public List getBusinessGroupBusiness() throws SQLException{

					String query ="select bg.id as bgid,bg.businessgroup,b.id as bid,b.companyDisplayname  from business b,businessgroup bg, businessgroup_business bgb where b.id=bgb.business_id and bg.id=bgb.businessgroup_id";

					Connection conn=dataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					ResultSet resultSet = pstmt.executeQuery();

					List<BusinessGroupLoc> businessGroupLOC  = new ArrayList();


					while (resultSet.next()) {
						BusinessGroupLoc businessGroup = new BusinessGroupLoc();


						businessGroup.setBusinessId(resultSet.getInt("bgid"));
						businessGroup.setLocId(Integer.toString(resultSet.getInt("bid")));
						businessGroup.setBusinessGroup(resultSet.getString("businessgroup"));
						businessGroup.setLocation(resultSet.getString("companyDisplayName"));

						//businessGroup.setId(getBusinessGroupId(businessGroup.getBusinessId(), businessGroup.getLocId()));


						businessGroupLOC.add(businessGroup);

					}
					pstmt.close();
					conn.close();

					return businessGroupLOC;

				}

				public int getBusinessGroupByBusinessId(int bgid, String locid) throws SQLException{
					String []s=locid.split(",");
					BusinessGroupLoc businessGroupLoc = new BusinessGroupLoc();
					for(int i=0;i<s.length;i++){
						int id=Integer.parseInt(s[i]);
						String query2 ="select bg_loc_map_id from businessgroup_location where group_id=? and location_id=?";

						Connection conn2=dataSource.getConnection();
						PreparedStatement pstmt2 = conn2.prepareStatement(query2);
						pstmt2.setInt(1, bgid);
						pstmt2.setInt(2, id);
						ResultSet resultSet2 = pstmt2.executeQuery();


						while (resultSet2.next()) {
							System.out.println(resultSet2.getInt("bg_loc_map_id"));


							businessGroupLoc.setId(resultSet2.getInt("bg_loc_map_id"));



						}
						pstmt2.close();
						conn2.close();
					}

					return businessGroupLoc.getId();

				}


				public boolean addBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException {
					int row = 0;
					System.out.println(businessGroupLoc.getLocId());

					String []s=businessGroupLoc.getLocId().split(",");
					for(int i=0;i<s.length;i++){
						int id=Integer.parseInt(s[i]);

						String sql = "insert into businessgroup_business(businessgroup_id,business_id) values(?,?)";
						Connection conn=dataSource.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);


						pstmt.setInt(1, businessGroupLoc.getBusinessId());

						pstmt.setInt(2,id);


						row = pstmt.executeUpdate();
						pstmt.close();
						conn.close();
					}

					if (row>0)
						return true;
					else
						return false;


				}
				public boolean deleteBusinessGroupBusiness(int bgid, String locid) throws SQLException{
					int row = 0;
					String []s=locid.split(",");
					for(int i=0;i<s.length;i++){
						int id=Integer.parseInt(s[i]);
						String sql = "delete from businessgroup_business where businessgroup_id=? and business_id=?";
						Connection conn=dataSource.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, bgid);
						pstmt.setInt(2, id);

						row = pstmt.executeUpdate();
						pstmt.close();
						conn.close();
					}
					if (row > 0)
						return true;
					else
						return false;


				}
				public BusinessGroupLoc editBusinessGroupBusiness(int bgid, String locid) throws SQLException{
					int row = 0;
					String []s=locid.split(",");
					BusinessGroupLoc businessGroupLoc = new BusinessGroupLoc();
					for(int i=0;i<s.length;i++){
						int id=Integer.parseInt(s[i]);
						String query ="select bg.id as bid,bg.businessgroup,l.id as lid,l.locationdisplayname  from location l,businessgroup bg, businessgroup_location bgl where l.id=? and bg.id=?";
						Connection conn=dataSource.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, id);
						pstmt.setInt(2, bgid);
						ResultSet resultSet = pstmt.executeQuery();


						while (resultSet.next()) {


							businessGroupLoc.setBusinessGroup(resultSet.getString("businessgroup"));
							businessGroupLoc.setLocation(resultSet.getString("LocationDisplayName"));


						}
						businessGroupLoc.setId(getBusinessGroupId( bgid,locid));


						pstmt.close();
						conn.close();






					}
					return businessGroupLoc;


				}
				public boolean updateBusinessGroupBusiness(BusinessGroupLoc businessGroupLoc) throws SQLException{
					int row = 0;
					System.out.println(businessGroupLoc.getLocId());
					System.out.println("kkkkkkk"+businessGroupLoc.getId());

					String []s=businessGroupLoc.getLocId().split(",");
					for(int i=0;i<s.length;i++){
						int id=Integer.parseInt(s[i]);

						String sql = "update  businessgroup_location set group_id=?,location_id=? where bg_loc_map_id=?";
						Connection conn=dataSource.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);


						pstmt.setInt(1, businessGroupLoc.getBusinessId());

						pstmt.setInt(2,id);

						pstmt.setInt(3,businessGroupLoc.getId());


						row = pstmt.executeUpdate();
						pstmt.close();
						conn.close();
					}

					if (row>0)
						return true;
					else
						return false;


				}
				//*****************End*************************



		//*********************End**********************************


}
