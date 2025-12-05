public class EventoOnline extends Evento {

    private String link;
    private String plataforma;
    private String senhaAcesso;

    public EventoOnline(String titulo, String data, String link, String plataforma, String senha) {
        super(titulo, data);
        this.link = link;
        this.plataforma = plataforma;
        this.senhaAcesso = senha;
    }

    @Override
    public String gerarComprovante() {
        return "COMPROVANTE ONLINE\n" +
                "Evento: " + getTitulo() + "\n" +
                "Plataforma: " + this.plataforma + "\n" +
                "Link de Acesso: " + this.link + "\n" +
                "Senha da Sala: " + this.senhaAcesso + "\n";
    }

    @Override
    public String getDetalhes() {
        return super.getDetalhes() + " | Plataforma: " + this.plataforma;
    }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public String getSenhaAcesso() { return senhaAcesso; }
    public void setSenhaAcesso(String senhaAcesso) { this.senhaAcesso = senhaAcesso; }
}