package br.com.fiap;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class PrimaryController {
    @FXML private TextField txtFieldMarca;
    @FXML private TextField txtFieldModelo;
    @FXML private TextField txtFieldPreco;
    @FXML private TextField txtFieldAno;
    @FXML private TextField txtFieldPlaca;
    @FXML private ListView<Veiculo> listView;

    private List<Veiculo> listaVeiculos = new ArrayList<>();

    public void salvar() {
        String marca = txtFieldMarca.getText();
        String modelo = txtFieldModelo.getText();
        double preco = Double.valueOf(txtFieldPreco.getText());
        int ano = Integer.valueOf(txtFieldAno.getText());
        String placa = txtFieldPlaca.getText();

        Veiculo veiculo = new Veiculo(marca, modelo, ano, preco, placa);
        listaVeiculos.add(veiculo);

        alertaInfo("Veículo cadastrado com sucesso!");

        limpaTxtField();

        listView.getItems().addAll(listaVeiculos);
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
}
