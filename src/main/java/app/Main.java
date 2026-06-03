package app;

import javax.swing.JOptionPane;

import tela.MenuMovimentacao;
import tela.MenuProduto;
import tela.MenuReajustePreco;
import tela.MenuRelatorio;

/**
 * Classe principal do sistema de gestão de estoque.
 *
 * @author Pedro Leite
 * @version 1.0
 */
public class Main {

    /**
     * Inicia a aplicação com o menu principal.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {

        MenuProduto menuProduto = new MenuProduto();
        MenuMovimentacao menuMovimentacao = new MenuMovimentacao();
        MenuReajustePreco menuReajustePreco = new MenuReajustePreco();
        MenuRelatorio menuRelatorio = new MenuRelatorio();

        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "SISTEMA DE ESTOQUE\n\n"
                    + "1 - Administrar produtos\n"
                    + "2 - Movimentação de estoque\n"
                    + "3 - Reajuste de preços\n"
                    + "4 - Relatórios\n"
                    + "0 - Sair\n\n"
                    + "Opção: "
            );

            if (opcao == null) {
                break;
            }

            switch (opcao) {
                case "1":
                    menuProduto.menu();
                    break;
                case "2":

                    menuMovimentacao.produtos = menuProduto.produtos;
                    menuMovimentacao.total = menuProduto.total;

                    menuMovimentacao.menu();
                    break;
                case "3":
                    menuReajustePreco.produtos = menuProduto.produtos;
                    menuReajustePreco.total = menuProduto.total;

                    menuReajustePreco.menu();
                    break;
                case "4":
                    menuRelatorio.produtos = menuProduto.produtos;
                    menuRelatorio.total = menuProduto.total;

                    menuRelatorio.menu();
                    break;
                case "0":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (!opcao.equals("0"));
    }
}
