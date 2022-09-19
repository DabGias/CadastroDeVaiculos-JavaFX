package br.com.fiap.controller;

import java.sql.SQLException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import br.com.fiap.dao.VeiculoDao;
import br.com.fiap.model.Veiculo;

public class PrimaryController {
    @FXML 
    private TextField txtFieldMarca;
    @FXML 
    private TextField txtFieldModelo;
    @FXML 
    private TextField txtFieldPreco;
    @FXML 
    private TextField txtFieldAno;
    @FXML 
    private TextField txtFieldPlaca;
    @FXML 
    private ListView<Veiculo> listView;

    private VeiculoDao veiculoDao;

    public PrimaryController() {
        try {
            veiculoDao = new VeiculoDao();
        } catch (SQLException e) {
            alertaErro("Erro de conexao com BD " + e.getMessage());
        }
    }

    public void salvar() {
        try {
            veiculoDao.inserir(carregaVeiculo());
        } catch (SQLException e) {
            alertaErro("Erro de SQL: " + e.getMessage());
        }

        alertaInfo("Veículo cadastrado com sucesso!");

        limpaTxtField();
    }

    private Veiculo carregaVeiculo() {
        String marca = txtFieldMarca.getText();
        String modelo = txtFieldModelo.getText();
        double preco = Double.valueOf(txtFieldPreco.getText());
        int ano = Integer.valueOf(txtFieldAno.getText());
        String placa = txtFieldPlaca.getText();

        return new Veiculo(marca, modelo, ano, preco, placa);
    }

    public void limpaTxtField() {
        txtFieldMarca.setText("");
        txtFieldModelo.setText("");
        txtFieldPreco.setText("");
        txtFieldAno.setText("");
        txtFieldPlaca.setText("");
    }

    public void alertaInfo(String msg) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setContentText(msg);
        alerta.show();
    }

    public void alertaErro(String msg) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setContentText(msg);
        alerta.show();
    }

    public void ordenarPorPreco() {
        List<Veiculo> listaVeiculos;

        try {
            listaVeiculos = veiculoDao.listarTodos();
            listaVeiculos.sort((o1, o2) -> Double.compare(o1.getPreco(), o2.getPreco()));
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de conexao com BD " + e.getMessage());
        }
    }

    public void ordenarPorAno() {
        List<Veiculo> listaVeiculos;

        try {
            listaVeiculos = veiculoDao.listarTodos();
            listaVeiculos.sort((o1, o2) -> Integer.compare(o1.getAno(), o2.getAno()));
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de conexao com BD " + e.getMessage());
        }
    }

    public void mostrarTodos() {
        List<Veiculo> listaVeiculos;
        
        try {
            listaVeiculos = veiculoDao.listarTodos();
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de SQL " + e.getMessage());
        }
    }

    public void filtraPorMarca(String marca) {
        List<Veiculo> listaVeiculos;
        
        try {
            listaVeiculos = veiculoDao.buscarPorMarca(marca);
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de SQL " + e.getMessage());
        }
    }
}
