package org.niuhuang.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
	public static String getFileEncode(BufferedInputStream in) throws IOException {
		int p = (in.read() << 8) + in.read();
		String code = null;
		
		switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
		}
		return code;
	}
	
	public static String getTmpPath() {
		return System.getProperty("java.io.tmpdir");
	}
	
	public static int getRootPathIdx(Path path) {
		int endPathIdx = path.getNameCount();
		int rootPathIdx = endPathIdx - 1;
		Path rootPath = path.subpath(rootPathIdx, endPathIdx);
		while(!Files.isDirectory(rootPath)
				&& rootPathIdx>=0) {
			rootPathIdx -- ;
			rootPath = path.subpath(rootPathIdx, endPathIdx);
		}
		return rootPathIdx;
	}
	
	public static String getRootPath(Path path) {
		int endPathIdx = path.getNameCount();
		int rootPathIdx = endPathIdx - 1;
		Path rootPath = path.subpath(rootPathIdx, endPathIdx);
		return System.getProperty("java.io.tmpdir");
	}
	
	
	
}
