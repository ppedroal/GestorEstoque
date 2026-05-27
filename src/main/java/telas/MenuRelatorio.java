package telas;

import modelo.Produto;
import javax.swing.*;

public class MenuRelatorio {

    public Produto[] produtos = new Produto[100];
    public int total = 0;

    public MenuRelatorio(Produto[] produtos, int total) {
        this.produtos = produtos;
        this.total = total;
    }

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

    // SUB ROTINAS
    private void listaDePrecos() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder lista = new StringBuilder();
        lista.append("RELATÓRIO: LISTA DE PREÇOS\n\n");
        lista.append(String.format("%-4s %-20s %-10s %12s%n", "Nº", "PRODUTO", "UNIDADE", "PREÇO"));
        lista.append("─".repeat(50)).append("\n");

        for (int i = 0; i < total; i++) {
            lista.append(String.format("%-4d %-20s %-10s %12s%n",
                    i + 1, ordenados[i].nome, ordenados[i].unidade,
                    String.format("R$ %.2f", ordenados[i].preco)));
        }

        lista.append("─".repeat(50)).append("\n");
        lista.append("Total de produtos: ").append(total);

        JTextArea area = new JTextArea(lista.toString());
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }

    private void balancoFisico() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO: BALANÇO FÍSICO\n\n");
        relatorio.append(String.format("%-4s %-20s %s%n", "Nº", "PRODUTO", "QUANTIDADE"));
        relatorio.append("─".repeat(44)).append("\n");

        for (int i = 0; i < total; i++) {
            relatorio.append(String.format("%-4d %-20s %s%n",
                    i + 1, ordenados[i].nome,
                    formatarUnidade(ordenados[i].unidade, ordenados[i].quantidade)));
        }

        relatorio.append("─".repeat(44)).append("\n");
        relatorio.append("Total de produtos: ").append(total);

        JTextArea area = new JTextArea(relatorio.toString());
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }

    private void balancoFinanceiro() {
        Produto[] ordenados = ordenarAlfabeticamente();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("RELATÓRIO: BALANÇO FINANCEIRO\n\n");
        relatorio.append(String.format("%-4s %-20s %12s %6s %14s%n",
                "Nº", "PRODUTO", "PREÇO UNIT.", "QTD", "VALOR TOTAL"));
        relatorio.append("─".repeat(60)).append("\n");

        double totalGeral = 0;

        for (int i = 0; i < total; i++) {
            double valorTotal = ordenados[i].preco * ordenados[i].quantidade;
            totalGeral += valorTotal;
            relatorio.append(String.format("%-4d %-20s %12s %6d %14s%n",
                    i + 1, ordenados[i].nome,
                    String.format("R$ %.2f", ordenados[i].preco),
                    ordenados[i].quantidade,
                    String.format("R$ %.2f", valorTotal)));
        }

        relatorio.append("─".repeat(60)).append("\n");
        relatorio.append(String.format("%44s %14s%n", "TOTAL:", String.format("R$ %.2f", totalGeral)));
        relatorio.append("\nTotal de produtos: ").append(total);

        JTextArea area = new JTextArea(relatorio.toString());
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area);
    }

    // Retorna uma cópia do vetor de produtos ordenada alfabeticamente pelo nome.
    // Não altera o vetor original — a ordem de cadastro é preservada no sistema.
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
