package kr.study.database;

import java.util.Objects;

// ROWID 클래스: 데이터 행의 물리적 위치 표현
public class RowId {
    private final int fileNumber;  // Segment 고유번호
    private final int blockNumber; // Segment 내 블록 인덱스
    private final int rowNumber;   // 블록 내 레코드 인덱스

    public RowId(int fileNumber, int blockNumber, int rowNumber) {
        this.fileNumber = fileNumber;
        this.blockNumber = blockNumber;
        this.rowNumber = rowNumber;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowId)) return false;
        RowId rowId = (RowId) o;
        return fileNumber == rowId.fileNumber &&
                blockNumber == rowId.blockNumber &&
                rowNumber == rowId.rowNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileNumber, blockNumber, rowNumber);
    }

    @Override
    public String toString() {
        return "RowId{" + fileNumber + ":" + blockNumber + ":" + rowNumber + '}';
    }
}