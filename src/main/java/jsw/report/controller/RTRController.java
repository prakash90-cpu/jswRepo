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
import jsw.report.delegate.RTRDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
@RequestMapping(value = "/RTRController")
public class RTRController {
	
	
	private static String UPLOADED_FOLDER = "D://";
	@Autowired
	private RTRDelegate rtrDelegate;
	
	

	
	
	public RTRDelegate getRtrDelegate() {
		return rtrDelegate;
	}

	public void setRtrDelegate(RTRDelegate rtrDelegate) {
		this.rtrDelegate = rtrDelegate;
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
// RTR Report Controller

@RequestMapping(value = "/listNumberOfCasesCreatedRTR", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCreatedRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedRTR");
	
	//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );

	request.setAttribute("helpManual","RTR_MANUAL_REPORT1.pdf" );
	}
else{
		
		model=new ModelAndView("index");
	}
	return model;
	
	

	
}

@RequestMapping(value = "/listNumberOfCasesCreatedRTR", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCreatedRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedRTR");
	
	System.out.println("functionAppBean"+functionAppBean);

System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

request.setAttribute("listCasesCreated", rtrDelegate.getNumberOfCasesCreatedRTR(functionAppBean));
// request.setAttribute("functionTable",
// rtrDelegate.getNumberOfCases());
int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

//String toDate[] = functionAppBean.getToDate().split("-");
//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean", functionAppBean);

request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));




request.setAttribute("helpManual","RTR_MANUAL_REPORT1.pdf" );



	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}





@RequestMapping(value = "/listNumberOfCasesCompletedRTR", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCompletedRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedRTR");
	
	//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","RTR_MANUAL_REPORT2.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}



@RequestMapping(value = "/listNumberOfCasesCompletedRTR", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCompletedRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedRTR");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", rtrDelegate.getCasesCreatedCompletedRTR(functionAppBean));
//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","RTR_MANUAL_REPORT2.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
return model;
}




@RequestMapping(value = "/listNumberOfCasesProcessedRTR", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesProcessedRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedRTR");
	
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));
	
	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","RTR_MANUAL_REPORT3.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listNumberOfCasesProcessedRTR", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesProcessedRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedRTR");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", rtrDelegate.getCasesProcessedRTR(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());



request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));
request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","RTR_MANUAL_REPORT3.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}










@RequestMapping(value = "/listRoleWiseTimeTakenRTR", method = RequestMethod.GET)
public ModelAndView listRoleWiseTimeTakenRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenRTR");
	
	
	//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","RTR_MANUAL_REPORT4.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}

@RequestMapping(value = "/listRoleWiseTimeTakenRTR", method = RequestMethod.POST)
public ModelAndView submitRoleWiseTimeTakenRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {

	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenRTR");
	
	System.out.println("functionAppBean"+functionAppBean);
request.setAttribute("listRoleWiseTimeTaken", rtrDelegate.getRoleWiseTimeTakenRTR(functionAppBean));
//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","RTR_MANUAL_REPORT4.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}

return model;
}

@RequestMapping(value = "/listTotalCycleTimeRTR", method = RequestMethod.GET)
public ModelAndView listTotalCycleTimeRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenRTR");
	
	//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

	request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	request.setAttribute("helpManual","RTR_MANUAL_REPORT5.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listTotalCycleTimeRTR", method = RequestMethod.POST)
public ModelAndView submitTotalCycleTimeRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenRTR");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listTotalTimeTaken", rtrDelegate.getTotalCycleTimeRTR(functionAppBean));
//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,0,"RTR"));

request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","RTR_MANUAL_REPORT5.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}


@RequestMapping(value = "/listNumberOfCasesPendingRTR", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesPendingRTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingRTR");
	
	//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,5,"RTR"));
	
	FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","RTR_MANUAL_REPORT6.pdf" );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/listNumberOfCasesPendingRTR", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesPendingRTR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingRTR");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", rtrDelegate.getCasesPendingRTR(functionAppBean));
//request.setAttribute("functionTable", rtrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", rtrDelegate.getNumberOfCasesRTRById(id,5,"RTR"));
request.setAttribute("listPeriod",
		rtrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

/*//String toDate[] = functionAppBean.getToDate().split("-");

//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
request.setAttribute("FunctionAppBean",functionAppBean);
request.setAttribute("helpManual","RTR_MANUAL_REPORT6.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}


}
