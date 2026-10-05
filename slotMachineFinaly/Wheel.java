import java.util.List;
import java.util.ArrayList;

/**
 * Una Wheel (rueda) contiene una lista de posibles Symbol (simbolos).
 * En todo momento, uno de ellos es el "actual" (el que se muestra).
 * Girar elige un nuevo simbolo actual al azar; colocar lo fija
 * directamente.
 *
 * Esta clase es la rueda normal. Lefty, Rebel y Reverse la extienden.
 * En pantalla cada rueda tiene un marco detras de su simbolo; el color
 * del marco dice de que tipo es.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Wheel
{
    private List<Symbol> symbols;
    protected int currentIndex;
    private int slot;
    private boolean locked;
    private Rectangle frame;
    private Circle mark;
    private static final int PER_ROW = 10;
    private static final int GAP = 50;

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
        this(slot, "#d9d9d9");
    }

    /**
     * Crea una rueda vacia con un marco del color dado. Lo usan los
     * tipos de rueda para distinguirse en pantalla.
     *
     * @param slot       el indice de la ranura de esta rueda (base 0).
     * @param frameColor el color del marco.
     */
    protected Wheel(int slot, String frameColor)
    {
        this.slot = slot;
        symbols = new ArrayList<Symbol>();
        currentIndex = 0;
        locked = false;
        frame = new Rectangle();
        frame.changeSize(40, 40);
        frame.moveHorizontal(-55 + (slot % PER_ROW) * GAP);
        frame.moveVertical(55 + (slot / PER_ROW) * GAP);
        frame.changeColor(frameColor);
        mark = new Circle();
        mark.changeSize(8);
        mark.moveHorizontal(11 + (slot % PER_ROW) * GAP);
        mark.moveVertical(71 + (slot / PER_ROW) * GAP);
    }

    /**
     * Informa si esta rueda es rebelde (no se deja bloquear,
     * intercambiar ni eliminar). La rueda normal no lo es.
     *
     * @return false.
     */
    public boolean isRebel()
    {
        return false;
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
        int dx = (newSlot % PER_ROW - slot % PER_ROW) * GAP;
        int dy = (newSlot / PER_ROW - slot / PER_ROW) * GAP;
        frame.moveHorizontal(dx);
        frame.moveVertical(dy);
        mark.moveHorizontal(dx);
        mark.moveVertical(dy);
        for (Symbol s : symbols) {
            s.moveHorizontal(dx);
            s.moveVertical(dy);
        }
        slot = newSlot;
    }

    /**
     * Avanza el simbolo actual exactamente una posicion (dando la
     * vuelta al llegar al final), a menos que esta rueda este fijada
     * o vacia. Usado por SlotMachine para animar una rotacion paso a
     * paso.
     *
     * @param left la rueda de la izquierda (o null); la normal no la
     *             usa.
     */
    public void stepOnce(Wheel left)
    {
        if (!locked && !symbols.isEmpty()) {
            turnTo((currentIndex + 1) % symbols.size());
        }
    }

    /**
     * Deja como actual el simbolo de la posicion dada y les avisa a
     * los simbolos: a todos que la rueda giro y al nuevo actual que
     * quedo seleccionado.
     *
     * @param index la posicion del nuevo simbolo actual (base 0).
     */
    protected void turnTo(int index)
    {
        currentIndex = index;
        for (Symbol s : symbols) {
            s.turned();
        }
        symbols.get(index).selected();
    }

    /**
     * Agrega un simbolo (de cualquier tipo) a esta rueda.
     *
     * @param s el simbolo a agregar.
     */
    public void addSymbol(Symbol s)
    {
        s.moveHorizontal((slot % PER_ROW) * GAP);
        s.moveVertical(60 + (slot / PER_ROW) * GAP);
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
                symbols.get(i).selected();
                return;
            }
        }
    }

    /**
     * Gira esta rueda: elige un simbolo al azar entre los
     * disponibles.
     *
     * @param left la rueda de la izquierda (o null); la normal no la
     *             usa.
     */
    public void spin(Wheel left)
    {
        if (!locked && !symbols.isEmpty()) {
            turnTo((int) (Math.random() * symbols.size()));
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
     * Hace visible en pantalla la rueda: su marco, su simbolo actual
     * y, si el simbolo lo pide, el punto que muestra su tipo.
     */
    public void makeVisible()
    {
        frame.makeVisible();
        for (Symbol s : symbols) {
            s.makeInvisible();
        }
        mark.makeInvisible();
        if (current() != null) {
            current().makeVisible();
            String c = current().markColor();
            if (c != null) {
                mark.changeColor(c);
                mark.makeVisible();
            }
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
        mark.makeInvisible();
        frame.makeInvisible();
    }
}
