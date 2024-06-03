module com.orthoarkansas.reconciler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.orthoarkansas.reconciler to javafx.fxml;
    exports com.orthoarkansas.reconciler;
}