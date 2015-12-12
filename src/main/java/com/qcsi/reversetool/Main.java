package com.qcsi.reversetool;


import com.qcsi.reversetool.domain.Table;
import com.qcsi.reversetool.generators.*;

import java.util.List;

public class Main {

    public static final String DBMS_NAME = "PostgreSQL";

    public static void main(String[] args){
        List<Table> tables = MetaDataParser.getTables();

        AbstractDaoGenerator.generate("AbstractDao.java", DBMS_NAME);
        GenericDaoGenerator.generate("GenericDao.java");
        for (Table table : tables) {
            String tableName = Converter.toCamelCase(table.getName());
            EntityGenerator.generate(tableName + ".java", table);
            DaoGenerator.generate(tableName + "Dao.java", table);
            DaoImplGenerator.generate(
                    DBMS_NAME + tableName + "Dao.java", DBMS_NAME, table);
        }
    }
}
