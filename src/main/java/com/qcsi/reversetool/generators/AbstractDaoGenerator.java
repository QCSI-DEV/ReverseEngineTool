package com.qcsi.reversetool.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractDaoGenerator {

    public static void generate(String fileName, String dbmsName){
        Configuration cfg = new Configuration();
        try {
            Template template = cfg.getTemplate(
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
