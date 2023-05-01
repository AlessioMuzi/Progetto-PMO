package utils.enums;

public enum Difficulty {

    EASY("Facile", 1),
    NORMAL("Normale", 2),
    HARD("Difficile", 4),
    EXTREME("Estrema", 8);
    
    private String name;
    private int    diff;
    
    public String getName() {
        return name;
        }
    
    public int getDiff() {
        return diff;
        }
    
    private Difficulty (String n, int d) {
        this.name = n;
        this.diff = d;
    }
}