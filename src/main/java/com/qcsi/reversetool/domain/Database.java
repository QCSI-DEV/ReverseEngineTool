package com.qcsi.reversetool.domain;

import java.util.List;

public class Database {
    private List<Table> tables;

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
