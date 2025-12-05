import java.util.ArrayList;

public abstract class Evento implements Detalhes {

    private String titulo;
    private String data;
    private ArrayList<Participante> participantes;

    public Evento(String titulo, String data) {
        this.titulo = titulo;
        this.data = data;
        this.participantes = new ArrayList<>();
    }

    public abstract String gerarComprovante();

    public void adicionarParticipante(Participante p) {
        this.participantes.add(p);
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public ArrayList<Participante> getParticipantes() { return participantes; }

    public int getTotalInscritos() {
        return this.participantes.size();
    }

    @Override
    public String getDetalhes() {
        return "Evento: " + this.titulo + " | Data: " + this.data;
    }
}