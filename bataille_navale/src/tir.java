import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class tir extends Application {

    public void placement_vertical_horizontal(boolean vertical, int[] cas, int taille_bat, String bateau, ToggleButton bt_type, ToggleButton[][] boutons, int taille_plateau, int y, int x, int[] compteur){

        if ((abs(cas[1] - cas[0])) >= taille_bat) {
            System.out.println("Votre " + bateau + " est trop grand (il doit faire " + taille_bat + "  cases). Veuillez recommencer votre placement");
            bt_type.getProperties().put("compteur",0);
            compteur[0]=0;
        } else if (abs(cas[1] - cas[0]) < (taille_bat - 1) ) {
            System.out.println("Votre " + bateau + " est trop petit (il doit faire " + taille_bat + "  cases). Veuillez recommencer votre placement");
            bt_type.getProperties().put("compteur",0);
            compteur[0]=0;
        } else {
            for(int i = -1;i<2;i++){
                for (int z = min(cas[0]-1, cas[1]-1); z <= max(cas[0]+1, cas[1]+1); z++) {
                    if (vertical){
                        if((x+i)>=0 && (x+i)<=(taille_plateau-1) && z>=0 && z<=(taille_plateau-1)){
                            //boutons[x+i][z].setText("x");
                            boutons[x+i][z].getProperties().put("cases_prises","x");
                            boutons[x+i][z].getStyleClass().add("bat_rempli");}
                    }else{
                        if((y+i)>=0 && (y+i)<=(taille_plateau-1) && z>=0 && z<=(taille_plateau-1)){
                            //boutons[z][y+i].setText("x");
                            boutons[z][y+i].getProperties().put("cases_prises","x");
                            boutons[z][y+i].getStyleClass().add("bat_rempli");}
                    }
                }}
            for (int z = min(cas[0], cas[1]); z <= max(cas[0], cas[1]); z++) {
                if (vertical){
                    boutons[x][z].setText("⬛");
                    boutons[x][z].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                    //boutons[x][z].setText(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
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

    public void reinitialiser_carte(){
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

    public void effacer_carte(){
        for (int o = 0; o < taille_plateau; o++) {
            for (int p = 0; p < taille_plateau; p++) {
                boutons[o][p].setText("");
                boutons[o][p].getStyleClass().add("bat_rempli");
            }
        }
    }

    public void afficher_carte_bat(int tour){
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
    int u=-1;
    int v=-1;
    // PV des bateaux : index 0=cuirassé, 1=croiseur, 2=destroyer, 3=torpilleur
    final private int[][][] pv_bateaux = {{{4,0,0,0},{3,3,0,0},{2,2,2,0},{1,1,1,1}},{{4,0,0,0},{3,3,0,0},{2,2,2,0},{1,1,1,1}}};
    // Les etapes permettent de passer d'un plateau à un autre puis à la phase de tir.
    int mode=0;

    int etape = 1;

    final private int[] bateaux_coules = {0,0};

    final int[] bateaux_a_placer = {4,3,3,2,2,2,1,1,1,1};
    boolean vertical;
    int compt[];

    public void placement_ia(int taille_bat){
        int[] cas ={0,0};
        int autre_co;
        boolean placement_impossible = false;
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
            cas[1]=cas[0]+((int)Math.random()*((2*taille_bat-1))-taille_bat+1);
        if (vertical){
            if(boutons[autre_co][cas[0]].getProperties().get("cases_prises").equals("x")||boutons[autre_co][cas[1]].getProperties().get("cases_prises").equals("x")){
                System.out.println("Vos bateaux sont trop proches.");
                bt_type_torpilleur.getProperties().put("compteur",0);
                placement_impossible = true;
            }
        }else{
            if(boutons[cas[0]][autre_co].getProperties().get("cases_prises").equals("x")||boutons[cas[1]][autre_co].getProperties().get("cases_prises").equals("x")){
                System.out.println("Vos bateaux sont trop proches.");
                bt_type_torpilleur.getProperties().put("compteur",0);
                placement_impossible = true;
            }
        }
        if(!placement_impossible){

        switch (taille_bat) {
            case 4:placement_vertical_horizontal(vertical,cas,taille_bat,"Cuirassé",bt_type_cuirasse,boutons,taille_plateau,autre_co,autre_co,compt);
                break;
            case 3:placement_vertical_horizontal(vertical,cas,taille_bat,"Croiseur",bt_type_croiseur,boutons,taille_plateau,autre_co,autre_co,compt);
                bt_type_croiseur.getProperties().put("nb_bat_places",(int)bt_type_croiseur.getProperties().get("nb_bat_places")+1);
            break;
            case 2:placement_vertical_horizontal(vertical,cas,taille_bat,"Destroyer",bt_type_destroyer,boutons,taille_plateau,autre_co,autre_co,compt);
                bt_type_destroyer.getProperties().put("nb_bat_places",(int)bt_type_destroyer.getProperties().get("nb_bat_places")+1);
            break;
        }
        }else{
            placement_ia(taille_bat);
        }
        }else{
            int x=(int)(Math.random()*taille_plateau);
            int y=(int)(Math.random()*taille_plateau);
            if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                System.out.println("Les torpilleurs de l'IA sont trop proches.");
                placement_ia(1);
            }else{

                    bt_type_torpilleur.getProperties().put("compteur",0);
                    bt_type_torpilleur.getProperties().put("nb_bat_places",(int)bt_type_torpilleur.getProperties().get("nb_bat_places")+1);
                    for(int i=-1;i<2;i++)
                        for(int z=-1;z<2;z++) {
                            if(x+i>=0 && x+i<=(taille_plateau-1) && y+z>=0 &&y+z<=(taille_plateau-1)){
                                boutons[x+i][y+z].getProperties().put("cases_prises","x");
                                //boutons[x+i][y+z].setText("x");
                                boutons[x+i][y+z].getStyleClass().add("bat_rempli");}
                        }
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
        final int[] compteur = {(int)bt_type.getProperties().get("compteur")};
        //System.out.println(bt_type.getProperties().get("nb_bat_places"));
        a[1]=taille_plateau;
        b[1]=taille_plateau;
        if ((int)bt_type.getProperties().get("est_place")==0){
            if (taille_bat>1) {
                if (compteur[0] < 2) {
                    a[compteur[0]] = x;
                    b[compteur[0]] = y;
                    if ((u!=-1)&&(v!=-1)&&((boutons[u][v].getText().equals("\uD83D\uDD34"))||(boutons[u][v].getText().equals("❌")))){
                        boutons[u][v].setText("");
                    }
                    boutons[x][y].setText("\uD83D\uDD34");
                    boutons[x][y].getStyleClass().add("pre_select");
                    if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                        boutons[x][y].setText("❌");
                        boutons[x][y].getStyleClass().add("case_prise");
                    }
                    u=x;
                    v=y;

                    bt_type.getProperties().put("compteur",compteur[0]+1);
                    //System.out.println(a[0] + "," + a[1]);
                    if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                        System.out.println("Vos bateaux sont trop proches.");
                        bt_type.getProperties().put("compteur",0);
                        compteur[0]=0;
                    }else{
                        if ((int)bt_type.getProperties().get("compteur") > 1) {
                            if ((abs(a[0] - a[1]) > 0) && b[0] == b[1]) {
                                placement_vertical_horizontal(false,a,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur);
                            } else if ((abs(b[0] - b[1]) > 0) && a[0] == a[1]) {
                                placement_vertical_horizontal(true,b,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur);
                            } else {
                                System.out.println("Merci de bien choisir un placement soit vertical, soit horizontal.");
                                bt_type.getProperties().put("compteur",0);
                                compteur[0]=0;
                            }
                        }}
                    //System.out.println(compteur[0]);
                    if (((nb_max_bat -1) > (int)bt_type.getProperties().get("nb_bat_places"))&&(compteur[0] >= 1)) {
                        bt_type.getProperties().put("compteur",0);
                        bt_type.getProperties().put("nb_bat_places",(int)bt_type.getProperties().get("nb_bat_places")+1);
                        compteur[0]=0;
                    }
                    if (!((nb_max_bat -1) > (int)bt_type.getProperties().get("nb_bat_places"))&&(compteur[0] >= 1)){
                        System.out.println("Tous les "+bateau+"s sont placés.");
                        bt_type.getProperties().put("est_place",1);
                        bt_type.getStyleClass().add("bt_type_bat_desactive");
                        bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4).setSelected(true);
                        bt_type.setDisable(true);
                        while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                            index_actuel=index_actuel+1;
                            if (index_actuel==4){
                                if (etape==1) {
                                    System.out.println("2e Joueur");

                                    reinitialiser_carte();
                                    etape++;

                                    break;
                                }
                                if(etape==2){
                                    if (mode>=1){
                                        effacer_carte();
                                        reinitialiser_carte();
                                        etape=1;
                                        for(int i=0;i<10;i++){

                                            placement_ia(bateaux_a_placer[i]);
                                        }
                                    }
                                    System.out.println("Phase de tir");
                                    bt_tirer.setManaged(true);

                                    for(ToggleButton z:liste_bt){
                                        z.setManaged(false);
                                        z.setVisible(false);
                                    }
                                    effacer_carte();
                                    afficher_carte_bat(1);
                                    break;
                                }
                            }
                        }
                    }
                }

            }
            //partie torpilleur
            else {
                if (boutons[x][y].getProperties().get("cases_prises").equals("x")){
                    System.out.println("Vos bateaux sont trop proches.");
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
                                    boutons[x+i][y+z].getStyleClass().add("bat_rempli");}
                            }
                        //En commentaire ci-dessous : donne les indices des bateaux.
                        //boutons[x][y].setText(bt_type.getProperties().get("index").toString() + "" + bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[x][y].setText("⬛");
                        boutons[x][y].getProperties().put("carte_bat_indicative"+(etape-1),"⬛");
                        boutons[x][y].getProperties().put("carte_bat"+(etape-1), bt_type.getProperties().get("index").toString() + "" + ((int)bt_type.getProperties().get("nb_bat_places")-1));
                        boutons[x][y].getStyleClass().add("bat_rempli");
                    }
                    if (nb_max_bat<=(int)bt_type.getProperties().get("nb_bat_places")){
                        System.out.println("Tous les "+bateau+"s sont placés.");
                        bt_type.getProperties().put("est_place",1);
                        bt_type.getStyleClass().add("bt_type_bat_desactive");
                        bt_type.setDisable(true);
                        while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                            index_actuel=index_actuel+1;
                            if (index_actuel==4){
                                if (etape==1) {
                                    System.out.println("2e Joueur");

                                    reinitialiser_carte();
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
                                    effacer_carte();
                                    afficher_carte_bat(1);
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
    final private int taille_plateau = 10;                // taille de la grille (n x n)
    private ToggleButton[][] boutons;     // matrice des boutons
    int tour=0;
    int joueur=0;
    public void tirer(int x, int y,boolean ia) {
        ToggleButton bouton = boutons[x][y];
        if (joueur==1){
            System.out.println("C'est au tour du joueur "+(2-(tour%2)));
            afficher_carte_bat(tour);
            tour++;
            joueur=0;
            return;

        }
        if (!bouton.getProperties().get("etat_tir"+(tour%2)).equals("non_tire")) {

            if(!ia){
                System.out.println("Vous avez déjà tiré ici.");
            }else{
                //System.out.println("L'IA a tiré une case déjà tirée.");
                tirer((int)(Math.random()*9),(int)(Math.random()*9),true);
            }
            return;
        }

        bouton.getProperties().put("etat_tir"+(tour%2), "tire");

        if (bouton.getProperties().get("carte_bat"+(tour%2)) == null) {
            if(!ia){
            bouton.setText("🌊");
            System.out.println("Manqué !");
            bouton.getStyleClass().add("manque_avant");
            }
            bouton.getProperties().put("coups"+((tour+1)%2),"🌊");
            if(mode==0){
            System.out.println();
            effacer_carte();
            System.out.println("Cliquez un bouton pour continuer la partie.");
            joueur=1;}
            else if(!ia){
                tour++;
                tirer((int)(Math.random()*9),(int)(Math.random()*9),true);
                tour++;
            }

            return;
        }

        String id = bouton.getProperties().get("carte_bat"+(tour%2)).toString();
        int index_bateau0 = Character.getNumericValue(id.charAt(0));
        int index_bateau1 = Character.getNumericValue(id.charAt(1));
        pv_bateaux[tour%2][index_bateau0][index_bateau1]--;


        bouton.getProperties().put("coups"+(tour%2),"❌");
        bouton.getProperties().put("coups" + ((tour + 1) % 2), "🔥");
        if(!ia) {
            bouton.setText("🔥");
            System.out.println("Touché !");
            System.out.println();
            bouton.getStyleClass().add("touche");
        }else{
            bouton.setText("❌");
            System.out.println("L'IA a touché un de vos bateaux !");
            System.out.println();
            tirer((int)(Math.random()*9),(int)(Math.random()*9),true);

        }
        //System.out.println(Arrays.deepToString(pv_bateaux));
        //Si joueur


        if (pv_bateaux[tour%2][index_bateau0][index_bateau1] == 0) {

            bateaux_coules[tour%2]++;
            if(!ia) {
                System.out.println("Touché-coulé !");
                System.out.println();
            }else{
                System.out.println("L'IA vous a coulé un bateau !");
                System.out.println();
            }
            // Nombre total de bateaux à couler
            int total_bateaux = 1 + 2 + 3 + 4;
            if (bateaux_coules[tour%2] == total_bateaux) {
                System.out.println("🎉 Tous les bateaux sont coulés ! Victoire !");
                bt_tirer.setDisable(true);
            }
        }
    }

    @Override
    public void start(Stage plateau) {

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

                bouton.setOnAction( e -> {

                    Toggle selectedToggle = bt_type.getSelectedToggle();
                    ToggleButton type_bateau = (ToggleButton) selectedToggle;
                    if (selectedToggle == null) {
                        System.out.println("Merci de sélectionner un type de bateau.");
                        return;
                    }
                    if (selectedToggle == bt_tirer) {
                        tirer(x, y,false);
                        return;
                    }


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

        Image mer = new Image("vagues2.png");
        ImageView vagues = new ImageView(mer);
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
        menu.getChildren().addAll(titre_bat_nav,mode_jcj,mode_ia_1,mode_ia_2,mode_ia_3);
        StackPane boutons_plateau = new StackPane();
        VBox jeu_entier = new VBox();
        jeu_entier.getChildren().addAll(boutons_plateau,select_bat);
        boutons_plateau.getChildren().addAll(grille);
        jeu_entier.setSpacing(20);
        jeu_entier.setAlignment(Pos.CENTER);
        StackPane racine_jeu = new StackPane();
        racine_jeu.getStyleClass().add("racine");
        menu.getStyleClass().add("racine");
        racine_jeu.getChildren().addAll(vagues,jeu_entier);
        racine_jeu.setAlignment(Pos.CENTER);
        Scene fenetre_jeu = new Scene(menu,800,800, Color.rgb(99, 107, 194));
        for(Button modes : liste_modes){
            modes.getStyleClass().add("modes");
//            modes.setOnAction(e->{
//                fenetre_jeu.setRoot(racine_jeu);
//            });
            mode_ia_1.setOnAction(e->{
                fenetre_jeu.setRoot(racine_jeu);
                mode=1;
                etape=2;
            });
        }
        if (mode>=1){
            etape=2;
        }


        fenetre_jeu.getStylesheets().add("/styles.css");
        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(fenetre_jeu);
        plateau.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
