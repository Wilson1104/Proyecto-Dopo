/**
 * Rueda reversa (tipo propuesto por nosotros): cuando se rota paso a
 * paso, retrocede en lugar de avanzar (del primer simbolo pasa al
 * ultimo). Al girar al azar es igual a una normal. Su marco es
 * naranja claro.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Reverse extends Wheel
{
    /**
     * Crea una rueda reversa vacia en la ranura dada.
     *
     * @param slot el indice de la ranura de esta rueda (base 0).
     */
    public Reverse(int slot)
    {
        super(slot, "#ffc98a");
    }

    /**
     * Retrocede exactamente una posicion, a menos que este fijada o
     * vacia.
     *
     * @param left la rueda de la izquierda; la reversa no la usa.
     */
    @Override
    public void stepOnce(Wheel left)
    {
        if (!isLocked() && size() > 0) {
            turnTo((currentIndex - 1 + size()) % size());
        }
    }
}
