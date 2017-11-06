package br.edu.ifpb.domain;

import br.edu.ifpb.infra.ClientesEmMemoria;
import br.edu.ifpb.infra.ClientesJDBC;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo Job
 */
@WebServlet(name = "ControladorDeClientes", urlPatterns = {"/clientes"})
public class ControladorDeClientes extends HttpServlet {

    private final ClientesJDBC clientes = new ClientesJDBC();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorDeClientes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listagem de Clientes</h1>");
            imprimirTodosOsClientes(out);
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    private void imprimirTodosOsClientes(final PrintWriter out) {
        clientes
                .todosOsClientes()
                .forEachRemaining(
                        c -> out.println(c.getNome())
                //                        new Consumer<Cliente>() {
                //                    @Override
                //                    public void accept(Cliente c) {
                //                        out.println(c.getNome());
                //                    }
                //                }
                );
    }

}
