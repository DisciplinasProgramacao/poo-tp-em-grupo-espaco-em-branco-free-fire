package codigo;

import java.util.LinkedList;
import java.util.stream.IntStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private final double CONSUMO = 8.2;
    private String placa;
    private Map<LocalDate, Rota> mapaDeRotas;
    private int quantRotas;
    private Tanque tanqueDoVeiculo;
    private double totalReabastecido;
    private EnumTipoVeiculo tipoVeiculo;
    private LinkedList<Manutencao> manutencoes = new LinkedList<>();

    Veiculo(String placa, EnumTipoVeiculo tipoVeiculo, Tanque tanqueDoVeiculo) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.tanqueDoVeiculo = tanqueDoVeiculo;
    }

    /**
     * Função que adiciona uma rota em nosso vetor de rotas
     * 
     * @param rota que deverá ser avaliada para colocar em nosso vetor de rotas
     * @return boolean que servirá como guia se a rota foi ou não adicionada
     */

    public boolean addRota(Rota rota) {
        if (autonomiaMaxima() >= rota.getQuilometragem()) {
            LocalDate dataDaRota = rota.getData();
            if (verificarCapacidadeDeRota(dataDaRota))
                mapaDeRotas.put(dataDaRota, rota);

            percorrerRota(rota.getQuilometragem());
            return true;

        }

        return false;
    }

    /**
     * Realiza a consulta no objeto tanque em busca da autonomia maxima
     * 
     * @return a resposta da consulta
     * 
     */
    private double autonomiaMaxima() {
        return tanqueDoVeiculo.autonomiaMaxima();
    }

    /**
     * Realiza a consulta no objeto tanque em busca da autonomia atual
     * 
     * @return a resposta da consulta
     * 
     */
    private double autonomiaAtual() {
        return tanqueDoVeiculo.autonomiaAtual();
    }

    /**
     * 
     * @param litros a quantidade de litros que devem ser enviadas para o tanque
     *               abastecer
     * @return o valor abastecido
     */
    public double abastecer(double litros) {
        return tanqueDoVeiculo.abastecer(litros);
    }

    /**
     * A função realiza uma pesquisa no vetor rotas e calcula a quantidade maxima de
     * km rodados no mês
     * 
     * @return a quantidade de km rodados no mês
     */
    public double kmNoMes(Month mes) {
        double somaDosKmNoMes = mapaDeRotas.entrySet().stream()
                .filter(e -> e.getKey().getMonth() == mes)
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmNoMes;
    }

    /**
     * A função realiza um calculo em cima do totalReabastecido * CONSUMO e o
     * retorno dessa conta é o km total rodado pelo carro
     * 
     * @return o resultado do calculo
     */
    public double kmTotal() {
        double somaDosKmTotal = mapaDeRotas.entrySet().stream()
                .mapToDouble(e -> e.getValue().getQuilometragem())
                .sum();
        return somaDosKmTotal;
    }

    /**
     * A função verifica se a autonomia atual do carro consegue realizar a rota
     * desginada a ser percorrida
     * caso a autonomia atual não conseguir completar a rota ele chama a função de
     * abastecer passando um calculo que o resultado é a quantidade de litros a ser
     * reabastecido
     * 
     * 
     * @param rota que deverá ser percorrida
     * 
     */
    private void percorrerRota(double quilometragemDaRota) {
        if (verificarManutencaoPeriodica(quilometragemDaRota))
            ;
        // MANDAR PRA MANUTENÇÃO PERIODICA

        if (verificarManutencaoPorPeca(quilometragemDaRota))
            // MANDAR PRA MANUTENÃO POR PEÇA
            ;

        if (autonomiaAtual() < quilometragemDaRota / CONSUMO) {
            abastecer(tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
            totalReabastecido += (tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
        }

        tanqueDoVeiculo.consumirCombustivel(quilometragemDaRota / CONSUMO);
    }

    public boolean verificarManutencaoPeriodica(double quilometragemDaRota) {
        int count = (int) IntStream.range(0, manutencoes.size())
                .filter(e -> e % 2 == 0)
                .count();
        if (kmTotal() + quilometragemDaRota > tipoVeiculo.getManuPeriodica()
                * Math.max(count, 1))
            return false;

        return true;
    }

    public boolean verificarManutencaoPorPeca(double quilometragemDaRota) {
        int count = (int) IntStream.range(0, manutencoes.size())
                .filter(e -> e % 2 != 0)
                .count();
        if (kmTotal() + quilometragemDaRota > tipoVeiculo.getManuPreventiva()
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

    public int getMAX_ROTAS() {
        return MAX_ROTAS;
    }

    public double getCONSUMO() {
        return CONSUMO;
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
