import model.AppleTree;
import model.ChestnutTree;
import model.PineTree;
import model.SpruceTree;
import model.abstraction.Tree;

public class Main {

    public static void main(String[] args) {

        Tree appleTree = new AppleTree();
        appleTree.printData();
        appleTree.grow();
        appleTree.bloom();

        Tree chestnutTree = new ChestnutTree();
        chestnutTree.printData();
        chestnutTree.grow();
        chestnutTree.bloom();

        Tree pineTree = new PineTree();
        pineTree.printData();
        pineTree.grow();
        pineTree.bloom();

        Tree spruceTree = new SpruceTree();
        spruceTree.printData();
        spruceTree.grow();
        spruceTree.bloom();

    }

}
