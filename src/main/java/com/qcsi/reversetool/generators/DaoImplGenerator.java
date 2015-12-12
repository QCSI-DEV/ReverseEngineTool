package com.qcsi.reversetool.generators;

import com.qcsi.reversetool.domain.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class DaoImplGenerator {

    public static void generate(String fileName, String dbmsName, Table table) {
        Configuration cfg = new Configuration();
        try {
            Template template = cfg.getTemplate(
                    "src/main/java/com/qcsi/reversetool/templates/DaoImplementation.ftl");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("dbms", dbmsName);
            data.put("table", table);

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
