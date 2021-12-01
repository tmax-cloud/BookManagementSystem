package org.tmaxcloud.sampleapp.book.bookmanagementsystem;

public class BookNotFoundException extends RuntimeException {
    BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}