package ru.serdar1980.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;
import ru.serdar1980.dto.authorDto;
import ru.serdar1980.service.AuthorService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("author")
public class AuthorController {
    @Autowired
    AuthorService service;

    @RequestMapping("getall")
    public String getAll(Model model) {
        model.addAttribute("authors", service.findAll());
        return "author";
    }

    @RequestMapping("edit/{id}")
    public String getbyId(Model model, @PathVariable("id") String id) {
        if ("-1".equals(id)) {
            Author author = new Author();
            model.addAttribute("author", author);
        } else {
            model.addAttribute("author", service.findById(id));
        }
        return "eauthor";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute(value = "author") authorDto authorDto) {

        Author author = Author.of().setFio(authorDto.getFio())
                .setBooks(
                        Stream.of(authorDto.getBooks().split("\n"))
                                .map(Book::new)
                                .collect(Collectors.toList()
                                )
                );
        service.insert(author);
        return "author";
    }

    @RequestMapping("delete/{id}")
    public String delete(Model model, @PathVariable("id") String id) {
        model.addAttribute("authors", service.findAll());
        return "author";
    }


}
