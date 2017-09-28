package com.myblog.util;

/**
 * Created by Zephery on 2017/7/17.
 */
import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {


    /**
     * @Description
     * @param str
     * @return
     */
    public static String md5(String str){
        //Md5Hash
        return new Md5Hash(str).toString();
    }

    public static void main(String[] args) {
        String password="root";
        System.out.println(CryptographyUtil.md5(password));
    }
}