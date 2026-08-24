import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 * SlotMachine simulates a simple slot machine made of several Wheels,
 * each holding several colored Symbols. It can be spun, inspected,
 * and shown or hidden on screen.
 *
 * @author  (tu nombre aqui)
 * @version 1.0
 */
public class SlotMachine
{
    private List<Wheel> wheels;
    private boolean visible;
    private boolean lastOk;

    /**
     * Create a new, empty slot machine (no wheels yet), hidden.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = false;
        lastOk = true;
    }

    /**
     * Add a new empty wheel at the given position.
     * Positions start at 1. Out-of-range values are clamped.
     *
     * @param pos the desired position of the new wheel.
     */
    public void addWheel(int pos)
    {
        int p = adjustPos(pos, wheels.size() + 1);
        wheels.add(p - 1, new Wheel(p - 1));
        lastOk = true;
    }

    /**
     * Remove the wheel at the given position.
     * Positions start at 1. Out-of-range values are clamped.
     *
     * @param pos the position of the wheel to remove.
     */
    public void delWheel(int pos)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para eliminar.");
            return;
        }
        int p = adjustPos(pos, wheels.size());
        wheels.remove(p - 1);
        lastOk = true;
    }

    /**
     * Add a new symbol of the given color to the wheel at the given
     * position. Positions start at 1. Out-of-range values are clamped.
     *
     * @param pos   the position of the wheel that receives the symbol.
     * @param color a valid CSS color name.
     */
    public void addSymbol(int pos, String color)
    {
        if (wheels.isEmpty()) {
            fail("Primero debe crear una rueda.");
            return;
        }
        int p = adjustPos(pos, wheels.size());
        wheels.get(p - 1).addSymbol(color);
        lastOk = true;
    }

    /**
     * Remove the given symbol color from every wheel that has it.
     *
     * @param symbol the color of the symbol to remove.
     */
    public void delSymbol(String symbol)
    {
        for (Wheel w : wheels) {
            w.delSymbol(symbol);
        }
        lastOk = true;
    }

    /**
     * Place a specific symbol as the current one in the given wheel,
     * without spinning. Positions start at 1.
     *
     * @param wheel  the position of the wheel to modify.
     * @param symbol the color of the symbol to show.
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas configuradas.");
            return;
        }
        int p = adjustPos(wheel, wheels.size());
        wheels.get(p - 1).place(symbol);
        lastOk = true;
    }

    /**
     * Spin a single wheel, picking a random symbol among its own.
     * Positions start at 1.
     *
     * @param wheel the position of the wheel to spin.
     */
    public void spin(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para girar.");
            return;
        }
        int p = adjustPos(wheel, wheels.size());
        wheels.get(p - 1).spin();
        if (visible) {
            wheels.get(p - 1).makeVisible();
        }
        lastOk = true;
    }

    /**
     * Spin every wheel of the machine.
     */
    public void spin()
    {
        for (int i = 1; i <= wheels.size(); i++) {
            spin(i);
        }
    }

    /**
     * Returns the colors of every symbol in every wheel of the
     * machine, in order (wheel by wheel, and within each wheel in
     * the order its symbols were added).
     *
     * @return an array with all the machine's symbol colors.
     */
    public String[] symbols()
    {
        List<String> all = new ArrayList<String>();
        for (Wheel w : wheels) {
            for (String color : w.symbols()) {
                all.add(color);
            }
        }
        return all.toArray(new String[0]);
    }

    /**
     * Returns how many different colors exist among the machine's
     * symbols (see {@link #symbols()}).
     *
     * @return the number of distinct colors.
     */
    public int distinctSymbols()
    {
        String[] colors = symbols();
        Set<String> unique = new HashSet<String>();
        for (String c : colors) {
            unique.add(c);
        }
        return unique.size();
    }

    /**
     * Returns the colors currently visible on every wheel, from left
     * to right.
     *
     * @return an array with one color per wheel.
     */
    public String[] configuration()
    {
        String[] result = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            Symbol s = wheels.get(i).current();
            result[i] = (s != null) ? s.getColor() : null;
        }
        return result;
    }

    /**
     * Checks whether the current configuration is a winning one
     * (every visible wheel shows the same color).
     *
     * @return true if the configuration is a jackpot.
     */
    public boolean isJackpot()
    {
        String[] config = configuration();
        if (config.length == 0) {
            return false;
        }
        for (String c : config) {
            if (c == null || !c.equals(config[0])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Make the whole machine (all wheels and their current symbols)
     * visible on screen.
     */
    public void makeVisible()
    {
        visible = true;
        for (Wheel w : wheels) {
            w.makeVisible();
        }
    }

    /**
     * Hide the whole machine from screen.
     */
    public void makeInvisible()
    {
        for (Wheel w : wheels) {
            w.makeInvisible();
        }
        visible = false;
    }

    /**
     * Terminate the simulator, hiding it first.
     */
    public void exit()
    {
        makeInvisible();
    }

    /**
     * Reports whether the last operation performed on this machine
     * completed successfully.
     *
     * @return true if the last operation succeeded.
     */
    public boolean ok()
    {
        return lastOk;
    }

    /**
     * Clamp a 1-based position into the valid range [1, max].
     *
     * @param pos the requested position.
     * @param max the highest valid position.
     * @return the adjusted position.
     */
    private int adjustPos(int pos, int max)
    {
        if (max <= 0) {
            return 1;
        }
        if (pos < 1) {
            return 1;
        }
        if (pos > max) {
            return max;
        }
        return pos;
    }

    /**
     * Record a failed operation and, if the machine is visible,
     * show the user a message explaining what went wrong.
     *
     * @param message the explanation to show the user.
     */
    private void fail(String message)
    {
        lastOk = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
