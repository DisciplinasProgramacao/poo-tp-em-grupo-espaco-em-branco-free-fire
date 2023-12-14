package codigo;

import java.util.stream.IntStream;
import java.time.LocalDate;
import java.util.*;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private String placa;
    private Map<LocalDate, Rota> mapaDeRotas;
    private int quantRotas;
    private Tanque tanqueDoVeiculo;
    private double totalReabastecido;
    private ETipoVeiculo tipoVeiculo;
    private ManutencaoService manutencaoService;

    Veiculo(String placa, String tipoVeiculo, String combustivelDoVeiculo) {
        this.placa = placa;
        this.tipoVeiculo = ETipoVeiculo.valueOf(tipoVeiculo.toUpperCase());
        this.tanqueDoVeiculo = new Tanque(this.tipoVeiculo,
                EtipoCombustivel.valueOf(combustivelDoVeiculo.toUpperCase()));
        this.mapaDeRotas = new HashMap<>();
        this.totalReabastecido = 0;
        this.quantRotas = 0;
        this.manutencaoService = new ManutencaoService(this.tipoVeiculo.getManuPreventiva(),
                this.tipoVeiculo.getManuTrocaDePeca());
    }

    /**
     * Adiciona uma rota ao mapa de rotas se o veículo tiver autonomia suficiente
     * e a capacidade de rota para a data da rota estiver disponível.
     * 
     * @param rota A instância de Rota a ser adicionada ao mapa de rotas.
     * @return true se a rota foi adicionada com sucesso, false caso contrário.
     */
    public boolean addRota(Rota rota) {
        if (tanqueDoVeiculo.autonomiaMaxima() >= rota.getQuilometragem()) {
            if (verificarCapacidadeDeRota(rota.getData())) {
                mapaDeRotas.put(rota.getData(), rota);
                quantRotas++;
                percorrerRota(rota);
                return true;
            }
        }

        return false;
    }

    /**
     * Calcula a soma total de quilometragem percorrida no mês especificado com base
     * nas rotas registradas no mapa de rotas.
     *
     * @param dataConsulta A data do mês para o qual deseja-se calcular a
     *                     quilometragem.
     * @return A soma total de quilometragem percorrida no mês especificado.
     */
    public double kmNoMes(LocalDate dataConsulta) {
        double somaDosKmNoMes = 0;
        somaDosKmNoMes = mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == dataConsulta.getMonth()
                        && e.getKey().getYear() == dataConsulta.getYear())
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmNoMes;
    }

    /**
     * Calcula a soma total de quilometragem percorrida com base em todas as rotas
     * registradas no mapa de rotas.
     *
     * @return A soma total de quilometragem percorrida em todas as rotas.
     */
    public double kmTotal() {
        double somaDosKmTotal = 0;
        somaDosKmTotal = mapaDeRotas.entrySet().stream()
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmTotal;
    }

    /**
     * Executa as operações necessárias para percorrer uma rota, incluindo a
     * verificação
     * de manutenção com base na quilometragem total, abastecimento do tanque do
     * veículo
     * se necessário, e a atualização do consumo de combustível.
     *
     * @param rota A instância de Rota a ser percorrida.
     */

    private void percorrerRota(Rota rota) {
        manutencaoService.verifica(kmTotal());

        if (!tanqueDoVeiculo.autonomiaParaRota(rota.getQuilometragem())) {
            double abastecer = tanqueDoVeiculo.litrosParaAbastecer(rota.getQuilometragem());
            totalReabastecido += abastecer;
            tanqueDoVeiculo.abastecerParaRota(abastecer);

        }

        tanqueDoVeiculo.consumirCombustivel(rota.getQuilometragem());
    }

    /**
     * Verifica se ainda há capacidade disponível para adicionar rotas em um
     * determinado mês
     * com base no número máximo permitido de rotas (MAX_ROTAS).
     *
     * @param dataDaRota A data da rota para a qual a capacidade será verificada.
     * @return true se houver capacidade disponível, false caso contrário.
     */

    public boolean verificarCapacidadeDeRota(LocalDate dataDaRota) {
        int cont = (int) mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == dataDaRota.getMonth()
                        && e.getKey().getYear() == dataDaRota.getYear())
                .count();

        if (cont < MAX_ROTAS)
            return true;
        else
            return false;
    }

    /**
     * Retorna o total de despesas associadas ao veículo.
     *
     * @return O valor total das despesas do veículo.
     */
    public String despesasDoVeiculo() {
        StringBuilder sb = new StringBuilder();
        double totalDespesa = 0;
        totalDespesa = tanqueDoVeiculo.getDespesasDoTanque() + manutencaoService.getDespesaTotal();
        sb.append("Gastos do veiculo: de placa: ").append(placa).append("\n");
        sb.append("Despesas com combustivel: ").append(tanqueDoVeiculo.getDespesasDoTanque()).append("\n");
        sb.append("Despesa com manutenções: ").append(manutencaoService.getDespesaTotal()).append("\n");
        sb.append("Total: ").append(totalDespesa);
        return sb.toString();

    }

    /**
     * Adiciona um valor específico ao total de despesas associadas ao veículo.
     *
     * @param valor O valor a ser adicionado às despesas do veículo.
     */

    /**
     * Adiciona um valor específico às despesas totais do veículo e repassa a
     * informação
     * ao serviço de manutenção associado, identificado pelo ID.
     *
     * @param id    O identificador único do serviço de manutenção.
     * @param valor O valor a ser adicionado às despesas do veículo e ao serviço de
     *              manutenção.
     */
    public void addValorManutencao(int id, double valor) {

        manutencaoService.addValorManutencao(id, valor);
    }

    /**
     * Gera um relatório das rotas executadas pelo veículo, incluindo informações
     * sobre o tipo
     * do veículo, placa e a lista de rotas ordenadas por data.
     *
     * @return Uma string contendo o relatório das rotas do veículo.
     */
    public String relatorioRotas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tRelatorio: " + tipoVeiculo.name().toLowerCase() + " placa: " + placa + "\n");
        sb.append("Lista de rotas executadas pelo veiculo\n");
        mapaDeRotas.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getValue())
                .forEach(e -> sb.append(e.toString() + "\n"));

        return sb.toString();
    }

    /**
     * Gera um relatório das manutenções realizadas no veículo, incluindo
     * informações sobre a placa
     * do veículo e detalhes fornecidos pelo serviço de manutenção associado.
     *
     * @return Uma string contendo o relatório das manutenções do veículo.
     */
    public String relatorioManutencoes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de manutenções realizadas no veículo de placa: ").append(placa).append("\n");
        sb.append(manutencaoService);
        return sb.toString();
    }

    public String toString() {
        return tipoVeiculo.name().toLowerCase() + " Placa: " + this.placa + " | Km total: " + kmTotal();

    }

    public int getMAX_ROTAS() {
        return MAX_ROTAS;
    }

    public String getPlaca() {
        return placa;
    }

    public int getQuantRotas() {
        return quantRotas;
    }

    public Tanque getTanqueAtual() {
        return tanqueDoVeiculo;
    }

    public Tanque getTanqueMax() {
        return tanqueDoVeiculo;
    }

    public double getTotalReabastecido() {
        return totalReabastecido;
    }
}
