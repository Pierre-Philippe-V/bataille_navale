import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.*;

public class Main extends Application {

    public static void placement(String bateau, int taille_bat, int taille_plateau, int nb_max_bat, int[] a,
                                 int[] b, int compteur[], Button[][] boutons, int x, int y,int[] k,ToggleButton bt_type){

        a[1]=taille_plateau;
        b[1]=taille_plateau;
        if (bt_type.getUserData().equals(false)){
        if (taille_bat>1) {
            if (compteur[0] < 2) {
                a[compteur[0]] = x;
                b[compteur[0]] = y;
                boutons[x][y].setText("\uD83D\uDD34");
                boutons[x][y].getStyleClass().add("pre_select");
                compteur[0]++;
                //System.out.println(a[0] + "," + a[1]);
                if (compteur[0] > 1) {
                    if ((abs(a[0] - a[1]) > 0) && b[0] == b[1]) {
                        if ((abs(a[1] - a[0])) >= taille_bat) {
                            System.out.println("Votre " + bateau + " est trop grand (il doit faire " + taille_bat + "  cases). Veuillez choisir une nouvelle case");
                            compteur[0] = 1;
                        } else if (abs(a[1] - a[0]) < (taille_bat - 1) && (compteur[0] > 1)) {
                            System.out.println("Votre " + bateau + " est trop petit (il doit faire " + taille_bat + "  cases). Veuillez choisir une nouvelle case");
                            compteur[0] = 1;
                        } else {
                            for (int z = min(a[0], a[1]); z <= max(a[0], a[1]); z++) {

                                boutons[z][y].setText("\uD83D\uDD34");
                                boutons[z][y].getStyleClass().add("bat_rempli");
                            }
                        }
                    } else if ((abs(b[0] - b[1]) > 0) && a[0] == a[1]) {
                        // On pourrait faire une fonction pour les deux boucles pour éviter la redondance.
                        if ((abs(b[1] - b[0])) >= taille_bat) {
                            System.out.println("Votre " + bateau + " est trop grand (il doit faire " + taille_bat + "  cases). Veuillez choisir une nouvelle case");
                            compteur[0] = 1;
                        } else if (abs(b[1] - b[0]) < (taille_bat - 1) && (compteur[0] > 1)) {
                            System.out.println("Votre " + bateau + " est trop petit (il doit faire " + taille_bat + "  cases). Veuillez choisir une nouvelle case");
                            compteur[0] = 1;
                        } else {
                            for (int z = min(b[0], b[1]); z <= max(b[0], b[1]); z++) {

                                boutons[x][z].setText("\uD83D\uDD34");
                                boutons[x][z].getStyleClass().add("bat_rempli");
                            }
                        }
                    } else {
                        System.out.println("Merci de bien choisir un placement soit vertical, soit horizontal.");
                        compteur[0] = 1;
                    }
                }
            } else if ((nb_max_bat -1) > k[0]) {
                compteur[0] = 0;
                k[0] += 1;
                System.out.println("Saisie du " + (k[0] + 1) + "ème "+bateau+".");
            } else  {
                System.out.println("Tous les "+bateau+"s sont placés.");
                compteur[0] = 0;
                k[0] = 0;
                bt_type.setUserData(true);
                System.out.println(bt_type.getUserData());
                System.out.print(bt_type);
                bt_type.getStyleClass().add("bt_type_bat_desactive");
                bt_type.setDisable(true);

            }
        }
        else {
            if ((nb_max_bat) > k[0] ) {
                compteur[0] = 0;
                k[0] += 1;
                boutons[x][y].setText("\uD83D\uDD34");
                boutons[x][y].setUserData(k[0]);
                boutons[x][y].getStyleClass().add("bat_rempli");

            }
            if (nb_max_bat<=k[0]){
                System.out.println("Tous les "+bateau+"s sont placés.");
                compteur[0] = 0;

                bt_type.getStyleClass().add("bt_type_bat_desactive");
                bt_type.setDisable(true);
                bt_type.setUserData(true);
            }

        }}
    }
    private int taille_plateau = 10;                // taille de la grille (n x n)
    private Button[][] boutons;     // matrice des boutons

