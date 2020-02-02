package jsw.report.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jsw.report.dateUtil.SimpleDateFormatte;
import jsw.report.excelUtil.ExcellGenerator;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.FunctionApplication;
import jsw.report.viewBean.FunctionValueId;
import jsw.report.viewBean.UserFilter;
import jsw.report.viewBean.functionTable;

public class CommDaoImpl implements CommDao {
	DataSource dataSource;
	ExcellGenerator excellGen=new ExcellGenerator();
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
	
	
		
	
	
	

	 
	 public List getNumberOfCasesCOMMById(int id,int bucket) throws SQLException{
			
		 String query = "select * from user_filter where user_id=?";
			
			
			Connection conn=null;;
			PreparedStatement pstmt = null;
			List list=null;;
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, id);

				ResultSet resultSet = pstmt.executeQuery();
				UserFilter userFilter=new UserFilter();
				String filterArray[]=new String[4];
					
				while (resultSet.next()) {
					
					
					filterArray[0]=(resultSet.getString("business_filter"));
					filterArray[1]=(resultSet.getString("location_filter"));
					/*filterArray[2]=(resultSet.getString("doctype_filter"));
					filterArray[4]=(resultSet.getString("personal_area_filter"));
					filterArray[5]=(resultSet.getString("payroll_area_filter"));
					*/
					
					
				}
				System.out.println("filterArray[0]"+filterArray[0]);
				System.out.println("filterArray[1]"+filterArray[1]);
				
				
				String tableName[]={"Business","Location", "DisplayBy","Roles","Users","CaseType","CaseStatus","PrStatus"};
				String tableColumn[]={ "CompanyDisplayName","LocationDisplayName" ,"Period", "ARRoles","Username","CaseTypeDisplayName","case_status","prstatus"};
				String tableSecondColumn[]={ "CompanyDisplayName","LocationValue","Period", "ARRoles","Username","CaseTypeDisplayName","description","description"};
				
