package com.g7.ercdataservice.exception;

public class ProposalOngoingException extends RuntimeException{
    public ProposalOngoingException(String message) {
        super(message);
    }

    public ProposalOngoingException() {
    }
}
