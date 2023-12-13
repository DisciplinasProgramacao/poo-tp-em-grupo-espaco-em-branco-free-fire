package codigo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rota {

    private double quilometragem;
    private LocalDate data;

    /**
     * Função construtura da rota. Ela recebe a quilometragem e a data da rota.
     * @param quilometragem - Tamanho da rota em KM.
     * @param data - Data da rota.
     */
    public Rota(double quilometragem, LocalDate data) {
        this.quilometragem = quilometragem;
        this.data = data;
    }

    /**
     * Função retorna as informações da rota de maneira formatada.
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return " - "+data.format(formatter)+" | Quilometragem: "+quilometragem+"";
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public LocalDate getData() {
        return data;
    }
}
