package kr.study;

import kr.study.database.Block;
import kr.study.database.Table;
import kr.study.database.Record;
import kr.study.database.TableSpace;

import java.util.Map;

public class SimulatorDatabaseApplication {

    public static void main(String[] args) {
        TableSpace ts = new TableSpace();

        ts.createTable("employees", 3, 3); // 블록당 3개 레코드, 인덱스 차수 3
        Table employees = ts.getTable("employees");

        employees.insertRecord(new Record(Map.of("id", "10", "name", "Alice", "age", "25")));
        employees.insertRecord(new Record(Map.of("id", "20", "name", "Bob", "age", "30")));
        employees.insertRecord(new Record(Map.of("id", "5", "name", "Charlie", "age", "22")));
        employees.insertRecord(new Record(Map.of("id", "6", "name", "David", "age", "28")));
        employees.insertRecord(new Record(Map.of("id", "12", "name", "Eve", "age", "26")));

        System.out.println("\n=== Block Data ===");
        int blockNum = 0;
        for (Block block : employees.getBlocks()) {
            System.out.println("Block " + (blockNum++) + " records: " + block.getRecords());
        }

        System.out.println("\n=== Search by ID ===");
        String searchKey = "6";
        Record found = employees.searchById(searchKey);
        System.out.println("Search key='" + searchKey + "': " + (found != null ? found : "not found"));

        String missingKey = "15";
        Record notFound = employees.searchById(missingKey);
        System.out.println("Search key='" + missingKey + "': " + (notFound != null ? notFound : "not found"));
    }
}