					list = new ArrayList();	
					int selectedCounter=0;
int countWhereCluse=0;
					for(int countTable=0;countTable<tableName.length;countTable++){
				
						if(tableName[countTable]!=null){
							
							
					if(countTable==0||countTable==1)	{
					if(filterArray[countWhereCluse]!=null)
						query = "select * from "+tableName[countTable]+" where id in ('"+filterArray[countWhereCluse++].replace(",", "','")+"')";
					}
					else if(countTable==4)
						query = "select distinct username from (select username from "+tableConfig.getProperty("com_wip")+" union all select username from "+tableConfig.getProperty("com_completed")+") where username!=''";
					else{
						
						
						if(countTable==2)
							query = "select * from "+tableName[countTable]+" where bucket="+bucket;
						else
						query = "select * from "+tableName[countTable];
				
						
						
						
					}
						}
					
					if(countTable==3||countTable==5)
						query+=" where functions='Commercial'";
						
				System.out.println(query);
				 pstmt = conn.prepareStatement(query);

				resultSet = pstmt.executeQuery();


System.out.println(SelectedValue[0]+"  "+SelectedValue[1]+"  "+SelectedValue[2]);

				FunctionApplication funName=new FunctionApplication();	
				funName.setName(tableName[countTable]);
				if(selectedCounter<8)
				{
					System.out.println("*************"+SelectedValue[selectedCounter]);
					if(SelectedValue[selectedCounter]!=null&&SelectedValue[selectedCounter].length()>1)
						
						if(SelectedValue[selectedCounter].contains("'"))
					funName.setSelectedValue(SelectedValue[selectedCounter].substring(1, SelectedValue[selectedCounter].length()-1).trim());
						else
						funName.setSelectedValue(SelectedValue[selectedCounter]);
						
							
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
	 
	
 
 
// COMM Report DAO Impl

public List getNumberOfCasesCreatedCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
System.out.println("Working Directory = " +
       System.getProperty("user.dir"));
System.out.println("functionAppBean=" + functionAppBean);
assignFunctionAppToArrayCOMM(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
// System.out.println("userFunctionArray "+ userFunctionArray[0]);

String query = "select a2.businessgroup, a2.GBL_location ,a2.COLOR_NAME,a2.LOCATIONDISPLAYNAME";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("7")) {

	for (int day = 1; day <= 7; day++)
		query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

	query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME,a1.LOCATIONDISPLAYNAME ";

	JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

	for (int day = 0; day < JSONdays.length(); day++)
		query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.period end as d"
				+ (day + 1);

	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

} else if (functionAppBean.getDisplayBy().contains("4")) {

	for (int week = 1; week <= 4; week++)
		query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

	query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME,a1.LOCATIONDISPLAYNAME ";

	JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

	JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

	for (int week = 0; week < JSONstartWeek.length(); week++)
		query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
				+ JSONendWeek.getString(week) + "'" + " then a1.period end as w" + (week + 1);

	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(32,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";*/

} else if (functionAppBean.getDisplayBy().contains("3")) {
	for (int month = 1; month <= 3; month++)
		query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

	query += " from (select a1.businessgroup, a1.GBL_location,a1.COLOR_NAME,a1.LOCATIONDISPLAYNAME ";
	JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("startMonth");

	JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
	for (int month = 0; month < JSONstartMonth.length(); month++)
		query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
				+ JSONendMonth.getString(month) + "'" + " then a1.period end as m" + (month + 1);

	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(64,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";*/

}

String queryWip = " select bg.businessgroup, a.GBL_location ,l.COLOR_NAME,l.LOCATIONDISPLAYNAME,Date(DateCreated) as currentDate, coalesce(count( distinct a.CmAcmCaseidentifier),0) as period "
		+ " from "+tableConfig.getProperty("com_wip")+" a, businessgroup bg,businessgroup_business bgb,business b,location l "
		+ " where   bgb.business_id=b.id and bgb.businessgroup_id=bg.id and l.locationValue=a.Gbl_location "
		+ " and  b.companyValue=a.GBL_companyname and is_active='Y'   ";

String queryCompleted = " select bg.businessgroup, a.GBL_location ,l.COLOR_NAME,l.LOCATIONDISPLAYNAME,Date(DateCreated) as currentDate, coalesce(count( distinct a.CmAcmCaseidentifier),0) as period "
		+ " from "+tableConfig.getProperty("com_completed")+" a, businessgroup bg,businessgroup_business bgb,business b,location l "
		+ " where   bgb.business_id=b.id and bgb.businessgroup_id=bg.id and l.locationValue=a.Gbl_location "
		+ " and  b.companyValue=a.GBL_companyname ";

String commonQuery="";

// BusinessGroup Where clause
if (!functionAppBean.getBusinessGroup().isEmpty()) {
	commonQuery += "and BusinessGroup ='" + functionAppBean.getBusinessGroup() + "'";
		
}

// Business Where clause
if (!functionAppBean.getBusiness().isEmpty()) {
	commonQuery += "and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
	
	
	
}

// Location Where clause
if (!functionAppBean.getLocation().isEmpty()) {
	commonQuery += " and gbl_location in (" + functionAppBean.getLocation() + ")";


}


// Role Where clause
if (!functionAppBean.getRoles().isEmpty()) {
	commonQuery += " and queueName in (" + functionAppBean.getRoles() + ") "; 
}


// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
	commonQuery += " and UserName in (" + functionAppBean.getUsers() + ") ";

}

// Case Type Where clause
if (!functionAppBean.getCaseType().isEmpty()) {
	commonQuery += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
	
}

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
	commonQuery += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";
	
}

// PR Status Where clause
if (!functionAppBean.getPrStatus().isEmpty()) {
	if(functionAppBean.getPrStatus().equals("'Other'"))
	{
		commonQuery += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
	commonQuery += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
			
}


// Date Where clause
commonQuery += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
		+ functionAppBean.getToDate() + " 23:59:59' ";

commonQuery += "  group by businessgroup, GBL_location,LOCATIONDISPLAYNAME,COLOR_NAME,Date(DateCreated)";
queryWip+=commonQuery;
queryCompleted+=commonQuery;

query +=queryWip+" union all  "+queryCompleted;

query += " ) as a1) " +

		" as a2 group by a2.businessgroup, a2.GBL_location,a2.LOCATIONDISPLAYNAME,a2.COLOR_NAME order by a2.businessgroup";
// System.out.println("****************************@@@@@-AP report
// 1"+new Date("42615.55"));

// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
System.out.println("EXTRACT@@@@@-COMM report 1" + query);
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
				    
				    
public String  getNumberOfCasesCreatedCOMM(String columnValueJson) throws SQLException, JSONException {
System.out.println("aaaaaaaajaxxxxxxxxx" + columnValueJson);

JSONObject selectedDate = new JSONObject(columnValueJson);


String queryWip = "select  "+tableConfig.getProperty("com_report1")+" "

		+ " from "+tableConfig.getProperty("com_wip")+" a";
String queryCompleted = "select  "+tableConfig.getProperty("com_report1")+" "

		+ " from "+tableConfig.getProperty("com_completed")+" a";
String query= ", business b , businessgroup_business bgb  ,"
		+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

// Business group
if (selectedDate.has("busiGrp")) {
	query += "and bg.businessgroup ='" + selectedDate.getString("busiGrp") + "'";
}
if (selectedDate.has("businessGroup")) {
	query += "and bg.businessgroup in ('" + selectedDate.getString("businessGroup") + "') ";
}


// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}


		
// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

if (selectedDate.has("Loc")) {
	query += " and gbl_location ='" + selectedDate.getString("Loc") + "'";
}


// Role Where clause 
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}

// PR Status Where clause
if (selectedDate.has("PrStatus")) {
	if(selectedDate.getString("PrStatus").equals("'Other'"))
	{
		query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
		query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
	
}


query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' ";
/* + "group by CmAcmCaseidentifier"; */





query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

System.out.println("Restquery " + query);
return getTableStringFromQuery(query);
}


public void  getNumberOfCasesCreatedExcelCOMM(String columnValueJson) throws SQLException, JSONException {
System.out.println("aaaaaaaajaxxxxxxxxx" + columnValueJson);

JSONObject selectedDate = new JSONObject(columnValueJson);


String queryWip = "select  "+tableConfig.getProperty("com_report1")+" "

		+ " from "+tableConfig.getProperty("com_wip")+" a";
String queryCompleted = "select  "+tableConfig.getProperty("com_report1")+" "

		+ " from "+tableConfig.getProperty("com_completed")+" a";
String query= ", business b , businessgroup_business bgb  ,"
		+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

// Business group
if (selectedDate.has("busiGrp")) {
	query += "and bg.businessgroup ='" + selectedDate.getString("busiGrp") + "'";
}
if (selectedDate.has("businessGroup")) {
	query += "and bg.businessgroup in ('" + selectedDate.getString("businessGroup") + "') ";
}


// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}


		
// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

if (selectedDate.has("Loc")) {
	query += " and gbl_location ='" + selectedDate.getString("Loc") + "'";
}


// Role Where clause 
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
if (selectedDate.has("PrStatus")) {
	if(selectedDate.getString("PrStatus").equals("'Other'"))
	{
		query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
		query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
	
}
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' ";
/* + "group by CmAcmCaseidentifier"; */





query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

generateExcel(query,"Commercial_Report1","Number of cases created");
}



public List getCasesCreatedCompletedCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArrayCOMM(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
// System.out.println("userFunctionArray "+ userFunctionArray[0]);


// System.out.println(jsonProcessPerformance);
ArrayList list = new ArrayList();



/*
ArrayList<String> casetypeList=new ArrayList<String>();
if (!functionAppBean.getCaseType().isEmpty()) {
	casetypeList.add(functionAppBean.getCaseType());
}
else{
	String sql=" select caseTypeDisplayName from CaseType where functions='Commercial'";

	Connection  conn_first = dataSource.getConnection();
	PreparedStatement pstmt_first = conn_first.prepareStatement(sql);
	ResultSet resultSet_first = pstmt_first.executeQuery();

		System.out.println("Executed");

		while (resultSet_first.next()) {

			casetypeList.add(resultSet_first.getString("caseTypeDisplayName"));
	
}
		conn_first.close();
}



for(int countProcess=0;countProcess<casetypeList.size();countProcess++)

{
*/
	//FunctionApplication innerOuter = new FunctionApplication();
//	innerOuter.setName(casetypeList.get(countProcess));



	// System.out.println(query);

String query = "select a3.stagename,stages,a3.alias_name";

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
		+ tableConfig.getProperty("com_wip")
		+ " where endTime is not null   and is_active='Y'  "
		+ ") cc right join (select * from process_performance where Function='COM' ";

String queryCompleted = "  select stagename,stages,alias_name, CmAcmCaseidentifier ,Date(endTime) as currentDate from ("
		+ " select * from "
		+ tableConfig.getProperty("com_completed")
		+ " "
		+ ") cc right join (select * from process_performance where Function='COM' ";


	String commonQuery=" ";
	// Case Type Where clause
	if (!functionAppBean.getCaseType().isEmpty()) {
		
	
	commonQuery += " and process in ('" + functionAppBean.getCaseType() + "') ";

		
/*		else{
			commonQuery += " and process in ('other') ";
			commonQuery += " ) pp ON cc.gbl_CaseStatus =pp.Response ";			
		}		
*/	}
	
	commonQuery += " ) pp ON cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";

	/*else{
		
		
		commonQuery += " ) pp ON "
				+ "(cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response "
				+ " and workflowname='COM_PRToPOProcess') OR "
		+ "( cc.Response =pp.Response) ";
	}*/
	if (!functionAppBean.getBusiness().isEmpty()) {

		commonQuery += " and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";

	}

	if (!functionAppBean.getLocation().isEmpty()) {
		commonQuery += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

	}
	
	
	  // Role Where clause 
	 if (!functionAppBean.getRoles().isEmpty()) {
		 commonQuery += " and cc.queueName in (" + functionAppBean.getRoles() + ") ";
		 }
	 

	// Username Where clause
	if (!functionAppBean.getUsers().isEmpty()) {
		commonQuery += " and UserName in (" + functionAppBean.getUsers() + ") ";
	}

	

	// Case Type Where clause
	if (!functionAppBean.getCaseType().isEmpty()) {
		commonQuery += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
		
	}
	  
	  
	// Case Status Where clause
	if (!functionAppBean.getCaseStatus().isEmpty()) {
		commonQuery += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

	}
	
	// PR Status Where clause
	if (!functionAppBean.getPrStatus().isEmpty()) {
		if(functionAppBean.getPrStatus().equals("'Other'"))
		{
			commonQuery += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
								
		}
		else
		commonQuery += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
				
	}


	// Date Where clause
	commonQuery += " and endTime between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
			+ functionAppBean.getToDate() + " 23:59:59' ";

	commonQuery += "  group by stagename,stages ,alias_name,Date(endTime),CmAcmCaseidentifier";

	query = query + queryWip + commonQuery + " union all "
			+ queryCompleted + commonQuery;

	query += " ) as a1) " +groupBy+

	" ) as a2 ) as a3 group by stagename,stages,alias_name order by CAST(stages AS INTEGER)";
	System.out.println("Comm report 2" + query);
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
		conn = dataSource.getConnection();
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
			list.add(innerFunction);

		}


			// System.out.println("list"+list);
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



public String  getCaseCreatedCompanyCompletedCOMM(String columnValue) throws SQLException, JSONException {
System.out.println("JSONARRyy" + columnValue);

JSONObject selectedDate = new JSONObject(columnValue);

String queryWip = "select  "+tableConfig.getProperty("com_report2")+"  from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" cc,process_performance pp where "
		+ "  Function='COM' ";

String queryCompleted = "select "+tableConfig.getProperty("com_report2")+" from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_completed")+" cc,process_performance pp where"
		+ "  Function='COM' ";


String query="";


// Case Type Where clause
if (selectedDate.has("CaseType")) {

	
		query += " and process in ('" + selectedDate.getString("CaseType") + "') ";
		
	 /*else {
		query += " and process in ('other') ";
		query += " and cc.gbl_CaseStatus =pp.Response ";
	}*/
}

query += " and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";


// StegeName Where clause
if (selectedDate.has("StageName")) {
	query += "and StageName ='" + selectedDate.getString("StageName").trim() + "' ";
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
if (selectedDate.has("Roles"))
{  
	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";

query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

System.out.println("Restquery " + query);

return getTableStringFromQuery(query);
}


public void  getCaseCreatedCompanyCompletedExcelCOMM(String columnValue) throws SQLException, JSONException {
System.out.println("JSONARRyy" + columnValue);

JSONObject selectedDate = new JSONObject(columnValue);

String queryWip = "select "+tableConfig.getProperty("com_report2")+" from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" cc,process_performance pp where "
		+ "  Function='COM' ";

String queryCompleted = "select "+tableConfig.getProperty("com_report2")+" from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in ( select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_completed")+" cc,process_performance pp where"
		+ "  Function='COM' ";


String query="";


// Case Type Where clause
if (selectedDate.has("CaseType")) {

	
		query += " and process in ('" + selectedDate.getString("CaseType") + "') ";
		
	/* else {
		query += " and process in ('other') ";
		query += " and cc.gbl_CaseStatus =pp.Response ";
	}*/
}

query += " and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";


// StegeName Where clause
if (selectedDate.has("StageName")) {
	query += "and StageName ='" + selectedDate.getString("StageName").trim() + "' ";
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
if (selectedDate.has("Roles"))
{  
	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}

// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";

query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

System.out.println(query);
generateExcel(query,"Commercial_Report2","Cases completed upto step");	
}
					    
public List getCasesProcessedCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
System.out.println("functionAppBean=" + functionAppBean);
assignFunctionAppToArrayCOMM(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
// System.out.println("userFunctionArray "+ userFunctionArray[0]);
ArrayList listMain = new ArrayList();




/*
String query="select distinct userName from(select * from (select * from "+tableConfig.getProperty("com_wip")+" union all select * from "+tableConfig.getProperty("com_completed")+" )"
		+ " where userName is not null ";

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}


// Business Where clause
if (!functionAppBean.getBusiness().isEmpty()) {
	query += " and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
}

// Location Where clause
if (!functionAppBean.getLocation().isEmpty()) {
	query += " and gbl_location in (" + functionAppBean.getLocation() + ")";
}

// Role Where clause
if (!functionAppBean.getRoles().isEmpty()) {
	query += " and queueName in (" + functionAppBean.getRoles() + ") ";
}

// Case Type Where clause
if (!functionAppBean.getCaseType().isEmpty()) {
	query += " and WorkflowName in (" + functionAppBean.getCaseType() + ") ";

}

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
	query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

// Date Where clause
query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
		+ functionAppBean.getToDate() + " 23:59:59' )";
System.out.println("Distinct User-COMM report 3" + query);
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(query);
ResultSet resultSet_user = pstmt.executeQuery();



while (resultSet_user.next()) {
*/		




/*
ArrayList<String> casetypeList=new ArrayList<String>();
if (!functionAppBean.getCaseType().isEmpty()) {
	casetypeList.add(functionAppBean.getCaseType());
}
else{
	String sql=" select caseTypeDisplayName from CaseType where functions='Commercial'";

	Connection  conn_first = dataSource.getConnection();
	PreparedStatement pstmt_first = conn_first.prepareStatement(sql);
	ResultSet resultSet_first = pstmt_first.executeQuery();

		System.out.println("Executed");

		while (resultSet_first.next()) {

			casetypeList.add(resultSet_first.getString("caseTypeDisplayName"));
	
}
		conn_first.close();
}



for(int countProcess=0;countProcess<casetypeList.size();countProcess++)

{
*/	
//	FunctionApplication list = new FunctionApplication();
//	list.setName(casetypeList.get(countProcess));
	
String query = "select a2.UserName, a2.stagename ,stages,a2.COLOR_NAME";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("7")) {

	for (int day = 1; day <= 7; day++)
		query += ",coalesce(sum(d" + day + "),0) as day" + day + "";

	query += " from (select a1.UserName, a1.stagename,stages,a1.COLOR_NAME ";

	JSONArray JSONdays = date.convertDateToFormat(functionAppBean.getFromDate(), 7).getJSONArray("days");

	for (int day = 0; day < JSONdays.length(); day++)
		query += ", case when a1.currentDate='" + JSONdays.getString(day) + "' then a1.period end as d"
				+ (day + 1);

	query += " from ( ";


} else if (functionAppBean.getDisplayBy().contains("4")) {

	for (int week = 1; week <= 4; week++)
		query += ",coalesce(sum(w" + week + "),0) as week" + week + "";

	query += " from (select a1.UserName, a1.stagename,stages,a1.COLOR_NAME ";

	JSONArray JSONstartWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("startWeek");

	JSONArray JSONendWeek = date.convertDateToFormat(functionAppBean.getFromDate(), 4).getJSONArray("endWeek");

	for (int week = 0; week < JSONstartWeek.length(); week++)
		query += ", case when a1.currentDate between '" + JSONstartWeek.getString(week) + "'" + "and '"
				+ JSONendWeek.getString(week) + "'" + " then a1.period end as w" + (week + 1);

	query += " from ( ";

	
} else if (functionAppBean.getDisplayBy().contains("3")) {
	for (int month = 1; month <= 3; month++)
		query += ",coalesce(sum(m" + month + "),0) as month" + month + "";

	query += " from (select a1.UserName, a1.stagename,stages ,a1.COLOR_NAME";
	JSONArray JSONstartMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("startMonth");

	JSONArray JSONendMonth = date.convertDateToFormat(functionAppBean.getFromDate(), 3).getJSONArray("endMonth");
	for (int month = 0; month < JSONstartMonth.length(); month++)
		query += ", case when a1.currentDate between '" + JSONstartMonth.getString(month) + "'" + "and '"
				+ JSONendMonth.getString(month) + "'" + " then a1.period end as m" + (month + 1);

	query += " from ( ";

	
}

String queryWip = "  (select UserName,stagename,stages,COLOR_NAME,count( distinct CmAcmCaseidentifier) as period ,Date(endTime) as currentDate from (select * from "+tableConfig.getProperty("com_wip")+"  where  is_active='Y'  ";


String queryCompleted = " ( select UserName,stagename,stages,COLOR_NAME,count( distinct CmAcmCaseidentifier) as period ,Date(endTime) as currentDate from ( select * from "+tableConfig.getProperty("com_completed");

	String CommonQuery = " ";

	
	CommonQuery += " ) cc  join (select * from " + "process_performance  where function='COM'";



	// Case Type Where clause
if (!functionAppBean.getCaseType().isEmpty()) {

//	if (functionAppBean.getCaseType().equals("COM_PRTOPO")) {
		CommonQuery += " and process in ('" + functionAppBean.getCaseType() + "') ";
	/*
	}*/ /*else {
		CommonQuery += " and process in ('other') ";
		CommonQuery += " ) pp ON cc.gbl_CaseStatus =pp.Response and username!='' ";
	}*/
}

CommonQuery += " ) pp ON cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";

	
	


// Business Where clause
if (!functionAppBean.getBusiness().isEmpty()) {
	CommonQuery += "and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
}

// Location Where clause
if (!functionAppBean.getLocation().isEmpty()) {
	CommonQuery += " and gbl_location in (" + functionAppBean.getLocation() + ")";
}


// Role Where clause 
if (!functionAppBean.getRoles().isEmpty()) {
CommonQuery += " and cc.queueName in (" + functionAppBean.getRoles() + ") "; }

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
	CommonQuery += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

// Case Type Where clause
	if (!functionAppBean.getCaseType().isEmpty()) {
		CommonQuery += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
		
	}

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
	CommonQuery += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}
// PR Status Where clause
if (!functionAppBean.getPrStatus().isEmpty()) {
	if(functionAppBean.getPrStatus().equals("'Other'"))
	{
		CommonQuery += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
	}
	else
	CommonQuery += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
					
	}

// Date Where clause
CommonQuery += " and endTime between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
		+ functionAppBean.getToDate() + " 23:59:59' ";

CommonQuery += "  group by username ,stagename,stages,COLOR_NAME,DATE(endTime))" ;

queryWip+=CommonQuery;
queryCompleted+=CommonQuery;

query+=queryWip+" union all "+queryCompleted;
query += " ) as a1) " +

		" as a2 group by a2.username,a2.stagename,stages,a2.COLOR_NAME order by Username,CAST(stages AS INTEGER)";
// System.out.println("****************************@@@@@-AP report
// 1"+new Date("42615.55"));

// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
System.out.println("EXTRACT@@@@@-COMM report 3" + query);	
Connection conn = null;
PreparedStatement pstmt = null;
try {
	conn = dataSource.getConnection();
	
	pstmt = conn.prepareStatement(query);
	ResultSet resultSet = pstmt.executeQuery();
	
	
	
	while (resultSet.next()) {
		functionTable innerJson = new functionTable();
	
		
		
		innerJson.setName(resultSet.getString("username"));	
		innerJson.setLocation(resultSet.getString("stagename"));
		innerJson.setColor(resultSet.getString("COLOR_NAME"));
	
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
	
		listMain.add(innerJson);
	
	}
	// System.out.println(list);
	/*if(list.value.size()>0)
	listMain.add(list);
	*/
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
finally{
pstmt.close();
conn.close();
}


return listMain;
}

public String  getCasesProcessedCOMM(String columnValue) throws SQLException, JSONException {
System.out.println("JSONARRyy" + columnValue);

JSONObject selectedDate = new JSONObject(columnValue);

String queryWip= "select "+tableConfig.getProperty("com_report3")+" from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" cc,";
String queryCompleted= "select  "+tableConfig.getProperty("com_report3")+"  from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in (select  distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_completed")+" cc,";

String query = "process_performance pp where function='COM' ";

// Case Type Where clause
if (selectedDate.has("CaseType")) {

	/*if (selectedDate.getString("process").equals("COM_PRTOPO")) {*/
		query += " and process in ('" + selectedDate.getString("CaseType") + "') ";
	
	/*} else {
		query += " and process in ('other') ";
		query += " and cc.gbl_CaseStatus =pp.Response ";
	}*/
}

query += " and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";


// stageName Where clause
if (selectedDate.has("stageName")) {
	query += " and stageName='" + selectedDate.getString("stageName").trim() + "'";
}

// stageName Where clause
if (selectedDate.has("userName")) {
	query += " and userName='" + selectedDate.getString("userName").trim() + "'";
}

// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += " and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

// Role Where clause 
if (selectedDate.has("Roles"))
{  
	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}

// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";

query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

System.out.println("Restquery " + query);
return getTableStringFromQuery(query);
}  
					    



public void  getCasesProcessedExcelCOMM(String columnValue) throws SQLException, JSONException {
System.out.println("JSONARRyy" + columnValue);

JSONObject selectedDate = new JSONObject(columnValue);

String queryWip= "select  "+tableConfig.getProperty("com_report3")+"  from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" cc,";
String queryCompleted= "select  "+tableConfig.getProperty("com_report3")+"  from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in (select  distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_completed")+" cc,";

String query = "process_performance pp where function='COM' ";

// Case Type Where clause
if (selectedDate.has("CaseType")) {

	/*if (selectedDate.getString("process").equals("COM_PRTOPO")) {*/
		query += " and process in ('" + selectedDate.getString("CaseType") + "') ";
	
	/*} else {
		query += " and process in ('other') ";
		query += " and cc.gbl_CaseStatus =pp.Response ";
	}*/
}
query += " and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response ";


// stageName Where clause
if (selectedDate.has("stageName")) {
	query += " and stageName='" + selectedDate.getString("stageName").trim() + "'";
}

// stageName Where clause
if (selectedDate.has("userName")) {
	query += " and userName='" + selectedDate.getString("userName").trim() + "'";
}

// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += " and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}

// Role Where clause 
if (selectedDate.has("Roles"))
{  	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}


// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";
}

// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";

query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;

generateExcel(query,"Commercial_Report3","Cases processed by user");

}
					    
public List getRoleWiseTimeTakenCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
System.out.println("functionAppBean=" + functionAppBean);
assignFunctionAppToArrayCOMM(functionAppBean);

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
// System.out.println("userFunctionArray "+ userFunctionArray[0]);

/*String stringQueryCount[] = { " CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) )", "count( distinct CmAcmCaseidentifier )" };
*/List mainList = new ArrayList();

for (int countQuery = 0; countQuery < 2; countQuery++) {
	String query = "select queuename, alias_name,username";
	
	String groupBy = "";
	if (functionAppBean.getDisplayBy().contains("7")) {

		for (int day = 1; day <= 7; day++)
			query += ",coalesce(sum(d" + day + "),0) as day" + day + "";
		
		
		query += " from (select QUEUENAME, ALIAS_NAME,username ";

		JSONArray JSONdays = date.convertDateToFormat(
				functionAppBean.getFromDate(), 7).getJSONArray("days");

		for (int day = 0; day < JSONdays.length(); day++)
			query += ", case when d='d"
					+ (day+1)
					+ "' then a2.count end as d" + (day + 1);
	
		
		query += " from ( ";
		
		   query +="  select QUEUENAME, ALIAS_NAME,username,d, ";
		   if(countQuery==0)
		   query +=" sum(period) as count ";
		   else if(countQuery==1)
		   query +=" count(distinct count) as count ";
		   
		   query += " from (";
			
			groupBy=" group by QUEUENAME, ALIAS_NAME,username,d";
	
		
		
		query +="  select QUEUENAME, ALIAS_NAME,username,count,CURRENTDATE,period,  case ";
		
		for (int day = 0; day < JSONdays.length(); day++)
			query += " when a1.currentDate='"
					+ JSONdays.getString(day)
					+ "' then 'd"+ (day + 1)+"' " ;
		
		query += "  end as d from ( ";

	} else if (functionAppBean.getDisplayBy().contains("4")) {

		for (int week = 1; week <= 4; week++)
			query += ",coalesce(sum(w" + week + "),0)  as week" + week
					+ "";
		
		query += " from (select QUEUENAME, ALIAS_NAME ,username";

		JSONArray JSONstartWeek = date.convertDateToFormat(
				functionAppBean.getFromDate(), 4).getJSONArray(
				"startWeek");

		JSONArray JSONendWeek = date.convertDateToFormat(
				functionAppBean.getFromDate(), 4).getJSONArray(
				"endWeek");

		for (int week = 0; week < JSONstartWeek.length(); week++)
			query += ", case when w='w"+(week + 1)+"'  then a2.count end as w" + (week + 1);
	
	
	
		
		query += " from ( ";
		

        query +="  select QUEUENAME, ALIAS_NAME,username,w, ";
		  if(countQuery==0)
		   query +=" sum(period) as count ";
		   else if(countQuery==1)
		   query +=" count(distinct count) as count ";
		   
		   query += " from (";
		groupBy=" group by QUEUENAME, ALIAS_NAME,username,w";
		
		query +="  select QUEUENAME, ALIAS_NAME,username,count,CURRENTDATE,period,  case ";
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
	
		query += " from (select QUEUENAME, ALIAS_NAME,username ";
		JSONArray JSONstartMonth = date.convertDateToFormat(
				functionAppBean.getFromDate(), 3).getJSONArray(
				"startMonth");

		JSONArray JSONendMonth = date.convertDateToFormat(
				functionAppBean.getFromDate(), 3).getJSONArray(
				"endMonth");
		
		
		for (int month = 0; month < JSONstartMonth.length(); month++)
			query += ", case when m='m"+(month + 1)+"' then a2.count end as m" + (month + 1);
	
		query += " from ( ";
		 query +="  select QUEUENAME, ALIAS_NAME,username,m, ";
		    if(countQuery==0)
		   query +=" sum(period) as count ";
		   else if(countQuery==1)
		   query +=" count(distinct count) as count ";
		   
		   query += " from (";	
		groupBy=" group by QUEUENAME, ALIAS_NAME,username,m";
		
							
		query +="  select QUEUENAME, ALIAS_NAME,username,count,CURRENTDATE,period,  case ";
		for (int month = 0; month < JSONstartMonth.length(); month++)
			query += " when a1.currentDate between '"
					+ JSONstartMonth.getString(month) + "'" + "and '"
					+ JSONendMonth.getString(month) + "'"
					+ " then 'm" + (month + 1)+"' ";
		

		query += " end as m from ( ";

	}

	String queryCompleted = "   select queuename,c.username,r.alias_name,CmAcmCaseidentifier as count" +
						",CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as period " + "";
	/*if(countQuery==0){
		queryCompleted +=" , "+ stringQueryCount[countQuery+1]+" as count ";
	}
*/					queryCompleted += ",Date(endTime) as currentDate from "+tableConfig.getProperty("com_completed")+" c,roles r "
			+ " where  queuename!='' and username!='' and r.arroles=c.queuename and r.functions= 'Commercial' ";
	
	String commonQuery="";

	// Business Where clause
	if (!functionAppBean.getBusiness().isEmpty()) {
		commonQuery += "and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";
	}

	// Location Where clause
	if (!functionAppBean.getLocation().isEmpty()) {
		commonQuery += " and gbl_location in (" + functionAppBean.getLocation() + ")";
	}

	
	  // Role Where clause 
	if (!functionAppBean.getRoles().isEmpty()) {
	  commonQuery += " and c.queueName in (" + functionAppBean.getRoles() +") "; 
	  }
	 

	// Username Where clause
	if (!functionAppBean.getUsers().isEmpty()) {
		commonQuery += " and c.UserName in (" + functionAppBean.getUsers() + ") ";
	}

	
	// Case Type Where clause
	if (!functionAppBean.getCaseType().isEmpty()) {
		commonQuery += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
		
	}
	
	// Case Status Where clause
	if (!functionAppBean.getCaseStatus().isEmpty()) {
		commonQuery += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

	}

	// PR Status Where clause
	if (!functionAppBean.getPrStatus().isEmpty()) {
		if(functionAppBean.getPrStatus().equals("'Other'"))
		{
			commonQuery += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
								
		}
		else
		commonQuery += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
				
	}

	
	// Date Where clause
	commonQuery += " and endTime between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
			+ functionAppBean.getToDate() + " 23:59:59' ";

	
	String queryTotal=commonQuery;
	commonQuery += "  group by queuename,r.alias_name,c.username,DATE(endTime),CmAcmCaseidentifier";

	queryCompleted += commonQuery;
	query += queryCompleted;

	query += " ) as a1) " +groupBy+")"+

	"   as a2)  group by queuename,alias_name ,username order by queuename ";	// System.out.println("****************************@@@@@-AP report
	// 1"+new Date("42615.55"));

	// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
	System.out.println("EXTRACT@@@@@-COMM report 4" + query);
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		ArrayList<functionTable> list = new ArrayList<functionTable>();

		while (resultSet.next()) {
			functionTable innerJson = new functionTable();
			innerJson.setName(resultSet.getString("queuename"));

			innerJson.setLocation(resultSet.getString("username"));
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
		query ="select queuename,c.username,r.alias_name" ;
		
       if(countQuery==0)
        query +=", CAST((CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 ) AS DECIMAL(18,4) ) as count ";
         else if(countQuery==1)
         query +=", count( distinct CmAcmCaseidentifier) as count ";
				query +="from "
				+ tableConfig.getProperty("com_completed")
				+ " c ,roles r  where   r.arroles=c.queuename and r.functions='Commercial' "+queryTotal+"   group by queuename,r.alias_name,c.username  order by queuename";
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
	conn.close();
}
	}
// System.out.println(list);

return mainList;

}

