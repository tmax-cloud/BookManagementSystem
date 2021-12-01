package org.tmaxcloud.sampleapp.book.bookmanagementsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tmaxcloud.sampleapp.book.bookmanagementsystem.Book;


public interface BookRepository extends JpaRepository<Book, Long> {

}
