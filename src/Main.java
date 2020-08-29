import javafx.application.Application;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DAO.MySQL.MySQLUtilities;
import model.DTO.Category;
import model.DTO.Supplier;
import view.Dashboard;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new Dashboard().display();
    }
}
