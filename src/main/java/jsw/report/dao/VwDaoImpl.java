
package jsw.report.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import jsw.report.viewBean.ApplicationSetting;
import jsw.report.viewBean.Business;
import jsw.report.viewBean.BusinessGroup;
import jsw.report.viewBean.BusinessGroupLoc;
import jsw.report.viewBean.BusinessRoles;
import jsw.report.viewBean.CaseType;
import jsw.report.viewBean.DocumentType;
import jsw.report.viewBean.FileList;
import jsw.report.viewBean.Function;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.FunctionApplication;
import jsw.report.viewBean.FunctionValueId;
import jsw.report.viewBean.Location;
import jsw.report.viewBean.LocationBusiness;
import jsw.report.viewBean.RegistrationBean;
import jsw.report.viewBean.Role;
import jsw.report.viewBean.Stages;
import jsw.report.viewBean.Steps;
import jsw.report.viewBean.UserFilter;
import jsw.report.viewBean.functionTable;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.jsw.domain.object.ExtractionProgressDO;
import com.jsw.domain.object.ManualExtTriggerDO;
import com.jsw.transfer.object.ExtractionProcessTO;

public class VwDaoImpl implements VwDao {
	DataSource dataSource;

	@Autowired
	@Qualifier(value = "dbServer")
	private SessionFactory jswSessionFactory;
	
	@Autowired
	private  Properties tableConfig;
	//private static final String FILE_LOCATION = "D:\\properties.properties";


	
	//Master***********************
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
	//**************End****
	
	
	
	
	public ManualExtTriggerDO saveExtTrigger(ExtractionProcessTO extractionProcess) throws SQLException {
		
	
		Session session = null;
		Transaction tx = null;

		ManualExtTriggerDO triggerDo = prepareTriger(extractionProcess);
		
		
		try {
			session = jswSessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(triggerDo);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		 return triggerDo;
	}


	


	private ManualExtTriggerDO prepareTriger(ExtractionProcessTO extractionProcess) throws SQLException {
		ManualExtTriggerDO triggeredDO = new ManualExtTriggerDO();
		triggeredDO.setFunction(extractionProcess.getModule());
		triggeredDO.setStartDate(extractionProcess.getStartDate()+" "+extractionProcess.getStartTime());
		triggeredDO.setProcessType(extractionProcess.getProgressType());
		triggeredDO.setEndDate(extractionProcess.getEndDate()+" "+extractionProcess.getEndTime());
		/*triggeredDO.setEndTime(extractionProcess.getEndTime());
		triggeredDO.setStartTime(extractionProcess.getStartTime());*/
		
		
		return triggeredDO;
	}
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	String SelectedValue[]=new String[12];
	
	
	

	public boolean addExtractionProcess(ExtractionProcessTO extractionProcess) throws SQLException {
		// TODO Auto-generated method stub
		int row = 0;
		
		String sql = "insert into extraction_detail(Function,CASETYPE,StartDate,EndDate,StartTime,ENDTIME) values(?,?,?,?,?,?)";
		Connection conn=dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		
		//pstmt.setString(2, extractionProcess.getSystems());
		pstmt.setString(1, extractionProcess.getModule());
		pstmt.setString(2, extractionProcess.getProgressType());
		pstmt.setString(3, extractionProcess.getStartDate());
		pstmt.setString(4, extractionProcess.getEndDate());
		pstmt.setString(5, extractionProcess.getStartTime());
		pstmt.setString(6, extractionProcess.getEndTime());



		row = pstmt.executeUpdate();
		

		conn.close();


		if (row>0)
			return true;
		else
			return false;

	}



	public List getProgressresult() throws SQLException{
				String query = "select * from extraction_trigger_process";

				Connection conn=dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				List<ExtractionProcessTO> systemList = new ArrayList();

				while (resultSet.next()) {
					ExtractionProcessTO extractionProcess = new ExtractionProcessTO();


					extractionProcess.setModule(resultSet.getString("module_name"));
					extractionProcess.setId(resultSet.getInt("id"));
					extractionProcess.setProgressType(resultSet.getString("casetype"));

					extractionProcess.setStartTime(resultSet.getString("StartTime"));
					extractionProcess.setEndTime(resultSet.getString("EndTime"));
					//extractionProcess.setDateCreated(resultSet.getString("CreatedDate"));
					extractionProcess.setStartDate(resultSet.getString("startdate"));
					extractionProcess.setEndDate(resultSet.getString("enddate"));
					//extractionProcess.setshortName(resultSet.getString("module_name"));
					//extractionProcess.setStatus(resultSet.getString("status"));

					systemList.add(extractionProcess);

				}
               conn.close();
				return systemList;

			}


	public boolean deleteExtraction(ExtractionProcessTO process)
			throws SQLException {
		
		int row = 0;
		
		String sql = "delete from "+process.getModule()+"_"+process.getProgressType()+ " where STARTTIME=? and ENDTIME=? " ;
		Connection conn = null;
		System.out.println("query is :"+sql);
		
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			
			 /*SimpleDateFormat ft =new SimpleDateFormat ("yyyy.MM.dd  hh:mm:ss");
			System.out.println(ft.format(process.getStartDate()+" "+ process.getStartTime()));*/ 
			 
			
			p.setString(1, process.getStartDate()+" "+ process.getStartTime());
			p.setString(2, process.getEndDate()+" "+process.getEndTime());
			System.out.println("start date :"+process.getStartDate()+" "+ process.getStartTime());
			row = p.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		p.close();conn.close();
		if (row > 0)
			return true;
		else
			return false;
	}
	 
	

	//************************End*******************



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

		pstmt.close();conn.close();
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
	
		p.close();conn.close();
		if (row > 0)
			return true;
		else
			return false;

	}







	// *************Add User*************
	public boolean addUser(RegistrationBean regis) throws SQLException {
		// TODO Auto-generated method stub
		int row = 0;
		String sql = "insert into users(username,password,email,role) values(?,?,?,?)";
		Connection conn=dataSource.getConnection();
		PreparedStatement p = conn.prepareStatement(sql);
		p.setString(1, regis.getUsename());
		p.setString(2, regis.getPassword());
		p.setString(3, regis.getEmail());
		p.setString(4, regis.getRole());
		row = p.executeUpdate();

		if (row > 0){
			sql = "insert into user_filter(business_filter,location_filter,personnel_area_filter,payroll_area_filter,user_id)"+
					"values('0','0','0','0',(select id from users where username=?))";

			p = conn.prepareStatement(sql);
			p.setString(1, regis.getUsename());

			row = p.executeUpdate();
			p.close();conn.close();
			return true;
		}	else
			return false;

	}

	// *************edit,update,delete User*************


	public boolean updateUser(RegistrationBean regis) throws SQLException {

		int row = 0;
		String sql = "update users set username=?,password=?,email=?,role=? where id=?";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1, regis.getUsename());
			p.setString(2, regis.getPassword());
			p.setString(3, regis.getEmail());

		
			p.setString(4, regis.getRole());

			p.setInt(5, regis.getId());

