package org.niuhuang.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 *
 * @author niuhuang
 */
public class ZipUtil {

	public static int bufferSize = 1024;

	public static String zipExtention = ".zip";

	/**
	 * 将要压缩的文件夹或者文件压缩的指定源path的同目录 例如 srcPath是 d:/a/b/c 就会以c为root目录
	 * 将压缩的文件输出到d:/a/b/c.zip srcPath是 d:/a/b.txt 就会将b.txt 压缩的文件输出到d:/a/b.txt.zip
	 * 
	 * @param srcPath 要压缩的文件夹，或者文件
	 * @throws Exception
	 */
	public static void zip(String srcPath) throws Exception {
		Path sPath = Paths.get(srcPath);
		Path zipPath = getZipPath(sPath);
		zip(zipPath, sPath);
	}

	/**
	 * @param outZipPath
	 * @param srcPath
	 * @throws Exception
	 */
	public static void zip(String outZipPath, String srcPath) throws Exception {
		Path sPath = Paths.get(srcPath);
		Path zipPath = Paths.get(outZipPath);
		zip(zipPath, sPath);
	}

	/**
	 * 将要压缩的文件路径下的所有文件压缩成zip文件
	 * 
	 * @param outZipPath 压缩文件的输出路径
	 * @param srcPath    要压缩的文件路径
	 * @throws Exception
	 */
	private static void zip(Path outZipPath, Path srcPath) throws Exception {
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outZipPath.toFile()));
		try {
			checkZipPath(outZipPath, srcPath);
			// 例如 d://a/b/c 那么c就是rootpath 就从c开始创建zipEntry
			int rootPathIdx = srcPath.getNameCount() - 1;
			// 是目录的话，
			if (Files.isDirectory(srcPath)) {
				zipDir(zipOut, srcPath, rootPathIdx);
			} else {
				zip1File(zipOut, srcPath, rootPathIdx);
			}

		} finally {
			zipOut.close();
		}
	}

	/**
	 * @param srcPath
	 * @param out
	 * @throws IOException
	 */
	private static void zipDir(ZipOutputStream out, Path srcPath, int rootPathIndex) throws IOException {
		DirectoryStream<Path> dirStream = null;
		dirStream = Files.newDirectoryStream(srcPath);
		for (Path p : dirStream) {
			if (Files.isDirectory(p)) {
				// 递归调用压缩目录
				zipDir(out, p, rootPathIndex);
			} else {
				// 压缩一个文件
				zip1File(out, p, rootPathIndex);
			}
		}
	}

	/**
	 * 将一个文件压缩到zip流中
	 * 
	 * @param file 文件路径
	 * @param out  zip流
	 * @throws IOException
	 */
	private static void zip1File(ZipOutputStream out, Path file, int rootPathIndex) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.toFile()), bufferSize);
		try {
			out.putNextEntry(new ZipEntry(file.subpath(rootPathIndex, file.getNameCount()).toString()));
			int len;
			while ((len = in.read()) > 0) {
				out.write(len);
			}
		} finally {
			out.closeEntry();
			in.close();
		}
	}

	/**
	 * 根据压缩前文件目录，返回zip文件路径 例如 d:/a/b 返回d:/a/b.zip d:/a/b.txt 返回d:/a/b.txt.zip
	 * 
	 * @param srcPath
	 * @return zip文件路径
	 */
	private static Path getZipPath(Path srcPath) {
		Path parentPath = srcPath.getParent();
		Path fileNamePath = srcPath.getFileName();
		String outZipName = fileNamePath.toString() + zipExtention;
		return Paths.get(parentPath.toString(), outZipName);
	}

	private static void checkZipPath(Path zipPath, Path srcPath) throws Exception {
		Path zipParentPath = zipPath.getParent();
		if (Files.isDirectory(srcPath) && zipParentPath.toString().contains(srcPath.toString())) {
			throw new Exception(String.format("Zip file path {%s} must not be the child directory of src path {%s} .",
					zipPath, srcPath));
		}
	}

	public static void unzip(String zipIn) throws Exception {
		Path zipPath = Paths.get(zipIn);
		Path outPath = zipPath.getParent();
		unzip(outPath, zipPath);
	}
	
	public static void unzip(String outFolder, String zipIn) throws Exception {
		Path outPath = Paths.get(outFolder);
		Path zipPath = Paths.get(zipIn);
		unzip(outPath, zipPath);
	}
	
	public static void unzip(Path outPath, Path zipIn) throws Exception {
		// 如果zip文件不存在的话 抛出错误
		if (!Files.exists(zipIn)) {
			throw new Exception(String.format("zip file {%s} is not exists.", zipIn.toString()));
		}
		// 如果输出目录不存在，新建目录
		if (!Files.exists(outPath)) {
			Files.createDirectories(outPath);
		}
		ZipInputStream zin = null;
		try {
			// 读取zip输入流
			zin = new ZipInputStream(new FileInputStream(zipIn.toFile()));
			ZipEntry ze = null;
			byte[] buffer = new byte[bufferSize];
			int readSize = -1;
			FileOutputStream fout = null;
			// 循环获取zipEntry
			while ((ze = zin.getNextEntry()) != null) {
				try {
					// 输出目录 + zipEntry路劲
					Path fPath = Paths.get(outPath.toString(), ze.getName());
					// 如果是目录且不存在，新建目录
					if (Files.isDirectory(fPath) && !Files.exists(fPath)) {
						Files.createDirectories(fPath);
					} else {
						
						// 如果父目录不存在，新建目录
						Path parentPath = fPath.getParent();
						if(!Files.exists(parentPath)) {
							Files.createDirectories(parentPath);
						}
						// 解压缩写文件
						Files.copy(zin, fPath);
					}
				} finally {
					zin.closeEntry();
				}
			}
		} finally {
			zin.close();
		}
	}

}