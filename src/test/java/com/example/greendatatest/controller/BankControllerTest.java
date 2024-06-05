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
public class BankControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/bank.sql")
    public void testGetAllBanks() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/banks"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id":1,
                                        "name":"банк1",
                                        "bik":"бик1"
                                    },
                                    {
                                        "id":2,
                                        "name":"банк2",
                                        "bik":"бик2"
                                    },
                                    {
                                        "id":3,
                                        "name":"банк3",
                                        "bik":"бик3"
                                        }
                                ]
                        """));

    }

    @Test
    @Sql("/sql/bank.sql")
    public void testCreateBank() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {              
                                "name": "string",
                                "bik": "string"
          
                        }"""))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {              
                                "name": "string",
                                "bik": "string"
               
                        }
                        """));

    }

    @Test
    @Sql("/sql/bank.sql")
    public void testGetBankById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/banks/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                            "id":1,
                            "name":"банк1",
                            "bik":"бик1"
                        }
                        """));

    }

    @Test
    @Sql("/sql/bank.sql")
    public void testUpdateBank() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/banks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {              
                                "name": "string",
                                "bik": "string" 
                        }"""))

                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {       
                                "id": 1,
                                "name": "string",
                                "bik": "string"
                        }""")
                );

    }

    @Test
    @Sql("/sql/bank.sql")
    public void testDeleteBank() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/banks/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isNoContent());

    }


}
