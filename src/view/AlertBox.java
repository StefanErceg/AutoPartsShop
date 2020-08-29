package view;

import controllers.AlertBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public  void display(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/alert_box.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            AlertBoxController controller = loader.getController();
            controller.initialize(stage, message);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
