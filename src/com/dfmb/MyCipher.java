package com.dfmb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
public class MyCipher {

	public static String enCipherClass(String path) throws Exception {
		File classFile = new File(path);
		if (!classFile.exists()) {
			System.out.println("File does not exist!");
			return null;
		}
		// File f = new File(path.getClass().getResource("/").getPath());
		// System.out.println("f path=" + f.getAbsolutePath());
		String cipheredClass = classFile.getParent() + File.separator
				+ "Cipher" + classFile.getName();
		System.out.println("enCipherClass() cipheredClass=" + cipheredClass);

		try {
			BufferedInputStream is = new BufferedInputStream(
					new FileInputStream(classFile));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int data = 0;
			while ((data = is.read()) != -1) {
				out.write(data);
			}
			out.flush();
			is.close();
			byte[] fileBytes = aesEncryptToBytes(out.toByteArray(), "ytmfdw");
			out.close();
			BufferedOutputStream fileOut = new BufferedOutputStream(
					new FileOutputStream(cipheredClass));
			fileOut.write(fileBytes);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return cipheredClass;
	}

	public static byte[] deCihperClass(InputStream input) {
		try (BufferedInputStream in = new BufferedInputStream(input);) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int data = 0;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			in.close();
			out.flush();
			out.close();

			return aesDecryptByBytes(out.toByteArray(), "ytmfdw");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] deCihperClass(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("deCihperClass() File:" + path + " not found!");
			return null;
		}

		System.out.println("deCihperClass() path=" + path);

		try (BufferedInputStream in = new BufferedInputStream(
				new FileInputStream(file));) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int data = 0;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			in.close();
			out.flush();
			out.close();

			return aesDecryptByBytes(out.toByteArray(), "ytmfdw");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(byte[] content, String encryptKey)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey()
				.getEncoded(), "AES"));

		return cipher.doFinal(content);
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static byte[] aesDecryptByBytes(byte[] encryptBytes,
			String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey()
				.getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return decryptBytes;
	}

	private static boolean isEmpty(String str) {

		return str == null || str.length() == 0;
	}
}
