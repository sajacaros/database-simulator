package kr.study.database;

import java.util.HashMap;
import java.util.Map;

// Record: 컬럼 이름(String) → 값(String)
public class Record {
    private final Map<String, String> columns;

    public Record(Map<String, String> columns) {
        this.columns = new HashMap<>(columns);
    }

    public String getColumn(String colName) {
        return columns.get(colName);
    }

    @Override
    public String toString() {
        return columns.toString();
    }
}