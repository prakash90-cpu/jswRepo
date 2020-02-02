package jsw.report.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.excelUtil.ExcellGenerator;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.FunctionApplication;
import jsw.report.viewBean.FunctionValueId;
import jsw.report.viewBean.UserFilter;
import jsw.report.viewBean.functionTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

//import org.hibernate.SessionFactory;

public class ApDaoImpl implements ApDao {
	DataSource dataSource;

	ExcellGenerator excellGen = new ExcellGenerator();

	SimpleDateFormatte date = new SimpleDateFormatte();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	private Properties tableConfig;
	// private static final String FILE_LOCATION = "D:\\properties.properties";
	String SelectedValue[] = new String[12];

	private void assignFunctionAppToArray(FunctionAppBean functionAppBean) {

		System.out.println("%%%%%%%%%%%%%%" + functionAppBean);

		SelectedValue[0] = functionAppBean.getBusiness();
		SelectedValue[1] = functionAppBean.getLocation();
		SelectedValue[2] = functionAppBean.getDisplayBy();
		SelectedValue[3] = functionAppBean.getRoles();
		SelectedValue[4] = functionAppBean.getUsers();
		SelectedValue[5] = functionAppBean.getCaseType();
		SelectedValue[6] = functionAppBean.getCaseStatus();

	}

