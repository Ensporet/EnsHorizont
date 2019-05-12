package com.File;

import java.io.File;
import java.io.IOException;


public class EnsFile implements  IEnsFile {

    public static void main(String[] args) {
        EnsFile ensFile = new EnsFile();

        String path = "C:\\Users\\enspo\\Desktop\\File\\File";
        String name = "NewFile";
        String format = "txt";

        System.out.println(ensFile.createNewFile(path,null,null) + " " + ensFile.getFile().getAbsolutePath());


    }

    private File file ;


    //------------------------------------------------------------------------------------------------------------------

    public EnsFile(){}
    public EnsFile(File file){
        setFile(file);
    }
    public EnsFile(String path){
        setFile(new File((path == null) ? "" : path));
    }

    //------------------------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------


    protected String createParents(){

        if (getFile() == null) {
            return null;
        }

        String currentPath ="";
        String absolutesDirectories = "";
        for (char ch : getFile().getAbsolutePath().toCharArray()) {
            currentPath += ch;
            if (ch == File.separatorChar) {
                File file = createParent( absolutesDirectories +File.separator+ currentPath);
                if (file == null) {
                    return null;
                }
                absolutesDirectories = file.getAbsolutePath();
               currentPath = "";
            }

        }




return absolutesDirectories;
    }


    protected File  createParent(String absolutePath){
        if (absolutePath == null) {
            return null;
        }

        File file = new File(absolutePath);

        if (file.exists()) {
            if (!file.isDirectory()) {
            file = notCopyName(file.getAbsolutePath());
        } else {
                return file;
            }
        }
        return file.mkdir() ? file : null;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean createNewFile() {

        if (getFile() == null) {
            setFile(new File(getDefaultNameFile()));
        }

        if (getFile().exists()) {
            setFile(notCopyName(getFile().getAbsolutePath()));
        } else {

        String parent = getFile().getAbsolutePath();
        if (parent != null ) {
            if ((parent = createParents()) == null) {
                return false;
            }
            setFile(new File(parent + File.separator + getFile().getName()));

        }

        }

        try {
            return getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean createNewFile(String path, String name, String format) {

        setFile(new File(
                ((path == null) ? "" : path + File.separator) +
                        ((name == null) ? getDefaultNameFile() : name ) +
                        ((format == null) ? "" : EnsFile.FORMAT_SEPARATOR + format)
        ));


        return createNewFile();
    }

    @Override
    public boolean createNewFile(String path, String name) {
        return createNewFile(path,name,null);
    }

    @Override
    public boolean createNewFile(String name) {
        return createNewFile(null,name,null);
    }

    //------------------------------------------------------------------------------------------------------------------
    //!!Bag if D:\pack\ Пустой после спл
    public File notCopyName(String name) {

        if (name == null) {
            return null;
        }
        if (!new File(name).exists()) {
            return new File(name);
        }

        final int POS = name.lastIndexOf(File.separator); // pack
        int pos1 = name.lastIndexOf(IEnsFile.FORMAT_SEPARATOR); // .mod
        if (pos1 < 0) {
            pos1 = name.length();
        }
        final String MODIFY_FILE_NAME = getModifyFileName(name, POS, pos1);
        final int POS0 = MODIFY_FILE_NAME.lastIndexOf('(');
        String startPartName;
        int number = 0;

        try {
            number = Integer.valueOf(
                    (MODIFY_FILE_NAME.charAt(MODIFY_FILE_NAME.length() - 1) == ')' &&
                            POS0 > -1) ? MODIFY_FILE_NAME.substring(POS0 + 1, MODIFY_FILE_NAME.length() - 1) : ""
            );
            startPartName = name.substring(0, (POS + 1) + (MODIFY_FILE_NAME.lastIndexOf('(') + 1));

        } catch (NumberFormatException e) {
            startPartName = name.substring(0, pos1) + "(";
        }

        final String MOD = (pos1 > POS) ? ")" + name.substring(pos1) : "";
        while (true) {

            number++;
            File file = new File(startPartName + number + MOD);

            if (!file.exists()) {
                return file;
            }
        }
    }


    private String getModifyFileName(String name, int indexLastSep, int indexSepFormat) {
        if (indexSepFormat > indexLastSep) {
            return name.substring(indexLastSep + 1, indexSepFormat);
        } else {
            return name.substring(indexLastSep + 1);
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return this.file;
    }



    public String getDefaultNameFile(){
        return "File";
    }
}
