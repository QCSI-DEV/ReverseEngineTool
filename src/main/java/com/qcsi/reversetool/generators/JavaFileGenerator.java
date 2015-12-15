package com.qcsi.reversetool.generators;

import com.qcsi.reversetool.domain.Table;
import freemarker.template.Version;

public interface JavaFileGenerator {

    public void generate(Version version, String fileName, Table... table);
}
