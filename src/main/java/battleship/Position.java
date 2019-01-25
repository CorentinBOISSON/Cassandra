package battleship;

public class Position {

    public boolean bateau_joueur_1;
    public boolean bateau_joueur_2;
    public boolean tire_joueur_1;
    public boolean tire_joueur_2;
    public boolean bateau_joueur_1_touche;
    public boolean bateau_joueur_2_touche;
    public boolean bateau_joueur_1_coule;
    public boolean bateau_joueur_2_coule;
    public int id_bateau_joueur_1; // 0 si pas de bateau
    public int id_bateau_joueur_2; // 0si pas de bateau

    public Position(boolean bateau_joueur_1, boolean bateau_joueur_2, boolean tire_joueur_1, boolean tire_joueur_2, boolean bateau_joueur_1_touche, boolean bateau_joueur_2_touche, boolean bateau_joueur_1_coule, boolean bateau_joueur_2_coule, int id_bateau_joueur_1, int id_bateau_joueur_2) {
        this.bateau_joueur_1 = bateau_joueur_1;
        this.bateau_joueur_2 = bateau_joueur_2;
        this.tire_joueur_1 = tire_joueur_1;
        this.tire_joueur_2 = tire_joueur_2;
        this.bateau_joueur_1_touche = bateau_joueur_1_touche;
        this.bateau_joueur_2_touche = bateau_joueur_2_touche;
        this.bateau_joueur_1_coule = bateau_joueur_1_coule;
        this.bateau_joueur_2_coule = bateau_joueur_2_coule;
        this.id_bateau_joueur_1 = id_bateau_joueur_1;
        this.id_bateau_joueur_2 = id_bateau_joueur_2;
    }

    public boolean isBateau_joueur_1() {
        return bateau_joueur_1;
    }

    public void setBateau_joueur_1(boolean bateau_joueur_1) {
        this.bateau_joueur_1 = bateau_joueur_1;
    }

    public boolean isBateau_joueur_2() {
        return bateau_joueur_2;
    }

    public void setBateau_joueur_2(boolean bateau_joueur_2) {
        this.bateau_joueur_2 = bateau_joueur_2;
    }

    public boolean isTire_joueur1() {
        return tire_joueur_1;
    }

    public void setTire_joueur1(boolean tire_joueur1) {
        this.tire_joueur_1 = tire_joueur1;
    }

    public boolean isTire_joueur2() {
        return tire_joueur_2;
    }

    public void setTire_joueur2(boolean tire_joueur2) {
        this.tire_joueur_2 = tire_joueur2;
    }

    public boolean isBateau_joueur_1_touche() {
        return bateau_joueur_1_touche;
    }

    public void setBateau_joueur_1_touche(boolean bateau_joueur_1_touche) {
        this.bateau_joueur_1_touche = bateau_joueur_1_touche;
    }

    public boolean isBateau_joueur_2_touche() {
        return bateau_joueur_2_touche;
    }

    public void setBateau_joueur_2_touche(boolean bateau_joueur_2_touche) {
        this.bateau_joueur_2_touche = bateau_joueur_2_touche;
    }

    public boolean isBateau_joueur_1_coule() {
        return bateau_joueur_1_coule;
    }

    public void setBateau_joueur_1_coule(boolean bateau_joueur_1_coule) {
        this.bateau_joueur_1_coule = bateau_joueur_1_coule;
    }

    public boolean isBateau_joueur_2_coule() {
        return bateau_joueur_2_coule;
    }

    public void setBateau_joueur_2_coule(boolean bateau_joueur_2_coule) {
        this.bateau_joueur_2_coule = bateau_joueur_2_coule;
    }

    public int getId_bateau_joueur_1() {
        return id_bateau_joueur_1;
    }

    public void setId_bateau_joueur_1(int id_bateau_joueur_1) {
        this.id_bateau_joueur_1 = id_bateau_joueur_1;
    }

    public int getId_bateau_joueur_2() {
        return id_bateau_joueur_2;
    }

    public void setId_bateau_joueur_2(int id_bateau_joueur_2) {
        this.id_bateau_joueur_2 = id_bateau_joueur_2;
    }


}
