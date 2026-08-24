import java.util.List;
import java.util.ArrayList;

/**
 * A Wheel holds a list of possible Symbols. At any time, one of them
 * is "current" (the one showing). Spinning picks a new current symbol
 * at random; placing sets it directly.
 *
 * @author  (tu nombre aqui)
 * @version 1.0
 */
public class Wheel
{
    private List<Symbol> symbols;
    private int currentIndex;
    private int slot;

    /**
     * Create an empty wheel located at the given horizontal slot
     * (used only to decide where its symbols are drawn on screen).
     *
     * @param slot the horizontal slot index of this wheel (0-based).
     */
    public Wheel(int slot)
    {
        this.slot = slot;
        symbols = new ArrayList<Symbol>();
        currentIndex = 0;
    }

    /**
     * Add a new symbol of the given color to this wheel.
     *
     * @param color a valid CSS color name.
     */
    public void addSymbol(String color)
    {
        Symbol s = new Symbol(color);
        s.moveHorizontal(slot * 80);
        s.moveVertical(60);
        symbols.add(s);
    }

    /**
     * Remove the first symbol of the given color from this wheel.
     *
     * @param color the color to remove.
     */
    public void delSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equals(color)) {
                symbols.remove(i);
                if (currentIndex >= symbols.size()) {
                    currentIndex = 0;
                }
                return;
            }
        }
    }

    /**
     * Place a specific symbol as the current one (without spinning).
     *
     * @param color the color of the symbol to show.
     */
    public void place(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equals(color)) {
                currentIndex = i;
                return;
            }
        }
    }

    /**
     * Spin this wheel: pick a random symbol among the available ones.
     */
    public void spin()
    {
        if (!symbols.isEmpty()) {
            currentIndex = (int) (Math.random() * symbols.size());
        }
    }

    /**
     * Returns the symbol currently showing, or null if this wheel
     * has no symbols.
     *
     * @return the current Symbol.
     */
    public Symbol current()
    {
        if (symbols.isEmpty()) {
            return null;
        }
        return symbols.get(currentIndex);
    }

    /**
     * Returns the colors of all symbols in this wheel, in order.
     *
     * @return an array of color names.
     */
    public String[] symbols()
    {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    /**
     * Returns how many symbols this wheel currently has.
     *
     * @return the number of symbols.
     */
    public int size()
    {
        return symbols.size();
    }

    /**
     * Make the current symbol of this wheel visible on screen.
     */
    public void makeVisible()
    {
        if (current() != null) {
            current().makeVisible();
        }
    }

    /**
     * Make every symbol of this wheel invisible.
     */
    public void makeInvisible()
    {
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
    }
}
