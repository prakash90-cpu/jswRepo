package jsw.report.controller;

import java.sql.SQLException;
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

import jsw.report.delegate.ApDelegate;
import jsw.report.delegate.CORPDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;

@Controller
@RequestMapping(value = "/CORPController")
public class CORPController {
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private CORPDelegate corpDelegate;

	public CORPDelegate getCorpDelegate() {
		return corpDelegate;
	}

	public void setCorpDelegate(CORPDelegate corpDelegate) {
		this.corpDelegate = corpDelegate;
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

@RequestMapping(value = "/listNumberOfCasesCreatedCORP", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCreatedCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedCORP");
	
	//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));

	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitNumberOfCasesCreatedCORP", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCreatedCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCreatedCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", corpDelegate.getNumberOfCasesCreatedCORP(functionAppBean));
//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);

	}
	else{
			
			model=new ModelAndView("index");
		}
return model;
}





@RequestMapping(value = "/listNumberOfCasesCompletedCORP", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesCompletedCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCORP");
	
	//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));

	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitNumberOfCasesCompletedCORP", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesCompletedCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesCompletedCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", corpDelegate.getCasesCreatedCompletedCORP(functionAppBean));
//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);
	}
	else{
			
			model=new ModelAndView("index");
		}
return model;
}




@RequestMapping(value = "/listNumberOfCasesProcessedCORP", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesProcessedCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedCORP");
	
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));
	
	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitNumberOfCasesProcessedCORP", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesProcessedCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesProcessedCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", corpDelegate.getCasesProcessedCORP(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());



request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}










@RequestMapping(value = "/listRoleWiseTimeTakenCORP", method = RequestMethod.GET)
public ModelAndView listRoleWiseTimeTakenCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenCORP");
	
	//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));

	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitRoleWiseTimeTakenCORP", method = RequestMethod.POST)
public ModelAndView submitRoleWiseTimeTakenCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listRoleWiseTimeTakenCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
request.setAttribute("listCasesCreated", corpDelegate.getRoleWiseTimeTakenCORP(functionAppBean));
//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);


	}
	else{
			
			model=new ModelAndView("index");
		}
return model;
}

@RequestMapping(value = "/listTotalCycleTimeCORP", method = RequestMethod.GET)
public ModelAndView listTotalCycleTimeCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenCORP");
	
	//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));

	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitTotalCycleTimeCORP", method = RequestMethod.POST)
public ModelAndView submitTotalCycleTimeCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listTotalTimeTakenCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", corpDelegate.getTotalCycleTimeCORP(functionAppBean));
//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}


@RequestMapping(value = "/listNumberOfCasesPendingCORP", method = RequestMethod.GET)
public ModelAndView listNumberOfCasesPendingCORP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
	
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingCORP");
	
	//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));
	
	FunctionAppBean functionAppBean=new FunctionAppBean();
	request.setAttribute("FunctionAppBean",functionAppBean );
}
else{
		
		model=new ModelAndView("index");
	}
	return model;
}

@RequestMapping(value = "/submitNumberOfCasesPendingCORP", method = RequestMethod.POST)
public ModelAndView submitNumberOfCasesPendingCORP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
		@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	ModelAndView model = null;
	if(request.getSession().getAttribute("UserId")!= null)
	{
		model = new ModelAndView("listNumberOfCasesPendingCORP");
	
	System.out.println("functionAppBean"+functionAppBean);
	
request.setAttribute("listCasesCreated", corpDelegate.getCasesPendingCORP(functionAppBean));
//request.setAttribute("functionTable", corpDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());

request.getSession().setAttribute("functionTable", corpDelegate.getNumberOfCasesById(id,0,"AP"));


request.setAttribute("FunctionAppBean",functionAppBean);
	}
	else{
			
			model=new ModelAndView("index");
		}
	return model;
}


}
