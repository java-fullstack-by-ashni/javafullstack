package com.csvreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CsvProcessor implements ItemProcessor<RecordDTO, RecordDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(CsvProcessor.class);
    
    @Override
    public RecordDTO process(final RecordDTO recordDTO) throws Exception {
    	
    
        final String reference=recordDTO.getReference();
        final String accountNumber=recordDTO.getAccountNumber();
        final String description=recordDTO.getDescription();
        final String startBalance=recordDTO.getStartBalance();
        final String mutation=recordDTO.getMutation();
        final String endBalance=recordDTO.getEndBalance();

       final RecordDTO transformedRecordDTO = new RecordDTO(reference,accountNumber,description,startBalance,mutation,endBalance);

        log.info("Converting (" + recordDTO + ") into (" + transformedRecordDTO + ")");

        return transformedRecordDTO;
    }

}
