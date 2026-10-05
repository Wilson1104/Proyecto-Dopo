import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.JOptionPane;

/**
 * Pruebas de aceptacion del ciclo 4, para la presentacion. Corren con
 * la maquina visible y con pausas. Al final preguntan si lo que se vio
 * es correcto; la prueba pasa solo si se responde Si.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineATest
{
    /**
     * Ruedas: la lefty copia a su vecina, la rebel no se deja bloquear,
     * intercambiar ni eliminar, y la reverse gira hacia atras.
     */
    @Test
    public void accordingFvTgShouldShowTheWheelTypes()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel("normal", 1);
        m.addWheel("lefty", 2);
        m.addWheel("rebel", 3);
        m.addWheel("reverse", 4);
        for (int w = 1; w <= 4; w++) {
            m.addSymbol(w, "red");
            m.addSymbol(w, "blue");
            m.addSymbol(w, "green");
            m.addSymbol(w, "yellow");
        }
        m.makeVisible();
        pause();

        m.spin(1, 1);
        pause();
        m.spin(2);
        pause();
        m.lock(3);
        m.swap(3, 4);
        m.delWheel(3);
        m.spin(4, 3);
        pause();

        int answer = JOptionPane.showConfirmDialog(null,
            "Marcos: normal gris, lefty azul, rebel negro, reverse naranja.\n"
            + "La lefty copio el azul de la rueda 1, la rebel no se dejo\n"
            + "bloquear, intercambiar ni eliminar, y la reverse giro hacia\n"
            + "atras. Acepta la prueba?");
        m.makeInvisible();
        assertEquals(JOptionPane.YES_OPTION, answer);
    }

    /**
     * Simbolos: los ephemeral se encogen en cada giro, el shy se
     * esconde y aparece cada vez que le toca, y el normal no cambia.
     */
    @Test
    public void accordingFvTgShouldShowTheSymbolTypes()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addWheel(2);
        m.addWheel(3);
        m.addSymbol("ephemeral", 1, "red");
        m.addSymbol("ephemeral", 1, "blue");
        m.addSymbol("normal", 2, "yellow");
        m.addSymbol("shy", 2, "green");
        m.addSymbol("normal", 3, "magenta");
        m.addSymbol("normal", 3, "black");
        m.makeVisible();
        pause();

        for (int i = 0; i < 8; i++) {
            m.spin(1, 1);
            m.spin(2, 1);
            m.spin(3, 1);
            pause();
        }

        int answer = JOptionPane.showConfirmDialog(null,
            "Punto blanco: ephemeral. Punto negro: shy.\n"
            + "Los de la rueda 1 se encogieron hasta quedar como un punto,\n"
            + "el verde se escondio y aparecio cada vez que le toco, y los\n"
            + "de la rueda 3 no cambiaron. Acepta la prueba?");
        m.makeInvisible();
        assertEquals(JOptionPane.YES_OPTION, answer);
    }

    /**
     * Espera un momento para que se alcance a ver el paso anterior.
     */
    private void pause()
    {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            // no importa si la pausa se corta
        }
    }
}
