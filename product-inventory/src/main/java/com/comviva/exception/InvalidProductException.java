package com.comviva.exception;

public class InvalidProductException extends RuntimeException{
    public InvalidProductException(String message){
       super(message);
    }
}
