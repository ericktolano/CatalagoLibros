package tolano.catalagolibros;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anio;
    private int paginas;

    public Libro(int id, String titulo, String autor, int anio, int paginas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.paginas = paginas;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public int getPaginas() {
        return paginas;
    }
}