	public List getNumberOfCasesAPById(int id, int bucket, String functionName)
			throws SQLException {

		Connection conn =null;
		PreparedStatement pstmt =null;

		List list = new ArrayList();
		try{
		String query = "select * from user_filter where user_id=?";

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, id);

		ResultSet resultSet = pstmt.executeQuery();
		UserFilter userFilter = new UserFilter();
		String filterArray[] = new String[4];

		while (resultSet.next()) {

			filterArray[0] = (resultSet.getString("business_filter"));
			filterArray[1] = (resultSet.getString("location_filter"));
			// filterArray[2]=(resultSet.getString("doctype_filter"));
			/*
			 * filterArray[4]=(resultSet.getString("personal_area_filter"));
			 * filterArray[5]=(resultSet.getString("payroll_area_filter"));
			 */

		}
		System.out.println("filterArray[0]" + filterArray[0]);
		System.out.println("filterArray[1]" + filterArray[1]);

		String tableName[] = { "Business", "Location", "DisplayBy", "Roles",
				"Users", "CaseType", };
		String tableColumn[] = { "CompanyDisplayName", "LocationDisplayName",
				"Period", "ARRoles", "Username", "CaseTypeDisplayName" };
		String tableSecondColumn[] = { "CompanyDisplayName", "LocationValue",
				"Period", "ARRoles", "Username", "CaseTypeDisplayName" };

		int selectedCounter = 0;
		int countWhereCluse = 0;
		for (int countTable = 0; countTable < tableName.length; countTable++) {

			if (tableName[countTable] != null) {
				if (countTable == 0 || countTable == 1) {
					if (filterArray[countWhereCluse] != null)
						query = "select * from "
								+ tableName[countTable]
								+ " where id in ('"
								+ filterArray[countWhereCluse++].replace(",",
										"','") + "')";
				} else if (countTable == 4)

					query = "select distinct username from (select username from "
							+ tableConfig.getProperty("ap_wip")
							+ ""
							+ " union all select username from "
							+ tableConfig.getProperty("ap_completed")
							+ ") where username!='' ";

				else {

					if (countTable == 2)
						query = "select * from " + tableName[countTable]
								+ " where bucket=" + bucket;
					else
						query = "select * from " + tableName[countTable];

				}
			}

			if (countTable == 3 || countTable == 5)
				query += " where functions='" + functionName + "'";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);

			resultSet = pstmt.executeQuery();

			System.out.println(SelectedValue[0] + "  " + SelectedValue[1]
					+ "  " + SelectedValue[2]);

			FunctionApplication funName = new FunctionApplication();
			funName.setName(tableName[countTable]);
			if (selectedCounter < 7) {
				System.out.println("*************"
						+ SelectedValue[selectedCounter]);
				if (SelectedValue[selectedCounter] != null)
					funName.setSelectedValue(SelectedValue[selectedCounter]
							.replace("'", "").trim());

			}
			selectedCounter++;
			while (resultSet.next()) {

				FunctionValueId storeOption = new FunctionValueId();
				if (tableName[countTable].equals("DisplayBy")) {
					storeOption.setValue(resultSet
							.getString(tableColumn[countTable])
							+ " -"
							+ resultSet.getString("description") + "");

					funName.value.add(storeOption);
				}

				else if (tableName[countTable].equals("Location")) {

					storeOption
							.setValue(resultSet.getString("LocationValue")
									+ " - "
									+ resultSet
											.getString(tableColumn[countTable])
									+ "");
					funName.value.add(storeOption);
				} else if (tableName[countTable].equals("Business")) {

					storeOption.setValue(resultSet
							.getString(tableColumn[countTable]));

					funName.value.add(storeOption);
				} else {
					storeOption.setValue(resultSet
							.getString(tableColumn[countTable]));

					funName.value.add(storeOption);
				}

				funName.columnValue.add(resultSet
						.getString(tableSecondColumn[countTable]));

			}

			list.add(funName);
		}
		}
		catch(Exception ee){
			ee.printStackTrace();
			
		}
		finally{
		pstmt.close();
		conn.close();
		}
		return list;

	}

	public List getNumberOfCasesCreatedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {

		System.out.println("functionAppBean=" + functionAppBean);
		assignFunctionAppToArray(functionAppBean);

		// String toDate[] = functionAppBean.getToDate().split("-");
		// functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		// toDate[0]);
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		String query = "select a2.businessgroup, a2.GBL_location ,a2.COLOR_NAME,a2.LOCATIONDISPLAYNAME";
		String groupByDate = "";
		if (functionAppBean.getDisplayBy().contains("7")) {

			for (int day = 1; day <= 7; day++)
				query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

			query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME,a1.LOCATIONDISPLAYNAME ";

			JSONArray JSONdays = date.convertDateToFormat(
					functionAppBean.getFromDate(), 7).getJSONArray("days");

			for (int day = 0; day < JSONdays.length(); day++)
				query += ", case when a1.currentDate='"
						+ JSONdays.getString(day) + "' then a1.period end as d"
						+ (day + 1);

			query += " from ( ";

			// groupBy
			/*
			 * groupByDate = " TIMESTAMPDIFF(16,CHAR('" +
			 * functionAppBean.getFromDate() +
			 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";
			 */

		} else if (functionAppBean.getDisplayBy().contains("4")) {

			for (int week = 1; week <= 4; week++)
				query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

			query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME,a1.LOCATIONDISPLAYNAME ";

			JSONArray JSONstartWeek = date.convertDateToFormat(
					functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

			JSONArray JSONendWeek = date.convertDateToFormat(
					functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

			for (int week = 0; week < JSONstartWeek.length(); week++)
				query += ", case when a1.currentDate between '"
						+ JSONstartWeek.getString(week) + "'" + "and '"
						+ JSONendWeek.getString(week) + "'"
						+ " then a1.period end as w" + (week + 1);

			query += " from ( ";

			// groupBy
			/*
			 * groupByDate = " TIMESTAMPDIFF(32,CHAR('" +
			 * functionAppBean.getFromDate() +
			 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";
			 */

		} else if (functionAppBean.getDisplayBy().contains("3")) {
			for (int month = 1; month <= 3; month++)
				query += ",coalesce(sum(m" + month + "),0) as month" + month
						+ "";

			query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME ,a1.LOCATIONDISPLAYNAME";
			JSONArray JSONstartMonth = date.convertDateToFormat(
					functionAppBean.getFromDate(), 3)
					.getJSONArray("startMonth");

			JSONArray JSONendMonth = date.convertDateToFormat(
					functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
			for (int month = 0; month < JSONstartMonth.length(); month++)
				query += ", case when a1.currentDate between '"
						+ JSONstartMonth.getString(month) + "'" + "and '"
						+ JSONendMonth.getString(month) + "'"
						+ " then a1.period end as m" + (month + 1);

			query += " from ( ";

			// groupBy
			/*
			 * groupByDate = " TIMESTAMPDIFF(64,CHAR('" +
			 * functionAppBean.getFromDate() +
			 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";
			 */

		}

		String queryWip = " select bg.businessgroup, a.GBL_location ,l.COLOR_NAME,l.LOCATIONDISPLAYNAME,Date(DateCreated) as currentDate, coalesce(count( distinct a.CmAcmCaseidentifier),0) as period "
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " a, businessgroup bg,businessgroup_business bgb,business b,location l "
				+ " where   bgb.business_id=b.id and bgb.businessgroup_id=bg.id and l.locationValue=a.Gbl_location "
				+ " and  b.companyValue=a.GBL_companyname and is_active='Y' ";

		String queryCompleted = " select bg.businessgroup, a.GBL_location ,l.COLOR_NAME,l.LOCATIONDISPLAYNAME,Date(DateCreated) as currentDate, coalesce(count( distinct a.CmAcmCaseidentifier),0) as period "
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " a, businessgroup bg,businessgroup_business bgb,business b,location l "
				+ " where   bgb.business_id=b.id and bgb.businessgroup_id=bg.id and l.locationValue=a.Gbl_location "
				+ " and  b.companyValue=a.GBL_companyname  ";

		String commonQuery = "";

		// BusinessGroup Where clause
		if (!functionAppBean.getBusinessGroup().isEmpty()) {
			commonQuery += "and BusinessGroup ='"
					+ functionAppBean.getBusinessGroup() + "'";

		}

		// Business Where clause
		if (!functionAppBean.getBusiness().isEmpty()) {
			commonQuery += "and Gbl_CompanyName in ("
					+ functionAppBean.getBusiness() + ") ";

		}

		// Location Where clause
		if (!functionAppBean.getLocation().isEmpty()) {
			commonQuery += " and gbl_location in ("
					+ functionAppBean.getLocation() + ")";

		}

		// Role Where clause
		if (!functionAppBean.getRoles().isEmpty()) {
			commonQuery += " and queuename in (" + functionAppBean.getRoles()
					+ ") ";
		}

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			commonQuery += " and UserName in (" + functionAppBean.getUsers()
					+ ") ";

		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			commonQuery += " and CmAcmCaseidentifier LIKE   '"
					+ functionAppBean.getCaseType() + "%' ";

		}

		// Date Where clause
		commonQuery += " and DateCreated between '"
				+ functionAppBean.getFromDate() + " 00:00:00' and '"
				+ functionAppBean.getToDate() + " 23:59:59' ";

		commonQuery += "  group by businessgroup, GBL_location,LOCATIONDISPLAYNAME,COLOR_NAME,Date(DateCreated)";
		queryWip += commonQuery;
		queryCompleted += commonQuery;

		query += queryWip + " union all  " + queryCompleted;

		query += " ) as a1) "
				+

				" as a2 group by a2.businessgroup, a2.GBL_location,LOCATIONDISPLAYNAME,a2.COLOR_NAME order by a2.businessgroup";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-AP report 1" + query);
		Connection conn = null;
		PreparedStatement pstmt =null;
		ArrayList list = new ArrayList();

		try{
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		
		while (resultSet.next()) {
			functionTable innerJson = new functionTable();
			innerJson.setName(resultSet.getString("businessgroup"));
			// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
			innerJson.setLocation(resultSet.getString("GBL_location"));
			innerJson.setColor(resultSet.getString("COLOR_NAME"));
			innerJson.setAlias(resultSet.getString("LOCATIONDISPLAYNAME"));
			innerJson.setCount("0");
			if (functionAppBean.getDisplayBy().contains("7")) {

				innerJson.setDay1(resultSet.getString("day1"));
				innerJson.setDay2(resultSet.getString("day2"));
				innerJson.setDay3(resultSet.getString("day3"));
				innerJson.setDay4(resultSet.getString("day4"));
				innerJson.setDay5(resultSet.getString("day5"));
				innerJson.setDay6(resultSet.getString("day6"));
				innerJson.setDay7(resultSet.getString("day7"));

			} else if (functionAppBean.getDisplayBy().contains("4")) {
				innerJson.setWeek1(resultSet.getString("week1"));
				innerJson.setWeek2(resultSet.getString("week2"));
				innerJson.setWeek3(resultSet.getString("week3"));
				innerJson.setWeek4(resultSet.getString("week4"));

			} else if (functionAppBean.getDisplayBy().contains("3")) {

				innerJson.setPeriod1(resultSet.getString("month1"));
				innerJson.setPeriod2(resultSet.getString("month2"));
				innerJson.setPeriod3(resultSet.getString("month3"));

			}

			list.add(innerJson);

		}
		// System.out.println(list);
		}
		catch(Exception ee){
			ee.printStackTrace();
			
		}
		finally{
            pstmt.close();
			conn.close();
		}
		return list;

	}

	public String getNumberOfCasesCreatedAP(String columnValueJson)
			throws SQLException, JSONException {
		System.out.println("aaaaaaaajaxxxxxxxxx" + columnValueJson);

		JSONObject selectedDate = new JSONObject(columnValueJson);

		String queryWip = "select  " + tableConfig.getProperty("ap_report1")

		+ " from " + tableConfig.getProperty("ap_wip") + " a";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report1")

				+ " from " + tableConfig.getProperty("ap_completed") + " a";
		String query = ", business b , businessgroup_business bgb  ,"
				+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

		// Business group
		if (selectedDate.has("busiGrp")) {
			query += "and bg.businessgroup ='"
					+ selectedDate.getString("busiGrp") + "'";
		}
		if (selectedDate.has("businessGroup")) {
			query += "and bg.businessgroup in ('"
					+ selectedDate.getString("businessGroup") + "') ";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		if (selectedDate.has("Loc")) {
			query += " and gbl_location ='" + selectedDate.getString("Loc")
					+ "'";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}
		query += " and DateCreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59'";
		/* + "group by CmAcmCaseidentifier"; */

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getNumberOfCasesCreatedExcelAP(String columnValueJson)
			throws SQLException, JSONException {
		System.out.println("aaaaaaaajaxxxxxxxxx" + columnValueJson);

		JSONObject selectedDate = new JSONObject(columnValueJson);

		String queryWip = "select  " + tableConfig.getProperty("ap_report1")

		+ " from " + tableConfig.getProperty("ap_wip") + " a";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report1")

				+ " from " + tableConfig.getProperty("ap_completed") + " a";
		String query = ", business b , businessgroup_business bgb  ,"
				+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id   ";

		// Business group
		if (selectedDate.has("busiGrp")) {
			query += "and bg.businessgroup ='"
					+ selectedDate.getString("busiGrp") + "'";
		}
		if (selectedDate.has("businessGroup")) {
			query += "and bg.businessgroup in ('"
					+ selectedDate.getString("businessGroup") + "') ";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		if (selectedDate.has("Loc")) {
			query += " and gbl_location ='" + selectedDate.getString("Loc")
					+ "'";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}
		query += " and DateCreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59'";
		/* + "group by CmAcmCaseidentifier"; */

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);
		generateExcel(query, "AP_Report1", "Number of cases created");

	}

	public List getCasesCreatedCompletedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		// String toDate[] = functionAppBean.getToDate().split("-");
		// functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		// toDate[0]);
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		// System.out.println(jsonProcessPerformance);
		ArrayList list = new ArrayList();

		/*ArrayList<String> casetypeList = new ArrayList<String>();
		if (!functionAppBean.getCaseType().isEmpty()) {
			casetypeList.add(functionAppBean.getCaseType());
		} else {
			String sql = " select caseTypeDisplayName from CaseType where functions='AP'";

			
			PreparedStatement pstmt_first = conn_first.prepareStatement(sql);
			ResultSet resultSet_first = pstmt_first.executeQuery();

			System.out.println("Executed");

			while (resultSet_first.next()) {

				casetypeList.add(resultSet_first
						.getString("caseTypeDisplayName"));

			}
			conn_first.close();
		}

		for (int countProcess = 0; countProcess < casetypeList.size(); countProcess++)

		{
*/
			Connection conn=null;
			PreparedStatement pstmt=null;
			try {
				FunctionApplication innerOuter = new FunctionApplication();
//	innerOuter.setName(casetypeList.get(countProcess));

				// System.out.println(query);
				String query = "";
				if (functionAppBean.getCaseType()
						.equals("AP_NonPOEmployeeInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_NonPOVendorInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_CustomNonBRMInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_POSupplyInvoice")
						|| functionAppBean.getCaseType().equals("AP_TMCInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_SOServiceInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_POServicesInvoice")
						|| functionAppBean.getCaseType().equals(
								"AP_CustomBRMInvoice")
						|| functionAppBean.getCaseType().isEmpty()) {

					query = "select  '1'";

					if (functionAppBean.getDisplayBy().contains("7")) {

						for (int day = 1; day <= 7; day++)
							query += ",coalesce(sum(d" + day + "),0) as day" + day
									+ "";

						query += " from (select '1' ";

						JSONArray JSONdays = date.convertDateToFormat(
								functionAppBean.getFromDate(), 7).getJSONArray(
								"days");

						for (int day = 0; day < JSONdays.length(); day++)
							query += ", case when a1.currentDate='"
									+ JSONdays.getString(day)
									+ "' then a1.count end as d" + (day + 1);

						query += " from ( ";

					} else if (functionAppBean.getDisplayBy().contains("4")) {

						for (int week = 1; week <= 4; week++)
							query += ",coalesce(sum(w" + week + "),0) as week"
									+ week + "";

						query += " from (select  '1'";

						JSONArray JSONstartWeek = date.convertDateToFormat(
								functionAppBean.getFromDate(), 4).getJSONArray(
								"startWeek");

						JSONArray JSONendWeek = date.convertDateToFormat(
								functionAppBean.getFromDate(), 4).getJSONArray(
								"endWeek");

						for (int week = 0; week < JSONstartWeek.length(); week++)
							query += ", case when a1.currentDate between '"
									+ JSONstartWeek.getString(week) + "'" + "and '"
									+ JSONendWeek.getString(week) + "'"
									+ " then a1.count end as w" + (week + 1);

						query += " from ( ";

					} else if (functionAppBean.getDisplayBy().contains("3")) {
						for (int month = 1; month <= 3; month++)
							query += ",coalesce(sum(m" + month + "),0) as month"
									+ month + "";

						query += " from (select '1' ";
						JSONArray JSONstartMonth = date.convertDateToFormat(
								functionAppBean.getFromDate(), 3).getJSONArray(
								"startMonth");

						JSONArray JSONendMonth = date.convertDateToFormat(
								functionAppBean.getFromDate(), 3).getJSONArray(
								"endMonth");
						for (int month = 0; month < JSONstartMonth.length(); month++)
							query += ", case when a1.currentDate between '"
									+ JSONstartMonth.getString(month) + "'"
									+ "and '" + JSONendMonth.getString(month) + "'"
									+ " then a1.count end as m" + (month + 1);

						query += " from ( ";

					}

					String queryWip = "  select count( distinct CmAcmCaseidentifier) as count,Date(endTime) as currentDate from ("
							+ " select * from "
							+ tableConfig.getProperty("ap_wip")
							+ " where endTime is not null " + ") cc where is_active='Y'  ";

					String queryCompleted = "  select count( distinct CmAcmCaseidentifier) as count,Date(endTime) as currentDate from ("
							+ " select * from "
							+ tableConfig.getProperty("ap_completed")
							+ " "
							+ ") cc where '1'='1'  ";

					String commonQuery = "";

					if (!functionAppBean.getBusiness().isEmpty()) {

						commonQuery += " and Gbl_CompanyName in ("
								+ functionAppBean.getBusiness() + ") ";

					}

					if (!functionAppBean.getLocation().isEmpty()) {
						commonQuery += " and gbl_location in ("
								+ functionAppBean.getLocation() + ") ";

					}

					// Role Where clause
					if (!functionAppBean.getRoles().isEmpty()) {
						commonQuery += " and cc.queueName in ("
								+ functionAppBean.getRoles() + ") ";
					}

					// Username Where clause
					if (!functionAppBean.getUsers().isEmpty()) {
						commonQuery += " and UserName in ("
								+ functionAppBean.getUsers() + ") ";
					}

					// Case Type Where clause
					if (!functionAppBean.getCaseType().isEmpty()) {
						commonQuery += " and CmAcmCaseidentifier LIKE   '"
								+ functionAppBean.getCaseType() + "%' ";

					}

					// Date Where clause
					commonQuery += " and endTime between '"
							+ functionAppBean.getFromDate() + " 00:00:00' and '"
							+ functionAppBean.getToDate() + " 23:59:59' ";

					commonQuery += "  group by Date(endTime)";

					query = query + queryWip + commonQuery + " union all "
							+ queryCompleted + commonQuery;

					query += " ) as a1) " +

					" as a2 ";
					System.out.println("Comm report 2" + query);
					 conn = dataSource.getConnection();
					 pstmt = conn.prepareStatement(query);

					ResultSet resultSet = pstmt.executeQuery();

					while (resultSet.next()) {
						functionTable innerFunction = new functionTable();

						innerFunction.setName("Indexed");
						innerFunction.setAlias("Index");

						if (functionAppBean.getDisplayBy().contains("7")) {

							innerFunction.setDay1(resultSet.getString("day1"));
							innerFunction.setDay2(resultSet.getString("day2"));
							innerFunction.setDay3(resultSet.getString("day3"));
							innerFunction.setDay4(resultSet.getString("day4"));
							innerFunction.setDay5(resultSet.getString("day5"));
							innerFunction.setDay6(resultSet.getString("day6"));
							innerFunction.setDay7(resultSet.getString("day7"));

						} else if (functionAppBean.getDisplayBy().contains("4")) {
							innerFunction.setWeek1(resultSet.getString("week1"));
							innerFunction.setWeek2(resultSet.getString("week2"));
							innerFunction.setWeek3(resultSet.getString("week3"));
							innerFunction.setWeek4(resultSet.getString("week4"));

						} else if (functionAppBean.getDisplayBy().contains("3")) {

							innerFunction.setPeriod1(resultSet.getString("month1"));
							innerFunction.setPeriod2(resultSet.getString("month2"));
							innerFunction.setPeriod3(resultSet.getString("month3"));

						}
						innerFunction.setCount("0");
						innerOuter.value.add(innerFunction);

					}

				}

				query = "select a3.stagename,stages,a3.alias_name";
				
				String groupBy="";

				if (functionAppBean.getDisplayBy().contains("7")) {

					for (int day = 1; day <= 7; day++)
						query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

					query += " from (select a2.stagename,stages,a2.alias_name ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray("days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.count end as d" + (day + 1);
					query += " from ( ";
					
					query +="  select stagename,stages,alias_name ,d,count( distinct CmAcmCaseidentifier) as count from (";
				
					groupBy=" group by stagename,stages,alias_name ,d";
				
					
					
					query +="  select stagename,stages,alias_name ,CmAcmCaseidentifier, case";
					
					for (int day = 0; day < JSONdays.length(); day++)
						query += " when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 'd"+ (day + 1)+"' " ;
					
					query += "  end as d from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {

					for (int week = 1; week <= 4; week++)
						query += ",coalesce(sum(w" + week + "),0) as week" + week
								+ "";

					query += " from (select a2.stagename,stages,a2.alias_name ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.count end as w" + (week + 1);
				
					query += " from ( ";
					
					query +="  select stagename,stages,alias_name ,w,count( distinct CmAcmCaseidentifier) as count from (";
					
					groupBy=" group by stagename,stages,alias_name ,w";
					
					
					
					query +="  select stagename,stages,alias_name ,CmAcmCaseidentifier , case ";
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += " when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 'w" + (week + 1)+"'";
					

					query += "  end as w from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					for (int month = 1; month <= 3; month++)
						query += ",coalesce(sum(m" + month + "),0) as month"
								+ month + "";

					query += " from (select a2.stagename,stages ,a2.alias_name";
					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.count end as m" + (month + 1);
					
					query += " from ( ";
					query +="  select stagename,stages,alias_name ,m,count( distinct CmAcmCaseidentifier) as count from (";
					
					groupBy=" group by stagename,stages,alias_name ,m";
					
										
					query +="  select stagename,stages,alias_name ,CmAcmCaseidentifier, case ";
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += " when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'" + "and '"
								+ JSONendMonth.getString(month) + "'"
								+ " then 'm" + (month + 1)+"' ";
					

					query += " end as m from ( ";

				}

				String queryWip = "  select stagename,stages,alias_name,CmAcmCaseidentifier ,Date(endTime) as currentDate from ("
						+ " select * from "
						+ tableConfig.getProperty("ap_wip")
						+ " where endTime is not null   and is_active='Y'  "
						+ ") cc right join (select * from process_performance where Function='AP' ";

				String queryCompleted = "  select stagename,stages,alias_name, CmAcmCaseidentifier ,Date(endTime) as currentDate from ("
						+ " select * from "
						+ tableConfig.getProperty("ap_completed")
						+ " "
						+ ") cc right join (select * from process_performance where Function='AP' ";

				String commonQuery = "";
				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					System.out.println(functionAppBean.getCaseType());
					/*if (casetypeList.get(countProcess).equals(
							"AP_NonPOEmployeeInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_NonPOVendorInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_CustomNonBRMInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_POSupplyInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_TMCInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_SOServiceInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_POServicesInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_CustomBRMInvoice")) {
						

						
					/*} else {
						commonQuery += " and process in ('other') ";
						commonQuery += " ) pp ON cc.gbl_CaseStatus =pp.Response ";
					}*/
					commonQuery += " and process in ('"
							+ functionAppBean.getCaseType() + "') ";
				}
				commonQuery += " ) pp ON cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";

				/*
				 * else{
				 * 
				 * 
				 * commonQuery += " ) pp ON " +
				 * "(cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response "
				 * + " and workflowname='COM_PRToPOProcess') OR " +
				 * "( cc.Response =pp.Response) "; }
				 */

				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				// Role Where clause
				if (!functionAppBean.getRoles().isEmpty()) {
					commonQuery += " and cc.queueName in ("
							+ functionAppBean.getRoles() + ") ";
				}

				// Username Where clause
				if (!functionAppBean.getUsers().isEmpty()) {
					commonQuery += " and UserName in ("
							+ functionAppBean.getUsers() + ") ";
				}

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					commonQuery += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}

				// Date Where clause
				commonQuery += " and endTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  group by stagename,stages ,alias_name,Date(endTime),CmAcmCaseidentifier";

				query = query + queryWip + commonQuery + " union all "
						+ queryCompleted + commonQuery;

				query += " ) as a1) " +groupBy+

				" ) as a2 ) as a3 group by stagename,stages,alias_name order by CAST(stages AS INTEGER)";
				System.out.println("Comm report 2" + query);
				
				pstmt = conn.prepareStatement(query);

				ResultSet resultSet = pstmt.executeQuery();

				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName(resultSet.getString("StageName"));
					innerFunction.setAlias(resultSet.getString("alias_name"));

					if (functionAppBean.getDisplayBy().contains("7")) {

						innerFunction.setDay1(resultSet.getString("day1"));
						innerFunction.setDay2(resultSet.getString("day2"));
						innerFunction.setDay3(resultSet.getString("day3"));
						innerFunction.setDay4(resultSet.getString("day4"));
						innerFunction.setDay5(resultSet.getString("day5"));
						innerFunction.setDay6(resultSet.getString("day6"));
						innerFunction.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerFunction.setWeek1(resultSet.getString("week1"));
						innerFunction.setWeek2(resultSet.getString("week2"));
						innerFunction.setWeek3(resultSet.getString("week3"));
						innerFunction.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerFunction.setPeriod1(resultSet.getString("month1"));
						innerFunction.setPeriod2(resultSet.getString("month2"));
						innerFunction.setPeriod3(resultSet.getString("month3"));

					}
					innerFunction.setCount("0");
					innerOuter.value.add(innerFunction);

				}
				if (innerOuter.value.size() > 0)
					list.add(innerOuter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
		
		return list;

	}

	public String getCaseCreatedCompanyCompletedAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		String query = "";
		// Case Type Where clause
		if ((!selectedDate.getString("StageName").trim()
						.equals("Indexed"))) {

			/*if (selectedDate.getString("process").equals(
					"AP_NonPOEmployeeInvoice")
					|| selectedDate.getString("process").equals(
							"AP_NonPOVendorInvoice")
					|| selectedDate.getString("process").equals(
							"AP_CustomNonBRMInvoice")
					|| selectedDate.getString("process").equals(
							"AP_POSupplyInvoice")
					|| selectedDate.getString("process")
							.equals("AP_TMCInvoice")
					|| selectedDate.getString("process").equals(
							"AP_SOServiceInvoice")
					|| selectedDate.getString("process").equals(
							"AP_POServicesInvoice")
					|| selectedDate.getString("process").equals(
							"AP_CustomBRMInvoice")) {*/
				query += " , (select * from process_performance where  Function='AP' ";
				query += " )pp where cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response"
						+ "  ";
			/*} else {

				query += ", (select * from process_performance where  Function='AP' and process in ('other') ";
				query += " ) pp where cc.gbl_CaseStatus =pp.Response ";

			}
*/
			// StegeName Where clause
			if (selectedDate.has("StageName")) {
				query += "and StageName ='"
						+ selectedDate.getString("StageName").trim() + "' ";
			}
		} else {
			query += " where gbl_location is not null ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);

	}

	public void getCaseCreatedCompanyCompletedExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		String query = "";
		// Case Type Where clause
		if ((!selectedDate.getString("StageName").trim()
						.equals("Indexed"))) {

			/*if (selectedDate.getString("process").equals(
					"AP_NonPOEmployeeInvoice")
					|| selectedDate.getString("process").equals(
							"AP_NonPOVendorInvoice")
					|| selectedDate.getString("process").equals(
							"AP_CustomNonBRMInvoice")
					|| selectedDate.getString("process").equals(
							"AP_POSupplyInvoice")
					|| selectedDate.getString("process")
							.equals("AP_TMCInvoice")
					|| selectedDate.getString("process").equals(
							"AP_SOServiceInvoice")
					|| selectedDate.getString("process").equals(
							"AP_POServicesInvoice")
					|| selectedDate.getString("process").equals(
							"AP_CustomBRMInvoice")) {*/
				query += " , (select * from process_performance where  Function='AP' ";
				query += " )pp where cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response"
						+ "  ";
			/*} else {

				query += ", (select * from process_performance where  Function='AP' and process in ('other') ";
				query += " ) pp where cc.gbl_CaseStatus =pp.Response ";

			}
*/
			// StegeName Where clause
			if (selectedDate.has("StageName")) {
				query += "and StageName ='"
						+ selectedDate.getString("StageName").trim() + "' ";
			}
		} else {
			query += " where gbl_location is not null ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report2",
				"Cases completed upto a step");

	}

	public List getCasesProcessedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		System.out.println("functionAppBean=" + functionAppBean);
		assignFunctionAppToArray(functionAppBean);

		// String toDate[] = functionAppBean.getToDate().split("-");
		// functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		// toDate[0]);

		ArrayList listOuter = new ArrayList();

		/*ArrayList<String> casetypeList = new ArrayList<String>();
		if (!functionAppBean.getCaseType().isEmpty()) {
			casetypeList.add(functionAppBean.getCaseType());
		} else {
			String sql = " select caseTypeDisplayName from CaseType where functions='AP'";

			
			PreparedStatement pstmt_first = conn_first.prepareStatement(sql);
			ResultSet resultSet_first = pstmt_first.executeQuery();

			System.out.println("Executed");

			while (resultSet_first.next()) {

				casetypeList.add(resultSet_first
						.getString("caseTypeDisplayName"));

			}
			conn_first.close();
		}

		for (int countProcess = 0; countProcess < casetypeList.size(); countProcess++)

		{*/

			/*} else
							query += " and process in ('other') ";
			*/
			Connection conn=null;
			PreparedStatement pstmt = null;
			try {
				String query = "select Process,stagename,queuename, stepname,response from process_performance where function='AP' ";

				// Process Where clause
				/*if (casetypeList.get(countProcess)
						.equals("AP_NonPOEmployeeInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_NonPOVendorInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_CustomNonBRMInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_POSupplyInvoice")
						|| casetypeList.get(countProcess).equals("AP_TMCInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_SOServiceInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_POServicesInvoice")
						|| casetypeList.get(countProcess).equals(
								"AP_CustomBRMInvoice")) {
*/
				if (!functionAppBean.getCaseType().isEmpty()) {
					query += " and process in ('" +functionAppBean.getCaseType()
							+ "') ";
				}

				conn = dataSource.getConnection();
			    pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				JSONArray jsonProcessPerformance = new JSONArray();
				while (resultSet.next()) {
					JSONArray innerJson = new JSONArray();

					innerJson.put(resultSet.getString("stagename"));
					innerJson.put(resultSet.getString("queuename"));
					innerJson.put(resultSet.getString("stepname"));
					innerJson.put(resultSet.getString("response"));
					innerJson.put(resultSet.getString("Process"));
					jsonProcessPerformance.put(innerJson);

				}

				System.out.println(jsonProcessPerformance);

				for (int countOuter = 0; countOuter < jsonProcessPerformance
						.length(); countOuter++) {

					JSONArray innerJson = new JSONArray(
							jsonProcessPerformance.getString(countOuter));

					FunctionApplication innerOuter = new FunctionApplication();
					innerOuter.setName(innerJson.getString(0) + " - "
							+ innerJson.getString(4));

					query = "select a2.UserName";

					if (functionAppBean.getDisplayBy().contains("7")) {

						for (int day = 1; day <= 7; day++)
							query += ",coalesce(sum(d" + day + "),0) as day" + day
									+ "";

						query += " from (select a1.UserName ";

						JSONArray JSONdays = date.convertDateToFormat(
								functionAppBean.getFromDate(), 7).getJSONArray(
								"days");

						for (int day = 0; day < JSONdays.length(); day++)
							query += ", case when a1.currentDate='"
									+ JSONdays.getString(day)
									+ "' then a1.count end as d" + (day + 1);

						query += " from ( ";

					} else if (functionAppBean.getDisplayBy().contains("4")) {

						for (int week = 1; week <= 4; week++)
							query += ",coalesce(sum(w" + week + "),0) as week"
									+ week + "";

						query += " from (select a1.UserName ";

						JSONArray JSONstartWeek = date.convertDateToFormat(
								functionAppBean.getFromDate(), 4).getJSONArray(
								"startWeek");

						JSONArray JSONendWeek = date.convertDateToFormat(
								functionAppBean.getFromDate(), 4).getJSONArray(
								"endWeek");

						for (int week = 0; week < JSONstartWeek.length(); week++)
							query += ", case when a1.currentDate between '"
									+ JSONstartWeek.getString(week) + "'" + "and '"
									+ JSONendWeek.getString(week) + "'"
									+ " then a1.count end as w" + (week + 1);

						query += " from ( ";

					} else if (functionAppBean.getDisplayBy().contains("3")) {
						for (int month = 1; month <= 3; month++)
							query += ",coalesce(sum(m" + month + "),0) as month"
									+ month + "";

						query += " from (select a1.UserName ";
						JSONArray JSONstartMonth = date.convertDateToFormat(
								functionAppBean.getFromDate(), 3).getJSONArray(
								"startMonth");

						JSONArray JSONendMonth = date.convertDateToFormat(
								functionAppBean.getFromDate(), 3).getJSONArray(
								"endMonth");
						for (int month = 0; month < JSONstartMonth.length(); month++)
							query += ", case when a1.currentDate between '"
									+ JSONstartMonth.getString(month) + "'"
									+ "and '" + JSONendMonth.getString(month) + "'"
									+ " then a1.count end as m" + (month + 1);

						query += " from ( ";

					}

					String queryWip = "select UserName,count(distinct CmAcmCaseidentifier ) as count,Date(endTime) as currentDate from "
							+ tableConfig.getProperty("ap_wip")
							+ " where username is not null and username!='' and is_active='Y'  ";

					String queryCompleted = "select UserName,count(distinct CmAcmCaseidentifier ) as count,Date(endTime) as currentDate from "
							+ tableConfig.getProperty("ap_completed")
							+ " where username is not null and username!='' ";

					String commonQuery = "";
				/*	if (casetypeList.get(countProcess).equals(
							"AP_NonPOEmployeeInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_NonPOVendorInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_CustomNonBRMInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_POSupplyInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_TMCInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_SOServiceInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_POServicesInvoice")
							|| casetypeList.get(countProcess).equals(
									"AP_CustomBRMInvoice")) {*/
						commonQuery += " and QueueName='" + innerJson.getString(1)
								+ "' and StepName='" + innerJson.getString(2)
								+ "' and Response ='" + innerJson.getString(3)
								+ "'  ";

				/*	} else
						commonQuery += " and  gbl_CaseStatus ='"
								+ innerJson.getString(3) + "'  ";*/

					if (!functionAppBean.getBusiness().isEmpty()) {

						commonQuery += " and Gbl_CompanyName in ("
								+ functionAppBean.getBusiness() + ") ";
					}
					if (!functionAppBean.getLocation().isEmpty()) {
						commonQuery += " and gbl_location in ("
								+ functionAppBean.getLocation() + ") ";

					}

					// Role Where clause
					if (!functionAppBean.getRoles().isEmpty()) {
						commonQuery += " and queueName in ("
								+ functionAppBean.getRoles() + ") ";
					}

					// Username Where clause
					if (!functionAppBean.getUsers().isEmpty()) {
						commonQuery += " and UserName in ("
								+ functionAppBean.getUsers() + ") ";
					}

				
					
					// Case Type Where clause
					if (!innerJson.getString(4).isEmpty()) {
						commonQuery += " and CmAcmCaseidentifier LIKE   '"
								+ innerJson.getString(4) + "%' ";

					}


					// Date Where clause
					commonQuery += " and endTime between '"
							+ functionAppBean.getFromDate() + " 00:00:00' and '"
							+ functionAppBean.getToDate() + " 23:59:59' ";

					commonQuery += "  group by username,Date(endTime) ";

					query = query + queryWip + commonQuery + " union all "
							+ queryCompleted + commonQuery;

					query += " ) as a1) " +

					" as a2 group by UserName";
					// slf4jLogger.info("EXTRACT@@@@@-AP report 3"+query);
					System.out.println("EXTRACT@@@@@-AP report 3" + query);

					System.out.println(query + innerJson.getString(1)
							+ innerJson.getString(2) + innerJson.getString(3));
					pstmt = conn.prepareStatement(query);

					/*
					 * pstmt.setString(1, innerJson.getString(1));
					 * pstmt.setString(2, innerJson.getString(2));
					 * pstmt.setString(3, innerJson.getString(3));
					 * pstmt.setString(4, innerJson.getString(4));
					 * pstmt.setString(4, innerJson.getString(1));
					 * pstmt.setString(5, innerJson.getString(2));
					 * pstmt.setString(6, innerJson.getString(3));
					 */
					/* pstmt.setString(8, innerJson.getString(4)); */
					resultSet = pstmt.executeQuery();

					System.out.println("Executed");

					while (resultSet.next()) {

						functionTable innerFunction = new functionTable();
						innerFunction.setName(resultSet.getString("UserName"));
						if (functionAppBean.getDisplayBy().contains("7")) {

							innerFunction.setDay1(resultSet.getString("day1"));
							innerFunction.setDay2(resultSet.getString("day2"));
							innerFunction.setDay3(resultSet.getString("day3"));
							innerFunction.setDay4(resultSet.getString("day4"));
							innerFunction.setDay5(resultSet.getString("day5"));
							innerFunction.setDay6(resultSet.getString("day6"));
							innerFunction.setDay7(resultSet.getString("day7"));

						} else if (functionAppBean.getDisplayBy().contains("4")) {
							innerFunction.setWeek1(resultSet.getString("week1"));
							innerFunction.setWeek2(resultSet.getString("week2"));
							innerFunction.setWeek3(resultSet.getString("week3"));
							innerFunction.setWeek4(resultSet.getString("week4"));

						} else if (functionAppBean.getDisplayBy().contains("3")) {

							innerFunction.setPeriod1(resultSet.getString("month1"));
							innerFunction.setPeriod2(resultSet.getString("month2"));
							innerFunction.setPeriod3(resultSet.getString("month3"));

						}

						innerFunction.setCount("0");

						innerOuter.value.add(innerFunction);
						// listOuter.add();
					}

					if (innerOuter.value.size() > 0)
						listOuter.add(innerOuter);
				}

				System.out.println("listOuter" + listOuter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
finally{
	        pstmt.close();
			conn.close();
}

		return listOuter;
	}

	public String getCasesProcessedAP(String columnValue) throws SQLException,
			JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report3")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in (  select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc,";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report3")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ "  where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc,";

		String query = "";
		query = "process_performance pp where cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";

	/*	if (selectedDate.getString("process").equals("AP_NonPOEmployeeInvoice")
				|| selectedDate.getString("process").equals(
						"AP_NonPOVendorInvoice")
				|| selectedDate.getString("process").equals(
						"AP_CustomNonBRMInvoice")
				|| selectedDate.getString("process").equals(
						"AP_POSupplyInvoice")
				|| selectedDate.getString("process").equals("AP_TMCInvoice")
				|| selectedDate.getString("process").equals(
						"AP_SOServiceInvoice")
				|| selectedDate.getString("process").equals(
						"AP_POServicesInvoice")
				|| selectedDate.getString("process").equals(
						"AP_CustomBRMInvoice")) {*/
			
			// process Where clause
			if (selectedDate.has("process")) {
				query += " and process='"
						+ selectedDate.getString("process").trim() + "'";
			}
		/*}*//* else {
			query = "process_performance pp where gbl_caseStatus ='"
					+ selectedDate.getString("stageName").trim() + "' ";

			// process Where clause
			if (selectedDate.has("process")) {
				query += " and process='other'";
			}
		}*/

		// stageName Where clause
		if (selectedDate.has("stageName")) {
			query += " and stageName='"
					+ selectedDate.getString("stageName").trim() + "'";
		}

		// stageName Where clause
		if (selectedDate.has("userName")) {
			query += " and userName='"
					+ selectedDate.getString("userName").trim() + "'";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += " and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("process")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("process") + "%' ";

		}
		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getCasesProcessedExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report3")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in (  select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc,";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report3")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ "  where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc,";

		String query = "";

		if (selectedDate.getString("process").equals("AP_NonPOEmployeeInvoice")
				|| selectedDate.getString("process").equals(
						"AP_NonPOVendorInvoice")
				|| selectedDate.getString("process").equals(
						"AP_CustomNonBRMInvoice")
				|| selectedDate.getString("process").equals(
						"AP_POSupplyInvoice")
				|| selectedDate.getString("process").equals("AP_TMCInvoice")
				|| selectedDate.getString("process").equals(
						"AP_SOServiceInvoice")
				|| selectedDate.getString("process").equals(
						"AP_POServicesInvoice")
				|| selectedDate.getString("process").equals(
						"AP_CustomBRMInvoice")) {
			query = "process_performance pp where cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";

			// process Where clause
			if (selectedDate.has("process")) {
				query += " and process='"
						+ selectedDate.getString("process").trim() + "'";
			}
		} else {
			query = "process_performance pp where gbl_caseStatus ='"
					+ selectedDate.getString("stageName").trim() + "' ";

			// process Where clause
			if (selectedDate.has("process")) {
				query += " and process='other'";
			}
		}

		// stageName Where clause
		if (selectedDate.has("stageName")) {
			query += " and stageName='"
					+ selectedDate.getString("stageName").trim() + "'";
		}

		// stageName Where clause
		if (selectedDate.has("userName")) {
			query += " and userName='"
					+ selectedDate.getString("userName").trim() + "'";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += " and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("process")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("process") + "%' ";

		}
		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);
		generateExcel(query, "AP_Report3", "Cases processed by user");

	}

	public List getRoleWiseTimeTakenAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		System.out.println("functionAppBean=" + functionAppBean);
		assignFunctionAppToArray(functionAppBean);

		// String toDate[] = functionAppBean.getToDate().split("-");
		// functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		// toDate[0]);
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		List mainList = null;
		Connection conn = null;
		PreparedStatement pstmt =null;
		try {
			/*String stringQueryCount[] = {
					"CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) )",
					"CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) )",
					"count( distinct CmAcmCaseidentifier )" };
			*/mainList = new ArrayList();

			for (int countQuery = 0; countQuery < 3; countQuery++) {
				String query = "select queuename, alias_name";
				
				String groupBy = "";
				if (functionAppBean.getDisplayBy().contains("7")) {

					for (int day = 1; day <= 7; day++)
						query += ",coalesce(sum(d" + day + "),0) as day" + day + "";
					
					
					query += " from (select QUEUENAME, ALIAS_NAME ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray("days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.count end as d" + (day + 1);
				
					
					query += " from ( ";
					
					   query +="  select QUEUENAME, ALIAS_NAME,d, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";
						
						groupBy=" group by QUEUENAME, ALIAS_NAME,d";
				
					
					
					query +="  select QUEUENAME, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					
					for (int day = 0; day < JSONdays.length(); day++)
						query += " when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 'd"+ (day + 1)+"' " ;
					
					query += "  end as d from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {

					for (int week = 1; week <= 4; week++)
						query += ",coalesce(sum(w" + week + "),0)  as week" + week
								+ "";
					
					query += " from (select QUEUENAME, ALIAS_NAME ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.count end as w" + (week + 1);
				
				
				
					
					query += " from ( ";
					
	      
	                query +="  select QUEUENAME, ALIAS_NAME,w, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";
					groupBy=" group by QUEUENAME, ALIAS_NAME,w";
					
					query +="  select QUEUENAME, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += " when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 'w" + (week + 1)+"'";
					

					query += "  end as w from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					for (int month = 1; month <= 3; month++)
						query += ",coalesce(sum(m" + month + "),0) as month"
								+ month + "";
				
					query += " from (select QUEUENAME, ALIAS_NAME ";
					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					
					
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.count end as m" + (month + 1);
				
					query += " from ( ";
					 query +="  select QUEUENAME, ALIAS_NAME,m, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";	
					groupBy=" group by QUEUENAME, ALIAS_NAME,m";
					
										
					query +="  select QUEUENAME, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += " when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'" + "and '"
								+ JSONendMonth.getString(month) + "'"
								+ " then 'm" + (month + 1)+"' ";
					

					query += " end as m from ( ";

				}

				String queryCompleted = "   select queuename,r.alias_name,CmAcmCaseidentifier as count" +
						",CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as period " + "";

			/*	if (countQuery == 1)
					queryCompleted += "," + stringQueryCount[countQuery + 1]
							+ " as count";
*/
				queryCompleted += ",Date(endTime) as currentDate from "
						+ tableConfig.getProperty("ap_completed")
						+ " c"
						+ " ,roles r "
						+ " where   r.arroles=c.queuename and r.functions='AP' ";

				String commonQuery = "";

				// Business Where clause
				if (!functionAppBean.getBusiness().isEmpty()) {
					commonQuery += "and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";
				}

				// Location Where clause
				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ")";
				}

				// Role Where clause
				if (!functionAppBean.getRoles().isEmpty()) {
					commonQuery += " and c.queuename in ("
							+ functionAppBean.getRoles() + ") ";
				}

				// Username Where clause
				if (!functionAppBean.getUsers().isEmpty()) {
					commonQuery += " and UserName in ("
							+ functionAppBean.getUsers() + ") ";
				}

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					commonQuery += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}

				// BarCode Where clause
				if (!functionAppBean.getBarCode().isEmpty()) {
					commonQuery += " and gbl_barcodedc='"
							+ functionAppBean.getBarCode() + "' ";
				}

				// Date Where clause
				commonQuery += " and endTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				String queryTotal=commonQuery;
				commonQuery += "  group by queuename,r.alias_name,DATE(endTime),CmAcmCaseidentifier";

				queryCompleted += commonQuery;
				query += queryCompleted;

				query += " ) as a1) " +groupBy+")"+

				"   as a2)  group by queuename,alias_name order by queuename ";
				// System.out.println("****************************@@@@@-AP report
				// 1"+new Date("42615.55"));

				// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
				System.out.println("EXTRACT@@@@@-AP report 4" + query);
				 conn = dataSource.getConnection();
				 pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				ArrayList<functionTable> list = new ArrayList<functionTable>();

				while (resultSet.next()) {
					functionTable innerJson = new functionTable();
					innerJson.setName(resultSet.getString("queuename"));

					// innerJson.setLocation(resultSet.getString("username"));
					innerJson.setAlias(resultSet.getString("alias_name"));

					innerJson.setCount("0");
					if (functionAppBean.getDisplayBy().contains("7")) {

						innerJson.setDay1(resultSet.getString("day1"));
						innerJson.setDay2(resultSet.getString("day2"));
						innerJson.setDay3(resultSet.getString("day3"));
						innerJson.setDay4(resultSet.getString("day4"));
						innerJson.setDay5(resultSet.getString("day5"));
						innerJson.setDay6(resultSet.getString("day6"));
						innerJson.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerJson.setWeek1(resultSet.getString("week1"));
						innerJson.setWeek2(resultSet.getString("week2"));
						innerJson.setWeek3(resultSet.getString("week3"));
						innerJson.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerJson.setPeriod1(resultSet.getString("month1"));
						innerJson.setPeriod2(resultSet.getString("month2"));
						innerJson.setPeriod3(resultSet.getString("month3"));

					}

					list.add(innerJson);

				}
				
				
				query ="select queuename,r.alias_name" ;
					
                if(countQuery==0)
	           query +=" ,CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as count ";
	            else if(countQuery==1)
	            query +=", CAST((CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 )/count( distinct CmAcmCaseidentifier)AS DECIMAL(18,4) ) as count ";
	             else if(countQuery==2)
	             query +=", count( distinct CmAcmCaseidentifier) as count ";
						query +="from "
						+ tableConfig.getProperty("ap_completed")
						+ " c ,roles r  where   r.arroles=c.queuename and r.functions='AP' "+queryTotal+"   group by queuename,r.alias_name  order by queuename";
						System.out.println("EXTRACT@@@@@-AP report 4" + query);
						pstmt = conn.prepareStatement(query);
				resultSet = pstmt.executeQuery();
				int countList=0;
				while (resultSet.next()) {
					
				list.get(countList++).setCount(resultSet.getString("count"));
				}
				
				

				mainList.add(list);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pstmt.close();
			conn.close();
		}
		// System.out.println(list);

		return mainList;

	}

	public String getRoleWiseTimeTakenAP(String columnValue)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryCompleted = " select "
				+ tableConfig.getProperty("ap_report4")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " ";
		// String queryCompleted =
		// " select * from "+tableConfig.getProperty("ap_completed")+" ";
		String query = " where 1=1 ";

		// Role Where clause
		if (selectedDate.has("currentRole")) {
			query += " and queuename ='"
					+ selectedDate.getString("currentRole") + "'";
		}

		// User Where clause
		if (selectedDate.has("userName")) {
			query += " and username ='" + selectedDate.getString("userName")
					+ "'";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bar Code Where clause
		if (selectedDate.has("barCode")) {
			query += " and gbl_barcodedc='" + selectedDate.getString("barCode")
					+ "' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryCompleted + query;

		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);

	}

	public void getRoleWiseTimeTakenExcelAP(String columnValue)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryCompleted = " select "
				+ tableConfig.getProperty("ap_report4")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " ";
		// String queryCompleted =
		// " select * from "+tableConfig.getProperty("ap_completed")+" ";
		String query = " where 1=1 ";

		// Role Where clause
		if (selectedDate.has("currentRole")) {
			query += " and queuename ='"
					+ selectedDate.getString("currentRole") + "'";
		}

		// User Where clause
		if (selectedDate.has("userName")) {
			query += " and username ='" + selectedDate.getString("userName")
					+ "'";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bar Code Where clause
		if (selectedDate.has("barCode")) {
			query += " and gbl_barcodedc='" + selectedDate.getString("barCode")
					+ "' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryCompleted + query;
		generateExcel(query, "AP_Report4",
				"Role wise Time taken to complete a process");

	}

	public List getTotalCycleTimeAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		// String toDate[] = functionAppBean.getToDate().split("-");
		// functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		// toDate[0]);
		System.out.println("userFunctionArray  " + functionAppBean.getToDate());
			List mainList = new ArrayList();

		for (int countQuery = 0; countQuery < 3; countQuery++) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				String query = "select StageName,alias_name ";
				
				String groupBy = "";
				if (functionAppBean.getDisplayBy().contains("7")) {

					for (int day = 1; day <= 7; day++)
						query += ",coalesce(sum(d" + day + "),0) as day" + day + "";
					
					
					query += " from (select StageName, ALIAS_NAME ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray("days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.count end as d" + (day + 1);
				
					
					query += " from ( ";
					
					   query +="  select StageName, ALIAS_NAME,d, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";
						
						groupBy=" group by StageName, ALIAS_NAME,d";
				
					
					
					query +="  select StageName, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					
					for (int day = 0; day < JSONdays.length(); day++)
						query += " when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 'd"+ (day + 1)+"' " ;
					
					query += "  end as d from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {

					for (int week = 1; week <= 4; week++)
						query += ",coalesce(sum(w" + week + "),0)  as week" + week
								+ "";
					
					query += " from (select StageName, ALIAS_NAME ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.count end as w" + (week + 1);
				
				
				
					
					query += " from ( ";
					
	      
	                query +="  select StageName, ALIAS_NAME,w, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";
					groupBy=" group by StageName, ALIAS_NAME,w";
					
					query +="  select StageName, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += " when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 'w" + (week + 1)+"'";
					

					query += "  end as w from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					for (int month = 1; month <= 3; month++)
						query += ",coalesce(sum(m" + month + "),0) as month"
								+ month + "";
				
					query += " from (select StageName, ALIAS_NAME ";
					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					
					
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.count end as m" + (month + 1);
				
					query += " from ( ";
					 query +="  select StageName, ALIAS_NAME,m, ";
					   if(countQuery==0)
					   query +=" sum(period) as count ";
					   else if(countQuery==1)
					   query +=" sum(period)/count(distinct count) as count ";
					   else if(countQuery==2)
					   query +=" count(distinct count) as count ";
					   
					   query += " from (";	
					groupBy=" group by StageName, ALIAS_NAME,m";
					
										
					query +="  select StageName, ALIAS_NAME,count,CURRENTDATE,period,  case ";
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += " when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'" + "and '"
								+ JSONendMonth.getString(month) + "'"
								+ " then 'm" + (month + 1)+"' ";
					

					query += " end as m from ( ";
				}
				
				String queryWip = "";

				queryWip = "select pp.StageName,pp.alias_name,Date(endTime) as currentDate,CmAcmCaseidentifier as count, CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8))/3600 AS DECIMAL(18,4) ) as period ";

			
				queryWip += " from "
						+ tableConfig.getProperty("ap_wip")
						+ " cc,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null and is_active='Y'  ";

				String queryCompleted = "select pp.StageName,pp.alias_name,Date(endTime) as currentDate,CmAcmCaseidentifier as count, CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as period ";

			
				queryCompleted += " from "
						+ tableConfig.getProperty("ap_completed")
						+ " cc ,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%')";

				String commonQuery = "";

			
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {

					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				// Role Where clause
				if (!functionAppBean.getRoles().isEmpty()) {
					commonQuery += " and cc.queueName in ("
							+ functionAppBean.getRoles() + ") ";
				}

				// Username Where clause
				if (!functionAppBean.getUsers().isEmpty()) {
					commonQuery += " and UserName in ("
							+ functionAppBean.getUsers() + ") ";
				}

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					commonQuery += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}

				// BarCode Where clause
				if (!functionAppBean.getBarCode().isEmpty()) {
					commonQuery += " and gbl_barcodedc='"
							+ functionAppBean.getBarCode() + "' ";
				}

				commonQuery += " and endTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";
                String totalQuery=commonQuery;
				commonQuery += "group by StageName,alias_name,Date(endTime),CmAcmCaseidentifier ";

				queryWip += commonQuery;
				queryCompleted += commonQuery;
				query += queryWip + " union all " + queryCompleted;
				
				query += " ) as a1) " +groupBy+")"+

				"   as a2)  group by StageName,alias_name  ";
				// slf4jLogger.info("EXTRACT@@@@@-AP report 5"+query);
				System.out.println("EXTRACT@@@@@-AP report 5" + query);

				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				ResultSet resultSet = pstmt.executeQuery();

				ArrayList<functionTable> list = new ArrayList<functionTable>();
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName(resultSet.getString("StageName"));
					innerFunction.setAlias(resultSet.getString("alias_name"));

					innerFunction.setCount("0");
					if (functionAppBean.getDisplayBy().contains("7")) {

						innerFunction.setDay1(resultSet.getString("day1"));
						innerFunction.setDay2(resultSet.getString("day2"));
						innerFunction.setDay3(resultSet.getString("day3"));
						innerFunction.setDay4(resultSet.getString("day4"));
						innerFunction.setDay5(resultSet.getString("day5"));
						innerFunction.setDay6(resultSet.getString("day6"));
						innerFunction.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerFunction.setWeek1(resultSet.getString("week1"));
						innerFunction.setWeek2(resultSet.getString("week2"));
						innerFunction.setWeek3(resultSet.getString("week3"));
						innerFunction.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerFunction.setPeriod1(resultSet.getString("month1"));
						innerFunction.setPeriod2(resultSet.getString("month2"));
						innerFunction.setPeriod3(resultSet.getString("month3"));

					}

					list.add(innerFunction);

				}
				System.out.println(list);
				
				query= "select stagename,alias_name";
				
                if(countQuery==0)
	           query +=" ,CAST(CAST(sum(TIMESTAMPDIFF(4,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/60 AS DECIMAL(18,4) ) as count ";
	            else if(countQuery==1)
	            query +=", CAST((CAST(sum(TIMESTAMPDIFF(4,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/60 )/count( distinct CmAcmCaseidentifier)AS DECIMAL(18,4) ) as count ";
	             else if(countQuery==2)
	             query +=", count( distinct CmAcmCaseidentifier) as count ";
				query+="from ("
						+ " select stagename,alias_name,CmAcmCaseidentifier,starttime,endtime from "+tableConfig.getProperty("ap_wip")+"  " +
								"cc,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null  and is_active='Y'"
                          + totalQuery+" union all  select stagename,alias_name,CmAcmCaseidentifier,starttime,endtime from "+tableConfig.getProperty("ap_completed")+" cc,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null  "
                                  + totalQuery
						+ ") group by stagename,alias_name";
				
				System.out.println("EXTRACT@@@@@-AP report 4" + query);
				pstmt = conn.prepareStatement(query);
		resultSet = pstmt.executeQuery();
		int countList=0;
		while (resultSet.next()) {
			
		list.get(countList++).setCount(resultSet.getString("count"));
		}
		
				mainList.add(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();}
		}

		return mainList;

	}

	public String getTotalCycleTimeAP(String columnValue) throws SQLException,
			JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report5")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report5")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " ";
		String query = " cc,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null ";

		// StageName Where clause
		if (selectedDate.has("StageName")) {
			query += " and StageName in ('"
					+ selectedDate.getString("StageName") + "') ";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += " and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Bar Code Where clause
		if (selectedDate.has("barCode")) {
			query += " and gbl_barcodedc='" + selectedDate.getString("barCode")
					+ "' ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}
		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);

	}

	public void getTotalCycleTimeExcelAP(String columnValue)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report5")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report5")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " ";
		String query = " cc,process_performance pp where pp.Function='AP' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null ";

		// StageName Where clause
		if (selectedDate.has("StageName")) {
			query += " and StageName in ('"
					+ selectedDate.getString("StageName") + "') ";
		}
		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += " and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and cc.queuename in (" + selectedDate.getString("Roles")
					+ ") ";
		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";

		}

		// Bar Code Where clause
		if (selectedDate.has("barCode")) {
			query += " and gbl_barcodedc='" + selectedDate.getString("barCode")
					+ "' ";

		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}
		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryWip + query + " and is_active='Y'  union all " + queryCompleted + query;

		generateExcel(query, "AP_Report5",
				"Total Cycle time to complete process");

	}

	public List getCasesPendingAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		String dateCreatedBucket = "";

		if (functionAppBean.getAgingBy().equals("Creation Date"))
			dateCreatedBucket += "datecreated";
		else if (functionAppBean.getAgingBy().equals("Received Date"))
			dateCreatedBucket += " ap_received_Date ";
		else if (functionAppBean.getAgingBy().equals("Current Role"))
			dateCreatedBucket += "startTime";

		String query = "select a2.queuename,a2.alias_name ";
		String groupByDate = "";
		/*
		 * if (functionAppBean.getDisplayBy().contains("3/7/15/30/90/90+")) {
		 */
		for (int bucket = 0; bucket <= 5; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket
					+ "";

		query += " from ( select a1.queuename,a1.alias_name ";

		query += ", case when a1.pendingDays between " + 0 + " and " + 3
				+ " then 1 else 0 end as b0";
		query += ", case when a1.pendingDays between " + 4 + " and " + 7
				+ " then 1 else 0 end as b1";
		query += ", case when a1.pendingDays between " + 8 + " and " + 15
				+ " then 1 else 0 end as b2";
		query += ", case when a1.pendingDays between " + 16 + " and " + 30
				+ " then 1 else 0 end as b3";
		query += ", case when a1.pendingDays between " + 30 + " and " + 90
				+ " then 1 else 0 end as b4";
		query += ", case when a1.pendingDays>" + 90
				+ " then 1 else 0 end as b5";

		query += " from ( ";

		query += " select a.queuename,r.alias_name,  coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
				+ dateCreatedBucket
				+ ")) AS DECIMAL(18,2))/86400),0) as pendingDays "
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " a,roles r where   "
				+ "  endTime is null and r.arroles=queuename and functions='AP' and is_active='Y'  ";
		if (functionAppBean.getAgingBy().equals("Received Date"))
		query+=" and ap_received_Date is not null ";
		// Business Where clause
		if (!functionAppBean.getBusiness().isEmpty()) {
			query += "and Gbl_CompanyName in (" + functionAppBean.getBusiness()
					+ ") ";
		}

		// Location Where clause
		if (!functionAppBean.getLocation().isEmpty()) {
			query += " and gbl_location in (" + functionAppBean.getLocation()
					+ ")";
		}

		// Role Where clause
		if (!functionAppBean.getRoles().isEmpty()) {
			query += " and a.queuename in (" + functionAppBean.getRoles()
					+ ") ";
		}

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			query += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			query += " and CmAcmCaseidentifier LIKE   '"
					+ functionAppBean.getCaseType() + "%' ";

		}

		// Date Where clause

		/*query += " and " + dateCreatedBucket + " between '"
				+ functionAppBean.getFromDate() + " 00:00:00' and '"
				+ functionAppBean.getToDate() + " 23:59:59' ";
*/
		query += "  group by queuename,r.alias_name ,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
				+ dateCreatedBucket + ")) AS DECIMAL(18,2))/86400)";

		query += " ) as a1) " +

		" as a2 group by a2.queuename,a2.alias_name  order by a2.queuename";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-AP report 6" + query);
		Connection conn = null;
		PreparedStatement pstmt =null;
		ArrayList list = null;
		try {
			conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			list = new ArrayList();

			while (resultSet.next()) {
				functionTable innerJson = new functionTable();
				innerJson.setName(resultSet.getString("queuename"));
				// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));

				innerJson.setAlias(resultSet.getString("alias_name"));

				innerJson.setCount("0");
				/*
				 * if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {
				 */
				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));
				innerJson.setBucket5(resultSet.getString("bucket4"));
				innerJson.setBucket6(resultSet.getString("bucket5"));

				list.add(innerJson);

			}
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
finally{
		conn.close();
		pstmt.close();
}
		return list;
	}

	public String getCasesPendingAP(String columnValue) throws SQLException,
			JSONException {

		JSONObject selectedDate = new JSONObject(columnValue);

		String dateCreatedBucket = "";
		if (selectedDate.getString("AgingBy").equals("Creation Date"))
			dateCreatedBucket = "datecreated";
		else if (selectedDate.getString("AgingBy").equals("Received Date"))
			dateCreatedBucket = "ap_received_Date";

		String query = "select "
				+ tableConfig.getProperty("ap_report6")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
				+ dateCreatedBucket
				+ ")) AS DECIMAL(18,2))/86400) as Bucket"
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in  ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " where endTime is null  and is_active='Y' ";

		if (selectedDate.has("currentRole")) {

			query += " and queuename='" + selectedDate.getString("currentRole")
					+ "' ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		if (selectedDate.has("bucketFrom")) {
			// Bucket
			if (selectedDate.has("bucketTo")) {
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
						+ dateCreatedBucket
						+ ")) AS DECIMAL(18,2))/86400) between "
						+ selectedDate.getString("bucketFrom")
						+ " and "
						+ selectedDate.getString("bucketTo");
			} else
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
						+ dateCreatedBucket
						+ ")) AS DECIMAL(18,2))/86400) >"
						+ selectedDate.getString("bucketFrom");
		}

		/*
		 * // Case Status Where clause if (selectedDate.has("CaseStatus")) {
		 * query += " and gbl_CaseStatus in (" +
		 * selectedDate.getString("CaseStatus") + ") ";
		 * 
		 * }
		 */
		query += "  )";
		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);
	}

	public void getCasesPendingExcelAP(String columnValue) throws SQLException,
			JSONException {

		JSONObject selectedDate = new JSONObject(columnValue);

		String dateCreatedBucket = "";
		if (selectedDate.getString("AgingBy").equals("Creation Date"))
			dateCreatedBucket = "datecreated";
		else if (selectedDate.getString("AgingBy").equals("Received Date"))
			dateCreatedBucket = "ap_received_Date";

		String query = "select "
				+ tableConfig.getProperty("ap_report6")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
				+ dateCreatedBucket
				+ ")) AS DECIMAL(18,2))/86400) as Bucket "
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in  ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " where endTime is null and is_active='Y'  ";

		if (selectedDate.has("currentRole")) {

			query += " and  queuename='"
					+ selectedDate.getString("currentRole") + "' ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		if (selectedDate.has("bucketFrom")) {
			// Bucket
			if (selectedDate.has("bucketTo")) {
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
						+ dateCreatedBucket
						+ ")) AS DECIMAL(18,2))/86400) between "
						+ selectedDate.getString("bucketFrom")
						+ " and "
						+ selectedDate.getString("bucketTo");
			} else
				query += " and CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -"
						+ dateCreatedBucket
						+ ")) AS DECIMAL(18,2))/86400) >"
						+ selectedDate.getString("bucketFrom");
		}

		/*
		 * // Case Status Where clause if (selectedDate.has("CaseStatus")) {
		 * query += " and gbl_CaseStatus in (" +
		 * selectedDate.getString("CaseStatus") + ") ";
		 * 
		 * }
		 */
		query += "  )";
		System.out.println("Restquery " + query);
		generateExcel(query, "AP_Report6", "Number of cases pending");

	}

	public List getCasesCompletedCumulativelyAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);
		/*
		 * //String toDate[] = functionAppBean.getToDate().split("-");
		 * //functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		 * toDate[0]);
		 */
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		String query = "select a2.BusinessGroup, a2.GBL_location ,a2.COLOR_NAME,a2.LocationDisplayName";
		String groupByDate = "";
		/* if (functionAppBean.getDisplayBy().contains("3/7/10/15/15+")) { */

		for (int bucket = 0; bucket <= 4; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket
					+ "";

		query += " from (select a1.BusinessGroup, a1.GBL_location,a1.COLOR_NAME,a1.LocationDisplayName";

		query += ", case when a1.pendingDays between " + 0 + " and " + 3
				+ " then 1 else 0 end as b0";
		query += ", case when a1.pendingDays between " + 0 + " and " + 7
				+ " then 1 else 0 end as b1";
		query += ", case when a1.pendingDays between " + 0 + " and " + 10
				+ " then 1 else 0 end as b2";
		query += ", case when a1.pendingDays between " + 0 + " and " + 15
				+ " then 1 else 0 end as b3";

		query += ", case when a1.pendingDays>" + 15
				+ " then 1 else 0 end as b4";

		query += " from ( ";
		/*
		 * 
		 * }
		 * 
		 * 
		 * // groupBy /*groupByDate = " TIMESTAMPDIFF(16,CHAR('" +
		 * functionAppBean.getFromDate() +
		 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";
		 */

		/*
		 * } else if (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {
		 * 
		 * for (int bucket = 0; bucket <= 3; bucket++) query +=
		 * ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";
		 * 
		 * query +=
		 * " from (select a1.BusinessGroup, a1.GBL_location ,a1.COLOR_NAME,a1.LocationDisplayName "
		 * ;
		 * 
		 * 
		 * for (int bucket = 1; bucket <= 46; bucket+=15){ if(bucket==46) query
		 * += ", case when a1.pendingDays > " + (bucket-1) +
		 * " then 1 else 0 end as b" + (bucket- 1)/15; else query +=
		 * ", case when a1.pendingDays between " + 0 + " and " +(bucket+14) + ""
		 * + " then 1 else 0 end as b" + (bucket- 1)/15; } query += " from ( ";
		 * 
		 * // groupBy groupByDate = " TIMESTAMPDIFF(32,CHAR('" +
		 * functionAppBean.getFromDate() +
		 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";
		 * 
		 * } else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
		 * for (int bucket = 0; bucket <= 4; bucket++) query +=
		 * ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";
		 * 
		 * query +=
		 * " from (select a1.BusinessGroup, a1.GBL_location ,a1.COLOR_NAME,a1.LocationDisplayName"
		 * ;
		 * 
		 * for (int bucket = 1; bucket <=5; bucket++){
		 * 
		 * if(bucket==4) query += ", case when a1.pendingDays >" + (bucket-1)*7
		 * + " then 1 else 0 end as b" + (bucket - 1);
		 * 
		 * else query += ", case when a1.pendingDays between " + 0 + " and " +
		 * bucket*7 + "" + " then 1 else 0 end as b" + (bucket - 1);
		 * 
		 * 
		 * } query += " from ( ";
		 * 
		 * // groupBy groupByDate = " TIMESTAMPDIFF(64,CHAR('" +
		 * functionAppBean.getFromDate() +
		 * "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";
		 * 
		 * }
		 */

		query += " select bg.BusinessGroup, GBL_location,l.COLOR_NAME,l.LocationDisplayName, coalesce( CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) ,0) as pendingDays "
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " a,business b,businessGroup bg,location l, businessGroup_business bgb where b.id=bgb.business_id and l.locationValue= GBL_location and bg.id=bgb.businessGroup_id and b.companyValue=gbl_CompanyName "
				+ "  ";

		// Business Where clause
		if (!functionAppBean.getBusiness().isEmpty()) {
			query += "and Gbl_CompanyName in (" + functionAppBean.getBusiness()
					+ ") ";
		}

		// Location Where clause
		if (!functionAppBean.getLocation().isEmpty()) {
			query += " and gbl_location in (" + functionAppBean.getLocation()
					+ ")";
		}

		// Role Where clause
		if (!functionAppBean.getRoles().isEmpty()) {
			query += " and a.queuename in (" + functionAppBean.getRoles()
					+ ") ";
		}

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			query += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			query += " and CmAcmCaseidentifier LIKE   '"
					+ functionAppBean.getCaseType() + "%' ";

		}

		// query +=
		// " and a.ID in (select MAX(ap.ID) from "+tableConfig.getProperty("ap_completed")+" ap group by CmAcmCaseidentifier) ";

		// Date Where clause
		query += " and endTime between '" + functionAppBean.getFromDate()
				+ " 00:00:00' and '" + functionAppBean.getToDate()
				+ " 23:59:59' ";

		query += "  group by BusinessGroup,GBL_location,COLOR_NAME ,LocationDisplayName ,CmAcmCaseidentifier, CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) ";

		query += " ) as a1) "
				+

				" as a2 group by a2.BusinessGroup,a2.GBL_location,a2.COLOR_NAME,a2.LocationDisplayName order by a2.BusinessGroup";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-AP report 7" + query);
		Connection conn = null;
		PreparedStatement pstmt=null;
		ArrayList list = null;
		try {
			conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			list = new ArrayList();

			while (resultSet.next()) {
				functionTable innerJson = new functionTable();
				innerJson.setName(resultSet.getString("BusinessGroup"));
				// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
				innerJson.setStepName(resultSet.getString("GBL_location"));
				innerJson.setColor(resultSet.getString("COLOR_NAME"));
				innerJson.setAlias(resultSet.getString("LocationDisplayName"));

				innerJson.setCount("0");
				/*
				 * if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {
				 */
				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));
				innerJson.setBucket5(resultSet.getString("bucket4"));

				/*
				 * } else if
				 * (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {
				 * innerJson.setBucket1(resultSet.getString("bucket0"));
				 * innerJson.setBucket2(resultSet.getString("bucket1"));
				 * innerJson.setBucket3(resultSet.getString("bucket2"));
				 * innerJson.setBucket4(resultSet.getString("bucket3"));
				 * 
				 * 
				 * } else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+"))
				 * {
				 * 
				 * innerJson.setBucket1(resultSet.getString("bucket0"));
				 * innerJson.setBucket2(resultSet.getString("bucket1"));
				 * innerJson.setBucket3(resultSet.getString("bucket2"));
				 * innerJson.setBucket4(resultSet.getString("bucket3"));
				 * innerJson.setBucket5(resultSet.getString("bucket4"));
				 * 
				 * }
				 */

				list.add(innerJson);

			}
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
finally{
	pstmt.close();
conn.close();
}
		return list;
	}

	public String getCasesCompletedCumulativelyAP(String columnValueJson)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValueJson);

		String queryCompleted = " select "
				+ tableConfig.getProperty("ap_report7")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) as Bucket from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " a";
		String query = ", business b , businessgroup_business bgb  ,"
				+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

		// Business group
		if (selectedDate.has("busiGrp")) {
			query += "and bg.businessgroup ='"
					+ selectedDate.getString("busiGrp") + "'";
		}
		if (selectedDate.has("businessGroup")) {
			query += "and bg.businessgroup in ('"
					+ selectedDate.getString("businessGroup") + "') ";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}
		if (selectedDate.has("Loc")) {
			query += " and gbl_location ='" + selectedDate.getString("Loc")
					+ "'";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bucket

		if (selectedDate.has("bucketTo")) {
			query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  between "
					+ 0 + " and " + selectedDate.getString("bucketTo");
		} else
			query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  > 15 ";

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";
		/* + "group by CmAcmCaseidentifier"; */

		query = queryCompleted + query;

		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);
	}

	public void getCasesCompletedCumulativelyExcelAP(String columnValueJson)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValueJson);

		String queryCompleted = " select "
				+ tableConfig.getProperty("ap_report7")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) as Bucket from "
				+ tableConfig.getProperty("ap_completed")
				+ " where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " a";
		String query = ", business b , businessgroup_business bgb  ,"
				+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

		// Business group
		if (selectedDate.has("busiGrp")) {
			query += "and bg.businessgroup ='"
					+ selectedDate.getString("busiGrp") + "'";
		}
		if (selectedDate.has("businessGroup")) {
			query += "and bg.businessgroup in ('"
					+ selectedDate.getString("businessGroup") + "') ";
		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}
		if (selectedDate.has("Loc")) {
			query += " and gbl_location ='" + selectedDate.getString("Loc")
					+ "'";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bucket
		if (selectedDate.has("bucketTo")) {
			query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  between "
					+ 0 + " and " + selectedDate.getString("bucketTo");
		} else
			query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  > 15 ";

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";
		/* + "group by CmAcmCaseidentifier"; */

		query = queryCompleted + query;
		generateExcel(query, "AP_Report7",
				"Cases completed cumulatively");

	}

	public List getCasesPendingForProcesssingAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		/*
		 * //String toDate[] = functionAppBean.getToDate().split("-");
		 * //functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" +
		 * toDate[0]);
		 */
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		String query = "select a2.queuename,a2.alias_name ";
		String groupByDate = "";
		/* if (functionAppBean.getDisplayBy().contains("2/7/15/15+")) { */

		for (int bucket = 0; bucket <= 3; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket
					+ "";

		query += " from (select a1.queuename,a1.alias_name ";

		query += ", case when a1.pendingDays between " + 0 + " and " + 2
				+ " then 1 else 0 end as b0";
		query += ", case when a1.pendingDays between " + 3 + " and " + 7
				+ " then 1 else 0 end as b1";
		query += ", case when a1.pendingDays between " + 8 + " and " + 15
				+ " then 1 else 0 end as b2";

		query += ", case when a1.pendingDays>" + 15
				+ " then 1 else 0 end as b3";

		query += " from ( ";

		query += " select a.queuename,r.alias_name,coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400),0) as pendingDays "
				+ " from  "
				+ tableConfig.getProperty("ap_wip")
				+ " a, roles r WHERE"
				+ "  endTime is null and r.arroles=queuename and functions='AP' and is_active='Y'  ";

		// Business Where clause
		if (!functionAppBean.getBusiness().isEmpty()) {
			query += "and Gbl_CompanyName in (" + functionAppBean.getBusiness()
					+ ") ";
		}

		// Location Where clause
		if (!functionAppBean.getLocation().isEmpty()) {
			query += " and gbl_location in (" + functionAppBean.getLocation()
					+ ")";
		}

		// Role Where clause
		if (!functionAppBean.getRoles().isEmpty()) {
			query += " and a.queuename in (" + functionAppBean.getRoles()
					+ ") ";
		}

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			query += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			query += " and CmAcmCaseidentifier LIKE   '"
					+ functionAppBean.getCaseType() + "%' ";

		}

		// Date Where clause
		/*query += " and DateCreated between '" + functionAppBean.getFromDate()
				+ " 00:00:00' and '" + functionAppBean.getToDate()
				+ " 23:59:59' ";*/

		query += "  group by queuename,r.alias_name,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400)";

		query += " ) as a1) " +

		" as a2 group by a2.queuename,a2.alias_name order by a2.queuename";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-AP report 8" + query);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList list = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			list = new ArrayList();

			while (resultSet.next()) {
				functionTable innerJson = new functionTable();
				innerJson.setName(resultSet.getString("queuename"));
				innerJson.setAlias(resultSet.getString("alias_name"));

				innerJson.setCount("0");
				/*
				 * if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {
				 */
				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));

				list.add(innerJson);

			}
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
finally{
		conn.close();
		pstmt.close();
}
		return list;
	}

	public String getCasesPendingForProcesssingAP(String columnValueJson)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValueJson);

		String query = " select "
				+ tableConfig.getProperty("ap_report8")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) as Bucket from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " where endTime is null  and is_active='Y' ";

		if (selectedDate.has("currentRole")) {

			query += " and queuename='" + selectedDate.getString("currentRole")
					+ "' ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bucket
		if (selectedDate.has("bucketFrom")) {
			if (selectedDate.has("bucketTo")) {
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) between "
						+ selectedDate.getString("bucketFrom")
						+ " and "
						+ selectedDate.getString("bucketTo");
			} else
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) >"
						+ selectedDate.getString("bucketFrom");

		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in ("
					+ selectedDate.getString("CaseStatus") + ") ";

		}
		query += "  )";
		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);
	}

	public void getCasesPendingForProcesssingExcelAP(String columnValueJson)
			throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValueJson);

		String query = " select "
				+ tableConfig.getProperty("ap_report8")
				+ ",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) as Bucket from "
				+ tableConfig.getProperty("ap_wip")
				+ " where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " where endTime is null and is_active='Y'  ";

		if (selectedDate.has("currentRole")) {

			query += " and queuename='" + selectedDate.getString("currentRole")
					+ "' ";

		}

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (selectedDate.has("Roles")) {
			query += " and queuename in (" + selectedDate.getString("Roles")
					+ ") ";

		}

		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" + selectedDate.getString("Users")
					+ ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		// Bucket
		if (selectedDate.has("bucketFrom")) {
			if (selectedDate.has("bucketTo")) {
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) between "
						+ selectedDate.getString("bucketFrom")
						+ " and "
						+ selectedDate.getString("bucketTo");
			} else
				query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) >"
						+ selectedDate.getString("bucketFrom");

		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in ("
					+ selectedDate.getString("CaseStatus") + ") ";

		}
		query += "  )";
		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report8",
				"Cases pending from last role");

	}

	private void generateExcel(String query, String fileName, String sheetName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String FILE_NAME = fileName + ".xlsx";

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);

			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			Sheet sheet = workbook.createSheet(sheetName);

			int colNum = 0;

			ResultSet resultSet = pstmt.executeQuery();

			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			Row row = sheet.createRow(0);
			CellStyle style = workbook.createCellStyle();
			// style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font fontBold = workbook.createFont();
			fontBold.setFontHeightInPoints((short) 11);
			fontBold.setFontName("Times New Roman");
			fontBold.setBold(true);
			style.setFont(fontBold);

			for (int colCount = 1; colCount <= columnCount; colCount++) {
				Cell cell = row.createCell(colCount - 1);
				cell.setCellStyle(style);
				cell.setCellValue((String) rsmd.getColumnName(colCount));

			}

			int countTd = 1;
			while (resultSet.next()) {
				row = sheet.createRow(countTd);
				for (int colCount = 1; colCount <= columnCount; colCount++) {
					Cell cell = row.createCell(colCount - 1);
					cell.setCellValue((String) resultSet.getString(colCount));
				}
				countTd++;
			}

			try {
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pstmt = null;
			conn = null;
		}

	}

	private String getTableStringFromQuery(String query) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt =null;
		StringBuilder tableStringFinal = new StringBuilder();

		try {
			conn = dataSource.getConnection();
		
	        pstmt = conn.prepareStatement(query);

		ResultSet resultSet = pstmt.executeQuery();

		
		// String
		// tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

		tableStringFinal
				.append("<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>");

		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnCount = rsmd.getColumnCount();
		// tableString+="<thead> <tr style='background-color:blue;color:white'> ";

		tableStringFinal
				.append("<thead> <tr style='background-color:blue;color:white'> ");

		// The columnCount starts from 1
		for (int colCount = 1; colCount <= columnCount; colCount++) {

			// tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";

			tableStringFinal.append("<th>" + rsmd.getColumnName(colCount)
					+ "</th>");

		}
		// tableString+="</tr></thead><tbody>";

		tableStringFinal.append("</tr></thead><tbody>");
		while (resultSet.next()) {

			// tableString+="<tr>";
			tableStringFinal.append("<tr>");

			for (int colCount = 1; colCount <= columnCount; colCount++) {
				// innerJson.put(resultSet.getString(colCount));
				// tableString+="<td>"+resultSet.getString(colCount)+"</td>";
				tableStringFinal.append("<td>" + resultSet.getString(colCount)
						+ "</td>");
				// Do stuff with name
			}
			// tableString+="</tr>";
			tableStringFinal.append("</tr>");

		}
		// tableString+="</tbody></table>";
		tableStringFinal.append("</tbody></table>");
		// System.out.println("JsonData" + list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		pstmt.close();
		conn.close();
		}
		return tableStringFinal.toString();

	}

	public List<FunctionApplication> getExceptionAndFirstPassAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		
		ArrayList<FunctionApplication> list = new ArrayList<FunctionApplication>();

		
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				String query = "";
				
				String queryCompleted = "  select count( distinct CmAcmCaseidentifier) as count from "
						+ tableConfig.getProperty("ap_completed")
						+ " where  (CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
						+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
						" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " 
						;




				String commonQuery = "";
				// Case Type Where clause
				
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					commonQuery += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}

				// Date Where clause
				commonQuery += " and endTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  ";

				query = queryCompleted + commonQuery;


				System.out.println("AP report 9" + query);
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				FunctionApplication innerOuter = new FunctionApplication();
				innerOuter.setName("");
				ResultSet resultSet = pstmt.executeQuery();
				double totalCount=1;
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName("Completed");
				
					totalCount=Integer.parseInt(resultSet.getString("count"));
					innerFunction.setCount(resultSet.getString("count"));
					innerOuter.value.add(innerFunction);

				}


				 queryCompleted = "  select response,count( distinct CmAcmCaseidentifier) as count from "
						+ tableConfig.getProperty("ap_completed")
						+ " where response in ('Send to Stores','Send to Commercial','Send to Indirect Taxation') "
						+ " and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
						+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
						" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;

				 commonQuery = "";
				// Case Type Where clause
				
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					commonQuery += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}

				// Date Where clause
				commonQuery += " and endTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  group by response";

				query =  queryCompleted + commonQuery;


				System.out.println("AP report 9" + query);
				 pstmt = conn.prepareStatement(query);

				 resultSet = pstmt.executeQuery();
				  double otherCount=0;
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					if(resultSet.getString("response").equals("Send to Stores"))
						innerFunction.setName("Referred to Stores");
					else if(resultSet.getString("response").equals("Send to Commercial"))
						innerFunction.setName("Referred to Location Buyer");
					else if(resultSet.getString("response").equals("Send to Indirect Taxation"))
						innerFunction.setName("Referred to Indirect Taxation");
					
					innerFunction.setCount(resultSet.getString("count"));
					otherCount+=Integer.parseInt(resultSet.getString("count"));
					innerOuter.value.add(innerFunction);

				}
				
				list.add(innerOuter);
				innerOuter = new FunctionApplication();
				innerOuter.setName("Exception Rate and First Pass Rate");
				functionTable innerFunction = new functionTable();
				innerFunction.setName("Exception Rate");
				innerFunction.setCount(""+Math.round(((otherCount/totalCount)*100)*100)/100.00+" %");
				innerOuter.value.add(innerFunction);
				innerFunction = new functionTable();
				innerFunction.setName("First Pass Rate");
				innerFunction.setCount(""+(100-Math.round(((otherCount/totalCount)*100)*100)/100.00)+" %");
				innerOuter.value.add(innerFunction);
				
				list.add(innerOuter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
		
		return list;

	}

	public String getExceptionAndFirstPassAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

	
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		String query = "";
		// Case  Where clause
		if ((!selectedDate.getString("Cases").trim()
						.equals("Completed"))) {


		
			// Case Where clause
			if (selectedDate.has("Cases")) {
				
				if(selectedDate.getString("Cases").trim().equals("Referred to Stores"))
				query += "where response ='Send to Stores' ";
				else if(selectedDate.getString("Cases").trim().equals("Referred to Location Buyer"))
					query += "where response ='Send to Commercial' ";
				else if(selectedDate.getString("Cases").trim().equals("Referred to Indirect Taxation"))
					query += "where response ='Send to Indirect Taxation' ";
				
			}
		} else {
			query += " where gbl_location is not null ";

		}
		
		query +=" and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}



		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getExceptionAndFirstPassExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report2")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		String query = "";
		// Case  Where clause
		if ((!selectedDate.getString("Cases").trim()
						.equals("Completed"))) {


		
			// Case Where clause
			if (selectedDate.has("Cases")) {
				
				
				if(selectedDate.getString("Cases").trim().equals("Referred to Stores"))
				query += "where response ='Send to Stores' ";
				else if(selectedDate.getString("Cases").trim().equals("Referred to Location Buyer"))
					query += "where response ='Send to Commercial' ";
				else if(selectedDate.getString("Cases").trim().equals("Referred to Indirect Taxation"))
					query += "where response ='Send to Indirect Taxation' ";
				
			}
		} else {
			query += " where gbl_location is not null ";

		}
		query +=" and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;
		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}



		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '"
					+ selectedDate.getString("CaseType") + "%' ";

		}

		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryCompleted + query;

		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report9",
				"Exception Rate and First Pass Rate");
	}

	public List getUrgentInvoiceRecievedAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
