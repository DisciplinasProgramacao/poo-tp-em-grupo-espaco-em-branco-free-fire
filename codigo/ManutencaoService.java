package codigo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ManutencaoService {
    private final int VALOR_KM_MANUTENCAO_PREVENTIVA;
    private final int VALOR_KM_MANUTENCAO_TROCA_PECA;
    private List<Manutencao> manutencoes;
    private int multiplicadorManutencoesPreventiva;
    private int multiplicadorManutencoesTrocaPeca;
    private double despesaTotal;

    /**
     * Construtor da classe ManutencaoService que recebe como parâmetro a
     * quilometragem da manutenção preventiva e da troca de peça.
     * 
     * @param kmManutencaoPreventiva valor constante de quando deve ser realizada a manutenção preventiva
     * @param kmManutencaoTrocaPeca valor constante de quando deve ser realizada a manutenção de troca de peça
     */
    public ManutencaoService(int kmManutencaoPreventiva, int kmManutencaoTrocaPeca) {
        this.VALOR_KM_MANUTENCAO_PREVENTIVA = kmManutencaoPreventiva;
        this.VALOR_KM_MANUTENCAO_TROCA_PECA = kmManutencaoTrocaPeca;
        manutencoes = new LinkedList<>();
        multiplicadorManutencoesPreventiva = 1;
        multiplicadorManutencoesTrocaPeca = 1;
        despesaTotal = 0;
    }

    /**
     * Função que verifica a necessidade de realizar manutenção. Caso necessário,
     * adiciona uma manutenção na lista de manutenções.
     * 
     * @param kmTotal quilometragem total do veículo
     */
    public void verifica(double kmTotal) {
        int proxPreventiva = multiplicadorManutencoesPreventiva * VALOR_KM_MANUTENCAO_PREVENTIVA;
        int proxTrocaPeca = multiplicadorManutencoesTrocaPeca * VALOR_KM_MANUTENCAO_TROCA_PECA;

        if (kmTotal >= proxPreventiva) {
            multiplicadorManutencoesPreventiva++;
            aplicarManutencao("PREVENTIVA");
        }

        if (kmTotal >= proxTrocaPeca) {
            multiplicadorManutencoesTrocaPeca++;
            aplicarManutencao("TROCAPECA");
        }

    }

    /**
     * Função que adiciona a manutenção na lista de manutenções.
     * @param tipo da manutenção
     */
    private void aplicarManutencao(String tipo) {
        manutencoes.add(new Manutencao(LocalDate.now(), tipo));
    }

    /**
     * Função que adiciona um valor de manutenção, aguarda o ID da manutenção, e o
     * valor.
     * @param id da manutenção
     * @param valor da manutenção
     */
    public void addValorManutencao(int id, double valor) {
        boolean manutencaoEncontrada = false;

        for (Manutencao manutencao : manutencoes) {
            if (manutencao.getId() == id) {
                if (manutencao.addValorManutencao(valor))
                    despesaTotal += valor;
                manutencaoEncontrada = true;
            }
        }

        if (!manutencaoEncontrada) {
            throw new IllegalArgumentException("ID de manutenção inválido");
        }
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Manutencao manutencao : manutencoes) {
            sb.append(manutencao).append("\n");
        }
        return sb.toString();
    }

}
