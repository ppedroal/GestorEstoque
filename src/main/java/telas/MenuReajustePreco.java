package telas;

import modelo.Produto;
import javax.swing.JOptionPane;

public class MenuReajustePreco {

    // ─── ATRIBUTOS ────────────────────────────────────────────────────────────
    private Produto[] produtos;
    private int total;

    // ─── CONSTRUTOR ───────────────────────────────────────────────────────────
    /**
     * Recebe os dados do MenuProduto para trabalhar no mesmo array,
     * evitando dados duplicados ou desatualizados.
     */
    public MenuReajustePreco(Produto[] produtos, int total) {
        this.produtos = produtos;
        this.total = total;
    }

    // ─── MENU PRINCIPAL ───────────────────────────────────────────────────────
    /**
     * Exibe o menu principal de reajuste em loop até o usuário escolher Retornar.
     */
    public void menu() {

        if (nenhumProdutoCadastrado()) {
            return;
        }

        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "MENU REAJUSTE DE PREÇOS\n\n"
                    + "1 - Reajustar TODOS os produtos\n"
                    + "2 - Reajustar produto INDIVIDUAL\n"
                    + "3 - Consultar preços atuais\n"
                    + "4 - Retornar\n\n"
                    + "Opção: "
            );

            if (opcao == null) {
                return;
            }

