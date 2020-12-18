package com.unisound.iot.controller.jdk.binaryTree;

public class BinaryTree {


    static class TreeNode{

        private String key ;

        private Object value;

        private TreeNode[] next;

        public int path;
        public int end;


        public TreeNode( String key ){
            this.path = 0;
            this.end = 0;
            //只能存储字母
            next = new TreeNode[ 64];
            this.key = key;

        }
    }

    /**
     *
     *
     */
    static class Trie{
        private TreeNode root;

        public Trie(){
            root = new TreeNode( null );
        }

        public void addNode( String word , String value ){
            if( word == null )
                return;
            char[] info = word.toCharArray();
            TreeNode treeNode = root;
            int index = 0;
            for( char c : info){
                index = c - 'a';
                if( treeNode.next[ index ] == null ){
                    treeNode.next[ index ] = new TreeNode( value );
                }
                treeNode = treeNode.next[ index ];
                treeNode.path++;
            }
            treeNode.end++;
        }


    }






















}
