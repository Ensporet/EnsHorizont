package com.Window.Save;



import com.File.EnsFileReadAndWriteObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveParam extends EnsFileReadAndWriteObject<HashMap<String , Object>> {

    public final String DIRECTION_SAVE_PARAM = "SaveParam" ;

    
    public void LoadParam(Object object) {

        setFile(generationFile(generationKey(object)));


            HashMap <String,Object>hashMap = this.read();

        Class cl = object.getClass();

        do {
            Field[] fields = cl.getDeclaredFields();
            if (fields == null || hashMap == null) {
                return;
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                for (Field f : fields) {
                    f.setAccessible(true);

                    if (generationKeyField(f).equals(entry.getKey())) {

                        try {
                            f.set(object, entry.getValue());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } while ((cl = cl.getSuperclass()) != null);
    }


    public String generationKeyField(Field field) {
        return field.getAnnotatedType().getType().getTypeName() + " " + field.getName();
    }

    public  String generationKey(Object object){
        return
                object.getClass().getName()
        ;
    }

    public  File generationFile(String key){
        return new File(this.DIRECTION_SAVE_PARAM + File.separator + key);
    }


    public void saveParam(Object object  ){

        if (object == null) {
            return;
        }

        final HashMap <String , Object> HM = new HashMap<>();
        final String KEY = generationKey(object);
        for( Field field :  searchFields(object)) {

            field.setAccessible(true);
            try {
                HM.put(this.generationKeyField(field), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        setFile(
                generationFile(KEY)
        );

        cheak();

        if (!this.write(HM)){
            System.out.println("ERROR !!! key : " + KEY + " object : " + HM );
        };


    };


    //------------------------------------------------------------------------------------------------------------------

    protected List<Field> searchFields (Object object , Class cl , List<Field> list) {
        if (object == null) {
            return new ArrayList<>();
        }

        if (cl == null) {
            cl = object.getClass();
        }
        if (list == null) {
            list = new ArrayList<>();
        }

        for (Field field : cl.getDeclaredFields()) {
            if (field.getAnnotation(Loader.class) == null) {
                continue;
            }
            list.add(field);


        }

        return (cl.getSuperclass() == null) ? list : searchFields(object , cl.getSuperclass() , list);
    }

    protected List<Field> searchFields (Object object) {

        return searchFields(object,null,null);
    }

    public void cheak(){
        if (getFile() == null) {
            setFile(new File(getDefaultNameFile()));
        }


        if (getFile().isDirectory()) {
            try {
                Files.delete(getFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
