package com.employee.app.employeeSpark.config;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.employee.app.employeeSpark.entity.MyRecord;
import com.employee.app.employeeSpark.entity.TargetStudent;
import com.employee.app.employeeSpark.processer.MyItemProcessor;
import com.employee.app.employeeSpark.reader.SparkItemReader;
import com.employee.app.employeeSpark.writer.MyItemWriter;



@Configuration
@EnableBatchProcessing
public class BatchProcessing {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
	 private SparkSession sparkSession;
    
   
    
    @Bean
    public Job readExcelJob() throws Exception {
        return jobBuilderFactory.get("readExcelJob")
                .start(readExcelStep())  // Define a step that uses the Excel reader
                .build();
    }

    @Bean
    public Step readExcelStep() throws Exception {
        return stepBuilderFactory.get("readExcelStep")
                .<MyRecord, TargetStudent>chunk(10)
                .reader(excelItemReader())  // Use custom Excel reader
                .processor(processor())
                .writer(writer())
                .build();
    }

	@Bean
    public SparkItemReader excelItemReader() throws Exception {
    	System.out.println("Spark Started..........!");
        // String filePath = "/path/to/your/excel/file.xlsx";  // Replace with your file path
    	 String filePath = "C:\\Users\\SREEDHAR\\Downloads\\id_name_data_10000.csv";
    	 String filePath1 = "C:\\Users\\SREEDHAR\\Downloads\\New Text Document.txt";
    	 //Dataset<Row> dataset = sparkSession.read().csv(filePath);
    	 return new SparkItemReader(sparkSession, filePath);
    }
	
        @Bean
	    public MyItemProcessor processor() {
    	  System.out.println("processor Started..........!");
	        return new MyItemProcessor();
	    }
    
        @Bean
	    public MyItemWriter writer() {
        	System.out.println("writer Started..........!");
	        return new MyItemWriter();
	    }


}
