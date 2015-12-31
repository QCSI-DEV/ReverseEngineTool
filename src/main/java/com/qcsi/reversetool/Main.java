package com.qcsi.reversetool;


import com.qcsi.reversetool.domain.Table;
import com.qcsi.reversetool.generator.*;
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
        List<Table> tables = new MetaDataParser().getTables();
        // don't know why but it does not work with version 2.3.21 and older
        // now I have a suggestion
        Configuration conf = new Configuration(Configuration.VERSION_2_3_20);
        try {
            conf.setDirectoryForTemplateLoading(new File("src/main/java/com/qcsi/reversetool/template"));
        } catch (IOException e) {
            log.error("Directory with Freemarker template cannot be found", e);
            return;
        }
        log.trace("Freemarker Configuration is successfully specified.");

        Map<String, Object> data = new HashMap<>();
        data.put("dbms", DBMS_NAME);

        data.put("fileName", "AbstractDao.java");
        JavaFileGenerator.generate(conf, data, "AbstractDao.ftl");

        data.put("fileName", "GenericDao.java");
        JavaFileGenerator.generate(conf, data, "GenericDao.ftl");

        for (Table table : tables) {
            String tableName = Converter.toPascalCase(table.getName());
            data.put("table", table);

            data.put("fileName", tableName + ".java");
            JavaFileGenerator.generate(conf, data, "Entity.ftl");

            data.put("fileName", tableName + "Dao.java");
            JavaFileGenerator.generate(conf, data, "EntityDao.ftl");

            data.put("fileName", DBMS_NAME + tableName + "Dao.java");
            JavaFileGenerator.generate(conf, data, "DaoImplementation.ftl");
        }
        log.debug("The whole Dao Layer is formed.");
    }
}
