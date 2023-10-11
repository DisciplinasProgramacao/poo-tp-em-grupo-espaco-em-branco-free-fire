package codigo;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private final double CONSUMO = 8.2;
    private String placa;
    private Rota rotas[];
    private int quantRotas;
    private double tanqueAtual;
    private double tanqueMax;
    private double totalReabastecido;

    public boolean addRota(Rota rota) {
        return false;
    }

    private double autonomiaMaxima() {
        return 0;
    } 

    private double autonomiaAtual () {
        return 0;
    }

    public double abastecer(double litros) {
        return 0;
    } 

    public double kmNoMes() {
        return 0;
    }

    public double kmTotal() {
        return 0;
    }

    private void percorrerRota(Rota rota) {

    }
}
