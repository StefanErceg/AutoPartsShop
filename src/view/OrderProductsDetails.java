package view;

import controllers.OrderProductsController;
import controllers.OrderProductsDetailsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DTO.Product;

public class OrderProductsDetails {
    public void display(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/order_products_details.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Auto parts shop");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            OrderProductsDetailsController controller = loader.getController();
            controller.initialize(stage, product);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
