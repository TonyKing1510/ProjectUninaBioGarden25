module it.unina {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires MaterialFX;
    requires java.sql;


    opens it.unina to javafx.fxml;
    exports it.unina.controller;
    opens it.unina.controller to javafx.fxml;
    exports it.unina.gui;
    opens it.unina.gui to javafx.fxml;
}