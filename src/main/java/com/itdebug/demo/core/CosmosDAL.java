package com.itdebug.demo.core;

import com.itdebug.demo.models.DuplicateEmployee;
import com.itdebug.demo.models.Employee;

import java.util.Collection;
import java.util.List;

public interface CosmosDAL {
    Collection<Employee> persistEmployee(List<Employee> models);
    Collection<DuplicateEmployee> persistDuplicateEmployee(List<DuplicateEmployee> models);

    List<Employee> getAllEmployees();
}
