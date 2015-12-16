package com.qcsi.reversetool.generators;

import com.qcsi.reversetool.domain.Table;
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

public class DaoGenerator {

    private static final Logger log = LogManager.getLogger(DaoGenerator.class.getName());

    public static void generate(Configuration configuration, String fileName, Table table){
        try {
            Template template = configuration.getTemplate(
                    "src/main/java/com/qcsi/reversetool/templates/EntityDao.ftl");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("table", table);

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
