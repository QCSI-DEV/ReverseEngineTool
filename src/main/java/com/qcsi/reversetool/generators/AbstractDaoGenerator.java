package com.qcsi.reversetool.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractDaoGenerator {

    public static void generate(Configuration configuration, String fileName, String dbmsName){
        try {
            Template template = configuration.getTemplate(
                    "src/main/java/com/qcsi/reversetool/templates/AbstractDao.ftl");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("dbms", dbmsName);

            // File output
            Writer file = new FileWriter(new File(fileName));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }
}
