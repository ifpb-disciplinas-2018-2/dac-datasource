package br.edu.ifpb.infra;

import br.edu.ifpb.domain.Cliente;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 06/11/2017, 09:00:18
 */
public class ClientesEmMemoria {

    public Iterator<Cliente> todosOsClientes() {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        clientes.add(Cliente.of(1, "123", "Kiko"));
        clientes.add(Cliente.of(2, "133", "Florinda"));
        return clientes.iterator();
    }
}
