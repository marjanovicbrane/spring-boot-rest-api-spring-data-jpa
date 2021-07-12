package com.brane.springboot.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brane.springboot.crud.dao.EmployeeDAO;
import com.brane.springboot.crud.entity.Employee;

//THIS IS SERVICE LAYER
@Service
public class EmployeeServiceImpl implements EmployeeService {

			//private field employeeDAO, so we can do constructor dependency injection 
			//to delegate calls from SERVICE LAYER TO THE DAO LAYER
			private EmployeeDAO employeeDAO;
			
			//constructor injection
			@Autowired
			public EmployeeServiceImpl(EmployeeDAO theEmployeeDAO) {
				employeeDAO = theEmployeeDAO;
			}

			

		@Override
		//annotation for "begin transaction" and "commit transaction"
		@Transactional
		public List<Employee> findAll() {
			
			//DELEGATE CALLS TO THE DAO LAYER
			return employeeDAO.findAll();
		}


		@Override
		@Transactional
		public Employee findById(int theId) {

			return employeeDAO.findById(theId);
		}


		@Override
		@Transactional
		public void save(Employee theEmployee) {

			employeeDAO.save(theEmployee);

		}


		@Override
		@Transactional
		public void deleteById(int theId) {

			employeeDAO.deleteById(theId);

		}

	}
