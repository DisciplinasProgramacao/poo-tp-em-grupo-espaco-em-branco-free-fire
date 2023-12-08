package codigo;

import java.util.stream.IntStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private String placa;
    private Map<LocalDate, Rota> mapaDeRotas;
    private int quantRotas;
    private Tanque tanqueDoVeiculo;
    private double totalReabastecido;
    private ETipoVeiculo tipoVeiculo;
    private LinkedList<Manutencao> manutencoes = new LinkedList<>();

    Veiculo(String placa, ETipoVeiculo tipoVeiculo, Tanque tanqueDoVeiculo) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.tanqueDoVeiculo = tanqueDoVeiculo;
    }

    public boolean addRota(Rota rota) {
        if (autonomiaMaxima() >= rota.getQuilometragem()) {
            LocalDate dataDaRota = rota.getData();
            if (verificarCapacidadeDeRota(dataDaRota)) {
                mapaDeRotas.put(dataDaRota, rota);
                percorrerRota(rota);
                return true;
            }
        }

        return false;
    }

    private double autonomiaMaxima() {
        return tanqueDoVeiculo.autonomiaMaxima();
    }

    private double autonomiaAtual() {
        return tanqueDoVeiculo.autonomiaAtual();
    }

    public double abastecer(double litros) {
        return tanqueDoVeiculo.abastecer(litros);
    }

    public double kmNoMes(Month mes) {
        double somaDosKmNoMes = mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == mes)
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmNoMes;
    }

    public double kmTotal() {
        double somaDosKmTotal = mapaDeRotas.entrySet().stream()
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmTotal;
    }

    private void percorrerRota(Rota rota) {
        if (verificarManutencaoPreventiva(rota.getQuilometragem()))
            manutencoes.add(new Manutencao(EtipoManutencao.PREVENTIVA, rota.getData()));

        if (verificarManutencaoPorPeca(rota.getQuilometragem()))
            manutencoes.add(new Manutencao(EtipoManutencao.TROCAPECA, rota.getData()));

        if (autonomiaAtual() < rota.getQuilometragem() / CONSUMO) {
            abastecer(tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
            totalReabastecido += (tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
        }

        tanqueDoVeiculo.consumirCombustivel(rota.getQuilometragem() / CONSUMO);
    }

    public boolean verificarManutencaoPreventiva(double quilometragemDaRota) {
        int count = (int) IntStream.range(0, manutencoes.size())
                .filter(e -> e % 2 == 0)
                .count();
        if (kmTotal() + quilometragemDaRota >= tipoVeiculo.getManuPreventiva()
                * Math.max(count, 1))
            return false;

        return true;
    }

    public boolean verificarManutencaoPorPeca(double quilometragemDaRota) {
        int count = (int) IntStream.range(0, manutencoes.size())
                .filter(e -> e % 2 != 0)
                .count();
        if (kmTotal() + quilometragemDaRota > tipoVeiculo.getManuTrocaDePeca()
                * Math.max(count, 1))
            return false;

        return true;
    }

    public boolean verificarCapacidadeDeRota(LocalDate dataDaRota) {
        int cont = (int) mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == dataDaRota.getMonth())
                .count();

        if (cont < MAX_ROTAS)
            return true;

        return false;
    }

    public String relatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tRelatorio do " + ETipoVeiculo.values() + " de placa: " + placa + "\n");
        sb.append("Km total: " + kmTotal() + "\n");
        sb.append("Lista de rotas executadas pelo veiculo\n");
        mapaDeRotas.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> sb.append(
                        "Data: " + e.getKey() + " Quilometragem da Rota: " + e.getValue().getQuilometragem() + "\n"));

        return sb.toString();
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
