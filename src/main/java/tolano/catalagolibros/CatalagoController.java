package tolano.catalagolibros;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class CatalagoController {

    @FXML
    private void botonInsert() {
        if(estanCamposVacios()){
            String query = "insert into libros values("+campoId.getText()+",'"+campoTitulo.getText()+"','"+campoAutor.getText()+"',"+campoAnio.getText()+","+campoPaginas.getText()+")";
            ejecutarQuery(query);
            mostrarLibros();
        }else{
            mostrarAlertInfo(0, "campos vacios");
        }
    }

    @FXML
    private void botonUpdate() {
        String query = "UPDATE libros SET titulo='"+campoTitulo.getText()+"',autor='"+campoAutor.getText()+"',anio="+campoAnio.getText()+",paginas="+campoPaginas.getText()+" WHERE id="+campoId.getText()+"";
        ejecutarQuery(query);
        mostrarLibros();
    }

    @FXML
    private void botonDelete() {
        String query = "DELETE FROM libros WHERE id="+campoId.getText()+"";
        ejecutarQuery(query);
        mostrarLibros();
    }

    private void mostrarAlertInfo(int res, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Mensaje del sistema");
        String text = "";
        if(res==1){
            text = "operacion exitosa";
        }else{
            text = "operacion no exitosa  >> msg";
        }
        alert.setContentText(text);
        limpiarCampos();
        alert.showAndWait();

    }

    private boolean estanCamposVacios(){
        if(campoId.getText().length()>0 && campoTitulo.getText().length()>0 && campoAutor.getText().length()>0 && campoAnio.getText().length()>0 && campoPaginas.getText().length()>0){
            return true;
        }else{
            return false;
        }
    }

    private void limpiarCampos(){
        campoId.setText("");
        campoTitulo.setText("");
        campoAutor.setText("");
        campoAnio.setText("");
        campoPaginas.setText("");
    }

    public void ejecutarQuery(String query) {
        Connection con = conectarBD();
        Statement sta;
        try {
            sta = con.createStatement();
            int res = sta.executeUpdate(query);
            mostrarAlertInfo(res, "error en la consulta a la bd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection conectarBD() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/catalago","root", "123456");
            return con;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Libro> obtenerListadoLibros(){
        ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
        Connection connection = conectarBD();
        String query = "SELECT * FROM libros;";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Libro libro;
            while( rs.next() ) {
                libro = new Libro(rs.getInt("id"),rs.getString("titulo"),rs.getString("autor"),rs.getInt("anio"),rs.getInt("paginas"));
                listaLibros.add(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaLibros;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void mostrarLibros() {
        ObservableList<Libro> list = obtenerListadoLibros();
        columnaId.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("id"));
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<Libro,String>("titulo"));
        columnaAutor.setCellValueFactory(new PropertyValueFactory<Libro,String>("autor"));
        columnaAnios.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("anio"));
        columnaPaginas.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("paginas"));
        tablaLibros.setItems(list);
    }

    public void initialize() {
        mostrarLibros();
        tablaLibros.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                Libro libro = tablaLibros.getSelectionModel().getSelectedItem();
                campoId.setText(String.valueOf(libro.getId()));
                campoTitulo.setText(String.valueOf(libro.getTitulo()));
                campoAutor.setText(String.valueOf(libro.getAutor()));
                campoAnio.setText(String.valueOf(libro.getAnio()));
                campoPaginas.setText(String.valueOf(libro.getPaginas()));
            }
        });
    }

    @FXML
    private TextField campoId;
    @FXML
    private TextField campoTitulo;
    @FXML
    private TextField campoAutor;
    @FXML
    private TextField campoAnio;
    @FXML
    private TextField campoPaginas;
    @FXML
    private TableView<Libro> tablaLibros;
    @FXML
    private TableColumn<Libro, Integer> columnaId;
    @FXML
    private TableColumn<Libro, String> columnaTitulo;
    @FXML
    private TableColumn<Libro, String> columnaAutor;
    @FXML
    private TableColumn<Libro, Integer> columnaAnios;
    @FXML
    private TableColumn<Libro, Integer> columnaPaginas;
}
