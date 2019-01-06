package com.example.mjclements.myapplication;

public class AuthenticateException extends Exception {
    public boolean exists;

    public AuthenticateException(String message, Boolean exists){
        super(message);
        this.exists = exists;
    }
}
