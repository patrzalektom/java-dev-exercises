package model;

import model.abstraction.DeciduousTree;

import java.math.BigDecimal;

public class AppleTree extends DeciduousTree {

    public AppleTree() {
        super(16, 30, BigDecimal.valueOf(0.5), BigDecimal.valueOf(15));
    }

    @Override
    public void grow() {
        super.getOlder();
        if (!super.isFullyGrown()) {
            currentHeight = currentHeight.add(BigDecimal.ONE);
            branchCount = branchCount * 2;
            System.out.printf("Apple tree has grown to %f meters, has %d branches and is %d years old%n",
                    currentHeight, branchCount, age);
        } else {
            System.out.println("Apple tree is already fully grown");
        }
    }

    @Override
    public void bloom() {
        System.out.println("Apple tree has bloomed with delicious apples");
    }

    @Override
    public void printData() {
        System.out.println("Apple tree properties:");
        super.printData();
    }
}
