package br.com.alura.Screenmatch.principal;

import br.com.alura.Screenmatch.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=3b627f3f";

    public void exibieMenu(){
        System.out.println("Digite o nome da Serio para Buscar os dados:");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);




        //"https://www.omdbapi.com/?t=Supernatural&apikey=3b627f3f"
    }
}
