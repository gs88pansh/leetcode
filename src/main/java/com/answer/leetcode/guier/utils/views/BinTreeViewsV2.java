package com.answer.leetcode.guier.utils.views;

import com.answer.leetcode.guier.models.ITreeNode;

public class BinTreeViewsV2 {

    private static class Trunk {
        Trunk prev;
        String str;

        private Trunk(Trunk prev, String str) {
            this.prev = prev;
            this.str = str;
        }
    }

    // Helper function to print branches of the binary tree
    private static void showTrunks(Trunk p, StringBuilder sb) {
        if (p == null)
            return;
        showTrunks(p.prev, sb);
        sb.append(p.str);
    }

    // 使用中序遍历方式打印二叉树
    public static void traversalPrint(ITreeNode<?> root, Trunk prev, boolean isLeft, StringBuilder sb) {
        if (root == null)
            return;
        String ROOT_PREV                    = "   ";
        String CHILD_PREV                   = "    ";
        String LEFT_CHILD_CURVED_EDGE       = ".---";
        String LEFT_CHILD_STRAIGHT_EDGE     = "   |";
        String RIGHT_CHILD_CURVED_EDGE      = "`---";
        String RIGHT_CHILD_STRAIGHT_EDGE    = "   |";
        String prev_str = CHILD_PREV;
        Trunk trunk = new Trunk(prev, prev_str);
        // 遍历左子树
        traversalPrint(root.getLeft(), trunk, true, sb);
        if (prev == null)
            trunk.str = ROOT_PREV;
        else if (isLeft) {
            trunk.str = LEFT_CHILD_CURVED_EDGE;
            prev_str = LEFT_CHILD_STRAIGHT_EDGE;
        } else {
            trunk.str = RIGHT_CHILD_CURVED_EDGE;
            prev.str = prev_str;
        }
        showTrunks(trunk, sb);
        // 打印当前节点
        sb.append(root).append("\n");
        if (prev != null)
            prev.str = prev_str;
        trunk.str = RIGHT_CHILD_STRAIGHT_EDGE;
        // 遍历右子树
        traversalPrint(root.getRight(), trunk, false, sb);
    }
}
