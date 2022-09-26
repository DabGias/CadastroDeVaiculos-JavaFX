package br.com.fiap.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dao.VeiculoDao;
import br.com.fiap.model.Veiculo;
import br.com.fiap.model.Cliente;

public class PrimaryController implements Initializable {
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

    @FXML
    private TextField txtFieldClienteNome;
    @FXML
    private TextField txtFieldClienteEmail;
    @FXML
    private TextField txtFieldClienteTel;
    @FXML
    private TableView<Cliente> tbViewCliente;
    @FXML
    private TableColumn<Cliente, String> tbColumnNome;
    @FXML
    private TableColumn<Cliente, String> tbColumnEmail;
    @FXML
    private TableColumn<Cliente, String> tbColumnTel;

    private VeiculoDao veiculoDao;

    public PrimaryController() throws IOException {
        try {
            veiculoDao = new VeiculoDao();
        } catch (SQLException e) {
            alertaErro("Erro de conexao com BD " + e.getMessage());
        }
    }

    public void salvar() throws IOException {
        try {
            veiculoDao.inserir(carregaVeiculo());
            alertaInfo("Veículo cadastrado com sucesso!");
        } catch (SQLException e) {
            alertaErro("Erro de SQL: " + e.getMessage());
        }

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

    private Cliente carregaCliente() {
        String nome = txtFieldClienteNome.getText();
        String email = txtFieldClienteEmail.getText();
        String tel = txtFieldClienteTel.getText();

        return new Cliente(nome, email, tel);
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

    public void ordenarPorPreco() throws IOException {
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

    public void ordenarPorAno() throws IOException {
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

    public void mostrarTodos() throws IOException {
        List<Veiculo> listaVeiculos;
        
        try {
            listaVeiculos = veiculoDao.listarTodos();
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de SQL " + e.getMessage());
        }
    }

    public void filtraPorMarca(String marca) throws IOException {
        List<Veiculo> listaVeiculos;
        
        try {
            listaVeiculos = veiculoDao.buscarPorMarca(marca);
            listView.getItems().clear();
            listView.getItems().addAll(listaVeiculos);
        } catch (SQLException e) {
            alertaErro("Erro de SQL " + e.getMessage());
        }
    }

    public void salvarCliente() throws IOException {
        try {
            Cliente cliente = carregaCliente();
            new ClienteDao().inserir(cliente);

            tbViewCliente.getItems().add(cliente);
            alertaInfo("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            alertaErro("Erro ao cadastrar cliente. " + e.getMessage());
        } catch (IOException e) {
            alertaErro("Erro ao carregar propriedades. Verifique os dados em application.properties. " + e.getMessage());
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbColumnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }
}
