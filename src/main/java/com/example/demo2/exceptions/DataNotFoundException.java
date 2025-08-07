package com.example.demo2.exceptions;

//DataNotFoundException dc ke thua tu Exception
public class DataNotFoundException extends Exception{
    public DataNotFoundException(String message){
        super(message);
    }
}
