package com.daou.common;

public class Logger {

    public static void write(Object...log){
        System.out.println("\n############ [Log(api) 시작] ###########");
        for(Object s : log){
            System.out.println(s);
        }
        System.out.println("############ [Log(api) 종료] ###########\n");
    }
}
