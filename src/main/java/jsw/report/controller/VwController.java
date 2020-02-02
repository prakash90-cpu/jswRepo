package jsw.report.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*import java.io.file.Files;
import java.io.file.Path;
import java.io.file.Paths;*/
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*import org.rosuda.REngine.REXPMismatchException;*/
/*import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;

import jsw.report.delegate.VwDelegate;
import jsw.report.viewBean.*;

@Controller
@RequestMapping(value = "/mainController")
public class VwController {
	private static String UPLOADED_FOLDER = "D://";

	@Autowired
	private VwDelegate vwDelegate;

	public VwDelegate getVwDelegate() {
		return vwDelegate;
	}

	public void setVwDelegate(VwDelegate vwDelegate) {
		this.vwDelegate = vwDelegate;
	}

		
	// ****************Add User********************
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUserGet(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean) throws SQLException {
		ModelAndView model = new ModelAndView("updateUser");
		
		if(request.getSession().getAttribute("UserId")!= null)
		{
		request.setAttribute("regisBean", new RegistrationBean());
		request.setAttribute("addUpdate","Add" );
		request.setAttribute("userLink","addUser" );
		List<Role> list4 = null;
		try {

			list4 = vwDelegate.getRole();

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("userRole", list4);
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
		}else{
			
			model=new ModelAndView("index");
		}
		return model;

	} 
		@RequestMapping(value = "/addUser", method = RequestMethod.POST)
		public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
				@ModelAttribute("regisBean") RegistrationBean user) throws SQLException {
			ModelAndView model = new ModelAndView("adminRights");
			
			if(request.getSession().getAttribute("UserId")!= null)
			{
			request.setAttribute("message", "add UserType");
			List<RegistrationBean> list = null;
			try {

				boolean insert = vwDelegate.addUser(user);

			} catch (Exception e) {
				e.printStackTrace();
			}

			

			try {

				list = vwDelegate.getAllUsers();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userList", list);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
			}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}
		// *****************End User***********

		@RequestMapping(value = "/admin", method = RequestMethod.GET)
		public ModelAndView adminRights(HttpServletRequest request, HttpServletResponse response,
				RegistrationBean regisBean, @ModelAttribute("LoginBean") LoginBean loginBean) {
		
			ModelAndView model = new ModelAndView("adminRights");
			if(request.getSession().getAttribute("UserId")!= null)
			{
				List list = null;
			
			try {

				list = vwDelegate.getAllUsers();
				System.out.println(regisBean);

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("userList", list);
			request.setAttribute("regisBean", new RegistrationBean());
			
			
			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
            }else{
				
				model=new ModelAndView("index");
			}
			
			return model;

		}

		@RequestMapping(value = "/screen", method = RequestMethod.GET)
		public ModelAndView screenRights(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
				 Role screenRole) throws Exception {
			
			ModelAndView model = new ModelAndView("userMenuRights");

			if(request.getSession().getAttribute("UserId")!= null)
			{
			List<String> list3 = null;
			try {

				list3 = vwDelegate.getUserName();

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);

			request.setAttribute("userList", list3);
			model.addObject("LoginBean", loginBean);
			model.addObject("regisBean", regisBean);
			model.addObject("screenRole", screenRole);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );

}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}

		@RequestMapping(value = "/userScreen", method = RequestMethod.GET)
		public ModelAndView userScreenRights(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws Exception {
			ModelAndView model = new ModelAndView("userScreenPermission");
			if(request.getSession().getAttribute("UserId")!= null)
			{
			JSONArray json = vwDelegate.getMenuTree();
			JSONObject main = new JSONObject();
			request.setAttribute("jsonArray", main.put("main", json).toString());

			List list = null;
			try {

				list = vwDelegate.getAllUsers();
				System.out.println(regisBean);

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("userSList", list);

			List<String> list3 = null;
			try {

				list3 = vwDelegate.getUserName();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("message", "");

			request.setAttribute("userList", list3);

			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);

			model.addObject("LoginBean", loginBean);
			model.addObject("regisBean", regisBean);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}

		@RequestMapping(value = "/screenAssign", method = RequestMethod.GET)
		public ModelAndView screenAssignGet(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,@ModelAttribute("screenRole")Role screenRole,
				Role role) throws Exception {
			ModelAndView model=null;
			if(request.getSession().getAttribute("UserId")!= null)
			{
			model = new ModelAndView("newRole");
			JSONArray json = vwDelegate.getMenuTree();
			JSONObject main = new JSONObject();
			request.setAttribute("jsonArray", main.put("main", json).toString());
	System.out.println("jsonArray  "+json);


	request.setAttribute("helpManual","USER_MANUAL.pdf" );
	
}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}
		
		
		
		
		@RequestMapping(value = "/screenAssign", method = RequestMethod.POST)
		public ModelAndView screenAssign(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,
 @ModelAttribute("screenRole") Role screenRole,
				Role role) throws Exception {
			ModelAndView model = null;
			System.out.println(regisBean);
			if(request.getSession().getAttribute("UserId")!= null)
			{
			try {

				boolean insert = vwDelegate.addRole(screenRole);

			} catch (Exception e) {
				e.printStackTrace();
			}

			model = new ModelAndView("userMenuRights");

		
			List<String> list3 = null;
			try {

				list3 = vwDelegate.getUserName();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userList", list3);
			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);

			model.addObject("screenRole", role);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}

		@RequestMapping(value = "/screenUserAssign", method = RequestMethod.POST)
		public ModelAndView screenUserAssign(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean ) throws Exception {

			ModelAndView model = new ModelAndView("userScreenPermission");
			if(request.getSession().getAttribute("UserId")!= null)
			{
				System.out.println(regisBean);
			

			List<String> list3 = null;
			try {

				list3 = vwDelegate.getUserName();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userList", list3);

			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);

			try {

				boolean insert = vwDelegate.updateScreenID(regisBean);

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("message", "Screen role Successfully assigned to user!!");
			model.addObject("LoginBean", loginBean);
			model.addObject("regisBean", regisBean);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}
			return model;

		}
	
	
		@RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.GET)
		public ModelAndView deleteRole(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("RoleBean")Role role){
			ModelAndView model = new ModelAndView("userMenuRights");
			if(request.getSession().getAttribute("UserId")!= null)
			{
			try {

			 vwDelegate.deleteRole(id);

			} catch (Exception e) {
				e.printStackTrace();
			}

			
			List<String> list3 = null;
			try {

				list3 = vwDelegate.getUserName();

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Role> list4 = null;
			try {

				list4 = vwDelegate.getRole();

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("userRole", list4);

			request.setAttribute("userList", list3);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}

			return model;
		}
		@RequestMapping(value = "/editRole/{id}", method = RequestMethod.GET)
		public ModelAndView editBusiness(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,Role role) throws Exception {
			ModelAndView model = new ModelAndView("newRole");
			
			if(request.getSession().getAttribute("UserId")!= null)
			{
			JSONArray json = vwDelegate.getMenuTree();
			JSONObject main = new JSONObject();
			request.setAttribute("jsonArray", main.put("main", json).toString());
	System.out.println("jsonArray  "+json);
		Role screenRole=null;
	try {

		screenRole = vwDelegate.editRole(id);

	} catch (Exception e) {
		e.printStackTrace();
	}
	
	request.setAttribute("screenRole", screenRole);
	request.setAttribute("helpManual","USER_MANUAL.pdf" );
			
}else{
				
				model=new ModelAndView("index");
			}
	return model;
		
			
			}
	
	
	
	// ***********File Uploading**********

		@RequestMapping("/selectFile")
		public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = new ModelAndView("FileUploading");
			if(request.getSession().getAttribute("UserId")!= null)
			{
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}

			return model;
		}

		// @RequestMapping(value = "/upload", method = RequestMethod.POST)
		@RequestMapping("/upload") // //new annotation since 4.3
		
	    public ModelAndView singleFileUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam("files") MultipartFile file,@RequestParam("documentType")String type) throws SQLException {
	    	
		 ModelAndView model = new ModelAndView("SuccessFullyUploaded");
		 if(request.getSession().getAttribute("UserId")!= null)
			{
		
		FileList list=new FileList();
		list.setDocumentType(type);
	 
	        if (file.isEmpty()) {
	          
	            return model;
	        }
	         boolean insert;
	        
	        try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            String path = UPLOADED_FOLDER + file.getOriginalFilename();
	         
	            //This is supportable in java 8
	            //Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            File serverFile = new File(path);
	       	
	            
	            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				
				
				
	          // Files.write(path, bytes);
	            
	            
	            
	           
	           
	           
	            insert = vwDelegate.addFileName(file.getOriginalFilename(),list);
	            stream.close();
	           
	          

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        request.setAttribute("helpManual","USER_MANUAL.pdf" );
}else{
				
				model=new ModelAndView("index");
			}
	        
	        return model;
	    }
		
		
		
		
		// *********** end File Uploading**********

		// **************File Importing****************

			@RequestMapping(value = "/fileList", method = RequestMethod.GET)
			public ModelAndView getFileList(HttpServletRequest request, HttpServletResponse response, FileList flist) {
				ModelAndView model = new ModelAndView("FileList");
			
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List<FileList> list = null;
				try {

					list = vwDelegate.getFileList();

				} catch (Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("fileData", list);
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				}else{
					
					model=new ModelAndView("index");
				}

				return model;

			}

			@RequestMapping(value = "/importFile/{id}", method = RequestMethod.GET)
			public ModelAndView insert(HttpServletRequest request, HttpServletResponse response, @PathVariable int id)
					throws IOException, JSONException, SQLException {
			
				ModelAndView model=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				String s = vwDelegate.getFileById(id);
			
				String importRasult =vwDelegate.saveFileToDatabase(s);
				
				if(importRasult=="success"){
				vwDelegate.updatefileStatus(id);
				
				model = new ModelAndView("Success");
				}
				else{
					request.setAttribute("message", importRasult);
					 model = new ModelAndView("FileList");
					 List<FileList> list = null;
						try {

							list = vwDelegate.getFileList();

						} catch (Exception e) {
							e.printStackTrace();
						}

						request.setAttribute("fileData", list);
					
				}
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				}else{
					
					model=new ModelAndView("index");
				}
				
				return model;
			}



			@RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.GET)
			public ModelAndView deleteFile(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,
					FileList stage) throws SQLException {

				ModelAndView model = new ModelAndView("FileList");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				boolean isValidUser = vwDelegate.deleteFileList(id);

				List list = null;
				try {

					list = vwDelegate.getFileList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				request.setAttribute("fileData", list);
				}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
	
	
	
	
	
	
	
	
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
			public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,
					LoginBean loginBean, RegistrationBean regisBean) throws SQLException {
				ModelAndView model = new ModelAndView("adminRights");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				boolean isValidUser = vwDelegate.deleteUser(id);
				if (isValidUser) {
					System.out.println("delete user success");

				} else {
					request.setAttribute("message", "Invalid credentials!!");
				}
				List<RegistrationBean> list = null;
				try {

					list = vwDelegate.getAllUsers();

				} catch (Exception e) {
					e.printStackTrace();
				}
				

				List<Role> list4 = null;
				try {

					list4 = vwDelegate.getRole();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				request.setAttribute("userRole", list4);
				request.setAttribute("userList", list);
				model.addObject("regisBean", regisBean);
				}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}

			@RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
			public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,
					LoginBean loginBean, RegistrationBean regisBean) throws SQLException {
				ModelAndView model = new ModelAndView("updateUser");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				RegistrationBean regis = vwDelegate.editUser(id);
				System.out.println("edit User"+regis);
				request.setAttribute("regisData", regis);
				model.addObject("regisBean", regis);
				request.setAttribute("addUpdate","Update" );
				request.setAttribute("userLink","updateUser" );
				
				

				List<Role> list4 = null;
				try {

					list4 = vwDelegate.getRole();

				} catch (Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("userRole", list4);
				
				List<String> list5=new ArrayList<String>();
				list5.add("L1");list5.add("L2");list5.add("L3");list5.add("L4");list5.add("L5");list5.add("L6");
				
				list5.add("L6");list5.add("L7");
				request.setAttribute("UserLevelList", list5);
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				}else{
					
					model=new ModelAndView("index");
				}
				
				return model;
			}

			@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
			public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response,
					@ModelAttribute("regisData") RegistrationBean regisData) throws SQLException {
				ModelAndView model = new ModelAndView("adminRights");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {

					boolean insert = vwDelegate.updateUser(regisData);

				} catch (Exception e) {
					e.printStackTrace();
				}
				List<RegistrationBean> list = null;
				try {

					list = vwDelegate.getAllUsers();

				} catch (Exception e) {
					e.printStackTrace();
				}
				RegistrationBean regisBean=new RegistrationBean();
			
			
				List<Role> list4 = null;
				try {

					list4 = vwDelegate.getRole();

				} catch (Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("userRole", list4);
				request.setAttribute("userList", list);
				model.addObject("regisBean", regisBean);
				request.setAttribute("helpManual","USER_MANUAL.pdf" );
				}else{
					
					model=new ModelAndView("index");
				}

				return model;

			}
	
			/*private FunctionAppBean getfunctionAppBeanWithDate(){
				
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
				String dateTemp=dateToday+"-"+month+"-"+(1900+TodayDate.getYear());
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


			
*/	
	
	
	

	@RequestMapping(value = "/listUserFilter", method = RequestMethod.GET)
	public ModelAndView processAccess(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
	
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listUserFilter");
	
		
		request.setAttribute("userFilterList", vwDelegate.getUserFilterList());
		
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	@RequestMapping(value = "/submitUserFilter", method = RequestMethod.GET)
	public ModelAndView listUserFilter(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("processAccess");
	
		
		request.setAttribute("functionTableUserFilter", vwDelegate.getProcessAccessList());
		FunctionAppBean functionAppBean=new FunctionAppBean();
		request.setAttribute("FunctionAppBean",functionAppBean );
		
		
		List<String> list3 = null;
		try {

			list3 = vwDelegate.getUserName();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("userList", list3);
		
		//request.setAttribute("userFilterList", vwDelegate.getUserFilterList());
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		
		return model;
	}
	
	
	@RequestMapping(value = "/submitUserFilter", method = RequestMethod.POST)
	public ModelAndView submitUserFilter(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean,@ModelAttribute("FunctionAppBean")FunctionAppBean functionAppBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listUserFilter");
	
		
		vwDelegate.submitUserFilter(functionAppBean);
		request.setAttribute("functionTableUserFilter", vwDelegate.getProcessAccessList());
	System.out.println("functionAppBean"+functionAppBean);	
		List<String> list3 = null;
		try {

			list3 = vwDelegate.getUserName();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("userList", list3);
		request.setAttribute("userFilterList", vwDelegate.getUserFilterList());
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
		
	}
	else{
			
			model=new ModelAndView("index");
		}
		return model;
	}
	
	
	@RequestMapping(value = "/deleteUserFilter/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUserFilter(HttpServletRequest request, HttpServletResponse response, @PathVariable int id, @ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean) throws SQLException, JSONException {
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("listUserFilter");
	
			
vwDelegate.deleteUserFilter(id);
		
		request.setAttribute("userFilterList", vwDelegate.getUserFilterList());
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		
		return model;
	}
	
	
	
	
	@RequestMapping(value = "/editUserFilter/{id}", method = RequestMethod.GET)
	public ModelAndView editUserFilter(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LoginBean") LoginBean loginBean,@ModelAttribute("regisBean") RegistrationBean regisBean, @PathVariable int id) throws SQLException {
		
		ModelAndView model = null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
			model = new ModelAndView("processAccess");
	
		//FunctionApplication functionApplication=vwDelegate.editUserFilter(id);
		
		//System.out.println("userFilter"+userFilter);
		request.setAttribute("functionTableUserFilter",vwDelegate.editUserFilter(id));
		FunctionAppBean functionAppBean=new FunctionAppBean();
		int userId=vwDelegate.edit_User_Filter(id).getId();
		functionAppBean.setUserId(vwDelegate.editUser(userId).getUsename());
		functionAppBean.setBusiness("y");
		System.out.println("Edit**functionAppBean"+functionAppBean);
		request.setAttribute("FunctionAppBean",functionAppBean );
		
		
		List<String> list3 = null;
		try {

			list3 = vwDelegate.getUserName();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("userList", list3);

		
		
		//request.setAttribute("userFilterList", vwDelegate.getUserFilterList());
		request.setAttribute("helpManual","USER_MANUAL.pdf" );
		}
		else{
				
				model=new ModelAndView("index");
			}
		
		
		return model;
	}
	
			
				
	@RequestMapping(value = "/displayBy", method = RequestMethod.GET)
	public ModelAndView displayBy(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("LoginBean") LoginBean loginBean, @ModelAttribute("regisBean") RegistrationBean regisBean)
			throws SQLException {

		ModelAndView model = null;
		if (request.getSession().getAttribute("UserId") != null) {
			model = new ModelAndView("displayBy");

			ApplicationSetting displayBy= new ApplicationSetting();
			request.setAttribute("displayBy",displayBy);
			request.setAttribute("helpManual","USER_MANUAL.pdf" );
		} else {

			model = new ModelAndView("index");
		}
		return model;
	}
				
	@RequestMapping(value = "/displayBy", method = RequestMethod.POST)
	public ModelAndView displayByPOST(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("LoginBean") LoginBean loginBean, @ModelAttribute("regisBean") RegistrationBean regisBean
			, @ModelAttribute("displayBy") ApplicationSetting displayBy
			)
			throws SQLException {

		ModelAndView model = null;
		if (request.getSession().getAttribute("UserId") != null) {
			model = new ModelAndView("displayBy");
       System.out.println(displayBy);
       
      boolean row= vwDelegate.insertSetting(displayBy);
		
      if(row==true)
    	  request.setAttribute("insert", "yes");
      request.setAttribute("helpManual","USER_MANUAL.pdf" );
		} else {

			model = new ModelAndView("index");
		}
		return model;
	}			
				
	
}
