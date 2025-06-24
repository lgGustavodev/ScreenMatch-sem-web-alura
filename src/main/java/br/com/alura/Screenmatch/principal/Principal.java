package br.com.alura.Screenmatch.principal;

import br.com.alura.Screenmatch.model.DadosEpisodio;
import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.model.DadosTemporada;
import br.com.alura.Screenmatch.model.Episodio;
import br.com.alura.Screenmatch.service.ConsumoApi;
import br.com.alura.Screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=3b627f3f";

    public void exibieMenu(){
        System.out.println("Digite o nome da Serio para Buscar os dados:");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        //"https://www.omdbapi.com/?t=Supernatural&apikey=3b627f3f"

        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i<= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);


        }
        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e-> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Nico");
//        nomes.stream()
//                .sorted().limit(3)
//                .filter(n -> n.startsWith("N"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);
//
//
//        List<String> palavras = Arrays.asList("UVA", "JAVA", "JAVASCRIPT", "PYthon");
//        List<Integer> tamanhos = palavras.stream().map(s -> s.length()).collect(Collectors.toList());
//        System.out.println(tamanhos);
//
//        List<String> nomes2 = Arrays.asList("GUSTAVO", "SAVIO", "BEATRIZ");
//        nomes2.stream().forEach(nome -> System.out.println("Ola, " + nome + "!"));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        System.out.println("\nTop 10 Episodios:");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenaçao " + e))
//                .limit(10)
//                .peek(e-> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite um trecho do titulo do episodio: ");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().contains(trechoTitulo))
                .findFirst();

        if (episodioBuscado.isPresent()){
            System.out.println("Episodio entrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        }else {
            System.out.println("Episodio nao encontardo!");
        }
//
//        System.out.println("A partir de que ano voce deseja ver os episodios ?");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBucas = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBucas))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data de Lancamento: " + e.getDataLancamento().format(formatador)
//                ));


        Map<Integer, Double> avalivacoesPorTemporada = episodios.stream()
                .filter(e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avalivacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println(est);
    }

}
