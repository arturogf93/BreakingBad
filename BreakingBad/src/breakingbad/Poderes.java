/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbad;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Abraham
 */
public class Poderes extends Base {

    private int velocidad;

    public Poderes(int posX, int posY, int vel) {
        super(posX, posY);
        velocidad = vel;
        Image bomb1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder1.png"));
        Image bomb2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder2.png"));
        Image bomb3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder3.png"));
        Image bomb4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder4.png"));
        Image bomb5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder5.png"));
        Image bomb6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder6.png"));
        Image bomb7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder7.png"));
        Image bomb8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Poder8.png"));
        (this.getImagenes()).sumaCuadro(bomb1, 70);
        (this.getImagenes()).sumaCuadro(bomb2, 70);
        (this.getImagenes()).sumaCuadro(bomb3, 70);
        (this.getImagenes()).sumaCuadro(bomb4, 70);
        (this.getImagenes()).sumaCuadro(bomb5, 70);
        (this.getImagenes()).sumaCuadro(bomb6, 70);
        (this.getImagenes()).sumaCuadro(bomb7, 70);
        (this.getImagenes()).sumaCuadro(bomb8, 70);
    }

    public void setVelocidad(int velocidad) {  //Metodo para asignar la velocidad
        this.velocidad = velocidad;
    }

    //@return    regresa la velocidad de tipo <code>TipoM</code>
    public int getVelocidad() {                 //Metodo para obtener la velocidad
        return velocidad;
    }
}
