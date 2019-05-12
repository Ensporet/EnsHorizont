package com.File;


import java.io.*;

public class EnsFileReadAndWriteObject<T> extends EnsFile implements FileRead<T>, FileWrite<T>, Serializable{



    //------------------------------------------------------------------------------------------------------------------
    public EnsFileReadAndWriteObject(){
    }
    public EnsFileReadAndWriteObject(File file){
        setFile(file);
    }

    //------------------------------------------------------------------------------------------------------------------




    @Override
    public T read() {
        if (getFile() == null || !getFile().isFile() ) {
            return null;
        }

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getFile()))){
            return (T) objectInputStream.readObject();

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public boolean write(T t) {
        return write(t,true);
    }
    @Override
    public boolean write(T t , boolean inExist) {

            if (t == null || !checkCrateFile(inExist)) {
                return false;
            }

            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getFile()))){
            outputStream.writeObject(t);
            return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


    }

    protected boolean checkCrateFile(boolean inExist) {
        return (inExist) ? (((getFile() != null && getFile().isFile())) || createNewFile()) : createNewFile();
    }


}
