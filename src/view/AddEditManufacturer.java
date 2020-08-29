package view;

import controllers.AddEditManufacturerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Manufacturer;

public class AddEditManufacturer {

    public void display(Manufacturer manufacturer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_edit_manufacturer.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            AddEditManufacturerController controller = loader.getController();
            controller.initialize(stage, manufacturer);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
