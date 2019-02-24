/**
 * We have studied the algorithms for BST.
 * Write a program to implement the deletion from a binary search tree.
 *
 * You first make a BST (algorithm given in class.)
 *
 * Your tree must have at least 15 nodes.
 *
 * 1. Make the BST and do a inorder traversal
 *
 * 2. Now delete the nodes as follows:
 *
 *      a. a leaf node.  Then do inorder traversal.
 *
 *      b. a node that has a subtree.  Then do inorder traversal again.
 *
 * Submit code
 *
 * submit screen shots.
 */

public class BinarySearchTree {


    TreeNode root = null;

    public class TreeNode{

        int val;
        TreeNode right;
        TreeNode left;

        TreeNode(int x){
            val = x;
            right = null;
            left = null;
        }
    }

    public int countNode(TreeNode node){
        /**
         * count number of nodes in the tree
         */
        if(node == null){
            return 0;
        }
        return 1+countNode(node.right)+countNode(node.left);
    }

    private TreeNode InsertNode(TreeNode node, int val){
        /**
         * insert node to BST
         * if no node yet make new node
         * if node value already exist do nothing
         */

        if(node == null){
            node = new TreeNode(val);
            return node;
        }
        else if(val < node.val){
            node.left = InsertNode(node.left,val);
        }else if(val > node.val) {
            node.right = InsertNode(node.right, val);
        }
        return node;
    }

    private void Insert(int val){
        /**
         * Insert for ArrayToBST
         */
        root = InsertNode(root,val);
    }


    public void ArrayToBST(int ... vals){
        /**
         * make array to BST
         */

        for(int i : vals){
            Insert(i);
        }

    }

    public void InorderTraversal(TreeNode node){
        /**
         * Do inorder traversal to print out the tree
         */
        if(node != null) {
            InorderTraversal(node.left);
            System.out.print(node.val + " ");
            InorderTraversal(node.right);
        }
    }

    public boolean IsLeaf(TreeNode node, int val){
        /**
         * check if the node is a leaf node
         */
        if(node == null){
            return false;
        }

        if(node.val == val && node.right == null && node.left == null){
            return true;
        }else if(node.val == val && node.right != null){
            return false;
        }else if(node.val == val && node.left != null){
            return false;
        }
        return (IsLeaf(node.left,val)) ? true : IsLeaf(node.right,val);

    }

    private int getsuccessor(TreeNode node){
        /**
         * return successor value
         */
        if(node.left != null){
            return Math.min(node.val,getsuccessor(node.left));
        }
        return node.val;

    }

    public TreeNode DeleteNodeSuc(TreeNode node, int val){
        /**
         * Delete node with value equal to val(successor)
         */
        if(node == null){
            return node;
        }
        if(node.val > val){
            node.left = DeleteNodeSuc(node.left,val);
        }else if(node.val < val){
            node.right = DeleteNodeSuc(node.right,val);
        }else {
            if(node.left == null){
                return node.right;
            }else if(node.right == null){
                return node.left;
            }
            node.val = getsuccessor(node.right);
            node.right = DeleteNodeSuc(node.right,node.val);
        }
        return node;
    }

    private int getpredecessor(TreeNode node){
        /**
         * return predecessor value
         */

        if(node.right != null){
            return Math.max(node.val,getpredecessor(node.right));
        }
        return node.val;

    }

    public TreeNode DeleteNodePre(TreeNode node, int val){
        /**
         * Delete node with value equal to val (predecessor)
         */
        if(node == null){
            return node;
        }
        if(node.val > val){
            node.left = DeleteNodePre(node.left,val);
        }else if(node.val < val){
            node.right = DeleteNodePre(node.right,val);
        }else {
            if(node.left == null){
                return node.right;
            }else if(node.right == null){
                return node.left;
            }
            node.val = getpredecessor(node.left);
            node.left = DeleteNodePre(node.left,node.val);
        }
        return node;
    }

    public static void main(String[] args){
        BinarySearchTree tree = new BinarySearchTree();
        tree.ArrayToBST(23,4,5,6,2,12,43,32,21,24,35,15,17,47,51,53);
        int delete_leaf = 2;
        int delete_notleaf = 43;
        System.out.println("Number of nodes :"+ tree.countNode(tree.root));
        tree.InorderTraversal(tree.root);
        System.out.print("\n");
        System.out.println(String.format("%d is leaf node :%s", delete_leaf,tree.IsLeaf(tree.root, delete_leaf)));
        System.out.println(String.format("%d is leaf node :%s", delete_notleaf,tree.IsLeaf(tree.root, delete_notleaf)));
        tree.root = tree.DeleteNodeSuc(tree.root,delete_leaf);
        tree.InorderTraversal(tree.root);
        System.out.print("\n");
        tree.root = tree.DeleteNodePre(tree.root,delete_notleaf);
        tree.InorderTraversal(tree.root);
    }
}
