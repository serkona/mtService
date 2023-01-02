package com.example.mtservice;


import com.example.mtservice.data.BalanceRepo;
import com.example.mtservice.data.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MtControllerTest {

    @Autowired
    BalanceRepo balanceRepo;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getNotFoundUserTest() throws Exception {
        long id = 0L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getBalance").param("id", String.valueOf(id))).andExpect(status().isNotFound());
    }

    @Test
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserTest() throws Exception {
        long id = 0L;
        balanceRepo.save(new Account(id, 0L));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getBalance").param("id", String.valueOf(id))).andExpect(status().isOk());

    }

    @Test
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changeBalanceTest() throws Exception {
        balanceRepo.save(new Account(0L, 0L));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "0");
        params.add("amount", "1");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/changeBalance").params(params)).andExpect(status().isOk());

    }

    @Test
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM ACCOUNTS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changeNewBalanceTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "0");
        params.add("amount", "1");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/changeBalance").params(params)).andExpect(status().isCreated());
    }


}
