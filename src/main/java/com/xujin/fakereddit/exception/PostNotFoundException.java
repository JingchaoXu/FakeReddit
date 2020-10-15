package com.xujin.fakereddit.exception;

public class PostNotFoundException  extends  RuntimeException{

    public PostNotFoundException(String exception){
        super(exception);
    }
}
