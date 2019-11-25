package org.niuhuang.io;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class DirUtils {
	
	/**
	 * 创建多级目录
	 * @param path 目录路径 例如 "/home/a/b/c"
	 * @throws IOException 
	 */
	public static void mkdirs(String folder) throws IOException {
		Path folderpath = Paths.get(folder);
		Files.createDirectories(folderpath);
	}
	
	/**
	 * 遍历当前定目录folder下的 指定文件名过滤器fileNameFileter的所有文件
	 * @param folder	
	 * @param fileNameFileter 可以是*.{java,txt,bat}
	 *                        可以是正则表达式  *.[xX][mM][lL] 
	 * @throws IOException
	 */
	public static Set<Path> listFiles(String folder, String fileName) throws IOException {
		DirectoryStream<Path> dirStream = null;
		try {
			Path path = Paths.get(folder);
			// *.xml的正则表达式 *.[xX][mM][lL]
			
			if(StringUtils.isEmpty(fileName)) {
				dirStream = Files.newDirectoryStream(path);
			} else {
				dirStream = Files.newDirectoryStream(path, fileName);
			}
			Set<Path> paths = new HashSet<Path>();
			for (Path p : dirStream) {
				paths.add(p);
			}
			
			return paths;
		} finally {
			if(dirStream!=null) {
				dirStream.close();
			}
			
		}
	
	}
	
	/**
	 * 递归遍历指定目录folder下的 所有文件夹的所有文件
	 * @param folder	
	 * @throws IOException
	 */
	public static Set<Path> deepListFiles(String folder) throws IOException {
		DirectoryStream<Path> dirStream = null;
		try {
			Path path = Paths.get(folder);
			dirStream = Files.newDirectoryStream(path);
			Set<Path> paths = new HashSet<Path>();
			for (Path p : dirStream) {
				if(Files.isDirectory(p)) {
					Set<Path> pathSet = deepListFiles(p.toAbsolutePath().toString());
					paths.addAll(pathSet);
				} else {
					paths.add(p);
				}
			}
			
			return paths;
		} finally {
			if(dirStream!=null) {
				dirStream.close();
			}
			
		}
	
	}
	
	/**
	 * 遍历所有文件夹下的文件，将结果存放到 Set<Path> files中
	 * @param foldPath 			要遍历的文件夹
	 * @param files					存放结果的set
	 * @throws IOException
	 */
	public static void deepListDirFiles(Path foldPath, List<Path> files) throws IOException {
		Files.walkFileTree(foldPath, FileVistorUtil.dirFileVistor(files));
	}
	
	/**
	 * 遍历所有文件夹，将结果存放到 Set<Path> dirs中
	 * @param foldPath				要遍历的文件夹
	 * @param dirs						存放结果的set
	 * @param foleNameFilter	指定文件夹名过滤器， *aa*, *bb*
	 * @throws IOException
	 */
	public static void deepListOnlyDirs(Path foldPath, List<Path> dirs, String... foleNameFilter) throws IOException {
		Files.walkFileTree(foldPath, FileVistorUtil.dirOnlyVistor(dirs, foleNameFilter));
	}
	
	/**
	 * 遍历所有文件夹下的问价，将结果存放到 Set<Path> files中
	 * @param foldPath				要遍历的文件夹
	 * @param dirs						存放结果的set
	 * @param fileNameFilter	指定文件名过滤器， *aa.png, *bb.java, *cc*
	 * @throws IOException
	 */
	public static void deepListOnlyFiles(Path foldPath, List<Path> files, String... fileNameFilter) throws IOException {
		Files.walkFileTree(foldPath, FileVistorUtil.fileOnlyVistor(files, fileNameFilter));
	}
	
	public static void deepListSortFiles(Path foldPath, List<Path> files, FileSortUtil.FileAttrEnum sortAttr, boolean isAsc, String... fileNameFilter) throws IOException {
		Files.walkFileTree(foldPath, FileVistorUtil.fileOnlyVistor(files, fileNameFilter));
		java.util.Collections.sort(files, FileSortUtil.fileAttrSort(sortAttr, isAsc));
	}


}
