package view;

import controllers.AddEditSupplierController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Supplier;
import model.DTO.SupplierCity;

public class AddEditSupplier {

    public void display(SupplierCity supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add_edit_supplier.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            stage.setResizable(false);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AddEditSupplierController controller = loader.getController();
            controller.initialize(stage, supplier);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
