package com.sommor.usercenter.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wuyu
 * @since 2019/2/1
 */
public class Encryption {

    public static String hmacsha256(String data, String key) {
        return hmacsha256(data.getBytes(), key.getBytes());
    }

    public static String hmacsha256(byte[] data, byte[] key) {
        try  {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String byte2hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (int n = 0; b!=null && n < b.length; n++) {
            s = Integer.toHexString(b[n] & 0XFF);
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密出现错误");
        }
    }
}
