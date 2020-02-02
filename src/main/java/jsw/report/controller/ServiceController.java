package jsw.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import jsw.report.delegate.ARDelegate;
import jsw.report.delegate.ApDelegate;
import jsw.report.delegate.CORPDelegate;
import jsw.report.delegate.CommDelegate;
import jsw.report.delegate.HRDelegate;
import jsw.report.delegate.RTRDelegate;
import jsw.report.delegate.TaxDelegate;
import jsw.report.delegate.VwDelegate;



@Controller
@RequestMapping(value="/NW")
public class ServiceController {
	@Autowired
	private VwDelegate vwDelegate;
	@Autowired
	private ApDelegate apDelegate;
	@Autowired
	private ARDelegate aRDelegate;
	@Autowired
	private CommDelegate commDelegate;
	@Autowired
	private CORPDelegate cORPDelegate;
	@Autowired
	private HRDelegate hRDelegate;
	@Autowired
	private RTRDelegate rTRDelegate;
	@Autowired
	private TaxDelegate tAXDelegate;
	
	

	public VwDelegate getVwDelegate() {
		return vwDelegate;
	}

	public void setVwDelegate(VwDelegate vwDelegate) {
		this.vwDelegate = vwDelegate;
	}

	
	

	public ApDelegate getApDelegate() {
		return apDelegate;
	}

	public void setApDelegate(ApDelegate apDelegate) {
		this.apDelegate = apDelegate;
	}

	public ARDelegate getaRDelegate() {
		return aRDelegate;
	}

	public void setaRDelegate(ARDelegate aRDelegate) {
		this.aRDelegate = aRDelegate;
	}

	public CommDelegate getCommDelegate() {
		return commDelegate;
	}

	public void setCommDelegate(CommDelegate commDelegate) {
		this.commDelegate = commDelegate;
	}

	public CORPDelegate getcORPDelegate() {
		return cORPDelegate;
	}

	public void setcORPDelegate(CORPDelegate cORPDelegate) {
		this.cORPDelegate = cORPDelegate;
	}

	public HRDelegate gethRDelegate() {
		return hRDelegate;
	}

	public void sethRDelegate(HRDelegate hRDelegate) {
		this.hRDelegate = hRDelegate;
	}

	public RTRDelegate getrTRDelegate() {
		return rTRDelegate;
	}

	public void setrTRDelegate(RTRDelegate rTRDelegate) {
		this.rTRDelegate = rTRDelegate;
	}

	public TaxDelegate gettAXDelegate() {
		return tAXDelegate;
	}

	public void settAXDelegate(TaxDelegate tAXDelegate) {
		this.tAXDelegate = tAXDelegate;
	}

	//AP WebServices
		 @RequestMapping(value="/getCaseCreatedCompanyAP/{jsonData}",method=RequestMethod.GET)			
			public ModelAndView getCaseCreatedCompanyAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getNumberOfCasesCreatedAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelAP/"+jsonData);
			 
