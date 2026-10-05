/**
 * Simbolo efimero: cada vez que su rueda gira se hace mas pequeno,
 * hasta quedar como un punto. Se encoge hacia su centro para no
 * salirse de su lugar. Mientras es grande lleva un punto blanco.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Ephemeral extends Symbol
{
    private int size;

    /**
     * Crea un simbolo efimero del color dado, de tamano normal.
     *
     * @param color un nombre de color valido.
     */
    public Ephemeral(String color)
    {
        super(color);
        size = 30;
    }

    /**
     * Su rueda giro: se encoge 4 pixeles (2 por cada lado) hasta
     * llegar a 6, que es el punto.
     */
    @Override
    public void turned()
    {
        if (size > 6) {
            size = size - 4;
            changeSize(size);
            moveHorizontal(2);
            moveVertical(2);
        }
    }

    /**
     * Lleva un punto blanco mientras sea suficientemente grande.
     *
     * @return "white", o null si ya es muy pequeno.
     */
    @Override
    public String markColor()
    {
        if (size >= 14) {
            return "white";
        }
        return null;
    }

    /**
     * Retorna el diametro actual del simbolo.
     *
     * @return el tamano en pixeles.
     */
    public int getSize()
    {
        return size;
    }
}
