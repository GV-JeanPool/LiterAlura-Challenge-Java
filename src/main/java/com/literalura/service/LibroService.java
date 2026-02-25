package com.literalura.service;

import com.literalura.dto.AutorDTO;
import com.literalura.dto.LibroDTO;
import com.literalura.dto.RespuestaAPIDTO;
import com.literalura.model.Autor;
import com.literalura.model.Libro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void buscarYGuardarLibro(String titulo) {
        RespuestaAPIDTO respuesta = ConsultaAPI.buscarLibrosPorTitulo(titulo);

        if (respuesta != null && respuesta.getLibros() != null && !respuesta.getLibros().isEmpty()) {
            LibroDTO libroDTO = respuesta.getLibros().get(0);

            // Verificar si el libro ya existe
            if (libroRepository.findByTitulo(libroDTO.getTitulo()).isPresent()) {
                System.out.println("El libro '" + libroDTO.getTitulo() + "' ya existe en la base de datos.");
                return;
            }

            // Procesar autor
            Autor autor = null;
            if (libroDTO.getAutores() != null && !libroDTO.getAutores().isEmpty()) {
                AutorDTO autorDTO = libroDTO.getAutores().get(0);
                Optional<Autor> autorExistente = autorRepository.findByNombre(autorDTO.getNombre());

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(autorDTO.getNombre(), autorDTO.getAnioNacimiento(), autorDTO.getAnioFallecimiento());
                    autor = autorRepository.save(autor);
                }
            }

            // Procesar idioma (solo el primero)
            String idioma = "";
            if (libroDTO.getIdiomas() != null && !libroDTO.getIdiomas().isEmpty()) {
                idioma = libroDTO.getIdiomas().get(0);
            }

            // Crear y guardar libro
            Libro libro = new Libro(libroDTO.getTitulo(), autor, idioma, libroDTO.getNumeroDescargas());
            libroRepository.save(libro);

            System.out.println("\n✓ Libro guardado exitosamente:");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró ningún libro con el título: " + titulo);
        }
    }

    public void listarTodosLosLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados en la base de datos.");
        } else {
            System.out.println("\n========== LIBROS REGISTRADOS ==========");
            for (Libro libro : libros) {
                System.out.println(libro);
            }
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados en la base de datos.");
        } else {
            System.out.println("\n========== AUTORES REGISTRADOS ==========");
            for (Autor autor : autores) {
                System.out.println(autor);
            }
        }
    }

    public void listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros en el idioma: " + idioma);
        } else {
            System.out.println("\n========== LIBROS EN IDIOMA: " + idioma + " ==========");
            for (Libro libro : libros) {
                System.out.println(libro);
            }
        }
    }

    public void mostrarEstadisticasIdioma(String idioma) {
        Long cantidad = libroRepository.contarLibrosPorIdioma(idioma);
        System.out.println("\nTotal de libros en idioma '" + idioma + "': " + cantidad);
    }

    public void listarAutoresVivosEnAnio(Integer anio) {
        List<Autor> autores = autorRepository.encontrarAutoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores vivos en el año: " + anio);
        } else {
            System.out.println("\n========== AUTORES VIVOS EN " + anio + " ==========");
            for (Autor autor : autores) {
                System.out.println(autor);
            }
        }
    }
}