			 System.out.println("Table String Completed  ");
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			 
			}
		 
		
				 @RequestMapping(value="/getCaseCreatedCompanyExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 apDelegate.getNumberOfCasesCreatedExcelAP(finalJsonString);
					  return getExcelEntity("AP_Report1");
			 
			}
		 
		 
		 @RequestMapping(value="/getCaseCreatedCompanyCompletedAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getCaseCreatedCompanyCompletedAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getCaseCreatedCompanyCompletedAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getCaseCreatedCompanyCompletedExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report2");
	 
	}

		 
		 
		 
		 
		 
		 
		 @RequestMapping(value="/getCasesProcessedAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getCasesProcessedAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getCasesProcessedAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			
			}
		 
		 
		 @RequestMapping(value="/getCasesProcessedExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getCasesProcessedExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getCasesProcessedExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report3");
	 
	}

		 
		 
		 @RequestMapping(value="/getRoleWiseTimeTakenAP/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getRoleWiseTimeTakenAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getRoleWiseTimeTakenAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			
		

			}
		 
		 
		 @RequestMapping(value="/getRoleWiseTimeTakenExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getRoleWiseTimeTakenExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report4");
	 
	}

		 
		 
		 @RequestMapping(value="/getTotalCycleTimeAP/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getTotalCycleTimeAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getTotalCycleTimeAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;

			}
		 
		 
		 
		 @RequestMapping(value="/getTotalCycleTimeExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getTotalCycleTimeExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report5");
	 
	}
			
		    @RequestMapping(value="/getCasesPendingAP/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getCasesPendingAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{	
		    	
		    	 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", apDelegate.getCasesPendingAP(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelAP/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;


			}
		 
		 
		    
		    @RequestMapping(value="/getCasesPendingExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesPendingExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 apDelegate.getCasesPendingExcelAP(finalJsonString);
				  return getExcelEntity("AP_Report6");
		 
		}
		    
		    
		    
		  @RequestMapping(value="/getCasesCompletedCumulativelyAP/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getCasesCompletedCumulativelyAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{	
			  
			  System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", apDelegate.getCasesCompletedCumulativelyAP(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesCompletedCumulativelyExcelAP/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
		return model;
			}
		 
		  
		  
		  
		  @RequestMapping(value="/getCasesCompletedCumulativelyExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesCompletedCumulativelyExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 apDelegate.getCasesCompletedCumulativelyExcelAP(finalJsonString);
				  return getExcelEntity("AP_Report7");
		 
		}
		  
		  
		  
		  
		  
		  
		 
		 @RequestMapping(value="/getCasesPendingForProcessingAP/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getCasesPendingForProcessingAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getCasesPendingForProcesssingAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingForProcessingExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
			return model;

			}
		 
		 
		 
		 
		 
		 @RequestMapping(value="/getCasesPendingForProcessingExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getCasesPendingForProcessingExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getCasesPendingForProcesssingExcelAP(finalJsonString);
			
	   return getExcelEntity("AP_Report8");
	}
	  
	  
	 
		 
		 
		 @RequestMapping(value="/getExceptionAndFirstPassAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getExceptionAndFirstPassAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getExceptionAndFirstPassAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getExceptionAndFirstPassExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getExceptionAndFirstPassExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getExceptionAndFirstPassExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getExceptionAndFirstPassExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report9");
	 
	}
		 
		 
		 
		 
		 @RequestMapping(value="/getUrgentInvoiceRecievedAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getUrgentInvoiceRecievedAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getUrgentInvoiceRecievedAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getUrgentInvoiceRecievedExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getUrgentInvoiceRecievedExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getUrgentInvoiceRecievedExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getUrgentInvoiceRecievedExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report10");
	 
	}
		 
		 
		 
		 
		 
		 @RequestMapping(value="/getAverageTimeTakenFromReceiptToPaymentAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getAverageTimeTakenFromReceiptToPaymentAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getAverageTimeTakenFromReceiptToPaymentAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getAverageTimeTakenFromReceiptToPaymentExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getAverageTimeTakenFromReceiptToPaymentExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getAverageTimeTakenFromReceiptToPaymentExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getAverageTimeTakenFromReceiptToPaymentExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report11");
	 
	}
		 
		 
		 
		 @RequestMapping(value="/getTATforScanningAndindexingAP/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getTATforScanningAndindexingAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", apDelegate.getTATforScanningAndindexingAP(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getTATforScanningAndindexingExcelAP/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getTATforScanningAndindexingExcelAP/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getTATforScanningAndindexingExcelAP(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 apDelegate.getTATforScanningAndindexingExcelAP(finalJsonString);
			  return getExcelEntity("AP_Report12");
	 
	}
		 
		 
		 
		 
		 
	   
		  //HR WebServices
		 @RequestMapping(value="/getCaseCreatedCompanyHR/{jsonData}",method=RequestMethod.GET)			
			public ModelAndView getCaseCreatedCompanyHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", hRDelegate.getNumberOfCasesCreatedHR(finalJsonString).toString());
			 System.out.println("Table String Completed  ");
			 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelHR/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			 
			}
		 
		
				 @RequestMapping(value="/getCaseCreatedCompanyExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 hRDelegate.getNumberOfCasesCreatedExcelHR(finalJsonString);
					  return getExcelEntity("HRM_Report1");
			 
			}
		 
		 
		 @RequestMapping(value="/getCaseCreatedCompanyCompletedHR/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getCaseCreatedCompanyCompletedHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", hRDelegate.getCaseCreatedCompanyCompletedHR(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelHR/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
		
			}
		 
		 
		 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 hRDelegate.getCaseCreatedCompanyCompletedExcelHR(finalJsonString);
			  return getExcelEntity("HRM_Report2");

	}

		 
		 
		 
		 
		 
		 
		 @RequestMapping(value="/getCasesProcessedHR/{jsonData}",method=RequestMethod.GET)		
			public ModelAndView getCasesProcessedHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", hRDelegate.getCasesProcessedHR(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelHR/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			
			}
		 
		 
		 @RequestMapping(value="/getCasesProcessedExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getCasesProcessedExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 hRDelegate.getCasesProcessedExcelHR(finalJsonString);
			  return getExcelEntity("HRM_Report3");

	}

		 
		 
		 @RequestMapping(value="/getRoleWiseTimeTakenHR/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getRoleWiseTimeTakenHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", hRDelegate.getRoleWiseTimeTakenHR(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelHR/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;
			
		

			}
		 
		 
		 @RequestMapping(value="/getRoleWiseTimeTakenExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 hRDelegate.getRoleWiseTimeTakenExcelHR(finalJsonString);
			  return getExcelEntity("HRM_Report4");

	}

		 
		 
		 @RequestMapping(value="/getTotalCycleTimeHR/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getTotalCycleTimeHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{
			 
			 System.out.println("encoded json  "+jsonData);
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 System.out.println("decoded json  "+finalJsonString);
			 request.setAttribute("detailTable", hRDelegate.getTotalCycleTimeHR(finalJsonString).toString());
			 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelHR/"+jsonData);
				
				ModelAndView model = new ModelAndView("caseDetailPage");
				
				return model;

			}
		 
		 
		 
		 @RequestMapping(value="/getTotalCycleTimeExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
		 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
			) throws SQLException, JSONException, IOException
	{
			 
			 
			 byte[] bytes = Base64.decodeBase64(jsonData); 
			 String finalJsonString = new String(bytes);
			 hRDelegate.getTotalCycleTimeExcelHR(finalJsonString);
			  return getExcelEntity("HRM_Report5");

	}
			
		    @RequestMapping(value="/getCasesPendingHR/{jsonData}",method=RequestMethod.GET)
			public ModelAndView getCasesPendingHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
			{	
		    	
		    	 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", hRDelegate.getCasesPendingHR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelHR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;


			}
		 
		 
		    
		    @RequestMapping(value="/getCasesPendingExcelHR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesPendingExcelHR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 hRDelegate.getCasesPendingExcelHR(finalJsonString);
				  return getExcelEntity("HRM_Report6");
		 
		}
		    
			
	 

			//AR WebServices
			 @RequestMapping(value="/getCaseCreatedCompanyAR/{jsonData}",method=RequestMethod.GET)			
				public ModelAndView getCaseCreatedCompanyAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getNumberOfCasesCreatedAR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelAP/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				 
				}
			 
			
					 @RequestMapping(value="/getCaseCreatedCompanyExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 aRDelegate.getNumberOfCasesCreatedExcelAR(finalJsonString);
						  return getExcelEntity("AR_Report1");
				 
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedAR/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCaseCreatedCompanyCompletedAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getCaseCreatedCompanyCompletedAR(finalJsonString).toString());

				 
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelAR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
			
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 aRDelegate.getCaseCreatedCompanyCompletedExcelAR(finalJsonString);
				  return getExcelEntity("AR_Report2");
		 
		}

			 
			 
			 
			 
			 
			 
			 @RequestMapping(value="/getCasesProcessedAR/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCasesProcessedAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getCasesProcessedAR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelAR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
				}
			 
			 
			 @RequestMapping(value="/getCasesProcessedExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesProcessedExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 aRDelegate.getCasesProcessedExcelAR(finalJsonString);
				  return getExcelEntity("AR_Report3");
		 
		}

			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenAR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getRoleWiseTimeTakenAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getRoleWiseTimeTakenAR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelAR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
			

				}
			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 aRDelegate.getRoleWiseTimeTakenExcelAR(finalJsonString);
				  return getExcelEntity("AR_Report4");
		 
		}

			 
			 
			 @RequestMapping(value="/getTotalCycleTimeAR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getTotalCycleTimeAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getTotalCycleTimeAR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelAR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;

				}
			 
			 
			 
			 @RequestMapping(value="/getTotalCycleTimeExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 aRDelegate.getTotalCycleTimeExcelAR(finalJsonString);
				  return getExcelEntity("AR_Report5");
		 
		}
				
			    @RequestMapping(value="/getCasesPendingAR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
			    	
			    	 System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", aRDelegate.getCasesPendingAR(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelAR/"+jsonData);
						
						ModelAndView model = new ModelAndView("caseDetailPage");
						
						return model;


				}
			 
			 
			    
			    @RequestMapping(value="/getCasesPendingExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesPendingExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 aRDelegate.getCasesPendingExcelAR(finalJsonString);
					  return getExcelEntity("AR_Report6");
			 
			}
			    
			    
			    
			  @RequestMapping(value="/getCasesCompletedCumulativelyAR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesCompletedCumulativelyAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
				  
				  System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", aRDelegate.getCasesCompletedCumulativelyAR(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesCompletedCumulativelyExcelAR/"+jsonData);
						
						ModelAndView model = new ModelAndView("caseDetailPage");
			return model;
				}
			 
			  
			  
			  
			  @RequestMapping(value="/getCasesCompletedCumulativelyExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesCompletedCumulativelyExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 aRDelegate.getCasesCompletedCumulativelyExcelAR(finalJsonString);
					  return getExcelEntity("AR_Report7");
			 
			}
			  
			  
			  
			  
			  
			  
			 
			 @RequestMapping(value="/getCasesPendingForProcessingAR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingForProcessingAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", aRDelegate.getCasesPendingForProcesssingAR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingForProcessingExcelAR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
				return model;

				}
			 
			 
			 
			 
			 
			 @RequestMapping(value="/getCasesPendingForProcessingExcelAR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesPendingForProcessingExcelAR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 aRDelegate.getCasesPendingForProcesssingExcelAR(finalJsonString);
				
		   return getExcelEntity("AR_Report8");
		}
		  
		 
	 
			 
			 //COMM WebServices
			 @RequestMapping(value="/getCaseCreatedCompanyCOMM/{jsonData}",method=RequestMethod.GET)			
				public ModelAndView getCaseCreatedCompanyCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getNumberOfCasesCreatedCOMM(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelCOMM/"+jsonData);
				 
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				 
				}
			 
			
					 @RequestMapping(value="/getCaseCreatedCompanyExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 commDelegate.getNumberOfCasesCreatedExcelCOMM(finalJsonString);
						  return getExcelEntity("Commercial_Report1");
				 
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedCOMM/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCaseCreatedCompanyCompletedCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getCaseCreatedCompanyCompletedCOMM(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelCOMM/"+jsonData);
				 
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
			
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 commDelegate.getCaseCreatedCompanyCompletedExcelCOMM(finalJsonString);
				  return getExcelEntity("Commercial_Report2");
		 
		}

			 
			 
			 
			 
			 
			 
			 @RequestMapping(value="/getCasesProcessedCOMM/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCasesProcessedCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getCasesProcessedCOMM(finalJsonString).toString());

				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelCOMM/"+jsonData);
				 	ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
				}
			 
			 
			 @RequestMapping(value="/getCasesProcessedExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesProcessedExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 commDelegate.getCasesProcessedExcelCOMM(finalJsonString);
				  return getExcelEntity("Commercial_Report3");
		 
		}

			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getRoleWiseTimeTakenCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getRoleWiseTimeTakenCOMM(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelCOMM/"+jsonData);
				 
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
			

				}
			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 commDelegate.getRoleWiseTimeTakenExcelCOMM(finalJsonString);
				  return getExcelEntity("Commercial_Report4");
		 
		}

			 
			 
			 @RequestMapping(value="/getTotalCycleTimeCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getTotalCycleTimeCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getTotalCycleTimeCOMM(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelCOMM/"+jsonData);
				 
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;

				}
			 
			 
			 
			 @RequestMapping(value="/getTotalCycleTimeExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 commDelegate.getTotalCycleTimeExcelCOMM(finalJsonString);
				  return getExcelEntity("Commercial_Report5");
		 
		}
				
			    @RequestMapping(value="/getCasesPendingCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
			    	
			    	 System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", commDelegate.getCasesPendingCOMM(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelCOMM/"+jsonData);
					 
						ModelAndView model = new ModelAndView("caseDetailPage");
						
						return model;


				}
			 
			 
			    
			    @RequestMapping(value="/getCasesPendingExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesPendingExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 commDelegate.getCasesPendingExcelCOMM(finalJsonString);
					  return getExcelEntity("Commercial_Report6");
			 
			}
			    
			    
			    
			  @RequestMapping(value="/getCasesCompletedCumulativelyCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesCompletedCumulativelyCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
				  
				  System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", commDelegate.getCasesCompletedCumulativelyCOMM(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesCompletedCumulativelyExcelCOMM/"+jsonData);
					 
						ModelAndView model = new ModelAndView("caseDetailPage");
			return model;
				}
			 
			  
			  
			  
			  @RequestMapping(value="/getCasesCompletedCumulativelyExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesCompletedCumulativelyExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 commDelegate.getCasesCompletedCumulativelyExcelCOMM(finalJsonString);
					  return getExcelEntity("Commercial_Report7");
			 
			}
			  
			  
			  
			  
			  
			  
			 
			 @RequestMapping(value="/getCasesPendingForProcessingCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingForProcessingCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", commDelegate.getCasesPendingForProcesssingCOMM(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingForProcessingExcelCOMM/"+jsonData);
				 
					ModelAndView model = new ModelAndView("caseDetailPage");
				return model;

				}
			 
			 
			 
			 
			 
			 @RequestMapping(value="/getCasesPendingForProcessingExcelCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesPendingForProcessingExcelCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 commDelegate.getCasesPendingForProcesssingExcelCOMM(finalJsonString);
				
		   return getExcelEntity("Commercial_Report8");
		}
		  
			 
			 
			 
			 
			  @RequestMapping(value="/getCasesPendingWithPRTOPOCOMM/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingWithPRTOPOCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
			    	
			    	 System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", commDelegate.getCasesPendingWithPRTOPOCOMM(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelWithPRTOPOCOMM/"+jsonData);
					 
						ModelAndView model = new ModelAndView("caseDetailPage");
						
						return model;


				}
			 
			 
			    
			    @RequestMapping(value="/getCasesPendingExcelWithPRTOPOCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesPendingExcelWithPRTOPOCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 commDelegate.getCasesPendingExcelWithPRTOPOCOMM(finalJsonString);
					  return getExcelEntity("Commercial_Report9");
			 
			}
			    
			 
				  
				 
				 @RequestMapping(value="/getCasesPendingForProcessingWithPRTOPOCOMM/{jsonData}",method=RequestMethod.GET)
					public ModelAndView getCasesPendingForProcessingWithPRTOPOCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
					{
					 
					 System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", commDelegate.getCasesPendingForProcesssingWithPRTOPOCOMM(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingForProcessingExcelWithPRTOPOCOMM/"+jsonData);
					 
						ModelAndView model = new ModelAndView("caseDetailPage");
					return model;

					}
				 
				 
				 
				 
				 
				 @RequestMapping(value="/getCasesPendingForProcessingExcelWithPRTOPOCOMM/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesPendingForProcessingExcelWithPRTOPOCOMM(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 commDelegate.getCasesPendingForProcesssingExcelWithPRTOPOCOMM(finalJsonString);
					
			   return getExcelEntity("Commercial_Report10");
			}
			 
			 //RTR WebServices
			 @RequestMapping(value="/getCaseCreatedCompanyRTR/{jsonData}",method=RequestMethod.GET)			
				public ModelAndView getCaseCreatedCompanyRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", rTRDelegate.getNumberOfCasesCreatedRTR(finalJsonString).toString());
				 System.out.println("Table String Completed  ");
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelRTR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				 
				}
			 
			
					 @RequestMapping(value="/getCaseCreatedCompanyExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 rTRDelegate.getNumberOfCasesCreatedExcelRTR(finalJsonString);
						  return getExcelEntity("RTR_Report1");
				 
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedRTR/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCaseCreatedCompanyCompletedRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", rTRDelegate.getCaseCreatedCompanyCompletedRTR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelRTR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
			
				}
			 
			 
			 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 rTRDelegate.getCaseCreatedCompanyCompletedExcelRTR(finalJsonString);
				  return getExcelEntity("RTR_Report2");

		}

			 
			 
			 
			 
			 
			 
			 @RequestMapping(value="/getCasesProcessedRTR/{jsonData}",method=RequestMethod.GET)		
				public ModelAndView getCasesProcessedRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", rTRDelegate.getCasesProcessedRTR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelRTR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
				}
			 
			 
			 @RequestMapping(value="/getCasesProcessedExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getCasesProcessedExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 rTRDelegate.getCasesProcessedExcelRTR(finalJsonString);
				  return getExcelEntity("RTR_Report3");

		}

			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenRTR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getRoleWiseTimeTakenRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", rTRDelegate.getRoleWiseTimeTakenRTR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelRTR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;
				
			

				}
			 
			 
			 @RequestMapping(value="/getRoleWiseTimeTakenExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 rTRDelegate.getRoleWiseTimeTakenExcelRTR(finalJsonString);
				  return getExcelEntity("RTR_Report4");

		}

			 
			 
			 @RequestMapping(value="/getTotalCycleTimeRTR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getTotalCycleTimeRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{
				 
				 System.out.println("encoded json  "+jsonData);
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 System.out.println("decoded json  "+finalJsonString);
				 request.setAttribute("detailTable", rTRDelegate.getTotalCycleTimeRTR(finalJsonString).toString());
				 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelRTR/"+jsonData);
					
					ModelAndView model = new ModelAndView("caseDetailPage");
					
					return model;

				}
			 
			 
			 
			 @RequestMapping(value="/getTotalCycleTimeExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
			 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
				) throws SQLException, JSONException, IOException
		{
				 
				 
				 byte[] bytes = Base64.decodeBase64(jsonData); 
				 String finalJsonString = new String(bytes);
				 rTRDelegate.getTotalCycleTimeExcelRTR(finalJsonString);
				  return getExcelEntity("RTR_Report5");

		}
				
			    @RequestMapping(value="/getCasesPendingRTR/{jsonData}",method=RequestMethod.GET)
				public ModelAndView getCasesPendingRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
				{	
			    	
			    	 System.out.println("encoded json  "+jsonData);
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 System.out.println("decoded json  "+finalJsonString);
					 request.setAttribute("detailTable", rTRDelegate.getCasesPendingRTR(finalJsonString).toString());
					 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelRTR/"+jsonData);
						
						ModelAndView model = new ModelAndView("caseDetailPage");
						
						return model;


				}
			 
			 
			    
			    @RequestMapping(value="/getCasesPendingExcelRTR/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
				 public @ResponseBody ResponseEntity   getCasesPendingExcelRTR(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
					) throws SQLException, JSONException, IOException
			{
					 
					 
					 byte[] bytes = Base64.decodeBase64(jsonData); 
					 String finalJsonString = new String(bytes);
					 rTRDelegate.getCasesPendingExcelRTR(finalJsonString);
					  return getExcelEntity("RTR_Report6");
			 
			}
			    
			    
			 
			 
				
				  //Tax WebServices
					 @RequestMapping(value="/getCaseCreatedCompanyTax/{jsonData}",method=RequestMethod.GET)			
						public ModelAndView getCaseCreatedCompanyTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{
						 System.out.println("encoded json  "+jsonData);
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 System.out.println("decoded json  "+finalJsonString);
						 request.setAttribute("detailTable", tAXDelegate.getNumberOfCasesCreatedTax(finalJsonString).toString());
						 System.out.println("Table String Completed  ");
						 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyExcelTax/"+jsonData);
							
							ModelAndView model = new ModelAndView("caseDetailPage");
							
							return model;
						 
						}
					 
					
							 @RequestMapping(value="/getCaseCreatedCompanyExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
							 public @ResponseBody ResponseEntity   getCaseCreatedCompanyExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
								) throws SQLException, JSONException, IOException
						{
								 
								 
								 byte[] bytes = Base64.decodeBase64(jsonData); 
								 String finalJsonString = new String(bytes);
								 tAXDelegate.getNumberOfCasesCreatedExcelTax(finalJsonString);
								  return getExcelEntity("TAX_Report1");
						 
						}
					 
					 
					 @RequestMapping(value="/getCaseCreatedCompanyCompletedTax/{jsonData}",method=RequestMethod.GET)		
						public ModelAndView getCaseCreatedCompanyCompletedTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{
						 
						 
						 System.out.println("encoded json  "+jsonData);
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 System.out.println("decoded json  "+finalJsonString);
						 request.setAttribute("detailTable", tAXDelegate.getCaseCreatedCompanyCompletedTax(finalJsonString).toString());
						 request.setAttribute("downloadLink","/jsw-report/NW/getCaseCreatedCompanyCompletedExcelTax/"+jsonData);
							
							ModelAndView model = new ModelAndView("caseDetailPage");
							
							return model;
					
						}
					 
					 
					 @RequestMapping(value="/getCaseCreatedCompanyCompletedExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getCaseCreatedCompanyCompletedExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 tAXDelegate.getCaseCreatedCompanyCompletedExcelTax(finalJsonString);
						  return getExcelEntity("TAX_Report2");

				}

					 
					 
					 
					 
					 
					 
					 @RequestMapping(value="/getCasesProcessedTax/{jsonData}",method=RequestMethod.GET)		
						public ModelAndView getCasesProcessedTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{
						 
						 System.out.println("encoded json  "+jsonData);
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 System.out.println("decoded json  "+finalJsonString);
						 request.setAttribute("detailTable", tAXDelegate.getCasesProcessedTax(finalJsonString).toString());
						 request.setAttribute("downloadLink","/jsw-report/NW/getCasesProcessedExcelTax/"+jsonData);
							
							ModelAndView model = new ModelAndView("caseDetailPage");
							
							return model;
						
						}
					 
					 
					 @RequestMapping(value="/getCasesProcessedExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getCasesProcessedExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 tAXDelegate.getCasesProcessedExcelTax(finalJsonString);
						  return getExcelEntity("TAX_Report3");

				}

					 
					 
					 @RequestMapping(value="/getRoleWiseTimeTakenTax/{jsonData}",method=RequestMethod.GET)
						public ModelAndView getRoleWiseTimeTakenTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{
						 
						 System.out.println("encoded json  "+jsonData);
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 System.out.println("decoded json  "+finalJsonString);
						 request.setAttribute("detailTable", tAXDelegate.getRoleWiseTimeTakenTax(finalJsonString).toString());
						 request.setAttribute("downloadLink","/jsw-report/NW/getRoleWiseTimeTakenExcelTax/"+jsonData);
							
							ModelAndView model = new ModelAndView("caseDetailPage");
							
							return model;
						
					

						}
					 
					 
					 @RequestMapping(value="/getRoleWiseTimeTakenExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getRoleWiseTimeTakenExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 tAXDelegate.getRoleWiseTimeTakenExcelTax(finalJsonString);
						  return getExcelEntity("TAX_Report4");

				}

					 
					 
					 @RequestMapping(value="/getTotalCycleTimeTax/{jsonData}",method=RequestMethod.GET)
						public ModelAndView getTotalCycleTimeTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{
						 
						 System.out.println("encoded json  "+jsonData);
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 System.out.println("decoded json  "+finalJsonString);
						 request.setAttribute("detailTable", tAXDelegate.getTotalCycleTimeTax(finalJsonString).toString());
						 request.setAttribute("downloadLink","/jsw-report/NW/getTotalCycleTimeExcelTax/"+jsonData);
							
							ModelAndView model = new ModelAndView("caseDetailPage");
							
							return model;

						}
					 
					 
					 
					 @RequestMapping(value="/getTotalCycleTimeExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
					 public @ResponseBody ResponseEntity   getTotalCycleTimeExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
						) throws SQLException, JSONException, IOException
				{
						 
						 
						 byte[] bytes = Base64.decodeBase64(jsonData); 
						 String finalJsonString = new String(bytes);
						 tAXDelegate.getTotalCycleTimeExcelTax(finalJsonString);
						  return getExcelEntity("TAX_Report5");

				}
						
					    @RequestMapping(value="/getCasesPendingTax/{jsonData}",method=RequestMethod.GET)
						public ModelAndView getCasesPendingTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
						{	
					    	
					    	 System.out.println("encoded json  "+jsonData);
							 byte[] bytes = Base64.decodeBase64(jsonData); 
							 String finalJsonString = new String(bytes);
							 System.out.println("decoded json  "+finalJsonString);
							 request.setAttribute("detailTable", tAXDelegate.getCasesPendingTax(finalJsonString).toString());
							 request.setAttribute("downloadLink","/jsw-report/NW/getCasesPendingExcelTax/"+jsonData);
								
								ModelAndView model = new ModelAndView("caseDetailPage");
								
								return model;


						}
					 
					 
					    
					    @RequestMapping(value="/getCasesPendingExcelTax/{jsonData}",method=RequestMethod.GET,produces="application/vnd.ms-excel")			
						 public @ResponseBody ResponseEntity   getCasesPendingExcelTax(@PathVariable String jsonData,HttpServletRequest request, HttpServletResponse response
							) throws SQLException, JSONException, IOException
					{
							 
							 
							 byte[] bytes = Base64.decodeBase64(jsonData); 
							 String finalJsonString = new String(bytes);
							 tAXDelegate.getCasesPendingExcelTax(finalJsonString);
							  return getExcelEntity("TAX_Report6");
					 
					}
					    
						
						
	 
	
	
	 //Other WebServices
	 @RequestMapping(value="/getRoleAndCase/{functionValue}",method=RequestMethod.GET)
		@ResponseBody
		public String getRoleAndCase(@PathVariable(value="functionValue")String functionValue) throws SQLException, JSONException
		{
		 
	
		return vwDelegate.getRoleAndCasetype(functionValue).toString();
		}
	 
	 @RequestMapping(value="/getLocationByGroup/{group}",method=RequestMethod.GET)
		@ResponseBody
		public String getLocationByGroup(@PathVariable(value="group")String group,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
		{
		 
	
		return vwDelegate.getBusinessAndLocationByGrp(group,Integer.parseInt(request.getSession().getAttribute("UserId").toString())).toString();
		}
	 
	 @RequestMapping(value="/getBusinessByLocation/{location}",method=RequestMethod.GET)
		@ResponseBody
		public String getBusinessByLocation(@PathVariable(value="location")String location,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
		{
		 
	
		return vwDelegate.getBusiByLoc(location,Integer.parseInt(request.getSession().getAttribute("UserId").toString())).toString();
		}
	 

@RequestMapping(value="/getPersoneelAndPayrollByGrp/{busiGroup}",method=RequestMethod.GET)
@ResponseBody
public String getPersoneelAndPayrollByGrp(@PathVariable(value="busiGroup")String busiGroup,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
{


return vwDelegate.getPersoneelAndPayrollByGrp(busiGroup,Integer.parseInt(request.getSession().getAttribute("UserId").toString())).toString();
}
@RequestMapping(value="/getPersoneelAndPayrollByBusiness/{busiGroup}/{business}",method=RequestMethod.GET)
@ResponseBody
public String getPersoneelAndPayrollByBusiness(@PathVariable(value="busiGroup")String busiGroup,@PathVariable(value="business")String business,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
{


return vwDelegate.getPersoneelAndPayrollByBusiness(busiGroup,business,Integer.parseInt(request.getSession().getAttribute("UserId").toString())).toString();
}

@RequestMapping(value="/getPayrollByPersonnel/{business}",method=RequestMethod.GET)
@ResponseBody
public String getPayrollByPersonnel(@PathVariable(value="business")String business,HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException
{
System.out.println("business  "+business);

return vwDelegate.getPayrollByPersonnel(business,Integer.parseInt(request.getSession().getAttribute("UserId").toString())).toString();
}	














private ResponseEntity getExcelEntity(String fileName)throws SQLException, JSONException, IOException{
	 
	 ResponseEntity respEntity = null;

	    byte[] reportBytes = null;
	    File result=new File(fileName+".xlsx");

	    if(result.exists()){
	        InputStream inputStream = new FileInputStream(fileName+".xlsx");
	        String type=result.toURL().openConnection().guessContentTypeFromName(fileName+".xlsx");

	        byte[]out=org.apache.commons.io.IOUtils.toByteArray(inputStream);

	        HttpHeaders responseHeaders = new HttpHeaders();
	        responseHeaders.add("content-disposition", "attachment; filename="+fileName+".xlsx");
	        responseHeaders.add("Content-Type",type);

	        respEntity = new ResponseEntity(out, responseHeaders,HttpStatus.OK);
	    }else{
	        respEntity = new ResponseEntity ("File Not Found", HttpStatus.OK);
	    }
	    return respEntity;
	 
	 
}
	
}
