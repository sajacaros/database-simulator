package kr.study.tree;


import kr.study.database.RowId;

// B+트리: key(String) → value(RowId) 매핑
public class BPlusTree {
    private BPlusNode root;
    private final int degree;

    public BPlusTree(int degree) {
        if (degree < 2) throw new IllegalArgumentException("Degree must be >= 2");
        this.degree = degree;
        this.root = new BPlusNode(true);
    }

    public void insert(String key, RowId value) {
        BPlusNode r = root;
        if (r.keys.size() == 2 * degree - 1) {
            BPlusNode s = new BPlusNode(false);
            root = s;
            s.children.add(r);
            splitChild(s, 0, r);
            insertNonFull(s, key, value);
        } else {
            insertNonFull(r, key, value);
        }
    }

    private void splitChild(BPlusNode parent, int index, BPlusNode child) {
        BPlusNode newNode = new BPlusNode(child.leaf);
        int t = degree;

        // 안전성 검사
        if (child.keys.size() < 2 * t - 1) {
            throw new IllegalStateException("Child node doesn't have enough keys to split");
        }

        if (!child.leaf) {
            // 내부 노드의 경우
            for (int i = 0; i < t - 1; i++) {
                if (child.keys.size() > t) {
                    newNode.keys.add(child.keys.remove(t));
                }
            }
            for (int i = 0; i < t; i++) {
                if (child.children.size() > t) {
                    newNode.children.add(child.children.remove(t));
                }
            }

            if (child.keys.size() >= t) {
                parent.children.add(index + 1, newNode);
                parent.keys.add(index, child.keys.remove(t - 1));
            }
        } else {
            // 리프 노드의 경우
            for (int i = 0; i < t - 1; i++) {
                if (child.keys.size() > t) {
                    newNode.keys.add(child.keys.remove(t));
                    newNode.children.add(child.children.remove(t));
                }
            }

            // 리프 노드 연결
            newNode.next = child.next;
            child.next = newNode;

            if (child.keys.size() >= t && newNode.keys.size() > 0) {
                parent.children.add(index + 1, newNode);
                parent.keys.add(index, newNode.keys.get(0));
            }
        }
    }

    private void insertNonFull(BPlusNode node, String key, RowId value) {
        int i = node.keys.size() - 1;

        if (node.leaf) {
            while (i >= 0 && key.compareTo(node.keys.get(i)) < 0) {
                i--;
            }
            node.keys.add(i + 1, key);
            node.children.add(i + 1, value);
        } else {
            while (i >= 0 && key.compareTo(node.keys.get(i)) < 0) {
                i--;
            }
            i++;

            if (i < node.children.size()) {
                BPlusNode child = (BPlusNode) node.children.get(i);
                if (child.keys.size() == 2 * degree - 1) {
                    splitChild(node, i, child);
                    if (key.compareTo(node.keys.get(i)) > 0) {
                        i++;
                    }
                }
                if (i < node.children.size()) {
                    insertNonFull((BPlusNode) node.children.get(i), key, value);
                }
            }
        }
    }

    public RowId search(String key) {
        return searchRecursive(root, key);
    }

    private RowId searchRecursive(BPlusNode node, String key) {
        int i = 0;
        while (i < node.keys.size() && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }
        if (node.leaf) {
            if (i < node.keys.size() && key.equals(node.keys.get(i))) {
                return (RowId) node.children.get(i);
            }
            return null;
        } else {
            if (i < node.children.size()) {
                return searchRecursive((BPlusNode) node.children.get(i), key);
            }
            return null;
        }
    }
}