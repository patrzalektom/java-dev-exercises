package model.enumeration;

public enum TreeType {
    CONIFEROUS("Coniferous"), DECIDUOUS("Deciduous");

    private final String label;

    TreeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
