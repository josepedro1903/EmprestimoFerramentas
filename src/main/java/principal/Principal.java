package principal;

import javax.swing.JOptionPane;
import model.Amigo;
import model.Ferramentas;

public class Principal {

    public static void main(String[] args) {

        Amigo amigo = new Amigo();
        //amigo.ler();
        JOptionPane.showMessageDialog(null, amigo.test());

        Ferramentas ferramentas = new Ferramentas();

        JOptionPane.showMessageDialog(null, ferramentas.test());
    
     
    
    }
}
