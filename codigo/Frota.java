package codigo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Frota {
    private int tamanhoFrota;
    private Map<String, Veiculo> veiculos;

    /***
     * Função construtora da classe, ela recebe uma array de veiculos e determina o tamanho da frota pelo array da lista.
     * @param veiculos - Array de Veiculos
     */
    Frota(Map<String, Veiculo> veiculos) {
        tamanhoFrota+= veiculos.size();
        this.veiculos = veiculos;
    }

    /***
     * Função construtora da classe, ela não recebe parametros, inicializando o tamanho da frota com 0 e instanciando o HashMap.
     */
    Frota() {
        tamanhoFrota=0;
        veiculos = new HashMap<String,Veiculo>();
    }

    //#region Funções da frota

    /***
     * Função que retorna uma mensagem se foi possivel adicionar o veiculo, ou se o veiculo já tinha sido cadastrado.
     * @param placa - Placa do veiculo a ser adicionado
     * @param veiculo - Objeto Veiculo a ser adicionado
     * @return Mensagem informando sucesso ao adicionar ou RuntimeException informando que não foi possivel.
     */
    public String addVeiculo(String placa, Veiculo veiculo) {

        if(veiculo==null) 
            throw new NullPointerException("Veiculo é nulo.");

        if(!veiculos.containsKey(placa)) {
            veiculos.put(placa, veiculo);
            tamanhoFrota++;
            return "Veiculo de placa: "+placa+" foi adicionado com sucesso.";
        }

        throw new RuntimeException("Não é possível adicionar. A placa já está cadastrada.");
    }

    /***
     * Função de tipo Veiculo que retorna o veiculo removido caso, exista, caso contrario retorna null
     * @param placa - Placa do veiculo a ser removido
     * @return Veiculo removido ou null, caso não tenha sido removido.
     */
    public String removerVeiculo(String placa) {
        Veiculo veiculoRemovido = veiculos.remove(placa);

        if(veiculoRemovido!=null) {
            return "O veiculo removido foi: \n"+veiculoRemovido.toString();
        }
            return "Veículo com placa: "+placa+" não foi encontrado.";
    }

     /**
      * Função que retorna a soma da quilometragem de todos os veiculos da frota.
      * @return double contendo a soma da quilometragem de todos os veiculos da frota.
      */
    public String quilometragemTotal() {
        double quilometragemTotal=0;
        quilometragemTotal = veiculos.values().stream()
                                  .mapToDouble(Veiculo::kmTotal)
                                  .sum();
        return "A quilometragem total percorrida por todos os veículos da rota é: "+quilometragemTotal;
    }

    /**
     * Função que retorna informações do veiculo com maior quilometragem total percorrida ou mensagem informando
     * que não há veiculos.
     * @return String contendo o veiculo com maior KM total da frota ou mensagem de erro.
     */ 
    public String maiorKMTotal() {
        Veiculo veiculoMaiorKMTotal = veiculos.values().stream()                        
                                        .max(Comparator.comparing(Veiculo::kmTotal))
                                        .orElse(null);
        if(veiculoMaiorKMTotal!=null) 
            return veiculoMaiorKMTotal.toString();
        
        return "Não há veiculos.";
    }  

    /**
     * Função que retorna informações do veiculo com maior quilometragem média percorrida ou mensagem informando
     * que não há veiculos.
     * @return String contendo o veiculo com maior KM medio da frota ou mensagem de erro.
     */ 
    public String maiorKMMedia() {
        Veiculo veiculoMaiorKMMedia = veiculos.values().stream()
                                    .max((v1, v2) -> {
                                        return Double.compare(v1.kmTotal() / v1.getQuantRotas(), v2.kmTotal() / v2.getQuantRotas());
                                    })
                                    .orElse(null);
        if(veiculoMaiorKMMedia!=null) 
            return veiculoMaiorKMMedia.toString();
        
        return "Não há veiculos.";
    } 

    /**
     * Função que retorna o combustivel total consumido pela frota.
     * @return String contendo a quantidade total.
     */
    public String combustivelTotalConsumido() { 
        double combustivelTotal = veiculos.values().stream()
                       .mapToDouble(Veiculo::getTotalReabastecido)
                       .sum();
        
        return "O combustivel consumido por todos veículos da frota é "+combustivelTotal+"L";
    }
    //#endregion


    //#region Funções de veiculo

    /**
     * Função que retorna um objeto veiculo ou lança uma exceção, caso não encontre. Ele recebe uma placa e itera todos os veiculos da frota.
     * @param placa do veiculo a ser pesquisado.
     * @return objeto Veiculo encontrado ou erro de exceção.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado.     
     */
    private Veiculo localizarVeiculo(String placa) {
        return veiculos.entrySet().stream()
                .filter(v-> v.getKey().equals(placa))
                .findFirst() //assim que encontrar já envia, evitando percorrer se já encontrou.
                .map(Map.Entry::getValue)
                .orElseThrow();    
    }

    /**
     * Função que retorna se a rota foi adicionada ou não ao veiculo.  Ela recebe uma placa e uma rota,
     * pesquisa o veiculo e adiciona a rota nele.
     * @param placa - Placa do veiculo.
     * @param rota - Objeto rota.
     * @return String informando se a rota foi adicionada ou não.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado.     
     **/
     public String addRota(String placa, Rota rota) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        if (veiculoEncontrado.addRota(rota)) {
            return "A rota foi adicionada para o veículo de placa: " + placa;
        } else {
            return "Não foi possível adicionar a rota para o veículo de placa: " + placa;
        } 
    }

    /**
     * Função que retorna a quantidade de combustivel consumido pelo veiculo pesquisado. Ela recebe uma placa, 
     * pesquisa o veiculo e retorna a quantidade de combustivel.
     * @return String a quantidade de combustivel consumido.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado. 
     */
    public String combustivelConsumido(String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        double combustivelConsumido = veiculoEncontrado.getTotalReabastecido();

        return "Veiculo de placa: "+placa + " consumiu "+ combustivelConsumido+ " litros de combustivel.";
    }

    /**
     * Função que retorna a quilometragem total percorrida por um veiculo. Ela recebe uma placa,
     * pesquisa o veiculo e retorna a quilometragem.
     * @param placa - Placa do veiculo.
     * @return A quilometragem total do veiculo pesquisado.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado. 
     */
    public String quilometragemTotalDeVeiculo(String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        double quilometragemTotal = veiculoEncontrado.kmTotal();

        return "A quilometragem do veículo é "+quilometragemTotal;
    }

    /**
     * Função que retorna a quilometragem total percorrida no mês por um veiculo. Ela recebe uma placa e uma data,
     * pesquisa o veiculo e retorna a quilometragem percorrida naquele mês.
     * @param placa - Placa do veiculo.
     * @return A quilometragem do veiculo do mês pesquisado.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado. 
     */
    public String quilometragemDeVeiculoNoMes(LocalDate data, String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        double quilometragemMes = veiculoEncontrado.kmNoMes(data);

        return "Veiculo de placa: "+placa + " percorreu "+quilometragemMes+ " quilômetros no mês ("+data.getMonthValue()+").";
    }

    /**
     * Função que permite adicionar um valor a manutenção dos veículos. Ela recebe uma placa, um ID da manutenção, e um valor. 
     * Pesquisa o veiculo, e chama a função para adicionar o valor a manutenção
     * @param placa - Placa do veiculo.
     * @param idManutencao - ID da manutenção
     * @param valor - valor da manutenção
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado. 
     */
    public void addValorManutencao(String placa, int idManutencao, double valor) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        veiculoEncontrado.addValorManutencao(idManutencao, valor);
    }

    //#endregion


    //#region Funções de relatorios

    /**
     * Função que retorna o relatorio da frota contendo seu tamanho e informações da lista de veiculos.
     * @return String contendo o relatorio de frota contendo os veiculos.
     */
    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder("\n🔫Frota FreeFire☠️");
        relatorio.append("\nTamanho: "+tamanhoFrota);
        relatorio.append("\nLista de veiculos:");

        veiculos.values().stream()
                         .forEach(v -> relatorio.append("\n"+v.toString()));

        return relatorio.toString();
    }

    /**
     * Função que retorna o relatorio de manutenções de um veiculo. Ela recebe uma placa, pesquisa o veiculo e retorna uma string com o relatorio.
     * @param placa - Placa do veiculo.
     * @return String contendo o relatorio de manutenções.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado.
     */
    public String relatorioManutencoesDeVeiculo(String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        return veiculoEncontrado.relatorioManutencoes();
    }

    /**
     * Função que retorna o relatorio de rotas de um veiculo. Ela recebe uma placa, pesquisa o veiculo e retorna uma string com todas as rotas percorridas.
     * @param placa - Placa do veiculo.
     * @return String contendo o relatorio de rotas.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado.
     */
    public String relatorioRotasDeVeiculo(String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);

        return veiculoEncontrado.relatorioRotas();
    }

    /**
     * Função que retorna o relatorio de despesas de um veiculo. Ela recebe uma placa, pesquisa o veiculo e retorna uma string com o valor gasto em despesas.
     * @param placa - Placa do veiculo.
     * @return String contendo o valor gasto em despesas.
     * @throws NoSuchElementException Caso o veiculo não seja encontrado, o erro é propagado.
     */
    public String relatorioDespesasDeVeiculo(String placa) throws NoSuchElementException {
        Veiculo veiculoEncontrado = localizarVeiculo(placa);
        double gastosDespesa = veiculoEncontrado.despesasDoVeiculo();

        return "Veículo de placa:"+placa+" teve um total de R$"+gastosDespesa+" gastos em despesas.";
    }


    //#endregion
}
