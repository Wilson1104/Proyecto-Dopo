import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Pruebas de unidad del ciclo 3: el constructor SlotMachine(n) y la
 * solucion de la maraton en SlotMachineContest. Todas corren en modo
 * invisible.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineContestTest
{
    // ---------- SlotMachine(n) ----------

    /**
     * SlotMachine(n) SI DEBE crear n ruedas con n simbolos de colores
     * diferentes.
     */
    @Test
    public void shouldCreateMachineWithNWheelsAndNDifferentSymbols()
    {
        SlotMachine m = new SlotMachine(5);

        Set<String> colors = new HashSet<String>(Arrays.asList(m.symbols()));

        assertEquals(5, m.configuration().length);
        assertEquals(25, m.symbols().length);
        assertEquals(5, colors.size());
        assertTrue(m.ok());
    }

    /**
     * SlotMachine(n) NO debe crear una maquina que ya este ganada.
     */
    @Test
    public void shouldNotCreateAWinningMachine()
    {
        for (int i = 0; i < 50; i++) {
            SlotMachine m = new SlotMachine(3);
            assertTrue(m.distinctSymbols() > 1);
        }
    }

    /**
     * SlotMachine(n) NO debe crear una maquina con menos de dos ruedas.
     */
    @Test
    public void shouldNotCreateMachineWithLessThanTwoWheels()
    {
        SlotMachine m = new SlotMachine(1);

        assertFalse(m.ok());
        assertEquals(0, m.configuration().length);
    }

    // ---------- solve ----------

    /**
     * solve SI DEBE dejar en jackpot la maquina mas pequena (n = 3).
     */
    @Test
    public void shouldSolveSmallestMachine()
    {
        SlotMachine m = new SlotMachine(3);

        SlotMachineContest.solve(m, 3);

        assertEquals(1, m.distinctSymbols());
    }

    /**
     * solve SI DEBE dejar en jackpot una maquina mediana (n = 10).
     */
    @Test
    public void shouldSolveMediumMachine()
    {
        SlotMachine m = new SlotMachine(10);

        SlotMachineContest.solve(m, 10);

        assertEquals(1, m.distinctSymbols());
    }

    /**
     * solve SI DEBE dejar en jackpot la maquina mas grande (n = 50)
     * sin pasar el limite de 10000 acciones de la maraton.
     */
    @Test
    public void shouldSolveLargestMachineWithinTheLimit()
    {
        SlotMachine m = new SlotMachine(50);

        int[][] actions = SlotMachineContest.solve(m, 50);

        assertEquals(1, m.distinctSymbols());
        assertTrue(actions.length <= 10000);
    }

    /**
     * solve(n) SI DEBE retornar acciones validas: ruedas entre 1 y n y
     * pasos entre 0 y n - 1.
     */
    @Test
    public void shouldReturnValidActions()
    {
        int[][] actions = SlotMachineContest.solve(7);

        assertTrue(actions.length > 0);
        for (int[] action : actions) {
            assertEquals(2, action.length);
            assertTrue(action[0] >= 1 && action[0] <= 7);
            assertTrue(action[1] >= 0 && action[1] <= 6);
        }
    }

    /**
     * solve(n) NO debe resolver tamanos fuera del rango de la maraton.
     */
    @Test
    public void shouldNotSolveInvalidSizes()
    {
        assertEquals(0, SlotMachineContest.solve(2).length);
        assertEquals(0, SlotMachineContest.solve(51).length);
    }
}
