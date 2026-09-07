/**
 * Un Symbol es una figura de color usada dentro de una Wheel de una
 * SlotMachine. Extiende de Circle para reutilizar su comportamiento
 * de dibujo (hacerse visible, moverse, cambiar de color), y ademas
 * recuerda su propio color, ya que Circle no ofrece una forma de
 * consultarlo.
 *
 * @author  Wilson Florez, Ronald Tarapues
 */
public class Symbol extends Circle
{
    private String currentColor;

    /**
     * Crea un nuevo Symbol con el color dado.
     *
     * @param color un nombre de color CSS valido
     */
    public Symbol(String color)
    {
        super();
        currentColor = color;
        changeColor(color);
    }

    /**
     * Retorna el color actual de este simbolo.
     *
     * @return el color de este simbolo.
     */
    public String getColor()
    {
        return currentColor;
    }

    /**
     * Cambia el color de este simbolo, guardando el registro
     * localmente (Circle solo sabe dibujar el color, no reportarlo
     * de vuelta).
     *
     * @param newColor un nombre de color CSS valido.
     */
    @Override
    public void changeColor(String newColor)
    {
        super.changeColor(newColor);
        currentColor = newColor;
    }
}
