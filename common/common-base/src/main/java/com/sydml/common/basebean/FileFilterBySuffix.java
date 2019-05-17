package com.sydml.common.basebean;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Liuym
 * @date 2019/3/22 0022
 */
public class FileFilterBySuffix implements FileFilter {

    private String suffix;

    public FileFilterBySuffix(String suffix) {
        super();
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith(suffix);
    }
}
