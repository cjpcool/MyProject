package com.demo.utils;


public class JiaJieMiUtil {
	// MD5加码。32位

	/**
	 * 对MD5进行加密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 对md5进行解密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	// 测试主函数
	public static void main(String args[]) {
		// 0cc175b9c0f1b6a831c399e269772661
		String s = new String("a");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + MD5.md5(s));
		System.out.println("MD5后再加密：" + KL(MD5.md5(s)));
		System.out.println("解密为MD5后的：" + JM(KL(MD5.md5(s))));
	}
}