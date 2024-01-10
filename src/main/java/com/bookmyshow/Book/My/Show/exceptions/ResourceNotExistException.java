package com.bookmyshow.Book.My.Show.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;

public class ResourceNotExistException extends RuntimeException {
    public ResourceNotExistException(String message){
        super(message);
    }
}
