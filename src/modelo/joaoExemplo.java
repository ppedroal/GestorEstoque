
package modelo;

import javax.swing.JOptionPane;


public class joaoExemplo {

    public static void main (String[] args){
            int a = Integer.parseInt(JOptionPane.showInputDialog("Menu:\n1 - Cadastro de produtos.\n2 - Movimentação.\n3 - Reajustes de preços.\n4 - Relatórios\n0 - Finalizar."));

            do {
            switch (a){
                case 1:
                    //Aqui iremos colocar o direcionamento para a "página" de cadastro dos produtos.
                    break;
                case 2:
                    //Aqui iremos colocar o direcionamento para a "página" de Movimentação.
                    break;
                case 3:
                    //Aqui iremos colocar o direcionamento para a "página" de Reajustes de preços.
                    break;
                case 4:
                    //Aqui iremos colocar o direcionamento para a "página" de Relatórios.
                    break;
                case 0:
                    //Aqui iremos colocar o direcionamento para a "página" de Finalização (onde teremos que encerrar o sistema).
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "opção inválida.");
                    break;
            }
        }
            while (a != 0);
    }
}
