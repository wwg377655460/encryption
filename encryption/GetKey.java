package com.save.util;

import java.util.UUID;

/**
 * Created by wsdevotion on 15/10/26.
 */
public class GetKey {

    public static String getKey(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        String [] str = s.split("-");
        s = "";
        for(String st : str){
            s = s + st;
        }
        System.out.println(s);
        return s;
    }
}
