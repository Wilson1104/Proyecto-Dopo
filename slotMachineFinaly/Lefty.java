/**
 * Rueda zurda: si hay una rueda a su izquierda, al girar copia su
 * estado, es decir, queda mostrando el mismo color que ella. Si no hay
 * rueda a la izquierda o no tiene ese color, gira como una normal. Su
 * marco es azul claro.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Lefty extends Wheel
{
    /**
     * Crea una rueda zurda vacia en la ranura dada.
     *
     * @param slot el indice de la ranura de esta rueda (base 0).
     */
    public Lefty(int slot)
    {
        super(slot, "#9ec5fe");
    }

    /**
     * Gira copiando a la rueda de la izquierda, o al azar si no puede.
     *
     * @param left la rueda de la izquierda, o null si no hay.
     */
    @Override
    public void spin(Wheel left)
    {
        if (!copy(left)) {
            super.spin(left);
        }
    }

    /**
     * Da un paso copiando a la rueda de la izquierda, o avanza uno si
     * no puede.
     *
     * @param left la rueda de la izquierda, o null si no hay.
     */
    @Override
    public void stepOnce(Wheel left)
    {
        if (!copy(left)) {
            super.stepOnce(left);
        }
    }

    /**
     * Intenta quedar mostrando el color que muestra la rueda de la
     * izquierda.
     *
     * @param left la rueda de la izquierda, o null si no hay.
     * @return true si pudo copiarla.
     */
    private boolean copy(Wheel left)
    {
        if (isLocked() || left == null || left.current() == null) {
            return false;
        }
        String color = left.current().getColor();
        String[] mine = symbols();
        for (int i = 0; i < mine.length; i++) {
            if (mine[i].equals(color)) {
                turnTo(i);
                return true;
            }
        }
        return false;
    }
}
