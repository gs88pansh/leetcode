package com.answer.leetcode.guier.algorithms;

import com.answer.leetcode.guier.models.ITreeNode;
import com.answer.leetcode.guier.utils.views.BinTreeViewsV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红黑树的实现
 *
 * 红黑树具备以下性质：
 *
 * 1. 二叉搜索树
 * 2. 每个节点存储了一个字段，表示节点的颜色，节点要么是红色，要么是黑色
 * 3. 根节点是黑色
 * 4. 每个叶节点是null，是黑色
 * 5. 如果一个节点是红色的，则它的两个叶节点是黑色的
 * 6. 对于每个节点，到其后代的所有叶节点的简单路径上，均包含相同数目的黑色节点
 */
public class BlackRedTree<T extends Comparable<T>> {

    private final Node nilNode = new Node(Node.black, null);

    private Node root;

    private int totalBlack = 0;

    public BlackRedTree() {
        this.root = this.nilNode;
    }

    class Node implements ITreeNode<T> {
        static final int red = 1;
        static final int black = 2;
        int color;
        T data;
        Node left;
        Node right;
        Node parent;

        private Node(int color, T data) {
            checkColor(color);
            this.color = color;
            this.data = data;
            this.parent = nilNode;
            this.left = nilNode;
            this.right = nilNode;
        }

        public Node(T data) {
            this.color = Node.black;
            this.data = data;
        }

        public void checkColor(int color) {
            if (color != red && color != black) {
                throw new RuntimeException("illegal color");
            }
        }

        public String toString() {
            if (this == BlackRedTree.this.nilNode) {
                return "<nil>";
            }
            return this.color == Node.red ? ("<红" + this.data + ">") : ("<黑" + this.data + ">");
        }

        @Override
        public ITreeNode<T> getRight() {
            return right;
        }

        @Override
        public ITreeNode<T> getLeft() {
            return left;
        }
    }

    /**
     * 1. 插入节点是红色
     * 2. 插入到叶子节点
     * while 父节点是红色节点：
     * 3. 如果父节点是红色节点：
     *  3.a. 如果父节点是左孩子：
     *      3.a.1. 如果父节点是红色节点，并且叔节点是红色节点：父同辈变成黑节点，爷节点变为红节点，设置当前节点为爷节点，继续。
     *      3.a.2. 如果父节点是红色节点，并且叔节点是黑色节点，如果当前节点是右，设置当前节点为父节点，并以当前节点为基准，进行左旋。
     *      3.a.3. 如果父节点是红色节点，并且叔节点是黑色节点，如果当前节点是左，父设置为黑，爷设置为红，爷为基准进行右旋。
     *
     */
    public void insert(T data) {
        // 插入节点是红色
        Node node = new Node(Node.red, data);
        if (this.root == nilNode) {
            this.root = node;
            this.root.color = Node.black;
            return;
        }
        Node current = this.root;
        while (true) {
            Node tmpCur = current;
            if (current.data.compareTo(node.data) > 0) {
                current = tmpCur.left;
                if (current == nilNode) {
                    tmpCur.left = node;
                    node.parent = tmpCur;
                    break;
                }
            }else {
                current = tmpCur.right;
                if (current == nilNode) {
                    tmpCur.right = node;
                    node.parent = tmpCur;
                    break;
                }
            }
        }

        while (node.parent.color == Node.red) {
            if (node.parent.parent.left == node.parent) {
                // 如果父是左孩子：
                if (node.parent.parent.right.color == Node.red) {
                    // 情况一：叔是红
                    node.parent.parent.right.color = Node.black;
                    node.parent.color = Node.black;
                    node.parent.parent.color = Node.red;
                    node = node.parent.parent;
                    continue;
                }
                if (node.parent.right == node) {
                    // 情况二：叔是黑，同时当前节点是右
                    node = node.parent;
                    this.leftRotate(node);
                    continue;
                }

                // 情况三：叔是黑，同时同时当前节点是左
                node.parent.color = Node.black;
                node.parent.parent.color = Node.red;
                this.rightRotate(node.parent.parent);
            }else {

                // 如果父是右孩子，和上一种情况左右相反。
                if (node.parent.parent.left.color == Node.red) {
                    // 情况一：叔是红
                    node.parent.parent.left.color = Node.black;
                    node.parent.color = Node.black;
                    node.parent.parent.color = Node.red;
                    node = node.parent.parent;
                    continue;
                }
                if (node.parent.left == node) {
                    // 情况二：叔是黑，同时当前节点是右
                    node = node.parent;
                    this.rightRotate(node);
                    continue;
                }

                // 情况三：叔是黑，同时同时当前节点是左
                node.parent.color = Node.black;
                node.parent.parent.color = Node.red;
                this.leftRotate(node.parent.parent);
            }
        }
        this.root.color = Node.black;
    }

