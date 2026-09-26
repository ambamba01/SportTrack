package views;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.List;

/**
 * This class permits to "fill" the star with yellow color when the user click on this. Moreover, it permits to get
 * the difficulty of an exercise
 */
public class StarSelectionFx {

    public StarSelectionFx(List<Button> starButtons, List<SVGPath> svgPaths) {
        starButtons.get(0).setOnAction(actionEvent -> changeDifficultySelection(1, svgPaths));
        starButtons.get(1).setOnAction(actionEvent -> changeDifficultySelection(2, svgPaths));
        starButtons.get(2).setOnAction(actionEvent -> changeDifficultySelection(3, svgPaths));
        starButtons.get(3).setOnAction(actionEvent -> changeDifficultySelection(4, svgPaths));
        starButtons.get(4).setOnAction(actionEvent -> changeDifficultySelection(5, svgPaths));
    }

    /**
     * Change the color of svg star buttons and update the difficulty value
     *
     * @param id id of the pressed star button
     */
    public void changeDifficultySelection(int id, List<SVGPath> svgs) {
        for (int i = 0; i < svgs.size(); i++) {
            if (i + 1 <= id) {
                svgs.get(i).setFill(Color.valueOf("#EFCE4A"));
            } else {
                svgs.get(i).setFill(Color.valueOf("#d6d6cd"));
            }

        }
    }

    public int getDifficulty(List<SVGPath> svgs) {
        int res = 0;
        for (SVGPath svg : svgs) {
            if (svg.getFill().equals(Color.valueOf("#EFCE4A"))) {
                res++;
            }
        }
        return res;
    }
}
