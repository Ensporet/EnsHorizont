package com.File;

public interface FileWrite <T> {

    boolean write(T t);
    boolean write(T t, boolean inExist);

}
