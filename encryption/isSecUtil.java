package com.save.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by wsdevotion on 15/10/24.
 */
public class isSecUtil {

    public static JSONObject isSecMes(String json) {
        try {

            System.out.println(json);
            json = json.replace(" ", "+");//传输过程中RSA密文“+”变成了空格，再替换回来
            String result = SecUtil.decryptMax(Data.pri, json);
            result = desUtil.decrypt(result, Data.key);
//            result = result.substring(0, result.lastIndexOf("1"));//将后面的padding去掉
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