	public String  getRoleWiseTimeTakenCOMM(String columnValue) throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);


		String queryCompleted= " select  "+tableConfig.getProperty("com_report4")+"  from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_completed");
		String query = " where QueueName is not null ";

		// Role Where clause
		if (selectedDate.has("roleName")) {
			query += " and QueueName ='" + selectedDate.getString("roleName") + "'";
		}

		// Role Where clause
		if (selectedDate.has("userName")) {
			query += " and username ='" + selectedDate.getString("userName") + "'";
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
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		
		
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		
		query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query=queryCompleted+query;
		
		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);
	}

	
	
	
	
	public void  getRoleWiseTimeTakenExcelCOMM(String columnValue) throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);



		String queryCompleted= " select  "+tableConfig.getProperty("com_report4")+"  from "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_completed");
		String query = " where QueueName is not null ";

		// Role Where clause
		if (selectedDate.has("roleName")) {
			query += " and QueueName ='" + selectedDate.getString("roleName") + "'";
		}

		// Role Where clause
		if (selectedDate.has("userName")) {
			query += " and username ='" + selectedDate.getString("userName") + "'";
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
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		
		
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		
		query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query=queryCompleted+query;
		
		System.out.println("Restquery " + query);
	generateExcel(query,"Commercial_Report4","Role wise time taken to complete process");	
	}
	
	
	
	
	
	
	public List getTotalCycleTimeCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		assignFunctionAppToArrayCOMM(functionAppBean);

		//String toDate[] = functionAppBean.getToDate().split("-");
		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		System.out.println("userFunctionArray  " + functionAppBean.getToDate());

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
			 
			   query +=" sum(period)/count(distinct count) as count ";
			  
			  
			   
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
			 
			   query +=" sum(period)/count(distinct count) as count ";
			   
			 
			   
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
			  
			   query +=" sum(period)/count(distinct count) as count ";
			
			  
			   
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

		String queryWip = "select pp.StageName,pp.alias_name,Date(endTime) as currentDate, CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as period,count( distinct CmAcmCaseidentifier  ) as count from "+tableConfig.getProperty("com_wip")+" cc,process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and endTime is not null and is_active='Y'   ";

		String queryCompleted = "select pp.StageName,pp.alias_name,Date(endTime) as currentDate, CAST(CAST(sum(TIMESTAMPDIFF(2,CHAR(endTime-startTime))) AS DECIMAL(18,8) )/3600 AS DECIMAL(18,4) ) as period,count( distinct CmAcmCaseidentifier  ) as count from "+tableConfig.getProperty("com_completed")+" cc ,process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response";

		
		String commonQuery="";
	
		if (!functionAppBean.getCaseType().isEmpty()) {

			commonQuery +=" and process in ('" + functionAppBean.getCaseType() + "') ";
		}		
		
		if (!functionAppBean.getBusiness().isEmpty()) {

			commonQuery += " and Gbl_CompanyName in (" + functionAppBean.getBusiness() + ") ";

		} 

		if (!functionAppBean.getLocation().isEmpty()) {
			
			commonQuery += " and gbl_location in (" + functionAppBean.getLocation() + ") ";

		}

		
		  // Role Where clause 
		  if (!functionAppBean.getRoles().isEmpty()) {
			  commonQuery += " and cc.queueName in (" + functionAppBean.getRoles() + ") ";
		  
		  }
		 

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			commonQuery += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		
		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			commonQuery += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
			
		}
		 

		// Case Status Where clause
		if (!functionAppBean.getCaseStatus().isEmpty()) {
			commonQuery += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

		}
		
		// PR Status Where clause
		if (!functionAppBean.getPrStatus().isEmpty()) {
			if(functionAppBean.getPrStatus().equals("'Other'"))
			{
				commonQuery += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
			commonQuery += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
					
		}


		commonQuery += " and endTime between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
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

		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<functionTable> list = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			list = new ArrayList<functionTable>();
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
			
           
            query +=", CAST((CAST(sum(TIMESTAMPDIFF(4,CHAR(endTime-startTime))) AS DECIMAL(18,6) )/60 )/count( distinct CmAcmCaseidentifier)AS DECIMAL(18,4) ) as count ";
            
			query+="from ("
					+ " select stagename,alias_name,CmAcmCaseidentifier,starttime,endtime from "+tableConfig.getProperty("com_wip")+"  " +
							"cc,process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null  and is_active='Y'"
                      + totalQuery+" union all  select stagename,alias_name,CmAcmCaseidentifier,starttime,endtime from "+tableConfig.getProperty("com_completed")+" cc,process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response and CmAcmCaseidentifier LIKE  CONCAT(process,'%') and endTime is not null  "
                              + totalQuery
					+ ") group by stagename,alias_name";
			
			System.out.println("EXTRACT@@@@@-comm report 4" + query);
			pstmt = conn.prepareStatement(query);
	resultSet = pstmt.executeQuery();
	int countList=0;
	while (resultSet.next()) {
		
	list.get(countList++).setCount(resultSet.getString("count"));
	}
			
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

	public String  getTotalCycleTimeCOMM(String columnValue) throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip="select  "+tableConfig.getProperty("com_report5")+"  from "+tableConfig.getProperty("com_wip")+""+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_wip")+" cc ";
		String queryCompleted="select  "+tableConfig.getProperty("com_report5")+"  from "+tableConfig.getProperty("com_completed")+" "+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_completed")+" cc ";
	
		
		String query = ",process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response"
				;
		if (selectedDate.has("CaseType")) {
		query += " and process in ('" +  selectedDate.getString("CaseType") + "') ";
		}
		
		// StageName Where clause
		if (selectedDate.has("StepName")) {
			query += " and pp.stageName='" + selectedDate.getString("StepName") + "'";
		}

		
		
		// Business Where clause
		if (selectedDate.has("Business")) {
			query += " and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (selectedDate.has("Location")) {
			query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
		}

		
		 // Role Where clause 
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;
		
		System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);			}
		
	
	public void  getTotalCycleTimeExcelCOMM(String columnValue) throws SQLException, JSONException {
		JSONObject selectedDate = new JSONObject(columnValue);

		String queryWip="select  "+tableConfig.getProperty("com_report5")+"  from "+tableConfig.getProperty("com_wip")+""+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_wip")+" cc ";
		String queryCompleted="select  "+tableConfig.getProperty("com_report5")+"  from "+tableConfig.getProperty("com_completed")+" "+" where CmAcmCaseidentifier in  (select distinct CmAcmCaseidentifier  from "+tableConfig.getProperty("com_completed")+" cc ";
	
		
		String query = ",process_performance pp where pp.Function='COM' and cc.QueueName=pp.QueueName and cc.StepName=pp.StepName and cc.Response =pp.Response"
				;
		if (selectedDate.has("CaseType")) {
		query += " and process in ('" +  selectedDate.getString("CaseType") + "') ";
		}
		
		// StageName Where clause
		if (selectedDate.has("StepName")) {
			query += " and pp.stageName='" + selectedDate.getString("StepName") + "'";
		}

		
		
		// Business Where clause
		if (selectedDate.has("Business")) {
			query += " and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
		}

		// Location Where clause
		if (selectedDate.has("Location")) {
			query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
		}

		
		 // Role Where clause 
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";

		query=queryWip+query+" and is_active='Y'   union all "+queryCompleted+query;
		
		System.out.println("Restquery " + query);
	generateExcel(query,"Commercial_Report5","Total cyle time to taken to complete process");
		
	}
					  
	public List getCasesPendingCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
		assignFunctionAppToArrayCOMM(functionAppBean);
		
		System.out.println("comming date"+functionAppBean.getFromDate());

		
		String query = "select a2.QueueName,a2.alias_name, a2.stepName ,a2.color_name ";
		String groupByDate = "";
		if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {

			for (int bucket = 0; bucket <= 4; bucket++)
				query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

			query += " from (select a1.QueueName,a1.alias_name, a1.stepName,a1.color_name  ";

		
			for (int bucket = 1; bucket<=17; bucket+=4){
				if(bucket==17)
					query += ", case when a1.pendingDays>" +(bucket-2)+ " then 1 else 0 end as b"+(bucket-1)/4
					;
				else
				query += ", case when a1.pendingDays between " +(bucket-1)+ " and " +(bucket+3)+ " then 1 else 0 end as b"+(bucket-1)/4
						;

				
			}
			query += " from ( ";

			// groupBy
			/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
					+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

		} else if (functionAppBean.getDisplayBy().contains("15/30/45/60+")) {

			for (int bucket = 0; bucket <= 4; bucket++)
				query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

			query += " from (select a1.QueueName,a1.alias_name, a1.stepName,a1.color_name ";

			
			for (int bucket = 1; bucket <= 61; bucket+=15){
				if(bucket==61)
				query += ", case when a1.pendingDays > " + (bucket-1) +  " then 1 else 0 end as b" + (bucket- 1)/15;
				else if(bucket==1)
				query += ", case when a1.pendingDays between " + (bucket-1)  + " and "
						+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
				else 
					query += ", case when a1.pendingDays between " + (bucket)  + " and "
							+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
			}
			query += " from ( ";

			// groupBy
			/*groupByDate = " TIMESTAMPDIFF(32,CHAR('" + functionAppBean.getFromDate()
					+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";*/

		} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
			for (int bucket = 0; bucket <= 4; bucket++)
				query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

			query += " from (select a1.QueueName,a1.alias_name, a1.stepName,a1.color_name ";
			
			for (int bucket = 1; bucket <=5; bucket++){
				
				if(bucket==4)
				query += ", case when a1.pendingDays >" + (bucket-1)*7 + " then 1 else 0 end as b" + (bucket - 1);
				
				else
				query += ", case when a1.pendingDays between " + (bucket-1)*7  + " and "
						+ bucket*7 + "" + " then 1 else 0 end as b" + (bucket - 1);
			
			
			}
			query += " from ( ";

			// groupBy
		/*	groupByDate = " TIMESTAMPDIFF(64,CHAR('" + functionAppBean.getFromDate()
					+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";*/

		}

		query += " select a.QueueName,r.alias_name, a.stepName,ps.color_name, coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -DateCreated)) AS DECIMAL(18,2))/86400),0) as pendingDays "
				+ " from "+tableConfig.getProperty("com_wip")+" a,roles r,process_steps ps where   "
				+ "  endTime is null and r.arroles=queuename and a.stepname=ps.step_name and r.functions='Commercial' and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%'  and is_active='Y'  ";

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
		  query += " and queueName in (" + functionAppBean.getRoles() + ") ";
		  }
		 

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			query += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			query += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
			
		}

		// Case Status Where clause
		if (!functionAppBean.getCaseStatus().isEmpty()) {
			query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

		}

		// PR Status Where clause
		if (!functionAppBean.getPrStatus().isEmpty()) {
			if(functionAppBean.getPrStatus().equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
			query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
					
		}

		
	/*	// Date Where clause
		query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
				+ functionAppBean.getToDate() + " 23:59:59' ";
*/
		query += "  group by QueueName,r.alias_name,a.stepName,ps.color_name ,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -DateCreated)) AS DECIMAL(18,2))/86400)" ;

		query += " ) as a1) " +

				" as a2 group by a2.QueueName,a2.stepName,a2.alias_name,a2.color_name  order by a2.QueueName";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-COMM report 6" + query);
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
				innerJson.setName(resultSet.getString("QueueName"));
				// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
				innerJson.setStepName(resultSet.getString("stepName"));				
				 innerJson.setColor(resultSet.getString("color_name"));
				 innerJson.setAlias(resultSet.getString("alias_name"));
				
				innerJson.setCount("0");
				if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {

					innerJson.setBucket1(resultSet.getString("bucket0"));
					innerJson.setBucket2(resultSet.getString("bucket1"));
					innerJson.setBucket3(resultSet.getString("bucket2"));
					innerJson.setBucket4(resultSet.getString("bucket3"));
					innerJson.setBucket5(resultSet.getString("bucket4"));
					

				} else if (functionAppBean.getDisplayBy().contains("15/30/45/60+")) {
					innerJson.setBucket1(resultSet.getString("bucket0"));
					innerJson.setBucket2(resultSet.getString("bucket1"));
					innerJson.setBucket3(resultSet.getString("bucket2"));
					innerJson.setBucket4(resultSet.getString("bucket3"));
					innerJson.setBucket5(resultSet.getString("bucket4"));
					

				} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {

					innerJson.setBucket1(resultSet.getString("bucket0"));
					innerJson.setBucket2(resultSet.getString("bucket1"));
					innerJson.setBucket3(resultSet.getString("bucket2"));
					innerJson.setBucket4(resultSet.getString("bucket3"));
					innerJson.setBucket5(resultSet.getString("bucket4"));

				}

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
					    
					    
	public String  getCasesPendingCOMM(String columnValue) throws SQLException, JSONException {

		JSONObject selectedDate = new JSONObject(columnValue);
		String query = "select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket   from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null  and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%'   and is_active='Y'  ";

		if (selectedDate.has("currentRole")) {

			query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

		}
		
		if (selectedDate.has("stepName")) {

			query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		  //Bucket
		  if(selectedDate.has("bucketFrom")){
		  if(selectedDate.has("bucketTo")){
		  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
		  }
		  else
			  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) >"+60;
				  
		  }
		    
		  query+=" ) ";
		 
	/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";
	*/	System.out.println("Restquery " + query);
		return getTableStringFromQuery(query);
	} 
	
	
	
	public void  getCasesPendingExcelCOMM(String columnValue) throws SQLException, JSONException {

		JSONObject selectedDate = new JSONObject(columnValue);
		String query = "select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket  from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null  and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%'  and is_active='Y'  ";

		if (selectedDate.has("currentRole")) {

			query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

		}
		
		if (selectedDate.has("stepName")) {

			query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
	    if (selectedDate.has("Roles"))
		 {  
	    	
	    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
		 }
	    
	    
	    
		// Username Where clause
		if (selectedDate.has("Users")) {
			query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
		}

		// Case Type Where clause
		if (selectedDate.has("CaseType")) {
			query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
		
		}

		// Case Status Where clause
		if (selectedDate.has("CaseStatus")) {
			query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

		}
		 
		// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
		
		  //Bucket
		  if(selectedDate.has("bucketFrom")){
		  if(selectedDate.has("bucketTo")){
		  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
		  }
		  else
			  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) >"+60;
				  
		  }
		  
		  query+=" ) ";
	/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
				+ selectedDate.getString("dateTo") + " 23:59:59' )";
	*/	System.out.println("Restquery " + query);
		
	generateExcel(query,"Commercial_Report6","Number of cases pending from creation date");	
	}

private void assignFunctionAppToArrayCOMM(FunctionAppBean functionAppBean){
	 
	 SelectedValue[0]=functionAppBean.getBusiness();  
	 SelectedValue[1]=functionAppBean.getLocation(); 
  SelectedValue[2]=functionAppBean.getDisplayBy();  	
	 SelectedValue[3]=functionAppBean.getRoles();    
	 SelectedValue[4]=functionAppBean.getUsers();
	 SelectedValue[5]=functionAppBean.getCaseType();   
	 SelectedValue[6]=functionAppBean.getCaseStatus();   
	 SelectedValue[7]=functionAppBean.getPrStatus(); 
	 
}



 public List getCasesCompletedCumulativelyCOMM(FunctionAppBean functionAppBean)  throws SQLException, JSONException{
 	assignFunctionAppToArrayCOMM(functionAppBean);
/*
		//String toDate[] = functionAppBean.getToDate().split("-");
		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
		// System.out.println("userFunctionArray "+ userFunctionArray[0]);

		String query = "select a2.BusinessGroup, a2.GBL_location ,a2.COLOR_NAME,a2.LocationDisplayName";
		String groupByDate = "";
		
			for (int bucket = 0; bucket <= 4; bucket++)
				query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

			query += " from (select a1.BusinessGroup, a1.GBL_location,a1.COLOR_NAME,a1.LocationDisplayName";

			
				query += ", case when a1.pendingDays between " +0+ " and " +3+ " then 1 else 0 end as b0"
						;
				query += ", case when a1.pendingDays between " +0+ " and " +7+ " then 1 else 0 end as b1"
						;
			
				query += ", case when a1.pendingDays between " +0+ " and " +10+ " then 1 else 0 end as b2"
						;
				query += ", case when a1.pendingDays between " +0+ " and " +15+ " then 1 else 0 end as b3"
						;

				query += ", case when a1.pendingDays>" +15+ " then 1 else 0 end as b4"
						;
				
			
			query += " from ( ";

			// groupBy
			/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
					+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

		

		query += " select bg.BusinessGroup, GBL_location,l.COLOR_NAME,l.LocationDisplayName, coalesce( CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) ,0) as pendingDays "
				+ " from "+tableConfig.getProperty("com_completed")+" a,business b,businessGroup bg,location l, businessGroup_business bgb where b.id=bgb.business_id and l.locationValue= GBL_location and bg.id=bgb.businessGroup_id and b.companyValue=gbl_CompanyName "
				+ "  ";

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
		  query += " and QueueName in (" + functionAppBean.getRoles() + ") "; 
		  }
		 

		// Username Where clause
		if (!functionAppBean.getUsers().isEmpty()) {
			query += " and UserName in (" + functionAppBean.getUsers() + ") ";
		}

		// Case Type Where clause
		if (!functionAppBean.getCaseType().isEmpty()) {
			query += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
			
		}

		// Case Status Where clause
		if (!functionAppBean.getCaseStatus().isEmpty()) {
			query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

		}

		// PR Status Where clause
		if (!functionAppBean.getPrStatus().isEmpty()) {
			if(functionAppBean.getPrStatus().equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
			query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
					
		}

		
		// Date Where clause
		query += " and endTime between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
				+ functionAppBean.getToDate() + " 23:59:59' ";
		
	//	query +="and a.id in (select max(aa.id) from "+tableConfig.getProperty("com_completed")+" aa group by CmAcmCaseidentifier)";

		

		query += "  group by BusinessGroup,GBL_location,COLOR_NAME ,LocationDisplayName ,CmAcmCaseidentifier,"
				+ " CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) " ;

		query += " ) as a1) " +

				" as a2 group by a2.BusinessGroup,a2.GBL_location,a2.COLOR_NAME,a2.LocationDisplayName order by a2.BusinessGroup";
		// System.out.println("****************************@@@@@-AP report
		// 1"+new Date("42615.55"));

		// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
		System.out.println("EXTRACT@@@@@-COMM report 7" + query);
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
				innerJson.setName(resultSet.getString("BusinessGroup"));
				// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
				innerJson.setStepName(resultSet.getString("GBL_location"));
				innerJson.setColor(resultSet.getString("COLOR_NAME"));
				innerJson.setAlias(resultSet.getString("LocationDisplayName"));

				innerJson.setCount("0");
				
					innerJson.setBucket1(resultSet.getString("bucket0"));
					innerJson.setBucket2(resultSet.getString("bucket1"));
					innerJson.setBucket3(resultSet.getString("bucket2"));
					innerJson.setBucket4(resultSet.getString("bucket3"));
					innerJson.setBucket5(resultSet.getString("bucket4"));
					

				
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

public String  getCasesCompletedCumulativelyCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);



String queryCompleted = "select  "+tableConfig.getProperty("com_report7")+", CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) as Bucket  from  "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in (  select distinct CmAcmCaseidentifier"

		+ " from "+tableConfig.getProperty("com_completed")+" a";
String query= ", business b , businessgroup_business bgb  ,"
		+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

// Business group
if (selectedDate.has("busiGroup")) {
	query += "and bg.businessgroup ='" + selectedDate.getString("busiGroup") + "'";
}
if (selectedDate.has("businessGroup")) {
	query += "and bg.businessgroup in ('" + selectedDate.getString("businessGroup") + "') ";
}



// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}
if (selectedDate.has("Loc")) {
	query += " and gbl_location ='" + selectedDate.getString("Loc") + "'";
}

// Role Where clause 
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

//Bucket
if(selectedDate.has("bucketFrom")){
if(selectedDate.has("bucketTo")){
query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
}
else
	  query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  >"+15;
	
}



query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";
/* + "group by CmAcmCaseidentifier"; */


query=queryCompleted+query;

System.out.println("Restquery " + query);
return getTableStringFromQuery(query);
}


public void  getCasesCompletedCumulativelyExcelCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);


