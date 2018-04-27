package com.demo.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 陈建朋
 *
 */
public class GenUtil {
	
	/**
	 * 检查管理员密码是否安全
	 * @param pwd
	 * @return
	 */
	public static boolean pwdSecury(String pwd){
		//任意字符匹配8到11位
		Pattern t = Pattern.compile(".{8,16}");
		//不能全是数字
		Pattern w = Pattern.compile("^[\\d]*$");
		// 不能全部相同
		boolean flag = false;
		for(int i = 0; i < pwd.length()-1; i++){
			if(pwd.charAt(i) != pwd.charAt(i+1)){
				flag = true;
				break;
			}
		}
		if(t.matcher(pwd).matches() && !w.matcher(pwd).matches() && flag == true){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 獲取圖片隨機名字
	 * @return
	 */
	public static String genImgName() {
		String name = UUID.randomUUID().toString();
		return name;
	}

	/**
	 * 獲取Id隨機名字
	 * @return
	 */
	public static String genId() {
		int rand = new Random().nextInt(899) + 100;
		long time = new Date().getTime();
		return time + "" + rand;
	}

	/**
	 * 将单词转换为可正则的单词形式
	 * @param vocab
	 * @return 返回这个新的正则字符
	 */
	public static String regexText(String vocab) {
		String regex="";
		if(vocab.length()>3){
			//去掉末尾两位,并在后可接6位任意字符(最长如call=>calling最短如fulies=>full)
			regex = "^"+vocab.substring(0, vocab.length()-2)+"[a-zA-Z]{0,6}$";
		}else{
			regex = "^"+vocab+"[a-zA-Z]{0,6}$";
		}
		return regex;
	}

	/**
	 * 檢測該單詞是否為英文
	 * @param vocab
	 * @return 是TRUE  不是FALSE
	 */
	public static boolean isVocab(String vocab){
		boolean flag = true;
		for(int i = 0; i < vocab.length(); i++){
			if(!(vocab.charAt(i) >='a' && vocab.charAt(i)<='z') && !(vocab.charAt(i) >='A' && vocab.charAt(i)<='Z')){
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 檢測該例句是否含有相對應的單詞
	 * 例句前面可以有多个非字母的东西, 可以多种形式
	 * @param example 例句
	 * @param vocab 該例句的單詞
	 * @return 含有返回 true , 否则返回 false
	 */
	public static boolean hasThisVocab(String example, String vocab){
		String regex = GenUtil.regexText(vocab);
		//regex = "[\\s.,\\d]*[^a-zA-Z]"+regex;
		//创建忽视大小写的正则表达式
		Pattern pattern =Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(example);
		
		//判断例句是否含有该单词或其其他形式
		if(matcher.find()){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean emaileCheck(String email) {
		Pattern p = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	/**
	 * 生成11位的管理员Id
	 * @return
	 */
	public static long getMgrId() {
		Random rand = new Random();
		int a = rand.nextInt(899)+100;
		int b = rand.nextInt(8999)+1000;
		int c = rand.nextInt(8999)+1000;
		return Long.parseLong(""+a+b+c);
	}
}
