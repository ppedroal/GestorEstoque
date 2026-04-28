package telas;

import javax.swing.*;

import modelo.Produto;

public class MenuProduto {

    Produto[] produtos = new Produto[100];
    int total = 0;

    public void menu() {
        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "Menu de Produtos\n" +
                    "1 - Cadastrar Produto\n" +
                    "2 - Alterar Produtos\n" +
                    "3 - Consultar Produtos\n" +
                    "4 - Excluir Produtos\n" +
                    "5 - Retornar\n" +
                    "Opção: "
            );

            switch (opcao) {
                case "1":
                    incluir();
                    break;
                case "2":
                    alterar();
                    break;
                case "3":
                    consultar();
                    break;
                case "4":
                    excluir();
                    break;
                case "5":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while (true);
    }

    public void incluir() {

        if (total == 100) {
            JOptionPane.showMessageDialog(null, "Limite de produtos atingido!");
            return;
        }

        String novaInclusao;

        do {
            Produto produto = new Produto();

            // NOME
            while (true) {
                produto.nome = JOptionPane.showInputDialog(
                        "INLCUSÃO DE PRODUTO\n\nNOME: "
                );

                boolean existe = false;

                for (int i = 0; i < total; i++) {
                    if (produtos[i].nome.equalsIgnoreCase(produto.nome)) {
                        existe = true;
                        break;
                    }
                }

                if (existe) {
                    JOptionPane.showMessageDialog(null, "Já existe um produto com este nome!");
                } else {
                    break;
                }
            }

            // PREÇO
            while (true) {
                produto.preco = Double.parseDouble(
                        JOptionPane.showInputDialog("PREÇO: ")
                );

                if (produto.preco > 0) {
                    break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Preço deve ser maior que zero!");
                }
            }

            // UNIDADE
            produto.unidade = JOptionPane.showInputDialog("UNIDADE: ");

            // QUANTIDADE
            while (true) {
                produto.quantidade = Integer.parseInt(
                        JOptionPane.showInputDialog("QUANTIDADE: ")
                );

                if (produto.quantidade >= 0) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade deve ser maior ou igual a zero!");
                }
            }

            // CONFIRMACAO
            String confirma = JOptionPane.showInputDialog("CONFIRMA INCLUSÃO? (S/N)");

            if (confirma.equalsIgnoreCase("S")) {
                produtos[total] = produto;
                total++;

                JOptionPane.showMessageDialog(null, "Produto Incluido com sucesso!");
            }

            novaInclusao = JOptionPane.showInputDialog("NOVO INCLUSÃO DE PRODUTO? (S/N)");
        }  while (novaInclusao.equals("S"));
    }

    public void alterar() {

    }

    public void consultar() {

    }

    public void excluir() {

    }
}
