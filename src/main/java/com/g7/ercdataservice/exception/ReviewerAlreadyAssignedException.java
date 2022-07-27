package com.g7.ercdataservice.exception;

public class ReviewerAlreadyAssignedException extends RuntimeException{
    public ReviewerAlreadyAssignedException(String message) {
        super(message);
    }
}
