package model.abstraction;

import model.enumeration.TreeType;

import java.math.BigDecimal;

public abstract class DeciduousTree extends Tree {

    protected DeciduousTree(Integer branchCount, Integer rootCount, BigDecimal currentHeight, BigDecimal maxHeight) {
        super(TreeType.DECIDUOUS, branchCount, rootCount, currentHeight, maxHeight);
    }

}
