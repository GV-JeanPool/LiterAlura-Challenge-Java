package com.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.RespuestaAPIDTO;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsultaAPI {

    private static final String URL_BASE = "https://gutendex.com/books";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static RespuestaAPIDTO buscarLibrosPorTitulo(String titulo) {
        try {
            String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String url = URL_BASE + "?search=" + tituloCodificado;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), RespuestaAPIDTO.class);
            } else {
                System.out.println("Error en la solicitud. Código de estado: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
            return null;
        }
    }
}
