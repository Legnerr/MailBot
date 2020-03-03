package com.mail;

public class Main {
    private static EmailTLSSender emailTLSSender = new EmailTLSSender("...@gmail.com", "...");

    public static void main(String[] args) {
        emailTLSSender.send("Test", "Test", "...@gmail.com");

    }
}
