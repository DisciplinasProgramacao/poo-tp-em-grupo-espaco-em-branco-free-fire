package codigo;

import java.util.stream.IntStream;
import java.time.LocalDate;
import java.util.*;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private String placa;
    private Map<LocalDate, Rota> mapaDeRotas; // TODO: Deveria ser Rotas[] - pq se ele pode fazer 30 por mes, daria
                                              // pra
                                              // usar vetor. Além do mais, a verificação não precisaria contar quantas
                                              // já foram na lista, apenas puxaria o array referente a data e
                                              // verificaria o tamanho do array
    private int quantRotas;
    private Tanque tanqueDoVeiculo;
    private double totalReabastecido;
    private ETipoVeiculo tipoVeiculo;
    private ManutencaoService manutencaoService;
    private double despesa;

    Veiculo(String placa, String tipoVeiculo, String combustivelDoVeiculo) {
        this.placa = placa;
        this.tipoVeiculo = ETipoVeiculo.valueOf(tipoVeiculo.toUpperCase());
        this.tanqueDoVeiculo = new Tanque(this.tipoVeiculo,
                EtipoCombustivel.valueOf(combustivelDoVeiculo.toUpperCase()));
        this.mapaDeRotas = new HashMap<>();
        this.despesa = 0;
        this.manutencaoService = new ManutencaoService(this.tipoVeiculo.getManuPreventiva(),
                this.tipoVeiculo.getManuTrocaDePeca());
    }

    public boolean addRota(Rota rota) {
        if (tanqueDoVeiculo.autonomiaMaxima() >= rota.getQuilometragem()) {
            if (verificarCapacidadeDeRota(rota.getData())) {
                mapaDeRotas.put(rota.getData(), rota);
                percorrerRota(rota);
                return true;
            }
        }

        return false;
    }

    public double abastecerParaRota(double litros) {
        return tanqueDoVeiculo.abastecerParaRota(litros);
    }

    public double kmNoMes(LocalDate dataConsulta) {
        double somaDosKmNoMes = 0;
        somaDosKmNoMes = mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == dataConsulta.getMonth()
                        && e.getKey().getYear() == dataConsulta.getYear())
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmNoMes;
    }

    public double kmTotal() {
        double somaDosKmTotal = 0;
        somaDosKmTotal = mapaDeRotas.entrySet().stream()
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmTotal;
    }

    private void percorrerRota(Rota rota) {
        manutencaoService.verifica(kmTotal(), rota.getQuilometragem());

        if (!tanqueDoVeiculo.autonomiaParaRota(rota.getQuilometragem())) {
            double abastecer = tanqueDoVeiculo.litrosParaAbastecer(rota.getQuilometragem());
            totalReabastecido += abastecer;
            double valorDoAbastecimento = tanqueDoVeiculo.abastecerParaRota(abastecer);
            addDespesa(valorDoAbastecimento);
        }

        tanqueDoVeiculo.consumirCombustivel(rota.getQuilometragem());
    }

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

    public double despesasDoVeiculo() {
        return despesa;
    }

    private void addDespesa(double valor) {
        despesa += despesa;
    }

    public void addValorManutencao(int id, double valor) {
        addDespesa(valor);
        manutencaoService.addValorManutencao(id, valor);
    }

    public String relatorioRotas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tRelatorio do " + tipoVeiculo + " de placa: " + placa + "\n");
        sb.append("Lista de rotas executadas pelo veiculo\n");
        mapaDeRotas.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getValue())
                .forEach(e -> sb.append(e.toString() + "\n"));

        return sb.toString();
    }

    public String relatorioManutencoes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de manutenções realizadas no veículo de placa").append(placa).append("\n");
        sb.append(manutencaoService);
        return sb.toString();
    }

    public String toString() {
        return tipoVeiculo + " Placa: " + this.placa + " | Km total: " + kmTotal();

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
