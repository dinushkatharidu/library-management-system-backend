package com.library.management.repository;

import com.library.management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    public Book save(Book book);
    public List<Book> findAll();
    public Book findBookById(Long id);
    public void deleteById(Book deletingBook );
    public Book updateBook(long bookId, Book newBookDetails);
}
