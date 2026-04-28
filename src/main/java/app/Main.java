package main.java.app;

import javax.swing.*;
import  main.java.telas.MenuProduto;

public class Main {

    public static void main(String[] args) {

        MenuProduto menuProduto = new MenuProduto();

        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "SISTEMA DE ESTOQUE\n\n" +
                    "1 - Administrar produtos\n" +
                    "2 - Movimentação de estoque\n" +
                    "3 - Reajuste de preços\n" +
                    "4 - Relatórios\n" +
                    "0 - Sair\n" +
                    "Opção: "
            );

            switch (opcao) {
                case "1":
                    menuProduto.menu();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento...");
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento...");
                    break;
                case "4":
                    JOptionPane.showMessageDialog(null, "Em desenvolvimento...");
                    break;
                case "0":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }
}
