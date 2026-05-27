package telas;

import javax.swing.JOptionPane;

import modelo.Produto;

public class MenuMovimentacao {

    public Produto[] produtos;
    public int total;

    public void menu() {

        String opcao;

        do {

            opcao = JOptionPane.showInputDialog(
                    "MOVIMENTAÇÃO DE ESTOQUE\n\n"
                    + "1 - Entrada\n"
                    + "2 - Saída\n"
                    + "0 - Retornar\n\n"
                    + "Opção: "
            );

            if (opcao == null) {
                return;
            }

            switch (opcao) {

                case "1":
                    entrada();
                    break;

                case "2":
                    saida();
                    break;

                case "0":
                    return;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Opção inválida!");
            }

        } while (true);
    }

    public void entrada() {

        if (total == 0) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum produto cadastrado!\n"
                    + "Cadastre produtos antes de registrar entradas.");
            return;
        }

        String novaEntrada;

        do {

            String nome = JOptionPane.showInputDialog(
                    "ENTRADA DE PRODUTO\n\n"
                    + "DIGITE O NOME DO PRODUTO:"
            );

            if (nome == null) {
                break;
            }

            boolean existe = false;

            for (int i = 0; i < total; i++) {

                if (produtos[i].nome.equalsIgnoreCase(nome.trim())) {

                    Produto produto = produtos[i];

                    JOptionPane.showMessageDialog(null,
                            "PRODUTO ENCONTRADO\n\n"
                            + "Produto: " + produto.nome + "\n"
                            + "Preço: R$ " + String.format("%.2f", produto.preco) + "\n"
                            + "Unidade: " + produto.unidade + "\n"
                            + "Quantidade Atual: " + produto.quantidade
                    );

                    int quantidadeEntrada;

                    // VALIDA A QUANTIDADE
                    while (true) {
                        String entradaQtd = JOptionPane.showInputDialog("QUANTIDADE DE ENTRADA:");

                        int qtdValidada = parseEValidarQuantidade(entradaQtd);

                        if (qtdValidada > 0) {
                            quantidadeEntrada = qtdValidada;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "QUANTIDADE INVÁLIDA!\n"
                                    + "Por favor, introduza um número inteiro maior que zero.",
                                    "Erro de Validação",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String confirma = JOptionPane.showInputDialog(
                            "CONFIRMA ENTRADA?\n\n"
                            + "Produto              : " + produto.nome + "\n"
                            + "Preço                : R$ " + String.format("%.2f", produto.preco) + "\n"
                            + "Unidade              : " + produto.unidade + "\n"
                            + "Quantidade a adicionar: " + quantidadeEntrada + "\n"
                            + "Estoque atual        : " + produto.quantidade + "\n"
                            + "Estoque após entrada : " + (produto.quantidade + quantidadeEntrada) + "\n\n"
                            + "(S/N)"
                    );

                    if (confirma != null && confirma.equalsIgnoreCase("S")) {
                        produto.quantidade += quantidadeEntrada;
                        JOptionPane.showMessageDialog(null,
                                "ENTRADA REALIZADA COM SUCESSO!\n\n"
                                + "Produto    : " + produto.nome + "\n"
                                + "Novo Estoque: " + produto.quantidade
                        );

                    } else {

                        JOptionPane.showMessageDialog(null,
                                "ENTRADA CANCELADA!");
                    }

                    existe = true;
                    break;
                }
            }

            if (!existe) {

                JOptionPane.showMessageDialog(null,
                        "Produto não encontrado!");
            }

            novaEntrada = JOptionPane.showInputDialog(
                    "DESEJA FAZER UMA NOVA ENTRADA? (S/N)"
            );

        } while (novaEntrada != null
                && novaEntrada.equalsIgnoreCase("S"));
    }

    public void saida() {

        if (total == 0) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum produto cadastrado!\n"
                    + "Cadastre produtos antes de registrar saídas.");
            return;
        }

        String novaSaida;

        do {

            String nome = JOptionPane.showInputDialog(
                    "SAÍDA DE PRODUTO\n\n"
                    + "DIGITE O NOME DO PRODUTO:"
            );

            if (nome == null) {
                break;
            }

            boolean existe = false;

            for (int i = 0; i < total; i++) {

                if (produtos[i].nome.equalsIgnoreCase(nome.trim())) {

                    Produto produto = produtos[i];

                    JOptionPane.showMessageDialog(null,
                            "PRODUTO ENCONTRADO\n\n"
                            + "Produto: " + produto.nome + "\n"
                            + "Preço: R$ " + String.format("%.2f", produto.preco) + "\n"
                            + "Unidade: " + produto.unidade + "\n"
                            + "Quantidade Atual: " + produto.quantidade
                    );

                    int quantidadeSaida;

                    // VALIDA A QUANTIDADE
                    while (true) {
                        String entradaQtd = JOptionPane.showInputDialog("QUANTIDADE DE SAÍDA:");

                        int qtdValidada = parseEValidarQuantidade(entradaQtd);

                        if (qtdValidada > 0) {
                            quantidadeSaida = qtdValidada;
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "QUANTIDADE INVÁLIDA!\n"
                                    + "Por favor, introduza um número inteiro maior que zero.",
                                    "Erro de Validação",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    // VERIFICA ESTOQUE
                    if (quantidadeSaida > produto.quantidade) {

                        JOptionPane.showMessageDialog(null,
                                "ESTOQUE INSUFICIENTE!\n\n"
                                + "Quantidade disponível: "
                                + produto.quantidade
                        );

                    } else {

                        String confirma = JOptionPane.showInputDialog(
                                "CONFIRMA SAÍDA?\n\n"
                                + "Produto             : " + produto.nome + "\n"
                                + "Quantidade retirada : " + quantidadeSaida + "\n"
                                + "Estoque atual       : " + produto.quantidade + "\n"
                                + "Estoque após saída  : "
                                + (produto.quantidade - quantidadeSaida)
                                + "\n\n(S/N)"
                        );

                        if (confirma != null && confirma.equalsIgnoreCase("S")) {

                            produto.quantidade -= quantidadeSaida;

                            JOptionPane.showMessageDialog(null,
                                    "SAÍDA REALIZADA COM SUCESSO!\n\n"
                                    + "Novo estoque: "
                                    + produto.quantidade
                            );

                        } else {

                            JOptionPane.showMessageDialog(null,
                                    "SAÍDA CANCELADA!");
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

            novaSaida = JOptionPane.showInputDialog(
                    "DESEJA FAZER UMA NOVA SAÍDA? (S/N)"
            );

        } while (novaSaida != null
                && novaSaida.equalsIgnoreCase("S"));
    }

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