assignFunctionAppToArray(functionAppBean);

		
		ArrayList<FunctionApplication> list = new ArrayList<FunctionApplication>();

		
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				String query = "";
				query = "select '1' ";

				if (functionAppBean.getDisplayBy().contains("7")) {

					for (int day = 1; day <= 7; day++)
						query += ",CAST(CAST(coalesce(sum(u" + day + "),0) AS DECIMAL(18,4))/coalesce(sum(d" + day + "),1)*100 AS DECIMAL(18,4)) as day" + day
								+ "";

					query += ",CAST(CAST(coalesce(sum(tcu),0) AS DECIMAL(18,4))/coalesce(sum(tcd),1)*100 AS DECIMAL(18,4)) as total from (select '1' ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray(
							"days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 1 else null end as d" + (day + 1);
					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "'   and GBL_PRIORITY='Urgent' then 1 else null end as u" + (day + 1);

					query += ", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' then 1 else null end as tcd" +
							", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' and  GBL_PRIORITY='Urgent' then 1 else null end as tcu" +
							" from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {

					for (int week = 1; week <= 4; week++)
						query += ",CAST(CAST(coalesce(sum(u" + week + "),0) AS DECIMAL(18,4))/coalesce(sum(w" + week + "),1)*100 AS DECIMAL(18,4)) as week"
								+ week + "";

					query += ",CAST(CAST(coalesce(sum(tcu),0) AS DECIMAL(18,4))/coalesce(sum(tcd),1)*100 AS DECIMAL(18,4)) as total from (select '1' ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 1 else null end as w" + (week + 1);
					
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "' and GBL_PRIORITY='Urgent' "
								+ " then 1 else null end as u" + (week + 1);

					query += ", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' then 1 else null end as tcd" +
							", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' and  GBL_PRIORITY='Urgent' then 1 else null end as tcu" +
							" from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					for (int month = 1; month <= 3; month++)
						query += ",CAST(CAST(coalesce(sum(u" + month + "),0) AS DECIMAL(18,4))/coalesce(sum(m" + month + "),1)*100 AS DECIMAL(18,4)) as month"
								+ month + "";

					query += ",CAST(CAST(coalesce(sum(tcu),0) AS DECIMAL(18,4))/coalesce(sum(tcd),1)*100 AS DECIMAL(18,4)) as total from (select '1' ";

					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'"
								+ "and '" + JSONendMonth.getString(month) + "'"
								+ " then 1 else null end as m" + (month + 1);
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "' "
								+ "and '" + JSONendMonth.getString(month) + "' and GBL_PRIORITY='Urgent' "
								+ " then 1 else null end as u" + (month + 1);

					query += ", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' then 1 else null end as tcd" +
							", case when a1.currentDate between" +
							"'"+functionAppBean.getFromDate()+"' and '"+functionAppBean.getToDate()+"' and  GBL_PRIORITY='Urgent' then 1 else null end as tcu" +
							" from ( ";

				}

				String queryWip = "  select CmAcmCaseidentifier,DATE(datecreated) as currentDate,GBL_PRIORITY from "
						+ tableConfig.getProperty("ap_wip")
						+ " where gbl_location is not null and is_active='Y'  ";
				String queryCompleted = "  select CmAcmCaseidentifier,DATE(datecreated) as currentDate,GBL_PRIORITY from "
						+ tableConfig.getProperty("ap_completed")
						+ " where gbl_location is not null ";




				String commonQuery = " and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
						+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
						" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;
				// Case Type Where clause
				
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				


				// Date Where clause
				commonQuery += " and datecreated between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  group by CmAcmCaseidentifier,DATE(datecreated),GBL_PRIORITY ";
				query = query+queryWip+commonQuery+" union all "+queryCompleted + commonQuery;

				query += ") as a1 ) as a2";
			

				System.out.println("AP report 10" + query);
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				FunctionApplication innerOuter = new FunctionApplication();
				innerOuter.setName("");
				ResultSet resultSet = pstmt.executeQuery();
				
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName("% of invoices rescanned");
					if (functionAppBean.getDisplayBy().contains("7")) {

						innerFunction.setDay1(resultSet.getString("day1"));
						innerFunction.setDay2(resultSet.getString("day2"));
						innerFunction.setDay3(resultSet.getString("day3"));
						innerFunction.setDay4(resultSet.getString("day4"));
						innerFunction.setDay5(resultSet.getString("day5"));
						innerFunction.setDay6(resultSet.getString("day6"));
						innerFunction.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerFunction.setWeek1(resultSet.getString("week1"));
						innerFunction.setWeek2(resultSet.getString("week2"));
						innerFunction.setWeek3(resultSet.getString("week3"));
						innerFunction.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerFunction.setPeriod1(resultSet.getString("month1"));
						innerFunction.setPeriod2(resultSet.getString("month2"));
						innerFunction.setPeriod3(resultSet.getString("month3"));

					}
				
					innerFunction.setCount(resultSet.getString("total"));
					innerOuter.value.add(innerFunction);

				}


				 
				
				list.add(innerOuter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
		
		return list;
	}

	public String getUrgentInvoiceRecievedAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) and gbl_priority='Urgent' " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}




		query += " and datecreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query =queryWip+query+" and is_active='Y'  union all "+ queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getUrgentInvoiceRecievedExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) and gbl_priority='Urgent' " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}




		query += " and datecreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query =queryWip+query+" and is_active='Y'  union all "+ queryCompleted + query;

		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report10",
				"Urgent Invoice Recieved");
		
	}
	
	
	
	
	
	
	
	
	
	public List getAverageTimeTakenFromReceiptToPaymentAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {

		assignFunctionAppToArray(functionAppBean);

		
		ArrayList<FunctionApplication> list = new ArrayList<FunctionApplication>();

		
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
               String query = "select '1' ";
				
				String groupBy="";

				if (functionAppBean.getDisplayBy().contains("7")) {

					for (int day = 1; day <= 7; day++)
						query += ",CAST(coalesce(sum(d" + day + "),0)/coalesce(sum(c" + day + "),1) AS DECIMAL(18,6) ) as day" + day + "";
					query += ",CAST(((";
					for (int day = 1; day <= 7; day++)
						query += "coalesce(sum(d" + day + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce(";
					for (int day = 1; day <= 7; day++)
						query += " sum(c" + day + ") +";
					query = query.substring(0, query.length() - 1);
					query += ",1))) AS DECIMAL(18,6) ) as total";
					
					query += " from (select '1' ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray("days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.diff end as d" + (day + 1);
					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.count end as c" + (day + 1);
				
					
					query += " from ( ";
					
					query +="  select d,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
				
					groupBy=" group by d";
				
					
					
					query +="  select CmAcmCaseidentifier,diff, case ";
					
					for (int day = 0; day < JSONdays.length(); day++)
						query += " when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 'd"+ (day + 1)+"' " ;
					
					query += "  end as d from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {

					for (int week = 1; week <= 4; week++)
						query += ", CAST(coalesce(sum(w" + week + "),0)/coalesce(sum(c" + week + "),1) AS DECIMAL(18,6) ) as week" + week
								+ "";
					query += ",CAST(((";
					for (int week = 1; week <= 4; week++)
						query += "coalesce(sum(w" + week + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce(";
					for (int week = 1; week <= 4; week++)
						query += " sum(c" + week + ") +";
					query = query.substring(0, query.length() - 1);
					query += ",1)) AS DECIMAL(18,6) ) as total";
					query += " from (select '1' ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.diff end as w" + (week + 1);
				
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.count end as c" + (week + 1);
				
					
					query += " from ( ";
					
					query +="  select w,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
					
					groupBy=" group by w";
					
					
					
					query +="  select CmAcmCaseidentifier,diff , case ";
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += " when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 'w" + (week + 1)+"'";
					

					query += "  end as w from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					for (int month = 1; month <= 3; month++)
						query += ",CAST(coalesce(sum(m" + month + "),0)/coalesce(sum(c" + month + "),1) AS DECIMAL(18,6) ) as month"
								+ month + "";
					query += ",CAST(((";
					for (int month = 1; month <= 3; month++)
						query += "coalesce(sum(m" + month + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce(";
					for (int month = 1; month <= 3; month++)
						query += " sum(c" + month + " )+";
					query = query.substring(0, query.length() - 1);
					query += ",1))) AS DECIMAL(18,6) ) as total";
					query += " from (select '1' ";
					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					
					
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.count end as m" + (month + 1);
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.diff end as c" + (month + 1);
				
					query += " from ( ";
					query +="  select m,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
					
					groupBy=" group by m";
					
										
					query +="  select CmAcmCaseidentifier,diff, case ";
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += " when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'" + "and '"
								+ JSONendMonth.getString(month) + "'"
								+ " then 'm" + (month + 1)+"' ";
					

					query += " end as m from ( ";

				}
	             String queryCompleted = "  select CmAcmCaseidentifier,DATE(EndTime) as currentDate,CAST(CAST(sum(TIMESTAMPDIFF(8,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/24 AS DECIMAL(18,4) ) as diff from "
						+ tableConfig.getProperty("ap_completed")
						+ " where gbl_location is not null ";




				String commonQuery = " and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
						+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
						" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;
				// Case Type Where clause
				
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				// Case Type Where clause
				if (!functionAppBean.getCaseType().isEmpty()) {
					query += " and CmAcmCaseidentifier LIKE   '"
							+ functionAppBean.getCaseType() + "%' ";

				}



				// Date Where clause
				commonQuery += " and EndTime between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  group by DATE(EndTime),CmAcmCaseidentifier  ";
				query = query+queryCompleted + commonQuery;

				query += " ) as a1) " +groupBy+

						" ) as a2 ) as a3 ";
			

				System.out.println("AP report 11" + query);
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				FunctionApplication innerOuter = new FunctionApplication();
				innerOuter.setName("");
				ResultSet resultSet = pstmt.executeQuery();
				
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName("Average time taken ");
					if (functionAppBean.getDisplayBy().contains("7")) {

						innerFunction.setDay1(resultSet.getString("day1"));
						innerFunction.setDay2(resultSet.getString("day2"));
						innerFunction.setDay3(resultSet.getString("day3"));
						innerFunction.setDay4(resultSet.getString("day4"));
						innerFunction.setDay5(resultSet.getString("day5"));
						innerFunction.setDay6(resultSet.getString("day6"));
						innerFunction.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerFunction.setWeek1(resultSet.getString("week1"));
						innerFunction.setWeek2(resultSet.getString("week2"));
						innerFunction.setWeek3(resultSet.getString("week3"));
						innerFunction.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerFunction.setPeriod1(resultSet.getString("month1"));
						innerFunction.setPeriod2(resultSet.getString("month2"));
						innerFunction.setPeriod3(resultSet.getString("month3"));

					}
				
					innerFunction.setCount(resultSet.getString("total"));
					innerOuter.value.add(innerFunction);

				}


				 
				
				list.add(innerOuter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
		
		return list;
	}

	public String getAverageTimeTakenFromReceiptToPaymentAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		/*String queryWip = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
*/		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' )  " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Case Type Where clause
				if (selectedDate.has("CaseType")) {
					query += " and CmAcmCaseidentifier LIKE '"
							+ selectedDate.getString("CaseType") + "%' ";

				}



		query += " and endTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query = queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getAverageTimeTakenFromReceiptToPaymentExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' )  " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}

		// Case Type Where clause
				if (selectedDate.has("CaseType")) {
					query += " and CmAcmCaseidentifier LIKE '"
							+ selectedDate.getString("CaseType") + "%' ";

				}



		query += " and EndTime between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query =queryCompleted + query;

		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report11",
				"AVG Time From Receipt To Payment");
		
	}
	
	
	
	

	public List getTATforScanningAndindexingAP(FunctionAppBean functionAppBean)
			throws SQLException, JSONException {
assignFunctionAppToArray(functionAppBean);

		
		ArrayList<FunctionApplication> list = new ArrayList<FunctionApplication>();

	//	String caseCondition[]={" CAST(CAST(sum(TIMESTAMPDIFF(8,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/24 AS DECIMAL(18,4) ) "," CAST(CAST(sum(TIMESTAMPDIFF(8,CHAR(endTime-datecreated))) AS DECIMAL(18,8) )/24 AS DECIMAL(18,4) ) "};
		String caseConditionName[]={"Total Time ","No. of cases","Avg Time","Total Time","No. of cases","Avg Time"};
			Connection conn = null;
			PreparedStatement pstmt = null;
			FunctionApplication innerOuter = new FunctionApplication();	
			try {
				for(int countCondition=0;countCondition<6;countCondition++){
				
               String query = "select '1' ";
				
				String groupBy="";

				if (functionAppBean.getDisplayBy().contains("7")) {

					if(countCondition==2||countCondition==5){
					for (int day = 1; day <= 7; day++)
						query += ",CAST(coalesce(sum(d" + day + "),0)/coalesce(sum(c" + day + "),1) AS DECIMAL(18,6) ) as day" + day + "";
					query += ",CAST(((";
					for (int day = 1; day <= 7; day++)
						query += "coalesce(sum(d" + day + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce((";
					for (int day = 1; day <= 7; day++)
						query += "sum(c" + day + ")+";
					query = query.substring(0, query.length() - 1);
					query += "),1))) AS DECIMAL(18,6) ) as total";
					}
					else if(countCondition==0||countCondition==3){
						for (int day = 1; day <= 7; day++)
							query += ",CAST(coalesce(sum(d" + day + "),0) AS DECIMAL(18,6) ) as day" + day + "";
						query += ",CAST(";
						for (int day = 1; day <= 7; day++)
							query += "coalesce(sum(d" + day + "),0)+";
						query = query.substring(0, query.length() - 1);

						query += " AS DECIMAL(18,6) ) as total";
						
					}
                    else if(countCondition==1||countCondition==4){
                    	for (int day = 1; day <= 7; day++)
    						query += ",coalesce(sum(c" + day + "),1) as day" + day + "";
    					query += ",";
    					for (int day = 1; day <= 7; day++)
    						query += "coalesce(sum(c" + day + "),0)+";
    					query = query.substring(0, query.length() - 1);

    					query += "  as total";
						
					}
					
					query += " from (select '1' ";

					JSONArray JSONdays = date.convertDateToFormat(
							functionAppBean.getFromDate(), 7).getJSONArray("days");

					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.diff end as d" + (day + 1);
					for (int day = 0; day < JSONdays.length(); day++)
						query += ", case when d='d"
								+ (day+1)
								+ "' then a2.count end as c" + (day + 1);
				
					
					query += " from ( ";
					
					query +="  select d,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
				
					groupBy=" group by d";
				
					
					
					query +="  select CmAcmCaseidentifier,diff, case ";
					
					for (int day = 0; day < JSONdays.length(); day++)
						query += " when a1.currentDate='"
								+ JSONdays.getString(day)
								+ "' then 'd"+ (day + 1)+"' " ;
					
					query += "  end as d from ( ";

				} else if (functionAppBean.getDisplayBy().contains("4")) {
					 if(countCondition==2||countCondition==5){
					for (int week = 1; week <= 4; week++)
						query += ",CAST(coalesce(sum(w" + week + "),0)/coalesce(sum(c" + week + "),1) AS DECIMAL(18,6) ) as week" + week
								+ "";
					query += ",CAST(((";
					for (int week = 1; week <= 4; week++)
						query += "coalesce(sum(w" + week + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce((";
					for (int week = 1; week <= 4; week++)
						query += "sum(c" + week + ")+";
					query = query.substring(0, query.length() - 1);
					query += "),1))) AS DECIMAL(18,6) ) as total";
					}
					else if(countCondition==0||countCondition==3){
						for (int week = 1; week <= 4; week++)
							query += ",CAST(coalesce(sum(w" + week + "),0) AS DECIMAL(18,6) ) as week" + week
									+ "";
						query += ",CAST(";
						for (int week = 1; week <= 4; week++)
							query += "coalesce(sum(w" + week + "),0)+";
						query = query.substring(0, query.length() - 1);

						query += " AS DECIMAL(18,6) ) as total";
						
					}
                    else if(countCondition==1||countCondition==4){
                    	for (int week = 1; week <= 4; week++)
    						query += ",coalesce(sum(c" + week + "),1)  as week" + week
    								+ "";
    					query += ",";
    					for (int week = 1; week <= 4; week++)
    						query += "coalesce(sum(c" + week + "),0)+";
    					query = query.substring(0, query.length() - 1);

    					query += "  as total";
						
					}
					
					
					query += " from (select '1' ";

					JSONArray JSONstartWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"startWeek");

					JSONArray JSONendWeek = date.convertDateToFormat(
							functionAppBean.getFromDate(), 4).getJSONArray(
							"endWeek");

					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.diff end as w" + (week + 1);
				
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += ", case when w='w"+(week + 1)+"'  then a2.count end as c" + (week + 1);
				
					
					query += " from ( ";
					
					query +="  select w,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
					
					groupBy=" group by w";
					
					
					
					query +="  select CmAcmCaseidentifier,diff , case ";
					for (int week = 0; week < JSONstartWeek.length(); week++)
						query += " when a1.currentDate between '"
								+ JSONstartWeek.getString(week) + "'" + "and '"
								+ JSONendWeek.getString(week) + "'"
								+ " then 'w" + (week + 1)+"'";
					

					query += "  end as w from ( ";

				} else if (functionAppBean.getDisplayBy().contains("3")) {
					  if(countCondition==2||countCondition==5){
					for (int month = 1; month <= 3; month++)
						query += ",CAST(coalesce(sum(m" + month + "),0)/coalesce(sum(c" + month + "),1) AS DECIMAL(18,6) ) as month"
								+ month + "";
					query += ",CAST(((";
					for (int month = 1; month <= 3; month++)
						query += "coalesce(sum(m" + month + "),0)+";
					query = query.substring(0, query.length() - 1);

					query += ")/(coalesce((";
					for (int month = 1; month <= 3; month++)
						query += " sum(c" + month + ") +";
					query = query.substring(0, query.length() - 1);
					query += "),1))) AS DECIMAL(18,6) ) as total";
					  }
					else if(countCondition==0||countCondition==3){
						for (int month = 1; month <= 3; month++)
							query += ",CAST(coalesce(sum(m" + month + "),0) AS DECIMAL(18,6) ) as month"
									+ month + "";
						query += ",CAST(";
						for (int month = 1; month <= 3; month++)
							query += "coalesce(sum(m" + month + "),0)+";
						query = query.substring(0, query.length() - 1);

						query += " AS DECIMAL(18,6) ) as total";
						
					}
                    else if(countCondition==1||countCondition==4){
                    	for (int month = 1; month <= 3; month++)
    						query += ",coalesce(sum(c" + month + "),1) as month"
    								+ month + "";
    					query += ",";
    					for (int month = 1; month <= 3; month++)
    						query += "coalesce(sum(c" + month + "),0)+";
    					query = query.substring(0, query.length() - 1);

    					query += "  as total";
						
					}
					query += " from (select '1' ";
					JSONArray JSONstartMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"startMonth");

					JSONArray JSONendMonth = date.convertDateToFormat(
							functionAppBean.getFromDate(), 3).getJSONArray(
							"endMonth");
					
					
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.diff end as m" + (month + 1);
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += ", case when m='m"+(month + 1)+"' then a2.count end as c" + (month + 1);
				
					query += " from ( ";
					query +="  select m,count( distinct CmAcmCaseidentifier) as count,sum(diff) as diff from (";
					
					groupBy=" group by m";
					
										
					query +="  select CmAcmCaseidentifier,diff, case ";
					for (int month = 0; month < JSONstartMonth.length(); month++)
						query += " when a1.currentDate between '"
								+ JSONstartMonth.getString(month) + "'" + "and '"
								+ JSONendMonth.getString(month) + "'"
								+ " then 'm" + (month + 1)+"' ";
					

					query += " end as m from ( ";

				}
				 String queryWip= "  select CmAcmCaseidentifier,DATE(datecreated) as currentDate, CAST(CAST(sum(TIMESTAMPDIFF(8,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/24 AS DECIMAL(18,4) )  as diff from "
							+ tableConfig.getProperty("ap_wip")
							+ " where gbl_location is not null and is_active='Y'  ";
	             String queryCompleted = "  select CmAcmCaseidentifier,DATE(datecreated) as currentDate, CAST(CAST(sum(TIMESTAMPDIFF(8,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/24 AS DECIMAL(18,4) )  as diff from "
						+ tableConfig.getProperty("ap_completed")
						+ " where gbl_location is not null ";




				String commonQuery = " and ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
						+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
						" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
						" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
						" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) " ;
				// Case Type Where clause
				
				if (!functionAppBean.getBusiness().isEmpty()) {

					commonQuery += " and Gbl_CompanyName in ("
							+ functionAppBean.getBusiness() + ") ";

				}

				if (!functionAppBean.getLocation().isEmpty()) {
					commonQuery += " and gbl_location in ("
							+ functionAppBean.getLocation() + ") ";

				}

				


				// Date Where clause
				commonQuery += " and datecreated between '"
						+ functionAppBean.getFromDate() + " 00:00:00' and '"
						+ functionAppBean.getToDate() + " 23:59:59' ";

				commonQuery += "  group by DATE(datecreated),CmAcmCaseidentifier  ";
				query = query+queryWip + commonQuery+" union all "+queryCompleted + commonQuery;

				query += " ) as a1) " +groupBy+

						" ) as a2 ) as a3 ";
			

				System.out.println("AP report 12" + query);
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				
				innerOuter.setName("");
				ResultSet resultSet = pstmt.executeQuery();
				
				while (resultSet.next()) {
					functionTable innerFunction = new functionTable();

					innerFunction.setName(caseConditionName[countCondition]);
					if (functionAppBean.getDisplayBy().contains("7")) {

						innerFunction.setDay1(resultSet.getString("day1"));
						innerFunction.setDay2(resultSet.getString("day2"));
						innerFunction.setDay3(resultSet.getString("day3"));
						innerFunction.setDay4(resultSet.getString("day4"));
						innerFunction.setDay5(resultSet.getString("day5"));
						innerFunction.setDay6(resultSet.getString("day6"));
						innerFunction.setDay7(resultSet.getString("day7"));

					} else if (functionAppBean.getDisplayBy().contains("4")) {
						innerFunction.setWeek1(resultSet.getString("week1"));
						innerFunction.setWeek2(resultSet.getString("week2"));
						innerFunction.setWeek3(resultSet.getString("week3"));
						innerFunction.setWeek4(resultSet.getString("week4"));

					} else if (functionAppBean.getDisplayBy().contains("3")) {

						innerFunction.setPeriod1(resultSet.getString("month1"));
						innerFunction.setPeriod2(resultSet.getString("month2"));
						innerFunction.setPeriod3(resultSet.getString("month3"));

					}
				
					innerFunction.setCount(resultSet.getString("total"));
					innerOuter.value.add(innerFunction);

				}

				}
				 
				
				list.add(innerOuter);
				
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			pstmt.close();
			conn.close();
			}
		
		return list;
	}

	public String getTATforScanningAndindexingAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report12")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report12")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) and gbl_priority='Urgent' " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}




		query += " and datecreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query =queryWip+query+" and is_active='Y'  union all "+ queryCompleted + query;

		System.out.println("Restquery " + query);

		return getTableStringFromQuery(query);
	}

	public void getTATforScanningAndindexingExcelAP(String columnValue)
			throws SQLException, JSONException {
		System.out.println("JSONARRyy" + columnValue);
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_wip")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_wip") + " cc " + "  ";
		String queryCompleted = "select "
				+ tableConfig.getProperty("ap_report10")
				+ " from "
				+ tableConfig.getProperty("ap_completed")
				+ " where  CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "
				+ tableConfig.getProperty("ap_completed") + " cc " + "  ";
		
		
		String query = "  ";
		// Case  Where clause
		
		
		query +=" where ( CmAcmCaseidentifier LIKE  'AP_POSupplyInvoice%' "
		+ " OR  CmAcmCaseidentifier LIKE  'AP_POServicesInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_SOServiceInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_TMCInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_CustomBRMInvoice%' " +
		" OR CmAcmCaseidentifier LIKE  'AP_CustomNonBRMInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_NonPOVendorInvoice%' " +
		" OR  CmAcmCaseidentifier LIKE  'AP_NonPOEmployeeInvoice%' OR  CmAcmCaseidentifier LIKE  'AP_Invoicemismatch%'" +
		" OR  CmAcmCaseidentifier LIKE  'AP_ERSInvoices%' ) and gbl_priority='Urgent' " ;

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in ("
					+ selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ("
					+ selectedDate.getString("Location") + ")";
		}




		query += " and datecreated between '"
				+ selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query =queryWip+query+" and is_active='Y'  union all "+ queryCompleted + query;

		System.out.println("Restquery " + query);

		generateExcel(query, "AP_Report12",
				"TAT for Scanning & Indexing");
		
	}
	
	

}
