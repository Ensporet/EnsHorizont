package com.Window.PagePanel.TargetPanel.LoaderTargets;

import com.Target.ITarget;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface ILoaderTargets {


    ITarget loadTarget(File file);
    ITarget loadTarget(File file, String key);

    List<ITarget> searchTargets(File direct, String key);
    List<ITarget> searchTargets(File direct);

    List<ITarget> searchTargets(String key);
    List<ITarget> searchTargets();

    List<ITarget>  searchAndLoadTarget(File direct, String key, File directTo);
    List<ITarget>  searchAndLoadTarget(File direct, String key, File directTo, Collection<ITarget> targetsExistFromDirect);


}
