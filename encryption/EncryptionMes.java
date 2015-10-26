package com.save.util;

/**
 * Created by wsdevotion on 15/10/26.
 */
public class EncryptionMes {

    public static String encrytionMes(String mes){
        String result = "";
        try {
            result = desUtil.encrypt(mes, Data.key);//DES加密
            result = SecUtil.encryptMax(Data.pub, result);//RSA加密
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
