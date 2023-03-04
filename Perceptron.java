import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Perceptron {
    private int nbLigne;
    private int nbColumn;
    private Workbook workbook = null;
    private Sheet sheet = null;
    float w[];
    float X[][];
    boolean sortie;
    boolean testAlgo;

    // Constructeur
    Perceptron() {
        this.nbLigne = 0;
        this.nbColumn = 0;
        this.sortie = false;
        this.testAlgo = false;
    }

    public boolean egalite(float b[]) {
        boolean rep = true;
        int i = 0, taille = this.w.length;
        while ((rep == true) && (i < taille)) {
            if (this.w[i] != b[i])
                rep = false;
            i++;
        }
        return rep;
    }

    public float somme(int position) {
        int s = 0;
        int i1 = 0, taille = this.w.length;
        for (i1 = 0; i1 < taille; i1++)
            s += this.w[i1] * this.X[position][i1];
        return s;
    }

    public void copie(float b[]) {
        int i = 0;
        for (i = 0; i < this.w.length; i++)
            b[i] = this.w[i];
    }

    public void chargerFichier() {
        String path = "";
        try {
            String extension = "";
            do {
                File excelFile;
                String defaultCurrentDirectory = "C:\\Users\\Ame-Ahmada-Ngom\\Desktop\\java_Programme\\Perception";
                JFileChooser excFileChooser = new JFileChooser(defaultCurrentDirectory);
                int excelChoser = excFileChooser.showOpenDialog(null);

                // Test if you are choosen a new file
                if (excelChoser == JFileChooser.APPROVE_OPTION) {
                    excelFile = excFileChooser.getSelectedFile();
                    path = excelFile.getPath();
                    String nom = excelFile.getName();
                    int k = nom.lastIndexOf(".");
                    extension = nom.substring(k + 1);
                    System.out.println("Path : " + path);
                    System.out.println("Extension : " + extension);
                    if (!extension.equals("xls"))
                        JOptionPane.showMessageDialog(null, "Attention l'extension de fichier est incorrecte !!!");
                }
            } while (!extension.equals("xls"));

            // Récupération du classeur Excel (en lecture)
            this.workbook = Workbook.getWorkbook(new File(path));
            /*
             * Un fichier excel est composé de plusieurs feuilles, on y accède de la manière
             * suivante les numeros correspondent au numeero des feuilles.
             */
            this.sheet = workbook.getSheet(0);
            this.nbColumn = this.sheet.getColumns();
            this.nbLigne = this.sheet.getRows();
        } catch (BiffException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } 
        this.sortie = true;
    }

    public void Rosenblatt() {
        int i = 0, j = 0;
        int n = this.nbLigne, m = this.nbColumn;
        System.out.println("Affichage du fichier obtenu  :  " + this.nbLigne + " lignes et " + this.nbColumn);
        this.w = new float[m - 1];
        this.X = new float[n - 1][m];
        for (i = 1; i < n; i++) {
            for (j = 0; j < m; j++) {
                /* Pour acceder aux cellules */
                Cell a1 = this.sheet.getCell(j, i);
                String contenuA1 = a1.getContents();
                this.X[i - 1][j] = Float.parseFloat(contenuA1);
            }
            System.out.println();
        }
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < m; j++)
                if (j == 0)
                    System.out.print(this.X[i][j]);
                else
                    System.out.print("  ;  " + this.X[i][j]);
            System.out.println();
        }
        float w0[] = new float[m - 1];
        for (i = 0; i < m - 1; i++) {
            this.w[i] = 1;
        }

        // Algorithme perceptron
        float oj, cj_oj;
        int k = 0, step = 0;
        i = 0;
        this.copie(w0);
        do {
            String a = "";
            a = a + "Donner l'indice de la stabilité inferieur a : " + (n - 2);
            String stables = JOptionPane.showInputDialog(null, a);
            try {
                k = Integer.parseInt(stables);
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
            }
        } while (k > n - 1);
        int stable = 0;
        while ((step < n - 1) || (stable != k)) {
            if (this.somme(step) > 0)
                oj = 1;
            else
                oj = 0;
            cj_oj = this.X[step][m - 1] - oj;
            System.out.println("cj = " + this.X[step][m - 1] + " ; oj = " + oj + " ; cj - oj = " + cj_oj + "  ;  ");
            for (i = 0; i < m - 1; i++) {
                w[i] = w[i] + cj_oj * this.X[step][i];
            }
            if (this.egalite(w0))
                stable++;
            else {
                this.copie(w0);
                stable = 0;
            }
            step++;
            if (step == n - 1) {
                if (stable >= k)
                    break;
                else {
                    String rep = "";
                    do {
                        rep = JOptionPane.showInputDialog(null, "voulez vous continuer l'apprentissage ? O/N");
                    } while (!rep.equals("O") && !rep.equals("N"));
                    if ('O' == rep.charAt(0)) {
                        System.out.println("---------------------------------");
                        for (int l = 0; l < this.w.length; l++)
                            System.out.print(w[l] + "  ;  ");
                        System.out.println();
                        System.out.println("---------------------------------");
                        step = 0;
                    } else {
                        break;
                    }
                }
            }
        }

        String message = "";
        message = "W = ( " + this.w[0];
        for (i = 1; i < w.length; i++) {
            message += " ; " + this.w[i];
        }
        message += " )";
        if (stable >= k) {
            JOptionPane.showMessageDialog(null, message);
            this.testAlgo = true;
        } else {
            message += " mais il est n'est pas stable";
            JOptionPane.showMessageDialog(null, message);
            this.testAlgo = false;
        }
        System.out.println("dans la methode sortie = " + this.sortie);
        
            if (this.workbook != null) {
                /* On ferme le worbook pour libérer la mémoire */
                this.workbook.close();
            }
            

    }

    public void test() {
        float resultatTest = 0;
        int i = 0;
        float test[] = new float[this.w.length];
        test[0] = 1;
        resultatTest = this.w[0] * test[0];
        System.out.println(resultatTest);
        for (i = 1; i < this.w.length; i++) {
            String reponse = JOptionPane.showInputDialog(null, "Donner l'élément ");
            test[i] = Float.parseFloat(reponse);
            resultatTest += (w[i] * test[i]);
            System.out.println(resultatTest);
        }
        if (resultatTest > 0) {
            JOptionPane.showMessageDialog(null, "La fleur est de type iris-versicolor");
        } else {
            JOptionPane.showMessageDialog(null, "La fleur est de type iris-setosa");
        }
    }

    public boolean verifierFichier() {
        return this.sortie;
    }

    public boolean verifierApprentissage() {
        return this.testAlgo;
    }
}
