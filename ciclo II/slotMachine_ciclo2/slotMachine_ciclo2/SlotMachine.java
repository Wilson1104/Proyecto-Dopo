import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 * SlotMachine simula una maquina tragamonedas sencilla formada por
 * varias Wheel (ruedas), cada una con varios Symbol (simbolos) de
 * colores. Se puede girar, consultar, y mostrar u ocultar en pantalla.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class SlotMachine
{
    private List<Wheel> wheels;
    private boolean visible;
    private boolean lastOk;

    /**
     * Crea una nueva maquina tragamonedas vacia (sin ruedas todavia),
     * oculta.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = false;
        lastOk = true;
    }

    /**
     * Agrega una nueva rueda vacia en la posicion indicada.
     * Las posiciones empiezan en 1. Los valores fuera de rango se
     * ajustan al limite mas cercano.
     *
     * @param pos la posicion deseada para la nueva rueda.
     */
    public void addWheel(int pos)
    {
        int p = adjustPos(pos, wheels.size() + 1);
        wheels.add(p - 1, new Wheel(p - 1));
        lastOk = true;
    }

    /**
     * Elimina la rueda en la posicion indicada.
     * Las posiciones empiezan en 1. Los valores fuera de rango se
     * ajustan al limite mas cercano.
     *
     * @param pos la posicion de la rueda a eliminar.
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
     * Intercambia las ruedas en las dos posiciones dadas, tanto su
     * orden como su ubicacion en pantalla. Las posiciones empiezan
     * en 1.
     *
     * @param wheel1 la posicion de la primera rueda.
     * @param wheel2 la posicion de la segunda rueda.
     */
    public void swap(int wheel1, int wheel2)
    {
        if (wheels.size() < 2) {
            fail("Se necesitan al menos dos ruedas para intercambiar.");
            return;
        }
        int p1 = adjustPos(wheel1, wheels.size());
        int p2 = adjustPos(wheel2, wheels.size());
        Wheel w1 = wheels.get(p1 - 1);
        Wheel w2 = wheels.get(p2 - 1);
        wheels.set(p1 - 1, w2);
        wheels.set(p2 - 1, w1);
        w1.relocate(p2 - 1);
        w2.relocate(p1 - 1);
        lastOk = true;
    }

    /**
     * Fija la rueda en la posicion indicada para que ignore las
     * solicitudes de giro hasta que se libere. Las posiciones
     * empiezan en 1.
     *
     * @param wheel la posicion de la rueda a fijar.
     */
    public void lock(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para fijar.");
            return;
        }
        int p = adjustPos(wheel, wheels.size());
        wheels.get(p - 1).lock();
        lastOk = true;
    }

    /**
     * Libera la rueda en la posicion indicada para que pueda volver
     * a girar. Las posiciones empiezan en 1.
     *
     * @param wheel la posicion de la rueda a liberar.
     */
    public void unlock(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para soltar.");
            return;
        }
        int p = adjustPos(wheel, wheels.size());
        wheels.get(p - 1).unlock();
        lastOk = true;
    }

    /**
     * Agrega un nuevo simbolo del color dado a la rueda en la
     * posicion indicada. Las posiciones empiezan en 1. Los valores
     * fuera de rango se ajustan al limite mas cercano.
     *
     * @param pos   la posicion de la rueda que recibe el simbolo.
     * @param color un nombre de color CSS valido.
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
     * Elimina el color de simbolo indicado de todas las ruedas que
     * lo tengan.
     *
     * @param symbol el color del simbolo a eliminar.
     */
    public void delSymbol(String symbol)
    {
        for (Wheel w : wheels) {
            w.delSymbol(symbol);
        }
        lastOk = true;
    }

    /**
     * Coloca un simbolo especifico como el actual en la rueda dada,
     * sin girar. Las posiciones empiezan en 1.
     *
     * @param wheel  la posicion de la rueda a modificar.
     * @param symbol el color del simbolo a mostrar.
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
     * Gira una sola rueda, eligiendo un simbolo al azar entre los
     * suyos. Las posiciones empiezan en 1.
     *
     * @param wheel la posicion de la rueda a girar.
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
     * Gira todas las ruedas de la maquina.
     */
    public void spin()
    {
        for (int i = 1; i <= wheels.size(); i++) {
            spin(i);
        }
    }

    /**
     * Rota una sola rueda exactamente el numero de pasos indicado
     * (no al azar). Si la maquina esta visible, el movimiento se
     * muestra paso a paso. Las posiciones empiezan en 1.
     *
     * @param wheel la posicion de la rueda a rotar.
     * @param steps cuantas posiciones avanzar.
     */
    public void spin(int wheel, int steps)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para girar.");
            return;
        }
        int p = adjustPos(wheel, wheels.size());
        Wheel w = wheels.get(p - 1);
        for (int i = 0; i < steps; i++) {
            w.stepOnce();
            if (visible) {
                w.makeVisible();
                pause();
            }
        }
        lastOk = true;
    }

    /**
     * Deja toda la maquina mostrando exactamente la configuracion
     * dada, un color por rueda, de izquierda a derecha. Cada color
     * debe existir previamente entre los simbolos de su rueda
     * correspondiente.
     *
     * @param setSymbols el color deseado para cada rueda, en orden.
     */
    public void spin(String[] setSymbols)
    {
        if (setSymbols.length != wheels.size()) {
            fail("La configuración no coincide con el número de ruedas.");
            return;
        }
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).place(setSymbols[i]);
        }
        if (visible) {
            makeVisible();
        }
        lastOk = true;
    }

    /**
     * Hace una pausa breve, usada para animar una rotacion paso a
     * paso.
     */
    private void pause()
    {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // Se ignora: una pausa saltada no afecta la correccion.
        }
    }

    /**
     * Retorna los colores de todos los simbolos de todas las ruedas
     * de la maquina, en orden (rueda por rueda, y dentro de cada
     * rueda en el orden en que se agregaron sus simbolos).
     *
     * @return un arreglo con todos los colores de simbolos de la
     *         maquina.
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
     * Retorna cuantos colores distintos existen entre los simbolos
     * de la maquina (ver {@link #symbols()}).
     *
     * @return el numero de colores distintos.
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
     * Retorna los colores actualmente visibles en cada rueda, de
     * izquierda a derecha.
     *
     * @return un arreglo con un color por rueda.
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
     * Verifica si la configuracion actual es ganadora (todas las
     * ruedas visibles muestran el mismo color).
     *
     * @return true si la configuracion es un jackpot.
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
     * Hace visible en pantalla toda la maquina (todas las ruedas y
     * sus simbolos actuales).
     */
    public void makeVisible()
    {
        visible = true;
        for (Wheel w : wheels) {
            w.makeVisible();
        }
    }

    /**
     * Oculta toda la maquina de la pantalla.
     */
    public void makeInvisible()
    {
        for (Wheel w : wheels) {
            w.makeInvisible();
        }
        visible = false;
    }

    /**
     * Termina el simulador, ocultandolo primero.
     */
    public void exit()
    {
        makeInvisible();
    }

    /**
     * Informa si la ultima operacion realizada sobre esta maquina
     * se completo con exito.
     *
     * @return true si la ultima operacion tuvo exito.
     */
    public boolean ok()
    {
        return lastOk;
    }

    /**
     * Ajusta una posicion base 1 al rango valido [1, max].
     *
     * @param pos la posicion solicitada.
     * @param max la posicion valida mas alta.
     * @return la posicion ajustada.
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
     * Registra una operacion fallida y, si la maquina esta visible,
     * muestra al usuario un mensaje explicando que salio mal.
     *
     * @param message la explicacion a mostrar al usuario.
     */
    private void fail(String message)
    {
        lastOk = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
