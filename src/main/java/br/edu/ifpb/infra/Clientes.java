package br.edu.ifpb.infra;

import br.edu.ifpb.domain.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 06/11/2017, 09:00:18
 */
@Stateless
public class Clientes {

//    private Connection connection;
    @Resource(name = "java:app/jdbc/exemplo")
    private DataSource dataSource;

    public Iterator<Cliente> todosOsClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement createStatement = connection.createStatement()) {
                ResultSet executeQuery = createStatement.executeQuery("Select * from cliente;");
                clientes.addAll(listarClientesDoResultSet(executeQuery));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
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
 
}
