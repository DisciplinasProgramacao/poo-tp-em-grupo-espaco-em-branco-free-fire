package codigo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Frota {
    private int tamanhoFrota;
    private Map<String, Veiculo> veiculos;

    /***
     * Função construtura da classe, ela recebe uma array de veiculos e determina o tamanho da frota pelo array da lista.
     * @param veiculos = Array de Veiculos
     */
    Frota(Map<String, Veiculo> veiculos) {
        tamanhoFrota+= veiculos.size();
        this.veiculos = veiculos;
    }

    /***
     * Função construtura da classe, ela não recebe parametros, inicializando o tamanho da frota com 0 e instanciando o HashMap.
     */
    Frota() {
        tamanhoFrota=0;
        veiculos = new HashMap<String,Veiculo>();
    }


    /***
     * Função de tipo boolean que retorna se foi possivel adicionar o veiculo, ou se o carro já foi adicionado.
     * @param placa = Placa do veiculo a ser adicionado
     * @param veiculo = Veiculo a ser adicionado
     * @return boolean true, se adicionou e false, caso não.
     */
    public boolean addVeiculo(String placa, Veiculo veiculo) {
        if(veiculos.containsKey(placa)) 
            return false;

        veiculos.put(placa, veiculo);
        tamanhoFrota++;
            return true;
    }

    /***
     * Função de tipo Veiculo que retorna o veiculo removido caso, exista, caso contrario retorna null
     * @param placa = Placa do veiculo a ser removido
     * @return Veiculo removido ou null, caso não tenha sido removido.
     */
    public Veiculo removerVeiculo(String placa) {
        return veiculos.remove(placa);
    }

    public boolean addRota(String placa, Rota rota) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        if( veiculoEncontrado !=null) 
            return veiculoEncontrado.addRota(rota);
        

        return false; //throws?
    }



    /***
     * Função do tipo String que retorna o relatorio da frota contendo seu tamanho e informações da lista de veiculos.
     * @return relatorio = String contendo o relatorio.
     */
    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder("\nFrota");
        relatorio.append("Tamanho: "+tamanhoFrota);
        relatorio.append("\nLista de veiculos:");

        veiculos.values().stream()
                         .forEach(v -> relatorio.append("\n"+v.relatorioRotas()));

        return relatorio.toString();
    }

    /**
     * Função do tipo Veiculo que retorna um objeto veiculo ou null, caso não encontre. Ele recebe uma placa e itera todos os veiculos da frota.
     * @param placa do veiculo a ser pesquisado.
     * @return objeto Veiculo encontrado ou null.
     */
    public Veiculo localizarVeiculo(String placa) {
        return veiculos.entrySet().stream()
                .filter(v-> v.getKey().equals(placa))
                .findFirst() //assim que encontrar já envia, evitando percorrer se já encontrou.
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    /***
     * Função double que retorna a soma da quilometragem de todos os veiculos da frota.
     * @return qTotal = Soma da quilometragem de todos os veiculos da frota.
     */
    public double quilometragemTotal() {
        double qTotal=0;
        qTotal = veiculos.values().stream()
                                  .mapToDouble(Veiculo::kmTotal)
                                  .sum();
        return qTotal;
    }

    /***
     * Função do tipo Veiculo que retorna o veiculo com maior quilometragem total percorrida.
     * @return Veiculo com maior KM total da frota.
     */
    public Veiculo maiorKMTotal() {

        return veiculos.values().stream()                        
                         .max(Comparator.comparing(Veiculo::kmTotal))
                         .orElse(null);
    } 

    /***
     * Função do tipo Veiculo que retorna o veiculo com maior quilometragem media percorrida. Ele itera os veiculos calculando a media de km totais rodados pela quantidade de rotas feitas.
     * @return Veiculo com maior KM média da frota.
     */
    public Veiculo maiorKMMedia() {
        return veiculos.values().stream()
                        .max((v1, v2) -> {
                            return Double.compare(v1.kmTotal() / v1.getQuantRotas(), v2.kmTotal() / v2.getQuantRotas());
                        })
                        .orElse(null);
    } 

    public String quilometragemTotalDeVeiculo(String placa) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        
        if( veiculoEncontrado !=null)
            return "Veiculo de placa: "+placa + " percorreu "+ localizarVeiculo(placa).kmTotal()+ " quilômetros no total.";
        
        return null; //throws?
    }

    public String combustivelConsumido(String placa) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        if( veiculoEncontrado !=null)
            return "Veiculo de placa: "+placa + " consumiu "+ localizarVeiculo(placa).getTotalReabastecido()+ " livros de combustivel.";
        
        return null; //throws?
    }

    public double combustivelTotalConsumido() { //qual dos dois?
        return veiculos.values().stream()
                       .mapToDouble(Veiculo::getTotalReabastecido)
                       .sum();
    }


    public String quilometragemDeVeiculoNoMes(LocalDate data, String placa) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        
        if( veiculoEncontrado !=null)
            return "Veiculo de placa: "+placa + " percorreu "+ localizarVeiculo(placa).kmNoMes(data)+ " quilômetros.";
        
        return null; //throws?
    }

    public String relatorioDespesasDeVeiculo(String placa) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        if( veiculoEncontrado !=null)
            return "CAAAAAAADE OO RELATORIO DE DESPESASSSSSSSSSSSSSSSS"    ;
                // TODO: Relatorio de despesas

        return null; //throws?
    }

    public String relatorioRotasDeVeiculo(String placa) {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        if( veiculoEncontrado !=null)
            return veiculoEncontrado.relatorioRotas();

        return null; //throws?
    }

}
