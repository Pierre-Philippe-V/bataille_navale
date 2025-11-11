import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Main extends Application {

    private int taille_plateau = 10;                // taille de la grille (n x n)
    private ValueButton[][] boutons;     // matrice des boutons

    @Override
    public void start(Stage plateau) {
        GridPane grille = new GridPane();
        boutons = new ValueButton[taille_plateau][taille_plateau];
        final int[] compteur = {0};
        int[] a = new int[2];
        int[] b = new int[2];
        for (int i = 0; i < taille_plateau; i++) {
            for (int j = 0; j < taille_plateau; j++) {
                int index = i * taille_plateau + j;              // indice unique
                ValueButton bouton = new ValueButton(0);
                bouton.setPrefSize(60, 60);
                int x = i;
                int y=j;
                int limite =0;
                boutons[i][j] = bouton;

                bouton.setOnAction(e -> {
                    a[1]=taille_plateau;
                    b[1]=taille_plateau;
//                    System.out.println(x+""+y);
                    if (compteur[0]<2){
                        a[compteur[0]] = x;
                        b[compteur[0]] = y;

                        compteur[0]++;
                        System.out.println(a[0]+","+a[1]);
                        if (compteur[0]>1){
                        if((abs(a[0]-a[1])>0)&&b[0]==b[1]){
                            if ((abs(a[1] - a[0])) >= 4) {
                                System.out.println("Votre [bateau] est trop grand (il doit faire [taille_bat] cases). Veuillez choisir une nouvelle case");
                                compteur[0] = 1;
                            } else if (abs(a[1] - a[0]) < 3 && (compteur[0] > 1)) {
                                System.out.println("Votre [bateau] est trop petit (il doit faire [taille_bat] cases). Veuillez choisir une nouvelle case");
                                compteur[0] = 1;
                            } else {
                                for (int z = min(a[0],a[1]); z <= max(a[0],a[1]); z++) {

                                    boutons[z][y].setText("\uD83D\uDD34");
                                }
                            }
                        }else if((abs(b[0]-b[1])>0)&&a[0]==a[1]){
                            // On pourrait faire une fonction pour les deux boucles pour éviter la redondance.
                            if ((abs(b[1] - b[0])) >= 4) {
                                System.out.println("Votre [bateau] est trop grand (il doit faire [taille_bat] cases). Veuillez choisir une nouvelle case");
                                compteur[0] = 1;
                            } else if (abs(b[1] - b[0]) < 3 && (compteur[0] > 1)) {
                                System.out.println("Votre [bateau] est trop petit (il doit faire [taille_bat] cases). Veuillez choisir une nouvelle case");
                                compteur[0] = 1;
                            } else {
                                for (int z = min(b[0],b[1]); z <= max(b[0],b[1]); z++) {

                                    boutons[x][z].setText("\uD83D\uDD34");
                                }
                            }
                        }else{
                            System.out.println("Merci de bien choisir un placement soit vertical, soit horizontal.");
                            compteur[0]=1;
                        }}
                    }else{

                        System.out.println("Le nombre de cases max est atteint");
                    }

                });




                grille.add(bouton, j, i);
            }
        }


        grille.setHgap(10);
        grille.setVgap(10);
        grille.setAlignment(Pos.CENTER);

        StackPane centrer = new StackPane();
        centrer.getChildren().addAll(grille);
        VBox racine = new VBox();
        racine.getStyleClass().add("racine");
        racine.getChildren().addAll(centrer);
        racine.setAlignment(Pos.CENTER);
        racine.setSpacing(20);
        Scene scene = new Scene(racine,700,700, Color.rgb(99, 107, 194));
        scene.getStylesheets().add("/styles.css");
        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(scene);
        plateau.show();
        setButtonValue(1,2,3);
    }

    // méthode pour réassigner une valeur après coup
    public void setButtonValue(int i, int j, int newValue) {
        if (i >= 0 && i < taille_plateau && j >= 0 && j < taille_plateau) {
            boutons[i][j].setValue(newValue);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ValueButton extends Button {
    private int value;

    public ValueButton(int value) {
        super(String.valueOf(value));
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
    }
}

