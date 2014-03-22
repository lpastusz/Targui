package targui;
import domain.DomainController;
import ui.ConsoleApplication;

/**
 *
 * @author Lukas.Pasta
 */
public class Targui {
    
    public static void main(String[] args) {
        DomainController controller = new DomainController();
        ConsoleApplication app = new ConsoleApplication(controller);
        app.Start();
    }
    
}
