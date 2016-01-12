package com.qcsi.reversetool;


import com.beust.jcommander.JCommander;
import com.qcsi.reversetool.domain.Table;
import com.qcsi.reversetool.generator.*;
import com.qcsi.reversetool.jcommander.Settings;
import freemarker.template.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final String DBMS_NAME = "PostgreSQL";
    public static Logger log = LogManager.getLogger();

    public static void main(String[] args){
        Configuration conf = getFreeMarkerConfig();

        List<Table> tables = new MetaDataParser().getTables();

        writeFilesBasedOnSettings(args, conf, tables);
    }

    private static void writeFilesBasedOnSettings(String[] args, Configuration conf, List<Table> tables){
        Settings settings = new Settings();
        JCommander jCommander = new JCommander(settings);
        jCommander.parse(args);
        String path = settings.getPath();

        File dir = new File(path);
        if (dir.mkdirs()){
            log.trace("The directory was created, along with all necessary parent directories");
        } else {
            log.fatal("The directory for generated files was not created");
        }

        if (settings.isDaoEntityRequired()) {
            writeFiles(conf, tables, dir.getPath(), true);
        } else {
            writeFiles(conf, tables, dir.getPath(), false);
        }
    }

    private static Configuration getFreeMarkerConfig(){
        Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File templateDirectory = new File(classloader.getResource("template").getFile());
            conf.setDirectoryForTemplateLoading(templateDirectory);
        } catch (IOException e) {
            log.error("Directory with Freemarker template cannot be found", e);
        }
        log.trace("Freemarker Configuration is successfully specified.");
        return conf;
    }

    private static void writeFiles(Configuration conf, List<Table> tables, String dir, boolean isDaoEntityRequired){
        Map<String, Object> data = new HashMap<>();
        data.put("dbms", DBMS_NAME);

        data.put("fileName", String.format("%s/%sAbstractDao.java", dir, DBMS_NAME));
        JavaFileGenerator.generate(conf, data, "AbstractDao.ftl");

        data.put("fileName", dir + "/GenericDao.java");
        JavaFileGenerator.generate(conf, data, "GenericDao.ftl");

        for (Table table : tables) {
            String tableName = Converter.toPascalCase(table.getName());
            data.put("table", table);

            data.put("fileName", String.format("%s/%s.java", dir, tableName));
            JavaFileGenerator.generate(conf, data, "Entity.ftl");

            if (isDaoEntityRequired) {
                data.put("fileName", String.format("%s/%sDao.java", dir, tableName));
                JavaFileGenerator.generate(conf, data, "EntityDao.ftl");

                data.put("fileName", String.format("%s/%s%sDao.java", dir, DBMS_NAME, tableName));
                JavaFileGenerator.generate(conf, data, "DaoImplementation.ftl");
            }
        }
        log.debug("The whole Dao Layer is formed.");
    }
}
