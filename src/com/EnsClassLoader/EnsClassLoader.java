package com.EnsClassLoader;

import com.File.EnsFile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class EnsClassLoader {

final private Class MAIN_CLASS ;
final private String FORMAT_JAR ="jar";
final private String FORMAT_CLASS = "class";
final private int LENGTH_FORMAT_CLASS = (EnsFile.FORMAT_SEPARATOR + FORMAT_CLASS).length();

    public EnsClassLoader(Class mainClass) {
    this.MAIN_CLASS = mainClass;
    }



    //------------------------------------------------------------------------------------------------------------------
    public List<Class> getAllClass(Predicate<Class> predicate){
        File file = getRunJarFilePath();
        if (file == null || !file.exists()) {
            return null;
        }

        if (isJarFileOrDirect(file)) {
            return getAllClassJar(file,predicate);
        }

        return getAllClassDirect(file,predicate);

    }

    private boolean filterClass(String s){
        return s != null && s.endsWith(EnsFile.FORMAT_SEPARATOR + this.FORMAT_CLASS);
    }

    private Class filePathToClass (String path) {
        if (path == null) {
            return null;
        }

        path = path
                .replace(File.separatorChar,'.')
                .replace('/','.');


        final int ST = path.indexOf(getMAIN_CLASS().getPackage().getName());
        if (ST < 0) {
            return null;
        }


        try {
            return Class.forName(
            path
                            .substring(ST , path.length() - this.LENGTH_FORMAT_CLASS)

            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    private List<Class> getAllClassDirect(File file , Predicate<Class> predicate) {
        List<Class> classes = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(file.getAbsolutePath()), new SimpleFileVisitor<>(){
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileName = file.toAbsolutePath().toString();

                    Class cl ;
                   if (
                           filterClass(fileName) &&
                                   (cl = filePathToClass(fileName))        != null &&
                                   predicate.test(cl)
                   ){
                classes.add(cl);

                 }

                    return FileVisitResult.CONTINUE;
                }


                @Override // файлы или папки к которым не удалось получить доступ
                public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {

                    return FileVisitResult.SKIP_SUBTREE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }


    //............
    private List<Class> getAllClassJar(File file , Predicate<Class> predicate){
        List<Class> classes = new ArrayList<>();
        ZipInputStream zip = null;
        try {
            zip = new ZipInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                final String NAME = entry.getName();
                Class cl ;
                if (
                        !entry.isDirectory() &&
                                filterClass(NAME) &&
                                    ((cl = filePathToClass(NAME)) != null) &&
                                        predicate.test(cl)) {
                 classes.add(cl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    //...
    private boolean isJarFileOrDirect(File file){

        return file.isFile() && file.getName().endsWith(EnsFile.FORMAT_SEPARATOR + this.FORMAT_JAR);
    }

    //...

    private File getRunJarFilePath(){
        try {
            return new File(getMAIN_CLASS().getProtectionDomain().getCodeSource().getLocation()
                    .toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public Class getMAIN_CLASS() {
        return MAIN_CLASS;
    }

    //------------------------------------------------------------------------------------------------------------------

}
