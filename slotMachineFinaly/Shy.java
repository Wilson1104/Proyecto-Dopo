/**
 * Simbolo timido: cada vez que queda seleccionado en su rueda alterna
 * entre visible e invisible. Empieza visible. Esconderse solo cambia
 * el dibujo: sigue siendo el simbolo actual de su rueda. Lleva un
 * punto negro.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Shy extends Symbol
{
    private boolean hidden;

    /**
     * Crea un simbolo timido del color dado, visible.
     *
     * @param color un nombre de color valido.
     */
    public Shy(String color)
    {
        super(color);
        hidden = false;
    }

    /**
     * Lo seleccionaron: si estaba visible se esconde y si estaba
     * escondido aparece.
     */
    @Override
    public void selected()
    {
        hidden = !hidden;
    }

    /**
     * Se dibuja solo si no esta escondido.
     */
    @Override
    public void makeVisible()
    {
        if (hidden) {
            makeInvisible();
        } else {
            super.makeVisible();
        }
    }

    /**
     * Lleva un punto negro, salvo cuando esta escondido.
     *
     * @return "black", o null si esta escondido.
     */
    @Override
    public String markColor()
    {
        if (hidden) {
            return null;
        }
        return "black";
    }

    /**
     * Informa si el simbolo esta escondido.
     *
     * @return true si esta escondido.
     */
    public boolean isHidden()
    {
        return hidden;
    }
}
