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
public class Meth extends Base {
    
    public Meth(int posX, int posY) {
        super(posX, posY);
        Image imag1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/meth.jpg"));
        (this.getImagenes()).sumaCuadro(imag1, 70);
    }
    
}
