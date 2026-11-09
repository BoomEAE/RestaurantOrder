module com.mycompany.restaurantordermvclab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.restaurantordermvclab to javafx.fxml;
    exports com.mycompany.restaurantordermvclab;
}
