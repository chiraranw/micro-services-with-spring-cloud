package com.chiraranw.msbookservice.control;

import com.chiraranw.msbookservice.model.Book;
import com.chiraranw.msbookservice.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("lib/v1/bk")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @RequestMapping(value = "/readby/{mbId}", method = RequestMethod.GET)
    public List<Book> getBkReadByMb(@PathVariable("mbId") Integer mbId) {
        return this.bookService.getBksReadByMb(mbId);
    }


    @RequestMapping(value = "/{bkId}", method = RequestMethod.GET)
    public Optional<Book> getBk(@PathVariable("bkId") Integer bkId) {
        return this.bookService.getBk(bkId);
    }
}
