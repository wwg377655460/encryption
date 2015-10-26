package com.save.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by wsdevotion on 15/10/24.
 */
public class desUtil {


    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * 生成密钥key对象
     *
     * @param KeyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        //对data进行填充
//        int num = desUtil.length(data)%8;
//        num = 8 - num;
//        if(num!=0) {
//            data = data + "1";
//            while (num > 1) {
//                data = data + "0";
//                num--;
//            }
//        }else{
//            data = data + "10000000";
//        }
        System.out.print(data);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecureRandom random = new SecureRandom();
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        byte[] results = cipher.doFinal(data.getBytes());
        // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
//        for (int i = 0; i < results.length; i++) {
//            System.out.print(results[i] + " ");
//        }
        System.out.println();
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.decodeBase64(data)));
    }

    public static void main(String[] args) throws Exception {
        String source = "{\"username\":\"洒出123\"}";
        System.out.println("原文: " + source);
        String key = "A1B2C3D4E5F60708";
        String encryptData = encrypt(source, key);
        String s1 = "Muy3AUvwiqSBTW7P4KdmqrDC5GJXnCpfPfdPN3AXV19HgXriHt8e/3x7LKVsgkgg5ImFzWkPX+Po\n" +
                "8fVT5+dGqVHcUiQp07qsy+8KHYwFuBwSvPysv7ZpwT9RKR79YfOFlciqHCQUsKkc485OEpRa4RkX\n" +
                "6mlEco/0YZil4OyP/T7JBJRgZ0/gbK3G7jnJ9PQ2b+fERCys91n2e27b2x06Iu7uu0OGpAROEMYk\n" +
                "YYBCYOA=";
        String s2 = "Muy3AUvwiqSBTW7P4KdmqrDC5GJXnCpfPfdPN3AXV19HgXriHt8e/3x7LKVsgkgg5ImFzWkPX+Po\n" +
                "8fVT5+dGqVHcUiQp07qsy+8KHYwFuBwSvPysv7ZpwT9RKR79YfOFlciqHCQUsKkc485OEpRa4RkX\n" +
                "6mlEco/0YZil4OyP/T7JBJRgZ0/gbK3G7jnJ9PQ2b+fERCys91n2e27b2x06Iu7uu0OGpAROEMYk\n" +
                "YYBCYOA=\n";
        System.out.println("加密后: " + encryptData);
//        String decryptData = decrypt(s1, key);
//        System.out.println("解密后: " + decryptData);
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 3;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

}
