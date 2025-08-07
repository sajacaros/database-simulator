package kr.study.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Segment: 여러 Block 보유, fileNumber (Segment 고유번호) 관리
public class Segment {
    private static int segmentIdCounter = 0; // 세그먼트 고유번호 자동 부여

    private final int fileNumber;
    private final List<Block> blocks = new ArrayList<>();
    private final int blockCapacity;

    public Segment(int blockCapacity) {
        this.blockCapacity = blockCapacity;
        this.fileNumber = segmentIdCounter++;
        blocks.add(new Block(0, blockCapacity));
    }

    // 레코드 추가 후 ROWID 반환
    public RowId addRecord(Record record) {
        Block lastBlock = blocks.get(blocks.size() - 1);
        int rowIndex = lastBlock.addRecord(record);
        if (rowIndex == -1) {
            // 새 블록 생성
            Block newBlock = new Block(blocks.size(), blockCapacity);
            blocks.add(newBlock);
            rowIndex = newBlock.addRecord(record);
            return new RowId(fileNumber, newBlock.getBlockNumber(), rowIndex);
        } else {
            return new RowId(fileNumber, lastBlock.getBlockNumber(), rowIndex);
        }
    }

    // ROWID 이용해 레코드 직접 조회
    public Record getRecordByRowId(RowId rid) {
        if (rid.getFileNumber() != this.fileNumber) {
            return null; // 다른 세그먼트
        }
        int bIdx = rid.getBlockNumber();
        int rIdx = rid.getRowNumber();
        if (bIdx < 0 || bIdx >= blocks.size()) {
            return null;
        }
        Block block = blocks.get(bIdx);
        return block.getRecord(rIdx);
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }
}