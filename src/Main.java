import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {

    private static ArrayList<Evento> listaEventos = new ArrayList<>();

    public static void main(String[] args) {
        Object[] opcoesMenu = {
                "Cadastrar Presencial", "Cadastrar Online", "Inscrever Participante", "Listar Eventos",
                "Ver Participantes", "Gerar Comprovante", "Relatório de Lotação", "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "<html><h2><font color='#003366'>Gestão de Eventos</font></h2>" +
                            "Selecione uma operação abaixo:</html>",
                    "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opcoesMenu, opcoesMenu[0]
            );

            if (escolha == -1 || escolha == 7) break;

            try {
                switch (escolha) {
                    case 0 -> cadastrarPresencial();
                    case 1 -> cadastrarOnline();
                    case 2 -> inscreverParticipante();
                    case 3 -> listarEventos();
                    case 4 -> verParticipantes();
                    case 5 -> gerarComprovante();
                    case 6 -> gerarRelatorioLotacao();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "<html><b style='color:red'>Erro inesperado:</b><br>" + e.getMessage() + "</html>");
            }
        }
    }


    private static void cadastrarPresencial() {
        String titulo = lerTexto("<b>Título</b> do Evento:");
        if (titulo == null) return;

        String data = lerData("Data do Evento (<b>dd/mm/aaaa</b>):");
        if (data == null) return;

        String local = lerTexto("<b>Local</b> do Evento:");
        int capacidade = lerInteiro("Capacidade <b>Máxima</b>:");

        if (capacidade > 0) {
            listaEventos.add(new EventoPresencial(titulo, data, local, capacidade));
            mostrarSucesso("Evento Presencial cadastrado!<br>Código: <b>" + listaEventos.size() + "</b>");
        }
    }

    private static void cadastrarOnline() {
        String titulo = lerTexto("<b>Título</b> do Evento:");
        if (titulo == null) return;

        // Uso do validador de data
        String data = lerData("Data do Evento (<b>dd/mm/aaaa</b>):");
        if (data == null) return;

        String plataforma = lerTexto("Nome da <b>Plataforma</b>:");
        String link = lerTexto("<b>Link</b> de acesso:");
        String senha = lerTexto("<b>Senha</b> (caso exista):");

        listaEventos.add(new EventoOnline(titulo, data, link, plataforma, senha));
        mostrarSucesso("Evento Online cadastrado!<br>Código: <b>" + listaEventos.size() + "</b>");
    }


    private static void inscreverParticipante() {
        if (listaEventos.isEmpty()) {
            mostrarAlerta("Não há eventos para inscrever.");
            return;
        }

        int indice = lerInteiro("Digite o <b>Código do evento</b> (1 a " + listaEventos.size() + "):") - 1;
        if (indice < 0 || indice >= listaEventos.size()) {
            mostrarErro("Evento inválido!");
            return;
        }

        Evento evento = listaEventos.get(indice);

        if (evento instanceof EventoPresencial ep) {
            if (!ep.temVaga()) {
                mostrarErro("<html>Evento <b>Lotado</b>!<br>Não é possível realizar nova inscrição.</html>");
                return;
            }
        }

        String nome = lerTexto("Nome do <b>Participante</b>:");
        if (nome == null) return;
        String email = lerTexto("<b>E-mail</b> de contato:");

        Object[] tipos = {"Pagante", "Convidado"};
        int tipoEscolha = JOptionPane.showOptionDialog(null,
                "<html><b>Tipo de Inscrição:</b></html>", "Selecionar Tipo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipoEscolha == 0) {
            double valor = lerDouble("Valor pago (R$):");
            evento.adicionarParticipante(new Pagante(nome, email, valor));
        } else if (tipoEscolha == 1) {
            String empresa = lerTexto("Nome da <b>Empresa</b>:");
            evento.adicionarParticipante(new Convidado(nome, email, empresa));
        }

        if (tipoEscolha != -1) {
            mostrarSucesso("Inscrição realizada para <b>" + nome + "</b>!");
        }
    }

    private static void listarEventos() {
        if (listaEventos.isEmpty()) {
            mostrarAlerta("Nenhum evento cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("<html><body style='width: 300px'>");
        sb.append("<h2 style='color: #003366'>Lista de Eventos</h2>");

        for (int i = 0; i < listaEventos.size(); i++) {
            Evento e = listaEventos.get(i);
            String detalheHtml = e.getDetalhes().replace("|", "<br>• ");

            sb.append("<div style='background-color: #f0f0f0; margin-bottom: 5px; padding: 5px; border: 1px solid #ccc'>")
                    .append("<b>Código ").append(i + 1).append("</b><hr>")
                    .append(detalheHtml)
                    .append("</div><br>");
        }
        sb.append("</body></html>");

        JOptionPane.showMessageDialog(null, sb.toString(), "Eventos", JOptionPane.PLAIN_MESSAGE);
    }

    private static void verParticipantes() {
        if (listaEventos.isEmpty()) {
            mostrarAlerta("Nenhum evento cadastrado.");
            return;
        }
        int indice = lerInteiro("Digite o <b>Código do evento</b>:") - 1;
        if (indice < 0 || indice >= listaEventos.size()) {
            mostrarErro("Evento inválido!");
            return;
        }

        Evento evento = listaEventos.get(indice);
        StringBuilder sb = new StringBuilder("<html><body style='width: 350px'>");
        sb.append("<h2>Participantes: ").append(evento.getTitulo()).append("</h2>");
        sb.append("<table border='0' width='100%'>");

        if (evento.getParticipantes().isEmpty()) {
            sb.append("<tr><td><i>(Nenhum participante inscrito)</i></td></tr>");
        } else {
            for (Participante p : evento.getParticipantes()) {
                sb.append("<tr><td style='border-bottom: 1px solid #ddd; padding: 3px;'>")
                        .append("<b>").append(p.getNome()).append("</b><br>")
                        .append("<font size='2'>").append(p.getDetalhes()).append("</font>")
                        .append("</td></tr>");
            }
        }
        sb.append("</table></body></html>");
        JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Presença", JOptionPane.PLAIN_MESSAGE);
    }

    private static void gerarComprovante() {
        if (listaEventos.isEmpty()) {
            mostrarAlerta("Nenhum evento cadastrado.");
            return;
        }
        int indice = lerInteiro("Digite o <b>Código do evento</b>:") - 1;

        if (indice >= 0 && indice < listaEventos.size()) {
            Evento evento = listaEventos.get(indice);
            String textoOriginal = evento.gerarComprovante().replace("\n", "<br>");

            String html = "<html><body style='width: 300px; font-family: sans-serif;'>" +
                    "<div style='border: 2px dashed #000; padding: 10px; background-color: #fffff0'>" +
                    "<center><h3>🎟️ COMPROVANTE</h3></center><hr>" +
                    textoOriginal +
                    "<hr><center><i>Apresente este comprovante na entrada</i></center>" +
                    "</div></body></html>";

            JOptionPane.showMessageDialog(null, html, "Comprovante", JOptionPane.PLAIN_MESSAGE);
        } else {
            mostrarErro("Evento inválido.");
        }
    }

    private static void gerarRelatorioLotacao() {
        StringBuilder sb = new StringBuilder("<html><body style='width: 350px'>");
        sb.append("<h2>📊 Lotação (Presencial)</h2>");
        sb.append("<table border='1' cellspacing='0' cellpadding='5' width='100%'>");
        sb.append("<tr style='background-color: #ddd'><td><b>Evento</b></td><td><b>Ocupação</b></td></tr>");

        boolean achouPresencial = false;

        for (Evento e : listaEventos) {
            if (e instanceof EventoPresencial ep) {
                int ocupados = ep.getTotalInscritos();
                int maximo = ep.getCapacidadeMaxima();

                String cor = (ocupados >= maximo) ? "red" : "green";
                String status = (ocupados >= maximo) ? "<b>LOTADO</b>" : "Disponível";

                sb.append("<tr>")
                        .append("<td>").append(ep.getTitulo()).append("</td>")
                        .append("<td><font color='").append(cor).append("'>")
                        .append(ocupados).append("/").append(maximo)
                        .append(" (").append(status).append(")</font></td>")
                        .append("</tr>");
                achouPresencial = true;
            }
        }
        sb.append("</table>");

        if (!achouPresencial) {
            sb.append("<br><i>Nenhum evento presencial encontrado.</i>");
        }
        sb.append("</body></html>");
        JOptionPane.showMessageDialog(null, sb.toString(), "Relatório", JOptionPane.PLAIN_MESSAGE);
    }

    private static String lerData(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        while (true) {
            String input = JOptionPane.showInputDialog("<html>" + msg + "</html>");
            if (input == null) return null;

            if (input.trim().isEmpty()) {
                mostrarErro("A data não pode ser vazia.");
                continue;
            }

            try {
                sdf.parse(input);
                return input;
            } catch (ParseException e) {
                mostrarErro("Data inválida! Use o formato <b>dd/mm/aaaa</b>.<br>Ex: 25/12/2023");
            }
        }
    }

    private static String lerTexto(String msg) {
        String input = JOptionPane.showInputDialog("<html>" + msg + "</html>");
        if (input == null || input.trim().isEmpty()) return null;
        return input;
    }

    private static int lerInteiro(String msg) {
        while (true) {
            String str = JOptionPane.showInputDialog("<html>" + msg + "</html>");
            if (str == null) return -1;
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                mostrarErro("Digite apenas <b>números inteiros</b>!");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            String str = JOptionPane.showInputDialog("<html>" + msg + "</html>");
            if (str == null) return -1;
            try {
                return Double.parseDouble(str.replace(",", "."));
            } catch (NumberFormatException e) {
                mostrarErro("Valor inválido! Digite apenas números.");
            }
        }
    }


    private static void mostrarSucesso(String msg) {
        JOptionPane.showMessageDialog(null, "<html><font color='green'>" + msg + "</font></html>",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(null, "<html><font color='red'>" + msg + "</font></html>",
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private static void mostrarAlerta(String msg) {
        JOptionPane.showMessageDialog(null, "<html>" + msg + "</html>",
                "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}