/*
 * projet
 *
 * Copyright 2025 Ratpi <Ratpi@PC_DE_P-LOUIS>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 *
 */
import java.util.Random;

public class placement {

    public static void main(String[] args) {

        int Plateau_1[][] = new int[10][10];
        int Plateau_2[][] = new int[10][10];
        int i, j, d, num_bat, min, max, taille_bat = 0;
        String bat, case_;
        for (i = 0; i <= 9; i++) {
            for (j = 0; j <= 9; j++) {
                Plateau_1[i][j] = 0;
                Plateau_2[i][j] = 0;
            }
        }
        for (i = 0; i <= 9; i++) {
            System.out.println("Saisir quel type de bateau vous voulez placer parmi cuirasse, croiseurs, destroyers et torpilleurs");
            bat = Lire.S();
            if (bat == "cuirasse") {
                taille_bat = 4;
                num_bat = 1;
            } else {
                if (bat == "croiseurs") {
                    taille_bat = 3;
                    min = 2;
                    max = 3;
                    Random random = new Random();
                    num_bat = random.nextInt(max + min) + min;
                } else {
                    if (bat == "destroyers") {
                        taille_bat = 2;
                        min = 4;
                        max = 6;
                        Random random = new Random();
                        num_bat = random.nextInt(max + min) + min;
                    } else {
                        if (bat == "torpilleurs") {
                            taille_bat = 1;
                            min = 7;
                            max = 10;
                            Random random = new Random();
                            num_bat = random.nextInt(max + min) + min;
                        }
                    }
                }
            }
        }
        System.out.println("« Saisir 0 si vous voulez placer le bateau horizontalement, et 1 si vous voulez le placer verticalement");
        d = Lire.i();
        if (d == 0) {
            System.out.println("Saisir la case la plus à gauche de votre bateau");
            case_ = Lire.S();
            for (i = 0; i <= (taille_bat - 1); i++) {

            }
        }
    }
}