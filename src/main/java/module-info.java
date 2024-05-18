module com.example.labyrinth {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.labyrinth to javafx.fxml;
    exports com.example.labyrinth;
}