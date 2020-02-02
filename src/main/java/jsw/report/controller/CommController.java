package jsw.report.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jsw.report.delegate.CommDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
@RequestMapping(value = "/CommController")
public class CommController {
	private static String UPLOADED_FOLDER = "D://";
	@Autowired
	private CommDelegate commDelegate;

	public CommDelegate getCommDelegate() {
		return commDelegate;
	}

	public void setCommDelegate(CommDelegate commDelegate) {
		this.commDelegate = commDelegate;
	}

	
private FunctionAppBean getfunctionAppBeanWithDate(){
		
		FunctionAppBean functionAppBean=new FunctionAppBean();
		Date TodayDate=new Date();
		String month="";
		if((TodayDate.getMonth()+1)<10){
		month+="0"+(TodayDate.getMonth()+1);
		}
		else
			month+=(TodayDate.getMonth()+1);
			
		String dateToday="";

		if((TodayDate.getDate())<10){
			dateToday+="0"+(TodayDate.getDate());
			
		}
		else{
			dateToday+=(TodayDate.getDate());
		
		}
		String dateTemp=(1900+TodayDate.getYear())+"-"+month+"-"+ dateToday;
		functionAppBean.setToDate(dateTemp);
		
		TodayDate.setDate(TodayDate.getDate()-6);
		month="";dateToday="";
		
		
		if((TodayDate.getMonth()+1)<10){
			month+="0"+(TodayDate.getMonth()+1);
			}
			else
				month+=(TodayDate.getMonth()+1);
			
		if((TodayDate.getDate())<10){
			dateToday+="0"+(TodayDate.getDate());
			
		}
		else{
			dateToday+=(TodayDate.getDate());
		
		}
		
		functionAppBean.setFromDate((TodayDate.getYear()+1900)+"-"+month+"-"+dateToday);	
		
		return functionAppBean;
		
	}
	
	



// COMM Report Controller


@RequestMapping(value = "/listNumberOfCasesCreatedCOMM", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCreatedCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );

	request.setAttribute("helpManual","COM_MANUAL_REPORT1.pdf" );
	}
else{
		
		model=new ModelAndView("index");
	}
	return model;
	
	

	
}

@RequestMapping(value = "/listNumberOfCasesCreatedCOMM", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCreatedCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);

System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

request.setAttribute("listCasesCreated", commDelegate.getNumberOfCasesCreatedCOMM(functionAppBean));
// request.setAttribute("functionTable",
// commDelegate.getNumberOfCases());
int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

request.setAttribute("FunctionAppBean", functionAppBean);

request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));




request.setAttribute("helpManual","COM_MANUAL_REPORT1.pdf" );



	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}





@RequestMapping(value = "/listNumberOfCasesCompletedCOMM", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCompletedCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","COM_MANUAL_REPORT2.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}



@RequestMapping(value = "/listNumberOfCasesCompletedCOMM", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCompletedCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", commDelegate.getCasesCreatedCompletedCOMM(functionAppBean));
//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
	
request.setAttribute("helpManual","COM_MANUAL_REPORT2.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
return model;
}




@RequestMapping(value = "/listNumberOfCasesProcessedCOMM", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesProcessedCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedCOMM");
	
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","COM_MANUAL_REPORT3.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listNumberOfCasesProcessedCOMM", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesProcessedCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", commDelegate.getCasesProcessedCOMM(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());



request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));
request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","COM_MANUAL_REPORT3.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}










@RequestMapping(value = "/listRoleWiseTimeTakenCOMM", method = RequestMethod.GET)
public ModelAndView listRoleWiseTimeTakenCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenCOMM");
	
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","COM_MANUAL_REPORT4.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}

@RequestMapping(value = "/listRoleWiseTimeTakenCOMM", method = RequestMethod.POST)
public ModelAndView submitRoleWiseTimeTakenCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {

	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
request.setAttribute("listRoleWiseTimeTaken", commDelegate.getRoleWiseTimeTakenCOMM(functionAppBean));
//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","COM_MANUAL_REPORT4.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}

@RequestMapping(value = "/listTotalCycleTimeCOMM", method = RequestMethod.GET)
public ModelAndView listTotalCycleTimeCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","COM_MANUAL_REPORT5.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listTotalCycleTimeCOMM", method = RequestMethod.POST)
public ModelAndView submitTotalCycleTimeCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", commDelegate.getTotalCycleTimeCOMM(functionAppBean));
//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,0));

request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","COM_MANUAL_REPORT5.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}


