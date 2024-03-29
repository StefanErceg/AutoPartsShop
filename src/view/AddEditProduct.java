package view;

import controllers.AddEditProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Product;
import model.DTO.VehicleProduct;

public class AddEditProduct {
    public void display(VehicleProduct vehicleProduct) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_edit_product.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            stage.setResizable(false);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddEditProductController controller = loader.getController();
            controller.initialize(stage, vehicleProduct);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
