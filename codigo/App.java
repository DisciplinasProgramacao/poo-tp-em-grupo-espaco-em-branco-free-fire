package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static Frota frota = new Frota();
    static Scanner sc;

    /**
     * Exibe um menu de opções para o usuário, lendo as opções de um arquivo de texto.
     * @param nomeArquivo Nome do arquivo de texto.
     * @return Opção escolhida pelo usuário.
     */
    public static int menu(String nomeArquivo) {
        try {
            limparTela();
            File arqMenu = new File(nomeArquivo);
            Scanner leitor = new Scanner(arqMenu, StandardCharsets.UTF_8);
            System.out.println(leitor.nextLine());
            System.out.println("==========================");
            int contador = 1;
            while (leitor.hasNextLine()) {
                System.out.println(contador + " - " + leitor.nextLine());
                contador++;
            }
            System.out.println("0 - Sair");
            System.out.print("\nSua opção: ");
            int opcao = Integer.parseInt(sc.nextLine());
            leitor.close();
            return opcao;
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + nomeArquivo);
            return -1;
        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
            return -1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            return -1;
        }
    }

    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Encapsula uma leitura de teclado, com mensagem personalizada. A mensagem sempre é completada com ":". Retorna uma string
     * que pode ser posteriormente convertida.
     *
     * @param mensagem A mensagem a ser exibida, sem pontuação final.
     * @return String lida do usuário.
     */
    public static String leitura(String mensagem) {
        System.out.print(mensagem + ": ");
        return sc.nextLine();
    }

    /**
     * Pausa para leitura de mensagens em console.
     */
    static void pausa() {
        System.out.println("Enter para continuar.");
        sc.nextLine();
    }

    /**
     * Encapsula a ação de criar um veículo, lendo os dados do usuário e validando-os.
     * @return Veículo criado.
     */
    public static Veiculo criarVeiculo() {
        String placa;
        do {
            placa = leitura("Digite a placa do veículo (formato AAA-0000): ");
            if (!placa.matches("[A-Z]{3}-\\d{4}"))
                System.out.println("Placa em formato inválido.");
        } while (!placa.matches("[A-Z]{3}-\\d{4}"));

        String tipoVeiculo;
        do {
            tipoVeiculo = leitura("Digite o tipo do veículo (CARRO, VAN, FURGAO, CAMINHAO): ");
            if (!tipoVeiculo.matches("(?i)(CARRO|VAN|FURGAO|CAMINHAO)"))
                System.out.println("Tipo de veículo inválido.");
        } while (!tipoVeiculo.matches("(?i)(CARRO|VAN|FURGAO|CAMINHAO)"));

        String tipoCombustivel;
        do {
            tipoCombustivel = leitura("Digite o tipo de combustível do veículo (ALCOOL, GASOLINA, DIESEL): ");
            if (!tipoCombustivel.matches("(?i)(ALCOOL|GASOLINA|DIESEL)"))
                System.out.println("Tipo de combustível inválido.");
        } while (!tipoCombustivel.matches("(?i)(ALCOOL|GASOLINA|DIESEL)"));

        return new Veiculo(placa, tipoVeiculo, tipoCombustivel);
    }


    /**
     * Encapsula a ação de criar uma rota, lendo os dados do usuário e validando-os.
     * @return Rota criada.
     */
    public static Rota criarRota() {
        LocalDate data = informeData();

        double quilometragem;
        do {
            String input = leitura("Digite a quilometragem da rota: ");
            try {
                quilometragem = Double.parseDouble(input);
                if (quilometragem < 0) {
                    System.out.println("A quilometragem deve ser um valor não negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                quilometragem = -1;
            }
        } while (quilometragem < 0);

        return new Rota(quilometragem, data);
    }

    /**
     * Encapsula a ação de ler uma linha de um arquivo e retorna um array de strings, separando os campos por ";".
     * @param leitor Scanner do arquivo.
     * @return Array de strings com os campos.
     */
    public static String[] lerLinhaDeArquivo(Scanner leitor) {
        String linha = leitor.nextLine();
        return linha.split(";");
    }

    /**
     * Encapsula a ação de ler uma data do usuário, validando o formato.
     * @return Objeto LocalDate.
     */
    public static LocalDate informeData() {
        LocalDate data = null;
        do {
            String dataInput = leitura("Informe uma data (dd/MM/yyyy): ");
            try {
                data = LocalDate.parse(dataInput, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, digite uma data válida.");
            }
        } while (data == null);

        return data;
    }


    /**
     * Encapsula a ação de ler a placa de um veículo do usuário.
     * @return Placa do veículo.
     */
    public static String informeVeiculo() {
        return leitura("Digite a placa do veículo");
    }

    /**
     * Encapsula a ação de ler o nome de um arquivo do usuário.
     * @return Nome do arquivo.
     */
    public static String informeArquivo() {
        return leitura("Digite o nome do arquivo");
    }

    /**
     * Encapsula a ação de exibir o menu de relatórios disponíveis e chama a função correspondente.
     * @param opcao Opção escolhida pelo usuário.
     */
    public static int menuRelatorio(int opcao) {
        try {
            limparTela();
            String response;
            switch (opcao) {
                case 1 -> {
                    limparTela();
                    response = frota.relatorioFrota();
                    System.out.println(response);
                }
                case 2 -> {
                    limparTela();
                    response = String.valueOf(frota.quilometragemTotal());
                    System.out.println(response);
                }
                case 3 -> {
                    limparTela();
                    response = String.valueOf(frota.maiorKMTotal());
                    System.out.println(response);
                }
                case 4 -> {
                    limparTela();
                    response = String.valueOf(frota.maiorKMMedia());
                    System.out.println(response);
                }
                case 5 -> {
                    limparTela();
                    response = frota.combustivelConsumido(informeVeiculo());
                    System.out.println(response);
                }
                case 6 -> {
                    limparTela();
                    response = frota.quilometragemDeVeiculoNoMes(informeData(), informeVeiculo());
                    System.out.println(response);
                }
                case 7 -> {
                    limparTela();
                    response = frota.quilometragemTotalDeVeiculo(informeVeiculo());
                    System.out.println(response);
                }
                case 8 -> {
                    limparTela();
                    response = frota.relatorioDespesasDeVeiculo(informeVeiculo());
                    System.out.println(response);
                }
                case 9 -> {
                    limparTela();
                    response = frota.relatorioRotasDeVeiculo(informeVeiculo());
                    System.out.println(response);
                }
                case 10 -> {
                    limparTela();
                    response = frota.relatorioManutencoesDeVeiculo(informeVeiculo());
                    System.out.println(response);
                }
                case 11 -> menuPrincipal();
                case 0 -> {
                    return 0;
                }
                default -> System.out.println("Opção inválida.");
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Exibe o menu de gestão e chama a função escolhida.
     * @param opcao Opção escolhida pelo usuário.
     */
    public static int menuGestao(int opcao) {
        String response;
        try {
            limparTela();
            switch (opcao) {
                case 1 -> {
                    Veiculo veiculo = criarVeiculo();
                    response = frota.addVeiculo(veiculo.getPlaca(), veiculo);
                    System.out.println(response);
                }
                case 2 -> {
                    String arquivo = informeArquivo();
                    Scanner leitor = new Scanner(new File(arquivo), StandardCharsets.UTF_8);
                    while (leitor.hasNextLine()) {
                        String[] dados = lerLinhaDeArquivo(leitor);
                        Veiculo veiculo = new Veiculo(dados[0], dados[1], dados[2]);
                        response = frota.addVeiculo(veiculo.getPlaca(), veiculo);
                        System.out.println(response);
                    }
                    leitor.close();
                }
                case 3 ->  {
                    response = frota.removerVeiculo(informeVeiculo());
                    System.out.println(response);
                }
                case 4 -> {
                    Rota rota = criarRota();
                    response = frota.addRota(informeVeiculo(), rota);
                    System.out.println(response);
                }
                case 5 -> {
                    String placa = informeVeiculo();
                    String arquivo = informeArquivo();
                    Scanner leitor = new Scanner(new File(arquivo), StandardCharsets.UTF_8);
                    while (leitor.hasNextLine()) {
                        String[] dados = lerLinhaDeArquivo(leitor);
                        Rota rota = new Rota(Double.parseDouble(dados[0]), LocalDate.parse(dados[1], DATE_TIME_FORMATTER));
                        response = frota.addRota(placa, rota);
                        System.out.println(response);
                    }
                    leitor.close();
                }
                case 6 -> menuPrincipal();
                case 0 -> {
                    return 0;
                }
                default -> System.out.println("Opção inválida.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo.");
        } catch (IOException e) {
            System.out.println("Erro de leitura do arquivo.");
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido no arquivo.");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Exibe o menu principal e chama a função escolhida.
     */
    public static int menuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            limparTela();
            opcao = menu("menuPrincipal");
            switch (opcao) {
                case 1 -> {
                    limparTela();
                    opcao = menu("menuGestao");
                    opcao = menuGestao(opcao);
                }
                case 2 -> {
                    limparTela();
                    opcao = menu("menuRelatorio");
                    opcao = menuRelatorio(opcao);
                }
                case 0 -> {
                    return 0;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            sc = new Scanner(System.in);

            String nomeArq = "menuFreeFire";
            int opcao = -1;

            while (opcao != 0) {
                limparTela();
                opcao = menu(nomeArq);
                switch (opcao) {
                    case 1 -> {
                        limparTela();
                        opcao = menuPrincipal();
                    }
                    case 0 -> {
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
            System.out.println("Saindo...");
            System.out.println("Free Fire Logística: Trajetos Rápidos, Resultados Explosivos! Booyah!");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

    }
}
