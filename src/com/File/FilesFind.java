package com.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public abstract  class FilesFind  {

    public FilesFind(){

    }

   protected abstract void step(Path path);
    protected abstract void stepNotAcces(Path path);

    public void filesFind(File ... directs) {

        for (File file : directs) {

            try {
                Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {

                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                        step(file);

                        return FileVisitResult.CONTINUE;
                    }


                    @Override // файлы или папки к которым не удалось получить доступ
                    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
                    stepNotAcces(path);
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }


}