            switch (opcao.trim()) {
                case "1" ->
                    reajustarTodos();
                case "2" ->
                    reajustarIndividual();
                case "3" ->
                    consultarPrecos();
                case "4" -> {
                    return;
                }
                default ->
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }

        } while (true);
    }

    // ─── SUBROTINA: REAJUSTAR TODOS ───────────────────────────────────────────
    /**
     * Aplica um mesmo percentual de reajuste em TODOS os produtos cadastrados.
     * Exibe uma prévia completa antes de confirmar para evitar erros.
     */
    private void reajustarTodos() {

        double percentual = lerPercentual("REAJUSTE GERAL\n\nPercentual de reajuste (%):");

        // Double.MIN_VALUE é o valor sentinela que lerPercentual() devolve quando o usuário cancela.
        if (percentual == Double.MIN_VALUE) {
            return;
        }

        StringBuilder previa = new StringBuilder();
        previa.append(String.format("PRÉVIA DO REAJUSTE (%.1f%%)\n\n", percentual));
        previa.append(String.format("%-20s %10s  →  %10s%n", "PRODUTO", "ATUAL", "NOVO"));
        previa.append("─".repeat(46)).append("\n");

        for (int i = 0; i < total; i++) {
            double novoPreco = calcularNovoPreco(produtos[i].preco, percentual);
            previa.append(String.format("%-20s R$%8.2f  →  R$%8.2f%n",
                    produtos[i].nome, produtos[i].preco, novoPreco));
        }

        String confirma = JOptionPane.showInputDialog(previa + "\nCONFIRMA REAJUSTE? (S/N)");

        if (confirma != null && confirma.equalsIgnoreCase("S")) {
            for (int i = 0; i < total; i++) {
                produtos[i].preco = calcularNovoPreco(produtos[i].preco, percentual);
            }
            JOptionPane.showMessageDialog(null,
                    String.format("Reajuste de %.1f%% aplicado em %d produto(s)!", percentual, total));
        } else {
            JOptionPane.showMessageDialog(null, "Reajuste cancelado!");
        }
    }

    // ─── SUBROTINA: REAJUSTAR INDIVIDUAL ─────────────────────────────────────
    /**
     * Permite reajustar o preço de um produto específico buscado pelo nome.
     * Repete o processo até o usuário optar por não reajustar outro produto.
     */
    private void reajustarIndividual() {
        String novaAlteracao;

        do {
            String nome = JOptionPane.showInputDialog(
                    "REAJUSTE INDIVIDUAL\n\nDigite o nome do produto: ");

            if (nome == null) {
                return;
            }

            int idx = buscarProduto(nome.trim());

            if (idx == -1) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            } else {
                Produto p = produtos[idx];

                exibirDadosProduto(p);

                double percentual = lerPercentual(
                        "Percentual de reajuste para\n'" + p.nome + "' (%):");

                if (percentual == Double.MIN_VALUE) {
                    novaAlteracao = JOptionPane.showInputDialog(
                            "DESEJA REAJUSTAR OUTRO PRODUTO? (S/N)");
                    continue;
                }

                double novoPreco = calcularNovoPreco(p.preco, percentual);

                String confirma = JOptionPane.showInputDialog(
                        "Preço atual: R$ " + String.format("%.2f", p.preco) + "\n"
                        + "Novo preço:  R$ " + String.format("%.2f", novoPreco) + "\n\n"
                        + "CONFIRMA REAJUSTE? (S/N)");

                if (confirma != null && confirma.equalsIgnoreCase("S")) {
                    p.preco = novoPreco;
                    JOptionPane.showMessageDialog(null,
                            "Preço de '" + p.nome + "' atualizado para R$ "
                            + String.format("%.2f", p.preco) + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Reajuste cancelado!");
                }
            }

            novaAlteracao = JOptionPane.showInputDialog("DESEJA REAJUSTAR OUTRO PRODUTO? (S/N)");

        } while (novaAlteracao != null && novaAlteracao.equalsIgnoreCase("S"));
    }

    // ─── SUBROTINA: CONSULTAR PREÇOS ──────────────────────────────────────────
    /**
     * Exibe uma tabela com todos os produtos e seus preços atuais.
     */
    private void consultarPrecos() {
        StringBuilder lista = new StringBuilder();
        lista.append("LISTA DE PREÇOS ATUAL\n\n");
        lista.append(String.format("%-4s %-20s %10s%n", "Nº", "PRODUTO", "PREÇO"));
        lista.append("─".repeat(38)).append("\n");

        for (int i = 0; i < total; i++) {
            lista.append(String.format("%-4d %-20s R$%7.2f%n",
                    i + 1, produtos[i].nome, produtos[i].preco));
            //  i+1 = número visual (começa em 1, não em 0)
            //  %-20s = texto alinhado à esquerda com 20 caracteres de largura
            //  %7.2f = número com 7 dígitos, 2 decimais, alinhado à direita
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    // ─── SUBROTINAS AUXILIARES ────────────────────────────────────────────────
    /**
     * Verifica se não há produtos cadastrados. Retorna true se estiver vazio
     * (e já exibe o aviso), false se houver produtos.
     */
    private boolean nenhumProdutoCadastrado() {
        if (total == 0) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum produto cadastrado!\n"
                    + "Cadastre produtos antes de reajustar.");
            return true;
        }
        return false;
    }

    /**
     * Busca um produto pelo nome (sem diferenciar maiúsculas/minúsculas).
     * Retorna o índice do produto no array, ou -1 se não encontrar.
     */
    private int buscarProduto(String nome) {
        for (int i = 0; i < total; i++) {
            if (produtos[i].nome.equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Exibe os dados atuais de um produto em uma janela de informação.
     */
    private void exibirDadosProduto(Produto p) {
        JOptionPane.showMessageDialog(null,
                "PRODUTO ENCONTRADO\n\n"
                + "Nome:        " + p.nome + "\n"
                + "Preço atual: R$ " + String.format("%.2f", p.preco) + "\n"
                + "Unidade:     " + p.unidade + "\n"
                + "Quantidade:  " + p.quantidade);
    }

    /**
     * Lê e valida o percentual de reajuste digitado pelo usuário.
     * Retorna Double.MIN_VALUE como sinal de cancelamento (usuário fechou a janela).
     */
    private double lerPercentual(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(
                    mensagem + "\n(Use valor negativo para redução de preço)");

            if (entrada == null) {
                return Double.MIN_VALUE; // Valor sentinela: sinaliza "cancelado" para quem chamou
            }

            try {
                double valor = Double.parseDouble(entrada.replace(",", ".").trim());

                if (valor == 0) {
                    JOptionPane.showMessageDialog(null, "O percentual não pode ser zero!");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido! Ex: 10 ou -5");
            }
        }
    }

    /**
     * Calcula o novo preço aplicando o percentual de reajuste.
     * Fórmula: precoAtual × (1 + percentual ÷ 100)
     */
    private double calcularNovoPreco(double precoAtual, double percentual) {
        return precoAtual * (1 + percentual / 100.0);
        // 100.0 (e não 100) garante divisão decimal — em Java, 10/100 = 0, mas 10/100.0 = 0.1
    }
}
