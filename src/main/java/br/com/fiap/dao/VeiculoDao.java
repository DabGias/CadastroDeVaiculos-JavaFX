package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.model.Veiculo;

public class VeiculoDao {
    private static final String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String user = "user";
    private static final String password = "senha";
    private Connection con;

    public VeiculoDao() throws SQLException {
        con = DriverManager.getConnection(url, user, password);
    }

    public void inserir(Veiculo veiculo) throws SQLException {
        con = DriverManager.getConnection(url, user, password);
        String sql = "INSERT INTO DDD_CONC_TB_VEICULOS (id, marca, modelo, ano, preco, placa)"
        + "VALUES (SEQ_VEICULOS.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, veiculo.getMarca());
        stm.setString(2, veiculo.getModelo());
        stm.setInt(3, veiculo.getAno());
        stm.setDouble(4, veiculo.getPreco());
        stm.setString(5, veiculo.getPlaca());

        // ----- Uso com vulnerabilidade -----
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

    public List<Veiculo> listarTodos() throws SQLException{
        con = DriverManager.getConnection(url, user, password);
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
    
    public List<Veiculo> buscarPorMarca(String marca) throws SQLException{
        con = DriverManager.getConnection(url, user, password);
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
