package breakingbad;
//hola

/**
 *
 * @author Oscar Abraham Rodriguez Quintanilla, Arturo Armando Gonzalez
 * Fernandez
 */
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Font;

public class Juego extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public Juego() throws IOException {
        init();
        start();
    }

    private Walter walter;
    private Bueno heroe;                //Objeto tipo Bueno
    private Pelota bola;                 //objeto tipo bomba
    private Graphics dbg;               //Objeto tipo Graphics
    private Image dbImage;              //Imagen para el doblebuffer  
    private Poderes poder;              //imagen para el poder extra
    private long tiempoActual;          //Long para el tiempo del applet
    private SoundClip chChoco;          //audio para el heroe
    private SoundClip chFalla;          //audio para las paredes
    private SoundClip tituloSonido;     //audio para el titulo
    private SoundClip nivelSonido;          //audio para el primer y segundo nivel
    private SoundClip bossSonido;       //audio para el jefe
    private SoundClip hit;              //audio cuando pega
    private SoundClip powerUp;          //audio cuando agarra el poder

    private Image im_over;              //imagen del gameover
    private Image informacion;          //imagen de la informacion
    private Image titulo;               //imagen del principio
    private Image start;                //imagen del "press start"
    private Image fondo;                //imagen del fondo del nivel 1
    private Image fondo2;               //imagen del fondo del nivel 2
    private Image fondo3;               //imagen del fondo del nivel 3
    private Image fin;                  //imagen del gameover
    private Image comp;                 //imagen cuando se completa el juego
    private Image finW;                 //imagen cuando pierdes el juego

    private LinkedList<Meth> cubos;     //lista de los bloques para destruir del nivel 1
    private LinkedList<Meth> cubos2;    //lista de los bloques para destruir del nivel 1
    private long timer;                 //tiempo para los poderes
    private String letrero;             //letrero

    private int tituloMov;              //velocidad de animacion cuando empieza el juego
    private int direccion;              //entero para la direccion
    private int vidas;                  //entero para las vidas
    private int vx;                     //velocidad en x
    private int vy;                     //velocidad en
    private int cont;                   //contador
    private int randompoder;            //numero random para que el poder aparesca

    private boolean movimiento;         //Booleano si esta en movimient
    private boolean bolamueve;         //Booleano para saber si se mueve la bomba
    private boolean pausa;              //Booleando para pausa
    private boolean gameover;           //flag para el gameover
    private boolean empezar;            //flag para empezar
    private boolean sound;              //flag para saber si se hace sonido
    private boolean info;               //flag para saber si se despliega la informacion
    private boolean inclinado;          //flag para el movimiento
    private boolean lanzada;            //flag para saber si se lanza la pelota
    private boolean nivel1;             //flag para saber el nivel
    private boolean nivel2;             //flag para saber el nivel
    private boolean nivel3;             //flag para saber el nivel
    private boolean caer;               //flag para checar si cayo al piso
    private int tipo1;                  //flag para la direccion
    private int tipo2;                  //flag para la direccion
    private boolean ganaste;            //flag para saber si acabo el juego
    private boolean tipo3;              //flag para la direccion
    private int decision;               //numero para escoger el poder
    private boolean letr;               //flag para el letrero
    private boolean poderactivo;        //flag para saber si esta activo el poder
    private int pegadaswalter;          //numero de veces que se le pega al malo
    private boolean tSonido;            //flag para el sonido del titulo
    private boolean nSonido;            //flag para el sonido del nivel 1 y 2
    private boolean bSonido;            //flag para el sonido del jefe

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        pegadaswalter = 3;     //vidas del malo son 3
        ganaste = false;
        decision = -1;
        poderactivo = false;
        letr = false;
        tipo1 = 1;
        tipo2 = 1;
        poder = new Poderes(-100, -100, 0);
        walter = new Walter(-200, -200, 0, 0);
        caer = false;
        randompoder = 0;
        nivel1 = true;
        this.setSize(1024, 683);
        cubos = new LinkedList();       //Se crea una lista de los bloques del nivel 1
        cubos2 = new LinkedList();      //Se crea una lista de los bloques del nivel 2
        addKeyListener(this);           //Uso de las teclas
        addMouseListener(this);          //Uso de las teclas
        addMouseMotionListener(this);      //Uso de las teclas
        //setBackground(Color.BLACK);     //fondo negra

        //heroe y bola
        bola = new Pelota(30, 330, 0, 0);   //Se pone la pelota
        heroe = new Bueno(0, 0);            //Se pone la camioneta
        heroe.setPosX((this.getWidth() / 2) - (new ImageIcon(heroe.getImagen())).getIconWidth() / 2);   //posicion x del Bueno
        heroe.setPosY(this.getHeight() - (new ImageIcon(heroe.getImagen())).getIconHeight() - 2);    //posicion y del Bueno
        bola.setPosX(heroe.getPosX() + heroe.getWidth() / 2 - (bola.getWidth() / 2));               //posicion x de la pelota
        bola.setPosY(heroe.getPosY() - bola.getHeight());                                               //posicion y de la pelota

        //Sonidos
        chChoco = new SoundClip("Sounds/chocaHeroe.wav");           //Sonido cuando choca
        chFalla = new SoundClip("Sounds/chocaPared.wav");           //Sonido cuando falla
        tituloSonido = new SoundClip("Sounds/tituloSonido.wav");    //Sonido del titulo
        nivelSonido = new SoundClip("Sounds/nivelSonido.wav");      //Sonido del nivel1
        bossSonido = new SoundClip("Sounds/bossSonido.wav");        //Sonido del nivel ultimo
        powerUp = new SoundClip("Sounds/PowerUp.wav");              //Sonido del poder
        hit = new SoundClip("Sounds/hit.wav");                      //Sonido cuando al pelota choca con el camion
        //
        //Imagenes
        titulo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/titulo.png")); //Imagen del titulo
        start = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/start3.gif")); //Imagen del "press start"
        fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/RV.jpg"));     //Imagen del camion
        fondo2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/fondo2.jpg")); //Imagen del nivel2
        fondo3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/fondo3.jpg")); //Imagen del nivel3
        fin = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/gameover.jpg")); //Imagen del del gameover
        comp = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/completo.jpg")); //Imagen del juego completo
        URL gURL = this.getClass().getResource("Images/finwalter.gif");      //gif del final
        im_over = Toolkit.getDefaultToolkit().getImage(gURL);
        URL iURL = this.getClass().getResource("Images/info.jpg");             //gif del juego completo
        informacion = Toolkit.getDefaultToolkit().getImage(iURL);
        URL fURL = this.getClass().getResource("Images/fin.gif");               // //Imagen de las instrucciones
        finW = Toolkit.getDefaultToolkit().getImage(fURL);
       //

        //ints
        vidas = 3;                      //Se empieza con 3 vidas
        direccion = 0;                  //Se inicializa a 0 la direccion (no se mueve)
        tituloMov = 0;                  //velocidad del titulo al principio es 0
        vx = 0;                         //velocidad de la pelota en x al principio es 0
        vy = 0;                         //velocidad de la pelota en y al principio es 0
        //

        //booleans son falsos hasta que se haga la accion requerida exepto el sonido
        info = false;
        sound = true;
        gameover = false;
        movimiento = false;             // al principi esta quieto
        empezar = false;
        nivel2 = false;
        nivel3 = false;
        lanzada = false;
        inclinado = false;
        tSonido = true;
        nSonido = false;
        bSonido = false;

        for (int i = 80; i < this.getWidth() - 80; i += 92) {  //Se crea una cantidad de bloques para el nivel 1
            for (int j = 100; j < 300; j += 68) {   //48 58
                cubos.add(new Meth(i, j));
            }

        }

        for (int i = 80; i < this.getWidth() - 80; i += 52) {   //Se crea una cantidad de bloques para el nivel 2
            for (int j = 100; j < 300; j += 68) {   //48
                cubos2.add(new Meth(i, j));
            }
        }

    }

    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        while (true) {
            if (!pausa) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void actualiza() {
        //if(movimiento){
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        //Actualiza la animación en base al tiempo transcurrido
        if (bolamueve) {
            (bola.getImagenes()).actualiza(tiempoActual);
            bola.setPosX(bola.getPosX() + vx);
            bola.setPosY(bola.getPosY() + vy);

        }

        if (movimiento) {//Si se mueve se actualiza
            (heroe.getImagenes()).actualiza(tiempoActual);

        }
        //Se coloca a la camioneta en la posicion
        heroe.setPosX(heroe.getPosX() + direccion * tipo1);
        if (!lanzada) {
            bola.setPosX(bola.getPosX() + direccion * tipo1);
        }

        //cuando la bomba sale por abajo
        if (bola.getPosY() >= this.getHeight()) {
            if (sound) {
                chFalla.play();
            }
            vidas--;
            lanzada = false;
            bolamueve = false;
            bola.setPosX(heroe.getPosX() + heroe.getWidth() / 2 - (bola.getWidth() / 2));
            bola.setPosY(heroe.getPosY() - bola.getHeight());
        }
        if (bola.getWidth() + bola.getPosX() >= this.getWidth() || bola.getPosX() < 0) {
            vx = -vx;
        }
        //cuando la bomba pega en el techo
        if (bola.getPosY() < 0) {
            vy = -vy;
        }
        //cuando la bomba cae
        if (caer) {
            (poder.getImagenes()).actualiza(tiempoActual);
            poder.setPosY(poder.getPosY() + poder.getVelocidad());
            if (poder.intersecta(heroe)) {
                if (sound) {
                    powerUp.play();
                }
                letr = true;
                poderactivo = true;
                timer = 0;
                decision = ((int) (Math.random() * 3));
                if (decision == 0) {
                    tipo1 = 2;
                    letrero = "Velocidad Aumentada";
                }
                if (decision == 1) {
                    tipo2 = 2;
                    letrero = "Puntaje al Doble";
                }
                if (decision == 2) {
                    vidas++;
                    letrero = "Vida Extra";
                    tipo3 = true;
                }
            }
        }

        //cuando las vidas llegan a 0
        if (vidas <= 0) {
            gameover = true;
        }
        //si se presiona la letra 'e' el titulo se mueve a la derecha
        if (empezar) {
            tituloMov += 10;
        }
        //checa si la lista del nivel 1 esta vacia y pasa al nivel 2
        if (cont == 0) {
            if (cubos.isEmpty() && !ganaste && !gameover && !nivel3) {
                nivel2 = true;
                lanzada = false;
                bola.setPosX(heroe.getPosX() + heroe.getWidth() / 2 - (bola.getWidth() / 2));
                bola.setPosY(heroe.getPosY() - bola.getHeight());
                bolamueve = false;
                cont++;
                nivel1 = false;
            }
        }
        //checa si la lista del nivel 2 esta vacia y pasa al nivel 3
        if (cubos2.isEmpty() && !nivel3 && !ganaste && !gameover && !nivel1) {
            nivel3 = true;
            nivel2 = false;
            walter.setPosX(150);
            walter.setPosY(150);
            walter.setVelocidadX(10);
            walter.setVelocidadY(3);
            lanzada = false;
            bola.setPosX(heroe.getPosX() + heroe.getWidth() / 2 - (bola.getWidth() / 2));
            bola.setPosY(heroe.getPosY() - bola.getHeight());
            bolamueve = false;
        }
        //verifica el tiempo del poder
        if (timer < 750 && poderactivo) {
            timer += 1;
            if (tipo3 && timer > 100) {
                poderactivo = false;
            }
        } else {
            timer = 0;
            poderactivo = false;
            letr = false;
            tipo1 = 1;
            tipo2 = 1;
            tipo3 = false;
        }

        //verifica si ya empezo el nivel 3
        if (nivel3) {
            if (walter.getPosX() == -150) {
                walter.setPosX(150);
                walter.setPosY(150);
                walter.setVelocidadX(10);
                walter.setVelocidadY(3);
            }
            if (walter.getPosX() + walter.getWidth() > this.getWidth()) {
                walter.setVelocidadX(-10);
            }
            if (walter.getPosX() < 0) {
                walter.setVelocidadX(10);
            }
            if (walter.getPosY() + walter.getHeight() > 450) {
                walter.setVelocidadY(-3);
            }
            if (walter.getPosY() < 150) {
                walter.setVelocidadY(3);
            }
            walter.setPosX(walter.getPosX() + walter.getVelocidadX());
            walter.setPosY(walter.getPosY() + walter.getVelocidadY());
        }
        if (tSonido && sound) {
            tituloSonido.play2();
        } else {
            tituloSonido.stop();
        }
        if (!tSonido) {
            tituloSonido.stop();
        }
        if (nSonido && sound) {
            nivelSonido.play2();
        } else {
            nivelSonido.stop();
        }
        if (nivel3) {
            nivelSonido.stop();
        }
        if (nivel3 && sound) {
            bossSonido.play2();
        } else {
            bossSonido.stop();
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        heroe.colision(this.getWidth(), this.getHeight());        //Checa colision del heroe con el applet
        if (direccion != 0) {
            movimiento = true;
        } else {
            movimiento = false;
        }

        //checa todas las colisiones del nivel 2
        if (nivel2) {
            walter.setVelocidadX(0);
            walter.setVelocidadY(0);
            walter.setPosX(-100);
            walter.setPosY(-100);
            for (int i = 0; i < cubos2.size(); i++) {
                Meth actual = (Meth) cubos2.get(i);
                if (actual.intersecta(bola)) {
                    int centrox = bola.getPosX() + (bola.getWidth() / 2);
                    int centroy = bola.getPosY() + (bola.getHeight() / 2);
                    if (centroy < actual.getPosY() && vy > 0) {
                        vy = -vy;
                    }
                    if (centroy > actual.getPosY() + actual.getHeight() && vy < 0) {
                        vy = -vy;
                    }
                    if (centrox < actual.getPosX() && vx > 0) {
                        vx = -vx;
                    }
                    if (centrox > actual.getPosX() + actual.getWidth() && vx < 0) {
                        vx = -vx;
                    }
                    randompoder = ((int) (Math.random() * 7));
                    if (randompoder == 4 && !caer && !poderactivo) {
                        caer = true;
                        poder.setPosX(actual.getPosX() + 5);
                        poder.setPosY(actual.getPosY() + 5);
                        poder.setVelocidad(2);
                    }
                    cubos2.remove(i);
                    if (sound) {
                        chChoco.play();
                    }
                    bola.setScore(bola.getScore() + 10 * tipo2);

                }
            }
        }
        //checa todas las colisiones del nivel 1
        if (nivel1) {
            walter.setVelocidadX(0);
            walter.setVelocidadY(0);
            walter.setPosX(-100);
            walter.setPosY(-100);
            for (int i = 0; i < cubos.size(); i++) {
                Meth actual = (Meth) cubos.get(i);
                if (actual.intersecta(bola)) {
                    int centrox = bola.getPosX() + (bola.getWidth() / 2);
                    int centroy = bola.getPosY() + (bola.getHeight() / 2);
                    if (centroy < actual.getPosY() && vy > 0) {
                        vy = -vy;
                    }
                    if (centroy > actual.getPosY() + actual.getHeight() && vy < 0) {
                        vy = -vy;
                    }
                    if (centrox < actual.getPosX() && vx > 0) {
                        vx = -vx;
                    }
                    if (centrox > actual.getPosX() + actual.getWidth() && vx < 0) {
                        vx = -vx;
                    }

                    randompoder = ((int) (Math.random() * 6));
                    if (randompoder == 1 && !caer && !poderactivo) {
                        caer = true;
                        poder.setPosX(actual.getPosX() + 5);
                        poder.setPosY(actual.getPosY() + 5);
                        poder.setVelocidad(2);
                    }
                    if (sound) {
                        chChoco.play();
                    }
                    bola.setScore(bola.getScore() + 10 * tipo2);
                    cubos.remove(i);
                }
            }
        }
        //cuando el poder toca la camioneta
        if (poder.intersecta(heroe) || poder.getPosY() > this.getHeight()) {
            caer = false;
            poder.setPosX(-100);
            poder.setPosY(-100);
            poder.setVelocidad(0);
        }
        //cuando la camioneta le pega a la bola
        if (heroe.intersecta(bola)) {
            if (sound) {
                hit.play();
            }
            vy = -vy;
            int centro = bola.getPosX() + (bola.getWidth() / 2);
            if ((centro >= heroe.getPosX() + (heroe.getWidth() / 4)) && (centro <= heroe.getPosX() + ((heroe.getWidth() / 4) * 3))) {
                if (inclinado) {
                    vx = vx / 2;
                    inclinado = false;
                }
            }
            //cuando la camioneta pega en una esquina del cuadro
            if (centro < heroe.getPosX() + (heroe.getWidth() / 4)) {
                if (centro < heroe.getPosX() + (heroe.getWidth() / 8)) {
                    if (vx > 0) {
                        vx = -1 * (vx * 2);
                        if (inclinado) {
                            vx = vx / 2;
                        }
                        inclinado = true;
                    }
                } else {
                    if (vx > 0) {
                        vx = -vx;
                    }
                    if (inclinado) {
                        vx = vx / 2;
                        inclinado = false;
                    }
                }
            }
            //cuando la camioneta pega en una esquina del cuadro
            if (centro > heroe.getPosX() + ((heroe.getWidth() / 4) * 3)) {
                if (centro > heroe.getPosX() + ((heroe.getWidth() / 8) * 7)) {
                    if (vx < 0) {
                        vx = -1 * (vx * 2);
                        if (inclinado) {
                            vx = vx / 2;
                        }
                        inclinado = true;
                    }
                } else {
                    if (vx < 0) {
                        vx = -vx;
                    }
                    if (inclinado) {
                        vx = vx / 2;
                        inclinado = false;
                    }
                }
            }
        }
        //checa todas las colisiones del nivel 3
        if (nivel3) {
            if (bola.intersecta(walter)) {
                if (sound) {
                    chChoco.play();
                }
                lanzada = false;
                bola.setPosX(heroe.getPosX() + heroe.getWidth() / 2 - (bola.getWidth() / 2));
                bola.setPosY(heroe.getPosY() - bola.getHeight());
                walter.setPosX(150);
                walter.setPosY(150);
                bolamueve = false;
                pegadaswalter--;
                if (pegadaswalter <= 0) {
                    ganaste = true;
                    nivel3 = false;
                }
            }
        }

        //cuando se termina el juego se elimina todas las listas
        if (ganaste || gameover) {
            nivel1 = false;
            nivel2 = false;
            nivel3 = false;
            for (int i = 0; i < cubos.size(); i++) {
                cubos.remove(i);
            }
            for (int i = 0; i < cubos2.size(); i++) {
                cubos2.remove(i);
            }
            walter.setVelocidadX(0);
            walter.setVelocidadY(0);
            walter.setPosX(-100);
            walter.setPosY(-100);
        }

        //bomba.colision(this.getHeight(), this.getHeight());
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        // Presiono izq
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movimiento = true;
            direccion = -7;
        } //Presiono der
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movimiento = true;
            direccion = 7;
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {  //cuando el usuario presiona p
            if (pausa) {
                pausa = false;
            } else {
                pausa = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {  //cuando el usuario presiona s
            if (!sound) {
                sound = true;
            } else {
                sound = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {  //cuando el usuario presiona i
            if (!info) {
                info = true;
                pausa = true;
            } else {
                info = false;
                pausa = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direccion = 0;
        } //Presiono der
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_N) { //cuando el usuario presiona n el juego empieza otra vez
            if (gameover || ganaste) {
                cont = 0;
                vidas = 3;
                bola.setScore(0);
                nivel1 = true;
                gameover = false;
                ganaste = false;
                walter.setPosX(-150);
                walter.setPosY(-150);
                walter.setVelocidadX(0);
                walter.setVelocidadY(0);
                for (int i = 80; i < this.getWidth() - 80; i += 92) {  //42 52
                    for (int j = 100; j < 300; j += 68) {   //48 58
                        cubos.add(new Meth(i, j));
                    }
                }

                for (int i = 80; i < this.getWidth() - 80; i += 52) {  //42
                    for (int j = 100; j < 300; j += 68) {   //48
                        cubos2.add(new Meth(i, j));
                    }
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_E) {  //cuando el usuario presiona e
            if (!empezar) {
                empezar = true;
                tSonido = false;
                nSonido = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) { //cuando el usuario presiona la barra de espacio
            if (!lanzada && empezar) {
                bolamueve = true;
                vx = 4 + (int) (Math.random() * 2);
                if ((int) (Math.random() * 2) == 1) {
                    vx = -vx;
                }
                vy = -1 * (5 + (int) (Math.random() * 2));
                lanzada = true;
            }
        }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (nivel1 && !ganaste) { //imagen del juego completo
            g.drawImage(fondo, 0, 0, this);
        }
        if (nivel2 && !ganaste) { //imagen del juego completo
            g.drawImage(fondo2, 0, 0, this);
        }
        if (nivel3 && !ganaste) { //imagen del juego completo
            g.drawImage(fondo3, 0, 0, this);
        }
        if (heroe != null && bola != null && titulo != null) {
            if (gameover) { //imagen del gameover
                g.drawImage(fin, 0, 0, this);
                g.drawImage(im_over, 280, 210, this);
                g.setColor(Color.WHITE);
                g.drawString("Presiona 'n' para", 20, 350);
                g.drawString("empezar otra vez", 20, 370);
                g.drawString("Puntaje: " + bola.getScore(), 20, 470);
            }
            if (ganaste) { //imagen del juego completo
                g.drawImage(comp, 0, -15, this);
                g.drawImage(finW, 280, 210, this);
                g.setColor(Color.WHITE);
                g.drawString("Presiona 'n' para", 20, 350);
                g.drawString("empezar otra vez", 20, 370);
                g.drawString("Puntaje: " + bola.getScore(), 20, 470);

            } else {
                if (info) { //imagen de las instrucciones
                    g.drawImage(informacion, 0, 0, this);
                } else {
                    //Dibuja la imagen en la posicion actualizada
                    if (!(ganaste || gameover)) {
                        g.drawImage(heroe.getImagen(), heroe.getPosX(), heroe.getPosY(), this);
                        g.drawImage(bola.getImagen(), bola.getPosX(), bola.getPosY(), this);
                    }
                    if (!lanzada && (!(ganaste || gameover))) { //imagen del la instruccion para lanzae
                        g.setColor(Color.WHITE);
                        g.drawString("Presiona espacio para lanzar", (this.getWidth() / 2) - 160, (this.getHeight() / 2) + 60);
                    }
                    if (pausa) { //imagen del pausa
                        g.setColor(Color.WHITE);
                        g.drawString("" + heroe.getPAUSADO(), heroe.getPosX() - heroe.getWidth() / 7, heroe.getPosY() + (heroe.getHeight() / 2));
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Puntaje: " + bola.getScore(), 20, 60);
                    g.drawString("Vidas    : " + vidas, 20, 90);
                    if (!nivel2) {
                        for (int i = 0; i < cubos.size(); i++) {
                            g.drawImage(((Meth) cubos.get(i)).getImagen(), ((Meth) cubos.get(i)).getPosX(), ((Meth) cubos.get(i)).getPosY(), this);
                        }
                    }
                    if (caer) { //imagen cuando se cae la pelota y aparece arriba del camion
                        g.drawImage(poder.getImagen(), poder.getPosX(), poder.getPosY(), this);
                    }
                    if (letr && (!(ganaste || gameover))) {
                        g.setColor(Color.BLACK);
                        g.drawString(letrero, (this.getWidth() / 2) - 100, this.getHeight() / 2);
                        g.setColor(Color.WHITE);
                    }
                    if (nivel2) { //imagen del fondo del nivel 2
                        for (int i = 0; i < cubos2.size(); i++) {
                            g.drawImage(((Meth) cubos2.get(i)).getImagen(), ((Meth) cubos2.get(i)).getPosX(), ((Meth) cubos2.get(i)).getPosY(), this);
                        }
                    }

                    if (tituloMov < this.getWidth()) { //imagen del titulo
                        g.drawImage(titulo, tituloMov, 0, this);
                        g.drawImage(start, tituloMov + 600, 100, this);
                        g.drawString("Presione -e- para empezar", tituloMov + 625, 300);
                    }
                }

            }
            if (sound) { //imagen del sonido
                g.drawString("Sonido ON", this.getWidth() - 180, 60);
            } else {
                g.drawString("Sonido OFF", this.getWidth() - 180, 60);
            }

        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }

        if (nivel3) { //imagen del fondo del nivel 3
            g.drawImage(walter.getImagen(), walter.getPosX(), walter.getPosY(), this);
        }

        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

}
