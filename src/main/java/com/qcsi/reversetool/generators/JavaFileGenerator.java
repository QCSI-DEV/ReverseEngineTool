package com.qcsi.reversetool.generators;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public abstract class JavaFileGenerator {

    public void generate(Template template, Map<String, Object> data, String fileName){
        try {
            Writer file = new FileWriter(new File(fileName));
            template.process(data, file);
            file.flush();
            file.close();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }
}
