package view;

import controllers.ProductsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Products {
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/products.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            ProductsController controller = loader.getController();
            controller.initialize(stage);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

