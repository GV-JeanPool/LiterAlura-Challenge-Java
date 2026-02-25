package com.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Autor autor;

    @Column(name = "language")
    private String idioma;

    @Column(name = "download_count")
    private Long numeroDescargas;

    public Libro() {
    }

    public Libro(String titulo, Autor autor, String idioma, Long numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "\n" +
                "========== LIBRO ==========\n" +
                "Título: " + titulo + '\n' +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido") + '\n' +
                "Idioma: " + idioma + '\n' +
                "Descargas: " + numeroDescargas + '\n' +
                "===========================\n";
    }
}
