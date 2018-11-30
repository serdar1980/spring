package ru.serdar1980.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.serdar1980.controller.AuthorController;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest extends BaseControllerTest {
    @Autowired
    private AuthorController controller;

    @Autowired
    private AuthorService service;

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

    @Test
    public void getModelForAddNew() throws Exception {
        mockMvc.perform(get("/author/edit/-1").
                accept(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE)))
                .andExpect(status().isOk());
    }

    @Test
    public void getModelForEdit() throws Exception {
        List<Book> books = new ArrayList<>(Arrays.asList(Book.of().setName("Test")));
        Author author = service.insert(Author.of().setFio("Test").setBooks(books));
        mockMvc.perform(get("/author/edit/" + author.getId()).
                accept(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE)))
                .andExpect(status().isOk());
    }


    @Test
    public void getModelForDelete() throws Exception {
        mockMvc.perform(get("/author/delete/1").
                accept(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE)))
                .andExpect(status().is(302));
    }

}
