package com.nicolas.maven;

import java.util.Properties;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //JAVA 訪問系統屬性
        Properties properties = System.getProperties();
        Set<Object> porNameSet = properties.keySet();
        porNameSet.forEach(System.out::println);
        new Thread(() -> {
            System.out.println("HEllo");
        });
    }
}