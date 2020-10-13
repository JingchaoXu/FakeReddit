package com.xujin.fakereddit.exception;

public class SpringRedditException extends RuntimeException{

    //In Restful API, exceptions are very common, so we have to define our own exception for detail actions
    public SpringRedditException(String exMessage){
        super(exMessage);
    }
}
