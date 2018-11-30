package ru.serdar1980.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.serdar1980.domain.Author;
import ru.serdar1980.domain.Book;
import ru.serdar1980.dto.BookDto;
import ru.serdar1980.service.BookService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    BookService service;

    @RequestMapping("getall")
    public String getAll(Model model) {
        model.addAttribute("books", service.findAll());
        return "book";
    }

    @RequestMapping("edit/{id}")
    public String getbyId(Model model, @PathVariable("id") String id) {
        if ("-1".equals(id)) {
            Book book = Book.of();
            model.addAttribute("book", book);
        } else {
            model.addAttribute("book", service.findById(id));
        }
        return "ebook";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute(value = "book") BookDto bookDto) {
        if (StringUtils.isEmpty(bookDto.getId())) {
            Book book = Book.of().setName(bookDto.getName())
                    .setAuthors(
                            Stream.of(
                                    bookDto.getBooks().split("\n"))
                                    .map(Author::new)
                                    .collect(Collectors.toList()
                                    )
                    );
            service.insert(book);
        } else {
            Book book = service.findById(bookDto.getId());
            book.setName(bookDto.getName());
            book.setAuthors(Stream.of(
                    bookDto.getBooks().split("\n"))
                    .map(Author::new)
                    .collect(Collectors.toList()
                    )
            );
            service.update(book);
        }
        return "redirect:/author/getall";
    }

    @RequestMapping("delete/{id}")
    public String delete(Model model, @PathVariable("id") String id) {
        service.delete(service.findById(id));
        return "redirect:/author/getall";
    }

}
