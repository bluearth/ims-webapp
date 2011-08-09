package com.xsis.security.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Md5Encryptor{
	
	public static String getMd5Digest(String input)
    {
		Md5PasswordEncoder md5encode = new Md5PasswordEncoder();
		return md5encode.encodePassword(input, null);
//        try
//        {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(input.getBytes());
//            BigInteger number = new BigInteger(1,messageDigest);
//            return number.toString(16);
//        }
//        catch(NoSuchAlgorithmException e)
//        {
//            throw new RuntimeException(e);
//        }
    }
	
	public static void main(String[] args){
		String input = "a";
		String encrypt = getMd5Digest(input);
		System.out.println(encrypt);
		System.out.println(encrypt.length());
	}

}
