package view;

import controllers.AddEditCityController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.City;

public class AddEditCity {

    public void display(City city) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_edit_city.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            stage.setResizable(false);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddEditCityController controller = loader.getController();
            controller.initialize(stage, city);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
