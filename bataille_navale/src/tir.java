import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class tir extends Application {
    Label marin = new Label("Cliquez une case puis une autre pour placer un bateau.");
    List<Integer> cases_invalides_placement_ia = new ArrayList<>();
    Label torpilleurs = new Label("");
    Label cuirasses = new Label("");
    Label croiseurs = new Label("");
    Label destroyers = new Label("");
    public void placement_vertical_horizontal(boolean vertical, int[] cas, int taille_bat, String bateau, ToggleButton bt_type, ToggleButton[][] boutons, int taille_plateau, int y, int x, int[] compteur,boolean ia){
        //Les valeurs absolues donnent la taille du bateau placé par le joueur.
        //Mettre le compteur à 0 permet au joueur de recommencer un placement.
        //Le compteur donne le nombre de cases placées par le joueur sachant qu'à 2 cases prises,
        //un bateau est placé (ou le placement est invalide) et le compteur est remis à O.
        if ((abs(cas[1] - cas[0])) >= taille_bat) {
            //System.out.println("Votre " + bateau + " est trop grand (il doit faire " + taille_bat + "  cases). Veuillez recommencer votre placement");
            marin.setText("Votre " + bateau + " est trop grand (il doit faire " + taille_bat + "  cases).");
            bt_type.getProperties().put("compteur",0);
            compteur[0]=0;
        } else if (abs(cas[1] - cas[0]) < (taille_bat - 1) ) {
            marin.setText("Votre " + bateau + " est trop petit (il doit faire " + taille_bat + "  cases).");
            bt_type.getProperties().put("compteur",0);
            compteur[0]=0;
        } else {
            //On remplit les cases autour du bateau de cases de placement invalide.
            for(int i = -1;i<2;i++){
                for (int z = min(cas[0]-1, cas[1]-1); z <= max(cas[0]+1, cas[1]+1); z++) {
                    if (vertical){
                        if((x+i)>=0 && (x+i)<=(taille_plateau-1) && z>=0 && z<=(taille_plateau-1)){
                            //boutons[x+i][z].setText("x");
                            if(!ia){
                            boutons[x+i][z].getProperties().put("cases_prises","x");
                            boutons[x+i][z].getStyleClass().add("bat_rempli");
                            }else{
                                cases_invalides_placement_ia.add(z*10+(x+i));
                                boutons[x+i][z].getProperties().put("cases_prises", "x");
                                boutons[x+i][z].getProperties().put(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString(),"x" );

                            }

                        }
                    }else{
                        if((y+i)>=0 && (y+i)<=(taille_plateau-1) && z>=0 && z<=(taille_plateau-1)){
                            //boutons[z][y+i].setText("x");
                            if(!ia) {
                                boutons[z][y + i].getProperties().put("cases_prises", "x");
                                boutons[z][y + i].getStyleClass().add("bat_rempli");
                            }else{
                                 cases_invalides_placement_ia.add((y+i)*10+z);
                                boutons[z][y + i].getProperties().put("cases_prises", "x");
                                boutons[z][y+i].getProperties().put(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString(),"x" );
                            }
                        }
                    }
                }
            }
            cases_valides_placement_ia.removeAll(cases_invalides_placement_ia);
            cases_invalides_placement_ia.clear();
            //On place le bateau entre la plus petite case donnée et la plus grande.
            for (int z = min(cas[0], cas[1]); z <= max(cas[0], cas[1]); z++) {
                if (vertical){
                    // On affiche le bateau sur la carte
                    boutons[x][z].setText("⬛");
                    // On stocke son affichage pour pouvoir le réafficher à tout moment
                    boutons[x][z].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                    //On stocke le type du bateau et son numéro dans les boutons (pour le tir)
                    boutons[x][z].getProperties().put("carte_bat"+(etape-1),bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                    boutons[x][z].getStyleClass().add("bat_rempli");

                }else{
                    boutons[z][y].setText("⬛");
                    boutons[z][y].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                    //boutons[z][y].setText(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                    boutons[z][y].getProperties().put("carte_bat"+(etape-1),bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                    boutons[z][y].getStyleClass().add("bat_rempli");
                }
            }
        }
    }
    //A CHANGER D'EMPLACEMENT
    final ToggleButton bt_tirer = new ToggleButton("Tirer");
    ToggleButton bt_type_cuirasse = new ToggleButton("Cuirassé");
    ToggleButton bt_type_croiseur = new ToggleButton("Croiseur");
    ToggleButton bt_type_destroyer = new ToggleButton("Destroyer");
    ToggleButton bt_type_torpilleur = new ToggleButton("Torpilleur");
    Button mode_jcj = new Button("Joueur contre joueur");
    Button mode_ia_1 = new Button("Facile");
    Button mode_ia_2 = new Button("Intermédiaire");
    Button mode_ia_3 = new Button("Difficile");
    List<Button> liste_modes = Arrays.asList(
            mode_jcj,
            mode_ia_1,
            mode_ia_2,
            mode_ia_3
    );
    ToggleGroup bt_type = new ToggleGroup();
    List<ToggleButton> liste_bt = Arrays.asList(
            bt_type_cuirasse,
            bt_type_croiseur,
            bt_type_destroyer,
            bt_type_torpilleur
    );
    Button avec_aide = new Button("Activer l'aide");
    boolean aide_joueur = false;
    public void reinitialiser_plateau(){
        for (int o = 0; o < taille_plateau; o++) {
            for (int p = 0; p < taille_plateau; p++) {

                boutons[o][p].getProperties().put("cases_prises", 0);
                boutons[o][p].setText("");
            }
        }
        for(ToggleButton z : liste_bt ){
            z.setDisable(false);
            z.getProperties().put("nb_bat_places",0);
            z.getProperties().put("est_place",0);
            z.getProperties().put("compteur",0);
        }
    }

    public void effacer_plateau(){
        for (int o = 0; o < taille_plateau; o++) {
            for (int p = 0; p < taille_plateau; p++) {
                boutons[o][p].setText("");
                boutons[o][p].getStyleClass().add("bat_rempli");
            }
        }
    }

    public void afficher_plateau(int tour){
        for (int o = 0; o < taille_plateau; o++) {
            for (int p = 0; p < taille_plateau; p++) {
                boutons[o][p].getStyleClass().removeAll("manque", "touche", "touche_avant", "manque_avant");
                String coup = boutons[o][p].getProperties().get("coups"+(tour%2)).toString();
                boutons[o][p].setText(boutons[o][p].getProperties().get("carte_bat_indicative"+(tour%2)).toString());
                boutons[o][p].getStyleClass().add("carte_bat_indicative");
                if (!boutons[o][p].getProperties().get("coups"+(tour%2)).toString().isEmpty()) {
                    boutons[o][p].setText(boutons[o][p].getProperties().get("coups" + (tour%2)).toString());
                    switch (coup) {
                        case "❌" -> boutons[o][p].getStyleClass().add("manque");
                        case "🔥" -> boutons[o][p].getStyleClass().add("touche_avant");
                        case "🌊" -> boutons[o][p].getStyleClass().add("manque_avant");
                    }
                }

            }
        }
    }
    public void afficher_indications(String index_bateau){
        for (int o = 0; o < taille_plateau; o++) {
            for (int p = 0; p < taille_plateau; p++) {
                if(boutons[o][p].getProperties().get(index_bateau)!=null){
                String coup = boutons[o][p].getProperties().get(index_bateau).toString();

                if(coup.equals("x")&&!boutons[o][p].getText().equals("🔥")){
                    boutons[o][p].setText("🌊");
                    boutons[o][p].getStyleClass().add("manque_avant");
                }}

            }
        }
    }
    int indicateur_x =-1;
    int indicateur_y =-1;
    // PV des bateaux : index 0=cuirassé, 1=croiseur, 2=destroyer, 3=torpilleur
    final private int[][][] pv_bateaux = {{
            {4,0,0,0},
            {3,3,0,0},
            {2,2,2,0},
            {1,1,1,1}},
            {       {4,0,0,0},
                    {3,3,0,0},
                    {2,2,2,0},
                    {1,1,1,1}}};
    // Les etapes permettent de passer d'un plateau à un autre puis à la phase de tir.
    int mode=0;

    int etape = 1;

    final private int[] bateaux_coules = {0,0};

    final int[] bateaux_a_placer = {4,3,3,2,2,2,1,1,1,1};
    boolean vertical;
    int compt[];
    List<Integer> cases_valides_placement_ia = new ArrayList<>();
    public void placement_ia(int taille_bat){
        int[] cas ={0,0};
        //On place toujours une droite, donc soit le x soit le y reste constant, c'est ici l'"autre_co"
        int autre_co;
        boolean placement_impossible = false;
        //On choisit aléatoirement un placement vertical ou horizontal.
        int vh = (int)(Math.random()*2);
        if (vh==0){
            vertical=true;
        }else{
            vertical=false;
        }
        if(taille_bat>1){

            autre_co=(int)(Math.random()*taille_plateau);
            int xmin=taille_bat;
            int xmax=taille_plateau-taille_bat;

            cas[0]=(int)(Math.random()*(xmax-xmin+1))+xmin;
            //A REVOIR
                cas[1]=cas[0]+((int)Math.random()*(2*taille_bat-1)-taille_bat+1);
            if (vertical){
                if(boutons[autre_co][cas[0]].getProperties().get("cases_prises").equals("x")||boutons[autre_co][cas[1]].getProperties().get("cases_prises").equals("x")){
                    bt_type_torpilleur.getProperties().put("compteur",0);
                    placement_impossible = true;
                }
            }else{
                if(boutons[cas[0]][autre_co].getProperties().get("cases_prises").equals("x")||boutons[cas[1]][autre_co].getProperties().get("cases_prises").equals("x")){
                    bt_type_torpilleur.getProperties().put("compteur",0);
                    placement_impossible = true;
                }
            }
            if(!placement_impossible){
                switch (taille_bat) {
                    case 4:placement_vertical_horizontal(vertical,cas,taille_bat,"Cuirassé",bt_type_cuirasse,boutons,taille_plateau,autre_co,autre_co,compt,true);
                        break;
                    case 3:placement_vertical_horizontal(vertical,cas,taille_bat,"Croiseur",bt_type_croiseur,boutons,taille_plateau,autre_co,autre_co,compt,true);
                        bt_type_croiseur.getProperties().put("nb_bat_places",(int)bt_type_croiseur.getProperties().get("nb_bat_places")+1);
                    break;
                    case 2:placement_vertical_horizontal(vertical,cas,taille_bat,"Destroyer",bt_type_destroyer,boutons,taille_plateau,autre_co,autre_co,compt,true);
                        bt_type_destroyer.getProperties().put("nb_bat_places",(int)bt_type_destroyer.getProperties().get("nb_bat_places")+1);
                    break;
                }
            }else{
                //Si le placement est impossible le programme refait le placement.
                placement_ia(taille_bat);
            }
        }else{
            int case_aleatoire = cases_valides_placement_ia.get(new Random().nextInt(cases_valides_placement_ia.size()));
            int x=case_aleatoire%10;
            int y=(case_aleatoire-case_aleatoire%10)/10;
//            int x=(int)(Math.random()*taille_plateau);
//            int y=(int)(Math.random()*taille_plateau);
            if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                placement_ia(1);
            }else{

                    bt_type_torpilleur.getProperties().put("compteur",0);
                    bt_type_torpilleur.getProperties().put("nb_bat_places",(int)bt_type_torpilleur.getProperties().get("nb_bat_places")+1);
                    for(int i=-1;i<2;i++){
                        for(int z=-1;z<2;z++) {
                            if(x+i>=0 && x+i<=(taille_plateau-1) && y+z>=0 &&y+z<=(taille_plateau-1)){
                                boutons[x+i][y+z].getProperties().put("cases_prises","x");
                                //boutons[x+i][y+z].setText("x");
                                boutons[x+i][y+z].getStyleClass().add("bat_rempli");
                                cases_invalides_placement_ia.add((y+z)*10+x+i);
                                boutons[x+i][y+z].getProperties().put(bt_type_torpilleur.getProperties().get("index").toString() + "" + ((int)bt_type_torpilleur.getProperties().get("nb_bat_places")-1),"x" );

                            }
                        }
                    }
                    cases_valides_placement_ia.removeAll(cases_invalides_placement_ia);
                    cases_invalides_placement_ia.clear();
                    //En commentaire ci-dessous : donne les indices des bateaux.
                    //boutons[x][y].setText(bt_type.getProperties().get("index").toString() + "" + bt_type.getProperties().get("nb_bat_places").toString());
                    boutons[x][y].setText("⬛");
                    boutons[x][y].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                    boutons[x][y].getProperties().put("carte_bat"+(etape-1), bt_type_torpilleur.getProperties().get("index").toString() + "" + ((int)bt_type_torpilleur.getProperties().get("nb_bat_places")-1));
                    boutons[x][y].getStyleClass().add("bat_rempli");
            }
        }

    }
    public  void placement(String bateau, int taille_bat, int taille_plateau, int nb_max_bat, int[] a,
                           int[] b, ToggleButton[][] boutons, int x, int y,ToggleButton bt_type,ToggleGroup bt_type_group,int index_actuel){
        // NOTE DE DEVELOPPEMENT : On pourrait peut-être simplement utiliser compteur[]partout ou bt.type.. partout.
        final int[] compteur = {(int)bt_type.getProperties().get("compteur")};

        if ((int)bt_type.getProperties().get("est_place")==0){
            if (taille_bat>1) {
                if (compteur[0] < 2) {
                    a[compteur[0]] = x;
                    b[compteur[0]] = y;
                    //L'indicateur de placement du tour précédent est effacé du plateau
                    if ((indicateur_x !=-1)&&(indicateur_y !=-1)&&((boutons[indicateur_x][indicateur_y].getText().equals("\uD83D\uDD34"))||(boutons[indicateur_x][indicateur_y].getText().equals("❌")))){
                        boutons[indicateur_x][indicateur_y].setText("");
                    }
                    //On affiche un indicateur de placement (un rond si le placement serait possible,
                    boutons[x][y].setText("\uD83D\uDD34");
                    boutons[x][y].getStyleClass().add("pre_select");
                    // une croix si il est invalide.
                    // NOTE DE DEVELOPPEMENT : On pourrait peut-être mettre ça dans le if en dessous.
                    if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                        boutons[x][y].setText("❌");
                        boutons[x][y].getStyleClass().add("case_prise");
                    }
                    //On récupère les coordonnées de l'indicateur pour l'effacer à l'appel suivant.
                    indicateur_x =x;
                    indicateur_y =y;

                    bt_type.getProperties().put("compteur",compteur[0]+1);
                    if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                        marin.setText("Vos bateaux sont trop proches !");
                        bt_type.getProperties().put("compteur",0);
                        compteur[0]=0;
                    }else{
                        if ((int)bt_type.getProperties().get("compteur") > 1) {
                            //On vérifie si les x sont égaux (placement vertical) ou si les y le sont (vertical),
                            //ou non (invalide).
                            // NOTE DE DEVELOPPEMENT : on pourrait peut-être enlever les valeurs absolues ici.
                            if ((abs(a[0] - a[1]) > 0) && b[0] == b[1]) {
                                placement_vertical_horizontal(false,a,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur,false);
                            } else if ((abs(b[0] - b[1]) > 0) && a[0] == a[1]) {
                                placement_vertical_horizontal(true,b,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur,false);
                            } else {
                                marin.setText("Choisissez un placement soit vertical, soit horizontal.");
                                bt_type.getProperties().put("compteur",0);
                                compteur[0]=0;
                            }
                        }
                    }
                    // On regarde si tous les bateaux demandés dans les paramètres sont placés ou non.
                    if (((nb_max_bat -1) > (int)bt_type.getProperties().get("nb_bat_places"))&&(compteur[0] >= 1)) {
                        bt_type.getProperties().put("compteur",0);
                        bt_type.getProperties().put("nb_bat_places",(int)bt_type.getProperties().get("nb_bat_places")+1);
                        compteur[0]=0;
                    }
                    if (!((nb_max_bat -1) > (int)bt_type.getProperties().get("nb_bat_places"))&&(compteur[0] >= 1)){
                        marin.setText("Tous les "+bateau+"s sont placés.");
                        System.out.println("Tous les "+bateau+"s sont placés.");
                        bt_type.getProperties().put("est_place",1);
                        bt_type.getStyleClass().add("bt_type_bat_desactive");
                        //On sélectionne le bouton de bateau restant suivant.
                        bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4).setSelected(true);
                        //On désactive le bouton du type de bateau placé.
                        bt_type.setDisable(true);
                        while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                            // Tant que le bouton suivant est désactivé, on passe au suivant.
                            index_actuel=index_actuel+1;
                            // Et si ils le sont tous, on passe...
                            if (index_actuel==4){
                                if (etape==1) {
                                    // Au placement du deuxième joueur si seulement le premier a placé.
                                    System.out.println("2e placement");
                                    marin.setText("C'est au deuxième joueur de placer !");
                                    reinitialiser_plateau();
                                    etape++;
                                    break;
                                }
                                if(etape==2){
                                    // Au placement des bateaux si on est en mode IA
                                    if (mode>=1){
                                        marin.setText("");
                                        effacer_plateau();
                                        reinitialiser_plateau();
                                        etape=1;
                                        for(int i=0;i<10;i++){

                                            placement_ia(bateaux_a_placer[i]);
                                        }
                                    }
                                    //Puis à la phase de tir
                                    System.out.println("Phase de tir");
                                    marin.setText("La partie commence !");
                                    bt_tirer.setManaged(true);
                                    // On cache les boutons de placement (Cuirassé, Torpilleur...) et on affiche celui de tir
                                    for(ToggleButton z:liste_bt){
                                        z.setManaged(false);
                                        z.setVisible(false);
                                    }
                                    effacer_plateau();
                                    afficher_plateau(1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //Placement des torpilleurs (même fonctionnement pour 1 case, plus léger en vérifications)
            else {
                if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                    marin.setText("Vos bateaux sont trop proches.");
                    bt_type.getProperties().put("compteur",0);
                }else{
                    if ((nb_max_bat) > (int)bt_type.getProperties().get("nb_bat_places") ) {
                        bt_type.getProperties().put("compteur",0);
                        bt_type.getProperties().put("nb_bat_places",(int)bt_type.getProperties().get("nb_bat_places")+1);
                        for(int i=-1;i<2;i++)
                            for(int z=-1;z<2;z++) {
                                if(x+i>=0 && x+i<=(taille_plateau-1) && y+z>=0 &&y+z<=(taille_plateau-1)){
                                    boutons[x+i][y+z].getProperties().put("cases_prises","x");
                                    //boutons[x+i][y+z].setText("x");
                                    boutons[x+i][y+z].getStyleClass().add("bat_rempli");
                                }
                            }
                        //En commentaire ci-dessous : donne les indices des bateaux.
                        //boutons[x][y].setText(bt_type.getProperties().get("index").toString() + "" + bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[x][y].setText("⬛");
                        boutons[x][y].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                        boutons[x][y].getProperties().put("carte_bat"+(etape-1), bt_type.getProperties().get("index").toString() + "" + ((int)bt_type.getProperties().get("nb_bat_places")-1));
                        boutons[x][y].getStyleClass().add("bat_rempli");
                    }
                    if (nb_max_bat<=(int)bt_type.getProperties().get("nb_bat_places")){
                        marin.setText("Tous les "+bateau+"s sont placés.");
                        System.out.println("Tous les "+bateau+"s sont placés.");
                        bt_type.getProperties().put("est_place",1);
                        bt_type.getStyleClass().add("bt_type_bat_desactive");
                        bt_type.setDisable(true);
                        while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                            index_actuel=index_actuel+1;
                            if (index_actuel==4){
                                if (etape==1) {
                                    System.out.println("2e Joueur");

                                    reinitialiser_plateau();
                                    etape++;

                                    break;
                                }
                                if(etape==2){
                                    System.out.println("Phase de tir");
                                    bt_tirer.setManaged(true);

                                    for(ToggleButton z:liste_bt){
                                        z.setManaged(false);
                                        z.setVisible(false);
                                    }
                                    effacer_plateau();
                                    afficher_plateau(1);
                                    break;
                                }
                            }
                        }
                        bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4).setSelected(true);
                    }
                }
            }
        }
    }
    // A PLACER AILLEURS
    final private int taille_plateau = 10;                // taille de la grille (n x n)
    private ToggleButton[][] boutons;     // matrice des boutons
    int tour=0;
    int joueur =0;
    int dir1, dir2, dir3, dir4,coup_juste=0;
    int[] position_du_tir = new int[2];
    List<Integer> cases_valides_tir = new ArrayList<>();
    List<Integer> cases_invalides_tir = new ArrayList<>();
    int[][][] heatmap=new int[2][100][100];

    public void tirer(int x, int y,boolean ia) {
        // Dans cette partie on utilise des %2 puisqu'il n'y a que deux joueurs, on veut juste
        // savoir lequel joue. On utilise !ia quand c'est le joueur qui joue.
        ToggleButton bouton = boutons[x][y];
        int coord_base100 = y*10+x;
        int case_aleatoire;
        //Cette condition est vérifiée quand un joueur a déjà joué
        //elle permet d'afficher une carte vide entre les tours de 2 joueurs.
        if (joueur==1){
            heatmap[tour%2][x][y]++;
            for(int i =0;i<10;i++){
                for(int j =0;j<10;j++){
                    System.out.print(heatmap[tour%2][i][j]+ "  ");
                }
                System.out.println();
            }
            System.out.println("C'est au tour du joueur "+(2-(tour%2)));
            afficher_plateau(tour);
            tour++;
            joueur=0;
            return;
        }
        // Si le bouton cliqué (ou tiré par l'ia) a déjà été tiré
        if (!bouton.getProperties().get("etat_tir"+(tour%2)).equals("non_tire")) {
            if(!ia){
                System.out.println("Vous avez déjà tiré ici.");
            }else{
                // Si l'IA a pris une case déjà tirée, si elle avait choisi une direction, elle doit l'éliminer
                //et rejouer, sinon elle n'a pas tiré la case, si le programme marche.
                if(mode>=2){
                    if (dir1 ==1){
                        dir1 =-1;
                    } else if (dir2 ==1) {
                        dir2 =-1;
                    } else if (dir3 ==1) {
                        dir3 =-1;
                    } else if (dir4 ==1) {
                        dir4 =-1;
                    }
                    if (coup_juste>=1){
                        if (dir2 ==0){
                            System.out.println("L'IA place un point vers le haut");
                            dir2 =1;
                            tirer(position_du_tir[0]-1,position_du_tir[1],true);
                            return;
                        }else if (dir3 ==0){
                            System.out.println("L'IA place un point vers la droite");
                            dir3 =1;
                            tirer(position_du_tir[0],position_du_tir[1]+1,true);
                            return;

                        }else if(dir4 ==0) {
                            System.out.println("L'IA place un point vers la gauche");
                            dir4 = 1;
                            tirer(position_du_tir[0], position_du_tir[1] - 1, true);
                            return;

                        }
                    }
                }

            }
            return;
        }
        //On donne l'information au bouton qu'il a été tiré
        bouton.getProperties().put("etat_tir"+(tour%2), "tire");
        //Si il n'y a pas de bateau sur la case tirée...
        if (bouton.getProperties().get("carte_bat"+(tour%2)) == null) {
            System.out.print(tour%2);
            if(!ia){
            bouton.setText("🌊");
            marin.setText("Manqué!");
            System.out.println("Vous avez manqué.");
            bouton.getStyleClass().add("manque_avant");
            }else{
                System.out.print("L'IA a tiré dans le vide//");
                //On enlève la case pour que l'IA ne la tire plus.
                //System.out.println("On enlève la case"+coord_base100%10+","+(coord_base100-coord_base100%10)/10);
                cases_valides_tir.remove(Integer.valueOf(coord_base100));
                //bouton.setText("IA");
            }
            bouton.getProperties().put("coups"+((tour+1)%2),"🌊");
            if(mode==0){
                effacer_plateau();
                //Entre deux joueurs, on demande au joueur suivant de cliquer pour afficher son jeu.
                //(le plateau ayant été effacé à la fin du tour de l'autre joueur).
                marin.setText("Cliquez un bouton pour continuer la partie.");
                joueur=1;
                    return;
            }
            else if(!ia){
                //Si le joueur (!ia) a tiré dans le vide, alors l'IA joue.
                if(mode==1){
                    //En difficulté 1 l'IA tire aléatoirement dans les cases non déjà tirées.
                tour++;
                case_aleatoire = cases_valides_tir.get(new Random().nextInt(cases_valides_tir.size()));
                System.out.println("On tire la case"+case_aleatoire%10+","+(case_aleatoire-case_aleatoire%10)/10);
                tirer(case_aleatoire%10,(case_aleatoire-case_aleatoire%10)/10,true);
                tour++;

                }else{
                    //En difficulté 2 si l'IA a touché un bateau elle tente les 4 directions autour.
                    if (coup_juste>=1){
                        if(coup_juste>=2){
                            coup_juste=1;
                        }
                    if (dir2 ==0){
                        System.out.println("L'IA place un point vers le haut");
                        dir2 =1;
                        tour++;
                        tirer(position_du_tir[0]-1,position_du_tir[1],true);
                        tour++;
                        return;
                    }else if (dir3 ==0){
                        System.out.println("L'IA place un point vers la droite");
                        dir3 =1;
                        tour++;
                        tirer(position_du_tir[0],position_du_tir[1]+1,true);
                        tour++;
                        return;
                    }else if(dir4 ==0) {
                        System.out.println("L'IA place un point vers la gauche");
                        dir4 = 1;
                        tour++;
                        tirer(position_du_tir[0], position_du_tir[1] - 1, true);
                        tour++;
                        return;
                    }
                    }
                    if(coup_juste==0){
                    tour++;
                        case_aleatoire = cases_valides_tir.get(new Random().nextInt(cases_valides_tir.size()));
                        tirer(case_aleatoire%10,(case_aleatoire-case_aleatoire%10)/10,true);
                    tour++;
                        return;
                    }


                }
                return;
            }
            //Si l'IA perd en ayant choisi une des quatres direction alors elle l'élimine de ses choix suivants.
            if(mode>=2){
            if (dir1 ==1){
                dir1 =-1;
            } else if (dir2 ==1) {
                dir2 =-1;
            } else if (dir3 ==1) {
                dir3 =-1;
            } else if (dir4 ==1) {
                dir4 =-1;
            }
                return;
            }
            return;
        }

        String id = bouton.getProperties().get("carte_bat"+(tour%2)).toString();
        //On prend les valeurs du type de bateau touché et du numéro de
        //bateau de ce type (donnés respectivement par le premier et deuxième caractère)
        int index_type_bateau = Character.getNumericValue(id.charAt(0));
        int index_numero_bateau = Character.getNumericValue(id.charAt(1));
        // On retire un point de vie au bateau touché.
        pv_bateaux[tour%2][index_type_bateau][index_numero_bateau]--;
        // On met une croix sur la vue du joueur suivant (permet de savoir quelle case l'adversaire a touché)
        // Du feu pour le joueur actuel pour montrer qu'il a touché un bateau.
        bouton.getProperties().put("coups"+(tour%2),"❌");
        bouton.getProperties().put("coups" + ((tour + 1) % 2), "🔥");
        cuirasses.setText("J1 : "+pv_bateaux[0][0][0]+"           | "+"J2 :"+pv_bateaux[1][0][0]);
        croiseurs.setText("      "+pv_bateaux[0][1][0]+" "+pv_bateaux[0][1][1]+"        |    "+pv_bateaux[1][1][0]+" "+pv_bateaux[1][1][1]);
        destroyers.setText("      "+pv_bateaux[0][2][0]+" "+pv_bateaux[0][2][1]+" "+pv_bateaux[0][2][2]+"     | "+pv_bateaux[1][2][0]+" "+pv_bateaux[1][2][1]+" "+pv_bateaux[1][2][2]);
        torpilleurs.setText("      "+pv_bateaux[0][3][0]+" "+pv_bateaux[0][3][1]+" "+pv_bateaux[0][3][2]+" "+pv_bateaux[0][3][3]+"    | "+pv_bateaux[1][3][0]+" "+pv_bateaux[1][3][1]+" "+pv_bateaux[1][3][2]+" "+pv_bateaux[1][3][3]);
        if(!ia) {
            bouton.setText("🔥");
            marin.setText("Touché !");
            bouton.getStyleClass().add("touche");
        }else{
            System.out.println("On enlève la case"+coord_base100%10+","+(coord_base100-coord_base100%10)/10);
            cases_valides_tir.remove(Integer.valueOf(coord_base100));
            if(!bouton.getText().equals("🔥")&&!bouton.getText().equals("🌊")){
                bouton.setText("❌");
            }
            marin.setText("L'IA a touché un de vos bateaux !");
            System.out.println("L'IA a touché un de vos bateaux ! Dans la carte"+tour%2);
            if (pv_bateaux[tour%2][index_type_bateau][index_numero_bateau] != 0) {
            if (mode==1){
                case_aleatoire = cases_valides_tir.get(new Random().nextInt(cases_valides_tir.size()));
                tirer(case_aleatoire%10,(case_aleatoire-case_aleatoire%10)/10,true);
                return;
            }
            else{
                coup_juste++;
                if(dir1 !=1& dir2 !=1& dir3 !=1& dir4 !=1){
                    if(coup_juste==1&&(pv_bateaux[tour%2][index_type_bateau][index_numero_bateau] != 0)){
                        if (dir1 ==0){
                            dir1 =1;
                            position_du_tir[0]=x;
                            position_du_tir[1]=y;
                            System.out.println("L'IA place un point vers le bas.");
                            tirer(position_du_tir[0]+1,position_du_tir[1],true);
                            return;
                        }
                    }
                }
                // Si l'IA a tiré dans la bonne direction après son touché, elle continue dans celle-ci
                if (coup_juste==2&&(pv_bateaux[tour%2][index_type_bateau][index_numero_bateau] != 0)){
                    if(dir1 ==1) {
                        tirer(position_du_tir[0] + 2, position_du_tir[1], true);
                        return;
                    } else if (dir2 ==1) {
                        tirer(position_du_tir[0]  - 2, position_du_tir[1], true);
                        return;
                    }else if (dir3 ==1){
                        tirer(position_du_tir[0] , position_du_tir[1]+2, true);
                        return;
                    }else if (dir4 ==1){
                        tirer(position_du_tir[0] , position_du_tir[1]-2, true);
                        return;
                    }
                }
                if (coup_juste==3&&(pv_bateaux[tour%2][index_type_bateau][index_numero_bateau] != 0)){
                    if(dir1 ==1) {
                        tirer(position_du_tir[0] + 3, position_du_tir[1], true);
                        return;
                    } else if (dir2 ==1) {
                        tirer(position_du_tir[0] - 3, position_du_tir[1], true);
                        return;
                    }else if (dir3 ==1){
                        tirer(position_du_tir[0], position_du_tir[1]+3, true);
                        return;

                    }else if (dir4 ==1){
                        tirer(position_du_tir[0], position_du_tir[1]-3, true);
                        return;
                    }
                }
            }
            return;
            }
        }
        //Si le bateau touché a perdu tous ses points de vie (toutes les cases ont été touchées)...
        if (pv_bateaux[tour%2][index_type_bateau][index_numero_bateau] == 0) {
            bateaux_coules[tour%2]++;

            if(!ia) {
                marin.setText("Touché-coulé!");
                int total_bateaux = 1 + 2 + 3 + 4;
                if (bateaux_coules[tour%2] == total_bateaux) {
                    System.out.println("La partie est finie.");
                    marin.setText("Vous avez gagné !");
                    bt_tirer.setDisable(true);
                }
                if(aide_joueur){
                    afficher_indications(id);
                }
            }else{
                marin.setText("L'IA a coulé l'un de vos bateaux !");

                if(mode>=2) {
                    // Réinitialisation des directions..
                    coup_juste = 0;
                    dir1 = 0;
                    dir2 = 0;
                    dir3 = 0;
                    dir4 = 0;
                    //On supprime les cases autour d'un bateau coulé en mode 3.
                    if (mode==3){
                        if(index_type_bateau==3){
                            for(int i=-1;i<2;i++){
                                for(int z=-1;z<2;z++) {
                                    if(x+i>=0 && x+i<=(taille_plateau-1) && y+z>=0 &&y+z<=(taille_plateau-1)){
                                        coord_base100=(y+z)*10+(x+i);
                                        cases_invalides_tir.add(coord_base100);
                                        //boutons[x+i][y+z].setText("xIA");
                                    }
                                }
                            }
                            cases_valides_tir.removeAll(cases_invalides_tir);
                            cases_invalides_tir.clear();
                        }else{
                            if(position_du_tir[0]==x) {
                                for (int i = -1; i < 2; i++) {
                                    for (int z = min(position_du_tir[1] - 1, y - 1); z <= max(position_du_tir[1] + 1, y + 1); z++) {
                                        if ((x + i) >= 0 && (x + i) <= (taille_plateau - 1) && z >= 0 && z <= (taille_plateau - 1)) {
                                            coord_base100=z*10+(x+i);
                                            cases_invalides_tir.add(coord_base100);
                                            //boutons[x + i][z].setText("xIA");
                                        }
                                    }
                                }
                                cases_valides_tir.removeAll(cases_invalides_tir);
                                cases_invalides_tir.clear();
                            }else if(position_du_tir[1]==y){
                                for (int i = -1; i < 2; i++) {
                                    for (int z = min(position_du_tir[0] - 1, x - 1); z <= max(position_du_tir[0] + 1, x + 1); z++) {
                                        if ((y + i) >= 0 && (y + i) <= (taille_plateau - 1) && z >= 0 && z <= (taille_plateau - 1)) {
                                            coord_base100=(y+i)*10+z;
                                            cases_invalides_tir.add(coord_base100);
                                            //boutons[z][y+i].setText("xIA");
                                        }
                                    }
                                }
                                cases_valides_tir.removeAll(cases_invalides_tir);
                                cases_invalides_tir.clear();
                            }
                        }
                    }
                    //System.out.println(bateaux_coules[tour%2]+" Bateaux sont coulés !");
                }
                //Nouveau tir aléatoire dans les cases valides.
                int total_bateaux = 1 + 2 + 3 + 4;
                if (bateaux_coules[tour%2] == total_bateaux) {
                    System.out.println("La partie est finie.");
                    marin.setText("L'IA a gagné.");
                    bt_tirer.setDisable(true);
                }
                case_aleatoire = cases_valides_tir.get(new Random().nextInt(cases_valides_tir.size()));
                tirer(case_aleatoire%10,(case_aleatoire-case_aleatoire%10)/10,true);
                return;
            }
            // Nombre total de bateaux à couler
        }
    }

    @Override
    public void start(Stage plateau) {
        // Initialisations...
        for(int i =0; i<100;i++){
            cases_valides_tir.add(i);
            cases_valides_placement_ia.add(i);
        }
        for(int i =0;i<10;i++){
            for(int j =0;j<10;j++){
                for(int k=0;i<2;i++) {
                    heatmap[k][i][j] = 0;
                }
            }
        }
//        PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
//        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
//        pause1.setOnFinished(e->{
//            marin.setText("Pour placer vos bateaux, placez un côté ");
//        });
//        pause2.setOnFinished(e->{
//            marin.setText("puis l'autre du bateau de manière à ce que ça fasse la taille souhaitée.");
//        });
//        SequentialTransition sequence = new SequentialTransition(pause1,pause2);
//        sequence.play();
        GridPane grille = new GridPane();
        boutons = new ToggleButton[taille_plateau][taille_plateau];
        int[] a = new int[2];
        int[] b = new int[2];
        HBox select_bat = new HBox();
        int index =1;

        int k = 0;
        for(ToggleButton z : liste_bt ){
            z.getProperties().put("nb_bat_places",0);
            z.getProperties().put("est_place",0);
            z.getProperties().put("compteur",0);
            z.setToggleGroup(bt_type);

            z.getStyleClass().add("bt_type_bat");
            z.getProperties().put("index",k);
            k=k+1;
        }

        bt_tirer.setToggleGroup(bt_type);
        select_bat.getChildren().addAll(bt_type_torpilleur,bt_type_cuirasse,bt_type_croiseur,bt_type_destroyer,bt_tirer);
        bt_tirer.setManaged(false);

        bt_tirer.getStyleClass().add("bt_type_bat");
        for (int i = 0; i < taille_plateau; i++) {
            for (int j = 0; j < taille_plateau; j++) {
                ToggleButton bouton = new ToggleButton("");
                bouton.setPrefSize(60, 60);
                int x = i;
                int y=j;
                boutons[i][j] = bouton;
                boutons[i][j].getProperties().put("contour_bateaux","");
                boutons[i][j].getProperties().put("carte_bat_indicative0","");
                boutons[i][j].getProperties().put("carte_bat_indicative1","");
                boutons[i][j].getProperties().put("cases_prises",0);
                boutons[i][j].getProperties().put("coups0","");
                boutons[i][j].getProperties().put("coups1","");
                boutons[i][j].getProperties().put("etat_tir0", "non_tire");
                boutons[i][j].getProperties().put("etat_tir1", "non_tire");
                boutons[i][j].getStyleClass().add("button");
                if (((i%2==1)||(j%2==1))&&!((i%2==1)&&(j%2==1))) {
                    boutons[i][j].getStyleClass().add("button2");
                }
                //Quand un bouton du plateau est cliqué...
                bouton.setOnAction( e -> {

                    Toggle selectedToggle = bt_type.getSelectedToggle();
                    ToggleButton type_bateau = (ToggleButton) selectedToggle;
                    //On regarde si un des boutons d'actions (soit de placement, soit de tir)
                    //est activé.
                    if (selectedToggle == null) {
                        System.out.println("Merci de sélectionner un type de bateau.");
                        return;
                    }
                    if (selectedToggle == bt_tirer) {
                        tirer(x, y,false);
                        return;
                    }
                    //Si c'est un bouton de placement, on regarde à quel bateau il correspond.
                    switch (type_bateau.getText()) {
                        case "Cuirassé":
                            placement("cuirassé", 4, taille_plateau, 1, a, b, boutons, x, y, bt_type_cuirasse, bt_type, index);
                            break;
                        case "Croiseur":
                            placement("croiseur", 3, taille_plateau, 2, a, b, boutons, x, y, bt_type_croiseur, bt_type, index);
                            break;
                        case "Destroyer":
                            placement("destroyer", 2, taille_plateau, 3, a, b, boutons, x, y, bt_type_destroyer, bt_type, index);
                            break;
                        case "Torpilleur":
                            placement("torpilleur", 1, taille_plateau, 4, a, b, boutons, x, y, bt_type_torpilleur, bt_type, index);
                            break;
                        default:
                            System.out.println("Sélection de bateau non reconnue.");
                    }
                     });
                grille.add(bouton, j, i);
            }
        }
        //Initialisation de la fenêtre du jeu.
        Image mer = new Image("vagues2.png");
        ImageView vagues = new ImageView(mer);
        Image imagemarin = new Image("marin4.png");
        ImageView marinview = new ImageView(imagemarin);
        vagues.setPreserveRatio(true);
        vagues.setFitWidth(1920);
        vagues.setFitHeight(1080);
        StackPane.setAlignment(vagues,Pos.BOTTOM_CENTER);
        select_bat.setSpacing(10);
        select_bat.setAlignment(Pos.CENTER);

        grille.setHgap(10);
        grille.setVgap(10);
        grille.setAlignment(Pos.CENTER);

        VBox menu = new VBox();
        Label titre_bat_nav = new Label("Bataille Navale");

        titre_bat_nav.getStyleClass().add("titre_bat_nav");
        menu.setSpacing(10);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(titre_bat_nav,mode_jcj,mode_ia_1,mode_ia_2,mode_ia_3,avec_aide);
        StackPane boutons_plateau = new StackPane();
        VBox jeu_entier = new VBox();
        jeu_entier.getChildren().addAll(boutons_plateau,select_bat);
        VBox vies_bateaux = new VBox();
        vies_bateaux.getChildren().addAll(cuirasses,croiseurs,destroyers,torpilleurs);

        boutons_plateau.getChildren().addAll(grille);
        cuirasses.getStyleClass().add("marin");
        torpilleurs.getStyleClass().add("marin");
        destroyers.getStyleClass().add("marin");
        croiseurs.getStyleClass().add("marin");

        marin.getStyleClass().add("marin");
        jeu_entier.setSpacing(20);
        jeu_entier.setAlignment(Pos.CENTER);
        marinview.setPreserveRatio(true);
        marinview.setFitHeight(700);
        StackPane racine_jeu = new StackPane();
        racine_jeu.getStyleClass().add("racine");
        menu.getStyleClass().add("racine");
        racine_jeu.getChildren().addAll(vagues,vies_bateaux,marinview,marin,jeu_entier);
        racine_jeu.setAlignment(Pos.CENTER);
        StackPane.setAlignment(marin,Pos.CENTER_RIGHT);
        StackPane.setAlignment(marinview,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(vies_bateaux,Pos.CENTER_LEFT);
        StackPane.setMargin(marin, new Insets(20, 20, 20, 20));
        StackPane.setMargin(vies_bateaux, new Insets(20, 20, 20, 20));
        Scene fenetre_jeu = new Scene(menu,1920,1080, Color.rgb(99, 107, 194));
        //Pour le menu, on assigne chaque mode à son bouton correspondant.
        avec_aide.getStyleClass().add("modes");
        for(Button modes : liste_modes){
            modes.getStyleClass().add("modes");
            modes.setOnAction(e->{
                fenetre_jeu.setRoot(racine_jeu);
            });

        }
        mode_ia_1.setOnAction(e->{
            fenetre_jeu.setRoot(racine_jeu);
            mode=1;
            etape=2;
        });
        mode_ia_2.setOnAction(e->{
            fenetre_jeu.setRoot(racine_jeu);
            mode=2;
            etape=2;
        });
        mode_ia_3.setOnAction(e->{
            fenetre_jeu.setRoot(racine_jeu);
            mode=3;
            etape=2;
        });
        avec_aide.setOnAction(e->{
            aide_joueur=true;
        });
        if (mode>=1){
            etape=2;
        }

        fenetre_jeu.getStylesheets().add("/styles.css");
        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(fenetre_jeu);

        plateau.setFullScreen(true);
        plateau.setFullScreenExitHint("");

        plateau.show();
    }

}
