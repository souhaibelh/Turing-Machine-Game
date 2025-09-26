module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo1 to javafx.fxml;
    exports ViewFX;
    opens ViewFX to javafx.fxml;
    exports ModelUtils;
    opens ModelUtils to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
    exports Constants;
    opens Constants to javafx.fxml;
    exports GeneralMethods;
    opens GeneralMethods to javafx.fxml;
    exports Model.commands;
    opens Model.commands to javafx.fxml;
    exports Model.enums;
    opens Model.enums to javafx.fxml;
    exports Applications;
    opens Applications to javafx.fxml;
}