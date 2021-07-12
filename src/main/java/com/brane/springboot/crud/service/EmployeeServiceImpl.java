package com.brane.springboot.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brane.springboot.crud.dao.EmployeeRepository;
import com.brane.springboot.crud.entity.Employee;

//THIS IS SERVICE LAYER
@Service
public class EmployeeServiceImpl implements EmployeeService {

			//private field employeeRepository, because we are using here SPRING DATA JPA
			//so we can do constructor dependency injection 
			//to delegate calls from SERVICE LAYER TO THE DAO LAYER
			private EmployeeRepository employeeRepository;
			
			//constructor injection
			@Autowired
			public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
				employeeRepository = theEmployeeRepository;
			}

			

		//WE DELETED @Transactional annotation for all methods, because JpaRepository interface
		//provides this functionality automatically
		@Override
		public List<Employee> findAll() {
			
			//DELEGATE CALLS TO THE DAO LAYER
			return employeeRepository.findAll();
		}


		@Override
		public Employee findById(int theId) {

			return employeeRepository.findById(theId);
		}


		@Override
		public void save(Employee theEmployee) {

			employeeRepository.save(theEmployee);

		}


		@Override
		public void deleteById(int theId) {

			employeeRepository.deleteById(theId);

		}

	}
