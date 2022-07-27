package com.rest.springbootemployee;

import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void clearEmployeeInRepository(){
        employeeRepository.clean();
    }

    @Test
    void should_get_all_employee_when_perform_get_given_employees() throws Exception{
        //given
        employeeRepository.insert(new Employee(1, "Sally", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("AAA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(8000));

        //then
    }

    @Test
    void should_create_a_new_employee_when_perform_post_given_a_new_employee() throws Exception {
        //given
        String newEmployeeJson ="{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 21,\n" +
                "        \"gender\": \"female\",\n" +
                "        \"salary\": 2000\n" +
                "    }";

        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployeeJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(2000));

        //then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(2000));
    }

    @Test
    void should_update_employee_when_perform_put_given_a_new_employee() throws Exception {
        //given
        employeeRepository.insert(new Employee(0, "Lisa", 22, "female", 10000));
        String updateEmployeeJson ="{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 66,\n" +
                "        \"gender\": \"female\",\n" +
                "        \"salary\": 2000\n" +
                "    }";

        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}",0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(66))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(2000));

        //then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(66));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(2000));
    }


    @Test
    void should_delete_employee_when_perform_put_given_a_employee() throws Exception {
        //given
        employeeRepository.insert(new Employee(0, "Lisa", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}",0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));

        //then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees, hasSize(0));
    }

    @Test
    void should_get_a_employee_when_perform_get_given_id() throws Exception{
        //given
        employeeRepository.insert(new Employee(0, "Sally", 22, "female", 10000));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}",0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));

        //then
    }
}
