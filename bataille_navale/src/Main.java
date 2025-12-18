import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
import java.util.Stack;

import static java.lang.Math.*;

public class Main extends Application {
    public static void placement_vh(boolean vh,int[] cas, int taille_bat, String bateau, ToggleButton bt_type, ToggleButton[][] boutons, int taille_plateau, int y,int x, int[] compteur){
            int temp;
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
                        if (vh==true){
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
                    if (vh==true){
                        boutons[x][z].setText("⬛");
                        //boutons[x][z].setText(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[x][z].getProperties().put("carte_bat",bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[x][z].getStyleClass().add("bat_rempli");

                    }else{
                        boutons[z][y].setText("⬛");
                        //boutons[z][y].setText(bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[z][y].getProperties().put("carte_bat",bt_type.getProperties().get("index").toString()+""+bt_type.getProperties().get("nb_bat_places").toString());
                        boutons[z][y].getStyleClass().add("bat_rempli");
                    }
                }
            }
    }
    int u=-1;
    int v=-1;
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


                if ((u!=-1)&&(v!=-1)&&((boutons[u][v].getText()=="\uD83D\uDD34")||(boutons[u][v].getText()=="❌"))){
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
                        placement_vh(false,a,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur);
                    } else if ((abs(b[0] - b[1]) > 0) && a[0] == a[1]) {
                        placement_vh(true,b,taille_bat,bateau,bt_type,boutons,taille_plateau,y,x,compteur);
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
                    //System.out.println("Saisie du " + ((int)bt_type.getProperties().get("nb_bat_places") + 1) + "ème "+bateau+".");
                    compteur[0]=0;
                }
                if (!((nb_max_bat -1) > (int)bt_type.getProperties().get("nb_bat_places"))&&(compteur[0] >= 1)){
                    System.out.println("Tous les "+bateau+"s sont placés.");
                    bt_type.getProperties().put("est_place",1);
                    bt_type.getStyleClass().add("bt_type_bat_desactive");
                    while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                        index_actuel=index_actuel+1;
                        if (index_actuel>3){
                            System.out.println("Que la partie commence !");
                            break;
                        }
                    }
                    bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4).setSelected(true);
                    bt_type.setDisable(true);
                }
            }

        }
        //torpilleur

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
                //boutons[x][y].setText(bt_type.getProperties().get("index").toString() + "" + bt_type.getProperties().get("nb_bat_places").toString());
                boutons[x][y].setText("⬛");
                boutons[x][y].getProperties().put("carte_bat", bt_type.getProperties().get("index").toString() + "" + bt_type.getProperties().get("nb_bat_places").toString());
                boutons[x][y].getStyleClass().add("bat_rempli");
            }
            if (nb_max_bat<=(int)bt_type.getProperties().get("nb_bat_places")){
                System.out.println("Tous les "+bateau+"s sont placés.");
                bt_type.getProperties().put("est_place",1);
                bt_type.getStyleClass().add("bt_type_bat_desactive");
                bt_type.setDisable(true);
                while (((ToggleButton)bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4)).isDisabled()){
                    index_actuel=index_actuel+1;
                    if (index_actuel>3){
                        System.out.println("Que la partie commence !");
                        break;
                    }
                }
                bt_type_group.getToggles().get(((int)bt_type.getProperties().get("index")+index_actuel)%4).setSelected(true);
            }

        }}}
    }
    private int taille_plateau = 10;                // taille de la grille (n x n)
    private ToggleButton[][] boutons;     // matrice des boutons

    @Override
    public void start(Stage plateau) {
        GridPane grille = new GridPane();
        boutons = new ToggleButton[taille_plateau][taille_plateau];
        int[] a = new int[2];
        int[] b = new int[2];
        HBox select_bat = new HBox();
        int index =1;
        ToggleButton bt_type_cuirasse = new ToggleButton("Cuirassé");
        ToggleButton bt_type_croiseur = new ToggleButton("Croiseur");
        ToggleButton bt_type_destroyer = new ToggleButton("Destroyer");
        ToggleButton bt_type_torpilleur = new ToggleButton("Torpilleur");
        ToggleGroup bt_type = new ToggleGroup();
        List<ToggleButton> liste_bt = Arrays.asList(
                bt_type_cuirasse,
                bt_type_croiseur,
                bt_type_destroyer,
                bt_type_torpilleur
        );
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

        select_bat.getChildren().addAll(bt_type_torpilleur,bt_type_cuirasse,bt_type_croiseur,bt_type_destroyer);

        for (int i = 0; i < taille_plateau; i++) {
            for (int j = 0; j < taille_plateau; j++) {
                ToggleButton bouton = new ToggleButton("");
                bouton.setPrefSize(60, 60);
                int x = i;
                int y=j;
                boutons[i][j] = bouton;
                boutons[i][j].getProperties().put("cases_prises",0);
                boutons[i][j].getStyleClass().add("button");
                if (((i%2==1)||(j%2==1))&&!((i%2==1)&&(j%2==1))) {
                    boutons[i][j].getStyleClass().add("button2");
                }
                bouton.setOnAction( e -> {
                    Toggle selectedToggle = bt_type.getSelectedToggle();
                    if (selectedToggle == null) {
                        System.out.println("Merci de sélectionner un type de bateau.");
                        return;
                    }
                    ToggleButton type_bateau = (ToggleButton)selectedToggle;
                    switch (type_bateau.getText()) {
                        case "Cuirassé":
                            placement("cuirassé", 4, taille_plateau, 1, a, b,  boutons, x, y, bt_type_cuirasse,bt_type,index);
                            break;
                        case "Croiseur":
                            placement("croiseur", 3, taille_plateau, 2, a, b,  boutons, x, y, bt_type_croiseur,bt_type,index);
                            break;
                        case "Destroyer":
                            placement("destroyer", 2, taille_plateau, 3, a, b,  boutons, x, y, bt_type_destroyer,bt_type,index);
                            break;
                        case "Torpilleur":
                            placement("torpilleur", 1, taille_plateau, 4, a, b,  boutons, x, y, bt_type_torpilleur,bt_type,index);
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

        StackPane centrer = new StackPane();
        VBox centrer2 = new VBox();
        centrer2.getChildren().addAll(centrer,select_bat);
        centrer.getChildren().addAll(grille);
        centrer2.setSpacing(20);
        centrer2.setAlignment(Pos.CENTER);
        StackPane racine = new StackPane();
        racine.getStyleClass().add("racine");
        racine.getChildren().addAll(vagues,centrer2);
        racine.setAlignment(Pos.CENTER);

        Scene scene = new Scene(racine,800,800, Color.rgb(99, 107, 194));
        scene.getStylesheets().add("/styles.css");
        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(scene);
        plateau.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}