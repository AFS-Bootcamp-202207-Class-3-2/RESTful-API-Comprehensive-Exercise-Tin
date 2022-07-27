package com.rest.springbootemployee;

import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee update(int id, Employee employee) {
        Employee updatedEmployee = employeeRepository.findById(id);
        if(employee.getAge() != null){
            updatedEmployee.setAge(employee.getAge());
        }
        if(employee.getAge() != null){
            updatedEmployee.setSalary(employee.getSalary());
        }
        return updatedEmployee;
    }
}
