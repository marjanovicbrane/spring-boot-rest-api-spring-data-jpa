package com.brane.springboot.crud.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brane.springboot.crud.entity.Employee;
import com.brane.springboot.crud.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	///private field employeeService, so we can do constructor dependency injection 
	//to delegate calls from CONTROLLER LAYER TO THE SERVICE LAYER
	private EmployeeService employeeService;
	
	//constructor injection
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	

	//****************************************************************************************************************
	//WE WILL USING POSTMAN REST CLIENT TO SHOW ALL RESULTS FROM THE HTTP REQUEST METHODS.
	//IMPORTANT THING IS THAT JACKSON IS AUTOMATICALLY EMBEDDED IN SPRING BOOT, SO WHEN WE RUN OUR APP
	//AND THIS ENDPOINT /api/employees (GET) WI WILL HAVE RESPONSE OBJECTS FROM THE DATABASE IN .JSON FORMAT.
	//THAT DATA WE CAN SHOW IN BROWSER OR SOME OTHER REST CLIENT LIKE POSTMAN.
	//WHEN WE GET JAVA OBJECTS FROM THE DATABASE, SPRING BOOT HERE CALLS JACKSON TO CONVERT JAVA POJO IN .JSON FORMAT.
	//WHEN WE CONVERT JAVA POJO TO JSON--->JACKSON CALLS GETTER METHODS
	//WHEN WE CONVERT JSON TO JAVA POJO--->JACKSON CALLS SETTER METHODS
	//*****************************************************************************************************************
	
	
	//add mapping for GET /employees
	@GetMapping("/employees")
	public List<Employee> findAll(){
		
		//delegate calls from the CONTROLLER LAYER to the SERVICE LAYER
		return employeeService.findAll();
		
	}
	
	
	// add mapping for GET /customers/{customerId}
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee employee = employeeService.findById(employeeId);
		
				//IF WE DON'T HAVE EMPLOYEE OBJECT IN DATABASE WITH THIS ID (employeeId) THEN WE WILL THROW
				//RuntimeException WITH MESSAGE,else return our employee object from db.
				//JACKSON FOR NULL VALUE RETURNS EMPTY BODY-BLANK PAGE.
				if (employee == null) {

					throw new RuntimeException("There is no employee with the ID - " + employeeId);
				}
				
				return employee;
	}
	
	
	// add mapping for POST /customers  - add new customer
	//WITH ANNOTATION @RequestBody, WE BINDING THAT OBJECT FROM .JSON TO THIS Employee theEmployee OBJECT
	//IN POSTMAN REST CLIENT IN REQUEST BODY WE PASSING .JSON OBJECT TO BE SAVED TO DATABASE.
	//IN THE BACKGROUND JACKSON CALLING SETTER METHODS TO SET OUR EMPLOYEE OBJECT TO DATA FROM .JSON.
	//WHEN WE PASS AN OBJECT IN REQUEST BODY WE DON'T PASS ID,BECAUSE ID IS AUTOMATICALLY GENERATED FROM THE DATABASE.
	//BECAUSE OF THAT, JUST IN CASE WE SET ID TO 0.IF USER PASS ID BY ACCIDENT 
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// also just in case the pass an id in JSON ... set id to 0
		// this is force a save of new item ... instead of update
		theEmployee.setId(0);
		
		//WE ARE USING THIS SAME METHOD FOR SAVE AND FOR UPDATE
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	
	// add mapping for PUT /customers - update existing customer
	//WHEN WE PASS AN OBJECT IN REQUEST BODY WE ALSO PASSING ID THIS TIME, BECAUSE WE NEED TO DO AN UPDATE
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		
		//WE ARE USING THIS SAME METHOD FOR SAVE AND FOR UPDATE
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	
	// add mapping for DELETE /customers/{customerId} - delete customer
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		//get customer from database with id employeeId
		Employee tempEmployee = employeeService.findById(employeeId);
		
		//ID employee with that id doesn't exist, throw runtime exception
		if (tempEmployee == null) {
			throw new RuntimeException("There is no employee with the ID - " + employeeId);
		}
		
		//else delete employee with that id
		employeeService.deleteById(employeeId);
		
		//return a message that 
		return "Employee has been deleted with id - " + employeeId;
	}
		
}
