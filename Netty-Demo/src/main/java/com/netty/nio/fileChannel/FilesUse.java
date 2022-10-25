package com.netty.nio.fileChannel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 使用Paths Files API
 * @author zhaixinwei
 * @date 2022/10/25
 */
@Slf4j
public class FilesUse {

    public static void main(String[] args) throws IOException {
        findJar();
    }

    /**
     * 打印目录下所有的 jar 文件名称
     * @throws IOException
     */
    private static void findJar() throws IOException {
        Files.walkFileTree(Paths.get("D:\\environment\\jdk"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(file.toString().endsWith(".jar")){
                    log.info("jar file: {}", file.getFileName());
                }
                return super.visitFile(file, attrs);
            }
        });
    }


}
