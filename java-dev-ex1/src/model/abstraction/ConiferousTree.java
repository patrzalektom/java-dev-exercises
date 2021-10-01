package model.abstraction;

import model.enumeration.TreeType;

import java.math.BigDecimal;

public abstract class ConiferousTree extends Tree {

    protected ConiferousTree(Integer branchCount, Integer rootCount, BigDecimal currentHeight, BigDecimal maxHeight) {
        super(TreeType.CONIFEROUS, branchCount, rootCount, currentHeight, maxHeight);
    }

}