String queryCompleted = "select  "+tableConfig.getProperty("com_report7")+", CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400) as Bucket  from  "+tableConfig.getProperty("com_completed")+" where CmAcmCaseidentifier in (  select distinct CmAcmCaseidentifier"

		+ " from "+tableConfig.getProperty("com_completed")+" a";
String query= ", business b , businessgroup_business bgb  ,"
		+ "businessgroup bg where  bgb.business_id=b.id and b.companyValue=a.Gbl_companyName and bg.id=bgb.businessgroup_id  ";

// Business group
if (selectedDate.has("busiGroup")) {
	query += "and bg.businessgroup ='" + selectedDate.getString("busiGroup") + "'";
}
if (selectedDate.has("businessGroup")) {
	query += "and bg.businessgroup in ('" + selectedDate.getString("businessGroup") + "') ";
}



// Business Where clause
if (!selectedDate.getString("Business").isEmpty()) {
	query += "and Gbl_CompanyName in (" + selectedDate.getString("Business") + ") ";
}

// Location Where clause
if (!selectedDate.getString("Location").isEmpty()) {
	query += " and gbl_location in (" + selectedDate.getString("Location") + ")";
}
if (selectedDate.has("Loc")) {
	query += " and gbl_location ='" + selectedDate.getString("Loc") + "'";
}

