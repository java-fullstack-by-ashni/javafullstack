package com.csvreader;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@EnableBatchProcessing
@Configuration
public class CsvFileToObjectsConfig {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

   
    public void setDataSource(DataSource dataSource) {
     }
        
    // csv file reader
    @Bean
    public FlatFileItemReader<RecordDTO> csvReader(){
        FlatFileItemReader<RecordDTO> reader = new FlatFileItemReader<RecordDTO>();
        reader.setResource(new ClassPathResource("records.csv"));
        
    	reader.setLinesToSkip(1); 
        
        reader.setLineMapper(new DefaultLineMapper<RecordDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"reference","accountNumber","description","startBalance","mutation","endBalance"});
            }});
            
            setFieldSetMapper(new BeanWrapperFieldSetMapper<RecordDTO>() {{
                setTargetType(RecordDTO.class);
            }});
        }});
        
        
        return reader;
    }
    
    // xml file reader
    @Bean  
    public ItemReader<RecordDTO> xmlFileItemReader() {
        StaxEventItemReader<RecordDTO> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new ClassPathResource("records.xml"));
        xmlFileReader.setFragmentRootElementName("record");
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(RecordDTO.class);
        xmlFileReader.setUnmarshaller(studentMarshaller);
        return xmlFileReader;
    }
    
	@Bean
	ItemProcessor<RecordDTO, RecordDTO> csvProcessor() {
		return new FileProcessor();
	}

	@Bean
	public Step csvFileToListStep() {
		return stepBuilderFactory.get("csvFileToListStep")
				.<RecordDTO, RecordDTO>chunk(1)
				.reader(csvReader()) // csv file reader
				//.reader(xmlFileItemReader()) // xml file reader
				.processor(csvProcessor())
				.build();
	}

	@Bean
	Job csvFileToObjectsJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("csvFileToObjectsJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(csvFileToListStep())
				.end()
				.build();
	}
	 // end job info
}