    @Override
    public void start(Stage plateau) {
        GridPane grille = new GridPane();
        boutons = new Button[taille_plateau][taille_plateau];
        final int[] compteur = {0};
        int[] a = new int[2];
        int[] b = new int[2];
        final int[] k = {0};
        HBox select_bat = new HBox();

        ToggleButton bt_type_cuirasse = new ToggleButton("Cuirassé");
        ToggleButton bt_type_croiseur = new ToggleButton("Croiseur");
        ToggleButton bt_type_destroyer = new ToggleButton("Destroyer");
        ToggleButton bt_type_torpilleur = new ToggleButton("Torpilleur");
        bt_type_cuirasse.getStyleClass().add("bt_type_bat");
        bt_type_croiseur.getStyleClass().add("bt_type_bat");
        bt_type_destroyer.getStyleClass().add("bt_type_bat");
        bt_type_torpilleur.getStyleClass().add("bt_type_bat");
        select_bat.getChildren().addAll(bt_type_torpilleur,bt_type_cuirasse,bt_type_croiseur,bt_type_destroyer);
        ToggleGroup bt_type = new ToggleGroup();
        bt_type_cuirasse.setToggleGroup(bt_type);
        bt_type_croiseur.setToggleGroup(bt_type);
        bt_type_destroyer.setToggleGroup(bt_type);
        bt_type_torpilleur.setToggleGroup(bt_type);
        bt_type_torpilleur.setUserData(false);
        bt_type_croiseur.setUserData(false);
        bt_type_cuirasse.setUserData(false);
        bt_type_destroyer.setUserData(false);
        for (int i = 0; i < taille_plateau; i++) {
            for (int j = 0; j < taille_plateau; j++) {
                Button bouton = new Button("0");
                bouton.setPrefSize(60, 60);
                int x = i;
                int y=j;
                boutons[i][j] = bouton;
                bouton.setOnAction( e -> {
                    Toggle selectedToggle = bt_type.getSelectedToggle();
                    if (selectedToggle == null) {
                        System.out.println("Merci de sélectionner un type de bateau.");
                        return;
                    }
                    ToggleButton type_bateau = (ToggleButton)selectedToggle;
                    switch (type_bateau.getText()) {
                        case "Cuirassé":
                            placement("cuirassé", 4, taille_plateau, 1, a, b, compteur, boutons, x, y, k,bt_type_cuirasse);
                            break;
                        case "Croiseur":

                            placement("croiseur", 3, taille_plateau, 2, a, b, compteur, boutons, x, y, k,bt_type_croiseur);
                            break;
                        case "Destroyer":
                            placement("destroyer", 2, taille_plateau, 3, a, b, compteur, boutons, x, y, k,bt_type_destroyer);
                            break;
                        case "Torpilleur":
                            placement("torpilleur", 1, taille_plateau, 4, a, b, compteur, boutons, x, y, k,bt_type_torpilleur);
                            break;
                        default:
                            System.out.println("Sélection de bateau non reconnue.");
                    }
                });
                grille.add(bouton, j, i);
            }
        }

        select_bat.setSpacing(10);
        select_bat.setAlignment(Pos.CENTER);

        grille.setHgap(10);
        grille.setVgap(10);
        grille.setAlignment(Pos.CENTER);

        StackPane centrer = new StackPane();
        centrer.getChildren().addAll(grille);
        VBox racine = new VBox();
        racine.getStyleClass().add("racine");
        racine.getChildren().addAll(centrer,select_bat);
        racine.setAlignment(Pos.CENTER);
        racine.setSpacing(20);
        Scene scene = new Scene(racine,700,700, Color.rgb(99, 107, 194));
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