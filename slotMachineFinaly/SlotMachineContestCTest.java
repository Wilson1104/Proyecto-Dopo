import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas colectiva del ciclo 3, construida junto con el wiki.
 * Los nombres de las pruebas incluyen la identificacion de los autores.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineContestCTest
{
    /**
     * accordingFvTgShould: al repetir las acciones de solve sobre la
     * maquina en su estado inicial, el jackpot aparece justo en la
     * ultima accion (como exige la maraton: no sobran acciones).
     */
    @Test
    public void accordingFvTgShouldReachJackpotExactlyAtLastAction()
    {
        int n = 8;
        SlotMachine m = new SlotMachine(n);
        int[][] actions = SlotMachineContest.solve(m, n);
        for (int i = actions.length - 1; i >= 0; i--) {
            m.spin(actions[i][0], (n - actions[i][1] % n) % n);
        }

        for (int i = 0; i < actions.length - 1; i++) {
            m.spin(actions[i][0], actions[i][1]);
            assertTrue(m.distinctSymbols() > 1);
        }
        int[] last = actions[actions.length - 1];
        m.spin(last[0], last[1]);

        assertEquals(1, m.distinctSymbols());
    }

    /**
     * accordingFvTgShould: solve NO debe fallar en ninguna maquina
     * aleatoria y nunca debe usar mas de 2n^2 + 1 acciones.
     */
    @Test
    public void accordingFvTgShouldNotFailOnRandomMachines()
    {
        for (int n = 3; n <= 12; n++) {
            for (int r = 0; r < 5; r++) {
                SlotMachine m = new SlotMachine(n);
                int[][] actions = SlotMachineContest.solve(m, n);
                assertEquals(1, m.distinctSymbols());
                assertTrue(actions.length <= 2 * n * n + 1);
            }
        }
    }
}
