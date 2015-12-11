package ua.knure;

import ua.knure.domain.Table;
import ua.knure.generators.*;

import java.util.List;

public class Main {

    public static void main(String[] args){
        List<Table> tables = MetaDataParser.getTables();
        AbstractDaoClassGenerator.generate("AbstractDao.java");
        //DaoRealizationGenerator.generate(); not implemented
        for (Table table : tables) {
            EntityClassGenerator.generate(table,
                    Converter.toPascalCase(table.getName()) + ".java");
            EntityDaoInterfaceGenerator.generate(table,
                    Converter.toPascalCase(table.getName()) + "Dao.java");
        }
        GenericDaoGenerator.generate("GenericDao.java");
    }
}
