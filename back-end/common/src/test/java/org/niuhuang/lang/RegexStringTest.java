package org.niuhuang.lang;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegexStringTest {

	@Test
	void testMatchStar1() {
		boolean b = RegexString.matchStar("adfbsc.java", "*.java");
		assertTrue(b);
	}
	
	@Test
	void testMatchStar2() {
		boolean b = RegexString.matchStar("abooksc.java", "*book*");
		assertTrue(b);
	}
	
	@Test
	void testMatchStar3() {
		boolean b = RegexString.matchStar("abooksc.java", "abook*");
		assertTrue(b);
	}
	
	@Test
	void testFindGroupDemo() {
		RegexString.findGroupDemo();
	}
	
	@Test
	void testMatchGroupNameDemo() {
		RegexString.matchGroupNameDemo();
	}
	
	@Test
	void testMatchGroupDemo() {
		RegexString.matchGroupDemo();
	}
	
	@Test
	void testReplaceGroupDemo() {
		RegexString.replaceGroupDemo();
	}
	
	@Test
	void testReplaceTailDemo() {
		RegexString.replaceTailDemo();
	}
	
	@Test
	void testRegexNum() {
		RegexString.regexNum();
	}
	
	
	
	
	
	

}
