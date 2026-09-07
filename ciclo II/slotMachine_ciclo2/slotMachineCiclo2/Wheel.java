import java.util.List;
import java.util.ArrayList;

/**
 * Una Wheel (rueda) contiene una lista de posibles Symbol (simbolos).
 * En todo momento, uno de ellos es el "actual" (el que se muestra).
 * Girar elige un nuevo simbolo actual al azar; colocar lo fija
 * directamente.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Wheel
{
    private List<Symbol> symbols;
    private int currentIndex;
    private int slot;
    private boolean locked;

    /**
     * Crea una rueda vacia ubicada en la ranura horizontal dada
     * (usada solo para decidir donde se dibujan sus simbolos en
     * pantalla).
     *
     * @param slot el indice de la ranura horizontal de esta rueda
     *             (base 0).
     */
    public Wheel(int slot)
    {
        this.slot = slot;
        symbols = new ArrayList<Symbol>();
        currentIndex = 0;
        locked = false;
    }

    /**
     * Fija esta rueda para que ignore cualquier solicitud de giro o
     * rotacion.
     */
    public void lock()
    {
        locked = true;
    }

    /**
     * Libera esta rueda para que pueda volver a girar o rotar.
     */
    public void unlock()
    {
        locked = false;
    }

    /**
     * Informa si esta rueda esta actualmente fijada.
     *
     * @return true si esta rueda esta fijada.
     */
    public boolean isLocked()
    {
        return locked;
    }

    /**
     * Mueve todos los simbolos de esta rueda a una nueva ranura
     * horizontal, usado cuando dos ruedas intercambian su lugar.
     * Solo afecta donde se dibujan los simbolos, no cual esta
     * mostrandose actualmente.
     *
     * @param newSlot la ranura que esta rueda ocupa ahora (base 0).
     */
    public void relocate(int newSlot)
    {
        int delta = (newSlot - slot) * 80;
        for (Symbol s : symbols) {
            s.moveHorizontal(delta);
        }
        slot = newSlot;
    }

    /**
     * Avanza el simbolo actual exactamente una posicion (dando la
     * vuelta al llegar al final), a menos que esta rueda este fijada
     * o vacia. Usado por SlotMachine para animar una rotacion paso a
     * paso.
     */
    public void stepOnce()
    {
        if (!locked && !symbols.isEmpty()) {
            currentIndex = (currentIndex + 1) % symbols.size();
        }
    }

    /**
     * Agrega un nuevo simbolo del color dado a esta rueda.
     *
     * @param color un nombre de color CSS valido.
     */
    public void addSymbol(String color)
    {
        Symbol s = new Symbol(color);
        s.moveHorizontal(slot * 80);
        s.moveVertical(60);
        symbols.add(s);
    }

    /**
     * Elimina el primer simbolo del color dado en esta rueda.
     *
     * @param color el color a eliminar.
     */
    public void delSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equals(color)) {
                symbols.remove(i);
                if (currentIndex >= symbols.size()) {
                    currentIndex = 0;
                }
                return;
            }
        }
    }

    /**
     * Coloca un simbolo especifico como el actual (sin girar).
     *
     * @param color el color del simbolo a mostrar.
     */
    public void place(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equals(color)) {
                currentIndex = i;
                return;
            }
        }
    }

    /**
     * Gira esta rueda: elige un simbolo al azar entre los
     * disponibles.
     */
    public void spin()
    {
        if (!locked && !symbols.isEmpty()) {
            currentIndex = (int) (Math.random() * symbols.size());
        }
    }

    /**
     * Retorna el simbolo actualmente mostrado, o null si esta rueda
     * no tiene simbolos.
     *
     * @return el Symbol actual.
     */
    public Symbol current()
    {
        if (symbols.isEmpty()) {
            return null;
        }
        return symbols.get(currentIndex);
    }

    /**
     * Retorna los colores de todos los simbolos de esta rueda, en
     * orden.
     *
     * @return un arreglo de nombres de color.
     */
    public String[] symbols()
    {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    /**
     * Retorna cuantos simbolos tiene actualmente esta rueda.
     *
     * @return el numero de simbolos.
     */
    public int size()
    {
        return symbols.size();
    }

    /**
     * Hace visible en pantalla el simbolo actual de esta rueda.
     */
    public void makeVisible()
    {
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
        if (current() != null) {
            current().makeVisible();
        }
    }

    /**
     * Hace invisible cada simbolo de esta rueda.
     */
    public void makeInvisible()
    {
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
    }
}
