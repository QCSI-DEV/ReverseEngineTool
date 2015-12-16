package com.qcsi.reversetool.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractDaoGenerator extends JavaFileGenerator{

    private static final Logger log = LogManager.getLogger(AbstractDaoGenerator.class.getName());

    public static void generate(Configuration configuration, String fileName, String dbmsName){
        try {
            Template template = configuration.getTemplate(
                    "src/main/java/com/qcsi/reversetool/templates/AbstractDao.ftl");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("dbms", dbmsName);

            Writer file = new FileWriter(new File(fileName));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (TemplateException | IOException e) {
            log.error("Java class file for Entity cannot be generated", e);
        }
        log.trace(fileName + " is generated");
    }
}
