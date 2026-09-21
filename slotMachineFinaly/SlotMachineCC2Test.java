import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas colectiva para el ciclo 2, construida junto con
 * el wiki. Los nombres de las pruebas incluyen la identificacion de
 * los autores.
 * 
 * @authors  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineCC2Test
{
    /**
     * accordingFvTgShould: despues de intercambiar dos ruedas dos
     * veces seguidas (ida y vuelta), la maquina termina en su
     * configuracion original.
     */
    @Test
    public void accordingFvTgShouldReturnToOriginalAfterDoubleSwap()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        String[] original = m.configuration();

        m.swap(1, 2);
        m.swap(1, 2);

        assertArrayEquals(original, m.configuration());
    }

    /**
     * accordingFvTgShould: isJackpot() NO debe ser verdadero cuando
     * una rueda fijada (locked) mantiene un color distinto al resto
     * despues de girar todas las demas hacia colores coincidentes.
     */
    @Test
    public void accordingFvTgShouldNotJackpotWhenLockedWheelDiffers()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.lock(1);

        m.spin(new String[]{"red", "blue"});

        assertFalse(m.isJackpot());
    }
}
