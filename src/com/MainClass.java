package com;

import com.Window.WindowMain;

import java.io.File;


public  class MainClass {

    final public static String DIRECTORIAL_Target = buldDirectoria("TargetPacket");
    //"C:\\Users\\enspo\\Desktop\\Eugene";
    final public static String KeyOfTarget = "//Nots\\\\";
    final public static String [] FORMATS_TEXT_FILES = new String[] {
            "txt"


    };
    final public static String [] DIRECT_SEARCH_TARGETS = {

            "C:\\Users\\enspo\\Desktop"

    };

    final public static char [] UNA_SIMBOLS = {
            '*',
            ':',
            '<',
            '>',
            '?',
            '/',
            '\\',
            '|',
            '~',
            '#',
            '%',
            '&',
            '{',
            '}',
            '\''
};

    public final static String TEG_NAME_TARGET = "|NAME_TARGET|:";


    public static void main(String[] args) {

       new WindowMain();



/*
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setBounds(100,100,100,100);
        JPanel jPanel = new JPanel(new GridLayout(2,2));
        jPanel.add(new JButton("+"));
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        JTabbedPane jTabbedPane =  new JTabbedPane();
        jTabbedPane.addTab("dd",jScrollPane);
        jFrame.setContentPane(jTabbedPane);

        jFrame.setVisible(true);

        jPanel.add(new Button("++"));

        new WindowMessageText().printMessage("Hello my Yevhenii ", jFrame);
*/

        }


private static String buldDirectoria(String namePack){
        File file = new File(MainClass.class.getProtectionDomain().getCodeSource().getLocation().getPath()) ;
        if (file.isFile()) {
            return file.getParent() + File.separator + namePack;
        }

        return file.getAbsolutePath() + File.separator + namePack;
}


}
