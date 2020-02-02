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

import jsw.report.delegate.ARDelegate;
import jsw.report.delegate.ApDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
@RequestMapping(value = "/ARController")
public class ARController {
	
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private ARDelegate arDelegate;
	
	

	public ARDelegate getArDelegate() {
		return arDelegate;
	}


	public void setArDelegate(ARDelegate arDelegate) {
		this.arDelegate = arDelegate;
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

	@RequestMapping(value = "/listNumberOfCasesCreatedAR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCreatedAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AR_MANUAL_REPORT1.pdf" );
		
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
		
		

		
	}
	
	@RequestMapping(value = "/listNumberOfCasesCreatedAR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCreatedAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCreatedAR");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", arDelegate.getNumberOfCasesCreatedAR(functionAppBean));
	// request.setAttribute("functionTable",
	// arDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
	
	//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	
	
	
	request.setAttribute("helpManual","AR_MANUAL_REPORT1.pdf" );
	
	
	
	
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	

	
	
	
	@RequestMapping(value = "/listNumberOfCasesCompletedAR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesCompletedAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AR_MANUAL_REPORT2.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/listNumberOfCasesCompletedAR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesCompletedAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedAR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", arDelegate.getCasesCreatedCompletedAR(functionAppBean));
	//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
	
	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AR_MANUAL_REPORT2.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	return model;
	}
	
	
	
	
	@RequestMapping(value = "/listNumberOfCasesProcessedAR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesProcessedAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedAR");
		
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
		
		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AR_MANUAL_REPORT3.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesProcessedAR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesProcessedAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesProcessedAR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", arDelegate.getCasesProcessedAR(functionAppBean));
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	


	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AR_MANUAL_REPORT3.pdf" );
	
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/listRoleWiseTimeTakenAR", method = RequestMethod.GET)
	public ModelAndView listRoleWiseTimeTakenAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenAR");
		
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AR_MANUAL_REPORT4.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	@RequestMapping(value = "/listRoleWiseTimeTakenAR", method = RequestMethod.POST)
	public ModelAndView submitRoleWiseTimeTakenAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
	
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listRoleWiseTimeTakenAR");
		
		System.out.println("functionAppBean"+functionAppBean);
	request.setAttribute("listRoleWiseTimeTaken", arDelegate.getRoleWiseTimeTakenAR(functionAppBean));
	//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
    int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
	
	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AR_MANUAL_REPORT4.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeAR", method = RequestMethod.GET)
	public ModelAndView listTotalCycleTimeAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));

		request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
		request.setAttribute("helpManual","AR_MANUAL_REPORT5.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listTotalCycleTimeAR", method = RequestMethod.POST)
	public ModelAndView submitTotalCycleTimeAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listTotalTimeTakenAR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listTotalTimeTaken", arDelegate.getTotalCycleTimeAR(functionAppBean));
	//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,0,"AR"));
	
	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AR_MANUAL_REPORT5.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}
	
	
	@RequestMapping(value = "/listNumberOfCasesPendingAR", method = RequestMethod.GET)
	public ModelAndView listNumberOfCasesPendingAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,6,"AR"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","AR_MANUAL_REPORT6.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/listNumberOfCasesPendingAR", method = RequestMethod.POST)
	public ModelAndView submitNumberOfCasesPendingAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingAR");
		
		System.out.println("functionAppBean"+functionAppBean);
		
	request.setAttribute("listCasesCreated", arDelegate.getCasesPendingAR(functionAppBean));
	//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
	
	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,6,"AR"));
	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

	/*//String toDate[] = functionAppBean.getToDate().split("-");

	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean",functionAppBean);
	request.setAttribute("helpManual","AR_MANUAL_REPORT6.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		return model;
	}

	

	@RequestMapping(value = "/listCasesCompletedCumulativelyAR", method = RequestMethod.GET)
	public ModelAndView listCasesCompletedCumulativelyAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedCumulativelyAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,6,"AR"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","AR_MANUAL_REPORT7.pdf" );
		
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
		
		

		
	}
	
	@RequestMapping(value = "/listCasesCompletedCumulativelyAR", method = RequestMethod.POST)
	public ModelAndView submitCasesCompletedCumulativelyAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesCompletedCumulativelyAR");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", arDelegate.getCasesCompletedCumulativelyAR(functionAppBean));
	// request.setAttribute("functionTable",
	// arDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,6,"AR"));
	
	/*//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	request.setAttribute("helpManual","AR_MANUAL_REPORT7.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/listCasesPendingForProcessingAR", method = RequestMethod.GET)
	public ModelAndView listCasesPendingForProcessingAR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingForProcessingAR");
		
		//request.setAttribute("functionTable", arDelegate.getNumberOfCases());
		int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id,6,"AR"));
		
		FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
	
		request.setAttribute("helpManual","AR_MANUAL_REPORT8.pdf" );
		}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
}
	
	@RequestMapping(value = "/listCasesPendingForProcessingAR", method = RequestMethod.POST)
	public ModelAndView submitCasesPendingForProcessingAR(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
			@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listNumberOfCasesPendingForProcessingAR");
		
		System.out.println("functionAppBean"+functionAppBean);

	System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

	request.setAttribute("listCasesCreated", arDelegate.getCasesPendingForProcesssingAR(functionAppBean));
	// request.setAttribute("functionTable",
	// arDelegate.getNumberOfCases());
	int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

	request.getSession().setAttribute("functionTable", arDelegate.getNumberOfCasesARById(id, 6,"AR"));
	
	/*//String toDate[] = functionAppBean.getToDate().split("-");
	//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
	request.setAttribute("FunctionAppBean", functionAppBean);

	request.setAttribute("listPeriod",
			arDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
	
	
	
	
	
	request.setAttribute("helpManual","AR_MANUAL_REPORT8.pdf" );
	
	
		}
		else{
				
				model=new ModelAndView("index");
			}
	
	return model;
	}
	

}