    private Node find(T data) {
        if (data == null) {
            return nilNode;
        }
        Node current = this.root;
        Node target = nilNode;
        while (current != nilNode) {
            if (current.data == data) {
                target = current;
                break;
            }
            if (current.data.compareTo(data) < 0) {
                current = current.right;
            }else {
                current = current.left;
            }
        }
        return target;
    }

    private Node findMinNode(Node node) {
        if (node == nilNode) {
            return node;
        }
        Node resNode = node;
        while (node != nilNode) {
            resNode = node;
            node = node.left;
        }
        return resNode;
    }

    public T delete(T data) {
        Node target = find(data);
        if (target == nilNode) {
            return null;
        }
        Node nextTarget = nilNode;
        Node pNode = null;
        int deleteColor;
        if (target.left == nilNode) {
            deleteColor = target.color;
            nextTarget = target.right;
            transplantNode(target, nextTarget);
            pNode = nextTarget;
        }else if (target.right == nilNode) {
            deleteColor = target.color;
            nextTarget = target.left;
            transplantNode(target, nextTarget);
            pNode = nextTarget;
        }else {
            nextTarget = findMinNode(target.right);
            if (nextTarget.parent == target) {
                deleteColor = target.color;
                transplantNode(target, nextTarget);
                pNode = nextTarget;
            }else {
                deleteColor = nextTarget.color;
                pNode = nextTarget.right;
                transplantNode(nextTarget, nextTarget.right);
                if (target.parent == nilNode) {
                    this.root = nextTarget;
                    nextTarget.parent = nilNode;
                }else {
                    nextTarget.parent = target.parent;
                    if (target.parent.left == target) {
                        target.parent.left = nextTarget;
                    }else {
                        target.parent.right = nextTarget;
                    }
                }
                nextTarget.left = target.left;
                if (nextTarget.left != nilNode) {
                    nextTarget.left.parent = nextTarget;
                }
                nextTarget.right = target.right;
                if (nextTarget.right != nilNode) {
                    nextTarget.right.parent = nextTarget;
                }
                nextTarget.color = target.color;
            }
        }

        if (deleteColor == Node.black) {
            while (pNode != this.root && pNode.color == Node.black) {
                if (pNode.parent.left == pNode) {
                    if (pNode.parent.right.color == Node.red) {
                        // 情况1：右兄弟节点是红色
                        pNode.parent.color = Node.red;
                        pNode.parent.right.color = Node.black;
                        leftRotate(pNode.parent);
                    }else if (pNode.parent.right.left.color == Node.black && pNode.parent.right.right.color == Node.black) {
                        // 情况2：右兄弟节点是黑色，右兄弟的两个子节点都是黑色
                        pNode.parent.right.color = Node.red;
                        pNode = pNode.parent;
                    }else if (pNode.parent.right.left.color == Node.red && pNode.parent.right.right.color == Node.black) {
                        // 情况3：右兄弟节点是黑色，右兄弟的左孩子是红，右是黑
                        pNode.parent.right.color = Node.red;
                        pNode.parent.right.left.color = Node.black;
                        rightRotate(pNode.parent.right);
                    }else if (pNode.parent.right.right.color == Node.red) {
                        // 情况4：右兄弟节点是黑色，右兄弟的右孩子是红
                        int tmpColor = pNode.parent.color;
                        pNode.parent.color = Node.black;
                        pNode.parent.right.color = tmpColor;
                        pNode.parent.right.right.color = Node.black;
                        leftRotate(pNode.parent);
                        pNode = this.root;
                    }
                }else {
                    // 与上面相反
                    if (pNode.parent.left.color == Node.red) {
                        pNode.parent.color = Node.red;
                        pNode.parent.left.color = Node.black;
                        rightRotate(pNode.parent);
                    }else if (pNode.parent.left.right.color == Node.black && pNode.parent.left.left.color == Node.black) {
                        pNode.parent.left.color = Node.red;
                        pNode = pNode.parent;
                    }else if (pNode.parent.left.right.color == Node.red && pNode.parent.left.left.color == Node.black) {
                        pNode.parent.left.color = Node.red;
                        pNode.parent.left.right.color = Node.black;
                        leftRotate(pNode.parent.left);
                    }else if (pNode.parent.left.left.color == Node.red) {
                        int tmpColor = pNode.parent.color;
                        pNode.parent.color = Node.black;
                        pNode.parent.left.color = tmpColor;
                        pNode.parent.left.left.color = Node.black;
                        rightRotate(pNode.parent);
                        pNode = this.root;
                    }
                }
            }
        }
        nilNode.parent = null;
        this.root.color = Node.black;
        pNode.color = Node.black;
        return data;
    }

