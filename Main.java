import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame fenetre = new JFrame();
        JMenuBar br = new JMenuBar();

        JMenu D = new JMenu("Fichier");
        JMenu E = new JMenu("Perceptron");
        JMenu T = new JMenu("Test");
        JMenu P = new JMenu("Aide");

        JMenuItem N_fichier = new JMenuItem("Charger un fichier");
        JMenuItem A_propos = new JMenuItem("A Propos");
        JMenuItem Quitter = new JMenuItem("Quitter");
        JMenuItem algo1 = new JMenuItem("RosenBlatt");
        JMenuItem test = new JMenuItem("lancer un test");

        // JMenuItem N_Produit = new JMenuItem("Nouveau");
        // JMenuItem C_Produit = new JMenuItem("Consulter");
        // boolean sortie;

        // JPanel panH = new JPanel();

        fenetre.setTitle("Ngom");
        fenetre.setSize(new Dimension(400, 400));
        D.add(N_fichier);
        D.add(A_propos);
        D.addSeparator();
        D.add(Quitter);
        E.add(algo1);
        T.add(test);
        br.add(D);
        br.add(E);
        br.add(T);
        br.add(P);
        br.setBackground(Color.darkGray);
        D.setForeground(Color.white);
        E.setForeground(Color.white);
        T.setForeground(Color.white);
        P.setForeground(Color.white);
        fenetre.setJMenuBar(br);
        fenetre.setVisible(true);
        // creation d'un objet perceptron
        Perceptron perceptron = new Perceptron();
        // Declaration de la feuille excel et ses dimensions

        // chargement du fichier excel
        // Action du bouton charger un fichier de la barre de menu
        N_fichier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                perceptron.chargerFichier();
            }
        });

        // Lancer l'algorithme de RosenBlatt
        // Action du bouton Rosenblatt de la barre de menu
        algo1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (perceptron.verifierFichier() == false)
                    JOptionPane.showMessageDialog(null, "Veuiller charger d'abords un fichier excel !!!");
                else
                    perceptron.Rosenblatt();
            }
        });

        // Tester l'apprentissage Action du bouton test de la barre de menu
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (perceptron.verifierApprentissage() == false)
                    JOptionPane.showMessageDialog(null,
                            "Votre machine est un tete de noeud veullez l'apprendre quelque chose !!!");
                else
                    perceptron.test();
            }
        });
        // Acttion du bouton quitter de la barre de menu
        Quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /* On accède aux cellules avec la méthode getCell(indiceColonne, indiceLigne) */
        // Cell a1 = sheet.getCell(4, 4);

    }

}
