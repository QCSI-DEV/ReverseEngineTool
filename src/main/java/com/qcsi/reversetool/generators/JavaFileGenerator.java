package ua.knure.generators;

import ua.knure.domain.Table;

public interface JavaFileGenerator {

    public void generate(String fileName, Table... table);
}
