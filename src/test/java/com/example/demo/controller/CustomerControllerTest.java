package com.example.demo.controller;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.CustomerCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @Test
  void searchCustomers_withPagination_returnsPageMetadata() throws Exception {
    mockMvc.perform(get("/api/customers/search")
            .param("q", "example.com")
            .param("page", "0")
            .param("size", "2")
            .param("sort", "name,asc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.page", is(0)))
        .andExpect(jsonPath("$.size", is(2)))
        .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
  }

 

@Test
  void createCustomer_thenFetchById() throws Exception {
    CustomerCreateRequest req = new CustomerCreateRequest();
    req.setName("Daisy");
    req.setEmail("daisy@example.com");

    String body = objectMapper.writeValueAsString(req);

    String createdJson = mockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", is("Daisy")))
        .andReturn()
        .getResponse()
        .getContentAsString();

    Long id = objectMapper.readTree(createdJson).get("id").asLong();

    mockMvc.perform(get("/api/customers/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", is("daisy@example.com")));
  }
}