    /**
     * 子节点替代父节点
     */
    public void transplantNode(Node node1, Node node2) {
        if (node1.parent == nilNode) {
            this.root = node2;
            node2.parent = nilNode;
        }else if (node1.parent.left == node1) {
            node1.parent.left = node2;
        }else {
            node1.parent.right = node2;
        }
        // 有可能将nilNode的父节点设置为该节点，无所谓；
        node2.parent = node1.parent;
    }

    private void leftRotate(Node node) {
        if (node == null || node == this.nilNode) {
            return;
        }
        if (node.right == nilNode) {
            return;
        }
        Node tmpRight = node.right;
        Node tmpParent = node.parent;

        if (tmpParent != this.nilNode) {
            if (tmpParent.left == node) {
                tmpParent.left = tmpRight;
            } else {
                tmpParent.right = tmpRight;
            }
            tmpRight.parent = tmpParent;
        }else {
            this.root = tmpRight;
            tmpRight.parent = nilNode;
        }

        node.right = tmpRight.left;
        if (node.right != nilNode) {
            node.right.parent = node;
        }

        tmpRight.left = node;
        node.parent = tmpRight;

    }

    private void rightRotate(Node node) {
        if (node == nilNode) {
            return;
        }
        if (node.left == nilNode) {
            return;
        }
        Node tmpParent = node.parent;
        Node tmpLeft = node.left;

        if (tmpParent == nilNode) {
            this.root = tmpLeft;
            tmpLeft.parent = nilNode;
        }else {
            if (tmpParent.left == node) {
                tmpParent.left = tmpLeft;
            }else {
                tmpParent.right = tmpLeft;
            }
            tmpLeft.parent = tmpParent;
        }

        node.left = tmpLeft.right;
        if (node.left != nilNode) {
            node.left.parent = node;
        }

        tmpLeft.right = node;
        node.parent = tmpLeft;
    }

    public void checkLegal() {
        this.totalBlack = 0;
        this.checkLegal(this.root, 0);
    }

    public void checkLegal(Node node, int current) {
        if (node == nilNode) {
            // System.out.printf("当前黑高度=%s\n", current);
            if (this.totalBlack == 0) {
                this.totalBlack = current;
            }else {
                if (this.totalBlack != current) {
                    throw new RuntimeException("total black not right");
                }
            }
            return;
        }
        if (node.parent != nilNode) {
            if (node == node.parent.left) {
                if (node.data.compareTo(node.parent.data) > 0) {
                    throw new RuntimeException("not asc");
                }
            }else {
                if (node.data.compareTo(node.parent.data) < 0) {
                    throw new RuntimeException("not asc");
                }
            }
        }
        if (node.color == Node.black) {
            current ++;
        }else {
            if (node.left.color == Node.red || node.right.color == Node.red) {
                throw new RuntimeException("diff black");
            }
        }
        checkLegal(node.left, current);
        checkLegal(node.right, current);
    }

