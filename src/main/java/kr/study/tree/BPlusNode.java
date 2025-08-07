package kr.study.tree;

import java.util.ArrayList;
import java.util.List;

// B+트리 노드
public class BPlusNode {
    boolean leaf;
    List<String> keys;
    List<Object> children; // BPlusNode 또는 RowId
    BPlusNode next; // leaf 노드 연결용

    public BPlusNode(boolean leaf) {
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.next = null;
    }
}