package com.zzlin.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zlin
 * @date 20230306
 */
public class ZipFileUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ZipFileUtil.class);

    private ZipFileUtil(){}

    private static final String DEFAULT_ZIP_FILE_SUFFIX = ".zip";

    /**
     * 压缩文件/目录，生成压缩文件位于同级目录下以.zip结束
     *
     * @param sourcePath 待压缩文件/目录路径
     * @throws Exception 执行异常
     */
    public static void zipFile(String sourcePath) throws Exception {
        zipFile(sourcePath, getDefaultZipFilePath(sourcePath));
    }

    /**
     * 压缩文件/目录
     *
     * @param sourcePath 待压缩文件/目录路径
     * @param outPath 压缩文件路径
     * @throws Exception 执行异常
     */
    public static void zipFile(String sourcePath, String outPath) throws Exception {
        LOGGER.info("zip files param, sourcePath={}, outPath={}", sourcePath, outPath);
        // 参数检查
        if (StringUtils.isBlank(sourcePath) || StringUtils.isBlank(outPath)) {
            throw new RuntimeException("sourcePath or outPath is empty");
        }
        Path tmpSourcePath = Paths.get(sourcePath);
        if (!Files.exists(tmpSourcePath)) {
            throw new RuntimeException("sourcePath does not exists, sourcePath=" + sourcePath);
        }
        File outFile = new File(outPath);
        if (outFile.exists() || outFile.isDirectory()) {
            throw new RuntimeException("outPath is directory or file already exists, outPath=" + outPath);
        }
        // 输出文件创建上级目录
        Files.createDirectories(outFile.getParentFile().toPath());
        // 执行压缩
        doZip(tmpSourcePath, outFile.toPath());
    }

    /**
     * 执行压缩，未校验参数
     *
     * @param sourcePath 待压缩文件/目录路径
     * @param outPath 压缩文件路径
     * @throws Exception 执行异常
     */
    private static void doZip(Path sourcePath, Path outPath) throws Exception {
        LOGGER.info("do zip files start, sourcePath={}, outPath={}", sourcePath, outPath);
        long startTime = System.currentTimeMillis();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outPath.toFile()))) {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String relativePath = sourcePath.relativize(file).toString();
                    zos.putNextEntry(new ZipEntry(relativePath.isEmpty() ? file.getFileName().toString() : relativePath));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        LOGGER.info("zip files end, use time [{}]ms", System.currentTimeMillis() - startTime);
    }

    private static String getDefaultZipFilePath(String sourcePath) {
        return sourcePath + DEFAULT_ZIP_FILE_SUFFIX;
    }

}
