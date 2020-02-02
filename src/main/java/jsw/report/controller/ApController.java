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

import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;



@Controller
@RequestMapping(value = "/ApController")
public class ApController {
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private ApDelegate apDelegate;
	
	

	

	
	public ApDelegate getApDelegate() {
		return apDelegate;
	}


	public void setApDelegate(ApDelegate apDelegate) {
		this.apDelegate = apDelegate;
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


	@RequestMapping(value = "/listNumberOfCasesCreatedAP", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCreatedAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
	
		request.setAttribute("helpManual","AP_MANUAL_REPORT1.pdf" );
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
		
		

		
	}
	
	@RequestMapping(value = "/listNumberOfCasesCreatedAP", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCreatedAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedAP");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", apDelegate.getNumberOfCasesCreatedAP(functionAppBean));
	// request.setAttribute("functionTable",
	// apDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	
	
	request.setAttribute("helpManual","AP_MANUAL_REPORT1.pdf" );
	
	
	
	
	
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	

	@RequestMapping(value = "/listNumberOfCasesCompletedAP", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCompletedAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT2.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listNumberOfCasesCompletedAP", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCompletedAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getCasesCreatedCompletedAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT2.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
	
	
	@RequestMapping(value = "/listNumberOfCasesProcessedAP", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesProcessedAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedAP");
		
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT3.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesProcessedAP", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesProcessedAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getCasesProcessedAP(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	


	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT3.pdf" );
	
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/listRoleWiseTimeTakenAP", method = RequestMethod.GET)
	public ModelAndView listRoleWiseTimeTakenAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenAP");
		
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT4.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	@RequestMapping(value = "/listRoleWiseTimeTakenAP", method = RequestMethod.POST)
	public ModelAndView submitRoleWiseTimeTakenAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenAP");
		
		System.out.println("functionAppBean"+functionAppBean);
	request.setAttribute("listRoleWiseTimeTaken", apDelegate.getRoleWiseTimeTakenAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
    int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT4.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeAP", method = RequestMethod.GET)
	public ModelAndView listTotalCycleTimeAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT5.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeAP", method = RequestMethod.POST)
	public ModelAndView submitTotalCycleTimeAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listTotalTimeTaken", apDelegate.getTotalCycleTimeAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT5.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	@RequestMapping(value = "/listNumberOfCasesPendingAP", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesPendingAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,5,"AP"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","AP_MANUAL_REPORT6.pdf" );
	
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesPendingAP", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesPendingAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getCasesPendingAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,5,"AP"));
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	/*//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT6.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}

	

	@RequestMapping(value = "/listCasesCompletedCumulativelyAP", method = RequestMethod.GET)
	public ModelAndView listCasesCompletedCumulativelyAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedCumulativelyAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,3,"AP"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
	
		request.setAttribute("helpManual","AP_MANUAL_REPORT7.pdf" );
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
		
		

		
	}
	
	@RequestMapping(value = "/listCasesCompletedCumulativelyAP", method = RequestMethod.POST)
	public ModelAndView submitCasesCompletedCumulativelyAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedCumulativelyAP");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", apDelegate.getCasesCompletedCumulativelyAP(functionAppBean));
	// request.setAttribute("functionTable",
	// apDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,3,"AP"));
	
	/*//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	request.setAttribute("helpManual","AP_MANUAL_REPORT7.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/listCasesPendingForProcessingAP", method = RequestMethod.GET)
	public ModelAndView listCasesPendingForProcessingAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingForProcessingAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,4,"AP"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
	
		request.setAttribute("helpManual","AP_MANUAL_REPORT8.pdf" );
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
}
	
	@RequestMapping(value = "/listCasesPendingForProcessingAP", method = RequestMethod.POST)
	public ModelAndView submitCasesPendingForProcessingAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingForProcessingAP");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", apDelegate.getCasesPendingForProcesssingAP(functionAppBean));
	// request.setAttribute("functionTable",
	// apDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id, 4,"AP"));
	
	/*//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	request.setAttribute("helpManual","AP_MANUAL_REPORT8.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	
	
	
	
	

	@RequestMapping(value = "/listExceptionRateAndReferredRateAP", method = RequestMethod.GET)
	public ModelAndView listExceptionRateAndrefferedRateAPAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listExceptionRateAndreferredRateAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT9.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listExceptionRateAndReferredRateAP", method = RequestMethod.POST)
	public ModelAndView submitExceptionRateAndrefferedRateAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listExceptionRateAndreferredRateAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getExceptionAndFirstPassAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
/*	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
*/	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT9.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
	
	
	

	@RequestMapping(value = "/listUrgentInvoiceReceivedAP", method = RequestMethod.GET)
	public ModelAndView listUrgentInvoiceReceivedAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listUrgentInvoiceReceivedAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT10.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listUrgentInvoiceReceivedAP", method = RequestMethod.POST)
	public ModelAndView submitUrgentInvoiceReceivedAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listUrgentInvoiceReceivedAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getUrgentInvoiceRecievedAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT10.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/listAverageTimeTakenFromReceiptToPaymentAP", method = RequestMethod.GET)
	public ModelAndView listAverageTimeTakenFromReceiptToPaymentAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listAverageTimeTakenFromReceiptToPaymentAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT11.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listAverageTimeTakenFromReceiptToPaymentAP", method = RequestMethod.POST)
	public ModelAndView submitAverageTimeTakenFromReceiptToPaymentAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listAverageTimeTakenFromReceiptToPaymentAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getAverageTimeTakenFromReceiptToPaymentAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT11.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	

	
	
	@RequestMapping(value = "/listTATforScanningAndindexingAP", method = RequestMethod.GET)
	public ModelAndView listTATforScanningAndindexingAP(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTATforScanningAndindexingAP");
		
		//request.setAttribute("functionTable", apDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AP_MANUAL_REPORT12.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listTATforScanningAndindexingAP", method = RequestMethod.POST)
	public ModelAndView submitTATforScanningAndindexingAP(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTATforScanningAndindexingAP");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", apDelegate.getTATforScanningAndindexingAP(functionAppBean));
	//request.setAttribute("functionTable", apDelegate.getavinNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", apDelegate.getNumberOfCasesAPById(id,0,"AP"));
	
	request.setAttribute("listPeriod",
			apDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AP_MANUAL_REPORT12.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
}
