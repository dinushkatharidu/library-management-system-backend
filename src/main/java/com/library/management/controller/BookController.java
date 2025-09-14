package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable long bookId, @RequestBody Book bookDetails){
        return  bookService.updateBook(bookId, bookDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id){

        bookService.deleteById(id);
        return "Book Deleted Successfuly";
    }
}
