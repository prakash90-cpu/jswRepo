package jsw.report.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

//import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.FunctionApplication;
import jsw.report.viewBean.FunctionValueId;
import jsw.report.viewBean.UserFilter;
import jsw.report.viewBean.functionTable;

public class CORPDaoImpl implements CORPDao {
DataSource dataSource;
SimpleDateFormatte date=new  SimpleDateFormatte();
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	

	
	@Autowired
	private  Properties tableConfig;
	
	String SelectedValue[]=new String[12];
	
	
	
	
	
	
	
	private void assignFunctionAppToArray(FunctionAppBean functionAppBean) {

		System.out.println("%%%%%%%%%%%%%%" + functionAppBean);

		SelectedValue[0] = functionAppBean.getBusiness();SelectedValue[1] = functionAppBean.getLocation();
		SelectedValue[2] = functionAppBean.getDisplayBy();SelectedValue[3] = functionAppBean.getRoles();
		SelectedValue[4] = functionAppBean.getUsers();SelectedValue[5] = functionAppBean.getCaseType();
	    SelectedValue[6] = functionAppBean.getCaseStatus();

	}
	
	
	
	
	public List getNumberOfCasesById(int id,int bucket,String functionName) throws SQLException{
		
		 String query = "select * from user_filter where user_id=?";
			
			
			Connection conn=dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			ResultSet resultSet = pstmt.executeQuery();
			UserFilter userFilter=new UserFilter();
			String filterArray[]=new String[4];
				
			while (resultSet.next()) {
				
				
				filterArray[0]=(resultSet.getString("business_filter"));
				filterArray[1]=(resultSet.getString("location_filter"));
				//filterArray[2]=(resultSet.getString("doctype_filter"));
				/*filterArray[4]=(resultSet.getString("personal_area_filter"));
				filterArray[5]=(resultSet.getString("payroll_area_filter"));
				*/
				
				
			}
			System.out.println("filterArray[0]"+filterArray[0]);
			System.out.println("filterArray[1]"+filterArray[1]);
			
			
			String tableName[]={"Business","Location", "DisplayBy","Roles","Users","CaseType","CaseStatus",};
			String tableColumn[]={ "CompanyDisplayName","LocationDisplayName" ,"Period", "ARRoles","Username","CaseTypeDisplayName","case_status"};
			String tableSecondColumn[]={ "CompanyDisplayName","LocationValue","Period", "ARRoles","Username","CaseTypeDisplayName","description"};
			
				List list=new ArrayList();	
				int selectedCounter=0;
	int countWhereCluse=0;
				for(int countTable=0;countTable<tableName.length;countTable++){
		    
					if(tableName[countTable]!=null){
				if(countTable==0||countTable==1)	{
				if(filterArray[countWhereCluse]!=null)
					query = "select * from "+tableName[countTable]+" where id in ('"+filterArray[countWhereCluse++].replace(",", "','")+"')";
				}
				else if(countTable==4)
				
					query = "select distinct username from (select username from "+tableConfig.getProperty("ap_wip")+""
							+ " union all select username from "+tableConfig.getProperty("ap_completed")+") where username!='' ";
				
				else{
					
					
					if(countTable==2)
						query = "select * from "+tableName[countTable]+" where bucket="+bucket;
					else
					query = "select * from "+tableName[countTable];
			
					
					
					
				}
					}
				
				if(countTable==3||countTable==5)
					query+=" where functions='"+functionName+"'";
					
			System.out.println(query);
			 pstmt = conn.prepareStatement(query);
		
			resultSet = pstmt.executeQuery();
		

	System.out.println(SelectedValue[0]+"  "+SelectedValue[1]+"  "+SelectedValue[2]);

			FunctionApplication funName=new FunctionApplication();	
			funName.setName(tableName[countTable]);
			if(selectedCounter<7)
			{
				System.out.println("*************"+SelectedValue[selectedCounter]);
				if(SelectedValue[selectedCounter]!=null)
				funName.setSelectedValue(SelectedValue[selectedCounter].replace("'", "").trim());
				
					}
			selectedCounter++;
			while (resultSet.next()) {

				FunctionValueId storeOption=new FunctionValueId();
				if(tableName[countTable].equals("DisplayBy")){
					storeOption.setValue( resultSet.getString(tableColumn[countTable])+" -"+resultSet.getString("description")+"");
									
					funName.value.add( storeOption );
				}
					
				else if(tableName[countTable].equals("Location")){
					
					storeOption.setValue( resultSet.getString("LocationValue")+" - "+resultSet.getString(tableColumn[countTable])+"");
					funName.value.add( storeOption );
				}
            else if(tableName[countTable].equals("Business")){
					
               storeOption.setValue( resultSet.getString(tableColumn[countTable]));

             
                funName.value.add( storeOption );
				}
				else{
					storeOption.setValue( resultSet.getString(tableColumn[countTable]));
					
				funName.value.add(storeOption);
				}
				
			funName.columnValue.add(resultSet.getString(tableSecondColumn[countTable]));
				
			
			}

			list.add(funName);
			}

				conn.close();

			return list;
	
	}
	
	
	
	public List getNumberOfCasesCreatedCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {

		System.out.println("functionAppBean=" + functionAppBean);
		assignFunctionAppToArray(functionAppBean);

		//String toDate[] = functionAppBean.getToDate().split("-");
		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		String query = "select a2.businessgroup, a2.locationvalue ";
		String groupByDate = "";
		if (functionAppBean.getDisplayBy().contains("7")) {

			for (int day = 1; day <= 7; day++)
				query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

			query += " from (select a1.businessgroup, a1.locationvalue ";

			JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

			for (int day = 0; day < JSONdays.length(); day++)
				query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.period end as d"
						+ (day + 1);

			query += " from ( ";

			// groupBy
			groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
					+ functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

		} else if (functionAppBean.getDisplayBy().contains("4")) {

			for (int week = 1; week <= 4; week++)
				query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

			query += " from (select a1.businessgroup, a1.locationvalue ";

			JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

			JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

			for (int week = 0; week < JSONstartWeek.length(); week++)
				query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
						+ JSONendWeek.getString(week) + "'" + " then a1.period end as w" + (week + 1);

			query += " from ( ";

			// groupBy
			groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
					+ functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

		} else if (functionAppBean.getDisplayBy().contains("3")) {
			for (int month = 1; month <= 3; month++)
				query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

			query += " from (select a1.businessgroup, a1.locationvalue ";
			JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("startMonth");

			JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
			for (int month = 0; month < JSONstartMonth.length(); month++)
				query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
						+ JSONendMonth.getString(month) + "'" + " then a1.period end as m" + (month + 1);

			query += " from ( ";

			// groupBy
			groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
					+ functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

		}

		query += " select bg.businessgroup, l.locationvalue,Date(DateCreated) as currentDate, coalesce(count( distinct a.CmAcmCaseidentifier),0) as period "
				+ " from ap_wip_copy a, location l , businessgroup_location bgl , businessgroup bg  where  bgl.location_id=l.id "
				+ " and l.LocationValue=a.Gbl_Location and bg.id=bgl.group_id ";

		// Business Where clause
		if (!functionAppBean.getBusiness().isEmpty()) {
			query += "and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
		}

		// Location Where clause
		if (!functionAppBean.getLocation().isEmpty()) {
			query += " and gbl_location in (" + functionAppBean.getLocation() + ")";
		}

		// Role Where clause
		if (!functionAppBean.getRoles().isEmpty()) {
			query += " and Gbl_CurrentRole in (" + functionAppBean.getRoles() + ") ";
		}

		/*
		 * // Username Where clause if(!functionAppBean.getUsers().isEmpty()){
		 * query += " and UserName in ("+functionAppBean.getUsers()+") "; }
		 * 
		 * // Case Type Where clause
		 * if(!functionAppBean.getCaseType().isEmpty()){ query +=
		 * " and WorkflowName in ("+functionAppBean.getCaseType()+") ";
		 * 
		 * }
		 */

		// Date Where clause
		query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
				+ functionAppBean.getToDate() + " 23:59:59' ";

		query += "  group by Gbl_Location ," + groupByDate;

		query += " ) as a1) " +

				" as a2 group by a2.businessgroup,a2.locationvalue order by a2.businessgroup";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-AP report 1" + query);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		ArrayList list = new ArrayList();

		while (resultSet.next()) {
			functionTable innerJson = new functionTable();
			innerJson.setName(resultSet.getString("businessgroup"));
			// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
			innerJson.setLocation(resultSet.getString("locationvalue"));

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

		conn.close();
		return list;

	}

		    
		    
		    public String  getNumberOfCasesCreatedCORP(String columnValueJson) throws SQLException, JSONException{
		    	System.out.println("aaaaaaaajaxxxxxxxxx" + columnValueJson);

		JSONObject selectedDate = new JSONObject(columnValueJson);

		String query = "select distinct F_CaseFolderID,UserName,DateCreated,"
				+ "gbl_location,Gbl_BarCodeDC,Gbl_DocumentSource,Gbl_Amount," + "StepName,StartTime" + ",EndTime"
				+ " from ap_wip_copy a, location l , businessgroup_location bgl  ,"
				+ "businessgroup bg where  bgl.location_id=l.id and l.LocationValue=a.Gbl_Location and bg.id=bgl.group_id and "
				+ " bg.businessgroup='" + selectedDate.getString("busiGrp") + "' ";

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in ('" + selectedDate.getString("Location") + "')";
		}

		// Role Where clause
		if (!selectedDate.getString("Roles").isEmpty()) {
			query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
		}
		query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' group by CmAcmCaseidentifier";

		System.out.println("Restquery" + query);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		// pstmt.setString(1, columnValue.split("@")[0]);
		// pstmt.setString(2, columnValue.split("@")[1]);
		ResultSet resultSet = pstmt.executeQuery();

		String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;
			}
		
	public List getCasesCreatedCompletedCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);

		

		String query = "select Process,stagename,queuename, stepname,response from process_performance where function='AP'";

		
		// slf4jLogger.info("EXTRACT@@@@@-AP report 2"+query);
		System.out.println("EXTRACT@@@@@-AP report 2" + query);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		JSONArray jsonProcessPerformance = new JSONArray();
		while (resultSet.next()) {
			JSONArray innerJson = new JSONArray();

			innerJson.put(resultSet.getString("Process") + " - " + resultSet.getString("stagename"));
			innerJson.put(resultSet.getString("queuename"));
			innerJson.put(resultSet.getString("stepname"));
			innerJson.put(resultSet.getString("response"));

			jsonProcessPerformance.put(innerJson);

		}

		// System.out.println(jsonProcessPerformance);
		ArrayList list = new ArrayList();
		for (int countOuter = 0; countOuter < jsonProcessPerformance.length(); countOuter++) {

			JSONArray innerJson = new JSONArray(jsonProcessPerformance.getString(countOuter));

			// System.out.println(query);

			query = "select 1";
			String groupByDate = "";
			if (functionAppBean.getDisplayBy().contains("7")) {

				for (int day = 1; day <= 7; day++)
					query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

				query += " from (select 1 ";

				JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

				for (int day = 0; day < JSONdays.length(); day++)
					query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.count end as d"
							+ (day + 1);

				query += " from ( ";

				// groupBy
				groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

			} else if (functionAppBean.getDisplayBy().contains("4")) {

				for (int week = 1; week <= 4; week++)
					query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

				query += " from (select 1 ";

				JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4)
						.getJSONArray("startWeek");

				JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

				for (int week = 0; week < JSONstartWeek.length(); week++)
					query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
							+ JSONendWeek.getString(week) + "'" + " then a1.count end as w" + (week + 1);

				query += " from ( ";

				// groupBy
				groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

			} else if (functionAppBean.getDisplayBy().contains("3")) {
				for (int month = 1; month <= 3; month++)
					query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

				query += " from (select 1 ";
				JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3)
						.getJSONArray("startMonth");

				JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
				for (int month = 0; month < JSONstartMonth.length(); month++)
					query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
							+ JSONendMonth.getString(month) + "'" + " then a1.count end as m" + (month + 1);

				query += " from ( ";

				// groupBy
				groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

			}

			query += "  select count(distinct srno) as count,Date(DateCreated) as currentDate from ap_wip_copy where QueueName=? and StepName=? and Response =? ";

			if (!functionAppBean.getBusiness().isEmpty()) {

				query += " and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";

			}

			if (!functionAppBean.getLocation().isEmpty()) {
				query += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

			}

			// Date Where clause
			query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
					+ functionAppBean.getToDate() + " 23:59:59' ";

			query += "  group by Gbl_Location ," + groupByDate;

			query += " ) as a1) " +

					" as a2 ";
			System.out.println("AP report 2" + query);

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, innerJson.getString(1));
			pstmt.setString(2, innerJson.getString(2));
			pstmt.setString(3, innerJson.getString(3));
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				functionTable innerFunction = new functionTable();

				innerFunction.setName(innerJson.getString(0));
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
		}

		// System.out.println("list"+list);
		pstmt.close();
		conn.close();
		return list;

	}
			    public String  getCaseCreatedCompanyCompletedCORP(String columnValue) throws SQLException, JSONException{
				System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String query = "select Process,stagename,queuename, stepname,response from process_performance where function='AP' and"
				+ " StageName='" + selectedDate.getString("StepName").split("-")[1].trim() + "'";
		System.out.println(query);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		JSONArray innerJson = new JSONArray();

		while (resultSet.next()) {

			innerJson.put(resultSet.getString("queuename"));
			innerJson.put(resultSet.getString("stepname"));
			innerJson.put(resultSet.getString("response"));

		}
		System.out.println(innerJson);

		query = "select * from ap_wip_copy where QueueName=? and StepName=? and Response =?";

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (!selectedDate.getString("Roles").isEmpty()) {
			query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
		}
		query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' ";

		System.out.println(query);
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, innerJson.getString(0));
		pstmt.setString(2, innerJson.getString(1));
		pstmt.setString(3, innerJson.getString(2));

		resultSet = pstmt.executeQuery();

		String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;

				}
				
			    
	public List getCasesProcessedCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		assignFunctionAppToArray(functionAppBean);
		

		String query = "select Process,stagename,queuename, stepname,response from process_performance where function='AP'";

		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		JSONArray jsonProcessPerformance = new JSONArray();
		while (resultSet.next()) {
			JSONArray innerJson = new JSONArray();

			innerJson.put(resultSet.getString("Process") + " - " + resultSet.getString("stagename"));
			innerJson.put(resultSet.getString("queuename"));
			innerJson.put(resultSet.getString("stepname"));
			innerJson.put(resultSet.getString("response"));

			jsonProcessPerformance.put(innerJson);

		}

		System.out.println(jsonProcessPerformance);
		ArrayList listOuter = new ArrayList();
		for (int countOuter = 0; countOuter < jsonProcessPerformance.length(); countOuter++) {

			JSONArray innerJson = new JSONArray(jsonProcessPerformance.getString(countOuter));

			FunctionApplication innerOuter = new FunctionApplication();
			innerOuter.setName(innerJson.getString(0));

			query = "select a2.UserName";
			String groupByDate = "";
			if (functionAppBean.getDisplayBy().contains("7")) {

				for (int day = 1; day <= 7; day++)
					query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

				query += " from (select a1.UserName ";

				JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

				for (int day = 0; day < JSONdays.length(); day++)
					query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.count end as d"
							+ (day + 1);

				query += " from ( ";
				// groupBy
				groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

			} else if (functionAppBean.getDisplayBy().contains("4")) {

				for (int week = 1; week <= 4; week++)
					query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

				query += " from (select a1.UserName ";

				JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4)
						.getJSONArray("startWeek");

				JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

				for (int week = 0; week < JSONstartWeek.length(); week++)
					query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
							+ JSONendWeek.getString(week) + "'" + " then a1.count end as w" + (week + 1);

				query += " from ( ";

				// groupBy
				groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

			} else if (functionAppBean.getDisplayBy().contains("3")) {
				for (int month = 1; month <= 3; month++)
					query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

				query += " from (select a1.UserName ";
				JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3)
						.getJSONArray("startMonth");

				JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
				for (int month = 0; month < JSONstartMonth.length(); month++)
					query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
							+ JSONendMonth.getString(month) + "'" + " then a1.count end as m" + (month + 1);

				query += " from ( ";

				// groupBy
				groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate()
						+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

			}

			query += "select UserName,count(*) as count,Date(DateCreated) as currentDate from ap_wip_copy where QueueName=? and StepName=? and Response =? ";

			if (!functionAppBean.getBusiness().isEmpty()) {

				query += " and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
			}
			if (!functionAppBean.getLocation().isEmpty()) {
				query += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

			}

			// Date Where clause
			query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
					+ functionAppBean.getToDate() + " 23:59:59' ";

			query += "  group by username, " + groupByDate;

			query += " ) as a1) " +

					" as a2 ";
			// slf4jLogger.info("EXTRACT@@@@@-AP report 3"+query);
			System.out.println("EXTRACT@@@@@-AP report 3" + query);

			System.out.println(query + innerJson.getString(1) + innerJson.getString(2) + innerJson.getString(3));
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, innerJson.getString(1));
			pstmt.setString(2, innerJson.getString(2));
			pstmt.setString(3, innerJson.getString(3));
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

			listOuter.add(innerOuter);
		}

		System.out.println("listOuter" + listOuter);

		conn.close();
		return listOuter;

	}

	public String  getCasesProcessedCORP(String columnValue) throws SQLException, JSONException {
	System.out.println("JSONARRyy" + columnValue);

		JSONObject selectedDate = new JSONObject(columnValue);

		String query = "select Process,stagename,queuename, stepname,response from process_performance where function='AP' and"
				+ " StageName='" + selectedDate.getString("StepName").split("-")[1].trim() + "'";
		System.out.println(query);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		JSONArray innerJson = new JSONArray();

		while (resultSet.next()) {

			innerJson.put(resultSet.getString("queuename"));
			innerJson.put(resultSet.getString("stepname"));
			innerJson.put(resultSet.getString("response"));

		}
		System.out.println(innerJson);

		query = "select * from ap_wip_copy where QueueName=? and StepName=? and Response =?";

		// Business Where clause
		if (!selectedDate.getString("Business").isEmpty()) {
			query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (!selectedDate.getString("Location").isEmpty()) {
			query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
		}

		// Role Where clause
		if (!selectedDate.getString("Roles").isEmpty()) {
			query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
		}
		query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' ";

		System.out.println(query);
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, innerJson.getString(0));
		pstmt.setString(2, innerJson.getString(1));
		pstmt.setString(3, innerJson.getString(2));

		resultSet = pstmt.executeQuery();

		String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;
	}
			    
			    
			    
			    
			    
public List getRoleWiseTimeTakenCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArray(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);

String query = "select a2.Gbl_CurrentRole ";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("7")) {

for (int day = 1; day <= 7; day++)
query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

query += " from (select a1.Gbl_CurrentRole ";

JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

for (int day = 0; day < JSONdays.length(); day++)
query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.period end as d"
	+ (day + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
+ functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

} else if (functionAppBean.getDisplayBy().contains("4")) {

for (int week = 1; week <= 4; week++)
query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

query += " from (select a1.Gbl_CurrentRole ";

JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

for (int week = 0; week < JSONstartWeek.length(); week++)
query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
	+ JSONendWeek.getString(week) + "'" + " then a1.period end as w" + (week + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
+ functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

} else if (functionAppBean.getDisplayBy().contains("3")) {
for (int month = 1; month <= 3; month++)
query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

query += " from (select a1.Gbl_CurrentRole ";
JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("startMonth");

JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
for (int month = 0; month < JSONstartMonth.length(); month++)
query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
	+ JSONendMonth.getString(month) + "'" + " then a1.period end as m" + (month + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate()
+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

}

query += "select Gbl_CurrentRole,Date(DateCreated) as currentDate, count(*) as period from ap_wip_copy where ";

if (!functionAppBean.getBusiness().isEmpty()) {
query += " Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";

} else
query += " Gbl_CompanyName in ('') ";

if (!functionAppBean.getLocation().isEmpty()) {
query += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

}

// Role Where clause
if (!functionAppBean.getRoles().isEmpty()) {
query += " and Gbl_CurrentRole in (" + functionAppBean.getRoles() + ") ";
}

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

/*
* // Case Type Where clause
* if(!functionAppBean.getCaseType().isEmpty()){ query +=
* " and WorkflowName in ("+functionAppBean.getCaseType()+") ";
* 
* }
*/

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
+ functionAppBean.getToDate() + " 23:59:59' ";

query += "group by Gbl_CurrentRole," + groupByDate;

query += " ) as a1) " +

" as a2 group by a2.Gbl_CurrentRole ";

// slf4jLogger.info("EXTRACT@@@@@-AP report 4"+query);
System.out.println("EXTRACT@@@@@-AP report 4" + query);
System.out.println(query);
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet resultSet = pstmt.executeQuery();

ArrayList list = new ArrayList();
while (resultSet.next()) {
functionTable innerFunction = new functionTable();

innerFunction.setName(resultSet.getString("Gbl_CurrentRole"));
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
pstmt.close();
conn.close();
return list;

}

public String  getRoleWiseTimeTakenCORP(String columnValue) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValue);

String query = "select * from ap_wip_copy where Gbl_CurrentRole=?";

// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

// Role Where clause
if (!selectedDate.getString("Roles").isEmpty()) {
query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
}
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
+ selectedDate.getString("dateTo") + " 23:59:59' ";

System.out.println(query);
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, selectedDate.getString("CurrentRole"));

ResultSet resultSet = pstmt.executeQuery();

String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;

}

