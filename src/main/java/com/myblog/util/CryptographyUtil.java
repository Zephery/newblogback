package com.myblog.util;

/**
 * Created by Zephery on 2017/7/17.
 */
import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {


    /**
     * @Description
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str,String salt){
        //Md5Hash
        return new Md5Hash(str, salt).toString();
    }

    public static void main(String[] args) {
        String password="aaa";

        System.out.println(CryptographyUtil.md5(password, "test"));
    }
}