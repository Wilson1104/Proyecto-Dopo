import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas colectiva del ciclo 4, construida junto con el
 * wiki. Los nombres de las pruebas incluyen la identificacion de los
 * autores.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineCC4Test
{
    /**
     * accordingFvTgShould: si las ruedas despues de la primera son
     * lefty y tienen los mismos simbolos, girar toda la maquina SIEMPRE
     * da jackpot, porque cada una copia a su vecina.
     */
    @Test
    public void accordingFvTgShouldAlwaysWinWithLeftyWheels()
    {
        for (int i = 0; i < 20; i++) {
            SlotMachine m = new SlotMachine();
            m.addWheel("normal", 1);
            m.addWheel("lefty", 2);
            m.addWheel("lefty", 3);
            for (int w = 1; w <= 3; w++) {
                m.addSymbol(w, "red");
                m.addSymbol(w, "blue");
                m.addSymbol(w, "green");
            }

            m.spin();

            assertTrue(m.isJackpot());
        }
    }

    /**
     * accordingFvTgShould: los tipos nuevos NO deben romper la maraton:
     * la solucion del ciclo 3 sigue llegando al jackpot.
     */
    @Test
    public void accordingFvTgShouldStillSolveTheContest()
    {
        SlotMachine m = new SlotMachine(10);

        SlotMachineContest.solve(m, 10);

        assertTrue(m.isJackpot());
    }
}