// Role Where clause 
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

//Bucket
if(selectedDate.has("bucketFrom")){
if(selectedDate.has("bucketTo")){
query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
}
else
	  query += " and   CEILING(CAST(TIMESTAMPDIFF(2,CHAR(DateLastmodified-DateCreated)) AS DECIMAL(18,2))/86400)  >"+15;
	
}



query += " and endTime between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";
/* + "group by CmAcmCaseidentifier"; */


query=queryCompleted+query;

System.out.println("Restquery " + query);

generateExcel(query,"Commercial_Report7","Cases completed cumulatively");
}
	
	
	
	
	

public List getCasesPendingForProcesssingCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArrayCOMM(functionAppBean);

/*	//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
// System.out.println("userFunctionArray "+ userFunctionArray[0]);

String query = "select a2.QueueName, a2.stepName ,a2.alias_name,a2.color_name";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("2/7/15/15+")) {

	for (int bucket = 0; bucket < 4; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.alias_name,a1.color_name ";


	query += ", case when a1.pendingDays between " +0+ " and " +2+ " then 1 else 0 end as b0";
	query += ", case when a1.pendingDays between " +3+ " and " +7+ " then 1 else 0 end as b1";
	query += ", case when a1.pendingDays between " +8+ " and " +15+ " then 1 else 0 end as b2";
		
		
		
	query += ", case when a1.pendingDays>" +15+ " then 1 else 0 end as b3";
			
		

	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

} else if (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {

	for (int bucket = 0; bucket <= 3; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.alias_name,a1.color_name ";

	
	for (int bucket = 1; bucket <= 46; bucket+=15){
		if(bucket==46)
		query += ", case when a1.pendingDays > " + (bucket-1) +  " then 1 else 0 end as b" + (bucket- 1)/15;
		else
		query += ", case when a1.pendingDays between " + (bucket-1)  + " and "
				+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
	}
	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(32,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";*/

} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
	for (int bucket = 0; bucket <= 4; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.alias_name,a1.color_name ";
	
	for (int bucket = 1; bucket <=5; bucket++){
		
		if(bucket==4)
		query += ", case when a1.pendingDays >" + (bucket-1)*7 + " then 1 else 0 end as b" + (bucket - 1);
		
		else
		query += ", case when a1.pendingDays between " + (bucket-1)*7  + " and "
				+ bucket*7 + "" + " then 1 else 0 end as b" + (bucket - 1);
	
	
	}
	query += " from ( ";

	// groupBy
/*	groupByDate = " TIMESTAMPDIFF(64,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";*/

}

