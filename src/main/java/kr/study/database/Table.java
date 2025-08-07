package kr.study.database;

import kr.study.tree.BPlusTree;
import java.util.List;

// Table: Segment + B+트리 인덱스 (key = 특정 컬럼값, value = ROWID)
public class Table {
    private final String name;
    private final Segment segment;
    private final BPlusTree index;

    public Table(String name, int blockCapacity, int indexDegree) {
        this.name = name;
        this.segment = new Segment(blockCapacity);
        this.index = new BPlusTree(indexDegree);
    }

    // 레코드 삽입
    public void insertRecord(Record record) {
        String key = record.getColumn("id");
        if (key == null) {
            throw new IllegalArgumentException("Record must have 'id' column for indexing.");
        }

        // Segment에 저장: ROWID 반환
        RowId rid = segment.addRecord(record);

        // B+트리에 (key, ROWID) 삽입
        index.insert(key, rid);

        System.out.println("Inserted record with key=" + key + ", ROWID=" + rid);
    }

    // 인덱스를 통해 ROWID 얻고, ROWID로 실제 레코드 조회
    public Record searchById(String key) {
        RowId rid = index.search(key);
        if (rid == null) return null;
        return segment.getRecordByRowId(rid);
    }

    public List<Block> getBlocks() {
        return segment.getBlocks();
    }

    public String getName() {
        return name;
    }
}
