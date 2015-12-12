package com.qcsi.reversetool.generators;

import com.qcsi.reversetool.domain.Table;

public interface JavaFileGenerator {

    public void generate(String fileName, Table... table);
}
