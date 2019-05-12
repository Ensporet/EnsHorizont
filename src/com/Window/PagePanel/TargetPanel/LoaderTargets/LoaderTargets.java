package com.Window.PagePanel.TargetPanel.LoaderTargets;

import com.File.EnsFile;
import com.File.EnsFileReadAndWriteString;
import com.File.FilesFind;
import com.MainClass;
import com.Target.ITarget;
import com.Target.TargetDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


public class LoaderTargets implements ILoaderTargets {








    @Override
    public ITarget loadTarget(File file) {

        if (isTextFile(file)) {
            return new TargetDefault(file.getName() , getValue(file) , file);
        }

        return null;
    }

    @Override
    public ITarget loadTarget(File file, String key) {
        ITarget target = loadTarget(file);
        return (target == null || !searchKey(target.getValue() , key)) ? null : target;
    }



    //...

    private boolean searchKey(String text , String key) {

        if (text == null) {
            return false;
        } else if (key == null) {
            return true;
        }
        return text.contains(key);
    }




    private String getValue(File file) {
        try (EnsFileReadAndWriteString ensFileReadAndWriteString = new EnsFileReadAndWriteString(file)) {
            return ensFileReadAndWriteString.readAll();
        }
    }


    private boolean isTextFile(File file){

        return file != null && file.isFile() && isTrueFormatTextFile(file);

    }

    protected boolean isTrueFormatTextFile(File file) {

        final String FORMAT_FILE = getFormatFile(file);
        for (String format : getFormatsTextFiles()) {
                if (format != null && format.equals(FORMAT_FILE)) {
                    return true;
                }
        }
        return getFormatsTextFiles() == null || getFormatsTextFiles().length == 0;
    }


    private String getFormatFile(File file) {

        if (file == null) {
            return "";
        }

        final String NAME_FILE = file.getName();
        final int    INDEX_FORMAT_SEPARATOR = NAME_FILE.lastIndexOf(EnsFile.FORMAT_SEPARATOR);
        if (NAME_FILE.isEmpty() || INDEX_FORMAT_SEPARATOR < 0) {
            return "";
        }
        return NAME_FILE.substring(
                (INDEX_FORMAT_SEPARATOR + 1)
        );
    }


    private String[] getFormatsTextFiles() {
        return MainClass.FORMATS_TEXT_FILES;


    }

    //---------------------------------------------------------------------------------------------------------------




    @Override
    public List<ITarget> searchTargets(File direct, String key ) {

        final List <ITarget> LIST = new ArrayList<>();


       new FilesFind() {
           @Override
           protected void step(Path path) {



               ITarget target = loadTarget(path.toFile() , key);
               if (target != null) {

                   LIST.add(target);
               }
           }

           @Override
           protected void stepNotAcces(Path path) {

           }
       }.filesFind(checkDirect(direct));


        return LIST;
    }




    @Override
    public List<ITarget> searchTargets(File direct) {
        return searchTargets(direct,null);
    }

    @Override
    public List<ITarget> searchTargets(String key) {

        final List<ITarget> LIST = new ArrayList<>();

        for (File direct : File.listRoots()) {
            LIST.addAll(searchTargets(direct,key));
        }

        return LIST;
    }
    @Override
    public List<ITarget> searchTargets() {


        return searchTargets((String) null);
    }



    private File checkDirect(File file) {
        if (file == null) {
            return null;
        }

        return file.isFile() ? file.getParentFile() : file;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public List<ITarget>  searchAndLoadTarget(File direct , String key , File directTo) {
        return searchAndLoadTarget(direct ,key , directTo,null);
    }



    /**
     *
     * @param direct
     * @param key
     * @param directTo
     * @param targetsExistProgram Передается список уже существующих в прошрамме таргетов, для изменение файла при переносе
     * @return
     */
    @Override
    public List<ITarget>  searchAndLoadTarget(File direct , String key , File directTo , Collection<ITarget> targetsExistProgram) {

        List <ITarget> list = new ArrayList<>();

        try ( Stream<ITarget> stream = searchTargets(direct , key)
                .stream().filter(e-> !e.getFile()
                        .getAbsolutePath().equals(directTo.getAbsolutePath())) ) {

            stream.forEach(e->{

                File path = new EnsFile().notCopyName(directTo.getAbsolutePath()+File.separator + e.getName());


                try {
                    Files.move(e.getFile().toPath() , path.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    updateFileTarget(e.getFile() , path ,targetsExistProgram );
                    e.setFile(path);
                    list.add(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        }


        return list;
    }

  private void updateFileTarget(File oldPach , File newPach , Collection<ITarget> list) {
        if (list == null) {
            return;
        }
        for (ITarget target : list) {
            if (exulasPachTarget(oldPach , target)) {
                target.setFile(newPach);
                return;
            }
        }
  }

  private boolean exulasPachTarget(File old , ITarget target) {

       return (old != null &&
               target.getFile() != null) &&
               target.getFile().getAbsolutePath().equals(old.getAbsolutePath()) ;


  }





}

