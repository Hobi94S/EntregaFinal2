public class Pagante extends Participante {
    private double valorPago;

    public Pagante(String nome, String email, double valorPago) {
        super(nome, email);
        this.valorPago = valorPago;
    }

    @Override
    public String getDetalhes() {
        return super.getDetalhes() + " | Tipo: Pagante | Valor: R$ " + String.format("%.2f", this.valorPago);
    }

    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
}