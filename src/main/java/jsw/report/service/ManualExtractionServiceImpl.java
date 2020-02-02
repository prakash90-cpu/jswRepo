package jsw.report.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;


import com.jsw.dms.extraction.service.APExtractionServiceImpl;
import com.jsw.dms.extraction.service.ARExtractionServiceImpl;
import com.jsw.dms.extraction.service.COMExtractionServiceImpl;
import com.jsw.dms.extraction.service.ExtractionService;
import com.jsw.dms.extraction.service.HRExtractionServiceImpl;
import com.jsw.dms.extraction.service.RTRExtractionServiceImpl;
import com.jsw.dms.extraction.service.TAXExtractionServiceImpl;
import com.jsw.domain.object.ExtractionProgressDO;
import com.jsw.transfer.object.ExtractionProcessTO;

public class ManualExtractionServiceImpl implements ManualExtractionService{

	
	@Autowired
	private APExtractionServiceImpl apExtractionService;
	@Autowired
	private ARExtractionServiceImpl arExtractionService;
	@Autowired
	private COMExtractionServiceImpl comExtractionService;
	@Autowired
	private RTRExtractionServiceImpl rtrExtractionService;
	@Autowired
	private HRExtractionServiceImpl hrExtractionService;
	@Autowired
	private TAXExtractionServiceImpl taxExtractionService;
	ExtractionService extractionService;
	
	
	//@Async
	public void proceedAsyncExtraction(ExtractionProcessTO process,
			ExtractionProgressDO progress) {
		// try {
		
		if(process.getModule() .equalsIgnoreCase("apExtractionService"))
		{
			System.out.println(process.getModule());
			 extractionService=(ExtractionService)apExtractionService;
		}
		if(process.getModule() .equalsIgnoreCase("arExtractionService")){
			 extractionService=(ExtractionService)arExtractionService;
		}
		if(process.getModule() .equalsIgnoreCase("comExtractionService")){
			 extractionService=(ExtractionService)comExtractionService;
		}
		
		if(process.getModule() .equalsIgnoreCase("rtrExtractionService")){
			 extractionService=(ExtractionService)rtrExtractionService;
		}
		if(process.getModule() .equalsIgnoreCase("taxExtractionService")){
			extractionService=(ExtractionService)taxExtractionService;
			System.out.println(process.getModule());
			
		}
		if( process.getModule() .equalsIgnoreCase("hrExtractionService")){
			 extractionService=(ExtractionService)hrExtractionService;
		}
		
		if (process.getProgressType().equalsIgnoreCase("ALL")) {
			extractionService.initiateExtraction(process, progress);
			extractionService
					.initiateCompletedDataExtraction(process, progress);
		} else if (process.getProgressType().equalsIgnoreCase("WIP")) {
			extractionService.initiateExtraction(process, progress);
		} else {
			extractionService
					.initiateCompletedDataExtraction(process, progress);
		}
		
		
		
		
		
		progress.setStatus("SUCCESS");
		
	}

	
	public  void executorService(final ExtractionProcessTO process,
			final ExtractionProgressDO progress){
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
		    public void run() {
		        System.out.println("Asynchronous task");
		         proceedAsyncExtraction(process,progress);
		    }
		});

		executorService.shutdown();
		
	}

	
	
}
