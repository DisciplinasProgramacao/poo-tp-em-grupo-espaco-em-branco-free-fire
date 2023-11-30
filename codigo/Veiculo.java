package codigo;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private final double CONSUMO = 8.2;
    private String placa;
    private Rota rotas[];
    private int quantRotas;
    private Tanque tanqueDoVeiculo;
    private double totalReabastecido;

    Veiculo(String placa) {
        this.placa = placa;
    }

    /**
     * Função que adiciona uma rota em nosso vetor de rotas
     * 
     * @param rota que deverá ser avaliada para colocar em nosso vetor de rotas
     * @return boolean que servirá como guia se a rota foi ou não adicionada
     */

    public boolean addRota(Rota rota) {

        if (autonomiaMaxima() >= rota.getQuilometragem()) {
            rotas[quantRotas] = rota;
            quantRotas++;
            percorrerRota(rota);
            if (rotas[0] != null) {
                if (rotas[0].getData() != rota.getData()) {
                    return false;
                } else if (rotas[0].getData() == rota.getData()) {
                    for (int i = 0; i < rotas.length; i++) {
                        rotas[i] = null;
                        quantRotas = 0;
                    }
                }
                return true;
            }
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
    public double kmNoMes() {
        double kmMes = 0;
        for (Rota rota : rotas)
            kmMes += rota.getQuilometragem();
        return kmMes;
    }

    /**
     * A função realiza um calculo em cima do totalReabastecido * CONSUMO e o
     * retorno dessa conta é o km total rodado pelo carro
     * 
     * @return o resultado do calculo
     */
    public double kmTotal() {
        double kmTotal = totalReabastecido * CONSUMO;
        return kmTotal;
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
    private void percorrerRota(Rota rota) {
        if (autonomiaAtual() < rota.getQuilometragem() / CONSUMO) {
            abastecer(tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
            totalReabastecido += (tanqueDoVeiculo.autonomiaMaxima() - tanqueDoVeiculo.autonomiaAtual());
        }

        tanqueDoVeiculo.desabastecer(rota.getQuilometragem() / CONSUMO);
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

    public Rota[] getRotas() {
        return rotas;
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