query += " select a.QueueName, a.stepName,r.alias_name,ps.color_name, coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400),0) as pendingDays "
		+ " from  "+tableConfig.getProperty("com_wip")+" a, roles r,process_steps ps WHERE"
		+ "  endTime is null and r.arroles=queuename and a.stepname=ps.step_name and r.functions='Commercial'  and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%'  and is_active='Y'  ";

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
query += " and queueName in (" + functionAppBean.getRoles() + ") ";
}


// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
	query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

// Case Type Where clause
if (!functionAppBean.getCaseType().isEmpty()) {
	query += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
	
}

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
	query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

// PR Status Where clause
		if (!functionAppBean.getPrStatus().isEmpty()) {
			if(functionAppBean.getPrStatus().equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
			query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
					
		}


/*// Date Where clause
query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
		+ functionAppBean.getToDate() + " 23:59:59' ";
*/
query += "  group by QueueName,a.stepName, r.alias_name,ps.color_name,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400)" ;

query += " ) as a1) " +

		" as a2 group by a2.QueueName,a2.stepName,a2.alias_name,a2.color_name order by a2.QueueName";
// System.out.println("****************************@@@@@-AP report
// 1"+new Date("42615.55"));

// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
System.out.println("EXTRACT@@@@@-COMM report 8" + query);
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
		innerJson.setName(resultSet.getString("QueueName"));
		// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
		innerJson.setStepName(resultSet.getString("stepName"));
		innerJson.setAlias(resultSet.getString("alias_name"));
		innerJson.setColor(resultSet.getString("color_name"));
		innerJson.setCount("0");
		if (functionAppBean.getDisplayBy().contains("2/7/15/15+")) {
	
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
	
			
	
		} else if (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
			
	
		} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
	
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
			innerJson.setBucket5(resultSet.getString("bucket4"));
	
		}
	
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



public String  getCasesPendingForProcesssingCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);
String query = "select  "+tableConfig.getProperty("com_report8")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) as Bucket  from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" where endTime is null  and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%'  and is_active='Y'   ";

if (selectedDate.has("currentRole")) {

	query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

}

if (selectedDate.has("stepName")) {

	query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}

// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	
//Bucket
if(selectedDate.has("bucketFrom")){
if(selectedDate.has("bucketTo")){
query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
}
else
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) >"+selectedDate.getString("bucketFrom");
		  
}

query += " ) ";
/*
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";*/
System.out.println("Restquery " + query);
return getTableStringFromQuery(query);
}



public void  getCasesPendingForProcesssingExcelCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);
String query = "select  "+tableConfig.getProperty("com_report8")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -startTime)) AS DECIMAL(18,2))/86400) as Bucket  from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from "+tableConfig.getProperty("com_wip")+" where  endTime is null  and  CmAcmCaseidentifier not LIKE  'COM_PRTOPO%' and is_active='Y'   ";

