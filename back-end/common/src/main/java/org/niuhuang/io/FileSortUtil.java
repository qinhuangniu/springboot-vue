package org.niuhuang.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public class FileSortUtil {

	public enum FileAttrEnum {
		name, size, lastModifiedTime, lastAccessTime, creationTime
	};

	public static Comparator<Path> fileNameSort(boolean isAsc) {
		return new Comparator<Path>() {
			@Override
			public int compare(Path p1, Path p2) {
				if (isAsc) {
					return (p1.getFileName().toString().compareTo(p2.getFileName().toString()));
				} else {
					return (p2.getFileName().toString().compareTo(p1.getFileName().toString()));
				}

			}
		};
	}

	public static Comparator<Path> fileAttrSort(FileAttrEnum attr, boolean isAsc) {
		if(attr == FileAttrEnum.name) {
			return fileNameSort(isAsc);
		} 
		
		return new Comparator<Path>() {
			@Override
			public int compare(Path p1, Path p2) {
				if(isAsc) {
					return compareAsc(p1,p2);
				} else {
					return  compareAsc(p1,p2) * -1;
				}
			}

			private int compareAsc(Path p1, Path p2) {
				try {
					
					BasicFileAttributes attrs1 = Files.readAttributes(p1, BasicFileAttributes.class);
					BasicFileAttributes attrs2 = Files.readAttributes(p2, BasicFileAttributes.class);

					if (attr == FileAttrEnum.size) {
						return Long.compare(attrs1.size(), attrs2.size());
					} else if (attr == FileAttrEnum.lastModifiedTime) {
						return attrs1.lastModifiedTime().compareTo(attrs2.lastModifiedTime());
					} else if (attr == FileAttrEnum.lastAccessTime) {
						return attrs1.lastAccessTime().compareTo(attrs2.lastAccessTime());
					} else {
						return attrs1.creationTime().compareTo(attrs2.creationTime());
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				return 0;
			}

		};
	}

}
