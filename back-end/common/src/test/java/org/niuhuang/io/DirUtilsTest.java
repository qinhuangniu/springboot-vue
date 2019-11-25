package org.niuhuang.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.niuhuang.io.DirUtils;

class DirUtilsTest {

	@Test
	void testMkdirs() throws IOException {
		DirUtils.mkdirs("D:/a/b/c");
	}

	@Test
	void testListFiles() throws IOException {
		Set<Path> paths = DirUtils.listFiles("D:\\work\\github\\springboot-vue\\back-end\\common", "*.xml");
		for (Path p : paths) {
			System.out.println(p);
		}
	}
	
	@Test
	void testDeepListFiles() throws IOException {
		Set<Path> paths = DirUtils.deepListFiles("D:\\work\\github\\springboot-vue\\back-end\\common");
		for (Path p : paths) {
			System.out.println(p.toString());
		}
	}
	
	@Test
	void testDeepListDirs() throws IOException {
		List<Path> paths = new ArrayList<Path>();
		DirUtils.deepListDirFiles(Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common"), paths);
		for (Path p : paths) {
			System.out.println(p.toString());
		}
	}
	
	@Test
	void testDeepListOnlyDir() throws IOException {
		List<Path> paths = new ArrayList<Path>();
		DirUtils.deepListOnlyDirs(Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common"), paths, "src", "doc");
		for (Path p : paths) {
			System.out.println(p.toString());
		}
		
	}
	
	@Test
	void testDeepListOnlyFiles() throws IOException {
		List<Path> paths = new ArrayList<Path>();
		DirUtils.deepListOnlyFiles(Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common"), paths, "*.java", "*.xml");
		for (Path p : paths) {
			System.out.println(p.toString());
		}
		
	}
	
	@Test
	void testDeepListSortFiles() throws IOException {
		List<Path> paths = new ArrayList<Path>();
		DirUtils.deepListSortFiles(Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common"), paths, FileSortUtil.FileAttrEnum.lastModifiedTime, false , "*.java", "*.xml");
		for (Path p : paths) {
			System.out.println(p.toString());
		}
		
	}
	
	@Test
	void test111( ) throws IOException {
		Files.list(Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common"))
        .forEach(System.out::println);
	}
	
}
