package com.File;

import java.io.File;

public interface IEnsFile {

    char FORMAT_SEPARATOR = '.';

    File getFile();

    boolean createNewFile();

    boolean createNewFile(String path, String name, String format);
    boolean createNewFile(String path, String name);
    boolean createNewFile(String name);

    static String getFormat(String name) {

        int indexSepar ;
        if (name == null || name.isEmpty() || (indexSepar = name.lastIndexOf(IEnsFile.FORMAT_SEPARATOR)) < 0
        ) {
            return "";
        }


        return name.substring(indexSepar);

    }
}