public List getTotalCycleTimeCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArray(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
System.out.println("userFunctionArray  " + functionAppBean.getToDate());

String query = "select a2.StepName ";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("7")) {

for (int day = 1; day <= 7; day++)
query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

query += " from (select a1.StepName ";

JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

for (int day = 0; day < JSONdays.length(); day++)
query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.period end as d"
	+ (day + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
+ functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

} else if (functionAppBean.getDisplayBy().contains("4")) {

for (int week = 1; week <= 4; week++)
query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

query += " from (select a1.StepName ";

JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

for (int week = 0; week < JSONstartWeek.length(); week++)
query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
	+ JSONendWeek.getString(week) + "'" + " then a1.period end as w" + (week + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate() + "  00:00:00',coalesce(DateCreated,'"
+ functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

} else if (functionAppBean.getDisplayBy().contains("3")) {
for (int month = 1; month <= 3; month++)
query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

query += " from (select a1.StepName ";
JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("startMonth");

JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
for (int month = 0; month < JSONstartMonth.length(); month++)
query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
	+ JSONendMonth.getString(month) + "'" + " then a1.period end as m" + (month + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate()
+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

}

query += "select StepName,Date(DateCreated) as currentDate, round(sum(duration),2) as period from ap_wip_copy where ";

if (!functionAppBean.getBusiness().isEmpty()) {

query += "Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";

} else
query += "Gbl_CompanyName in ('') ";

if (!functionAppBean.getLocation().isEmpty()) {
// query = "select GblCurrentRole, count(*) as count from
// ap_custombrminvoice where GblCompanyName=(select companyvalue
// from business where
// CompanyDisplayName='"+functionAppBean.getBusiness()+"') and
// gbllocation in ("+userFunctionArray[1]+") group by
// GblCurrentRole";
query += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

}

// Role Where clause
if (!functionAppBean.getRoles().isEmpty()) {
query += " and Gbl_CurrentRole in (" + functionAppBean.getRoles() + ") ";
}

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

/*
* // Case Type Where clause
* if(!functionAppBean.getCaseType().isEmpty()){ query +=
* " and WorkflowName in ("+functionAppBean.getCaseType()+") ";
* 
* }
*/

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
+ functionAppBean.getToDate() + " 23:59:59' ";

query += "group by StepName, " + groupByDate;

query += " ) as a1) " +

" as a2 group by a2.StepName ";
// slf4jLogger.info("EXTRACT@@@@@-AP report 5"+query);
System.out.println("EXTRACT@@@@@-AP report 5" + query);

Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet resultSet = pstmt.executeQuery();

ArrayList list = new ArrayList();
while (resultSet.next()) {
functionTable innerFunction = new functionTable();

innerFunction.setName(resultSet.getString("StepName"));

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
pstmt.close();
conn.close();
return list;

}
			    
public String  getTotalCycleTimeCORP(String columnValue) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValue);

String query = "select * from ap_wip_copy where StepName=?";

// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
query += " and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

// Role Where clause
if (!selectedDate.getString("Roles").isEmpty()) {
query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
}
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
+ selectedDate.getString("dateTo") + " 23:59:59' ";

System.out.println(query);
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, selectedDate.getString("StepName"));

ResultSet resultSet = pstmt.executeQuery();

String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;

}

