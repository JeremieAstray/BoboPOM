package boboPOM.code.counters;

import boboPOM.config.Config;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

import static boboPOM.config.Config.LINE_MAX;
import static boboPOM.config.Config.LINE_MIN;

/**
 * @author yorlbgy
 */
public class LineCounter extends Parent {

    private int lines;
    private ContentPane content;

    public LineCounter(boolean p1) {
        content = new ContentPane();
        getChildren().add(content);
        if (p1) {
            this.setTranslateX(-52);
            this.setTranslateY(243);
        } else {
            this.setTranslateX(52);
            this.setTranslateY(243);
        }
        setLines(0);
    }

    public int useLines() {
        int addLines = lines;
        setLines(LINE_MIN);
        return addLines;
    }

    public void addLines(int add) {
        int newLines = lines + add;
        if (newLines <= LINE_MAX) {
            setLines(newLines);
        } else {
            setLines(LINE_MAX);
        }
    }

    public int getLines() {
        return lines;
    }

    private void setLines(int lines) {
        this.lines = lines;
        content.setLines(lines);
    }

    public ContentPane getContent() {
        return content;
    }

    //network use
    public void setNowLines(int lines) {
        this.content.setLines(lines);
    }

    private class ContentPane extends StackPane {

        private ImageView t, c;
        private ArrayList<Image> images;

        public ContentPane() {
            images = Config.getNumbers().get(3);
            t = new ImageView(images.get(0));
            t.setTranslateX(-images.get(0).getWidth());
            t.setVisible(false);
            c = new ImageView(images.get(0));
            this.getChildren().addAll(t, c);
        }

        public void setLines(int lines) {
            int T = lines % 100 / 10;
            int C = lines % 10;
            t.setImage(images.get(T));
            t.setVisible(T > 0);
            c.setImage(images.get(C));
        }

    }
}
