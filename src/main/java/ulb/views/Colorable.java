package ulb.views;

public interface Colorable {
    /**
     * Applies a stylesheet to the parent AnchorPane based on the current mode.
     * Removes the light mode stylesheet and applies the dark mode stylesheet if darkMode is true,
     * and vice versa.
     *
     * @param darkMode Indicates whether the dark mode stylesheet should be applied.
     */
    void changeMode(boolean darkMode) ;
}
