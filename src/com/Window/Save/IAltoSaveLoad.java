package com.Window.Save;


import java.io.Closeable;


public interface IAltoSaveLoad extends Closeable {


    @Override
    default void close() {
        new SaveParam().saveParam(this);
    }

    default void  open(){

      new SaveParam().LoadParam(this);
    };

}