			row = p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		p.close();conn.close();
		}if (row > 0)
			return true;
		else
			return false;

	}

	public boolean deleteUser(int id) throws SQLException {

		int row = 0;
		String sql = "delete from users where id=?";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setInt(1, id);

			row = p.executeUpdate();
			if(row>0){
			sql = "delete from user_filter where user_id=?";
			p = conn.prepareStatement(sql);
			p.setInt(1, id);
			p.executeUpdate();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		p.close();conn.close();}
		if (row > 0)
			return true;
		else
			return false;

	}

	public RegistrationBean editUser(int id) throws SQLException {
		String query = "select * from users where id=?";
		System.out.println(query);
		Connection conn = null;
		PreparedStatement pstmt = null;
		RegistrationBean lg = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			lg = new RegistrationBean();
			while (resultSet.next()) {

				lg.setId(resultSet.getInt("id"));
				lg.setUsename(resultSet.getString("username"));
				lg.setPassword(resultSet.getString("password"));
				lg.setEmail(resultSet.getString("email"));


				lg.setRole(resultSet.getString("role"));


			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		System.out.println(lg);

		return lg;

	}


	// **************End ***********





	public List<RegistrationBean> getAllUsers() throws SQLException {
		String query = "select * from users where Active='1'";
		System.out.println(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<RegistrationBean> ls = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			ls = new ArrayList<RegistrationBean>();
			while (resultSet.next()) {
				RegistrationBean lg = new RegistrationBean();
				lg.setUsename(resultSet.getString("username"));
				lg.setPassword(resultSet.getString("password"));
				lg.setName(resultSet.getString("name"));
				lg.setEmail(resultSet.getString("email"));


				lg.setRole(resultSet.getString("role"));
				lg.setId(resultSet.getInt("id"));
				ls.add(lg);
			}
			System.out.println("lllll" + ls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		return ls;

	}

	public JSONArray getMenuTree() throws Exception {
		String query = "select menu_name,menu_id from "+ tableConfig.getProperty("menutable_dev") +" where sub_menu=?";
		System.out.println(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		JSONArray finalJson = null;
		try {
			conn = dataSource.getConnection();

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "null");
			ResultSet resultSet = pstmt.executeQuery();


			JSONArray ls = new JSONArray();
			JSONObject ljs = new JSONObject();
			JSONObject temp = null;
			while (resultSet.next()) {
				temp = new JSONObject();
				temp.put("1zid",  resultSet.getString("menu_id"));
				temp.put("2pid", "null");
				temp.put("3value", resultSet.getString("menu_name"));
				ls.put(temp);

			}
			ljs.put("L1", ls);
			JSONArray ls1 = new JSONArray();

			for (int i = 0; i < ls.length(); i++) {
				// System.out.println(str[size++]+" abcd");
				String query1 = "select menu_name,menu_id from "+ tableConfig.getProperty("menutable_dev") +" where sub_menu=?";
				PreparedStatement pstmt1 = conn.prepareStatement(query1);
				pstmt1.setString(1, new JSONObject(ls.getString(i)).getString("3value"));
				ResultSet resultSet1 = pstmt1.executeQuery();

				JSONObject temp1 = null;
				while (resultSet1.next()) {
					temp1 = new JSONObject();

					temp1.put("1zid", resultSet1.getString("menu_id"));
					temp1.put("2pid", new JSONObject(ls.getString(i)).getString("1zid"));
					temp1.put("3value", resultSet1.getString("menu_name"));
					ls1.put(temp1);
				}
				ljs.put("L2", ls1);
				pstmt1.close();
				resultSet1.close();
			}

			JSONArray ls2 = new JSONArray();
			for (int i = 0; i < ls1.length(); i++) {
				// System.out.println(new
				// JSONObject(ls1.getString(i)).getString("value")+" abcd");
				String query1 = "select menu_name,menu_id from "+ tableConfig.getProperty("menutable_dev") +" where sub_menu=?";
				PreparedStatement pstmt1 = conn.prepareStatement(query1);
				pstmt1.setString(1, new JSONObject(ls1.getString(i)).getString("3value"));
				ResultSet resultSet1 = pstmt1.executeQuery();

				JSONObject temp1 = null;
				while (resultSet1.next()) {
					temp1 = new JSONObject();

					temp1.put("1zid", resultSet1.getString("menu_id"));
					temp1.put("2pid", new JSONObject(ls1.getString(i)).getString("1zid"));
					temp1.put("3value", resultSet1.getString("menu_name"));
					ls2.put(temp1);
				}
				ljs.put("L3", ls2);
				pstmt1.close();
				resultSet1.close();
			}

			finalJson = new JSONArray();

			finalJson.put(ljs);

			System.out.println(ls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		return finalJson;
	}

	public List<String> getUserName() throws SQLException {
		String query = "select * from users";
		System.out.println(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<String> ls = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			ls = new ArrayList<String>();
			while (resultSet.next()) {
				ls.add((String) resultSet.getString("username"));

			}
			System.out.println(ls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		return ls;

	}

	public boolean updateScreenID(RegistrationBean screenID) throws SQLException {

		int row = 0;
		String sql = "update users set role=? where username=?";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1, screenID.getRole());
			p.setString(2, screenID.getUsename());

			row = p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		p.close();conn.close();}
		if (row > 0)
			return true;
		else
			return false;

	}
	/*public boolean updateDashboard(RegistrationBean dashboard) throws SQLException {

		int row = 0;
		String sql = "update users set dashboard=? where username=?";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1, dashboard.getDashboard());
			p.setString(2, dashboard.getUsename());

			row = p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		p.close();conn.close();}
		if (row > 0)
			return true;
		else
			return false;

	}*/
	public boolean addRole(Role role) throws SQLException {

		int row = 0;
		String sql =null;
		if(role.getId()==0){
			sql = "insert into role(role_name,role_description,isActive,screenId) values(?,?,?,?)";

		}
		else{
			sql = "update role set role_name=?,role_description=?,isActive=?,screenId=? where id=?";		
		}

		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1, role.getRoleName());
			p.setString(2, role.getRoleDescription());
			p.setString(3, role.getIsActive());
			p.setString(4, role.getScreenId());
			if(role.getId()!=0){
				p.setInt(5, role.getId());
			}
			row = p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		p.close();conn.close();}
		if (row > 0)
			return true;
		else
			return false;

	}


	public List getRole() throws SQLException {

		/*String query1 = "select * from "+ tableConfig.getProperty("menutable_dev") +";

		PreparedStatement pstmt1 = dataSource.getConnection().prepareStatement(query1);
		ResultSet resultSet1 = pstmt1.executeQuery();
		int count = 0;
		while (resultSet1.next()) {
			count++;
		}
		System.out.println("%%%%%" + count);
		String names[] = new String[count + 1];
		int len = count;
		count = 0;
		resultSet1 = pstmt1.executeQuery();

		while (resultSet1.next()) {

			names[count] = resultSet1.getString("menu_name");
			System.out.println(names[count++]);

		}*/

		String query = "select * from role";
		System.out.println(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Role> ls = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			ls = new ArrayList<Role>();
			while (resultSet.next()) {

				Role lg = new Role();
				lg.setId(resultSet.getInt("id"));
				lg.setRoleName(resultSet.getString("role_name"));
				lg.setRoleDescription(resultSet.getString("role_description"));
				lg.setIsActive(resultSet.getString("isActive"));
				String screenID = resultSet.getString("screenId");

				query = "select * from "+ tableConfig.getProperty("menutable_dev") +" where menu_id in ('"+screenID.replace(",", "','")+"')";

				PreparedStatement pstmt1 = conn.prepareStatement(query);
				ResultSet resultSet1 = pstmt1.executeQuery();
				int count = 0;
				while (resultSet1.next()) {
					if(count==0)
						lg.setScreenId(resultSet1.getString("menu_name"));
					else
						lg.setScreenId(lg.getScreenId()+","+resultSet1.getString("menu_name"));
					count++;

				}
				/*String screen = "";
				String[] ary = screenID.split(",");
				System.out.println(ary.length);
				int first = 0;

				for (int k = 0; k < ary.length; k++) {

					System.out.println(names[Integer.parseInt(ary[k])]);

					if (k == 0)
						screen += names[Integer.parseInt(ary[k])];
					else
						screen += "," + names[Integer.parseInt(ary[k])];

				}*/

				/*yyyy-mm-dd
				 * for(int outerloop=0;outerloop<len;outerloop++) { int temp=0;
				 * for(int k=0;k<ary.length;k++) {
				 * 
				 * System.out.println(k+"$$$$$"+ary[k]);
				 * if(Integer.parseInt(ary[k])==outerloop){
				 * System.out.println("****"+names[k]); temp++; break; }
				 * 
				 * }
				 * 
				 * 
				 * 
				 * System.out.println("@@@@"+screen);
				 */
				//	lg.setScreenId(screen);
				ls.add(lg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		System.out.println(ls);

		return ls;

	}






	public String saveFileToDatabase(String fileName) throws IOException, SQLException {
		Connection conn = null;
		Statement pstmt=null;
		try {
			conn = dataSource.getConnection();
			//conn.setAutoCommit(false);
			String excelFilePath = "D:\\" + fileName;

			fileName = fileName.replace(".", "");
			pstmt = conn.createStatement();

			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

			// XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFFormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
			int numOfSheet = workbook.getNumberOfSheets();
			if (numOfSheet == 0)
				return "no sheets";

			for (int countSheets = 0; countSheets < numOfSheet; countSheets++) {

				String sheetName = workbook.getSheetAt(countSheets).getSheetName();

				/*DatabaseMetaData metadata = dataSource.getConnection().getMetaData();
				ResultSet resultSet;
				resultSet = metadata.getTables(null, null, sheetName, null);
				if (resultSet.next())
					return "sheet already exist in database";*/

				int totalRows = workbook.getSheetAt(countSheets).getPhysicalNumberOfRows();
				if (totalRows == 0)
					return "please remove empty sheet from file";

				String createTableQuery = "create table  " + sheetName + "(id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ";
				int numOfColumn = workbook.getSheetAt(countSheets).getRow(0).getPhysicalNumberOfCells();

				String insertDataQuery = "insert into " + sheetName + "(";
				System.out.println("numOfColumn  "+numOfColumn);
				for (int countCell = 0; countCell < numOfColumn; countCell++) {

					XSSFCell cellValue = workbook.getSheetAt(countSheets).getRow(0).getCell(countCell);
					String strCell2 = "";
					if(cellValue!=null ){
						if (cellValue.getCellType() == cellValue.CELL_TYPE_FORMULA) {
							strCell2 = "" + objFormulaEvaluator.evaluate(cellValue).getNumberValue();
						} else {
							strCell2 = cellValue.getStringCellValue();
						}
					}
					else{

						strCell2 += "column"+countCell ;

					}
					if(countCell==numOfColumn-2||countCell==numOfColumn-3||countCell==4||countCell==3){


						createTableQuery += "," +strCell2.replaceAll("\\s", "").trim()+ " TIMESTAMP ";

					}
					else if (countCell==numOfColumn-1){
						createTableQuery += "," + strCell2.replaceAll("\\s", "").trim() + " varchar(5000) ";

					}
					
					else	
						createTableQuery += "," + strCell2.replaceAll("\\s", "").trim() + " varchar(500) ";



					if (countCell == 0)
						insertDataQuery += strCell2.replaceAll("\\s", "").trim();

					else
						insertDataQuery += "," + strCell2.replaceAll("\\s", "").trim();

				}
				createTableQuery += ")";
				insertDataQuery += ") values(";
				pstmt = conn.createStatement();
				System.out.println("createTableQuery   "+createTableQuery);
			    // pstmt.executeUpdate(createTableQuery);

				int numOfRows = workbook.getSheetAt(countSheets).getPhysicalNumberOfRows();
//int countRow=0;
				for (int countRows = 1; countRows < numOfRows; countRows++) {
					

					
					String insertQuery = insertDataQuery;
					int numOfColumninCurrentRow = workbook.getSheetAt(countSheets).getRow(countRows).getPhysicalNumberOfCells();
					System.out.println("numOfColumninCurrentRow"+numOfColumninCurrentRow+"      countRows "+countRows);
					for (int countCell = 0; countCell < numOfColumn; countCell++) {
						System.out.println(countCell);
						XSSFCell cellValue = workbook.getSheetAt(countSheets).getRow(countRows).getCell(countCell);
						String strCell2 = "";

						if(cellValue!=null ){
							if( cellValue.getCellType()==cellValue.CELL_TYPE_BLANK|| cellValue.getCellType()==cellValue.CELL_TYPE_BOOLEAN|| cellValue.getCellType()==cellValue.CELL_TYPE_ERROR){
								cellValue.setCellType(cellValue.CELL_TYPE_STRING);
							}


							if (cellValue.getCellType() == cellValue.CELL_TYPE_FORMULA) {
								strCell2 = ""+ objFormulaEvaluator.evaluate(cellValue).getNumberValue();
							} 
							else if(cellValue.getCellType() == cellValue.CELL_TYPE_NUMERIC) {

								if(countCell==numOfColumn-2||countCell==numOfColumn-3||countCell==4||countCell==3){

									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



									strCell2 =""+ formatter.format(cellValue.getDateCellValue());
									
									
								}
								else
									strCell2 =""+ cellValue.getNumericCellValue();
							}
							else {
								strCell2 = cellValue.getStringCellValue().replace("'", "\"");
							}

							if (countCell == 0)
							{
								System.out.println("serial number"+strCell2);
								insertQuery += "'" + strCell2 + "'";
							}
							else if(countCell==numOfColumn-2||countCell==numOfColumn-3||countCell==4||countCell==3){
								System.out.println("countCell"+countCell+"  DateValue"+strCell2+"  strCell2.length()"+strCell2.length());

								if(strCell2.isEmpty()||strCell2.length()==0)	
									insertQuery += ",null" ;
								else
									insertQuery += ",'" +strCell2+"'" ;

							}
							else{
								System.out.println(strCell2);
								System.out.println("countCell"+countCell+"  DateValue"+strCell2+"  strCell2.length()"+strCell2.length());

							
								
								insertQuery += ",'" + strCell2 + "'";
							}
						}
						else{
							if (countCell==numOfColumn-2 ||countCell==numOfColumn-3||countCell==4||countCell==3) {
							insertQuery += ",null" ;
							
						}
							else{
								System.out.println(strCell2+"++++++++++");
								insertQuery += ",'" + strCell2 + "'";
								
							}
							
							

						}
					}

					insertQuery += ")";
					System.out.println(countRows);
					System.out.println(insertQuery);

					pstmt = conn.createStatement();
					System.out.println("insertQuery   "+insertQuery);
					pstmt.executeUpdate(insertQuery);
					
					/*if(countRows==numOfRows-1)
						conn.commit();*/

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed to import File. Please check contents of File";
		}finally{
		pstmt.close();conn.close();
		}
		return "success";
	}

	// *********FileName storage to database******


	public boolean updatefileStatus(int id) throws SQLException {
		// TODO Auto-generated method stub
		int row=0;
		String sql = "update  fileAttachement set status=1 where id="+id;
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			row=p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		finally{
		p.close();conn.close();}
		if (row>0)
			return true;
		else
			return false;

	}

	public boolean addFileName(String file,FileList f) throws SQLException {
		// TODO Auto-generated method stub
		int row=0;

		String sql = "insert into fileAttachement(FileName,Status,FileType) values(?,?,?)";
		Connection conn = null;
		PreparedStatement p = null;
		try {
			conn = dataSource.getConnection();
			p = conn.prepareStatement(sql);
			p.setString(1,file);
			p.setInt(2, 0);
			p.setString(3,f.getDocumentType());




			row=p.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		p.close();conn.close();}

		if (row>0)
			return true;
		else
			return false;

	}



	public List getFileList() throws SQLException {
		// TODO Auto-generated method stub
		String query = "select * from fileAttachement";

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<FileList> ls = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			ls = new ArrayList();

			while (resultSet.next()) {
				FileList stg = new FileList();

				stg.setId(resultSet.getInt("id"));
				stg.setFileName(resultSet.getString("FileName"));
				stg.setCreatedTimeStamp(resultSet.getDate("CreatedTimeStamp"));
				stg.setStatus(resultSet.getInt("Status"));
				System.out.println("comming" + stg.getFileName());

				ls.add(stg);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		return ls;

	}

	public String getFileById(int id) throws SQLException {

		String query = "select FileName from fileAttachement where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		String s = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			s = "";

			while (resultSet.next()) {

				s = resultSet.getString(1);
				System.out.println("ooooo" + s);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();conn.close();}
		return s;
	}

	public boolean deleteFileList(int id) throws SQLException {
		System.out.println("id" + id);
		int row = 0;
		String sql = "delete from fileAttachement where id=?";
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
		p.close();conn.close();}
		if (row > 0)
			return true;
		else
			return false;

	}

	// ********* end FileName storage to database******







	






	 
    public List getProcessAccessList() throws SQLException{
		// TODO Auto-generated method stub
		String tableName[]={"Business", "Location","PersonnelArea","PayrollArea"};
		String tableColumn[]={"CompanyDisplayName", "LocationDisplayName" ,"PersonnelAreaDisplayName","PayrollAreaDisplayName"};
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		List list = null;
		try {
			conn = dataSource.getConnection();
				list = new ArrayList();	
			int	selectedCounter=0;
			for(int countTable=0;countTable<tableName.length;countTable++){
			 
			String query = "select * from "+tableName[countTable];
			System.out.println(dataSource);
			
				pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();


			FunctionApplication funName=new FunctionApplication();	
			funName.setName(tableName[countTable]);
			if(selectedCounter<12)
			{
//		System.out.println(SelectedValue[selectedCounter]);
				funName.setSelectedValue(SelectedValue[selectedCounter]);
					}
			selectedCounter++;
			while (resultSet.next()) {

				FunctionValueId value=new FunctionValueId();
				 if(tableName[countTable].equals("Location")){
					value.setId(resultSet.getInt("id"));
					value.setValue(resultSet.getString("LocationValue")+" - "+resultSet.getString(tableColumn[countTable])+"");
					funName.value.add(value);
					
				}
					
				else{
					value.setId(resultSet.getInt("id"));
					value.setValue(resultSet.getString(tableColumn[countTable]));
				funName.value.add(value);
				
				}
				funName.columnValue.add(resultSet.getInt("id"));
			}
			list.add(funName);
			
			}
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();
        conn.close();}
		return list;
	
	}
    
    
public boolean submitUserFilter(FunctionAppBean functionAppBean)  throws SQLException, JSONException {
      		
    	System.out.println(functionAppBean);
    /*	String inserTableDate[]=functionAppBean.getFunctions().split(",");
    	
    	for(int countQuery=0;countQuery<inserTableDate.length;countQuery++){
    		
    	*/	
    	
    	boolean idExist=false;
 	   String query = "select * from user_filter where user_id=(select id from users where username='"+functionAppBean.getUserId()+"')";
 		System.out.println("Existquery"+query);
 		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			//pstmt.setString(1, functionAppBean.getUserId());
			ResultSet resultSet = pstmt.executeQuery();

			
			if(resultSet.next()) {
				idExist=true;
			}
			
			if(idExist)
				 query = "update user_filter set business_filter=?,location_filter=?,personnel_area_filter=?,payroll_area_filter=? where user_id=(select id from users where username=?)";
 
			else
				query = "insert into user_filter(business_filter,location_filter,personnel_area_filter,payroll_area_filter,user_id)"+
				"values(?,?,?,?,?,(select id from users where username=?))";
				

				System.out.println(query);
				 pstmt = conn.prepareStatement(query);
				//pstmt.setString(1, functionAppBean.getFunctions());
				 if(!functionAppBean.getBusiness().isEmpty())
				pstmt.setString(1, functionAppBean.getBusiness());
				 else
						pstmt.setString(1, "0");	 
				
				 if(!functionAppBean.getLocation().isEmpty())
				pstmt.setString(2, functionAppBean.getLocation());
				 else
					 pstmt.setString(2, "0"); 
				
				/* if(!functionAppBean.getDocType().isEmpty())
				pstmt.setString(3, functionAppBean.getDocType());
				 else
					 pstmt.setString(3, "0");*/ 
				 
				 if(!functionAppBean.getPersonnelArea().isEmpty())
				pstmt.setString(3, functionAppBean.getPersonnelArea());
				 else
					 pstmt.setString(3, "0"); 
				 if(!functionAppBean.getPayrollArea().isEmpty())
				pstmt.setString(4, functionAppBean.getPayrollArea());
				 else
					 pstmt.setString(4, "0"); 
				
				
				pstmt.setString(5, functionAppBean.getUserId());
				
				row = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pstmt.close();conn.close();
		}	
			if(row>=1)
			
			return true;
			else
			return false;

    					}
    
public List getUserFilterList() throws SQLException{

String query = "select * from user_filter";
System.out.println(dataSource);
Connection conn= dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet resultSet = pstmt.executeQuery();

List<UserFilter> ls = new ArrayList<UserFilter>();
while (resultSet.next()) {

UserFilter lg = new UserFilter();
lg.setId(resultSet.getInt("id"));
//	lg.setUserId(resultSet.getInt("user_id"));
//lg.setFunctionFilter(resultSet.getString("function_filter"));


 /* query = "select Functions from Functions where id in ('"+resultSet.getString("function_filter").replace(",", "','")+"')";
//			System.out.println("query"+query);
	PreparedStatement pstmt1= conn.prepareStatement(query);
	ResultSet resultSet1 = pstmt1.executeQuery();

	int count=0;
	while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));	
if(count==0)
lg.setFunctionFilter(resultSet1.getString("Functions"));
else
		lg.setFunctionFilter(lg.getFunctionFilter()+","+resultSet1.getString("Functions"));
count++;
	}*/

//	lg.setBusinessFilter(resultSet.getString("business_filter"));

PreparedStatement pstmt1=null;
ResultSet resultSet1=null;
int count=0;

if(resultSet.getString("business_filter").length()>0){
 query = "select CompanyDisplayName from Business where id in ('"+resultSet.getString("business_filter").replace(",", "','")+"')";
	System.out.println("query"+query);
	 pstmt1= conn.prepareStatement(query);
	resultSet1 = pstmt1.executeQuery();

	count=0;
	while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));	
 if(count==0)
  lg.setBusinessFilter(resultSet1.getString("CompanyDisplayName"));
    else
		lg.setBusinessFilter(lg.getBusinessFilter()+"<br/>"+resultSet1.getString("CompanyDisplayName"));
    count++;
	}
}
	
if(resultSet.getString("location_filter").length()>0){
	 query = "select username from users where id="+resultSet.getInt("user_id");
		System.out.println("query"+query);
		pstmt1= conn.prepareStatement(query);
		resultSet1 = pstmt1.executeQuery();

		
		while (resultSet1.next()) {
   
      lg.setUserId(resultSet1.getString("username"));
       
		}
	
//lg.setLocationFilter(resultSet.getString("location_filter"));
		 query = "select LocationDisplayName from location where id in ('"+resultSet.getString("location_filter").replace(",", "','")+"')";
			System.out.println("query"+query);
			pstmt1= conn.prepareStatement(query);
			resultSet1 = pstmt1.executeQuery();

			count=0;
			while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));
					
         if(count==0)
          lg.setLocationFilter(resultSet1.getString("LocationDisplayName"));
            else
				lg.setLocationFilter(lg.getLocationFilter()+"<br/>"+resultSet1.getString("LocationDisplayName"));
            
   
         count++;
			}
		
}

/*if(resultSet.getString("doctype_filter").length()>0){
 query = "select docname from doctype where id in ('"+resultSet.getString("doctype_filter").replace(",", "','")+"')";
//	System.out.println("query"+query);
	pstmt1= conn.prepareStatement(query);
	resultSet1 = pstmt1.executeQuery();

	count=0;
	while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));	
if(count==0)
  lg.setDoctypeFilter(resultSet1.getString("docname"));
 else
		lg.setDoctypeFilter(lg.getDoctypeFilter()+","+resultSet1.getString("docname"));
 count++;
	}
}*/

//lg.setPersonalAreaFilter(resultSet.getString("personal_area_filter"));
if(resultSet.getString("personnel_area_filter").length()>0){
 query = "select personnelareadisplayname from personnelarea where id in ('"+resultSet.getString("personnel_area_filter").replace(",", "','")+"')";
	//	System.out.println("query"+query);
		pstmt1= conn.prepareStatement(query);
		resultSet1 = pstmt1.executeQuery();

		count=0;
		while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));	
  if(count==0)
      lg.setPersonalAreaFilter(resultSet1.getString("personnelareadisplayname"));
     else
			lg.setPersonalAreaFilter(lg.getPersonalAreaFilter()+"<br/>"+resultSet1.getString("personnelareadisplayname"));
     count++;
		}
}

