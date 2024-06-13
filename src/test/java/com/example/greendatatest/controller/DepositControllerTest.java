package com.example.greendatatest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class DepositControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql({"/sql/bank.sql","/sql/client.sql", "/sql/deposit.sql"})
    public void testGetAllDeposit() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/deposits"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id":1,
                                        "bankId":1,
                                        "clientId":1,
                                        "openingDate":"2024-06-05T18:22:33.243Z",
                                        "percent":10.0,
                                        "termInMonths":3
                                    },
                                    {
                                        "id":2,
                                        "bankId":2,
                                        "clientId":1,
                                        "openingDate":"2024-06-05T18:22:33.243Z",
                                        "percent":10.0,
                                        "termInMonths":3
                                    },
                                    {
                                        "id":3,
                                        "bankId":3,
                                        "clientId":2,
                                        "openingDate":"2024-06-05T18:22:33.243Z",
                                        "percent":12.0,
                                        "termInMonths":5
                                    }
                                ]
                        """));

    }

    @Test
    @Sql({"/sql/bank.sql","/sql/client.sql"})
    public void testCreateDeposit() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/deposits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {              
                                
                                  "bankId":1,
                                  "clientId":1,
                                  "openingDate":"2024-06-05T18:22:33.243Z",
                                  "percent":10.0,
                                  "termInMonths":3
                             
                        }"""))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {              
                                 "bankId":1,
                                 "clientId":1,
                                 "openingDate":"2024-06-05T18:22:33.243Z",
                                 "percent":10.0,
                                 "termInMonths":3
                             
                        }"""));


    }

    @Test
    @Sql({"/sql/bank.sql","/sql/client.sql", "/sql/deposit.sql"})
    public void testGetDepositById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/deposits/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                            "bankId":1,
                            "clientId":1,
                            "openingDate":"2024-06-05T18:22:33.243Z",
                            "percent":10.0,
                            "termInMonths":3
                        }
                        """));

    }

    @Test
    @Sql({"/sql/bank.sql","/sql/client.sql", "/sql/deposit.sql"})
    public void testUpdateDeposit() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/deposits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {   
                            "bankId":1,           
                            "clientId":1,
                            "openingDate":"2024-06-05T18:22:33.243Z",
                            "percent":12.0,
                            "termInMonths":3
                        }"""))

                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {       
                             "id": 1,
                            "bankId":1,           
                            "clientId":1,
                            "openingDate":"2024-06-05T18:22:33.243Z",
                            "percent":12.0,
                            "termInMonths":3
                        }""")
                );

    }

    @Test
    @Sql({"/sql/bank.sql","/sql/client.sql", "/sql/deposit.sql"})
    public void testDeleteDeposit() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deposits/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isNoContent());

    }
}
