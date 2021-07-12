package com.brane.springboot.crud.service;

import java.util.List;

import com.brane.springboot.crud.entity.Employee;

//we created interface for employee SERVICE to delegate calls to the DAO LAYER
public interface EmployeeService {
	
			public List<Employee> findAll();
		
			public Employee findById(int theId);
			
			public void save(Employee theEmployee);
		
			public void deleteById(int theId);
}

