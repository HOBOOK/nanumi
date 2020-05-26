package com.daou.common;

public class Logger {

    public static void write(String...log){
        System.out.println("############ [Log(api) 시작] ###########");
        for(String s : log){
            System.out.println(s);
        }
        System.out.println("############ [Log(api) 종료] ###########");
    }
}
