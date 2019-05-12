package com.File;

import java.io.*;

public class EnsFileReadAndWriteString extends EnsFile implements FileRead<String>, FileWrite<String>, Closeable {



    public EnsFileReadAndWriteString(File file) {
        setFile(file);
    }


    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public String readAll() {
        String s ;
        String result = null;


        while ((s = read())!= null) {

            if (result == null) {
                result = s;
            } else {
                result += '\n' + s;
            }

        }

        return (result == null) ? "" : result;
    }

    @Override
    public String read() {


        if (getBufferedReader() == null) {
            try {
                if (getFile() == null || !getFile().isFile()) {
                    return null;
                }
                setBufferedReader(new BufferedReader(new FileReader(getFile())));

            } catch (IOException e) {
                e.printStackTrace();
                closeRead();
                return null;
            }
        }


        try {
            String result = getBufferedReader().readLine() ;
            if (result == null) {
                closeRead();
                return null;
            }
            return result.replace(System.lineSeparator(),"");
        } catch (IOException e) {
            e.printStackTrace();
            closeRead();

            return null;
        }



    }

    private void closeRead() {
        if (getBufferedReader() != null) {
            try {
                getBufferedReader().close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                setBufferedReader(null);
            }
        }
    }



    protected BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    protected void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean write(String s) {
        return write(s,true);
    }

    @Override
    public boolean write(String s, boolean inExist) {

        if (getBufferedWriter() == null) {
            try {
                if (getFile() == null) {
                    return false;
                } else if(!getFile().isFile() || !inExist) {
                    setFile(notCopyName(getFile().getAbsolutePath()));
                }
                if (!getFile().exists() && !createNewFile()) {
                    return false;
                }

                setBufferedWriter(new BufferedWriter(new FileWriter(getFile())));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }


        try {

            getBufferedWriter().write(s.replace("\n" , System.lineSeparator()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    private void  closeWrite() {

        if (getBufferedWriter() != null) {
            try {
                getBufferedWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                setBufferedWriter(null);
            }
        }
    }

    protected BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    protected void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void close() {
    closeRead();
    closeWrite();
    }
    //------------------------------------------------------------------------------------------------------------------

}
