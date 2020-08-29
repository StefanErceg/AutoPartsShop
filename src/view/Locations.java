package view;

import controllers.LocationsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Locations {
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/locations.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            stage.setResizable(false);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            LocationsController controller = loader.getController();
            controller.initialize(stage);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
