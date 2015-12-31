package com.qcsi.reversetool.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JavaFileGenerator {

    private static final Logger log = LogManager.getLogger(JavaFileGenerator.class);

    public static void generate(Configuration configuration, Map<String, Object> data, String templateName){
        log.entry(templateName);
        try {
            Template template = configuration.getTemplate(templateName);
            log.trace("Template is created.");
            Writer file = new FileWriter(new File(data.get("fileName").toString()));
            log.trace("FileWriter is created.");
            template.process(data, file);
            log.trace("The template is processed.");
            file.flush();
            file.close();
        } catch (TemplateException | IOException e) {
            log.error(data.get("fileName") + " cannot be generated.", e);
        }
        log.debug(data.get("fileName").toString() + " is generated.");
    }
}
