package laktionov.lifetracker.model;


public class Item {

    private int id;
    private String entry;

    public Item(int id, String entry) {
        this.id = id;
        this.entry = entry;
    }

    public int getId() {
        return id;
    }

    public String getEntry() {
        return entry;
    }
}
