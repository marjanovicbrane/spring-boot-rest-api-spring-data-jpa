package com.brane.springboot.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		//@Transactional
		public List<Employee> findAll() {
			
			//DELEGATE CALLS TO THE DAO LAYER
			return employeeRepository.findAll();
		}


		@Override
		public Employee findById(int theId) {

			//*********************************OPTIONAL************************************
			//Its findById method retrieves an entity by its id. The return value is Optional<T> . 
			//Optional<T> is a container object which may or may not contain a non-null value. 
			//If a value is present, isPresent returns true and get returns the value.Otherwise return false.
			//When returning an Optional instance, it's a useful hint that there's a possibility 
			//that the value might not exist.
			
			//Optional Class is a container for an object that may contains null.With this Optional class, 
			//we can semantically told clients that a function they will use may return a null value
			//that lead into NullPointerException.
			//******************************************************************************
			

			Optional<Employee> result = employeeRepository.findById(theId);
			
			Employee employee=null;
			
			if(result.isPresent()) {

				employee=result.get();
			}
			else {

				throw new RuntimeException("There is no employee with the ID - "+theId);
			}
			return employee;
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
