import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad del ciclo 4: tipos de ruedas y de simbolos. Todas
 * corren en modo invisible.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineC4Test
{
    /**
     * addWheel(type,pos) SI DEBE agregar ruedas de todos los tipos.
     */
    @Test
    public void shouldAddWheelsOfEveryType()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("normal", 1);
        m.addWheel("lefty", 2);
        m.addWheel("rebel", 3);
        m.addWheel("reverse", 4);

        assertTrue(m.ok());
        assertEquals(4, m.configuration().length);
    }

    /**
     * addWheel(type,pos) NO debe agregar una rueda de un tipo que no
     * existe.
     */
    @Test
    public void shouldNotAddWheelOfUnknownType()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("magic", 1);

        assertFalse(m.ok());
        assertEquals(0, m.configuration().length);
    }

    /**
     * addSymbol(type,pos,color) SI DEBE agregar simbolos de todos los
     * tipos.
     */
    @Test
    public void shouldAddSymbolsOfEveryType()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol("normal", 1, "red");
        m.addSymbol("ephemeral", 1, "blue");
        m.addSymbol("shy", 1, "green");

        assertTrue(m.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, m.symbols());
    }

    /**
     * addSymbol(type,pos,color) NO debe agregar un simbolo de un tipo
     * que no existe.
     */
    @Test
    public void shouldNotAddSymbolOfUnknownType()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol("golden", 1, "red");

        assertFalse(m.ok());
        assertEquals(0, m.symbols().length);
    }

    /**
     * Una rueda lefty SI DEBE copiar el color de la rueda de su
     * izquierda al girar.
     */
    @Test
    public void leftyShouldCopyTheWheelOnItsLeft()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("normal", 1);
        m.addWheel("lefty", 2);
        for (String c : new String[]{"red", "blue", "green"}) {
            m.addSymbol(1, c);
            m.addSymbol(2, c);
        }
        m.placeSymbol(1, "green");

        m.spin(2);

        assertEquals("green", m.configuration()[1]);
    }

    /**
     * Una rueda lefty sin rueda a la izquierda NO debe quedarse quieta:
     * gira como una normal.
     */
    @Test
    public void leftyShouldNotStayStillWithoutLeftWheel()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("lefty", 1);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");

        m.spin(1, 1);

        assertEquals("blue", m.configuration()[0]);
    }

    /**
     * Una rueda rebel NO debe dejarse bloquear, intercambiar ni
     * eliminar.
     */
    @Test
    public void rebelShouldNotBeLockedSwappedOrDeleted()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("rebel", 1);
        m.addWheel("normal", 2);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");

        m.lock(1);
        assertFalse(m.ok());
        m.swap(1, 2);
        assertFalse(m.ok());
        m.delWheel(1);
        assertFalse(m.ok());

        assertArrayEquals(new String[]{"red", "blue"}, m.configuration());
    }

    /**
     * Una rueda reverse SI DEBE rotar al reves: del primer simbolo pasa
     * al ultimo.
     */
    @Test
    public void reverseShouldRotateBackwards()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("reverse", 1);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");
        m.addSymbol(1, "green");

        m.spin(1, 1);

        assertEquals("green", m.configuration()[0]);
    }

    /**
     * Un simbolo ephemeral SI DEBE encogerse cada vez que su rueda gira
     * hasta quedar como un punto, y de ahi no bajar.
     */
    @Test
    public void ephemeralShouldShrinkUntilItIsADot()
    {
        Wheel w = new Wheel(0);
        Ephemeral e = new Ephemeral("red");
        w.addSymbol(e);
        w.addSymbol(new Symbol("blue"));

        w.stepOnce(null);
        assertEquals(26, e.getSize());
        for (int i = 0; i < 20; i++) {
            w.stepOnce(null);
        }
        assertEquals(6, e.getSize());
    }

    /**
     * Un simbolo shy SI DEBE esconderse la primera vez que lo
     * seleccionan y volver a aparecer la siguiente.
     */
    @Test
    public void shyShouldHideAndShowEachTimeItIsSelected()
    {
        Wheel w = new Wheel(0);
        w.addSymbol(new Symbol("red"));
        Shy s = new Shy("blue");
        w.addSymbol(s);

        w.stepOnce(null);
        assertTrue(s.isHidden());
        w.stepOnce(null);
        w.stepOnce(null);
        assertFalse(s.isHidden());
    }
}
