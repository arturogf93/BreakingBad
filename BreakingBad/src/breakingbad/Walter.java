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
public class Walter extends Base {

    private int velocidadX;
    private int velocidadY;

    public Walter(int posX, int posY, int velx, int vely) {
        super(posX, posY);
        velocidadX = velx;
        velocidadY = vely;
        Image imag1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/boss.png"));
        (this.getImagenes()).sumaCuadro(imag1, 70);
    }

    //@param  es para saber la velocidad que se asignara de tipo <code>Tipo1</code>
    public void setVelocidadX(int velocidadx) {  //Metodo para asignar la velocidad
        this.velocidadX = velocidadx;
    }

    //@return    regresa la velocidad de tipo <code>TipoM</code>
    public int getVelocidadX() {                 //Metodo para obtener la velocidad
        return velocidadX;
    }

    //@param  es para saber la velocidad que se asignara de tipo <code>Tipo1</code>
    public void setVelocidadY(int velocidady) {  //Metodo para asignar la velocidad
        this.velocidadY = velocidady;
    }

    //@return    regresa la velocidad de tipo <code>TipoM</code>
    public int getVelocidadY() {                 //Metodo para obtener la velocidad
        return velocidadY;
    }
}
