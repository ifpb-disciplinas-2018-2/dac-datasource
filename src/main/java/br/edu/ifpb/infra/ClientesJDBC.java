package br.edu.ifpb.infra;

import br.edu.ifpb.domain.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 06/11/2017, 09:00:18
 */
public class ClientesJDBC {

    private Connection connection;

    public Iterator<Cliente> todosOsClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            iniciarConexao();
            try (Statement createStatement = this.connection.createStatement()) {
                ResultSet executeQuery = createStatement.executeQuery("Select * from cliente;");
                clientes.addAll(listarClientesDoResultSet(executeQuery));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientesJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            finalizarConexao();
        }

        return clientes.iterator();
    }

    private List<Cliente> listarClientesDoResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            String cpf = resultSet.getString("cpf");
            clientes.add(Cliente.of(id, cpf, nome));
        }
        return clientes;

    }

    private void iniciarConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            //jdbc:postgresql://localhost:5432/dac-cliente
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://host-banco:5432/dac-cliente",
                    "postgres", "12345");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ClientesJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void finalizarConexao() {
        if (this.connection == null) {
            return;
        }
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientesJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
