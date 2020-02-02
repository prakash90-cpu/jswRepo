package jsw.report.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import com.jsw.dms.extraction.dao.DMSUpdateWIPCompleteStatusDaoImpl;

import jsw.report.delegate.LoginDelegate;
import jsw.report.service.LDAPService;
import jsw.report.viewBean.LoginBean;
import jsw.report.viewBean.RegistrationBean;


@Controller
public class LoginController {
	@Autowired
	private LoginDelegate loginDelegate;
	@Autowired
	private LDAPService ldapService;

	public LoginDelegate getLoginDelegate() {
		return loginDelegate;
	}

	public void setLoginDelegate(LoginDelegate loginDelegate) {
		this.loginDelegate = loginDelegate;
	}

	public static Logger logger = Logger.getLogger(LoginController.class);
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean,
			RegistrationBean regisBean) {
		request.getSession().invalidate();
		
		
		
		request.getSession().setAttribute("sid", null);
		request.getSession().setAttribute("menuNames", null);
		request.getSession().setAttribute("loggedUser", null);
		request.getSession().setAttribute("userName", null);
		request.getSession().setAttribute("UserId", null);
		request.getSession().setAttribute("groupList", null);
		request.getSession().setAttribute("settingValue", null);
		request.getSession().setAttribute("agingList", null);
		request.getSession().setAttribute("agingList_other", null);

		ModelAndView model = new ModelAndView("index");
		// LoginBean loginBean = new LoginBean();
		model.addObject("LoginBean", loginBean);
		model.addObject("regisBean", regisBean);

		return model;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean,
			RegistrationBean regisBean) {
		ModelAndView model =null;
		if(request.getSession().getAttribute("UserId")!= null)
		{

		model = new ModelAndView("home");
		// LoginBean loginBean = new LoginBean();
		model.addObject("LoginBean", loginBean);
		model.addObject("regisBean", regisBean);
		}
		else{
				
				model=new ModelAndView("index");
			}

		return model;
	}
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("LoginBean") LoginBean loginBean) {
		request.getSession().invalidate();
		ModelAndView model = null;
		System.out.println("inside executeLogin");
		logger.debug("Inside executeLogin and before try block:");
		try {
			
			logger.debug("Before isLdapValidUser:");
			
			boolean isLdapValidUser=ldapService.authenticate(loginBean.getUsername(), loginBean.getPassword());

					//ldapService.authenticate(loginBean.getUsername(), loginBean.getPassword());
					
			logger.debug("After isLdapValidUser and before if Statement:"+isLdapValidUser);
			
			
			//if (isValidUser != null && isValidUser != "0") {
			if (isLdapValidUser) {
				
				logger.debug("Inside  if Statement:");
				
				String isValidUser = loginDelegate.isValidUser(loginBean.getUsername());
				System.out.println("User Login Successful" + isValidUser);
				//String screenID = loginDelegate.getScreenID(isValidUser);
				
				String screenID = loginDelegate.getScreenID(isValidUser);
				logger.debug("After screen Id:"+screenID);
				
				request.getSession().setAttribute("sid", screenID);
				
				logger.debug("After request.getSession().setAttribute('sid', screenID)");
				
				request.getSession().setAttribute("menuNames", loginDelegate.getMenuNames(screenID));
				
				logger.debug("After request.getSession().setAttribute('menuNames', loginDelegate.getMenuNames(screenID))");
				
				request.getSession().setAttribute("loggedUser", loginBean.getUsername());
				
				logger.debug("Afterrequest.getSession().setAttribute('loggedUser', loginBean.getUsername())");
				
				request.getSession().setAttribute("userName", loginBean.getUsername());
				
				logger.debug("After request.getSession().setAttribute('userName', loginBean.getUsername())");
				
				request.getSession().setAttribute("UserId", loginDelegate.getProfile(loginBean.getUsername()).getId());
				
				logger.debug("After request.getSession().setAttribute('UserId', loginDelegate.getProfile(loginBean.getUsername()).getId())");

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:MM:SS");
				Date date = new Date();
			//	System.out.println(dateFormat.format(date));
			//	request.getSession().setAttribute("currentDate", dateFormat.format(date));
				int id=loginDelegate.getProfile(loginBean.getUsername()).getId();
				
				logger.debug("After loginDelegate.getProfile(loginBean.getUsername()).getId()");

				
				System.out.println("id="+id);
				logger.debug("After system out checking for id:"+id);
				//request.getSession().setAttribute("functionTable", loginDelegate.getNumberOfCasesById(id));
				
				//request.getSession().setAttribute("functionTableHR", loginDelegate.getNumberOfCasesHRById(id));
				logger.debug("Before request.getSession().setAttribute('groupList', loginDelegate.getBusinessGroup(id))");
				request.getSession().setAttribute("groupList", loginDelegate.getBusinessGroup(id));
				request.getSession().setAttribute("settingValue", loginDelegate.getSettingValue());
				logger.debug("After	request.getSession().setAttribute('settingValue', loginDelegate.getSettingValue()");

				
				ArrayList<String> agingList=new ArrayList<String>();
				agingList.add("Creation Date");agingList.add("Received Date");/*agingList.add("Current Role");*/
				request.getSession().setAttribute("agingList", agingList);
				ArrayList<String> agingList_other=new ArrayList<String>();
				agingList_other.add("Creation Date");agingList_other.add("Current Role");/*agingList.add("Current Role");*/
				request.getSession().setAttribute("agingList_other", agingList_other);
				logger.debug("Before ModelAndView('home')");
				
				return new ModelAndView("home");
			} else {
				logger.debug("inside else block");
				model = new ModelAndView("index");
				logger.debug("After ModelAndView('index')");
				model.addObject("regisBean", new RegistrationBean());
				request.setAttribute("message", "Invalid credentials!!");
			}

		}
		
		catch(NullPointerException ex){
			
			request.setAttribute("adminMessage", "Please contact Admin to provide screen");
			
			return new ModelAndView("home");
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

/*	@RequestMapping(value = "/regis", method = RequestMethod.POST)
	public ModelAndView executeRegis(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("regisBean") RegistrationBean regisBean, LoginBean loginBean) throws SQLException {
		ModelAndView model = new ModelAndView("index");
		boolean reg = loginDelegate.insertUser(regisBean.getUsename(), regisBean.getName(), regisBean.getPassword(),
				regisBean.getGender(), regisBean.getEmail(), regisBean.getPassportNo(), regisBean.getIssueDate(),
				regisBean.getExpiryDate(), regisBean.getNationality(), regisBean.getActor());
		// LoginBean loginBean = new LoginBean();
		model.addObject("LoginBean", loginBean);
		model.addObject("regisBean", regisBean);
		request.setAttribute("message", "Register Successful, Go for Login!!");

		return model;
	}*/

	@RequestMapping(value = "/regis", method = RequestMethod.GET)
	public ModelAndView getRegis(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("regisBean") RegistrationBean regisBean, LoginBean loginBean) {
		ModelAndView model = new ModelAndView("index");
		// LoginBean loginBean = new LoginBean();
		model.addObject("LoginBean", loginBean);
		model.addObject("regisBean", regisBean);
		request.setAttribute("regis", "yes");
		return model;
	}

	@RequestMapping(value = "/calculateEmi", method = RequestMethod.GET)
	public ModelAndView calcEmi(HttpServletRequest request, HttpServletResponse response, RegistrationBean regisBean) {
		ModelAndView model = new ModelAndView("calculateEmi");
		// LoginBean loginBean = new LoginBean();
		model.addObject("regisBean", regisBean);
		request.setAttribute("message", "Registration");

		return model;
	}

	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public ModelAndView getProfile(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(request.getSession().getAttribute("loggedUser"));
		request.setAttribute("loggedInUser", request.getSession().getAttribute("loggedUser"));
		RegistrationBean registrationBean = null;

		try {

			registrationBean = loginDelegate.getProfile((String) request.getSession().getAttribute("loggedUser"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("profile", "registrationBean", registrationBean);
	}
	
	
	
}