public List getCasesPendingCORP(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArray(functionAppBean);
/*
* String query =
* "select Process,stagename,queuename, stepname,response from process_performance where function='AP'"
* ; System.out.println(query); PreparedStatement pstmt =
* dataSource.getConnection().prepareStatement(query); ResultSet
* resultSet = pstmt.executeQuery();
*/

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
// System.out.println("userFunctionArray "+ userFunctionArray[0]);

String query = "";
String groupByDate = "";
JSONArray jsonProcessPerformance = new JSONArray("[Gbl_CurrentRole,Gbl_CompanyName]");
Connection conn = dataSource.getConnection();

System.out.println(jsonProcessPerformance);
ArrayList listOuter = new ArrayList();
for (int countOuter = 0; countOuter < jsonProcessPerformance.length(); countOuter++) {
query = "select a2." + jsonProcessPerformance.getString(countOuter) + "";

if (functionAppBean.getDisplayBy().contains("7")) {

for (int day = 1; day <= 7; day++)
query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

query += " from (select a1." + jsonProcessPerformance.getString(countOuter) + " ";

JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

for (int day = 0; day < JSONdays.length(); day++)
query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.count end as d"
		+ (day + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(DAY,'" + functionAppBean.getFromDate()
	+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 2 ";

} else if (functionAppBean.getDisplayBy().contains("4")) {

for (int week = 1; week <= 4; week++)
query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

query += " from (select a1." + jsonProcessPerformance.getString(countOuter) + " ";

JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4)
	.getJSONArray("startWeek");

JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

for (int week = 0; week < JSONstartWeek.length(); week++)
query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
		+ JSONendWeek.getString(week) + "'" + " then a1.count end as w" + (week + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(WEEK,'" + functionAppBean.getFromDate()
	+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 8 ";

} else if (functionAppBean.getDisplayBy().contains("3")) {
for (int month = 1; month <= 3; month++)
query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

query += " from (select a1." + jsonProcessPerformance.getString(countOuter) + " ";
JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3)
	.getJSONArray("startMonth");

JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
for (int month = 0; month < JSONstartMonth.length(); month++)
query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
		+ JSONendMonth.getString(month) + "'" + " then a1.count end as m" + (month + 1);

query += " from ( ";

// groupBy
groupByDate = " TIMESTAMPDIFF(MONTH,'" + functionAppBean.getFromDate()
	+ "  00:00:00',coalesce(DateCreated,'" + functionAppBean.getFromDate() + "  00:00:00')) / 31 ";

}

query += " select " + jsonProcessPerformance.getString(countOuter)
+ ",Date(DateCreated) as currentDate, count( *) as count " + " from ap_wip_copy a where  ";

if (!functionAppBean.getBusiness().isEmpty()) {

query += " Gbl_CompanyName in (" + functionAppBean.getBusiness() + ")  ";
} else {
query += " Gbl_CompanyName in ('')  ";
}

if (!functionAppBean.getLocation().isEmpty()) {

query += " and Gbl_location in (" + functionAppBean.getLocation() + ")  ";
}

// Role Where clause
if (!functionAppBean.getRoles().isEmpty()) {
query += " and Gbl_CurrentRole in (" + functionAppBean.getRoles() + ") ";
}

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

/*
* // Case Type Where clause
* if(!functionAppBean.getCaseType().isEmpty()){ query +=
* " and WorkflowName in ("+functionAppBean.getCaseType()+") ";
* 
* }
*/

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

// Date Where clause
query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
+ functionAppBean.getToDate() + " 23:59:59' ";

query += " group by " + jsonProcessPerformance.getString(countOuter);
query += " , " + groupByDate;

query += " ) as a1) " +

" as a2 group by a2." + jsonProcessPerformance.getString(countOuter);

// slf4jLogger.info("EXTRACT@@@@@-AP report 6"+query);
System.out.println("EXTRACT@@@@@-AP report 6" + query);

/*
* String query = "select "
* +jsonProcessPerformance.getString(countOuter)+
* ",count(*) as count from ap_custombrminvoice group by "
* +jsonProcessPerformance.getString(countOuter);
* System.out.println(query);
*/
PreparedStatement pstmt = conn.prepareStatement(query);

/*
* pstmt.setString(1, innerJson.getString(1)); pstmt.setString(2,
* innerJson.getString(2)); pstmt.setString(3,
* innerJson.getString(3));
*/
ResultSet resultSet = pstmt.executeQuery();

System.out.println("Executed");
ArrayList listInner = new ArrayList();
while (resultSet.next()) {

functionTable innerFunction = new functionTable();

innerFunction.setName(resultSet.getString(jsonProcessPerformance.getString(countOuter)));
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

listInner.add(innerFunction);
}
listOuter.add(listInner);
pstmt.close();
}

System.out.println("listOuter" + listOuter);
conn.close();
return listOuter;
}
			    
			    
public String  getCasesPendingCORP(String columnValue) throws SQLException, JSONException {

JSONObject selectedDate = new JSONObject(columnValue);
String query = "select * from ap_wip_copy where ";

if (selectedDate.has("Busi")) {

query += "  Gbl_CompanyName='" + selectedDate.getString("Busi") + "' ";

} else if (selectedDate.has("Role")) {

query += "  Gbl_CurrentRole='" + selectedDate.getString("Role") + "' ";

}

// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

// Role Where clause
if (!selectedDate.getString("Roles").isEmpty()) {
query += " and Gbl_CurrentRole in (" + selectedDate.getString("Roles") + ") ";
}
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
+ selectedDate.getString("dateTo") + " 23:59:59' ";
System.out.println(query);
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);

ResultSet resultSet = pstmt.executeQuery();

String tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";

ResultSetMetaData rsmd = resultSet.getMetaData();
int columnCount = rsmd.getColumnCount();
tableString+="<thead> <tr style='background-color:blue;color:white'> ";

int countTd=0;

// The columnCount starts from 1
for (int colCount = 1; colCount <= columnCount; colCount++) {

if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<th>"+rsmd.getColumnName(colCount)+"</th>";
else
tableString+="<th  style='display:none'>"+rsmd.getColumnName(colCount)+"</th>";
countTd++; 
}
tableString+="</tr></thead><tbody>";


while (resultSet.next()) {

tableString+="<tr>";
countTd=0;
for (int colCount = 1; colCount <= columnCount; colCount++) {
//innerJson.put(resultSet.getString(colCount));
if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
tableString+="<td>"+resultSet.getString(colCount)+"</td>";
else
tableString+="<td style='display:none'>"+resultSet.getString(colCount)+"</td>";
countTd++;
// Do stuff with name
}
tableString+="</tr>";


}
tableString+="</tbody></table>";

//System.out.println("JsonData" + list);
pstmt.close();
conn.close();
return tableString;
}
		

}
