package com.itdebug.demo.service;

import com.itdebug.demo.core.CosmosDAL;
import com.itdebug.demo.listener.EmployeeChangeFeedListener;
import com.itdebug.demo.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/test")
public class TestServiceController {

    @Autowired
    EmployeeChangeFeedListener employeeChangeFeedListener;

    @Autowired
    CosmosDAL cosmosDAL;

    @GetMapping("/terminateChangeFeedEmployee")
    public ResponseEntity<String> getTerminateChangeFeedEmployee()
    {
        employeeChangeFeedListener.cancelSubscription();
        return new ResponseEntity<String>("Terminated ", HttpStatus.OK);
    }

    @GetMapping("/statusChangeFeedEmployee")
    public ResponseEntity<String> getStatusChangeFeedEmployee()
    {
        String status = employeeChangeFeedListener.subscriptionStatus();
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

    @PostMapping(path="/insertEmployee", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Collection<Employee>> addNewEmployee(@RequestBody Employee employee) {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Collection<Employee> currentInsertedDocs = cosmosDAL.persistEmployee(employees);
        return new ResponseEntity<Collection<Employee>>(currentInsertedDocs, HttpStatus.OK);
    }

    @GetMapping("/allEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        List<Employee> employees = cosmosDAL.getAllEmployees();
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

}
