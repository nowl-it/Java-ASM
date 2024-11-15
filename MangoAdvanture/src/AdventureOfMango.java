
// AdventureOfMango.java
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.Random;

public class AdventureOfMango {
    private Screen screen;
    private int mangoPosition;
    private int bananaPosition;
    private int score;

    public AdventureOfMango(Screen screen) {
        this.screen = screen;
        this.mangoPosition = 5;
        this.bananaPosition = 20;
        this.score = 0;
    }

    public void startGame() throws IOException, InterruptedException {
        TextGraphics graphics = screen.newTextGraphics();
        boolean running = true;

        while (running) {
            // Xóa màn hình và cập nhật các đối tượng
            screen.clear();
            drawBackground(graphics);
            drawMango(graphics);
            drawBanana(graphics);

            screen.refresh();

            // Nhận phím từ người chơi
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') {
                    if (mangoPosition == bananaPosition) {
                        score++;
                        generateNewBanana();
                    }
                } else if (keyStroke.getKeyType() == KeyType.Escape) {
                    running = false;
                }
            }

            Thread.sleep(300); // Tạm dừng để hiển thị hành động

            // Di chuyển Mango và kiểm tra va chạm với chuối
            moveMango();
            if (mangoPosition == bananaPosition) {
                score++;
                generateNewBanana();
            }
        }

        screen.stopScreen();
    }

    private void drawBackground(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.CYAN);
        graphics.putString(2, 1, "Cuộc phiêu lưu của Mango");
        graphics.putString(2, 2, "Điểm số: " + score);
        graphics.putString(2, 3, "Nhấn SPACE để thu thập chuối, ESC để thoát");
    }

    private void drawMango(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(mangoPosition, 5), "M");
        graphics.disableModifiers(SGR.BOLD);
    }

    private void drawBanana(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.GREEN);
        graphics.putString(new TerminalPosition(bananaPosition, 5), "B");
    }

    private void moveMango() {
        mangoPosition++;
        if (mangoPosition >= 30) {
            mangoPosition = 5; // Quay lại vị trí ban đầu
        }
    }

    private void generateNewBanana() {
        Random random = new Random();
        bananaPosition = 5 + random.nextInt(25); // Tạo vị trí ngẫu nhiên cho chuối
    }
}
