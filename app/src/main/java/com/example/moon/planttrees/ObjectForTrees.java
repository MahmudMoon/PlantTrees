package com.example.moon.planttrees;

public class ObjectForTrees {
    private String TreeOne;
    private String TreeTwo;

    public ObjectForTrees() {

    }

    public ObjectForTrees(String treeOne, String treeTwo) {
        TreeOne = treeOne;
        TreeTwo = treeTwo;
    }

    public String getTreeOne() {
        return TreeOne;
    }

    public void setTreeOne(String treeOne) {
        TreeOne = treeOne;
    }

    public String getTreeTwo() {
        return TreeTwo;
    }

    public void setTreeTwo(String treeTwo) {
        TreeTwo = treeTwo;
    }
}
