package model;

import model.abstraction.ConiferousTree;

import java.math.BigDecimal;

public class PineTree extends ConiferousTree {

    public PineTree() {
        super(9, 12, BigDecimal.valueOf(0.8), BigDecimal.valueOf(22));
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
            System.out.println("Pine tree is already fully grown");
        }
    }

    @Override
    public void bloom() {
        System.out.println("Pine tree has bloomed with a lot of pines");
    }

    @Override
    public void printData() {
        System.out.println("Pine tree properties:");
        super.printData();
    }
}
