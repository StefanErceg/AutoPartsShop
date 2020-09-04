import javafx.application.Application;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DAO.MySQL.MySQLUtilities;
import model.DTO.*;
import view.Dashboard;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new Dashboard().display();
    }
}

