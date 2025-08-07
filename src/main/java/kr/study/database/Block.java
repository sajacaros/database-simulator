package kr.study.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Block: 여러 레코드 저장
public class Block {
    private final int blockNumber;
    private final List<Record> records = new ArrayList<>();
    private final int capacity;

    public Block(int blockNumber, int capacity) {
        this.blockNumber = blockNumber;
        this.capacity = capacity;
    }

    // 성공 시 저장한 레코드의 블록 내 인덱스 반환, 실패 시 -1
    public int addRecord(Record record) {
        if (records.size() < capacity) {
            records.add(record);
            return records.size() - 1;
        }
        return -1;
    }

    public Record getRecord(int index) {
        if (index < 0 || index >= records.size()) return null;
        return records.get(index);
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public List<Record> getRecords() {
        return Collections.unmodifiableList(records);
    }
}