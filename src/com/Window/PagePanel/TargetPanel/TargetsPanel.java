package com.Window.PagePanel.TargetPanel;


import com.File.EnsFile;
import com.File.EnsFileReadAndWriteString;
import com.File.IEnsFile;
import com.MainClass;
import com.Target.ITarget;
import com.Window.PagePanel.PageAnnotation;
import com.Window.PagePanel.PagePanel;
import com.Window.PagePanel.TargetPanel.LoaderTargets.ILoaderTargets;
import com.Window.PagePanel.TargetPanel.LoaderTargets.LoaderTargets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@PageAnnotation(name = "Targets")
public class TargetsPanel implements PagePanel {

    private HashMap<ITarget, Component> targetList = new HashMap<>();

    private JPanel jPanel = new JPanel(new GridLayout(2,2)) ;
    private JScrollPane jScrollPane = new JScrollPane(jPanel);
    private JPopupMenu menu ;





    @Override
    public JComponent createConteiner() {




        createMenu();


        jScrollPane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                menu.setVisible(!menu.isVisible());
                Point point = e.getLocationOnScreen();
                menu.setLocation(point.x , point.y);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addTarget(getLoaderTargets().searchTargets(getTargetsDirectories()));

        return jScrollPane;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void close() {

List<ITarget> targets = new ArrayList<>();
       getTargetList().forEach((key, value) -> targets.add(key));
       targets.stream().forEach(this::save);




    }


    private void save(ITarget target) {


        if (target.isModify()) {
            if (target.isEmpty()) {
                deleteTarget(target);
                return;
            }

           buldFileForSave(target);

           try (EnsFileReadAndWriteString ensFileReadAndWriteString = new EnsFileReadAndWriteString(target.getFile())){
               ensFileReadAndWriteString.write( (target.getValue() == null) ? "" : target.getValue());
           }




        }
    }


    private void buldFileForSave(ITarget target){

        String s = MainClass.DIRECTORIAL_Target +
                File.separator +
                (isNameTargetCorrect(target.getName()) ? target.getName() : defaultNameTarget());

        if (!checkFormat(s)) {
            s += (MainClass.FORMATS_TEXT_FILES.length == 0) ? "" : IEnsFile.FORMAT_SEPARATOR + MainClass.FORMATS_TEXT_FILES[0];
        }


        File file  = new EnsFile().notCopyName(
                s
        );
        if (target.getFile() == null) {
            target.setFile(
                    file
            );

        } else {

            if (!target.getFile().getName().equals(target.getName())) { // rename
                target.getFile().renameTo(
                        file
                );
            }


        }
    }

    private boolean checkFormat (String name) {



        if (name == null) {
            return false;
        }

        if (MainClass.FORMATS_TEXT_FILES.length == 0) {
            return true;
        }

        for (String s : MainClass.FORMATS_TEXT_FILES) {
            if (name.endsWith(IEnsFile.FORMAT_SEPARATOR +s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNameTargetCorrect(String name){
        if (name == null || name.isEmpty()) {
            return false;
        }


        for (char c : MainClass.UNA_SIMBOLS) {
            if (name.indexOf(c) > -1) {
                return false;
            }
        }
        return true;
    }



    private String defaultNameTarget(){
        return "Target";
    }


    //------------------------------------------------------------------------------------------------------------------
    public void deleteTarget(ITarget target) {

        Component component = getTargetList().get(target);

        getTargetList().remove(target);


        if (component != null) {
            jPanel.remove(component);
            jPanel.updateUI();
            updateGrid();
        }
        deleteFile(target);


    }
    private void deleteFile(ITarget target){

        if (target.getFile() != null && target.getFile().exists()) {
            target.getFile().delete();
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    public void addTarget(ITarget iTarget) {

       if ( addTargetIter(iTarget) ) {
    updateGrid();}
    }
    public void addTarget(List<ITarget>  targets) {



        boolean settingSizeTargets = false;
        for (ITarget target : targets) {
            if (addTargetIter(target) && !settingSizeTargets) {
                settingSizeTargets = true ;
            }

        }
        if (settingSizeTargets) {
        updateGrid();
        }
    }

    private boolean addTargetIter(ITarget target) {
        if (!notCopTarget(target)) {
            return false;
        }
        Component component = cell(target);
        getTargetList().put(target,component);

        jPanel.add(component);
        jPanel.revalidate();
        return true;
    }

    //....
    private boolean notCopTarget(ITarget target){

        if (target != null && target.getFile() != null ) {
          for (ITarget targetInList : getTargetList().keySet()) {
              if (targetInList.getFile() != null && target.getFile().getAbsolutePath().equals(
                      targetInList.getFile().getAbsolutePath()
              )) {
                  return false;
              }
          }
        }

        return true;
    }

    private void updateGrid(){
        jPanel.setLayout(new GridLayout(getTargetList().size(),1));
    }

    //.....

    public void addNewTarget(){

        addTarget(ITarget.createdDefaultTarget());

    }

    private Component cell(ITarget targets) {


        Component [] components = targets.createWindowComponents();
        JButton jButton = new JButton("Delete");

        JPanel cell = new JPanel (
                new GridLayout(1,
                (components == null) ? 1 : components.length+1
                ));
        jButton.addActionListener((e)->{
            deleteTarget(targets);
        });
        for (Component com : components) {
            cell.add(com);
        }
        cell.add(jButton);

        addedMenuToCell(cell);


        return cell;
    }


    private void addedMenuToCell(Container container) {
        if (getMenu() != null && container != null) {

            for (Component component : container.getComponents()) {

                if (component instanceof JComponent) {
                    ((JComponent) component).setComponentPopupMenu(getMenu());
                }

                if (component instanceof Container) {
                    addedMenuToCell((Container) component);
                }
            }

        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createMenu() {

        setMenu(new JPopupMenu());

        menuLoadTargets();
        menuNewTarget();

        jScrollPane.setComponentPopupMenu(getMenu());
    }

    private void menuNewTarget(){
        JMenuItem jMenuItem = new JMenuItem("Create new target");
        jMenuItem.addActionListener(e->{
            addNewTarget();
        });
        getMenu().add(jMenuItem);
    }


    private void menuLoadTargets() {

        JMenu jMenu = new JMenu("Load Targets");

        JMenuItem jMenuItem0 = new JMenuItem("Load in the direct") ;
        jMenuItem0.addActionListener(e->{
           addTarget(TargetsPanel.this.getLoaderTargets().searchTargets(getTargetsDirectories())
           );

        });
        jMenu.add(jMenuItem0);
        jMenu.add(new JSeparator());

        //..............................

        JMenuItem jMenuItem1 = new JMenuItem("Search in the local packs");
        jMenuItem1.addActionListener(e -> {

            for (String s : MainClass.DIRECT_SEARCH_TARGETS) {

                     addTarget(TargetsPanel.this.getLoaderTargets().searchTargets(
                                new File(s) ,
                                MainClass.KeyOfTarget
                        ));

            }

        });
        jMenu.add(jMenuItem1);

        JMenuItem jMenuItem11 = new JMenuItem("Search in the local packs and put to the main direct ");
        jMenuItem11.addActionListener(e -> {

            for (String s : MainClass.DIRECT_SEARCH_TARGETS) {

                addTarget(TargetsPanel.this.getLoaderTargets().searchAndLoadTarget(
                        new File(s) ,
                        MainClass.KeyOfTarget ,
                        getTargetsDirectories(),
                        getTargetList().keySet()
                        )

                );


            }

        });
        jMenu.add(jMenuItem11);
        jMenu.add(new JSeparator());

        //.......................
        JMenuItem jMenuItem2 = new JMenuItem("Search in devise");
        jMenuItem2.addActionListener(e->{
            addTarget(TargetsPanel.this.getLoaderTargets().searchTargets(MainClass.KeyOfTarget));
        });
        jMenu.add(jMenuItem2);

        JMenuItem jMenuItem22 = new JMenuItem("Search in devise and put to the main direct");
        jMenuItem22.addActionListener(e->{
            for (File rut : File.listRoots()) {
                addTarget(TargetsPanel.this.getLoaderTargets().searchAndLoadTarget(
                        rut ,
                        MainClass.KeyOfTarget ,
                        getTargetsDirectories(),
                        getTargetList().keySet()
                ));
            }
        });
        jMenu.add(jMenuItem22);

        getMenu().add(jMenu);

    }

    protected JPopupMenu getMenu() {
       return this.menu;
    }

    protected void setMenu(JPopupMenu jPopupMenu) {
        this.menu = jPopupMenu;
    }
    //------------------------------------------------------------------------------------------------------------------

    public HashMap<ITarget,Component> getTargetList() {
        return targetList;
    }



    public ILoaderTargets getLoaderTargets(){
        return new LoaderTargets();
    }

    private File getTargetsDirectories() {
        return new File(MainClass.DIRECTORIAL_Target).getAbsoluteFile();
    }


}
