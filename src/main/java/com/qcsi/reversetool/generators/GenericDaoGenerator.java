package com.qcsi.reversetool.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class GenericDaoGenerator {

    private static final Logger log = LogManager.getLogger(GenericDaoGenerator.class.getName());

    public static void generate(Configuration configuration, String fileName) {
        try {
            Template template = configuration.getTemplate(
                    "src/main/java/com/qcsi/reversetool/templates/GenericDao.ftl");

            Map<String, Object> data = new HashMap<String, Object>();

            Writer file = new FileWriter(new File(fileName));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (TemplateException | IOException e) {
            log.error("Java class file for GenericDao cannot be generated", e);
        }
        log.trace(fileName + " is generated");
    }
}
