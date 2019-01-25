package battleship;

import java.util.List;

public class Player {

    public String nom;
    private List<Bateau> Ships;
    private int nbTour;
    public int nbShip;


    public Player(String nom, List<Bateau> ships, int nbTour, int nbShip) {
        this.nom = nom;
        Ships = ships;
        this.nbTour = nbTour;
        this.nbShip = nbShip;
    }


    public String getPlayer() {
        return nom;
    }

    public void setPlayer(String player) {
        this.nom = nom;
    }

    public List<Bateau> getShips() {
        return Ships;
    }

    public void setShips(List<Bateau> ships) {
        Ships = ships;
    }

    public int getNbTour() {
        return nbTour;
    }

    public void setNbTour(int nbTour) {
        this.nbTour = nbTour;
    }

    public int getNbShip() {
        return nbShip;
    }

    public void setNbShip(int nbShip) {
        this.nbShip = nbShip;
    }


}