//	lg.setPayrollAreaFilter(resultSet.getString("payroll_area_filter"));
if(resultSet.getString("payroll_area_filter").length()>0){
 query = "select payrollareadisplayname from payrollarea where id in ('"+resultSet.getString("payroll_area_filter").replace(",", "','")+"')";
	//	System.out.println("query"+query);
		pstmt1= conn.prepareStatement(query);
		resultSet1 = pstmt1.executeQuery();

		count=0;
		while (resultSet1.next()) {
//System.out.println("innerQuery"+resultSet1.getString("Functions"));	
  if(count==0)
      lg.setPayrollAreaFilter(resultSet1.getString("payrollareadisplayname"));
     else
			lg.setPayrollAreaFilter(lg.getPayrollAreaFilter()+"<br/>"+resultSet1.getString("payrollareadisplayname"));
     count++;
		}
}

ls.add(lg);
}


pstmt.close();conn.close();
System.out.println(ls);

return ls;
}







public boolean updateUserFilter(UserFilter userFilter) throws SQLException {


String query = "update user_filter set business_filter=?,location_filter=?,doctype_filter=?,personal_area_filter=?,payroll_area_filter where user_id=(select id from users where username=?)";


//	System.out.println(query);
Connection conn=dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
//pstmt.setString(1, userFilter.getFunctionFilter());
if(!userFilter.getBusinessFilter().isEmpty())
pstmt.setString(1, userFilter.getBusinessFilter());
else
pstmt.setString(1, "0");

if(!userFilter.getLocationFilter().isEmpty())
pstmt.setString(2, userFilter.getLocationFilter());
else
pstmt.setString(2, "0");	

if(!userFilter.getDoctypeFilter().isEmpty())
pstmt.setString(3, userFilter.getDoctypeFilter());
else
pstmt.setString(3, "0");	


if(!userFilter.getPersonalAreaFilter().isEmpty())
pstmt.setString(4, userFilter.getPersonalAreaFilter());
else
pstmt.setString(4, "0");

if(!userFilter.getPayrollAreaFilter().isEmpty())
pstmt.setString(5, userFilter.getPayrollAreaFilter());
else
pstmt.setString(5, "0");


pstmt.setString(6, userFilter.getUserId());

int row=pstmt.executeUpdate();
pstmt.close();conn.close();
if(row>=1)

return true;
else
return false;

		}
