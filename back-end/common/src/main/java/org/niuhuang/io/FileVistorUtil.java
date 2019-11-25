package org.niuhuang.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;

public class FileVistorUtil {
	
	/**
	 * 遍历所有文件夹下的所有文件
	 * @param paths 存放所有文件夹，文件的set集合
	 * @return
	 */
	static FileVisitor<Path> dirFileVistor(List<Path> paths) {
		return new FileVisitor<Path>() {
			  @Override
			  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				  	  paths.add(dir);
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				  paths.add(file);
				  return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }
			};
	}
	
	/**
	 * 
	 * 根据指定的文件夹名字过滤器，遍历所有文件夹下的所有文件夹
	 * 可以指定目录文件名为：*pic*，*java*
	 * @param paths  存放所有文件夹的set集合
	 * @param dirNameFilter 文件夹名字过滤器 *代表任何字符
	 * @return
	 */
	static FileVisitor<Path> dirOnlyVistor(List<Path> paths, String... dirNameFilter) {
		return new FileVisitor<Path>() {
			  @Override
			  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				  if(dirNameFilter !=null) {
					  for( String filter : dirNameFilter) {
						  filter = filter.replace("*", ".*");
						  filter = "^" + filter + "$";
						  if(dir.getFileName().toString().matches(filter)) {
							  paths.add(dir);
						  }
					  }
				  }
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }
			};
	}
	
	/**
	 * 
	 * 根据指定的文件名字过滤器，遍历所有文件夹下的所有文件
	 * 可以指定文件名为：*aa.png，*bb.java, *aml*
	 * @param paths  存放所有文件夹的set集合
	 * @param fileNameFilter 文件名字过滤器 *代表任何字符
	 * @return
	 */
	static FileVisitor<Path> fileOnlyVistor(List<Path> paths, String... fileNameFilter) {
		return new FileVisitor<Path>() {
			  @Override
			  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }
			  
			  @Override
			  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				  if(fileNameFilter!=null) {
					  for(String filter : fileNameFilter ) {
						  filter = filter.replace("*", ".*");
						  filter = "^" + filter + "$";
						  if(file.getFileName().toString().matches(filter)) {
							  paths.add(file);
						  }
					  }
				  }
				
			    return FileVisitResult.CONTINUE;
			  }
	
			  @Override
			  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }

			  @Override
			  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			    return FileVisitResult.CONTINUE;
			  }
			};
	}
	
	
}
