import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Resuelve el problema I (Slot Machine) de la maraton ICPC 2025.
 * Usa SlotMachine unicamente como testing tool: solo la crea con
 * SlotMachine(n), la gira con spin(wheel,steps) y pregunta cuantos
 * simbolos distintos se ven con distinctSymbols(). Nunca mira los
 * colores directamente.
 *
 * La solucion tiene tres fases:
 * 1. separar: deja todas las ruedas mostrando simbolos distintos.
 * 2. medir: descubre cuantos pasos necesita cada rueda para llegar
 *    al simbolo que mostraba la rueda 1.
 * 3. alinear: gira cada rueda esos pasos y se obtiene el jackpot.
 * Usa como maximo 2n^2 + 1 acciones (5001 para n = 50).
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachineContest
{
    /**
     * Crea una maquina aleatoria de n ruedas y n simbolos, invisible,
     * y encuentra la secuencia de acciones que la lleva al jackpot.
     *
     * @param n el numero de ruedas y simbolos (entre 3 y 50).
     * @return las acciones {rueda, pasos} aplicadas, en orden; un
     *         arreglo vacio si n no es valido.
     */
    public static int[][] solve(int n)
    {
        if (n < 3 || n > 50) {
            return new int[0][];
        }
        return solve(new SlotMachine(n), n);
    }

    /**
     * Encuentra, sobre la maquina dada, la secuencia de acciones que
     * la lleva al jackpot. La maquina permanece invisible.
     *
     * @param machine la maquina de n ruedas y n simbolos a resolver.
     * @param n el numero de ruedas y simbolos de la maquina.
     * @return las acciones {rueda, pasos} aplicadas, en orden.
     */
    public static int[][] solve(SlotMachine machine, int n)
    {
        machine.makeInvisible();
        List<int[]> actions = new ArrayList<int[]>();
        if (separate(machine, n, actions) > 1) {
            int[] steps = measure(machine, n, actions);
            if (machine.distinctSymbols() > 1) {
                align(machine, n, steps, actions);
            }
        }
        return actions.toArray(new int[0][]);
    }

    /**
     * Resuelve una maquina aleatoria de n ruedas y luego muestra,
     * con la maquina visible, todas las acciones de la solucion.
     *
     * @param n el numero de ruedas y simbolos (entre 3 y 50).
     */
    public static void simulate(int n)
    {
        if (n < 3 || n > 50) {
            JOptionPane.showMessageDialog(null,
                "No es posible simular: n debe estar entre 3 y 50.");
            return;
        }
        SlotMachine machine = new SlotMachine(n);
        int[][] actions = solve(machine, n);
        undo(machine, n, actions);
        machine.makeVisible();
        for (int[] action : actions) {
            machine.spin(action[0], action[1]);
        }
    }

    /**
     * Fase 1: deja todas las ruedas mostrando simbolos distintos.
     * Cada rueda avanza de a un paso: si el conteo sube, quedo sola en
     * un simbolo que nadie mostraba y se deja ahi; si da la vuelta
     * completa sin subir, queda en su posicion original.
     *
     * @return el numero de simbolos distintos al terminar (n, o 1 si
     *         por casualidad se llego al jackpot).
     */
    private static int separate(SlotMachine machine, int n,
                                List<int[]> actions)
    {
        int k = machine.distinctSymbols();
        for (int w = 1; w <= n && k > 1 && k < n; w++) {
            int start = k;
            for (int s = 0; s < n && k > 1 && k != start + 1; s++) {
                k = act(machine, w, 1, actions);
            }
        }
        return k;
    }

    /**
     * Fase 2: con todas las ruedas distintas, mueve la rueda 1 un paso
     * (su simbolo queda libre) y recorre cada otra rueda una vuelta
     * completa. La posicion donde el conteo es mayor es la que ocupa el
     * simbolo libre: ahi estaba la rueda 1.
     *
     * @return para cada rueda w, los pasos que la llevan al simbolo
     *         original de la rueda 1.
     */
    private static int[] measure(SlotMachine machine, int n,
                                 List<int[]> actions)
    {
        int[] steps = new int[n + 1];
        int k = act(machine, 1, 1, actions);
        for (int w = 2; w <= n && k > 1; w++) {
            int bestK = -1;
            for (int s = 1; s <= n && k > 1; s++) {
                k = act(machine, w, 1, actions);
                if (s < n && k > bestK) {
                    bestK = k;
                    steps[w] = s;
                }
            }
        }
        steps[1] = n - 1;
        return steps;
    }

    /**
     * Fase 3: gira cada rueda los pasos medidos, de modo que todas
     * quedan mostrando el simbolo original de la rueda 1 (jackpot).
     */
    private static void align(SlotMachine machine, int n, int[] steps,
                              List<int[]> actions)
    {
        int k = n;
        for (int w = 1; w <= n && k > 1; w++) {
            k = act(machine, w, steps[w], actions);
        }
    }

    /**
     * Aplica una accion sobre la maquina, la guarda en la lista y
     * pregunta cuantos simbolos distintos se ven ahora.
     *
     * @return el numero de simbolos distintos despues de la accion.
     */
    private static int act(SlotMachine machine, int wheel, int steps,
                           List<int[]> actions)
    {
        machine.spin(wheel, steps);
        actions.add(new int[]{wheel, steps});
        return machine.distinctSymbols();
    }

    /**
     * Deshace las acciones en orden inverso (girando cada rueda lo que
     * le falta para completar la vuelta), dejando la maquina en su
     * configuracion inicial, sin necesidad de mirarla.
     */
    private static void undo(SlotMachine machine, int n, int[][] actions)
    {
        for (int i = actions.length - 1; i >= 0; i--) {
            int back = (n - actions[i][1] % n) % n;
            machine.spin(actions[i][0], back);
        }
    }
}
