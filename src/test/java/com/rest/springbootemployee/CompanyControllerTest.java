package com.rest.springbootemployee;

import com.rest.springbootemployee.pojo.Company;
import com.rest.springbootemployee.pojo.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    MockMvc client;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void clearEmployeeInRepository(){
        companyRepository.clean();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_a_company_when_perform_get_given_id() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeList[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

    @Test
    void should_get_employee_list_when_perform_get_given_company_id() throws Exception{
        //given
        ArrayList<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Sally", 22, "female", 10000));
            add(new Employee(1, "Lily", 26, "female", 5000));
        }};
        companyRepository.insert(new Company(1, "OOCL", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", 0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Sally","Lily")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(22, 26)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("female"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(10000, 5000)));

        //then
    }

}
