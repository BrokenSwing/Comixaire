open module comixaire {

    requires org.postgresql.jdbc;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires org.controlsfx.controls;

    exports com.github.brokenswing.comixaire;
    // opens com.github.brokenswing.comixaire to javafx.fxml;
    // opens com.github.brokenswing.comixaire.controller to javafx.fxml;

}