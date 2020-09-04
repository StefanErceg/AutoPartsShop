package view;

import controllers.AddCityToSupplierController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Supplier;

public class AddCityToSupplier {

    public void display(Supplier supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_city_to_supplier.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            stage.setResizable(false);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddCityToSupplierController controller = loader.getController();
            controller.initialize(stage, supplier);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}