package com.nicolas.maven;

public class LamdbaTest {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Hello");
        }).start();
    }
}
