public class Convidado extends Participante {

    private String empresa;

    public Convidado(String nome, String email, String empresa) {
        super(nome, email);
        this.setEmpresa(empresa);
    }

    @Override
    public String getDetalhes() {
        return super.getDetalhes() + " | Tipo: Convidado (Empresa: " + this.empresa + ")";
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}