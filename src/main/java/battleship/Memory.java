package battleship;

public class Memory {

    public boolean random;
    public int[] impact;
    public boolean haut;
    public boolean bas;
    public boolean droite;
    public boolean gauche;
    public int shootsH;
    public int shootsB;
    public int shootsD;
    public int shootsG;

    public Memory(boolean random, int[] impact, boolean haut, boolean bas, boolean droite, boolean gauche, int shootsH, int shootsB, int shootsD, int shootsG) {
        this.random = random;
        this.impact = impact;
        this.haut = haut;
        this.bas = bas;
        this.droite = droite;
        this.gauche = gauche;
        this.shootsH = shootsH;
        this.shootsB = shootsB;
        this.shootsD = shootsD;
        this.shootsG = shootsG;
    }
}
