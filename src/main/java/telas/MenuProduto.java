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
                    "MENU DE PRODUTOS\n\n"
                    + "1 - Cadastrar Produto\n"
                    + "2 - Alterar Produtos\n"
                    + "3 - Consultar Produtos\n"
                    + "4 - Excluir Produtos\n"
                    + "5 - Retornar\n\n"
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

            // NOME - Refatorado com travas de segurança e validação de duplicidade
            while (true) {
                String entradaNome = JOptionPane.showInputDialog(
                        "INCLUSÃO DE PRODUTO\n\nNOME: "
                );

                // Trava Nº 1: Prevenção contra NullPointerException.
                // Caso o usuário clicar em "Cancelar" ou fechar no "X", o método é abortado.
                if (entradaNome == null) {
                    return;
                }

                entradaNome = entradaNome.trim();

                // Trava Nº 2: Impede o cadastro de produtos com o nome em branco.
                if (entradaNome.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "NOME INVÁLIDO!\nO nome do produto não pode ficar em branco.",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Trava Nº 3: Regra de Negócio - Verificação de Duplicidade.
                boolean existe = false;

                for (int i = 0; i < total; i++) {
                    if (produtos[i].nome.equalsIgnoreCase(entradaNome)) {
                        existe = true;
                        break;
                    }
                }

                if (existe) {
                    JOptionPane.showMessageDialog(null,
                            "PRODUTO DUPLICADO!\nJá existe um produto cadastrado com este nome.",
                            "Erro de Validação",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    produto.nome = entradaNome;
                    break;
                }
            }

            // PREÇO - Refatorado
            while (true) {
                String entradaPreco = JOptionPane.showInputDialog("INCLUSÃO DE PRODUTO\n\nPREÇO: ");

                double precoValidado = parseEValidarPreco(entradaPreco);

                if (precoValidado != -1.0) {
                    produto.preco = precoValidado;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "PREÇO INVÁLIDO!\n"
                            + "Por favor, introduza um valor numérico maior que zero (Ex: 10.50).",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            // UNIDADE
            produto.unidade = JOptionPane.showInputDialog("UNIDADE (KG, L, UN...): ");
            produto.unidade = produto.unidade.trim().toUpperCase();

            while (!produto.unidade.matches("[A-Z]+")) {
                JOptionPane.showMessageDialog(null, "Digite apenas letras!(KG, L, UN...)");

                produto.unidade = JOptionPane.showInputDialog("UNIDADE (KG, L, UN...): ");
                produto.unidade = produto.unidade.trim().toUpperCase();
            }

            // QUANTIDADE - Refatorado
            while (true) {
                String entradaQtd = JOptionPane.showInputDialog("INCLUSÃO DE PRODUTO\n\nQUANTIDADE: ");

                int qtdValidada = parseEValidarQuantidade(entradaQtd);

                if (qtdValidada != -1) {
                    produto.quantidade = qtdValidada;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "QUANTIDADE INVÁLIDA!\n"
                            + "Por favor, introduza um número inteiro maior ou igual a zero.",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            String confirma = JOptionPane.showInputDialog(
                    "─────────────────────────────────\n"
                    + "      CONFIRMA A INCLUSÃO?\n"
                    + "─────────────────────────────────\n\n"
                    + "Nome      : " + produto.nome + "\n"
                    + "Preço     : R$ " + String.format("%.2f", produto.preco) + "\n"
                    + "Unidade   : " + produto.unidade + "\n"
                    + "Quantidade: " + produto.quantidade + "\n\n"
                    + "─────────────────────────────────\n"
                    + "(S/N)"
            );

            if (confirma != null && confirma.equalsIgnoreCase("S")) {
                produtos[total] = produto;
                total++;
                JOptionPane.showMessageDialog(null,
                        "Produto incluído com sucesso!");
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
                            + "Produto   : " + produto.nome + "\n"
                            + "Preço     : R$ " + String.format("%.2f", produto.preco) + "\n"
                            + "Unidade   : " + produto.unidade + "\n"
                            + "Quantidade: " + formatarUnidade(produto.unidade, produto.quantidade) + "\n"
                    );

                    // NOVO PREÇO - Refatorado
                    while (true) {
                        String entradaNovoPreco = JOptionPane.showInputDialog("NOVO PREÇO: ");

                        double precoValidado = parseEValidarPreco(entradaNovoPreco);

                        if (precoValidado != -1.0) {
                            produto.preco = precoValidado;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "PREÇO INVÁLIDO!\n"
                                    + "Por favor, introduza um valor numérico maior que zero.",
                                    "Erro de Validação",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    produto.unidade = JOptionPane.showInputDialog("NOVA UNIDADE (KG, L, UN...): ");

                    // NOVA QUANTIDADE - Refatorado
                    while (true) {
                        String entradaNovaQtd = JOptionPane.showInputDialog("NOVA QUANTIDADE: ");

                        int qtdValidada = parseEValidarQuantidade(entradaNovaQtd);

                        if (qtdValidada != -1) {
                            produto.quantidade = qtdValidada;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "QUANTIDADE INVÁLIDA!\n"
                                    + "Por favor, introduza um número inteiro maior ou igual a zero.",
                                    "Erro de Validação",
                                    JOptionPane.ERROR_MESSAGE);
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
        } while (novaAlteracao != null && novaAlteracao.equalsIgnoreCase("S"));
    }

    public void consultar() {
        String novaConsulta;
        do {
            boolean existe = false;
            String nome = JOptionPane.showInputDialog(
                    "CONSULTAR PRODUTO\n\nDIGITE O NOME DO PRODUTO: "
            );

            if (nome == null) {
                return;
            }

            for (int i = 0; i < total; i++) {
                if (produtos[i].nome.equalsIgnoreCase(nome.trim())) {

                    String unidadeFormatada = formatarUnidade(
                            produtos[i].unidade,
                            produtos[i].quantidade
                    );

                    JOptionPane.showMessageDialog(null,
                            "======= DADOS DO PRODUTO =======\n\n"
                            + "Produto   : " + produtos[i].nome + "\n"
                            + "Preco     : R$ " + String.format("%.2f", produtos[i].preco) + "\n"
                            + "Unidade   : " + produtos[i].unidade + "\n"
                            + "Quantidade: " + unidadeFormatada + "\n"
                            + "================================"
                    );
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                JOptionPane.showMessageDialog(null, "Produto nao encontrado!");
            }

            novaConsulta = JOptionPane.showInputDialog(
                    "DESEJA FAZER UMA NOVA CONSULTA? (S/N)"
            );

        } while (novaConsulta != null && novaConsulta.equalsIgnoreCase("S"));
    }

    public void excluir() {

        String novaExclusao;

        do {

            String nome = JOptionPane.showInputDialog(
                    "EXCLUSÃO DE PRODUTO\n\nDIGITE O NOME DO PRODUTO:"
            );

            if (nome == null) {
                break;
            }

            boolean existe = false;

            for (int i = 0; i < total; i++) {

                if (produtos[i].nome.equalsIgnoreCase(nome)) {

                    Produto produto = produtos[i];

                    JOptionPane.showMessageDialog(null,
                            "PRODUTO ENCONTRADO\n\n"
                            + "Produto: " + produto.nome + "\n"
                            + "Preço: R$ " + String.format("%.2f", produto.preco) + "\n"
                            + "Unidade: " + produto.unidade + "\n"
                            + "Quantidade em estoque: " + produto.quantidade
                    );

                    String quantidadeStr = JOptionPane.showInputDialog(
                            "Quantos itens deseja excluir?"
                    );

                    if (quantidadeStr == null) {
                        break;
                    }

                    int quantidadeExcluir = Integer.parseInt(quantidadeStr);

                    if (quantidadeExcluir <= 0) {

                        JOptionPane.showMessageDialog(null,
                                "Digite uma quantidade válida!");

                    } else if (quantidadeExcluir > produto.quantidade) {

                        JOptionPane.showMessageDialog(null,
                                "Quantidade maior que o estoque!");

                    } else {

                        String confirma = JOptionPane.showInputDialog(
                                "CONFIRMA EXCLUSÃO DE "
                                + quantidadeExcluir
                                + " UNIDADE(S)? (S/N)"
                        );

                        if (confirma != null && confirma.equalsIgnoreCase("S")) {

                            if (quantidadeExcluir < produto.quantidade) {

                                produto.quantidade -= quantidadeExcluir;

                                JOptionPane.showMessageDialog(null,
                                        "Quantidade removida com sucesso!\n\n"
                                        + "Estoque restante: "
                                        + produto.quantidade
                                );

                            } else {

                                for (int j = i; j < total - 1; j++) {
                                    produtos[j] = produtos[j + 1];
                                }

                                total--;

                                JOptionPane.showMessageDialog(null,
                                        "Produto removido completamente!");
                            }
                        }
                    }

                    existe = true;
                    break;
                }
            }

            if (!existe) {

                JOptionPane.showMessageDialog(null,
                        "Produto não encontrado!");

            }

            novaExclusao = JOptionPane.showInputDialog(
                    "DESEJA FAZER UMA NOVA EXCLUSÃO? (S/N)"
            );

            if (novaExclusao == null) {
                break;
            }

        } while (novaExclusao.equalsIgnoreCase("S"));
    }

    private String formatarUnidade(String unidade, int quantidade) {
        switch (unidade.toUpperCase()) {
            case "KG":
                return quantidade + " KG (Quilogramas)";
            case "G":
                return quantidade + " G (Gramas)";
            case "L":
                return quantidade + " L (Litros)";
            case "ML":
                return quantidade + " ML (Mililitros)";
            case "UN":
                return quantidade + " UN (Unidades)";
            case "CX":
                return quantidade + " CX (Caixas)";
            case "PC":
                return quantidade + " PC (Pacotes)";
            default:
                return quantidade + " " + unidade;
        }
    }

    // ─── VALIDADORES ──────────────────────────────────────────────────────────

    /* Processar a entrada do usuário, adaptar a formatação, converter para Double
    e validar se o valor é maior que zero. */
    private double parseEValidarPreco(String entrada) {
        // Trava Nº 1: Se o usuário clicar em cancelar na janela ou enviar caractere vazio.
        if (entrada == null || entrada.trim().isEmpty()) {
            return -1.0;
        }

        try {
            /* Nosso usuário típico será brasileiro, cujo hábito majoritário consiste em usar
            vírgulas para separar decimais em valores monetários e a pontuação exigida pelo
            Java é o padrão americano que utiliza ponto. */
            String entradaCorrigida = entrada.replace(",", ".").trim();
            double preco = Double.parseDouble(entradaCorrigida);

            // Trava Nº 2: Regra de Negócio = O preço DEVE ser maior que zero.
            if (preco > 0) {
                return preco;
            } else {
                return -1.0;
            }

        } catch (NumberFormatException e) {
            /* Trava Nº 3: Se o usuário digitar letras ("vinte reais") em vez de "20,00" ou
            "20.00", a exceção é capturada e o método retorna -1.0. */
            return -1.0;
        }
    }

    /* Tratar a entrada do usuário, converter para Inteiro e validar se a
    quantidade é maior ou igual a zero. */
    private int parseEValidarQuantidade(String entrada) {
        // Trava Nº 1: Prevenção contra NullPointerException e NumberFormatException.
        if (entrada == null || entrada.trim().isEmpty()) {
            return -1;
        }

        try {
            int quantidade = Integer.parseInt(entrada.trim());

            // Trava Nº 2: Regra de Negócio = A quantidade deve ser maior ou igual a zero.
            if (quantidade >= 0) {
                return quantidade;
            } else {
                return -1;
            }

        } catch (NumberFormatException e) {
            /* Trava Nº 3: Interceptar falhas com letras ou números decimais, já que a
            quantidade de peças de estoque deve ser apenas um número inteiro. */
            return -1;
        }
    }
}