    public String toString() {
        StringBuilder sb  = new StringBuilder();
        BinTreeViewsV2.traversalPrint(this.root, null, false, sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            randomTest();
        }
        String caseOpt = "add 70;add 28;add 27;delete 27;delete 27;add 47;delete 28;delete 47;add 10;add 21;add 27;add 7;delete 10;delete 27;add 96;delete 27;add 95;add 53;add 67;add 31;add 31;add 29;add 15;add 43;add 82;delete 29;add 0;add 44;add 85;add 19;add 63;add 77;add 4;add 67;delete 31;add 60;add 60;add 66;add 30;add 80;delete 70;add 62;add 35;add 15;add 39;delete 80;delete 70;delete 15;delete 47;add 15;add 53;add 26;add 40;add 97;add 98;add 3;add 5;add 66;delete 85;add 99;add 37;add 98;add 76;add 59;add 83;add 84;add 8;add 67;add 45;add 82;delete 19;add 94;add 51;add 57;add 34;add 78;add 95;add 64;delete 84;add 30;add 88;add 98;add 94;delete 66;add 4;add 74;add 5;add 30;add 96;add 40;delete 94;add 70;add 17;add 99;add 80;add 24;add 22;add 90;delete 37;add 93;add 38;delete 28;add 31;add 71;add 92;add 2;add 3;add 9;add 31;add 2;add 8;add 21;add 57;add 11;add 86;delete 15;add 50;delete 74;add 9;add 61;delete 38;add 24;add 74;add 45;add 73;add 13;add 82;add 69;delete 0;add 98;add 12;add 74;add 16;add 40;add 65;add 8;add 54;delete 38;add 6;add 91;add 88;add 9;delete 66;delete 66;add 40;add 46;add 13;add 79;delete 24;add 98;add 57;add 56;delete 19;add 13;add 45;delete 10;add 35;delete 31;add 71;add 89;add 99;add 43;add 33;delete 60;add 18;add 62;add 29;add 60;add 1;add 84;add 5;delete 15;add 9;add 54;delete 99;add 70;add 55;delete 59;delete 12;add 80;add 3;delete 31;add 79;add 54;add 9;add 3;add 74;add 21;delete 43;add 15;add 71;add 34;add 36;add 34;delete 38;add 8;delete 26;add 25;delete 31;delete 1;add 1;add 86;add 78;add 63;delete 96;add 8;add 71;add 96;add 78;delete 71;delete 61;delete 99;delete 8;delete 8;add 87;delete 80;add 31;delete 29;delete 86;add 95;delete 9;add 38;add 25;add 33;add 86;add 63;add 72;add 85;add 85;delete 65;add 3;add 8;add 72;delete 30;delete 79;add 20;delete 21;add 68;add 78;add 46;add 82;add 92;add 51;add 85;delete 86;add 73;add 71;add 17;delete 1;add 74;add 1;add 12;add 58;add 51;add 38;add 25;add 84;delete 73;add 26;add 81;delete 78;delete 4;delete 57;add 47;";
        caseTest(caseOpt);
    }

    public static void caseTest(String caseOpt) {
        String[] cases = caseOpt.split(";");
        BlackRedTree<Integer> tree = new BlackRedTree<>();
        for (String opt : cases) {
            if (opt.length() == 0) {
                continue;
            }
            String[] optNum = opt.split(" ");
            String optType = optNum[0];
            String data = optNum[1];
            Integer d = Integer.valueOf(data);
            System.out.println(opt);
            if ("add".equals(optType)) {
                tree.insert(d);
                // System.out.println(tree);
            }else {
                tree.delete(d);
                // System.out.println(tree);
            }
            tree.checkLegal();
        }
    }

    public static void randomTest() {
        List<String> cases = new ArrayList<>();
        try {
            BlackRedTree<Integer> tree = new BlackRedTree<>();

            Random random = new Random();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                int opt = random.nextInt(4);
                if (opt >= 3) {
                    if (list.size() > 0) {
                        int r = random.nextInt(list.size());
                        int value = list.get(r);
                        System.out.printf("delete %s \n", value);
                        cases.add("delete " + value);
                        StringBuilder sb = new StringBuilder();
                        cases.forEach(s -> sb.append(s).append(";"));
                        System.out.println(sb);
                        tree.delete(value);
                        // System.out.println(tree);
                    }
                } else {
                    int r = random.nextInt(100);
                    list.add(r);
                    System.out.printf("add %s \n", r);
                    cases.add("add " + r);
                    StringBuilder sb = new StringBuilder();
                    cases.forEach(s -> sb.append(s).append(";"));
                    System.out.println(sb);
                    tree.insert(r);
                    // System.out.println(tree);
                    tree.checkLegal();
                }
            }

            tree.checkLegal();
            System.out.print(tree.totalBlack);
        }catch (Exception e) {
            System.out.println(">>>>>>>>>> bad case <<<<<<<<");
            StringBuilder sb = new StringBuilder();
            cases.forEach(s -> sb.append(s).append(";"));
            System.out.println(sb);
        }
    }
}
