package telas;

import javax.swing.*;

import modelo.Produto;

public class MenuProduto {

    public Produto[] produtos = new Produto[100];
    public int total = 0;

    public void menu() {
        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "Menu de Produtos\n"
                    + "1 - Cadastrar Produto\n"
                    + "2 - Alterar Produtos\n"
                    + "3 - Consultar Produtos\n"
                    + "4 - Excluir Produtos\n"
                    + "5 - Retornar\n"
                    + "Opção: "
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
                        "INCLUSÃO DE PRODUTO\n\nNOME: "
                );
                produto.nome = produto.nome.trim();

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
            produto.unidade = JOptionPane.showInputDialog("UNIDADE (KG, L, UN...): ");
            produto.unidade = produto.unidade.trim().toUpperCase();

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
        } while (novaInclusao.equals("S"));
    }

    public void alterar() {
        String novaAlteracao;

        do {
            String nome = JOptionPane.showInputDialog("ALTERAR PRODUTO\n\n DIGITE O NOME DO PRODUTO: ");

            boolean existe = false;

            for (int i = 0; i < total; i++) {
                if (produtos[i].nome.equalsIgnoreCase(nome)) {
                    Produto produto = produtos[i];

                    JOptionPane.showMessageDialog(null,
                            "PRODUTO ENCONTRADO\n\n"
                            + "NOME: " + produto.nome + "\n"
                            + "PREÇO: " + produto.preco + "\n"
                            + "QUANTIDADE: " + produto.quantidade + "\n"
                    );

                    while (true) {
                        double novoPreco = Double.parseDouble(
                                JOptionPane.showInputDialog("NOVO PREÇO: ")
                        );
                        if (novoPreco > 0) {
                            produto.preco = novoPreco;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Preço deve ser maior que zero!");
                        }
                    }

                    produto.unidade = JOptionPane.showInputDialog("NOVA UNIDADE (KG, L, UN...): ");

                    while (true) {
                        int novaQtd = Integer.parseInt(
                                JOptionPane.showInputDialog("NOVA QUANTIDADE: ")
                        );
                        if (novaQtd >= 0) {
                            produto.quantidade = novaQtd;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Quantidade deve ser maior ou igual a zero!");
                        }
                    }

                    String confirma = JOptionPane.showInputDialog("CONFIRMA ALTERAÇÃO? (S/N)");
                    if (confirma.equalsIgnoreCase("S")) {
                        JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Alteração cancelada!");
                    }

                    existe = true;
                    break;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null,
                        "Produto não encontrado!");
            }
            novaAlteracao = JOptionPane.showInputDialog("DESEJA FAZER UMA NOVA ALTERAÇÃO? (S/N)");
        } while (novaAlteracao.equalsIgnoreCase("S"));
    }

    public void consultar() {
        String novaConsulta;
        boolean existe = false;

        do {
            String nome = JOptionPane.showInputDialog("CONSULTAR PRODUTO\n\n DIGITE O NOME DO PRODUTO: ");

            for (int i = 0; i < total; i++) {
                if (produtos[i].nome.equalsIgnoreCase(nome)) {
                    JOptionPane.showMessageDialog(null,
                            "NOME: " + produtos[i].nome + "\n"
                            + "PREÇO: R$" + produtos[i].preco + "\n"
                            + "QUANTIDADE: " + produtos[i].quantidade + produtos[i].unidade
                    );
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }

            novaConsulta = JOptionPane.showInputDialog("DESEJA FAZER UMA NOVA CONSULTA? (S/N)");
        } while (novaConsulta.equalsIgnoreCase("S"));
    }

    public void excluir() {

    }
}
