package com.employee.app.employeeSpark.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SparkConfig {

	@Bean
	public SparkSession sparkSession() {
		System.out.println("SparkSession");
		return SparkSession.builder()
			.appName("EmployeeSparkApplication")
				.master("local[*]") // Change this to your																								// cluster settings if																				// not running locally
				.getOrCreate();
	}

}