if (selectedDate.has("currentRole")) {

	query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

}

if (selectedDate.has("stepName")) {

	query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
if (selectedDate.has("Roles"))
{  
	
	query += " and QueueName in (" + selectedDate.getString("Roles") + ") "; 
}



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
		if (selectedDate.has("PrStatus")) {
			if(selectedDate.getString("PrStatus").equals("'Other'"))
			{
				query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
									
			}
			else
				query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
			
		}
	

//Bucket
if(selectedDate.has("bucketFrom")){
if(selectedDate.has("bucketTo")){
query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
}
else
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) >"+selectedDate.getString("bucketFrom");
		  
}


query += " ) ";
/*
query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";
*/
System.out.println("Restquery " + query);

generateExcel(query,"Commercial_Report8","Number of cases pending from last role start time");	
}







public List getCasesPendingWithPRTOPOCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
	assignFunctionAppToArrayCOMM(functionAppBean);
	
	System.out.println("comming date"+functionAppBean.getFromDate());

	
	String query = "select a2.QueueName, a2.stepName ,a2.color_name ";
	String groupByDate = "";
	if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {

		for (int bucket = 0; bucket <= 4; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

		query += " from (select a1.QueueName,a1.alias_name, a1.stepName,a1.color_name  ";

	
		for (int bucket = 1; bucket<=17; bucket+=4){
			if(bucket==17)
				query += ", case when a1.pendingDays>" +(bucket-2)+ " then 1 else 0 end as b"+(bucket-1)/4
				;
			else
			query += ", case when a1.pendingDays between " +(bucket-1)+ " and " +(bucket+3)+ " then 1 else 0 end as b"+(bucket-1)/4
					;

			
		}
		query += " from ( ";

		// groupBy
		/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
				+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

	} else if (functionAppBean.getDisplayBy().contains("15/30/45/60+")) {

		for (int bucket = 0; bucket <= 4; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

		query += " from (select a1.QueueName, a1.stepName,a1.color_name ";

		
		for (int bucket = 1; bucket <= 61; bucket+=15){
			if(bucket==61)
			query += ", case when a1.pendingDays > " + (bucket-1) +  " then 1 else 0 end as b" + (bucket- 1)/15;
			else if(bucket==1)
			query += ", case when a1.pendingDays between " + (bucket-1)  + " and "
					+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
			else 
				query += ", case when a1.pendingDays between " + (bucket)  + " and "
						+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
		}
		query += " from ( ";

		// groupBy
		/*groupByDate = " TIMESTAMPDIFF(32,CHAR('" + functionAppBean.getFromDate()
				+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";*/

	} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
		for (int bucket = 0; bucket <= 4; bucket++)
			query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

		query += " from (select a1.QueueName, a1.stepName,a1.color_name ";
		
		for (int bucket = 1; bucket <=5; bucket++){
			
			if(bucket==4)
			query += ", case when a1.pendingDays >" + (bucket-1)*7 + " then 1 else 0 end as b" + (bucket - 1);
			
			else
			query += ", case when a1.pendingDays between " + (bucket-1)*7  + " and "
					+ bucket*7 + "" + " then 1 else 0 end as b" + (bucket - 1);
		
		
		}
		query += " from ( ";

		// groupBy
	/*	groupByDate = " TIMESTAMPDIFF(64,CHAR('" + functionAppBean.getFromDate()
				+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";*/

	}

	query += "select a.QueueName, a.stepName,ps.color_name, coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -DateCreated)) AS DECIMAL(18,2))/86400),0) as pendingDays "
			+ " from (  ";
	
	
	query += " select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
			" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
			" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
			" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
			" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
			"  else 'GBS' END as QueueName, stepname,datecreated,CmAcmCaseidentifier "
			+ " from "+tableConfig.getProperty("com_wip")+" where endTime is null and is_active='Y'   ";
	
	
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
	  query += " and queueName in (" + functionAppBean.getRoles() + ") ";
	  }
	 

	// Username Where clause
	if (!functionAppBean.getUsers().isEmpty()) {
		query += " and UserName in (" + functionAppBean.getUsers() + ") ";
	}

	// Case Type Where clause
	if (!functionAppBean.getCaseType().isEmpty()) {
		query += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
		
	}

	// Case Status Where clause
	if (!functionAppBean.getCaseStatus().isEmpty()) {
		query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

	}

	// PR Status Where clause
	if (!functionAppBean.getPrStatus().isEmpty()) {
		if(functionAppBean.getPrStatus().equals("'Other'"))
		{
			query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
								
		}
		else
		query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
				
	}

	
/*	// Date Where clause
	query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
			+ functionAppBean.getToDate() + " 23:59:59' ";
*/
	
	query += " ) a,process_steps ps where   "
			+ "     a.stepname=ps.step_name and ps.function_area='COMM' ";

	
	
	// super role Where clause
		if (!functionAppBean.getAgingBy().isEmpty()) {
			query += " and a.QueueName in ('" + functionAppBean.getAgingBy() + "') ";

		}
	
	query += "  group by QueueName,a.stepName,ps.color_name ,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -DateCreated)) AS DECIMAL(18,2))/86400)" ;

	query += " )  as a1) " +

			" as a2 group by a2.QueueName,a2.stepName,a2.color_name  order by a2.QueueName";
	// System.out.println("****************************@@@@@-AP report
	// 1"+new Date("42615.55"));

	// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
	System.out.println("EXTRACT@@@@@-COMM report 6" + query);
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
			innerJson.setName(resultSet.getString("QueueName"));
			// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
			innerJson.setStepName(resultSet.getString("stepName"));				
			 innerJson.setColor(resultSet.getString("color_name"));
			 innerJson.setAlias(resultSet.getString("QueueName"));
			
			innerJson.setCount("0");
			if (functionAppBean.getDisplayBy().contains("3/7/11/15/15+")) {

				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));
				innerJson.setBucket5(resultSet.getString("bucket4"));
				

			} else if (functionAppBean.getDisplayBy().contains("15/30/45/60+")) {
				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));
				innerJson.setBucket5(resultSet.getString("bucket4"));
				

			} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {

				innerJson.setBucket1(resultSet.getString("bucket0"));
				innerJson.setBucket2(resultSet.getString("bucket1"));
				innerJson.setBucket3(resultSet.getString("bucket2"));
				innerJson.setBucket4(resultSet.getString("bucket3"));
				innerJson.setBucket5(resultSet.getString("bucket4"));

			}

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
				    
				    
public String  getCasesPendingWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {

	JSONObject selectedDate = new JSONObject(columnValue);
	String query = "  select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket   from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from " ;
	query += " ( select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
			" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
			" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
			" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
			" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
			"  else 'GBS' END as QueueName,CmAcmCaseidentifier from "+
			""+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null  and is_active='Y'     ";

	
	
	if (selectedDate.has("stepName")) {

		query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
    if (selectedDate.has("Roles"))
	 {  
    	
    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
	 }
    
    
    
	// Username Where clause
	if (selectedDate.has("Users")) {
		query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
	}

	// Case Type Where clause
	if (selectedDate.has("CaseType")) {
		query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
	
	}

	// Case Status Where clause
	if (selectedDate.has("CaseStatus")) {
		query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

	}
	// PR Status Where clause
	if (selectedDate.has("PrStatus")) {
		if(selectedDate.getString("PrStatus").equals("'Other'"))
		{
			query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
								
		}
		else
			query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
		
	}

	  //Bucket
	  if(selectedDate.has("bucketFrom")){
	  if(selectedDate.has("bucketTo")){
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
	  }
	  else
		  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) >"+60;
			  
	  }
	    
	  query+=" ) where queuename is not null " ;
	  if (selectedDate.has("currentRole")) {

			query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

		}
	  query+="" +
	  		") ";
	 
/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
			+ selectedDate.getString("dateTo") + " 23:59:59' )";
*/	System.out.println("Restquery " + query);
	return getTableStringFromQuery(query);
} 



public void  getCasesPendingExcelWithPRTOPOCOMM(String columnValue) throws SQLException, JSONException {

	JSONObject selectedDate = new JSONObject(columnValue);
	String query = "  select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket   from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from " ;
	query += " ( select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
			" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
			" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
			" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
			" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
			"  else 'GBS' END as QueueName,CmAcmCaseidentifier from "+
			""+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null  and is_active='Y'     ";

	
	
	if (selectedDate.has("stepName")) {

		query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
    if (selectedDate.has("Roles"))
	 {  
    	
    	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
	 }
    
    
    
	// Username Where clause
	if (selectedDate.has("Users")) {
		query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
	}

	// Case Type Where clause
	if (selectedDate.has("CaseType")) {
		query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";
	
	}

	// Case Status Where clause
	if (selectedDate.has("CaseStatus")) {
		query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

	}
	// PR Status Where clause
	if (selectedDate.has("PrStatus")) {
		if(selectedDate.getString("PrStatus").equals("'Other'"))
		{
			query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
								
		}
		else
			query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
		
	}

	  //Bucket
	  if(selectedDate.has("bucketFrom")){
	  if(selectedDate.has("bucketTo")){
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
	  }
	  else
		  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -dateCreated)) AS DECIMAL(18,2))/86400) >"+60;
			  
	  }
	    
	  query+=" ) where queuename is not null " ;
	  if (selectedDate.has("currentRole")) {

			query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

		}
	  query+="" +
	  		") ";
	 
/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
			+ selectedDate.getString("dateTo") + " 23:59:59' )";
*/	System.out.println("Restquery " + query);
	
generateExcel(query,"Commercial_Report9","Cases pending from creation");	
}









