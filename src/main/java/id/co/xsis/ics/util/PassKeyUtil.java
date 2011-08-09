package com.xsis.ics.util;

import java.util.Random;

public class PassKeyUtil {
	/*
	 * Cipher ecipher; Cipher dcipher;
	 * 
	 * // 8-byte Salt byte[] salt = { (byte)0xA9, (byte)0x9B, (byte)0xC8,
	 * (byte)0x32, (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03 };
	 * 
	 * // Iteration count int iterationCount = 19;
	 * 
	 * public PassKeyUtil(String passPhrase) { try { // Create the key KeySpec
	 * keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
	 * SecretKey key = SecretKeyFactory.getInstance(
	 * "PBEWithMD5AndDES").generateSecret(keySpec); ecipher =
	 * Cipher.getInstance(key.getAlgorithm()); dcipher =
	 * Cipher.getInstance(key.getAlgorithm());
	 * 
	 * // Prepare the parameter to the ciphers AlgorithmParameterSpec paramSpec
	 * = new PBEParameterSpec(salt, iterationCount);
	 * 
	 * // Create the ciphers ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	 * dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec); } catch
	 * (java.security.InvalidAlgorithmParameterException e) { } catch
	 * (java.security.spec.InvalidKeySpecException e) { } catch
	 * (javax.crypto.NoSuchPaddingException e) { } catch
	 * (java.security.NoSuchAlgorithmException e) { } catch
	 * (java.security.InvalidKeyException e) { } }
	 * 
	 * public String encrypt(String str) throws java.io.IOException { try { //
	 * Encode the string into bytes using utf-8 byte[] utf8 =
	 * str.getBytes("UTF8");
	 * 
	 * // Encrypt byte[] enc = ecipher.doFinal(utf8);
	 * 
	 * // Encode bytes to base64 to get a string return new
	 * sun.misc.BASE64Encoder().encode(enc); } catch
	 * (javax.crypto.BadPaddingException e) { } catch (IllegalBlockSizeException
	 * e) { } catch (UnsupportedEncodingException e) { } return null; }
	 */

	/**
	 * @author Sofyan
	 */
	/**
	 * @param args
	 */
	public static String generateKey(){
		
		String temp[] = new String[6];
		String genKey = "";
		Random rnd = new Random();

		do{
			genKey = "";
			for (int i = 0; i < 6; i++) {
				temp[i] = new String();
				int itemp = rnd.nextInt(2881);
				if (itemp % 2 == 0)
					genKey += Integer.toString(rnd.nextInt(9));
				else {
					int x = rnd.nextInt(25) + 97;
					genKey += (char) x;
				}
			}
		}while (!genKey.matches("[^1IOo0l]*"));
		return genKey;
	}
	
	
}