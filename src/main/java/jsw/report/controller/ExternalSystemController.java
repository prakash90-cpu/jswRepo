package jsw.report.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsw.report.delegate.VwDelegate;
import jsw.report.service.ManualExtractionService;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.google.gson.Gson;
import com.jsw.dms.extraction.beans.ConfigKeyValue;
import com.jsw.dms.extraction.dao.ExtractionProgressDAO;
import com.jsw.dms.extraction.service.ExtractionService;

import com.jsw.domain.object.ExtractionProgressDO;
import com.jsw.domain.object.ManualExtTriggerDO;
import com.jsw.transfer.object.ExtractionProcessTO;

@Controller
@RequestMapping(value = "/externalSystems")
public class ExternalSystemController {

	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private VwDelegate vwDelegate;
	
	@Autowired
	private ManualExtractionService extService;

	public VwDelegate getVwDelegate() {
		return vwDelegate;
	}

	public void setVwDelegate(VwDelegate vwDelegate) {
		this.vwDelegate = vwDelegate;
	}

	// @Autowired
	// private DmsExtractionService dmsExtractionService;

	// @Autowired
	// ExtractionService apExtractionService;

	@Autowired
	private ExtractionProgressDAO progressDAO;

	
	@RequestMapping(value="/caseType/{progressType}",method=RequestMethod.GET)
	@ResponseBody
	public String getCaseType(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "progressType") String progressType) throws SQLException{
		
		System.out.println(progressType);
		
			ExtractionService extractionService = getService(progressType);
			List<ConfigKeyValue> caseTypes = extractionService.getReportKeyValue();
			/*request.setAttribute("caseTypes", caseTypes);*/
			System.err.println("case type is:"+caseTypes.get(0).getKey());
		
		
			String json = new Gson().toJson(caseTypes);
			
		
			
		return json;
	}
	
	
	
	@RequestMapping(value = "/listExtraction", method = RequestMethod.GET)
	public ModelAndView getExtractionProcess(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("ExtractionBean") ExtractionProcessTO process) {
		ModelAndView model = new ModelAndView("extractionProcess");
		List functionList = null;

		try {
			
			functionList = vwDelegate.getFunctionManagement();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("FunctionList", functionList);

		return model;
	}

	@RequestMapping(value = "/submitExtraction", method = RequestMethod.POST)
	public ModelAndView submitExtractionProcess(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("ExtractionBean") ExtractionProcessTO process)
			throws SQLException, JSONException {
     System.err.println(process.getModule());
		ExtractionProgressDO progress = null;
		List<ExtractionProgressDO> list = null;
		try {
			
			ManualExtTriggerDO insert = vwDelegate.addExtractionProcess(process);
			

			//ExtractionService extractionService = getService(process.getModule());

			
			progress = prepareProgress(insert);
			
			progress.setStatus("Initiated");
			progressDAO.saveProgressDetails(progress);
			try {
				//extService.proceedAsyncExtraction(process, progress);
				extService.executorService(process, progress);
				
			}catch(Exception ex) {
				
				progress.setStatus("Failed");
			}
			
			progressDAO.saveProgressDetails(progress);

			//List mainlist = new ArrayList<Object>();

			// list= progressDAO.getAllProgressDetails();

			//list = vwDelegate.getProgressresult();

		} catch (Exception e) {
			e.printStackTrace();
			
		}

		
		
		ModelAndView model1 =getExtractionProgress( request,response,process);
		
		return model1;

	}

	

	ExtractionService getService(String function) {
		// WebApplicationContext ctx =
		// WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		ExtractionService extractionService = (ExtractionService) appContext
				.getBean(function);
		return extractionService;

	}

	@RequestMapping(value = "/viewExtraction", method = RequestMethod.GET)
	public ModelAndView getExtractionProgress(HttpServletRequest request,
			HttpServletResponse response, ExtractionProcessTO process) {

		

		List<ExtractionProgressDO> list = progressDAO.getAllProgressDetails();
		ModelAndView model = new ModelAndView("extractionProgress",
				"ProgressDetail", list);
		
		return model;
	}

	private ExtractionProgressDO prepareProgress(ManualExtTriggerDO insert) {
		ExtractionProgressDO progress = new ExtractionProgressDO();
		// progress.setPercentageCompleated(0);
		progress.setManualTriggerId(insert.getManualExtTriggerId());
		progress.setProcesType(insert.getProcessType());
		//progress.setEndTime(insert.getEndTime());
		progress.setEndDate(insert.getEndDate());
		//progress.setStartTime(insert.getStartTime());
		progress.setStartDate(insert.getStartDate());
		progress.setModuleName(insert.getFunction());
		//progress.setStatus("initiated");

		return progress;
	}
	
	
	@RequestMapping(value = "/deleteExtraction", method = RequestMethod.GET)
	public ModelAndView getExtractionProcessDeletion(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("ExtractionBean") ExtractionProcessTO process) {
		ModelAndView model = new ModelAndView("deletionOfManualExtraction");
		List functionList = null;

		try {
			
			functionList = vwDelegate.getFunctionManagement();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("FunctionList", functionList);

		return model;
	}
	
	@RequestMapping(value = "/submitDeleteExtraction", method = RequestMethod.POST)
	public ModelAndView deletetExtractionProcess(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("ExtractionBean") ExtractionProcessTO process)
			throws SQLException, JSONException {
     
		ExtractionProgressDO progress = null;
		List<ExtractionProgressDO> list = null;
		vwDelegate.deleteExtraction(process);

		ModelAndView model1 =getExtractionProcessDeletion(request,response,process);
		
		request.setAttribute("message", "Extraction deleted successfully");
		return model1;

	}

	
	
	

}
