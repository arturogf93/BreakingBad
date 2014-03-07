package breakingbad;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abraham
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Bueno extends Base {

    private static final String DESAPARECE = "DESAPARECE";  //Constante de tipo string
    private static final String PAUSADO = "PAUSADO";        //constante de tipo string

    //@param  posX es para saber la posicion x de tipo <code>int</code>
    //@param  posY es para saber la posicion y de tipo <code>int</code>
    public Bueno(int posX, int posY) {
        super(posX, posY);
        Image rv1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/RV1.png"));
        Image rv2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/RV2.png"));
        Image rv3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/RV3.png"));
        Image rv4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/RV3.png"));
        (this.getImagenes()).sumaCuadro(rv1, 70);
        (this.getImagenes()).sumaCuadro(rv2, 70);
        (this.getImagenes()).sumaCuadro(rv3, 70);
        (this.getImagenes()).sumaCuadro(rv4, 70);
    }

    //@return    regresa DESAPARECE de tipo <code>String</code>
    public String getDESAPARECE() {             //metodo para obtener valor DESAPARECE
        return DESAPARECE;
    }

    //@return    regresa PAUSADO de tipo <code>String</code>
    public String getPAUSADO() {                //metodo para obtener valor Pausado
        return PAUSADO;
    }

    //@param  width es para saber el ancho de tipo <code>int</code>
    //@param  height es para saber el alto de tipo <code>int</code>
    public void colision(int width, int height) {       //Metodo para saber si colisiono con el applet
        if (this.getPosX() + this.getWidth() >= width) {
            this.setPosX(width - this.getWidth());
        } else if (this.getPosX() <= 0) {
            this.setPosX(0);
        }
    }
}
