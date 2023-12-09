package codigo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rota {

    private double quilometragem;
    private LocalDate data;

    
    public Rota(double quilometragem, LocalDate data) {
        this.quilometragem = quilometragem;
        this.data = data;
    }

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
