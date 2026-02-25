package com.literalura.repository;

import com.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);

    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Long contarLibrosPorIdioma(String idioma);
}
