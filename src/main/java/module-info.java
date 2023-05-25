module tolano.catalagolibros {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens tolano.catalagolibros to javafx.fxml;
    exports tolano.catalagolibros;
}