public List editUserFilter(int id) throws SQLException {

String query = "select * from user_filter where id=?";

System.out.println(query);
Connection conn=dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setInt(1, id);

ResultSet resultSet = pstmt.executeQuery();
UserFilter userFilter=new UserFilter();
String filterArray[]=new String[6];

while (resultSet.next()) {

//innerJson.setUserId(userId);
//filterArray[0]=(resultSet.getString("function_filter"));
filterArray[0]=(resultSet.getString("business_filter"));
filterArray[1]=(resultSet.getString("location_filter"));
//	filterArray[2]=(resultSet.getString("doctype_filter"));
filterArray[2]=(resultSet.getString("personnel_area_filter"));
filterArray[3]=(resultSet.getString("payroll_area_filter"));



}




String tableName[]={"Business", "Location","PersonnelArea","PayrollArea"};
String tableColumn[]={"CompanyDisplayName", "LocationDisplayName" ,"PersonnelAreaDisplayName","PayrollAreaDisplayName"};
List list=new ArrayList();	
int	selectedCounter=0;
for(int countTable=0;countTable<tableName.length;countTable++){

query = "select * from "+tableName[countTable];
//System.out.println(dataSource);

pstmt = conn.prepareStatement(query);

resultSet = pstmt.executeQuery();

FunctionApplication funName=new FunctionApplication();	


funName.setName(tableName[countTable]);
if(selectedCounter<12)
{
//		System.out.println(SelectedValue[selectedCounter]);
funName.setSelectedValue(SelectedValue[selectedCounter]);
	}
selectedCounter++;
while (resultSet.next()) {
	
FunctionValueId value=new FunctionValueId();
/*if(tableName[countTable].equals("Functions")){
	value.setId(resultSet.getInt("id"));
	value.setValue(resultSet.getString(tableColumn[countTable])+" - "+resultSet.getString("description")+"");
	if(filterArray[countTable].indexOf(Integer.toString(resultSet.getInt("id")))!=-1)
		value.setIsActive("y");
	
	funName.value.add(value);
}
	
else*/ if(tableName[countTable].equals("Location")){
	value.setId(resultSet.getInt("id"));
	value.setValue(resultSet.getString("LocationValue")+" - "+resultSet.getString(tableColumn[countTable])+"");
	if(filterArray[countTable].indexOf(Integer.toString(resultSet.getInt("id")))!=-1)
		value.setIsActive("y");
	
	
	funName.value.add(value);
	
}
	
else{
	value.setId(resultSet.getInt("id"));
	value.setValue(resultSet.getString(tableColumn[countTable]));
	if(filterArray[countTable].indexOf(Integer.toString(resultSet.getInt("id")))!=-1)
		value.setIsActive("y");
	
funName.value.add(value);

}
funName.columnValue.add(resultSet.getInt("id"));
}
list.add(funName);
}
pstmt.close();
conn.close();
return list;
		}




