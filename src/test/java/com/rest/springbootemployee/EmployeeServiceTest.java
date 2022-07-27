package com.rest.springbootemployee;


import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        ArrayList<Employee> preparedEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee(1, "Susan", 23, "female", 10000);
        Employee secondEmployee = new Employee(1, "Mathew", 25, "male", 12000);
        preparedEmployees.add(firstEmployee);
        preparedEmployees.add(secondEmployee);
        given(employeeRepository.findAll()).willReturn(preparedEmployees);

        //when
        List<Employee> employees = employeeService.findAll();

        //then
        assertEquals(2, employees.size());
        assertEquals(firstEmployee, employees.get(0));
    }

    @Test
    void should_update_only_age_and_salary_when_update_given_employee() {
        //given
        Employee employeeToUpdate = new Employee(1, "Susan", 23, "female", 10000);
        Employee employeeInUpdateRequest = new Employee(1, "Mathew", 25, "male", 12000);
        given(employeeRepository.findById(1)).willReturn(employeeToUpdate);

        //when
        Employee employee = employeeService.update(1, employeeInUpdateRequest);

        //then
        assertEquals(employee.getId(), 1);
        assertEquals(employee.getName(), "Susan");
        assertEquals(employee.getAge(), 25);
        assertEquals(employee.getGender(), "female");
        assertEquals(employee.getSalary(), 12000);
    }

    @Test
    void should_a_new_employee_when_create_given_employee() {
        //given
        Employee employeeToCreate = new Employee(1, "Susan", 23, "female", 10000);
        given(employeeRepository.insert(employeeToCreate)).willReturn(employeeToCreate);

        //when
        Employee employee = employeeService.create(employeeToCreate);

        //then
        assertEquals(employee.getId(), 1);
        assertEquals(employee.getName(), "Susan");
        assertEquals(employee.getAge(), 23);
        assertEquals(employee.getGender(), "female");
        assertEquals(employee.getSalary(), 10000);
    }

    @Test
    void should_a_employee_when_delete_given_id() {
        //given
        Employee employee= new Employee(1, "Susan", 23, "female", 10000);
        given(employeeRepository.delete(1)).willReturn(employee);

        //when
        Employee deletedEmployee = employeeService.delete(1);

        //then
        assertEquals(deletedEmployee.getId(), 1);
        assertEquals(deletedEmployee.getName(), "Susan");
        assertEquals(deletedEmployee.getAge(), 23);
        assertEquals(deletedEmployee.getGender(), "female");
        assertEquals(deletedEmployee.getSalary(), 10000);
    }


    @Test
    void should_get_a_employee_when_find_given_id() {
        //given
        Employee employee = new Employee(1, "Susan", 23, "female", 10000);
        given(employeeRepository.findById(1)).willReturn(employee);

        //when
        Employee employeeById = employeeService.findById(1);

        //then
        assertEquals(employeeById.getId(), 1);
        assertEquals(employeeById.getName(), "Susan");
        assertEquals(employeeById.getAge(), 23);
        assertEquals(employeeById.getGender(), "female");
        assertEquals(employeeById.getSalary(), 10000);
    }

}
