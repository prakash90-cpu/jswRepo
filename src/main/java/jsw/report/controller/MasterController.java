package jsw.report.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jsw.report.delegate.MasterDelegate;
import jsw.report.delegate.VwDelegate;
import jsw.report.viewBean.Business;
import jsw.report.viewBean.BusinessGroup;
import jsw.report.viewBean.BusinessGroupLoc;
import jsw.report.viewBean.BusinessRoles;
import jsw.report.viewBean.CaseType;
import jsw.report.viewBean.DocumentType;
import jsw.report.viewBean.Function;
import jsw.report.viewBean.Location;
import jsw.report.viewBean.LocationBusiness;
import jsw.report.viewBean.Menutable;
import jsw.report.viewBean.PayrollArea;
import jsw.report.viewBean.PersonnelArea;
import jsw.report.viewBean.Stages;
import jsw.report.viewBean.Steps;

@Controller
@RequestMapping(value = "/master")
public class MasterController {

	@Autowired
	private MasterDelegate masterDelegate;
	

	
	public MasterDelegate getMasterDelegate() {
		return masterDelegate;
	}
	public void setMasterDelegate(MasterDelegate masterDelegate) {
		this.masterDelegate = masterDelegate;
	}
	
	@RequestMapping(value = "/listFunction", method = RequestMethod.GET)
	public ModelAndView getFunctionList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("FunctionManagementBean")Function function) {
		ModelAndView model = new ModelAndView("listFunction");
		if(request.getSession().getAttribute("UserId")!= null)
		{
		List functionList = null;
		try {

			functionList = masterDelegate.getFunctionManagement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("FunctionManagementList", functionList);
		request.setAttribute("helpManual","Master_Manual.pdf" );
		}
	else{
			
			model=new ModelAndView("index");
		}

		return model;
	}
	@RequestMapping(value = "/addFunction", method = RequestMethod.GET)
	public ModelAndView newFunction(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("FunctionManagementBean")Function functionBean){
		ModelAndView model1 = new ModelAndView("newFunction");
		request.setAttribute("helpManual","Master_Manual.pdf" );
		return model1;
		
	}
	@RequestMapping(value = "/submitFunction", method = RequestMethod.POST)
	public ModelAndView submitFunctionManagement(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("FunctionManagementBean")Function functionBean) throws SQLException, JSONException{
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
		if(functionBean.getIsActive()==null)
			functionBean.setIsActive("N");
		boolean insert;
		try {

			 insert = masterDelegate.addFunction(functionBean);
			 System.out.println("ggginsert"+ insert );

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	     model1 = getFunctionList(request,response,functionBean);
	 	request.setAttribute("helpManual","Master_Manual.pdf" );
		}
	else{
			
			model1=new ModelAndView("index");
		}
		 
		 return model1;
		
	}
	@RequestMapping(value = "/deleteFunction/{id}", method = RequestMethod.GET)
	public ModelAndView deleteFunctionList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("FunctionManagementBean")Function functionBean){
		
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
		try {

			boolean insert = masterDelegate.deleteFunction(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model1=getFunctionList( request,  response, functionBean);
		request.setAttribute("helpManual","Master_Manual.pdf" );
		}
	else{
			
			model1=new ModelAndView("index");
		}

		return model1;
		
	}
	@RequestMapping(value = "/editFunction/{id}", method = RequestMethod.GET)
	public ModelAndView editFunction(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws SQLException {
		ModelAndView model = new ModelAndView("editFunction");
		
		if(request.getSession().getAttribute("UserId")!= null)
		{
		List<Function> functionList=new ArrayList<Function>();

		Function function = masterDelegate.editFunction(id);
		System.out.println("kkkkk"+function.getFunctionName());
		functionList.add(function);
		request.setAttribute("FunctionManagementBean", function);
		request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
			
			model=new ModelAndView("index");
		}
		return model;

	}

	@RequestMapping(value = "/updateFunction", method = RequestMethod.POST)
	public ModelAndView updateFunction(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("FunctionManagementBean") Function functionData) throws SQLException {
		ModelAndView model=null;
		//ModelAndView model = new ModelAndView("updatefunction");
		if(request.getSession().getAttribute("UserId")!= null)
		{
		request.setAttribute("message", "edit UserType");
		if(functionData.getIsActive()==null)
			functionData.setIsActive("N");
		
		try {

			boolean insert = masterDelegate.updateFunction(functionData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		model=getFunctionList( request,  response, functionData);
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model=new ModelAndView("index");
		}

		return model;
		

	}
	
	//****************************Business *********************
	@RequestMapping(value = "/listBusiness", method = RequestMethod.GET)
	public ModelAndView getBusinessList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("FunctionManagementBean")Business business) {
		ModelAndView model = new ModelAndView("listBusiness");
		if(request.getSession().getAttribute("UserId")!= null)
		{
		List businessList = null;
		try {

			businessList = masterDelegate.getBusiness();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("BusinessList", businessList);
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model=new ModelAndView("index");
		}

		return model;
	}
	@RequestMapping(value = "/addBusiness", method = RequestMethod.GET)
	public ModelAndView newBusiness(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessBean")Business business){
		ModelAndView model = new ModelAndView("newBusiness");
		if(request.getSession().getAttribute("UserId")!= null)
		{
		request.setAttribute("head", "New");
		request.setAttribute("btn", "Add");
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model=new ModelAndView("index");
		}

		return model;
		
	}
	@RequestMapping(value = "/submitBusiness", method = RequestMethod.POST)
	public ModelAndView submitBusiness(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessBean")Business business) throws SQLException, JSONException{
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
		if(business.getIsActive()==null)
			business.setIsActive("N");
		boolean insert;
		if(business.getId()==0)
			 insert = masterDelegate.addBusiness(business);
		else
			 insert = masterDelegate.updateBusiness(business);
		
		
		
	     model1 = getBusinessList(request,response,business);
	 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model1=new ModelAndView("index");
		}

		 return model1;
		
	}
	@RequestMapping(value = "/deletebusiness/{id}", method = RequestMethod.GET)
	public ModelAndView deleteBusinessList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("BusinessBean")Business business){
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
		try {

			boolean insert = masterDelegate.deleteBusiness(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model1=getBusinessList( request,  response, business);
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model1=new ModelAndView("index");
		}

		return model1;
		
	}
	@RequestMapping(value = "/editBusiness/{id}", method = RequestMethod.GET)
	public ModelAndView editBusiness(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,Business business) throws SQLException {
		ModelAndView model = new ModelAndView("newBusiness");
		//List<Business> functionList=new ArrayList<Business>();
		if(request.getSession().getAttribute("UserId")!= null)
		{
		 business = masterDelegate.editBusiness(id);
		
		 
		
		//functionList.add(function);
		request.setAttribute("BusinessBean",business);
		
		request.setAttribute("head", "Edit");
		request.setAttribute("btn", "Update");
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model=new ModelAndView("index");
		}

		return model;
	}

	//***********************************Location***********************
	
	@RequestMapping(value = "/listLocation", method = RequestMethod.GET)
	public ModelAndView getLocationList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LocationBean")Location location) {
		ModelAndView model = new ModelAndView("listLocation");
		if(request.getSession().getAttribute("UserId")!= null)
		{

		List locationList = null;
		try {

			locationList = masterDelegate.getLocation();

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("LocationList", locationList);
		request.setAttribute("helpManual","Master_Manual.pdf" );
		
}else{
			
			model=new ModelAndView("index");
		}
		
		return model;
	}
	@RequestMapping(value = "/addLocation", method = RequestMethod.GET)
	public ModelAndView newLocation(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LocationBean")Location location){
		ModelAndView model1 = new ModelAndView("newLocation");
		if(request.getSession().getAttribute("UserId")!= null)
		{

		request.setAttribute("head", "New");
		request.setAttribute("btn", "Add");
		request.setAttribute("helpManual","Master_Manual.pdf" );
		
}else{
			
			model1=new ModelAndView("index");
		}

		return model1;
		
	}
	@RequestMapping(value = "/submitLocation", method = RequestMethod.POST)
	public ModelAndView submitLocation(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("LocationBean")Location location) throws SQLException, JSONException{
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{
		
		if(location.getIsActive()==null)
			location.setIsActive("N");
		boolean insert;
		
		if(location.getLocationId()==0)
			 insert = masterDelegate.addLocation(location);
		else
			 insert = masterDelegate.updateLocation(location);
		
		
		
	     model1 = getLocationList(request,response,location);
	 	request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

		 return model1;
		
	}
	@RequestMapping(value = "/deleteLocation/{id}", method = RequestMethod.GET)
	public ModelAndView deleteLocationList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("locationBean")Location location){
		ModelAndView model1=null;
		if(request.getSession().getAttribute("UserId")!= null)
		{

		try {

			boolean insert = masterDelegate.deleteLocation(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model1=getLocationList( request,  response, location);
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model1=new ModelAndView("index");
		}

		return model1;
		
	}
	@RequestMapping(value = "/editLocation/{id}", method = RequestMethod.GET)
	public ModelAndView editLocation(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,Location location) throws SQLException {
		ModelAndView model = new ModelAndView("newLocation");
		//List<Business> functionList=new ArrayList<Business>();
		if(request.getSession().getAttribute("UserId")!= null)
		{

		location = masterDelegate.editLocation(id);
		
		 
		
		//functionList.add(function);
		request.setAttribute("LocationBean",location);
		
		request.setAttribute("head", "Edit");
		request.setAttribute("btn", "Update");
		request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
			
			model=new ModelAndView("index");
		}

		return model;
	}
	
	//**************************End**********************************
	
	
	//***********************************Case Type***********************
	
		@RequestMapping(value = "/listCaseType", method = RequestMethod.GET)
		public ModelAndView getCaseTypeList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("CaseTypeBean")CaseType caseType) {
			ModelAndView model = new ModelAndView("listCaseType");
			if(request.getSession().getAttribute("UserId")!= null)
			{

			List caseTypeList = null;
			try {

				caseTypeList = masterDelegate.getCaseType();

			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("CaseTypeList", caseTypeList);

			}else{
				
				model=new ModelAndView("index");
			}
			request.setAttribute("helpManual","Master_Manual.pdf" );
			return model;
		}
		@RequestMapping(value = "/addCaseType", method = RequestMethod.GET)
		public ModelAndView newCaseType(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("CaseTypeBean")CaseType caseType){
			ModelAndView model1 = new ModelAndView("newCaseType");
			if(request.getSession().getAttribute("UserId")!= null)
			{
			
			request.setAttribute("head", "New");
			request.setAttribute("btn", "Add");
			}else{
				
				model1=new ModelAndView("index");
			}
			request.setAttribute("helpManual","Master_Manual.pdf" );
			return model1;
			
		}
		@RequestMapping(value = "/submitCaseType", method = RequestMethod.POST)
		public ModelAndView submitCaseType(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("CaseType")CaseType caseType) throws SQLException, JSONException{
			ModelAndView model1=null;

			if(request.getSession().getAttribute("UserId")!= null)
			{

			if(caseType.getIsActive()==null)
				caseType.setIsActive("N");
			boolean insert;
			if(caseType.getId()==0)
				 insert = masterDelegate.addCaseType(caseType);
			else
				 insert = masterDelegate.updateCaseType(caseType);
			try {

				
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		     model1 = getCaseTypeList(request,response,caseType);
		 	request.setAttribute("helpManual","Master_Manual.pdf" );
			}else{
				
				model1=new ModelAndView("index");
			}

			 return model1;
			
		}
		@RequestMapping(value = "/deleteCaseType/{id}", method = RequestMethod.GET)
		public ModelAndView deleteCaseTypeList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("CaseTypeBean")CaseType caseType){

			ModelAndView model1=null;
			if(request.getSession().getAttribute("UserId")!= null)
			{

			try {

				boolean insert = masterDelegate.deleteCaseType(id);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 model1=getCaseTypeList( request,  response, caseType);
				request.setAttribute("helpManual","Master_Manual.pdf" );
			}else{
				
				model1=new ModelAndView("index");
			}

			return model1;
			
		}
		@RequestMapping(value = "/editCaseType/{id}", method = RequestMethod.GET)
		public ModelAndView editCaseType(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,CaseType caseType) throws SQLException {
			ModelAndView model = new ModelAndView("newCaseType");
			//List<Business> functionList=new ArrayList<Business>();
			if(request.getSession().getAttribute("UserId")!= null)
			{

			caseType = masterDelegate.editCaseType(id);
			
			 
			
			//functionList.add(function);
			request.setAttribute("CaseTypeBean",caseType);
			
			request.setAttribute("head", "Edit");
			request.setAttribute("btn", "Update");
			request.setAttribute("helpManual","Master_Manual.pdf" );
			}else{
				
				model=new ModelAndView("index");
			}

			return model;
		}
		
		//**************************End**********************************
	
	
		//***********************************Document Type***********************
		
			@RequestMapping(value = "/listDocumentType", method = RequestMethod.GET)
			public ModelAndView getDocumentTypeList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("DocumentTypeBean")DocumentType documentType) {
				ModelAndView model = new ModelAndView("listDocumentType");
				if(request.getSession().getAttribute("UserId")!= null)
				{

				List documentTypeList = null;
				try {

					documentTypeList = masterDelegate.getDocumentType();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("DocumentTypeList", documentTypeList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
				}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			@RequestMapping(value = "/addDocumentType", method = RequestMethod.GET)
			public ModelAndView newDocumentType(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("DocumentTypeBean")DocumentType documentType){
				ModelAndView model1 = new ModelAndView("newDocumentType");

				if(request.getSession().getAttribute("UserId")!= null)
				{

				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );
				}else{
					
					model1=new ModelAndView("index");
				}
				
				return model1;
				
			}
			@RequestMapping(value = "/submitDocumentType", method = RequestMethod.POST)
			public ModelAndView submitDocumentType(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("DocumentType")DocumentType documentType) throws SQLException, JSONException{
				ModelAndView model1=null;

				if(request.getSession().getAttribute("UserId")!= null)
				{

				if(documentType.getIsActive()==null)
					documentType.setIsActive("N");
				boolean insert;
				if(documentType.getId()==0)
					 insert = masterDelegate.addDocumentType(documentType);
				else
					 insert = masterDelegate.updateDocumentType(documentType);
				try {

					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			     model1 = getDocumentTypeList(request,response,documentType);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
				}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			@RequestMapping(value = "/deleteDocumentType/{id}", method = RequestMethod.GET)
			public ModelAndView deleteDocumentTypeList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("DocumentTypeBean")DocumentType documentType){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{

				try {

					boolean insert = masterDelegate.deleteDocumentType(id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getDocumentTypeList( request,  response, documentType);
				request.setAttribute("helpManual","Master_Manual.pdf" );
				}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			@RequestMapping(value = "/editDocumentType/{id}", method = RequestMethod.GET)
			public ModelAndView editDocumentType(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,DocumentType documentType) throws SQLException {
				ModelAndView model = new ModelAndView("newDocumentType");
				//List<Business> functionList=new ArrayList<Business>();

				if(request.getSession().getAttribute("UserId")!= null)
				{

				documentType = masterDelegate.editDocumentType(id);
				
				 
				
				//functionList.add(function);
				request.setAttribute("DocumentTypeBean",documentType);
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );
				
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			//**************************End**********************************
		
		
		
			//*************************Business Group*********************
			@RequestMapping(value = "/listBusinessGroup", method = RequestMethod.GET)
			public ModelAndView getBusinessGroupList(HttpServletRequest request, HttpServletResponse response,BusinessGroup businessGroup) {
				ModelAndView model = new ModelAndView("listBusinessGroup");
				if(request.getSession().getAttribute("UserId")!= null)
				{

				List businessGroupList = null;
				try {

					businessGroupList = masterDelegate.getBusinessGroupList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupList", businessGroupList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			@RequestMapping(value = "/addBusinessGroup", method = RequestMethod.GET)
			public ModelAndView newBusinessGroup(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupBean")BusinessGroup businessGroup){
				ModelAndView model1 = new ModelAndView("newBusinessGroup");

				if(request.getSession().getAttribute("UserId")!= null)
				{

				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				return model1;
				
			}
			@RequestMapping(value = "/submitBgGroup", method = RequestMethod.POST)
			public ModelAndView submitBusinessGroup(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupBean")BusinessGroup businessGroup) {
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{

				System.out.println("businnnnn"+businessGroup.getBusinessGroup());
				if(businessGroup.getIsActive()==null)
					businessGroup.setIsActive("N");
				boolean insert;
				if(businessGroup.getId()==0)
					try {
						insert = masterDelegate.addBusinessGroup(businessGroup);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
						insert = masterDelegate.updateBusinessGroup(businessGroup);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				
				
			     model1 = getBusinessGroupList(request,response,businessGroup);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				 return model1;
				
			}
			@RequestMapping(value = "/deletebusinessGroup/{id}", method = RequestMethod.GET)
			public ModelAndView deleteBusinessGroupList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("BusinessGroupBean")BusinessGroup businessGroup){

				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{

				try {

					boolean insert = masterDelegate.deleteBusinessGroup(id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getBusinessGroupList( request,  response, businessGroup);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				return model1;
				
			}
			@RequestMapping(value = "/editBusinessGroup/{id}", method = RequestMethod.GET)
			public ModelAndView editBusinessGroup(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,BusinessGroup businessGroup) throws SQLException {
				ModelAndView model =null;
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{

				businessGroup = masterDelegate.editBusinessGroup(id);
				System.out.println("hhhhh"+businessGroup.getId());
				
				
				//functionList.add(function);
				request.setAttribute("BusinessGroupBean",businessGroup);
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				model = new ModelAndView("newBusinessGroup");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}
				return model;
			}

			
			
			
			
			
			//***********************End*****************************
			
			
			
			//*********************Business Group Location********
			@RequestMapping(value = "/listBusinessGroupLoc", method = RequestMethod.GET)
			public ModelAndView getBusinessGroupLoc(HttpServletRequest request, HttpServletResponse response,BusinessGroupLoc businessGroupLoc) {
				ModelAndView model = new ModelAndView("listBusinessGroupLoc");

				if(request.getSession().getAttribute("UserId")!= null)
				{

				List businessGroupLocList = null;
				try {

					businessGroupLocList = masterDelegate.getBusinessGroupLoc();
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupLocation", businessGroupLocList);
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			@RequestMapping(value = "/addBusinessGroupLoc", method = RequestMethod.GET)
			public ModelAndView newBusinessGroupLoc(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupLocBeanByID")BusinessGroupLoc businessGroupLoc){
				ModelAndView model1 = new ModelAndView("newBusinessGroupLoc");
				if(request.getSession().getAttribute("UserId")!= null)
				{

				List businessGroup=null;
				List locationList=null;
				try {
					businessGroup = masterDelegate.getBusinessGroupList();
					locationList=masterDelegate.getLocation();
					System.out.println("hiiiiiiii"+locationList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("BusinessGroupBean",businessGroup);
				request.setAttribute("LocationList",locationList);
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/submitBgGroupLoc", method = RequestMethod.POST)
			public ModelAndView submitBusinessGroupLoc(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupLocBean")BusinessGroupLoc businessGroupLoc) {
				ModelAndView model1=null;
				
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				if(businessGroupLoc.getId()==0)
				
					try {
						insert = masterDelegate.addBusinessGroupLoc(businessGroupLoc);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
				else
					
					try {
						insert = masterDelegate.updateBusinessGroupLoc(businessGroupLoc);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				
			     model1 = getBusinessGroupLoc(request,response,businessGroupLoc);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			
			
			@RequestMapping(value = "/deletebusinessGroupLoc/{bgId}/{locId}", method = RequestMethod.GET)
			public ModelAndView deleteBusinessGroupLocList(HttpServletRequest request, HttpServletResponse response,@PathVariable int bgId,@PathVariable String locId,@ModelAttribute("BusinessGroupLocBean")BusinessGroupLoc businessGroupLoc){

				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{

				try {
					System.out.println("comming"+bgId +"lg id"+locId);

					boolean insert = masterDelegate.deleteBusinessGroupLoc(bgId,locId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getBusinessGroupLoc( request,  response, businessGroupLoc);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			@RequestMapping(value = "/editBusinessGR/{bgId}/{locId}", method = RequestMethod.GET)
			public ModelAndView editBusinessGrLoc(HttpServletRequest request, HttpServletResponse response, @PathVariable int bgId,@PathVariable String locId) throws SQLException {
				ModelAndView model = new ModelAndView("newBusinessGroupLoc");
				//List<Business> functionList=new ArrayList<Business>();

				if(request.getSession().getAttribute("UserId")!= null)
				{

				BusinessGroupLoc businessGroupLoc = masterDelegate.editBusinessGroupLoc(bgId,locId);
				businessGroupLoc.setId(masterDelegate.getBusinessGroupId(bgId,locId));
				
				 System.out.println("tesssssst"+businessGroupLoc.getId());
				
				//functionList.add(function);
				request.setAttribute("BusinessGroupLocBeanByID",businessGroupLoc);
				
				List businessGroup=null;
				List locationList=null;
				try {
					businessGroup = masterDelegate.getBusinessGroupList();
					locationList=masterDelegate.getLocation();
					System.out.println("hiiiiiiii"+locationList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("BusinessGroupBean",businessGroup);
				request.setAttribute("LocationList",locationList);
				
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			
			
			
			
			//******************End***********************
			
			
			//***************************Location Business********************
			@RequestMapping(value = "/listBusinessLoc", method = RequestMethod.GET)
			public ModelAndView getBusinessLoc(HttpServletRequest request, HttpServletResponse response,LocationBusiness businessGroupLoc) {
				ModelAndView model = new ModelAndView("listLocationBusiness");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List businessGroupLocList = null;
				try {

					businessGroupLocList = masterDelegate.getBusinessLoc();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessLocation", businessGroupLocList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			@RequestMapping(value = "/addLocBusiness", method = RequestMethod.GET)
			public ModelAndView newBusinessLoc(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessLocBean")LocationBusiness businessGroupLoc){
				ModelAndView model1 = new ModelAndView("newLocationBusiness");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List businessGroup=null;
				List locationList=null;
				
				try {
					businessGroup = masterDelegate.getBusiness();
					locationList=masterDelegate.getLocation();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("ttttttttt"+businessGroup);
				
				request.setAttribute("BusinessGroupBean",businessGroup);
				request.setAttribute("LocationList",locationList);
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/submitBgLoc", method = RequestMethod.POST)
			public ModelAndView submitBusinessLoc(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessLocBean")LocationBusiness businessGroupLoc) {
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				System.out.println("testing"+businessGroupLoc.getBusinessId());
				System.out.println("hiiiiiiii"+businessGroupLoc);
				boolean insert;
				
				if(businessGroupLoc.getId()==0)
				
					try {
						insert = masterDelegate.addBusinessLoc(businessGroupLoc);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
					insert = masterDelegate.updateBusinessLoc(businessGroupLoc);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				
				
			     model1 = getBusinessLoc(request,response,businessGroupLoc);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			
			
			
			@RequestMapping(value = "/deleteLocBussiness/{bgId}/{locId}", method = RequestMethod.GET)
			public ModelAndView deleteBusinessLocList(HttpServletRequest request, HttpServletResponse response,@PathVariable String bgId,@PathVariable int locId,@ModelAttribute("BusinessLocBean")LocationBusiness businessGroupLoc){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				try {
					System.out.println("comming"+bgId +"lg id"+locId);

					boolean insert = masterDelegate.deleteBusinessLoc(bgId, locId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getBusinessLoc( request,  response, businessGroupLoc);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/editBusinessLoc/{bgId}/{locId}", method = RequestMethod.GET)
			public ModelAndView editBusinessLoc(HttpServletRequest request, HttpServletResponse response, @PathVariable String bgId,@PathVariable int locId) throws SQLException {
				ModelAndView model = new ModelAndView("newLocationBusiness");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
				LocationBusiness businessGroupLoc=new LocationBusiness();
				businessGroupLoc = masterDelegate.editBusinessLoc(bgId,locId);
				businessGroupLoc.setId(masterDelegate.getLocationBusinessId(bgId,locId));
				
				 System.out.println("tesssssst"+businessGroupLoc);
				
				//functionList.add(function);
				request.setAttribute("BusinessLocBean",businessGroupLoc);
				
				List businessGroup=null;
				List locationList=null;
				try {
					businessGroup = masterDelegate.getBusiness();
					locationList=masterDelegate.getLocation();
					System.out.println("hiiiiiiii"+locationList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("BusinessGroupBean",businessGroup);
				request.setAttribute("LocationList",locationList);
				
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}


				return model;
			}
			
			
			
			
			
			
			//**************************End*************************
			
//********************Stages*******************
			
			@RequestMapping(value = "/listStages", method = RequestMethod.GET)
			public ModelAndView getStages(HttpServletRequest request, HttpServletResponse response,Stages stage) {
				ModelAndView model = new ModelAndView("listStages");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List stageList = null;
				try {

					stageList = masterDelegate.getStageList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("StageList", stageList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			
			
			
			@RequestMapping(value = "/deleteStage/{stageId}", method = RequestMethod.GET)
			public ModelAndView deleteStageList(HttpServletRequest request, HttpServletResponse response,@PathVariable int stageId,Stages stage){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {
					System.out.println("comming"+stageId);

					boolean insert = masterDelegate.deleteStages(stageId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getStages( request,  response, stage);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/editStage/{stageId}", method = RequestMethod.GET)
			public ModelAndView editStages(HttpServletRequest request, HttpServletResponse response,@PathVariable int stageId) throws SQLException {
				ModelAndView model = new ModelAndView("newStages");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				//List<Business> functionList=new ArrayList<Business>();
				Stages stage=new Stages();
				stage = masterDelegate.editStages(stageId);
				
				
				
				
				//functionList.add(function);
				request.setAttribute("StagesBean",stage);
				
				
				
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}

			@RequestMapping(value = "/addStage", method = RequestMethod.GET)
			public ModelAndView addStages(HttpServletRequest request, HttpServletResponse response) throws SQLException {
				ModelAndView model = new ModelAndView("newStages");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				//List<Business> functionList=new ArrayList<Business>();
				Stages stage=new Stages();
				//functionList.add(function);
				request.setAttribute("StagesBean",stage);
				
				
				
				
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			@RequestMapping(value = "/submitStage", method = RequestMethod.POST)
			public ModelAndView submitStages(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("StageBean")Stages stage) {
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				System.out.println("hiiiiiiii"+stage.getId());
				System.out.println("stage name is "+stage.getStageName());
				
				boolean insert;
				
				
				
					try {
					insert = masterDelegate.updateStages(stage);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				
				
			     model1 = getStages(request,response,stage);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			
			
			
			
			//*****************End******************
			
			
			
//********************Steps*******************
			
			@RequestMapping(value = "/listSteps", method = RequestMethod.GET)
			public ModelAndView getSteps(HttpServletRequest request, HttpServletResponse response,Steps steps) {
				ModelAndView model = new ModelAndView("listSteps");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List stageList = null;
				try {

					stageList = masterDelegate.getStepList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("StepList", stageList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			
			
			
			@RequestMapping(value = "/deleteSteps/{stepId}", method = RequestMethod.GET)
			public ModelAndView deleteSteps(HttpServletRequest request, HttpServletResponse response,@PathVariable int stepId,Steps steps){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {
					System.out.println("comming"+stepId);

					boolean insert = masterDelegate.deleteSteps(stepId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getSteps( request,  response, steps);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/editSteps/{stepId}", method = RequestMethod.GET)
			public ModelAndView editSteps(HttpServletRequest request, HttpServletResponse response,@PathVariable int stepId) throws SQLException {
				ModelAndView model = new ModelAndView("newSteps");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				//List<Business> functionList=new ArrayList<Business>();
				Steps steps=new Steps();
				steps = masterDelegate.editSteps(stepId);
				
				
				
				
				//functionList.add(function);
				request.setAttribute("StepsBean",steps);
				
				
				
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}
				return model;
			}
			
			
			@RequestMapping(value = "/submitSteps", method = RequestMethod.POST)
			public ModelAndView submitSteps(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("StageBean")Steps steps) {
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				System.out.println("hiiiiiiii"+steps.getStepId());
				System.out.println("stage name is "+steps.getStepName());
				
				boolean insert;
				
				
				
					try {
					insert = masterDelegate.updateSteps(steps);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				
				
			     model1 = getSteps(request,response,steps);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				 return model1;
				
			}
			
			
			
			
			//*****************End******************
			
			//*****************Busin4ess Roles***************
			
			
			
			@RequestMapping(value = "/listBusiRole", method = RequestMethod.GET)
			public ModelAndView getBusinessRole(HttpServletRequest request, HttpServletResponse response,BusinessRoles busiRole) {
				ModelAndView model = new ModelAndView("listBusiRole");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List stageList = null;
				try {

					stageList = masterDelegate.getBusinessRolesList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusiRoleList", stageList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}
				return model;
			}
			
			
			
			
			
			@RequestMapping(value = "/deleteBusiRole/{busiRoleId}", method = RequestMethod.GET)
			public ModelAndView deleteBusinessRole(HttpServletRequest request, HttpServletResponse response,@PathVariable int busiRoleId,BusinessRoles busiRole){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {
					System.out.println("comming"+busiRole);

					boolean insert = masterDelegate.deleteBusiRole(busiRoleId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getBusinessRole( request,  response, busiRole);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/editBusiRole/{busiRoleId}", method = RequestMethod.GET)
			public ModelAndView editBusinessRole(HttpServletRequest request, HttpServletResponse response,@PathVariable int busiRoleId) throws SQLException {
				ModelAndView model = new ModelAndView("newBusiRole");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
				BusinessRoles busiRole=new BusinessRoles();
				busiRole = masterDelegate.editBusiRole(busiRoleId);
				
				
				
				
				//functionList.add(function);
				request.setAttribute("BusiRoleBean",busiRole);
				
				
				
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}
				return model;
			}
			
			@RequestMapping(value = "/addBusiRole", method = RequestMethod.GET)
			public ModelAndView addBusinessRole(HttpServletRequest request, HttpServletResponse response) throws SQLException {
				ModelAndView model = new ModelAndView("newBusiRole");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
				BusinessRoles busiRole=new BusinessRoles();		
				
				//functionList.add(function);
				request.setAttribute("BusiRoleBean",busiRole);
				
				
				
				
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );

}else{
					
					model=new ModelAndView("index");
				}
				return model;
			}
			
			@RequestMapping(value = "/submitBusiRole", method = RequestMethod.POST)
			public ModelAndView submitBusinessRole(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusiRoleBean")BusinessRoles busiRole) {
				ModelAndView model1=null;
				
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				
				
				
					try {
					insert = masterDelegate.updateBusiRole(busiRole);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				
				
			     model1 = getBusinessRole(request,response,busiRole);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}
				 return model1;
				
			}
			
			
			
			
			//*****************End******************
			
			
			@RequestMapping(value = "/listMenu", method = RequestMethod.GET)
			public ModelAndView getMenu(HttpServletRequest request, HttpServletResponse response) {
				ModelAndView model = new ModelAndView("listMenu");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List menuList = null;
				try {

					menuList = masterDelegate.getMenuTable();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("MenuList", menuList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			@RequestMapping(value = "/addMenu", method = RequestMethod.GET)
			public ModelAndView newMenu(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("MenuBean")Menutable menuBean){
				ModelAndView model = new ModelAndView("newMenu");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
				
			}
			@RequestMapping(value = "/submitMenu", method = RequestMethod.POST)
			public ModelAndView submitBusiness(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("MenuBean")Menutable menuBean) throws SQLException, JSONException{
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				
					 insert = masterDelegate.updateMenuTable(menuBean);
				
				
				
			     model1 = getMenu(request,response);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			@RequestMapping(value = "/deleteMenu/{id}", method = RequestMethod.GET)
			public ModelAndView deleteMenuList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("MenuBean")Menutable menuBean){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {

					boolean insert = masterDelegate.deleteMenuTable(id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getMenu( request,  response);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			@RequestMapping(value = "/editMenu/{id}", method = RequestMethod.GET)
			public ModelAndView editMenu(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,Menutable menuBean) throws SQLException {
				ModelAndView model = new ModelAndView("newMenu");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
				 menuBean = masterDelegate.editMenuTable(id);
				
				 
				
				//functionList.add(function);
				request.setAttribute("MenuBean",menuBean);
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				request.setAttribute("helpManual","Master_Manual.pdf" );
				
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}

	
			
			
			
			
			@RequestMapping(value = "/listPersonnelArea", method = RequestMethod.GET)
			public ModelAndView getPersonnelArea(HttpServletRequest request, HttpServletResponse response) {
				ModelAndView model = new ModelAndView("listPersonnelArea");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List personnelAreaList = null;
				try {

					personnelAreaList = masterDelegate.getPersonnelArea();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("PersonnelAreaList", personnelAreaList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			@RequestMapping(value = "/addPersonnelArea", method = RequestMethod.GET)
			public ModelAndView newPersonnelArea(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("PersonnelAreaBean")PersonnelArea personnelAreaBean){
				ModelAndView model = new ModelAndView("newPersonnelArea");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				List businessGroupList = null;
				try {

					businessGroupList = masterDelegate.getBusinessGroupList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupList", businessGroupList);
				List businessList = null;
				try {

					businessList = masterDelegate.getBusiness();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessList", businessList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
				
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
				
			}
			@RequestMapping(value = "/submitPersonnelArea", method = RequestMethod.POST)
			public ModelAndView submitPersonnelArea(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("PersonnelAreaBean")PersonnelArea personnelAreaBean) throws SQLException, JSONException{
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				
					 insert = masterDelegate.updatePersonnelArea(personnelAreaBean);
				
				
				
			     model1 = getPersonnelArea(request,response);
		}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			@RequestMapping(value = "/deletePersonnelArea/{id}", method = RequestMethod.GET)
			public ModelAndView deletePersonnelAreaList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("PersonnelAreaBean")PersonnelArea personnelAreaBean){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {

					boolean insert = masterDelegate.deletePersonnelArea(id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getPersonnelArea( request,  response);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			@RequestMapping(value = "/editPersonnelArea/{id}", method = RequestMethod.GET)
			public ModelAndView editPersonnelArea(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,PersonnelArea personnelAreaBean) throws SQLException {
				ModelAndView model = new ModelAndView("newPersonnelArea");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
					personnelAreaBean = masterDelegate.editPersonnelArea(id);
				
				 
				
				//functionList.add(function);
				request.setAttribute("PersonnelAreaBean",personnelAreaBean);
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				List businessGroupList = null;
				try {

					businessGroupList = masterDelegate.getBusinessGroupList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupList", businessGroupList);
				List businessList = null;
				try {

					businessList = masterDelegate.getBusiness();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessList", businessList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
				
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}

			
			
			
			
			
			
			
			
			@RequestMapping(value = "/listPayrollArea", method = RequestMethod.GET)
			public ModelAndView getPayrollArea(HttpServletRequest request, HttpServletResponse response) {
				ModelAndView model = new ModelAndView("listPayrollArea");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				List payrollAreaList = null;
				try {

					payrollAreaList = masterDelegate.getPayrollArea();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("PayrollAreaList", payrollAreaList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			@RequestMapping(value = "/addPayrollArea", method = RequestMethod.GET)
			public ModelAndView newPayrollArea(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("PayrollAreaBean")PayrollArea payrollAreaBean){
				ModelAndView model = new ModelAndView("newPayrollArea");
				if(request.getSession().getAttribute("UserId")!= null)
				{
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				List businessGroupList = null;
				try {

					businessGroupList = masterDelegate.getBusinessGroupList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupList", businessGroupList);
				 List businessList = null;
					try {

						businessList = masterDelegate.getBusiness();

					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("BusinessList", businessList);
					List personnelAreaList = null;
					try {

						personnelAreaList = masterDelegate.getPersonnelArea();

					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("PersonnelAreaList", personnelAreaList);
					request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
				
			}
			@RequestMapping(value = "/submitPayrollArea", method = RequestMethod.POST)
			public ModelAndView submitPayrollArea(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("PayrollAreaBean")PayrollArea payrollAreaBean) throws SQLException, JSONException{
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				
					 insert = masterDelegate.updatePayrollArea(payrollAreaBean);
				
					
				
			     model1 = getPayrollArea(request,response);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			@RequestMapping(value = "/deletePayrollArea/{id}", method = RequestMethod.GET)
			public ModelAndView deletePayrollAreaList(HttpServletRequest request, HttpServletResponse response,@PathVariable int id,@ModelAttribute("PayrollAreaBean")PayrollArea payrollAreaBean){
				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{
				try {

					boolean insert = masterDelegate.deletePayrollArea(id);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getPayrollArea( request,  response);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			@RequestMapping(value = "/editPayrollArea/{id}", method = RequestMethod.GET)
			public ModelAndView editPayrollArea(HttpServletRequest request, HttpServletResponse response, @PathVariable int id,PayrollArea payrollAreaBean) throws SQLException {
				ModelAndView model = new ModelAndView("newPayrollArea");
				//List<Business> functionList=new ArrayList<Business>();
				if(request.getSession().getAttribute("UserId")!= null)
				{
					payrollAreaBean = masterDelegate.editPayrollArea(id);
				
				 
				
				//functionList.add(function);
				request.setAttribute("PayrollAreaBean",payrollAreaBean);
				
				request.setAttribute("head", "Edit");
				request.setAttribute("btn", "Update");
				List businessGroupList = null;
				try {

					businessGroupList = masterDelegate.getBusinessGroupList();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupList", businessGroupList);
				List businessList = null;
				try {

					businessList = masterDelegate.getBusiness();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessList", businessList);
				List personnelAreaList = null;
				try {

					personnelAreaList = masterDelegate.getPersonnelArea();

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("PersonnelAreaList", personnelAreaList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
		}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}

			
			
			
			
			
			
			
			
			
			
			//*********************Business Group Business********
			@RequestMapping(value = "/listBusinessGroupBusiness", method = RequestMethod.GET)
			public ModelAndView getBusinessGroupBusiness(HttpServletRequest request, HttpServletResponse response,BusinessGroupLoc businessGroupLoc) {
				ModelAndView model = new ModelAndView("listBusinessGroupBusiness");

				if(request.getSession().getAttribute("UserId")!= null)
				{

				List businessGroupLocList = null;
				try {

					businessGroupLocList = masterDelegate.getBusinessGroupBusiness();
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("BusinessGroupBusiness", businessGroupLocList);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model=new ModelAndView("index");
				}

				return model;
			}
			
			
			@RequestMapping(value = "/addBusinessGroupBusiness", method = RequestMethod.GET)
			public ModelAndView newBusinessGroupBusiness(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupLocBeanByID")BusinessGroupLoc businessGroupLoc){
				ModelAndView model1 = new ModelAndView("newBusinessGroupBusiness");
				if(request.getSession().getAttribute("UserId")!= null)
				{

				List businessGroup=null;
				List locationList=null;
				try {
					businessGroup = masterDelegate.getBusinessGroupList();
					locationList=masterDelegate.getBusiness();
					System.out.println("hiiiiiiii"+locationList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				request.setAttribute("BusinessGroupBean",businessGroup);
				request.setAttribute("BusinessList",locationList);
				request.setAttribute("head", "New");
				request.setAttribute("btn", "Add");
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
			
			@RequestMapping(value = "/submitBgGroupBusiness", method = RequestMethod.POST)
			public ModelAndView submitBusinessGroupBusiness(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("BusinessGroupLocBean")BusinessGroupLoc businessGroupLoc) {
				ModelAndView model1=null;
				
				if(request.getSession().getAttribute("UserId")!= null)
				{
				
				boolean insert;
				
				
					try {
						insert = masterDelegate.addBusinessGroupBusiness(businessGroupLoc);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
				
					
				
			     model1 = getBusinessGroupBusiness(request,response,businessGroupLoc);
			 	request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				 return model1;
				
			}
			
			
			@RequestMapping(value = "/deletebusinessGroupBusiness/{bgId}/{locId}", method = RequestMethod.GET)
			public ModelAndView deleteBusinessGroupBusinessList(HttpServletRequest request, HttpServletResponse response,@PathVariable int bgId,@PathVariable String locId,@ModelAttribute("BusinessGroupLocBean")BusinessGroupLoc businessGroupLoc){

				ModelAndView model1=null;
				if(request.getSession().getAttribute("UserId")!= null)
				{

				try {
					System.out.println("comming"+bgId +"lg id"+locId);

					boolean insert = masterDelegate.deleteBusinessGroupBusiness(bgId,locId);

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model1=getBusinessGroupBusiness( request,  response, businessGroupLoc);
				request.setAttribute("helpManual","Master_Manual.pdf" );
}else{
					
					model1=new ModelAndView("index");
				}

				return model1;
				
			}
			
			
		
			
			
			
			//******************End***********************
			
	//************************End******************************
	
	
}
