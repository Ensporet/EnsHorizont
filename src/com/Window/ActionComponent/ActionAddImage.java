package com.Window.ActionComponent;

import com.Window.PackFormats.FormatPictures;
import com.Window.PackFormats.IFormat;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

public abstract class ActionAddImage implements IAction<List<File>> {



    public ActionAddImage(Component component){

        if(component == null) {
            return;
        }

        final List<File> FILES = new ArrayList<>();
        final DropTarget dropTarget = new DropTarget();
        DropTargetListener dropTargetListener = new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                FILES.clear();

                Transferable transferable = dtde.getTransferable();

                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){

                    try {
                        Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);

                        if (object instanceof List) {
                            final IFormat FORMAT = new FormatPictures();
                            for (Object value : (List)object) {
                                if (value instanceof File &&
                                        FORMAT.isFormatTrue(((File)value).getName().toLowerCase())) {
                                    FILES.add((File) value);


                                } else {

                                    dtde.rejectDrag();
                                }
                            }
                        }


                    } catch (UnsupportedFlavorException | IOException e) {
                        e.printStackTrace();
                    }

                }

            }


            @Override
            public void dragOver(DropTargetDragEvent dtde) {


            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                action(FILES);
            }
        };


        dropTarget.setComponent(component);
        try {
            dropTarget.addDropTargetListener(dropTargetListener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        dropTarget.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);

    }



    public abstract void action(List<File> list);
}
