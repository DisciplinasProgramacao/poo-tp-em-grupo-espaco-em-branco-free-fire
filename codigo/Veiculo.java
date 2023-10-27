package codigo;

public class Veiculo {
    private final int MAX_ROTAS = 30;
    private final double CONSUMO = 8.2;
    private String placa;
    private Rota rotas[];
    private int quantRotas;
    private Tanque tanqueAtual;
    private Tanque tanqueMax;
    private double totalReabastecido;

    public boolean addRota(Rota rota) {
        if (rotas[0] != null) {
            // if verificar se o mes da rotas[0] == ao mês do rota.getData
            // apenas deixar seguir o processo
            // else if se a data for diferente
            /*
             * for(int i = 0; rotas.length; i++){
             * rotas[i] = null;
             * quantRotas = 0;
             * 
             * }
             */
        }

        if (autonomiaMaxima() >= rota.getQuilometragem()) {
            rotas[quantRotas] = rota;
            quantRotas++;
            percorrerRota(rota);
            return true;
        }

        return false;
    }

    private double autonomiaMaxima() {
        return tanqueMax.autonomiaMaxima();
    }

    private double autonomiaAtual() {
        return tanqueAtual.autonomiaAtual();
    }

    public double abastecer(double litros) {
        return tanqueAtual.abastecer(litros);
    }

    public double kmNoMes() {
        double kmMes = 0;
        for (Rota rota : rotas)
            kmMes += rota.getQuilometragem();
        return kmMes;
    }

    public double kmTotal() {
        double kmTotal = totalReabastecido * CONSUMO;
        return kmTotal;
    }

    private void percorrerRota(Rota rota) {
        if (autonomiaAtual() < rota.getQuilometragem()) {
            abastecer(tanqueMax.autonomiaMaxima() - tanqueAtual.autonomiaAtual());
            totalReabastecido += (tanqueMax.autonomiaMaxima() - tanqueAtual.autonomiaAtual());
        }

        tanqueAtual -= (rota.getQuilometragem() / CONSUMO);
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
        return tanqueAtual;
    }

    public Tanque getTanqueMax() {
        return tanqueMax;
    }

    public double getTotalReabastecido() {
        return totalReabastecido;
    }
}
