package model;

import model.abstraction.DeciduousTree;

import java.math.BigDecimal;

public class ChestnutTree extends DeciduousTree {

    public ChestnutTree() {
        super(12, 45, BigDecimal.valueOf(0.3), BigDecimal.valueOf(18));
    }

    @Override
    public void grow() {
        super.getOlder();
        if (!super.isFullyGrown()) {
            currentHeight = currentHeight.add(BigDecimal.valueOf(1.5));
            branchCount = branchCount * 2;
            System.out.printf("Chestnut tree has grown to %f meters, has %d branches and is %d years old%n",
                    currentHeight, branchCount, age);
        } else {
            System.out.println("Chestnut tree is already fully grown");
        }
    }

    @Override
    public void bloom() {
        System.out.println("Chestnut tree has bloomed with huge chestnuts");
    }

    @Override
    public void printData() {
        System.out.println("Chestnut tree properties:");
        super.printData();
    }
}
