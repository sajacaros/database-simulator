package kr.study.database;

import java.util.HashMap;
import java.util.Map;

// TableSpace: 여러 테이블 관리
public class TableSpace {
    private final Map<String, Table> tables = new HashMap<>();

    public void createTable(String tableName, int blockCapacity, int indexDegree) {
        tables.put(tableName, new Table(tableName, blockCapacity, indexDegree));
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
}