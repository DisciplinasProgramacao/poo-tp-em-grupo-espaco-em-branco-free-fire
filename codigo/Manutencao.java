package codigo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Manutencao {
    private final int id;
    private LocalDate data;
    private EtipoManutencao tipo;
    private double valor;

    private static int idCount = 0;

    /**
     * Construtor da classe Manutencao que recebe como parâmetro a data e o tipo de manutenção.
     * @param data
     * @param tipo
     */
    public Manutencao(LocalDate data, String tipo) {
        this.id = idCount++;
        this.data = data;
        this.tipo = EtipoManutencao.valueOf(tipo.toUpperCase());
        this.valor = 0;
    }

    /**
     * Função  que permite adicionar o valor da manutencao.
     */
    public void addValorManutencao(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + id + " Data: " + data.format(formatter) + " Tipo: " + tipo + " Valor: " + valor;
    }
}
