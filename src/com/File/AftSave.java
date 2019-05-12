package com.File;

import java.io.Closeable;
import java.io.File;
import java.io.Serializable;

public interface AftSave extends Closeable , Serializable {

    String DIRECTORIAL_SAVE = File.separator + "Save";

    default void save(){

        new EnsFileReadAndWriteObject<>(
                new File(AftSave.DIRECTORIAL_SAVE + File.separator + this.getClass().getName()))
                .write(this);

    }

    @Override
   default void close() {
        save();
    }
}
