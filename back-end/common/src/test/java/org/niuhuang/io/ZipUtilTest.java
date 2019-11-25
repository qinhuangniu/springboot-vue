package org.niuhuang.io;

import org.junit.jupiter.api.Test;

class ZipUtilTest {

	@Test
	void testZip1() throws Exception {
//		ZipUtil.zip("D:\\tmp\\eladmin");
		ZipUtil.zip("D:\\tmp\\tmp2\\eladmin\\dept.js");
//		Path p = Paths.get("D:\\tmp\\eladmin\\aa.txt");
//		System.out.println(p.subpath(p.getNameCount()-1, p.getNameCount()));
//		System.out.println(p.getName(0));
//		System.out.println(p.getRoot().toString());
//		System.out.println(p.getFileName().toString());
	
	}
	@Test
	void testZip2() throws Exception {
//		ZipUtil.zip("D:\\tmp\\tmp2\\eladmin.zip", "D:\\tmp\\eladmin");
		ZipUtil.zip("D:\\tmp\\gen.zip", "D:\\genSrc\\com");
	}
	
	@Test
	void testZip3() throws Exception {
		ZipUtil.zip("D:\\tmp\\tmp2\\eladmin\\eladmin.zip", "D:\\tmp\\tmp2\\eladmin");
	}
	
	@Test
	void testUnzip1() throws Exception {
//		ZipUtil.unzip("D:\\tmp\\tmp2\\eladmin.zip");
		ZipUtil.unzip("D:\\tmp\\gen.zip");
	}

}
