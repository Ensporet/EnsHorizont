package com.Target;

import com.Target.Stats.IStats;
import com.Target.StatusTarget.IStatusTarget;

import java.awt.*;
import java.io.File;


public interface ITarget extends IStats {


   String getName();
   void setName(String name);

   String getValue();
   void setValue(String value);

   File getFile();
   void setFile(File f);

   IStatusTarget getStatus();
   void setStatus(IStatusTarget status);

   boolean isModify();
   void setModify(boolean modify);

   boolean isEmpty();

   Component[] createWindowComponents();

   static ITarget createdDefaultTarget() {

      return new TargetDefault();
   }






}
