package UNOGame;

public class Card {
    private String type;
    private int num;
    private String color;
    private String owner;
    private int ID;

    public Card(final String name, final int num, final String color, final String owner, final int id) {
        this.type = name;
        this.num = num;
        this.color = color;
        this.owner = owner;
        this.ID = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Card [ID=" + ID + ", color=" + color + ", num=" + num + ", owner=" + owner + ", type=" + type + "]";
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public void setID(final int iD) {
        ID = iD;
    }
}