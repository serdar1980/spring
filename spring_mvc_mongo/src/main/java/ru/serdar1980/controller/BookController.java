package ru.serdar1980.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.serdar1980.service.BookService;

@Controller
public class BookController {
    @Autowired
    BookService service;

    @RequestMapping("getall")
    public String getAll(Model model) {
        model.addAttribute("books", service.findAll());
        return "book";
    }
}
