package tela;

import modelo.Produto;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Gerencia o menu de relatórios.
 *
 * @author Pedro Leite
 * @version 1.0
 */
public class MenuRelatorio {

    /** Construtor padrão */
    public MenuRelatorio() {}

    /** Array de produtos */
    public Produto[] produtos = new Produto[100];

    /** Total de produtos cadastrados */
    public int total = 0;

    /**
     * Construtor com parâmetros.
     *
     * @param produtos array de produtos
     * @param total quantidade de produtos
     */
    public MenuRelatorio(Produto[] produtos, int total) {
        this.produtos = produtos;
        this.total = total;
    }

    /**
     * Exibe o menu principal de relatórios.
     */
    public void menu() {

        if (total == 0) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum produto cadastrado!\n"
                    + "Cadastre produtos antes de gerar relatórios.");
            return;
        }

        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(
                    "MENU RELATÓRIOS\n\n"
                    + "1 - Lista de Preços\n"
                    + "2 - Balanço Físico\n"
                    + "3 - Balanço Financeiro\n"
                    + "4 - Retornar\n\n"
                    + "Opção: "
            );

            if (opcao == null) {
                return;
            }

            switch (opcao.trim()) {

                case "1" ->
                    listaDePrecos();
                case "2" ->
                    balancoFisico();
                case "3" ->
                    balancoFinanceiro();
                case "4" -> {
                    return;
                }
                default ->
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }

        } while (true);
    }

    /**
     * Gerar lista de preços.
     */
    private void listaDePrecos() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder lista = new StringBuilder();
        lista.append(cabecalho("\nLISTA DE PREÇOS"));
        lista.append(String.format("%-35s %-6s %12s%n", "PRODUTO", "UND", "PREÇO"));
        lista.append("─".repeat(56)).append("\n");

        for (int i = 0; i < total; i++) {
            lista.append(String.format("%-35s %-6s %12s%n",
                    ordenados[i].nome,
                    ordenados[i].unidade,
                    String.format("%,.2f", ordenados[i].preco).replace(",", ".")));
        }

        exibir(lista.toString());
    }

    /**
     * Gerar balanço físico.
     */
    private void balancoFisico() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append(cabecalho("\nBALANÇO FÍSICO"));
        relatorio.append(String.format("%-35s %-6s %6s%n", "PRODUTO", "UND", "QTDE"));
        relatorio.append("─".repeat(50)).append("\n");

        int totalItens = 0;
        for (int i = 0; i < total; i++) {
            relatorio.append(String.format("%-35s %-6s %6d%n",
                    ordenados[i].nome,
                    ordenados[i].unidade,
                    ordenados[i].quantidade));
            totalItens += ordenados[i].quantidade;
        }

        relatorio.append("─".repeat(50)).append("\n");
        relatorio.append(String.format("TOTAL DE PRODUTOS NO ESTOQUE : %d%n", totalItens));

        exibir(relatorio.toString());
    }

    /**
     * Gerar balanço financeiro.
     */
    private void balancoFinanceiro() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append(cabecalho("\nBALANÇO FINANCEIRO"));
        relatorio.append(String.format("%-20s %-6s %14s %6s %14s%n",
                "PRODUTO", "UND", "PREÇO UNITÁRIO", "QTDE", "PREÇO TOTAL"));
        relatorio.append("─".repeat(64)).append("\n");

        double totalGeral = 0;
        for (int i = 0; i < total; i++) {
            double valorTotal = ordenados[i].preco * ordenados[i].quantidade;
            totalGeral += valorTotal;
            relatorio.append(String.format("%-20s %-6s %14s %6d %14s%n",
                    ordenados[i].nome,
                    ordenados[i].unidade,
                    String.format("R$ %.2f", ordenados[i].preco),
                    ordenados[i].quantidade,
                    String.format("R$ %.2f", valorTotal)));
        }

        relatorio.append("─".repeat(60)).append("\n");
        relatorio.append(String.format("VALOR TOTAL DO ESTOQUE    : %s%n",
                String.format("R$ %,.2f", totalGeral)));

        exibir(relatorio.toString());
    }

    /**
     * Ordenar produtos alfabeticamente.
     *
     * @return array ordenado de produtos
     */
    private Produto[] ordenarAlfabeticamente() {
        Produto[] ordenados = new Produto[total];

        for (int i = 0; i < total; i++) {
            ordenados[i] = produtos[i];
        }

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                if (ordenados[j].nome.compareToIgnoreCase(ordenados[j + 1].nome) > 0) {
                    Produto temp = ordenados[j];
                    ordenados[j] = ordenados[j + 1];
                    ordenados[j + 1] = temp;
                }
            }
        }

        return ordenados;
    }

    /**
     * Gerar cabeçalho do relatório.
     *
     * @param titulo título do relatório
     * @return cabeçalho formatado
     */
    private String cabecalho(String titulo) {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "SISTEMA DE CONTROLE DE ESTOQUE - "
                + String.format("%-15s %s%n%n", data, titulo);
    }

    /**
     * Exibir relatório em janela.
     *
     * @param conteudo conteúdo a exibir
     */
    private void exibir(String conteudo) {
        JTextArea area = new JTextArea(conteudo);
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }


    /**
     * Formatar unidade de medida.
     *
     * @param unidade código da unidade
     * @param quantidade valor da quantidade
     * @return unidade formatada
     */
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
}
