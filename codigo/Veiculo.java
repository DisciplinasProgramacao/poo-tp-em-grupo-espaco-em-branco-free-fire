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
    private LinkedList<Manutencao> manutencoes = new LinkedList<>();
    private double despesa;

    Veiculo(String placa, ETipoVeiculo tipoVeiculo, EtipoCombustivel combustivelDoVeiculo) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.tanqueDoVeiculo = new Tanque(tipoVeiculo, combustivelDoVeiculo);
        this.mapaDeRotas = new HashMap<>();
        this.despesa = 0;
    }

    public boolean addRota(Rota rota) {
        if (autonomiaMaxima() >= rota.getQuilometragem()) {
            if (verificarCapacidadeDeRota(rota.getData())) {
                mapaDeRotas.put(rota.getData(), rota);
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

    public double kmNoMes(LocalDate dataConsulta) {
        double somaDosKmNoMes = mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == dataConsulta.getMonth()
                        && e.getKey().getYear() == dataConsulta.getYear())
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

        if (autonomiaAtual() < rota.getQuilometragem() / tanqueDoVeiculo.getConsumo()) {
            abastecer(tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
            totalReabastecido += (tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
        }

        tanqueDoVeiculo.consumirCombustivel(rota.getQuilometragem() / tanqueDoVeiculo.getConsumo());
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
                .filter(e -> e.getKey().getMonth() == dataDaRota.getMonth()
                        && e.getKey().getYear() == dataDaRota.getYear())
                .count();

        if (cont < MAX_ROTAS)
            return true;

        return false;
    }

    public double despesasDoVeiculo() {

        return 0.0;

    }

    public String relatorioRotas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tRelatorio do " + ETipoVeiculo.values() + " de placa: " + placa + "\n");
        sb.append("Lista de rotas executadas pelo veiculo\n");
        mapaDeRotas.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> sb.append(e.toString() + "\n"));

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
