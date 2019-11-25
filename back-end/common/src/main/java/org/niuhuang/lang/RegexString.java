package org.niuhuang.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexString {

	/**
	 * 在指定的字符串中查找指定的filter字符串，可以用*替代任何字符， 如果匹配返回true。
	 * 
	 * @param src    原字符串
	 * @param filter 匹配条件可以用*abc*bbb
	 * @return 如果匹配返回true
	 */
	public static boolean matchStar(String src, String filter) {
		// 将字符*替换成正则表达式.*
		String regex = filter.replace("*", ".*");
		// 加上匹配起始，终止正则表达式
		regex = "^" + regex + "$";
		// check是否匹配，如果匹配返回true。
		return src.matches(regex);
	}

	/**
	 * Matcher.find是部分匹配，可以不匹配aa 分组匹配是以（）来分组， group(0)是整个正则表达式匹配
	 * group(1)是匹配第一个分组，以此类推
	 */
	public static void findGroupDemo() {
		String patternStr = "(\\d{8})\\.(\\w*)\\.(\\w*)\\.(create|update)\\.(.*)$";
		String testStr1 = "aa20190317.cluster.index.create.yml";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(testStr1);
		if (m.find()) {
			System.out.println(m.group(0)); // 20190317.cluster.index.create.yml
			System.out.println(m.group(1)); // 20190317
			System.out.println(m.group(2)); // cluster
			System.out.println(m.group(3)); // index
		}
	}

	/**
	 * Matcher.matches是全部正则表达式匹配，testStr2就不会匹配
	 */
	public static void matchGroupDemo() {
		String patternStr = "^(\\d{8})\\.(\\w*)\\.(\\w*)\\.(create|update)\\.(.*)$";
		String testStr1 = "20190317.cluster.index.create.yml";
		String testStr2 = "aa20190317.cluster.index.create.yml";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(testStr1);
		if (m.matches()) {
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
	}

	/**
	 * (?<date>\\d{8})date是分组命名
	 * 
	 */
	public static void matchGroupNameDemo() {
		String patternStr = "^(?<date>\\d{8})\\.(?<cluster>\\w*)\\.(?<index>\\w*)\\.(?<operation>create|update)\\.(?<ohter>.*)$";
		String testStr1 = "20190317.cluster.index.create.yml";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(testStr1);
		if (m.matches()) {
			System.out.println(m.group(0));
			System.out.println(m.group("date"));
			System.out.println(m.group("cluster"));
			System.out.println(m.group("operation"));
			System.out.println(m.group("other"));
		}
	}

	public static void replaceGroupDemo() {
		String patternStr = "^(\\d{8})\\.(\\w*)\\.(\\w*)\\.(create|update)\\.(.*)$";
		String testStr1 = "20190317.cluster.index.create.yml";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(testStr1);
		// 替换日期20190317 -> 20190401
		if (m.matches()) {
			System.out.print("20190401" + "." + m.group(2) + "." + m.group(3) + "." + m.group(4) + "." + m.group(5));
		}
	}

	public static void replaceTailDemo() {
		String REGEX = "a*b";
		String INPUT = "aabhxabhxbhx^^!";
		String REPLACE = "-";
		// 生成 Pattern 对象并且编译一个简单的正则表达式"a*b"->匹配0到多个的a和b的组合，例aab、ab、b都可匹配到
		Pattern p = Pattern.compile(REGEX);
		// 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
		Matcher m = p.matcher(INPUT);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			// aab、ab、b被替换为-,并且将最后匹配到之前的子串（-hx-hx-）都添加到sb对象中，此时sb为-hx-hx-
			m.appendReplacement(sb, REPLACE);
		}
		// 将最后匹配到后面的子串（hx^^!）添加到sb对象中，此时sb为-hx-hx-hx^^!
		m.appendTail(sb);
		System.out.println(sb.toString());

	}
	
	/**
	 *  \1 指再次匹配第一个组匹配到的内容
	 */
	public static void regexNum() {
//		String patternStr = "(\\d+)\\w+\\1";			// 123abc123
		 String patternStr = "(\\d+)(\\w+)\\1\\2";	// 123abc123abc
		String testStr1 = "123abc123abc";				// 这个会匹配 
//		String testStr1 = "123abc124abc";				// 这个不会匹配 123 ！= 124
		String testStr2 = "123abc123123";	
		 String patternStr2 = "(\\d+)(\\w+)\\1{1}";
		Pattern p = Pattern.compile(patternStr2);
		Matcher m = p.matcher(testStr2);
		if (m.find()) {
			System.out.print(m.group(0)); 
		}
	}
	
}
