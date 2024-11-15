import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class App {
    public static void main(String[] args) {
        try {
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
            AdventureOfMango game = new AdventureOfMango(screen);
            game.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
