package com.save.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.save.util.*;
import com.save.util.GetKey;
import com.save.util.desUtil;
import org.junit.Test;

/**
 * Created by wsdevotion on 15/10/15.
 */
public class UserTest {



    @Test
    public void login() {
        JSONObject json = new JSONObject();
        json.put("username", "wer");
        json.put("password", "123");

        String result = "";
        String cryptionKey = "";
        String key = GetKey.getKey();//产生随机字符串
        try {
            result = desUtil.encrypt(json.toString(), key);//用DES加密
            cryptionKey = SecUtil.encrypt(Data.pub, key);//用RSA对密钥进行加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = result + "|" + cryptionKey;
        String url = "http://localhost:8080/login";

        String param = "json=" + result;
        System.out.println(param);
        String s = HttpRequest.sendPost(url, param);
        System.out.println(s);
    }

}
