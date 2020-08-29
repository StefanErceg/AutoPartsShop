package view;

import controllers.DashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard {
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            DashboardController controller = loader.getController();
            controller.initialize(stage);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
