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
import jsw.report.delegate.TaxDelegate;
import jsw.report.viewBean.FunctionAppBean;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
@RequestMapping(value = "/TAXController")
public class TaxController {
	
	
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private TaxDelegate taxDelegate;
	
	
	public TaxDelegate getTaxDelegate() {
		return taxDelegate;
	}


	public void setTaxDelegate(TaxDelegate taxDelegate) {
		this.taxDelegate = taxDelegate;
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

	
//Tax Report Controller

		@RequestMapping(value = "/listNumberOfCasesCreatedTax", method = RequestMethod.GET)
		public ModelAndView listNumberOfCasesCreatedTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesCreatedTax");
			
			//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
			
			request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
			request.setAttribute("helpManual","TAX_MANUAL_REPORT1.pdf" );
			
			}
		else{
				
				model=new ModelAndView("index");
			}
			return model;
			
			

			
		}
		
		@RequestMapping(value = "/listNumberOfCasesCreatedTax", method = RequestMethod.POST)
		public ModelAndView submitNumberOfCasesCreatedTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesCreatedTax");
			
			System.out.println("functionAppBean"+functionAppBean);

		System.out.println("ArrayList" + (ArrayList) request.getSession().getAttribute("functionTable"));

		request.setAttribute("listCasesCreated", taxDelegate.getNumberOfCasesCreatedTax(functionAppBean));
		// request.setAttribute("functionTable",
		// taxDelegate.getNumberOfCases());
		int id = Integer.parseInt(request.getSession().getAttribute("UserId").toString());

		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
		
		//String toDate[] = functionAppBean.getToDate().split("-");
		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean", functionAppBean);

		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
		
		
		
		
		request.setAttribute("helpManual","TAX_MANUAL_REPORT1.pdf" );
		
		
		
			}
			else{
					
					model=new ModelAndView("index");
				}
		
		return model;
		}
		

		
		
		
		@RequestMapping(value = "/listNumberOfCasesCompletedTax", method = RequestMethod.GET)
		public ModelAndView listNumberOfCasesCompletedTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesCompletedTax");
			
			//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
			
			request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
			request.setAttribute("helpManual","TAX_MANUAL_REPORT2.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
			return model;
		}
		
		
		
		@RequestMapping(value = "/listNumberOfCasesCompletedTax", method = RequestMethod.POST)
		public ModelAndView submitNumberOfCasesCompletedTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesCompletedTax");
			
			System.out.println("functionAppBean"+functionAppBean);
			
		request.setAttribute("listCasesCreated", taxDelegate.getCasesCreatedCompletedTax(functionAppBean));
		//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
		
		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","TAX_MANUAL_REPORT2.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
		return model;
		}
		
		
		
		
		@RequestMapping(value = "/listNumberOfCasesProcessedTax", method = RequestMethod.GET)
		public ModelAndView listNumberOfCasesProcessedTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesProcessedTax");
			
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
			
			request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
			request.setAttribute("helpManual","TAX_MANUAL_REPORT3.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
			return model;
		}
		
		@RequestMapping(value = "/listNumberOfCasesProcessedTax", method = RequestMethod.POST)
		public ModelAndView submitNumberOfCasesProcessedTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesProcessedTax");
			
			System.out.println("functionAppBean"+functionAppBean);
			
		request.setAttribute("listCasesCreated", taxDelegate.getCasesProcessedTax(functionAppBean));
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		


		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","TAX_MANUAL_REPORT3.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
			return model;
		}
		
		
		
		
		
		
		
		
		
		
		@RequestMapping(value = "/listRoleWiseTimeTakenTax", method = RequestMethod.GET)
		public ModelAndView listRoleWiseTimeTakenTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listRoleWiseTimeTakenTax");
			
			
			//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));

			request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
			request.setAttribute("helpManual","TAX_MANUAL_REPORT4.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
			return model;
		}
		
		@RequestMapping(value = "/listRoleWiseTimeTakenTax", method = RequestMethod.POST)
		public ModelAndView submitRoleWiseTimeTakenTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listRoleWiseTimeTakenTax");
			
			System.out.println("functionAppBean"+functionAppBean);
		request.setAttribute("listRoleWiseTimeTaken", taxDelegate.getRoleWiseTimeTakenTax(functionAppBean));
		//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
	    int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
		
		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","TAX_MANUAL_REPORT4.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
		
		return model;
		}
		
		@RequestMapping(value = "/listTotalCycleTimeTax", method = RequestMethod.GET)
		public ModelAndView listTotalCycleTimeTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listTotalTimeTakenTax");
			
			//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));

			request.setAttribute("FunctionAppBean",getfunctionAppBeanWithDate() );
			request.setAttribute("helpManual","TAX_MANUAL_REPORT5.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
			return model;
		}
		
		@RequestMapping(value = "/listTotalCycleTimeTax", method = RequestMethod.POST)
		public ModelAndView submitTotalCycleTimeTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listTotalTimeTakenTax");
			
			System.out.println("functionAppBean"+functionAppBean);
			
		request.setAttribute("listTotalTimeTaken", taxDelegate.getTotalCycleTimeTax(functionAppBean));
		//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,0,"TAX"));
		
		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));
		//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","TAX_MANUAL_REPORT5.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
			return model;
		}
		
		
		@RequestMapping(value = "/listNumberOfCasesPendingTax", method = RequestMethod.GET)
		public ModelAndView listNumberOfCasesPendingTax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
			
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesPendingTax");
			
			//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
			int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
			
			request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,5,"TAX"));
			
			FunctionAppBean functionAppBean=getfunctionAppBeanWithDate();
			//String toDate[] = functionAppBean.getToDate().split("-");

			//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);
			request.setAttribute("FunctionAppBean",functionAppBean);
			request.setAttribute("helpManual","TAX_MANUAL_REPORT6.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
			return model;
		}
		
		@RequestMapping(value = "/listNumberOfCasesPendingTax", method = RequestMethod.POST)
		public ModelAndView submitNumberOfCasesPendingTax(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
			ModelAndView model = null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
				model = new ModelAndView("listNumberOfCasesPendingTax");
			
			System.out.println("functionAppBean"+functionAppBean);
			
		request.setAttribute("listCasesCreated", taxDelegate.getCasesPendingTax(functionAppBean));
		//request.setAttribute("functionTable", taxDelegate.getNumberOfCases());
	int id=Integer.parseInt(request.getSession().getAttribute("UserId").toString());
		
		request.getSession().setAttribute("functionTable", taxDelegate.getNumberOfCasesTaxById(id,5,"TAX"));
		request.setAttribute("listPeriod",
				taxDelegate.getAllPeriodDisplay(functionAppBean.getFromDate(), functionAppBean.getDisplayBy()));

		/*//String toDate[] = functionAppBean.getToDate().split("-");

		//functionAppBean.setToDate(toDate[2] + "-" + toDate[1] + "-" + toDate[0]);*/
		request.setAttribute("FunctionAppBean",functionAppBean);
		request.setAttribute("helpManual","TAX_MANUAL_REPORT6.pdf" );
			}
			else{
					
					model=new ModelAndView("index");
				}
			return model;
		}

		
		
		
		
		

}
