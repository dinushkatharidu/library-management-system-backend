package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId){

        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

    }

    public Book saveBook(Book newBook){
        return bookRepository.save(newBook);
    }

    
    public Book updateBook(long bookId, Book newBookDetails){
        Book currentBook = getBookById(bookId);

        currentBook.setTitle(newBookDetails.getTitle());
        currentBook.setAuthor(newBookDetails.getAuthor());
        currentBook.setIsbn(newBookDetails.getIsbn());
        currentBook.setPublisher(newBookDetails.getPublisher());
        currentBook.setQuantity(newBookDetails.getQuantity());
        currentBook.setAvailableQuantity(newBookDetails.getAvailableQuantity());
        currentBook.setPublicationYear(newBookDetails.getPublicationYear());

        return  bookRepository.save(currentBook);
    }


    public void deleteById(Long bookId){
        Book deletingBook = getBookById(bookId);

        bookRepository.delete(deletingBook);
    }
}
