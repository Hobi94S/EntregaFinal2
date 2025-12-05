public class EventoPresencial extends Evento {

    private String local;
    private int capacidadeMaxima;

    public EventoPresencial(String titulo, String data, String local, int capacidadeMaxima) {
        super(titulo, data);
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public String gerarComprovante() {
        return "COMPROVANTE PRESENCIAL\n" +
                "Evento: " + getTitulo() + "\n" +
                "Local: " + this.local + "\n";
    }

    @Override
    public String getDetalhes() {
        return super.getDetalhes() + " | Local: " + this.local +
                " | Vagas: " + getTotalInscritos() + "/" + this.capacidadeMaxima;
    }

    public boolean temVaga() {
        return getTotalInscritos() < this.capacidadeMaxima;
    }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public void setCapacidadeMaxima(int capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
}