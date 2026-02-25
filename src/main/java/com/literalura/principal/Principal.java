package com.literalura.principal;

import com.literalura.service.LibroService;
import java.util.Scanner;

public class Principal {

    private final LibroService libroService;
    private final Scanner scanner;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║      ¡BIENVENIDO A LITERALURA!         ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Buscar libro por título             ║");
            System.out.println("║ 2. Listar todos los libros             ║");
            System.out.println("║ 3. Listar autores                      ║");
            System.out.println("║ 4. Listar autores vivos en un año      ║");
            System.out.println("║ 5. Listar libros por idioma            ║");
            System.out.println("║ 6. Ver estadísticas de idioma          ║");
            System.out.println("║ 0. Salir                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        libroService.listarTodosLosLibros();
                        break;
                    case 3:
                        libroService.listarAutores();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        verEstadisticasIdioma();
                        break;
                    case 0:
                        System.out.println("\n¡Gracias por usar LiterAlura! ¡Hasta luego!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor ingresa un número válido.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }

        scanner.close();
    }

    private void buscarLibro() {
        System.out.print("\nIngresa el título del libro a buscar: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        libroService.buscarYGuardarLibro(titulo);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("\nIngresa el año para buscar autores vivos: ");

        try {
            int anio = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (anio < 0 || anio > 9999) {
                System.out.println("Por favor ingresa un año válido.");
                return;
            }

            libroService.listarAutoresVivosEnAnio(anio);
        } catch (Exception e) {
            System.out.println("Error: Por favor ingresa un año válido.");
            scanner.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\nIdiomas disponibles:");
        System.out.println("1. en (Inglés)");
        System.out.println("2. es (Español)");
        System.out.println("3. fr (Francés)");
        System.out.println("4. de (Alemán)");
        System.out.println("5. pt (Portugués)");
        System.out.print("Ingresa el código del idioma o escribe manualmente: ");

        String idioma = scanner.nextLine().trim();

        if (idioma.isEmpty()) {
            System.out.println("El idioma no puede estar vacío.");
            return;
        }

        libroService.listarLibrosPorIdioma(idioma);
    }

    private void verEstadisticasIdioma() {
        System.out.println("\nIdiomas disponibles:");
        System.out.println("1. en (Inglés)");
        System.out.println("2. es (Español)");
        System.out.println("3. fr (Francés)");
        System.out.println("4. de (Alemán)");
        System.out.println("5. pt (Portugués)");
        System.out.print("Ingresa el código del idioma para ver estadísticas: ");

        String idioma = scanner.nextLine().trim();

        if (idioma.isEmpty()) {
            System.out.println("El idioma no puede estar vacío.");
            return;
        }

        libroService.mostrarEstadisticasIdioma(idioma);
    }
}
