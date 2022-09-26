package br.com.fiap.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.model.Veiculo;
import br.com.fiap.util.ConnectionFactory;

public class VeiculoDao {

    public VeiculoDao() throws SQLException, IOException {
        Connection con = ConnectionFactory.getConnection();
    }

    public void inserir(Veiculo veiculo) throws SQLException, IOException {
        Connection con = ConnectionFactory.getConnection();
        String sql = "INSERT INTO DDD_CONC_TB_VEICULOS (id, marca, modelo, ano, preco, placa)"
        + "VALUES (SEQ_VEICULOS.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, veiculo.getMarca());
        stm.setString(2, veiculo.getModelo());
        stm.setInt(3, veiculo.getAno());
        stm.setDouble(4, veiculo.getPreco());
        stm.setString(5, veiculo.getPlaca());

        // ----- Uso com vulnerabilidade -----
        // O cliente pode digitar "); DROP TABLE"

        // String sql = String.format("INSERT INTO DDD_CONC_TB_VEICULOS (id, marca, modelo, ano, preco, placa)"
        // + "VALUES (SEQ_VEICULOS.nextval, '%s', '%s', %d, %d, '%s')",
        //     veiculo.getMarca(),
        //     veiculo.getModelo(),
        //     veiculo.getAno(),
        //     veiculo.getPreco(),
        //     veiculo.getPlaca()
        // );

        stm.execute();
        con.close();
    }

    public List<Veiculo> listarTodos() throws SQLException, IOException{
        Connection con = ConnectionFactory.getConnection();
        List<Veiculo> lista = new ArrayList<>();
        Statement stm = con.createStatement();
        String sql = "SELECT * FROM DDD_CONC_TB_VEICULOS";
        ResultSet resultado = stm.executeQuery(sql);

        while (resultado.next()) {
           var veiculo = new Veiculo(
               resultado.getString("marca"), 
               resultado.getString("modelo"), 
               resultado.getInt("ano"), 
               resultado.getDouble("preco"), 
               resultado.getString("placa")
           );

           lista.add(veiculo);
        }
        con.close();
       
        return lista;
    }
    
    public List<Veiculo> buscarPorMarca(String marca) throws SQLException, IOException{
        Connection con = ConnectionFactory.getConnection();
        List<Veiculo> lista = new ArrayList<>();
        Statement stm = con.createStatement();
        String sql = "SELECT * FROM DDD_CONC_TB_VEICULOS WHERE marca='" + marca + "'";
        ResultSet resultado = stm.executeQuery(sql);

        while (resultado.next()) {
            var veiculo = new Veiculo(
                resultado.getString("marca"), 
                resultado.getString("modelo"), 
                resultado.getInt("ano"), 
                resultado.getDouble("preco"), 
                resultado.getString("placa")
            );

            lista.add(veiculo);
        }
        con.close();

        return lista;
    }
}