@RequestMapping(value = "/listNumberOfCasesPendingCOMM", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesPendingCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,2));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","COM_MANUAL_REPORT6.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listNumberOfCasesPendingCOMM", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesPendingCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", commDelegate.getCasesPendingCOMM(functionAppBean));
//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,2));
/*request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
*/
/*//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","COM_MANUAL_REPORT6.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}



@RequestMapping(value = "/listCasesCompletedCumulativelyCOMM", method = RequestMethod.GET)
public ModelAndView listCasesCompletedCumulativelyCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCumulatively");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,3));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);

	request.setAttribute("helpManual","COM_MANUAL_REPORT7.pdf" );
	}
else{
		
		model=new ModelAndView("index");
	}
	return model;
	
	

	
}

@RequestMapping(value = "/listCasesCompletedCumulativelyCOMM", method = RequestMethod.POST)
public ModelAndView submitCasesCompletedCumulativelyCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCumulatively");
	
	System.out.println("functionAppBean"+functionAppBean);

System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

request.setAttribute("listCasesCreated", commDelegate.getCasesCompletedCumulativelyCOMM(functionAppBean));
// request.setAttribute("functionTable",
// commDelegate.getNumberOfCases());
int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,3));

/*//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean", functionAppBean);

request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
request.setAttribute("helpManual","COM_MANUAL_REPORT7.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}






@RequestMapping(value = "/listCasesPendingForProcessingCOMM", method = RequestMethod.GET)
public ModelAndView listCasesPendingForProcessingCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingForProcessingCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,4));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","COM_MANUAL_REPORT8.pdf" );
	
	}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listCasesPendingForProcessingCOMM", method = RequestMethod.POST)
public ModelAndView submitCasesPendingForProcessingCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingForProcessingCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);

System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

request.setAttribute("listCasesCreated", commDelegate.getCasesPendingForProcesssingCOMM(functionAppBean));
// request.setAttribute("functionTable",
// commDelegate.getNumberOfCases());
int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id, 4));

/*//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean", functionAppBean);

/*request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
*/




request.setAttribute("helpManual","COM_MANUAL_REPORT8.pdf" );


	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}









@RequestMapping(value = "/listNumberOfCasesPendingWithPRTOPOCOMM", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesPendingWithPRTOPOCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingWithPRTOPOCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,2));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);

	
	ArrayList<String> roleList=new ArrayList<String>();
	roleList.add("GBS");roleList.add("LocationBuyer");
	roleList.add("PlantUser");roleList.add("Supplier");/*agingList.add("Current Role");*/
	request.getSession().setAttribute("roleList", roleList);
	request.setAttribute("helpManual","COM_MANUAL_REPORT9.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listNumberOfCasesPendingWithPRTOPOCOMM", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesPendingWithPRTOPOCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingWithPRTOPOCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", commDelegate.getCasesPendingWithPRTOPOCOMM(functionAppBean));
//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,2));
/*request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
*/
/*//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","COM_MANUAL_REPORT9.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}

	
	
	

@RequestMapping(value = "/listCasesPendingForProcessingWithPRTOPOCOMM", method = RequestMethod.GET)
public ModelAndView listCasesPendingForProcessingWithPRTOPOCOMM(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingForProcessingWithPRTOPOCOMM");
	
	//request.setAttribute("functionTable", commDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id,4));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);

	ArrayList<String> roleList=new ArrayList<String>();
	roleList.add("GBS");roleList.add("LocationBuyer");
	roleList.add("PlantUser");roleList.add("Supplier");/*agingList.add("Current Role");*/
	request.getSession().setAttribute("roleList", roleList);
	request.setAttribute("helpManual","COM_MANUAL_REPORT10.pdf" );
	}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listCasesPendingForProcessingWithPRTOPOCOMM", method = RequestMethod.POST)
public ModelAndView submitCasesPendingForProcessingWithPRTOPOCOMM(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingForProcessingWithPRTOPOCOMM");
	
	System.out.println("functionAppBean"+functionAppBean);

System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

request.setAttribute("listCasesCreated", commDelegate.getCasesPendingForProcesssingWithPRTOPOCOMM(functionAppBean));
// request.setAttribute("functionTable",
// commDelegate.getNumberOfCases());
int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", commDelegate.getNumberOfCasesCOMMById(id, 4));

/*//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean", functionAppBean);

/*request.setAttribute("listPeriod",
		commDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
*/




request.setAttribute("helpManual","COM_MANUAL_REPORT10.pdf" );


	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}	
}
