package targui;
import domain.DomainController;
import javafx.application.Application;
import static javafx.application.Application.*;
import javafx.stage.Stage;
import gui.GuiApplication;

/**
 *
 * @author Lukas.Pasta
 */
public class Targui extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        DomainController controller = new DomainController();
        GuiApplication app = new GuiApplication(controller);
        app.start(primaryStage);
    }
    
}
