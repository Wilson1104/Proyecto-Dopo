/**
 * Rueda rebelde: gira como una normal, pero no se deja bloquear, ni
 * intercambiar, ni eliminar. Su marco es negro.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Rebel extends Wheel
{
    /**
     * Crea una rueda rebelde vacia en la ranura dada.
     *
     * @param slot el indice de la ranura de esta rueda (base 0).
     */
    public Rebel(int slot)
    {
        super(slot, "black");
    }

    /**
     * Una rueda rebelde si es rebelde.
     *
     * @return true.
     */
    @Override
    public boolean isRebel()
    {
        return true;
    }
}
