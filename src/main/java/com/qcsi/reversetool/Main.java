package com.qcsi.reversetool;


import com.qcsi.reversetool.domain.Table;
import com.qcsi.reversetool.generators.*;
import freemarker.template.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    public static final String DBMS_NAME = "PostgreSQL";
    public static Logger log = LogManager.getLogger();

    public static void main(String[] args){
        List<Table> tables = new MetaDataParser().getTables();
        // don't know why but it does not work with version 2.3.21 and older
        Configuration conf = new Configuration(Configuration.VERSION_2_3_20);

        AbstractDaoGenerator.generate(conf, "AbstractDao.java", DBMS_NAME);
        GenericDaoGenerator.generate(conf, "GenericDao.java");
        for (Table table : tables) {
            String tableName = Converter.toPascalCase(table.getName());
            EntityGenerator.generate(conf, tableName + ".java", table);
            DaoGenerator.generate(conf, tableName + "Dao.java", table);
            DaoImplGenerator.generate(conf,
                    DBMS_NAME + tableName + "Dao.java", DBMS_NAME, table);
        }
    }
}
