import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad para las nuevas funcionalidades del ciclo 2 de
 * SlotMachine: swap, lock, unlock, spin(wheel,steps) y
 * spin(String[]). Todas las pruebas corren en modo invisible, como
 * se exige.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineC2Test
{
    // ---------- swap ----------

    /**
     * swap SI DEBE intercambiar el simbolo visible de dos ruedas.
     */
    @Test
    public void shouldSwapTwoWheels()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");

        m.swap(1, 2);

        assertArrayEquals(new String[]{"blue", "red"}, m.configuration());
        assertTrue(m.ok());
    }

    /**
     * swap NO debe tener exito si la maquina tiene menos de dos
     * ruedas.
     */
    @Test
    public void shouldNotSwapWithLessThanTwoWheels()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);

        m.swap(1, 1);

        assertFalse(m.ok());
    }

    // ---------- lock / unlock ----------

    /**
     * Una rueda fijada (locked) SI DEBE ignorar las solicitudes de
     * giro.
     */
    @Test
    public void lockedWheelShouldNotChangeOnSpin()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");
        m.addSymbol(1, "green");
        m.lock(1);
        String before = m.configuration()[0];

        for (int i = 0; i < 10; i++) {
            m.spin(1);
        }

        assertEquals(before, m.configuration()[0]);
    }

    /**
     * Una rueda liberada (unlocked) NO debe quedar estancada:
     * despues de liberarla, una rotacion deterministica debe cambiar
     * su simbolo actual.
     */
    @Test
    public void unlockedWheelShouldRotateAgain()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");
        m.lock(1);
        m.unlock(1);

        m.spin(1, 1);

        assertEquals("blue", m.configuration()[0]);
    }

    // ---------- spin(wheel, steps) ----------

    /**
     * spin(wheel,steps) SI DEBE mover el indice actual exactamente
     * esa cantidad de pasos, dando la vuelta en la lista.
     */
    @Test
    public void shouldRotateExactNumberOfSteps()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");
        m.addSymbol(1, "green");

        m.spin(1, 2);

        assertEquals("green", m.configuration()[0]);
    }

    /**
     * spin(wheel,steps) NO debe fallar ni cambiar nada en una rueda
     * sin simbolos.
     */
    @Test
    public void shouldNotFailRotatingAnEmptyWheel()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);

        m.spin(1, 3);

        assertTrue(m.ok());
        assertNull(m.configuration()[0]);
    }

    // ---------- spin(String[]) ----------

    /**
     * spin(String[]) SI DEBE dejar cada rueda mostrando exactamente
     * el color solicitado.
     */
    @Test
    public void shouldSetTheWholeConfiguration()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);
        m.addSymbol(1, "red");
        m.addSymbol(1, "blue");
        m.addSymbol(2, "green");
        m.addSymbol(2, "yellow");

        m.spin(new String[]{"blue", "yellow"});

        assertArrayEquals(new String[]{"blue", "yellow"}, m.configuration());
    }

    /**
     * spin(String[]) NO debe aceptarse si no tiene un color por
     * cada rueda.
     */
    @Test
    public void shouldNotAcceptConfigurationWithWrongSize()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);

        m.spin(new String[]{"red"});

        assertFalse(m.ok());
    }
}
