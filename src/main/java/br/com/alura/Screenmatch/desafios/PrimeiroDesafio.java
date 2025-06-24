package br.com.alura.Screenmatch.desafios;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PrimeiroDesafio {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);

        Optional<Integer> maiorNumero = numeros.stream().max(Integer::compare);

        maiorNumero.ifPresent(System.out::println);

    }
}