package view;

import controllers.AddEditCountryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Country;

public class AddEditCountry {

    public void display(Country country) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_edit_country.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            AddEditCountryController controller = loader.getController();
            controller.initialize(stage, country);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
