package battleship;

public class Bateau {


    private String joueur;
    private int id;
    private int taille;
    private int [][] coordonnées;
    private boolean coule;

    public Bateau(String joueur, int id, int taille, int[][] coordonnées, boolean coule) {
        this.joueur = joueur;
        this.id = id;
        this.taille = taille;
        this.coordonnées = coordonnées;
        this.coule = coule;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int[][] getCoordonnées() {
        return coordonnées;
    }

    public void setCoordonnées(int[][] coordonnées) {
        this.coordonnées = coordonnées;
    }

    public boolean isCoule() {
        return coule;
    }

    public void setCoule(boolean coule) {
        this.coule = coule;
    }
}