public boolean deleteUserFilter(int id) throws SQLException {


String query = "delete from user_filter where id=?";


//	System.out.println(query);
Connection conn = null;
PreparedStatement pstmt = null;
int row = 0;
try {
	conn = dataSource.getConnection();
	pstmt = conn.prepareStatement(query);
	pstmt.setInt(1, id);
	
	row = pstmt.executeUpdate();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}finally{
pstmt.close();conn.close();}
if(row>=1)

return true;
else
return false;

		}


public UserFilter edit_User_Filter(int id) throws SQLException{
String query = "select * from user_filter where id=?";
System.out.println(query);
Connection conn = null;
PreparedStatement pstmt = null;
UserFilter lg = null;
try {
	conn = dataSource.getConnection();
	pstmt = conn.prepareStatement(query);
	pstmt.setInt(1, id);
	ResultSet resultSet = pstmt.executeQuery();
	
	lg = new UserFilter();
	while (resultSet.next()) {
	
	lg.setId(resultSet.getInt("user_id"));
	
	
	
	}
	System.out.println(lg);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}finally{
pstmt.close();conn.close();}
return lg;
}




	// WebService Dropdown
	public JSONArray getRoleAndCasetype(String function) throws SQLException {

		String query = null;
		if (function.equals("ALL"))
			query = "select ARRoles from roles";
		else
			query = "select ARRoles from roles where functions='" + function + "'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		JSONArray list = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();

			list = new JSONArray();

			JSONArray innerJson = new JSONArray();

			while (resultSet.next()) {

				innerJson.put(resultSet.getString("ARRoles"));

			}
			list.put(innerJson);

			if (function.equals("ALL"))
				query = "select  CaseTypeDisplayName from casetype";
			else
				query = "select  CaseTypeDisplayName from casetype where functions='" + function + "'";

			pstmt = conn.prepareStatement(query);

			resultSet = pstmt.executeQuery();

			innerJson = new JSONArray();

			while (resultSet.next()) {

				innerJson.put(resultSet.getString("CaseTypeDisplayName"));

			}
			list.put(innerJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();
		conn.close();}
		return list;
	}


	public JSONArray getBusinessAndLocationByGrp(String busiGrp,int user_id) throws SQLException{

		String query = "";
		Connection conn = null;
		JSONArray innerJson = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			innerJson = new JSONArray();
			JSONArray innerJsonLoc = new JSONArray();
			String location_id=null;
			query="select location_filter from user_filter where user_id="+user_id;
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				location_id=resultSet.getString("location_filter");

			}

			if(location_id.equals("0")){
				pstmt.close();
				conn.close();
				innerJsonLoc = new JSONArray();}
			else {
				String location_id_final="";
				query="select location_id from businessgroup_location where group_id=(select id from businessgroup where businessgroup=?)";
				System.out.println(query+"   "+busiGrp);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, busiGrp);
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) {

					if(location_id.contains(String.valueOf(resultSet.getInt("location_id")))){

						location_id_final+="'"+resultSet.getInt("location_id")+"',";
					}

				}



				System.out.println("location_id_final"+location_id_final);

				if(busiGrp.equals("ALL"))
					query = "select * from location where id in('"+location_id.replace(",", "','")+"')";
				else
				{
					if(!location_id_final.isEmpty()){
						location_id_final=location_id_final.substring(0, location_id_final.length()-1);
					query = "select * from location where id in("+location_id_final+")";
						}
						else
							query = "select * from location where id in('0')";
				}


				pstmt = conn.prepareStatement(query);
				resultSet = pstmt.executeQuery();


				while (resultSet.next()) {

					innerJsonLoc.put(resultSet.getString("locationValue")+" - "+resultSet.getString("locationDisplayName"));

				}

			}

			JSONArray innerJsonBus=new JSONArray();
			query="select business_filter from user_filter where user_id="+user_id;


			pstmt = conn.prepareStatement(query);
			String business_id="";
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				business_id=resultSet.getString("business_filter");

			}

			if(business_id.equals("0")){
				pstmt.close();
				conn.close();
				innerJsonBus=new JSONArray();}
			else {


				if(busiGrp.equals("ALL"))
					query = "select * from business where id in('"+business_id.replace(",", "','")+"')";
				else
				{
					query = "select * from business b,businessGroup bg, businessGroup_business bgb where b.id=bgb.Business_id and bg.id=bgb.businessGroup_id and b.id in('"+business_id.replace(",", "','")+"') and businessGroup='"+busiGrp+"'";
				}


				pstmt = conn.prepareStatement(query);
				resultSet = pstmt.executeQuery();



				while (resultSet.next()) {

					innerJsonBus.put(resultSet.getString("CompanyDisplayName"));

				}
			}
			innerJson.put(innerJsonBus);
			innerJson.put(innerJsonLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		pstmt.close();
		conn.close();}
		return innerJson;


	}



	public JSONArray getBusiByLoc(String business,int user_id) throws SQLException {

		String query = null;

		Connection conn = null;
		JSONArray innerJson = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String businessGroup=business.split("@")[1];
			business=business.split("@")[0];

			innerJson = new JSONArray();
			String location_id=null;
			query="select location_filter from user_filter where user_id="+user_id;
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				location_id=resultSet.getString("location_filter");

			}

			if(location_id.equals("0"))
				innerJson = new JSONArray();
			else{

				if(business.equals("ALL")&&businessGroup.equals("ALL"))
					query = "select * from location";

				else if(business.equals("ALL"))
					query = "select locationValue,locationDisplayName from location l,  businessGroup bg,businessGroup_location bgl where  bg.id=bgl.group_id and  l.id=bgl.location_id"+

" and businessGroup='"+businessGroup+"' and l.id in('"+location_id.replace(",", "','")+"')";


				else if(businessGroup.equals("ALL"))
					query = "select locationValue,locationDisplayName from location l, business b,location_business lb where   b.id=lb.business_id  and l.id=lb.location_id "+

" and companyValue='"+business+"' and l.id in('"+location_id.replace(",", "','")+"')";



				else	{

					query = "select locationValue,locationDisplayName from location l, business b, businessGroup bg,businessGroup_location bgl,businessGroup_business bgb,location_business lb where b.id=bgb.business_id and b.id=lb.business_id and bgb.businessgroup_id=bg.id and bg.id=bgl.group_id and l.id=lb.location_id and l.id=bgl.location_id"+

" and businessGroup='"+businessGroup+"' and companyValue='"+business+"' and l.id in('"+location_id.replace(",", "','")+"')";


				}
				System.out.println("Query "+query);
				pstmt = conn.prepareStatement(query);

				resultSet = pstmt.executeQuery();



				while (resultSet.next()) {

					innerJson.put(resultSet.getString("locationValue")+" - "+resultSet.getString("locationDisplayName"));

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();
		conn.close();}
		return innerJson;


	}








	// Validate User BarCode
	public String getUserBarCode(int user_id) throws SQLException {
		String query = "select * from users where id=?";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, user_id);
		ResultSet resultSet = pstmt.executeQuery();
		String BarCode=null;
		while (resultSet.next()) {
			BarCode=resultSet.getString("dataentry");

		}

		pstmt.close();
		conn.close();
		return BarCode;

	}
	public List getAllPeriodDisplay(String selectedDate,String periodFormat) throws SQLException {

		ArrayList<String> listPeriod=new ArrayList<String>();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString = selectedDate;


			if(periodFormat.contains("7")){
				for(int countPeriod=0;countPeriod<7;countPeriod++)
				{

					Date date = formatter.parse(dateInString);       	
					date.setDate(date.getDate()+countPeriod);

					listPeriod.add("DAY "+(countPeriod+1)+"<br> "+format.format(date));
				}  

			}
			else if(periodFormat.contains("4")){
				for(int countPeriod=0;countPeriod<28;countPeriod=countPeriod+7){

					Date startDate=formatter.parse(dateInString);   Date endDate=formatter.parse(dateInString); 
					startDate.setDate(startDate.getDate()+countPeriod);
					endDate.setDate(endDate.getDate()+countPeriod+6);
					System.out.println(startDate +"   "+countPeriod);
					System.out.println(endDate +"   "+countPeriod+6);
					listPeriod.add("WEEK "+(countPeriod/7+1)+"<br> "+format.format(startDate)+" - "+format.format(endDate));

				}


			}
			else{
				for(int countPeriod=0;countPeriod<3;countPeriod++){
					Date startDate=formatter.parse(dateInString);   Date endDate=formatter.parse(dateInString); 
					startDate.setMonth(startDate.getMonth()+countPeriod);
					endDate.setMonth(endDate.getMonth()+countPeriod+1);
					endDate.setDate(endDate.getDate()-1);
					System.out.println(startDate +"   "+countPeriod);
					System.out.println(endDate +"   "+countPeriod+1);
					listPeriod.add("Month "+(countPeriod+1)+"<br> "+format.format(startDate)+" - "+format.format(endDate));

				}

			}

		}

		catch(Exception ee){
			ee.printStackTrace();
		}


		return listPeriod;
	}



	/*	public JSONArray getCaseCreatedAPGraph(FunctionAppBean functionAppBean)  throws SQLException{


String query = "select a2.locationdisplayname,a2.businessgroup, coalesce(sum(a2.Infra),0) as Infra, "+
"coalesce(sum(a2.Energy),0) as Energy, "+
"coalesce(sum(a2.Steel),0) as Steel, " +
"coalesce(sum(a2.Management),0) as Management, "+
"coalesce(sum(a2.Cement),0) as Cement from "+

"(select a1.locationdisplayname,a1.businessgroup, "+ 
"case when a1.businessgroup='Infra' then a1.period end as Infra,"+
"case when a1.businessgroup='Energy' then a1.period end as Energy,"+
"case when a1.businessgroup='Steel' then a1.period end as Steel,"+
"case when a1.businessgroup='Management' then a1.period end as Management,"+
"case when a1.businessgroup='Cement' then a1.period end as Cement"+
     " from( "+
"select l.locationdisplayname,bg.businessgroup,  count(*) as period  from ap_wip_copy a, location l , "+
" businessgroup_location bgl , businessgroup bg  where "+
" bgl.location_id=l.id and l.LocationValue=a.Gbl_Location and bg.id=bgl.group_id  ";



if(!functionAppBean.getBusiness().isEmpty()){


	query+=" and Gbl_CompanyName in ("+functionAppBean.getBusiness() +") ";
}	


if(!functionAppBean.getLocation().isEmpty()){
	query += " and gbl_location in ("+functionAppBean.getLocation()+") ";

}

// Date Where clause
	query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
			+ functionAppBean.getToDate() + " 23:59:59' ";

	query +=" group by Gbl_Location,businessgroup  ) as a1)"+
	         "	as a2 group by  a2.locationdisplayname,a2.businessgroup";
System.out.println("GGGGGGGGGGGrapg"+query);
	Connection conn=dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet resultSet = pstmt.executeQuery();

 {        
        type: "stackedColumn",
        toolTipContent: "{label}<br/><span style='\"'color: {color};'\"'><strong>{name}</strong></span>: {y}mn tonnes",
        name: "Belapur",
        showInLegend: "true",
        dataPoints: [
        {  y: 100 , label: "Steel"},
        {  y: 200, label: "Infra" },
        {  y: 500, label: "Energy" },
        {  y: 50, label: "Management" },
        {  y: 10, label: "Cement"}

        ]
      }
String graphString="[";
JSONArray finalArray=null;
try {
	finalArray=new JSONArray();;
while (resultSet.next()) {
JSONObject OuterJson=new JSONObject();
graphString+="{";

	OuterJson.put("type", "stackedColumn");
	OuterJson.put("toolTipContent", "{label}<br/><span style='color: {color};'><strong>{name}</strong></span>: {y}mn tonnes");
	OuterJson.put("name", resultSet.getString("locationdisplayname"));
	OuterJson.put("showInLegend", "true");
graphString+="type:\"stackedColumn\"";
graphString+=",toolTipContent:\"{label}<br/><span style='color: {color};'><strong>{name}</strong></span>: {y}mn tonnes\"";	
graphString+=",name:\""+resultSet.getString("locationdisplayname")+"\"";



JSONArray dataPointsArray=new JSONArray();
	JSONObject dataPointsObject=new JSONObject();
	String dataPointsObjectString="[";
	dataPointsObject.put("y", resultSet.getString("Steel"));dataPointsObject.put("label", "Steel");
	dataPointsArray.put(dataPointsObject);
	dataPointsObjectString+="{y:"+resultSet.getString("Steel")+",label:\"steel\"},";
	dataPointsObject=new JSONObject();
	dataPointsObject.put("y", resultSet.getString("Infra"));dataPointsObject.put("label", "Infra");
	dataPointsArray.put(dataPointsObject);
	dataPointsObjectString+="{y:"+resultSet.getString("Infra")+",label:\"Infra\"},";
	dataPointsObject=new JSONObject();
	dataPointsObject.put("y", resultSet.getString("Energy"));dataPointsObject.put("label", "Energy");
	dataPointsArray.put(dataPointsObject);
	dataPointsObjectString+="{y:"+resultSet.getString("Energy")+",label:\"Energy\"},";
	dataPointsObject=new JSONObject();
	dataPointsObject.put("y", resultSet.getString("Management"));dataPointsObject.put("label", "Management");
	dataPointsArray.put(dataPointsObject);
	dataPointsObjectString+="{y:"+resultSet.getString("Management")+",label:\"Management\"},";
	dataPointsObject=new JSONObject();
	dataPointsObject.put("y", resultSet.getString("Cement"));dataPointsObject.put("label", "Cement");
	dataPointsArray.put(dataPointsObject);
	dataPointsObjectString+="{y:"+resultSet.getString("Cement")+",label:\"Cement\"}";

	OuterJson.put("dataPoints", dataPointsArray);
	 dataPointsObjectString+="]";
	graphString+=",dataPoints:"+dataPointsObjectString;


	graphString+="},";


}
graphString=graphString.substring(0,graphString.length()-1);
graphString+="]";
finalArray.put(graphString);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}



System.out.println("$$$$$$$$$$$$$$$$$$$finalArray"+graphString);
conn.close();pstmt.close();
return finalArray;
}
	 */

	//Display By
	public boolean insertSetting(ApplicationSetting appSetting) {
		int row = 0;
		System.out.println("comming");
		String sql = "insert into application_settings(setting_name,setting_value,from_date,to_date,isActive) values(?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement pstmt = null;
		try {
			conn=dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, appSetting.getSettingName());
			pstmt.setString(2, appSetting.getSettingValue());
			pstmt.setString(3, appSetting.getFromDate());

			pstmt.setString(4, appSetting.getToDate());

			pstmt.setString(5, appSetting.getIsActive());
			row = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pstmt=null;
			conn=null;	
		}

		if (row>0)
			return true;
		else
			return false;
	}


	

	public JSONArray getPersoneelAndPayrollByGrp(String busiGrp,int user_id) throws SQLException{

		String query = "";
		Connection conn = null;
		JSONArray innerJson = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			innerJson = new JSONArray();










			JSONArray innerJsonLoc = new JSONArray();
			String payroll_area_id=null;
			query="select payroll_area_filter from user_filter where user_id="+user_id;
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				payroll_area_id=resultSet.getString("payroll_area_filter");

			}

			if(payroll_area_id.equals("0")){
			pstmt.close();
			conn.close();
			innerJsonLoc = new JSONArray();}
			else {
			String payroll_area_id_final="";
			query="select id from payrollarea where busiGrpId=(select id from businessgroup where businessgroup=?)";
			//System.out.println(query+"   "+busiGrp);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, busiGrp);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

			if(payroll_area_id.contains(String.valueOf(resultSet.getInt("id")))){

				payroll_area_id_final+="'"+resultSet.getInt("id")+"',";
			}

			}



			System.out.println("location_id_final"+payroll_area_id_final);


			if(!payroll_area_id.isEmpty())
			{
			if(busiGrp.equals("ALL"))
			query = "select * from payrollarea where id in('"+payroll_area_id.replace(",", "','")+"')";
			else
			{
				payroll_area_id_final=payroll_area_id_final.substring(0, payroll_area_id_final.length()-1);
			query = "select * from payrollarea where id in("+payroll_area_id_final+")";

			}



			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();


			while (resultSet.next()) {

			innerJsonLoc.put(resultSet.getString("PayrollAreaDisplayName"));

			}

			}
			}


			JSONArray innerJsonpersonnel=new JSONArray();
			query="select personnel_area_filter from user_filter where user_id="+user_id;


			pstmt = conn.prepareStatement(query);
			String personnel_area_id="";
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				personnel_area_id=resultSet.getString("personnel_area_filter");

			}

			if(personnel_area_id.equals("0")){
			pstmt.close();
			conn.close();
			innerJsonpersonnel=new JSONArray();}
			else {


			if(busiGrp.equals("ALL"))
			query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"')";
			else
			{
			query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"') and busiGrpId=(select id from businessgroup where businessgroup='"+busiGrp+"')";
			}


			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();



			while (resultSet.next()) {

			innerJsonpersonnel.put(resultSet.getString("personnelAreaDisplayName"));

			}
			}



			JSONArray innerJsonBus=new JSONArray();
			query="select business_filter from user_filter where user_id="+user_id;


			 pstmt = conn.prepareStatement(query);
			 String business_id="";
			 resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				business_id=resultSet.getString("business_filter");

			}

			if(business_id.equals("0")){
				pstmt.close();
				conn.close();
				innerJsonBus=new JSONArray();}
			else {


			if(busiGrp.equals("ALL"))
				query = "select * from business where id in('"+business_id.replace(",", "','")+"')";
			else
			{
			query = "select * from business b,businessGroup bg, businessGroup_business bgb where b.id=bgb.Business_id and bg.id=bgb.businessGroup_id and b.id in('"+business_id.replace(",", "','")+"') and businessGroup='"+busiGrp+"'";
			}


			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();



			while (resultSet.next()) {

				innerJsonBus.put(resultSet.getString("CompanyDisplayName"));

			}
			}
			innerJson.put(innerJsonBus);

			innerJson.put(innerJsonpersonnel);
			innerJson.put(innerJsonLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		pstmt.close();
		conn.close();}
		return innerJson;


		}



		public JSONArray getPersoneelAndPayrollByBusiness(String busiGrp,String busi,int user_id) throws SQLException{

		String query = "";
		Connection conn = null;
		JSONArray innerJson = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			innerJson = new JSONArray();
			JSONArray innerJsonLoc = new JSONArray();
			String payroll_area_id=null;
			query="select payroll_area_filter from user_filter where user_id="+user_id;
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				payroll_area_id=resultSet.getString("payroll_area_filter");

			}

			if(payroll_area_id.equals("0")){
			pstmt.close();
			conn.close();
			innerJsonLoc = new JSONArray();}
			else {
			/*String payroll_area_id_final="";
			query="select id from payrollarea where busiId_id=(select id from business where companyDisplayName=?)";
			//System.out.println(query+"   "+busiGrp);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, busi);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

			if(payroll_area_id.contains(String.valueOf(resultSet.getInt("id")))){

				payroll_area_id_final+="'"+resultSet.getInt("id")+"',";
			}

			}



			System.out.println("payroll_area_id_final"+payroll_area_id_final);
			*/

			if(!payroll_area_id.isEmpty())
			{
			if(busi.equals("ALL")){
				if(busiGrp.equals("ALL")){
					query = "select * from payrollarea where id in('"+payroll_area_id.replace(",", "','")+"') ";
					
					
				}
				else
			query = "select * from payrollarea where id in('"+payroll_area_id.replace(",", "','")+"') and busiGrpId=(select id from businessgroup where businessgroup='"+busiGrp+"')";


			}
			else
			{
				if(busiGrp.equals("ALL")){
					query = "select * from payrollarea where id in('"+payroll_area_id.replace(",", "','")+"') "
					+ " and busiId=(select id from business where companyValue='"+busi+"')";
					
				}
				else
				query = "select * from payrollarea where id in('"+payroll_area_id.replace(",", "','")+"') and busiGrpId=(select id from businessgroup where businessgroup='"+busiGrp+"')  "
						+ " and busiId=(select id from business where companyValue='"+busi+"')";

			}

			System.out.println("Payroll Query"+query);

			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();


			while (resultSet.next()) {

			innerJsonLoc.put(resultSet.getString("PayrollAreaDisplayName"));

			}

			}
			}


			JSONArray innerJsonBus=new JSONArray();
			query="select personnel_area_filter from user_filter where user_id="+user_id;


			pstmt = conn.prepareStatement(query);
			String personnel_area_id="";
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				personnel_area_id=resultSet.getString("personnel_area_filter");

			}

			if(personnel_area_id.equals("0")){
			pstmt.close();
			conn.close();
			innerJsonBus=new JSONArray();}
			else {

			if(busi.equals("ALL")){
			if(busiGrp.equals("ALL")){
				
			query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"')";

			}
			else
				query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"') and busiGrpId=(select id from businessgroup where businessgroup='"+busiGrp+"') ";

			}else
			{
				
				if(busiGrp.equals("ALL")){
					
					query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"') "
							+ " and busiId=(select id from business where CompanyValue='"+busi+"')";

					
				}
				else
				
			query = "select * from personnelArea where id in('"+personnel_area_id.replace(",", "','")+"') and busiGrpId=(select id from businessgroup where businessgroup='"+busiGrp+"') "
			+ " and busiId=(select id from business where CompanyValue='"+busi+"')";

			}

			System.out.println("Personnel Query"+query);
			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();



			while (resultSet.next()) {

			innerJsonBus.put(resultSet.getString("personnelAreaDisplayName"));

			}
			}
			innerJson.put(innerJsonBus);
			innerJson.put(innerJsonLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		pstmt.close();
		conn.close();}
		return innerJson;


		}


		public JSONArray getPayrollByPersonnel(String business,int user_id) throws SQLException {

		String query = null;
		System.out.println(business);
		Connection conn = null;
		JSONArray innerJson = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String personeellArea=business.split("@")[2];
			String businessGroup=business.split("@")[1];
			business=business.split("@")[0];

			innerJson = new JSONArray();
			String payroll_area=null;
			query="select payroll_area_filter from user_filter where user_id="+user_id;
			pstmt = conn.prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				payroll_area=resultSet.getString("payroll_area_filter");

			}

			if(payroll_area.equals("0"))
			innerJson = new JSONArray();
			else{

			if(business.equals("ALL")&&businessGroup.equals("ALL")&&personeellArea.equals("ALL"))
			query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')";

			else if(business.equals("ALL")&&businessGroup.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"
						+" and personnelAreaId=(select id from personnelArea where REPLACE(personnelAreaDisplayName,'.','')='"+personeellArea+"') ";



			else if(businessGroup.equals("ALL")&&personeellArea.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"+
			" and busiId=(select id from business where CompanyValue='"+business+"')";

			else if(business.equals("ALL")&&personeellArea.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"+
						"and busiGrpId=(select id from businessgroup where businessgroup='"+businessGroup+"') ";

			else if(business.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"
						+" and personnelAreaId=(select id from personnelArea where REPLACE(personnelAreaDisplayName,'.','')='"+personeellArea+"') "
			+"and busiGrpId=(select id from businessgroup where businessgroup='"+businessGroup+"') ";


			else if(businessGroup.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"+
			" and busiId=(select id from business where CompanyValue='"+business+"')"
			+" and personnelAreaId=(select id from personnelArea where REPLACE(personnelAreaDisplayName,'.','')='"+personeellArea+"') ";

			else if(personeellArea.equals("ALL"))
				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"')"+
						"and busiGrpId=(select id from businessgroup where businessgroup='"+businessGroup+"') "+
			" and busiId=(select id from business where CompanyValue='"+business+"')";

			else	{

				query = "select * from payrollArea where id in('"+payroll_area.replace(",", "','")+"') "+
						"and busiGrpId=(select id from businessgroup where businessgroup='"+businessGroup+"') "+
						" and busiId=(select id from business where CompanyValue='"+business+"')"+
						" and personnelAreaId=(select id from personnelArea where REPLACE(personnelAreaDisplayName,'.','')='"+personeellArea+"') ";


			}
			System.out.println("Query "+query);
			pstmt = conn.prepareStatement(query);

			resultSet = pstmt.executeQuery();



			while (resultSet.next()) {

			innerJson.put(resultSet.getString("PayrollAreaDisplayName"));

			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		pstmt.close();
		conn.close();}
		return innerJson;


		}




		


}
