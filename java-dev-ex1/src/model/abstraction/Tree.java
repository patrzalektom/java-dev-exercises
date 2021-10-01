package model.abstraction;

import model.enumeration.TreeType;

import java.math.BigDecimal;

public abstract class Tree {

    protected TreeType type;
    protected Integer age;
    protected Integer branchCount;
    protected Integer rootCount;
    protected BigDecimal currentHeight;
    protected BigDecimal maxHeight;

    protected Tree(TreeType type, Integer branchCount, Integer rootCount, BigDecimal currentHeight, BigDecimal maxHeight) {
        this.type = type;
        this.age = 0;
        this.branchCount = branchCount;
        this.rootCount = rootCount;
        this.currentHeight = currentHeight;
        this.maxHeight = maxHeight;
    }

    public abstract void grow();

    public abstract void bloom();

    public void getOlder() {
        this.age += 1;
    }

    public void printData() {
        System.out.printf("Type: %s\nAge: %d\nBranches count: %d\nRoots count: %d\nCurrent height: %f\nMax height: %f\n",
                type.getLabel(), age, branchCount, rootCount, currentHeight, maxHeight);
    }

    public boolean isFullyGrown() {
        return currentHeight.compareTo(maxHeight) != -1;
    }
}
