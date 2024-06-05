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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @Sql("/sql/client.sql")
    public void testGetAllClients() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    @Sql("/sql/client.sql")
    public void testCreateClient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {              
                                "name": "string",
                                "shortName": "string",
                                "address": "string",
                                "legalForm": "string"
                             
                        }"""))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {              
                                "name": "string",
                                "shortName": "string",
                                "address": "string",
                                "legalForm": "string"
                             
                        }"""));


    }

    @Test
    @Sql("/sql/client.sql")
    public void testGetClientById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


    }

    @Test
    @Sql("/sql/client.sql")
    public void testUpdateClient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {              
                                "name": "string",
                                "shortName": "string",
                                "address": "string",
                                "legalForm": "string"
                             
                        }"""))

                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {       
                                "id": 1,
                                "name": "string",
                                "shortName": "string",
                                "address": "string",
                                "legalForm": "string"
                             
                        }""")
                                );


    }

    @Test
    @Sql("/sql/client.sql")
    public void testDeleteClient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/1"))
                .andDo(print())
                .andExpectAll(MockMvcResultMatchers.status().isNoContent());


    }





}
