package com.csvreader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import com.itextpdf.text.DocumentException;

public class FileProcessor implements ItemProcessor<RecordDTO, RecordDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(FileProcessor.class);
    Map<String,String> recordMap =new HashMap<String,String>();
    List<RecordDTO> recordList=new ArrayList<RecordDTO>();
    
    
    @Override
    public RecordDTO process(final RecordDTO recordDTO) throws Exception {
    	
    
        final String reference=recordDTO.getReference();
        final String accountNumber=recordDTO.getAccountNumber();
        final String description=recordDTO.getDescription();
        final String startBalance=recordDTO.getStartBalance();
        final String mutation=recordDTO.getMutation();
        final String endBalance=recordDTO.getEndBalance();
        boolean ref=false;
        boolean tb=false;
       final RecordDTO transformedRecordDTO = new RecordDTO(reference,accountNumber,description,startBalance,mutation,endBalance);
       
       if(recordList.size()==0) {
    	   log.info("Converting (" + recordDTO + ") into (" + transformedRecordDTO + ")");
           recordList.add(transformedRecordDTO);
       }
       
       else {
          log.info("check duplicate Transaction reference==>");
          ref=Utility.checkDuplicateTransactionRef(recordList, transformedRecordDTO.getReference());
          log.info("is Transaction reference unique ? <<"+ transformedRecordDTO.getReference() +">>==>" + ref);
          if(ref==true) {
        	  recordMap.put(transformedRecordDTO.getReference(), "Duplicate Transaction Reference Number.");
          }
          if(ref==false) {
          log.info("check End Balance:==>");
          tb=Utility.checkTotalBalance(transformedRecordDTO.getStartBalance(),transformedRecordDTO.getMutation(), transformedRecordDTO.getEndBalance());
          if(tb==true) {
        	  recordMap.put(transformedRecordDTO.getReference(), "Incorrect End Balance.");
           }
          log.info("is End Balance coorect ? <<"+ transformedRecordDTO.getReference() +">>==>" + tb);

          }
         }
       if((!ref) && (!tb)) {
        log.info("Converting (" + recordDTO + ") into (" + transformedRecordDTO + ")");
        recordList.add(transformedRecordDTO);
       }
       
        return transformedRecordDTO;
    }
  
    @AfterStep
    void jenerateReoprt(){
        log.info("error map"+recordMap);

    	try {
			GenerateReport.generateReport(recordMap);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
    }
 

}
