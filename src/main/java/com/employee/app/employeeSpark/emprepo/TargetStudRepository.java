package com.employee.app.employeeSpark.emprepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.app.employeeSpark.entity.TargetStudent;
@Repository
public interface TargetStudRepository extends JpaRepository<TargetStudent, Integer>{

}
