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

import jsw.report.delegate.ApDelegate;
import jsw.report.delegate.HRDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
@RequestMapping(value = "/HRController")
public class HRController {
	
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private HRDelegate hrDelegate;
	
	
	

	public HRDelegate getHrDelegate() {
		return hrDelegate;
	}

	public void setHrDelegate(HRDelegate hrDelegate) {
		this.hrDelegate = hrDelegate;
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
	
	@RequestMapping(value = "/listNumberOfCasesCreatedHR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCreatedHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedHR");
		
		//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","HRM_MANUAL_REPORT1.pdf" );
		
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
			
	}
	
	@RequestMapping(value = "/listNumberOfCasesCreatedHR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCreatedHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedHR");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", hrDelegate.getNumberOfCasesCreatedHR(functionAppBean));
	// request.setAttribute("functionTable",
	// hrDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
	
	//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	
	
	
	
	request.setAttribute("helpManual","HRM_MANUAL_REPORT1.pdf" );
	
	
	
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	

	
	
	
	@RequestMapping(value = "/listNumberOfCasesCompletedHR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCompletedHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedHR");
		
		//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","HRM_MANUAL_REPORT2.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listNumberOfCasesCompletedHR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCompletedHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedHR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", hrDelegate.getCasesCreatedCompletedHR(functionAppBean));
	//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
	
	request.setAttribute("listPeriod",
			hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","HRM_MANUAL_REPORT2.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
	
	
	@RequestMapping(value = "/listNumberOfCasesProcessedHR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesProcessedHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedHR");
		
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","HRM_MANUAL_REPORT3.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesProcessedHR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesProcessedHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedHR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", hrDelegate.getCasesProcessedHR(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	


	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
	request.setAttribute("listPeriod",
			hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","HRM_MANUAL_REPORT3.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/listRoleWiseTimeTakenHR", method = RequestMethod.GET)
	public ModelAndView listRoleWiseTimeTakenHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenHR");
		
		
		//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","HRM_MANUAL_REPORT4.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	@RequestMapping(value = "/listRoleWiseTimeTakenHR", method = RequestMethod.POST)
	public ModelAndView submitRoleWiseTimeTakenHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenHR");
		
		System.out.println("functionAppBean"+functionAppBean);
	request.setAttribute("listRoleWiseTimeTaken", hrDelegate.getRoleWiseTimeTakenHR(functionAppBean));
	//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
    int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
	
	request.setAttribute("listPeriod",
			hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","HRM_MANUAL_REPORT4.pdf" );

		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeHR", method = RequestMethod.GET)
	public ModelAndView listTotalCycleTimeHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenHR");
		
		//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","HRM_MANUAL_REPORT5.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeHR", method = RequestMethod.POST)
	public ModelAndView submitTotalCycleTimeHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenHR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listTotalTimeTaken", hrDelegate.getTotalCycleTimeHR(functionAppBean));
	//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,0,"HRM"));
	
	request.setAttribute("listPeriod",
			hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","HRM_MANUAL_REPORT5.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	@RequestMapping(value = "/listNumberOfCasesPendingHR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesPendingHR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingHR");
		
		//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,5,"HRM"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","HRM_MANUAL_REPORT6.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesPendingHR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesPendingHR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingHR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", hrDelegate.getCasesPendingHR(functionAppBean));
	//request.setAttribute("functionTable", hrDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", hrDelegate.getNumberOfCasesHRById(id,5,"HRM"));
	//request.setAttribute("listPeriod",
	//		hrDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	/*//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","HRM_MANUAL_REPORT6.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}

	
	
	
	

}
