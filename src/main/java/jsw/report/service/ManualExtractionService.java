package jsw.report.service;

import com.jsw.domain.object.ExtractionProgressDO;
import com.jsw.transfer.object.ExtractionProcessTO;

public interface ManualExtractionService {
	public void proceedAsyncExtraction(ExtractionProcessTO process,
			ExtractionProgressDO progress) ;
	public  void executorService(final ExtractionProcessTO process,
			final ExtractionProgressDO progress);

}
