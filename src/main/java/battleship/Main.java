package battleship;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //fonction de tir utilisee par J1, a pour role d'actualiser les attributs (touche, coule, etc) des positions dans le plateau de jeux
    private static boolean TirJ1 (Position [][] plateau, Player J1) {
        boolean touche =false;
        System.out.println("C'est à votre tour de tirer.");
        int[] coordonnees = EntrerCoordonnes();
        if (plateau[coordonnees[0]][coordonnees[1]].tire_joueur_1) {//tant que le joueur tir sur un emplacement deja tire, la fonction TirJ1 est appelee
            System.out.println("Vous avez déjà tiré sur cette case. Veuillez renseigner une autre cible.");
            System.out.println();
            TirJ1 (plateau, J1);
        }
        else {
            plateau[coordonnees[0]][coordonnees[1]].tire_joueur_1 = true; //actualise position
            if (!plateau[coordonnees[0]][coordonnees[1]].bateau_joueur_2) {
                System.out.println("Dans l'eau !");
                System.out.println();
            }
            else {
                System.out.println("Vous avez touche un bateau adverse !");
                plateau[coordonnees[0]][coordonnees[1]].bateau_joueur_2_touche = true; //actualise position
                estCoule(plateau, coordonnees, J1);
                touche=true;
            }
        }
        return (touche);
    }

    //fonction de tir utilisee par l'ordi, a pour role d'actualiser les attributs des positions dans le plateau de jeu
    private static int TirOrdi (Position [][] plateau, Player J2, int[] coordonnees) {
        int tir; //si 0 tir dans l'eau ; si 1 touche bateau de J1 ; si 2 coule bateau de J1 ; si 3 deja tiré
        //indication pour l'ordi
        if (plateau[coordonnees[0]][coordonnees[1]].tire_joueur_2) {
            tir = 3;
        }
        else {
            plateau[coordonnees[0]][coordonnees[1]].tire_joueur_2 = true;
            if (!plateau[coordonnees[0]][coordonnees[1]].bateau_joueur_1) {
                tir = 0;
                System.out.println("L'ordinateur a tire dans l'eau !");
                System.out.println();
            }
            else {
                plateau[coordonnees[0]][coordonnees[1]].bateau_joueur_1_touche = true;
                tir = estCoule(plateau, coordonnees, J2);
                if (tir == 1) {
                    System.out.println("L'ordinateur a touche un bateau !");
                    System.out.println();
                }
                else if (tir == 2){
                    System.out.println("L'ordinateur a coule un bateau !");
                    System.out.println();
                }
            }
        }
        return tir;
    }

    //fonction qui verifie si le bateau est coule et qui met a jour les statuts des positions ainsi que le nombre de bateau du joueur
    //cette fonction est appelee par TirJ1 et TirOrdi
    private static int estCoule(Position[][] plateau, int[] coordonnees, Player J) { //fonction indique si navire adverse est coule
        int id;
        if (J.nom.equals("Ordinateur")) {
            id = plateau[coordonnees[0]][coordonnees[1]].id_bateau_joueur_1;
        }
        else{
            id = plateau[coordonnees[0]][coordonnees[1]].id_bateau_joueur_2;
        }
        int tir = 1; //informe la machine, pour l'instant elle a juste touche le bateau de J1
        boolean estcoule = true;
        int[] stock = new int[20];
        int k = 0;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[0].length; j++) {
                if (J.nom.equals("Ordinateur")) {
                    if (!plateau[i][j].bateau_joueur_1_touche && plateau[i][j].id_bateau_joueur_1 == id) {
                        estcoule = false;
                    } else if (plateau[i][j].bateau_joueur_1_touche && plateau[i][j].id_bateau_joueur_1 == id) {
                        stock[k] = i;
                        stock[k + 1] = j;
                        k = k + 2;
                    }
                }
                else {
                    if (!plateau[i][j].bateau_joueur_2_touche && plateau[i][j].id_bateau_joueur_2 == id) {
                        estcoule = false;
                    } else if (plateau[i][j].bateau_joueur_2_touche && plateau[i][j].id_bateau_joueur_2 == id) {
                        stock[k] = i;
                        stock[k + 1] = j;
                        k = k + 2;
                    }
                }
            }
        }
        if (estcoule && J.nom.equals("Ordinateur")) {
            for (int i = 0; i <= k; i = i + 2) {
                plateau[stock[i]][stock[i + 1]].bateau_joueur_1_coule = true;
            }
            J.nbShip--;
            tir = 3; //informe la machine
        }
        else if (estcoule && !J.nom.equals("Ordinateur")){
            for (int j = 0; j <= k; j = j + 2) {
                plateau[stock[j]][stock[j + 1]].bateau_joueur_2_coule = true;
            }
            J.nbShip--;
            System.out.println("Felicitation, vous avez coule le bateau adverse !"); //informe le joueur
            System.out.println();
        }

        return tir; //informe la machine
    }

    //sous fonction qui place un bateau de maniere aleatoire sur le plateau pour l'ordi en actualisant les attributs des positions du plateau de jeu
    private static Bateau placement2(Position[][] plateau, int taille, int id, String name_joueur){
        int [] coordonnees =new int[2];
        boolean placement=false;
        int ligne;
        int colonne;
        int test= (int)(Math.random()*100);
        boolean  horizontal= test>=50;
        while (!placement){
            if (horizontal){
                ligne = (int)(Math.random() * plateau.length);
                colonne = (int)(Math.random() * plateau[0].length-taille);
            }
            else{
                ligne = (int)(Math.random() * plateau.length-taille);
                colonne = (int)(Math.random() * plateau[0].length);
            }
            coordonnees[0]=ligne;
            coordonnees[1]=colonne;
            placement=placer_bateau_Ordi(plateau,taille,coordonnees,horizontal,id);
            test= (int)(Math.random()*100);
            horizontal= test>=50;
        }
        int[][]positions;
        positions = TestHorizontal(horizontal,coordonnees,taille);
        return(new Bateau(name_joueur,id,taille,positions,false));
    }

    //sous fonction de placement2
    private static boolean placer_bateau_Ordi(Position[][] plateau, int taille, int[] coordonnees, boolean horizontal, int id){
        int hauteur = plateau.length;
        int largeur = plateau[0].length;
        boolean effectue =false;
        boolean place =true;
        if (coordonnees[0]>=0 && coordonnees[0]<hauteur && coordonnees[1]>=0 && coordonnees[1]<largeur) {
            if (horizontal) {
                if (coordonnees[1]+taille <= largeur){
                    for (int i =coordonnees[1];i<coordonnees[1]+taille; i++ ){
                        if (plateau[coordonnees[0]][i].bateau_joueur_2){
                            place=false;
                        }
                    }
                    if (place){
                        for (int i =coordonnees[1];i<coordonnees[1]+taille; i++){
                            plateau[coordonnees[0]][i].bateau_joueur_2 = true;
                            plateau[coordonnees[0]][i].id_bateau_joueur_2 = id;
                            effectue=true;
                        }
                    }
                }
            }
            else {
                if (coordonnees[0]+taille <= hauteur){
                    for (int i =coordonnees[0];i<coordonnees[0]+taille; i++){
                        if ((plateau[i][coordonnees[1]]).bateau_joueur_2){
                            place=false;
                        }
                    }
                    if (place){
                        for (int i =coordonnees[0];i<coordonnees[0]+taille; i++ ){
                            plateau[i][coordonnees[1]].bateau_joueur_2 = true;
                            plateau[i][coordonnees[1]].id_bateau_joueur_2 = id;
                            effectue=true;
                        }
                    }
                }
            }
        }
        return(effectue);
    }

    //fonction d'affichage
    private static void AfficherPlateauJ1 (Position[][] plateau){
        String blanc = "                                           ";
        System.out.println(blanc + "Plateau de placement du joueur");
        System.out.print(blanc + "  |1|2|3|4|5|6|7|8|9|10|");
        for (int i=0; i<plateau.length; i++){
            System.out.println();
            String lettres [] = {blanc + "|A|", blanc + "|B|", blanc + "|C|", blanc + "|D|", blanc + "|E|", blanc + "|F|", blanc + "|G|", blanc + "|H|", blanc + "|I|", blanc + "|J|"};
            System.out.print(lettres[i]);
            for (int j=0; j<plateau[0].length; j++){
                Position p = plateau[i][j];
                if(p.id_bateau_joueur_1 == 0 && !p.tire_joueur_2){
                    System.out.print(" |");
                } else if (p.id_bateau_joueur_1 == 0 && p.tire_joueur_2){ //warning utile car sinon exclu des cas
                    System.out.print("*|"); }
                else if (p.id_bateau_joueur_1 != 0 && !p.bateau_joueur_1_touche){ //warning utile car sinon exclu des cas
                    System.out.print(0 +"|");
                } else if (p.id_bateau_joueur_1 != 0 && p.bateau_joueur_1_touche){ //warning utile car sinon exclu des cas
                    System.out.print("X|");
                }
            }
        }
        System.out.println();
        System.out.print("Legende : | | = eau ; |*| = tir du joueur adverse ; |0| = presence bateau non touche ; |X| = notre position est touchee");
        System.out.println();
    }

    //fonction d'affichage
    private static void AfficherPlateauOrdi (Position[][] plateau){
        String blanc = "                                           ";
        System.out.println(blanc + "  Plateau ordinateur petit tricheur");
        System.out.print(blanc + "  |1|2|3|4|5|6|7|8|9|10|");
        for (int i=0; i<plateau.length; i++){
            System.out.println();
            String lettres [] = {blanc + "|A|", blanc + "|B|", blanc + "|C|", blanc + "|D|", blanc + "|E|", blanc + "|F|", blanc + "|G|", blanc + "|H|", blanc + "|I|", blanc + "|J|"};
            System.out.print(lettres[i]);
            for (int j=0; j<plateau[0].length; j++){
                Position p = plateau[i][j];
                if(p.id_bateau_joueur_2 == 0 && !p.tire_joueur_1){
                    System.out.print(" |");
                } else if (p.id_bateau_joueur_2 == 0 && p.tire_joueur_1){ //warning utile car sinon exclu des cas
                    System.out.print("*|");
                } else if (p.id_bateau_joueur_2 != 0 && !p.bateau_joueur_2_touche){ //warning utile car sinon exclu des cas
                    System.out.print(0 +"|");
                } else if (p.id_bateau_joueur_2 != 0 && p.bateau_joueur_2_touche){ //warning utile car sinon exclu des cas
                    System.out.print("X|");
                }
            }
        }
        System.out.println();
        System.out.print("Legende : | | = eau ; |*| = tir du joueur adverse ; |0| = presence bateau non touche ; |X| = notre position est touchee");
        System.out.println();
    }

    //fonction d'affichage
    private static void AfficherPlateauAttaqueJ1 (Position[][] plateau) {
        String blanc = "                                           ";
        System.out.println(blanc + "Plateau d'attaque du joueur ");
        System.out.print(blanc + "  |1|2|3|4|5|6|7|8|9|10|");
        for (int i = 0; i < plateau.length; i++) {
            System.out.println();
            String lettres [] = {blanc + "|A|", blanc + "|B|", blanc + "|C|", blanc + "|D|", blanc + "|E|", blanc + "|F|", blanc + "|G|", blanc + "|H|", blanc + "|I|", blanc + "|J|"};
            System.out.print(lettres[i]);
            for (int j = 0; j < plateau[0].length; j++) {
                Position p = plateau[i][j];
                if (!p.tire_joueur_1) {
                    System.out.print(" |");
                } else if (p.tire_joueur_1 && !p.bateau_joueur_2) {
                    System.out.print("*|");
                } else if (p.bateau_joueur_2_touche && !p.bateau_joueur_2_coule) {
                    System.out.print("+|");
                } else if (p.bateau_joueur_2_coule) {
                    System.out.print("X|");
                }
            }
        }
        System.out.println();
        System.out.print("Legende : | | = eau ; |*| = tir dans l'eau ; |+| = tir sur bateau adverse ; |X| = bateau adverse coule");
        System.out.println();
    }

    //fonction qui place un bateau du joueur en fonction de sa demande en actualisant les attributs des positions du plateau
    private static Bateau placement(Position[][] plateau, int taille, int id, String name_joueur){
        char reponse;
        boolean horizontal = true;
        int [] coordonnees =new int[2];
        boolean placement=false;
        while (!placement){
            System.out.println("Placement du bateau de taille " + taille + " :");
            coordonnees = EntrerCoordonnes();
            ///
            System.out.println("Voulez-vous placer votre bateau à l'horizontale (H) ou à la verticale (V) ?");
            Scanner sc3 = new Scanner(System.in);
            String str2 = sc3.nextLine();
            reponse = str2.charAt(0);
            ///
            if (reponse=='V') {
                horizontal=false;
            }
            placement=placer_bateau_J1(plateau,taille,coordonnees,horizontal,id);
        }
        int[][]positions=new int[taille][2];
        positions = TestHorizontal(horizontal,coordonnees,taille);
        return(new Bateau(name_joueur,id,taille,positions,false));
    }

    //sous fonction de placement
    private static boolean placer_bateau_J1(Position[][] plateau, int taille, int[] coordonnees, boolean horizontal, int id){
        int hauteur = plateau.length;
        int largeur = plateau[0].length;
        boolean effectue =false;
        boolean place =true;
        if (coordonnees[0]>=0 && coordonnees[0]<hauteur && coordonnees[1]>=0 && coordonnees[1]<largeur) {
            if (horizontal) {
                if (coordonnees[1]+taille <= largeur){
                    for (int i =coordonnees[1];i<coordonnees[1]+taille; i++ ){
                        if (plateau[coordonnees[0]][i].bateau_joueur_1){
                            place=false;
                            System.out.print("Un bateau est deja place a cet endroit.");
                            System.out.println();
                            System.out.println();
                        }
                    }
                    if (place){
                        for (int i =coordonnees[1];i<coordonnees[1]+taille; i++ ){
                            plateau[coordonnees[0]][i].bateau_joueur_1 = true;
                            plateau[coordonnees[0]][i].id_bateau_joueur_1 = id;
                            effectue=true;
                        }
                    }
                }
                else {
                    System.out.print("Votre navire ne rentre pas dans le plateau.");
                    System.out.println();
                    System.out.println();
                }
            }
            else {
                if (coordonnees[0]+taille <= hauteur){
                    for (int i =coordonnees[0];i<coordonnees[0]+taille; i++ ){
                        if ((plateau[i][coordonnees[1]]).bateau_joueur_1){
                            place=false;
                            System.out.print("Un bateau est deja place a cet endroit.");
                            System.out.println();
                            System.out.println();
                        }
                    }
                    if (place){
                        for (int i =coordonnees[0];i<coordonnees[0]+taille; i++ ){
                            plateau[i][coordonnees[1]].bateau_joueur_1 = true;
                            plateau[i][coordonnees[1]].id_bateau_joueur_1 = id;
                            effectue=true;
                        }
                    }
                }
                else {
                    System.out.print("Votre navire ne rentre pas dans le plateau.");
                    System.out.println();
                    System.out.println();
                }
            }
        }
        else{
            System.out.print("Oups, la ligne ou colonne que tu as saisi est invalide.");
            System.out.println();
            System.out.println();
        }
        return(effectue);
    }

    //sous fonction pour eviter une redondance de code
    private static int [][] TestHorizontal(boolean horizontal, int[] coordonnees, int taille) {
        int[][] positions = new int[taille][2];
        if (horizontal) {
            for (int i = 0; i < taille; i++) {
                positions[i][0] = coordonnees[0];
                positions[i][1] = coordonnees[1] + i;
            }
        } else {
            for (int i = 0; i < taille; i++) {
                positions[i][0] = coordonnees[0] + i;
                positions[i][1] = coordonnees[1];
            }
        }
        return positions;
    }

    //fonction qui traite les coordonnees pour le placement et le tir du joueur et le changement des lettres en numero de ligne
    private static int[] EntrerCoordonnes() {
        int Ligne=-1;
        int number;
        int Colonne=0;
        char ligne;
        int code=0;
        String str ="";
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        while(code <10 || (code >19 && code < 106) || code > 107) {
            System.out.println("Veuillez saisir la ligne (lettre) :");
            sc = new Scanner(System.in);
            str = sc.nextLine();
            ligne = str.charAt(0);

            sc2 = new Scanner(System.in);
            number = readInt(sc2, "Veuillez saisir la colonne (chiffre) :", "Ceci n'est pas un nombre entier inférieur à 10. Recommencez : ");
            Colonne = number - 1;

            code = Character.getNumericValue(ligne);
            if (code >= 10 && code <= 35) {
                Ligne = code - 10;
            } else {
                if (code >= 97 && code <= 107) {
                    Ligne = code - 97;
                }
            }
        }
        return(new int[]{Ligne, Colonne});
    }

    //sous fonction de EntrerCoordonnees
    private static int readInt(Scanner scanner, String prompt, String promptOnError) {
        System.out.println(prompt);
        while ( !scanner.hasNextInt()) {
            System.out.print(promptOnError);
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    //fonction qui indique une position a l'ordi pour effectuer un tir expliquee dans le readMe
    private static boolean StrategieOrdi(Position[][] plateau, Player J2, Memory memory){
        int tire =3;
        boolean touche=false;
        int[]coordonnees=new int[2];
        int ligne;
        int colonne;
        if (memory.random) {
            while (tire == 3) {
                ligne = (int) (Math.random() * plateau.length);
                if (ligne % 2 == 0) {
                    colonne = (((int) (Math.random() * (plateau[0].length) / 2)) * 2) + 1;
                }
                else {
                    colonne = ((int) (Math.random() * (plateau[0].length) / 2)) * 2;
                }
                coordonnees[0] = ligne;
                coordonnees[1] = colonne;
                tire = TirOrdi(plateau, J2, coordonnees);
                if (tire == 2 || tire == 0) {
                    memory.random = true;
                }
                if (tire == 1) {
                    memory.random = false;
                }
                if (tire == 1 || tire == 2) {
                    memory.impact = coordonnees;
                    memory.shootsB=memory.shootsH=memory.shootsD=memory.shootsG=1;
                }
            }
        }
        else {
            while (tire == 3) {
                if (memory.haut) {
                    coordonnees[0] = memory.impact[0]-memory.shootsH;
                    coordonnees[1] = memory.impact[1];
                    if (coordonnees[0]>=0 && coordonnees[0]<plateau.length && coordonnees[1]>=0 && coordonnees[1]<plateau[0].length){
                        tire = TirOrdi(plateau, J2, coordonnees);
                    }
                    else{
                        memory.haut=false;
                    }
                    if (tire == 2) {
                        ActualisationMemory(memory);
                    }
                    if (tire == 1) {
                        memory.shootsH++;
                    }
                    if (tire == 0) {
                        memory.haut=false;
                    }
                    if (tire == 3) {
                        memory.haut=false;
                    }
                }
                else {
                    if (memory.bas) {
                        coordonnees[0] = memory.impact[0]+memory.shootsB;
                        coordonnees[1] = memory.impact[1];
                        if (coordonnees[0]>=0 && coordonnees[0]<plateau.length && coordonnees[1]>=0 && coordonnees[1]<plateau[0].length){
                            tire = TirOrdi(plateau, J2, coordonnees);
                        }
                        else{
                            memory.bas=false;
                        }
                        if (tire == 2) {
                            ActualisationMemory(memory);
                        }
                        if (tire == 1) {
                            memory.shootsB++;
                        }
                        if (tire == 0) {
                            memory.bas=false;
                        }
                        if (tire == 3) {
                            memory.bas=false;
                        }
                    }
                    else {
                        if (memory.droite) {
                            coordonnees[0] = memory.impact[0];
                            coordonnees[1] = memory.impact[1]+memory.shootsD;
                            if (coordonnees[0]>=0 && coordonnees[0]<plateau.length && coordonnees[1]>=0 && coordonnees[1]<plateau[0].length){
                                tire = TirOrdi(plateau, J2, coordonnees);
                            }
                            else{
                                memory.droite=false;
                            }
                            if (tire == 2) {
                                ActualisationMemory(memory);
                            }
                            if (tire == 1) {
                                memory.shootsD++;
                            }
                            if (tire == 0) {
                                memory.droite=false;
                            }
                            if (tire == 3) {
                                memory.droite=false;
                            }
                        }
                        else {
                            if (memory.gauche) {
                                coordonnees[0] = memory.impact[0];
                                coordonnees[1] = memory.impact[1]-memory.shootsG;
                                if (coordonnees[0]>=0 && coordonnees[0]<plateau.length && coordonnees[1]>=0 && coordonnees[1]<plateau[0].length){
                                    tire = TirOrdi(plateau, J2, coordonnees);
                                }
                                else{
                                    memory.gauche=false;
                                }
                                if (tire == 2) {
                                    ActualisationMemory(memory);
                                }
                                if (tire == 1) {
                                    memory.shootsG++;
                                }
                                if (tire == 0) {
                                    memory.gauche=false;
                                }
                                if (tire == 3) {
                                    memory.gauche=false;
                                }
                            }
                            else {
                                ActualisationMemory(memory);
                            }
                        }
                    }
                }
            }
        }
        if (tire == 1 || tire == 2) {
            touche = true;
        }
        return(touche);
    }

    //sous fonction de StrategieOrdi
    static void ActualisationMemory (Memory memory){
        memory.random = true;
        memory.haut=true;
        memory.bas=true;
        memory.droite=true;
        memory.gauche=true;
        memory.shootsB=memory.shootsH=memory.shootsD=memory.shootsG=0;
    }

    //fonction principale de deroulement du jeu
    private static void Jeu(int hauteur, int largeur){
        Position[][] plateau = new Position[hauteur][largeur];
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                plateau[i][j] = new Position(false, false, false, false, false, false, false, false, 0, 0);
            }
        }
        System.out.println("Veuillez saisir votre nom :");
        Scanner sc = new Scanner(System.in);
        String name1 = sc.nextLine();
        List<Bateau> bateaux1 = new ArrayList<Bateau>();
        for (int i = 2; i <= 6; i++) {
            AfficherPlateauJ1(plateau);
            System.out.println();
            bateaux1.add(placement(plateau, i, i + 98, name1));
        }
        AfficherPlateauJ1(plateau);
        System.out.println();
        Player J1 = new Player(name1, bateaux1, 0, 5);

        List<Bateau> bateaux2 = new ArrayList<Bateau>();
        for (int i = 2; i <= 6; i++) {
            bateaux2.add(placement2(plateau, i, i + 198, name1));
        }
        Player J2 = new Player("Ordinateur", bateaux2, 0, 5);
        boolean tour1;
        boolean tour2;
        int [] coordonnees=new int[2];
        boolean haut=true;
        boolean bas=true;
        boolean droite=true;
        boolean gauche=true;
        Memory memory= new Memory(true,coordonnees,haut,bas,droite,gauche,0,0,0,0);
       while (J1.nbShip!=0 && J2.nbShip!=0) {
           tour1=true;
           while (tour1){
               AfficherPlateauOrdi(plateau);   //NE PAS METTRE CETTE LIGNE EN COMMENTAIRE AFIN DE VISUALISER
               //LES BATEAUX DE L'ORDINATEUR ET AINSI POUVOIR GAGNER LA PARTIE
               AfficherPlateauAttaqueJ1(plateau);
               tour1=TirJ1(plateau, J1);
           }
           tour2=true;
           while (tour2){
               tour2=StrategieOrdi(plateau, J2, memory);
           }
           AfficherPlateauJ1(plateau);
       }
       if (J2.nbShip==0) {
           System.out.println("L'ordinateur a gagné, retenter votre chance !");
       }
       else{
           System.out.println("Bravo " + J1.nom + " ! Vous avez gagné :D ");
       }
    }

    public static void main(String[] args) {
        Jeu(10,10);
    }
}

