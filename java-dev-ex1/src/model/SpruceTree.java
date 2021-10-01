package model;

import model.abstraction.ConiferousTree;

import java.math.BigDecimal;

public class SpruceTree extends ConiferousTree {

    public SpruceTree() {
        super(14, 15, BigDecimal.ONE, BigDecimal.valueOf(21));
    }

    @Override
    public void grow() {
        super.getOlder();
        if (!super.isFullyGrown()) {
            currentHeight = currentHeight.add(BigDecimal.ONE);
            branchCount = branchCount * 2;
            System.out.printf("Pine tree has grown to %f meters, has %d branches and is %d years old%n",
                    currentHeight, branchCount, age);
        } else {
            System.out.println("Spruce tree is already fully grown");
        }
    }

    @Override
    public void bloom() {
        System.out.println("Spruce tree has bloomed with a lot of long-shaped spruce pines");
    }

    @Override
    public void printData() {
        System.out.println("Spruce tree properties:");
        super.printData();
    }
}
