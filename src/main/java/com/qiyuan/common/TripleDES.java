package com.qiyuan.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 3DES加密工具类
 */
public class TripleDES {
    // 密钥 长度不得小于24
    private final static String secretKey = "123456789012345678901234" ;
    // 向量 可有可无 终端后台也要约定
//    private final static String iv = "";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText
     *            普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey .getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede");
        deskey = keyfactory.generateSecret( spec);

        Cipher cipher = Cipher.getInstance( "desede/ECB/PKCS5Padding");
//        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher.init(Cipher. ENCRYPT_MODE, deskey);
        byte[] encryptData = cipher.doFinal( plainText.getBytes( encoding));
        return Base64. encode( encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
//        String encodeStr = TripleDES. encode( encryptText);
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec( secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory. generateSecret( spec);
        Cipher cipher = Cipher.getInstance( "desede/ECB/PKCS5Padding" );
//        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher. init(Cipher. DECRYPT_MODE, deskey);
        byte[] decryptData = cipher. doFinal(Base64.decode(encryptText));

        return new String( decryptData, encoding);
    }


    /***
     * SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            return  Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  "";
    }

    public static void main(String args[]) throws Exception{
        String str = "123123" ;
        System. out.println( "----加密前-----：" + str );
        String encodeStr = TripleDES. getSHA256Str( str);
        System. out.println( "----加密后-----：" + encodeStr.length() );
        System. out.println( "----解密后-----：" + TripleDES.decode( encodeStr));
        String aa="96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e";
        System.out.println(aa.length());
    }
}