public List getCasesPendingForProcesssingWithPRTOPOCOMM(FunctionAppBean functionAppBean) throws SQLException, JSONException {
assignFunctionAppToArrayCOMM(functionAppBean);

/*	//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
// System.out.println("userFunctionArray "+ userFunctionArray[0]);

String query = "select a2.QueueName, a2.stepName ,a2.color_name";
String groupByDate = "";
if (functionAppBean.getDisplayBy().contains("2/7/15/15+")) {

	for (int bucket = 0; bucket < 4; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.color_name ";


	query += ", case when a1.pendingDays between " +0+ " and " +2+ " then 1 else 0 end as b0";
	query += ", case when a1.pendingDays between " +3+ " and " +7+ " then 1 else 0 end as b1";
	query += ", case when a1.pendingDays between " +8+ " and " +15+ " then 1 else 0 end as b2";
		
		
		
	query += ", case when a1.pendingDays>" +15+ " then 1 else 0 end as b3";
			
		

	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(16,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 2 ";*/

} else if (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {

	for (int bucket = 0; bucket <= 3; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.color_name ";

	
	for (int bucket = 1; bucket <= 46; bucket+=15){
		if(bucket==46)
		query += ", case when a1.pendingDays > " + (bucket-1) +  " then 1 else 0 end as b" + (bucket- 1)/15;
		else
		query += ", case when a1.pendingDays between " + (bucket-1)  + " and "
				+(bucket+14) + "" + " then 1 else 0 end as b" + (bucket- 1)/15;
	}
	query += " from ( ";

	// groupBy
	/*groupByDate = " TIMESTAMPDIFF(32,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 8 ";*/

} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
	for (int bucket = 0; bucket <= 4; bucket++)
		query += ",coalesce(sum(b" + bucket + "),0) as bucket" + bucket + "";

	query += " from (select a1.QueueName, a1.stepName,a1.color_name ";
	
	for (int bucket = 1; bucket <=5; bucket++){
		
		if(bucket==4)
		query += ", case when a1.pendingDays >" + (bucket-1)*7 + " then 1 else 0 end as b" + (bucket - 1);
		
		else
		query += ", case when a1.pendingDays between " + (bucket-1)*7  + " and "
				+ bucket*7 + "" + " then 1 else 0 end as b" + (bucket - 1);
	
	
	}
	query += " from ( ";

	// groupBy
/*	groupByDate = " TIMESTAMPDIFF(64,CHAR('" + functionAppBean.getFromDate()
			+ "'-coalesce(DateCreated,'yyyy/mm/dd'))) / 31 ";*/

}

query += "select a.QueueName, a.stepName,ps.color_name, coalesce(CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400),0) as pendingDays "
		+ " from (  ";


query += " select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
		" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
		" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
		" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
		" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
		"  else 'GBS' END as QueueName, stepname,startTime,CmAcmCaseidentifier "
		+ " from "+tableConfig.getProperty("com_wip")+" where endTime is null  and is_active='Y'  ";


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
  query += " and queueName in (" + functionAppBean.getRoles() + ") ";
  }
 

// Username Where clause
if (!functionAppBean.getUsers().isEmpty()) {
	query += " and UserName in (" + functionAppBean.getUsers() + ") ";
}

// Case Type Where clause
if (!functionAppBean.getCaseType().isEmpty()) {
	query += " and CmAcmCaseidentifier LIKE   '"+functionAppBean.getCaseType()+"%' ";
	
}

// Case Status Where clause
if (!functionAppBean.getCaseStatus().isEmpty()) {
	query += " and gbl_CaseStatus in (" + functionAppBean.getCaseStatus() + ") ";

}

// PR Status Where clause
if (!functionAppBean.getPrStatus().isEmpty()) {
	if(functionAppBean.getPrStatus().equals("'Other'"))
	{
		query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
	query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + functionAppBean.getPrStatus() + ") ";
			
}


/*	// Date Where clause
query += " and DateCreated between '" + functionAppBean.getFromDate() + " 00:00:00' and '"
		+ functionAppBean.getToDate() + " 23:59:59' ";
*/

query += " ) a,process_steps ps where   "
		+ "     a.stepname=ps.step_name and ps.function_area='COMM' ";

// super role Where clause
	if (!functionAppBean.getAgingBy().isEmpty()) {
		query += " and a.QueueName in ('" + functionAppBean.getAgingBy() + "') ";

	}

query += "  group by QueueName,a.stepName,ps.color_name ,CmAcmCaseidentifier,CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400)" ;

query += " )  as a1) " +


		" as a2 group by a2.QueueName,a2.stepName,a2.color_name order by a2.QueueName";
// System.out.println("****************************@@@@@-AP report
// 1"+new Date("42615.55"));

// slf4jLogger.info("EXTRACT@@@@@-AP report 1"+query);
System.out.println("EXTRACT@@@@@-COMM report 8" + query);
Connection conn = null;
PreparedStatement pstmt = null;
ArrayList list = null;
try {
	conn = dataSource.getConnection();
	String insertSteps=" insert into process_steps(function_area,step_name,alias,color_name,isActive) select 'COMM',stepname,'step','#12ff56','1' from (select distinct stepname from "+tableConfig.getProperty("com_wip")+" minus select step_name from process_steps where function_AREA='COMM') ";
	
	pstmt = conn.prepareStatement(insertSteps);
      pstmt.executeUpdate();
	
	pstmt = conn.prepareStatement(query);
	ResultSet resultSet = pstmt.executeQuery();
	list = new ArrayList();
	
	while (resultSet.next()) {
		functionTable innerJson = new functionTable();
		innerJson.setName(resultSet.getString("QueueName"));
		// innerJson.setCompany(resultSet.getString("Gbl_CompanyName"));
		innerJson.setStepName(resultSet.getString("stepName"));
		innerJson.setAlias(resultSet.getString("QueueName"));
		innerJson.setColor(resultSet.getString("color_name"));
		innerJson.setCount("0");
		if (functionAppBean.getDisplayBy().contains("2/7/15/15+")) {
	
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
	
			
	
		} else if (functionAppBean.getDisplayBy().contains("15/30/45/45+")) {
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
			
	
		} else if (functionAppBean.getDisplayBy().contains("1/2/3/4/4+")) {
	
			innerJson.setBucket1(resultSet.getString("bucket0"));
			innerJson.setBucket2(resultSet.getString("bucket1"));
			innerJson.setBucket3(resultSet.getString("bucket2"));
			innerJson.setBucket4(resultSet.getString("bucket3"));
			innerJson.setBucket5(resultSet.getString("bucket4"));
	
		}
	
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



public String  getCasesPendingForProcesssingWithPRTOPOCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);
String query = "  select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket   from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from " ;
query += " ( select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
		" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
		" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
		" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
		" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
		"  else 'GBS' END as QueueName,CmAcmCaseidentifier from "+
		""+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null  and is_active='Y'     ";



if (selectedDate.has("stepName")) {

	query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
if (selectedDate.has("Roles"))
 {  
	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
 }



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
if (selectedDate.has("PrStatus")) {
	if(selectedDate.getString("PrStatus").equals("'Other'"))
	{
		query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
		query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
	
}

  //Bucket
  if(selectedDate.has("bucketFrom")){
  if(selectedDate.has("bucketTo")){
  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
  }
  else
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) >"+60;
		  
  }
    
  query+=" ) where queuename is not null " ;
  if (selectedDate.has("currentRole")) {

		query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

	}
  query+="" +
  		") ";
 
/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";
*/	System.out.println("Restquery " + query);
return getTableStringFromQuery(query);
}



public void  getCasesPendingForProcesssingExcelWithPRTOPOCOMM(String columnValueJson) throws SQLException, JSONException {
JSONObject selectedDate = new JSONObject(columnValueJson);
String query = "  select  "+tableConfig.getProperty("com_report6")+",CEILING(CAST(TIMESTAMPDIFF(2,CHAR(current Timestamp -dateCreated)) AS DECIMAL(18,2))/86400) as Bucket   from "+tableConfig.getProperty("com_wip")+" where CmAcmCaseidentifier in (select distinct CmAcmCaseidentifier from " ;
query += " ( select case when queuename in ('COM_LocationBuyer','COM_PlantInternalAudit','COM_PlantVisitManager') THEN 'LocationBuyer' " +
		
		" when queuename='COM_RFQCreator' and stepname='RFQ Creation' and COM_RFQstatus in ('RFQ Created/Create TR','RFQ Created/Create CS' ) then 'Supplier' " +
		" when queuename='COM_PRChecker' and stepname='Verify PR Details' and gbl_caseStatus='On Hold' then 'PlantUser' " +
		" when queuename='COM_QuotationManager' and stepname='Receive TR Approval' then 'PlantUser' " +
		" when queuename in ('COM_POCreator','COM_PRChecker','COM_PurchaseSectionIncharge') THEN 'GBS' " +
		"  else 'GBS' END as QueueName,CmAcmCaseidentifier from "+
		""+tableConfig.getProperty("com_wip")+" where QueueName!='' and endTime is null   and is_active='Y'    ";



if (selectedDate.has("stepName")) {

	query += " and stepName='" + selectedDate.getString("stepName") + "' ";

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
if (selectedDate.has("Roles"))
 {  
	
	query += " and cc.QueueName in (" + selectedDate.getString("Roles") + ") "; 
 }



// Username Where clause
if (selectedDate.has("Users")) {
	query += " and UserName in (" +  selectedDate.getString("Users") + ") ";
}

// Case Type Where clause
if (selectedDate.has("CaseType")) {
	query += " and CmAcmCaseidentifier LIKE '" +  selectedDate.getString("CaseType") + "%' ";

}

// Case Status Where clause
if (selectedDate.has("CaseStatus")) {
	query += " and gbl_CaseStatus in (" +  selectedDate.getString("CaseStatus")+ ") ";

}
// PR Status Where clause
if (selectedDate.has("PrStatus")) {
	if(selectedDate.getString("PrStatus").equals("'Other'"))
	{
		query += " and com_prStatus in ('Purchase Requisition Verified','RFQ Creation','Inventory Checked','Send for ARC Confirmation','ARC Confirmed','Refurbishment','Send for Clarification','Clarification updated','RFQ Creation') ";
							
	}
	else
		query += " and REPLACE(com_prStatus,'n\''t',' not') in (" + selectedDate.getString("PrStatus") + ") ";
	
}

  //Bucket
  if(selectedDate.has("bucketFrom")){
  if(selectedDate.has("bucketTo")){
  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) between "+selectedDate.getString("bucketFrom")+" and "+selectedDate.getString("bucketTo");
  }
  else
	  query += " and  CEILING(CAST(TIMESTAMPDIFF(2,CHAR( current TIMESTAMP -startTime)) AS DECIMAL(18,2))/86400) >"+60;
		  
  }
    
  query+=" ) where queuename is not null " ;
  if (selectedDate.has("currentRole")) {

		query += " and QueueName='" + selectedDate.getString("currentRole") + "' ";

	}
  query+="" +
  		") ";
 
/*	query += " and DateCreated between '" + selectedDate.getString("dateFrom") + " 00:00:00' and '"
		+ selectedDate.getString("dateTo") + " 23:59:59' )";
*/	System.out.println("Restquery " + query);

generateExcel(query,"Commercial_Report10","Cases Pending from Last Role");	
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


















	
	

}
