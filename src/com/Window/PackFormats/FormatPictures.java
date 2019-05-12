package com.Window.PackFormats;

public class FormatPictures implements IFormat {


    private final String [] FORMATS = {
            ".jpg" ,
            ".gif" ,
            ".tiff",
            ".png"
    };

    @Override
    public boolean isFormatTrue(String name) {

        if (name == null) {
            return false;
        }

        final int INDEX_LAST_DOT = name.lastIndexOf('.');
        if (INDEX_LAST_DOT < 0) {
            return false;
        }
        final String LAST_STR = name.toLowerCase().substring(INDEX_LAST_DOT);

        for (String format : getFORMATS()) {
           if (LAST_STR.equals(format)) {
               return true;
           }
        }


    return false;
    }


    public String[] getFORMATS() {
        return FORMATS;
    }
}
