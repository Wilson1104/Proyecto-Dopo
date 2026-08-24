/**
 * A Symbol is a colored figure used inside a Wheel of a SlotMachine.
 * It extends Circle to reuse its drawing behavior (make visible,
 * move, change color), and additionally remembers its own color,
 * since Circle does not provide a way to read it back.
 *
 * @author  (tu nombre aqui)
 * @version 1.0
 */
public class Symbol extends Circle
{
    private String currentColor;

    /**
     * Create a new Symbol with the given color.
     *
     * @param color a valid CSS color name (e.g. "red", "blue", "green").
     */
    public Symbol(String color)
    {
        super();
        currentColor = color;
        changeColor(color);
    }

    /**
     * Returns the current color of this symbol.
     *
     * @return the color of this symbol.
     */
    public String getColor()
    {
        return currentColor;
    }

    /**
     * Changes the color of this symbol, keeping track of it locally
     * (Circle only knows how to draw the color, not report it back).
     *
     * @param newColor a valid CSS color name.
     */
    @Override
    public void changeColor(String newColor)
    {
        super.changeColor(newColor);
        currentColor = newColor;
    }
}
