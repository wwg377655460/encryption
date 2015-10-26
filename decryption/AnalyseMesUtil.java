package com.save.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by wsdevotion on 15/10/26.
 */
public class AnalyseMesUtil {

    public static JSONObject analyseMes(String json){
        json = json.replace(" ", "+");//传输过程中RSA密文“+”变成了空格，再替换回来
        String [] mes = json.split("[|]");//分析字符串
        String key = SecUtil.decryptMax(Data.pri, mes[1]);//解密密钥
        String result = null;
        try {
            result = desUtil.decrypt(mes[0], key);//用key对数据进行解密
        } catch (Exception e) {
            e.printStackTrace();
        }
//            result = result.substring(0, result.lastIndexOf("1"));//将后面的padding去掉
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }
}
