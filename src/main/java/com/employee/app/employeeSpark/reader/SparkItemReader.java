package com.employee.app.employeeSpark.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.employee.app.employeeSpark.entity.MyRecord;


public class SparkItemReader implements ItemReader<MyRecord>{
	
	
	 private SparkSession sparkSession;	 
	
	 private String filePath;
	 
	 private List<MyRecord> records;

	public SparkItemReader(SparkSession sparkSession, String filePath) throws Exception {
		this.filePath = filePath;
		this.sparkSession = sparkSession;
		this.initial();
	}

	private void initial() throws Exception {
		 System.out.println("Spark Started..........!");
		Dataset<Row> dataset = sparkSession.read()
				.option("header", "true")  // Use the first row as headers
				.csv(filePath);
		// Convert Dataset<Row> to List<Row>
		List<Row> list = dataset.collectAsList();
		
		 // Convert List<Row> to List<MyRecord>
		List<MyRecord> records = mapRowDatasetToMyRecordDataset(list);
		   //  all feilds are Strings in MyRecord class then we can go with below step
				//Dataset<MyRecord> record = dataset.as(Encoders.bean(MyRecord.class));
		System.out.println("Class of MyRecord: " + MyRecord.class);
		    	 
	}
		
	
	
	@Override
	public MyRecord read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

      if((records != null)&&!(records.isEmpty())) {
		 for(MyRecord record : records) {
		return record;
		 }
      }
		return null;
}
	
	
	
	private List<MyRecord> mapRowDatasetToMyRecordDataset(List<Row> rows) {
		 return rows.stream().map(row -> {
	            int id = Integer.parseInt(row.getString(row.fieldIndex("id"))); // replace with your actual column name
	            String name = row.getString(row.fieldIndex("name")); // replace with your actual column name
	           
	            
	            return new MyRecord(id, name);
	        }).collect(Collectors.toList());
	   
}
}