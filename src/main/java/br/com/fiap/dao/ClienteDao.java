package br.com.fiap.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.fiap.model.Cliente;
import br.com.fiap.util.ConnectionFactory;

public class ClienteDao {

    public void inserir(Cliente cliente) throws SQLException, IOException {
        Connection con = ConnectionFactory.getConnection();
        var stm = con.prepareStatement("INSERT INTO DDD_CONC_TB_CLIENTES (ID, NOME, EMAIL, TELEFONE) VALUES (SEQ_CLIENTE.nextVal, ?, ?, ?)");

        stm.setString(1, cliente.getNome());
        stm.setString(2, cliente.getEmail());
        stm.setString(3, cliente.getTel());

        stm.execute();
        con.close();
    }
}
