package ru.serdar1980.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.serdar1980.controller.AuthorController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest extends BaseControllerTest {
    @Autowired
    private AuthorController controller;


    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getModelForGetAll() throws Exception {
        mockMvc.perform(get("/author/getall").
                accept(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE)))
                .andExpect(status().isOk());
    }

}
