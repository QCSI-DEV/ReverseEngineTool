package com.qcsi.reversetool.jcommander;

import com.beust.jcommander.Parameter;

/**
 * Created by Artem on 31.12.2015.
 */
public class Settings {

    @Parameter(names = {"--path", "-p"}, required = true, description = "Folder where to save generated files")
    private String path;

    @Parameter(names = {"--daoEntity", "-de"}, required = false,
            description = "Defines whether DaoEntity interfaces will be generated.")
    private boolean DaoEntityRequired;

    public String getPath() {
        return path;
    }

    public boolean isDaoEntityRequired() {
        return DaoEntityRequired;
    }
}
