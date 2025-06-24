package br.com.alura.Screenmatch.desafios;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SegundoDesafio {
    public static void main(String[] args) {
        List<String> palavras = Arrays.asList("java", "stream", "Lambda", "code", "gustavo", "savio", "Beatriz");
        Map<Integer, List<String>> agrupamento = palavras.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(agrupamento);
    }
}
