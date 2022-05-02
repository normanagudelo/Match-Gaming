// Bibliografia:  https://www.youtube.com/watch?v=3yYX1QkK448&list=PLc_8_1G9IIB6WCH4jWegHO5UKnpzCitgY&index=2&ab_channel=1iberProgramador

package Todo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Juan sebastian
 */

public class Juego {
    
    // Definifion de atributos 
    JFrame ventana; 
    JPanel panelPresentacion;
    JLabel fondoPresentacion;
    JLabel botonJugar;
    //jugar
    JPanel panelJuego;
    JLabel fondoJuego;
    JLabel matriz[][];
    int mat[][];
    int matAux [][];
    String Jugador;
    Random aleatorio;
    JLabel nombreJugador;
    //cronometro del juego 
    Timer tiempo ;
    JLabel contador;
    int min;
    int seg;
    int contar;
    Timer tiempoEspera;
    int contSegEspera;
    Timer tiempoEspera1;
    int bandera;
    int bandera1;
    
    int numeroCartAnt;
    int antX;
    int antY;
    int actualNumeroCart;
    int actualX;
    int actualY;
    
    public Juego(){
        ventana = new JFrame("Juego de memoria");
        ventana.setSize(650,700);// tamaño de la ventana 
        ventana.setLayout(null); // layout absoluto según el cual los elementos se posicionan
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cierra ventana cuando se presiona X de salidad
        ventana.setLocationRelativeTo(null);// coloca la ventana en una posición relativa a un componente que le pasemos como parámetro.
        ventana.setResizable(false); // no permitir redimensionamiento , no permite pantalla completa  
        
        
        //presentacion , aqui se crea una capa que se proyecta sobre la ventana creada
        panelPresentacion = new JPanel();
        panelPresentacion.setSize(ventana.getWidth(), ventana.getHeight()); //se asignan los mismo valores del tamaño de la ventana.
        panelPresentacion.setLocation(0,0);//usa como referencia de posicion la posiccion esquina puperior izquierda
        panelPresentacion.setLayout(null);
        panelPresentacion.setVisible(true); //Activa la visibilidad 
        ventana.add(panelPresentacion,0); // se adicciona la visibilidad del panel sobre la ventana
        
        //fondo de presentacion 
        fondoPresentacion = new JLabel();
        fondoPresentacion.setSize(ventana.getWidth(), ventana.getHeight());
        fondoPresentacion.setLocation(0, 0);
        fondoPresentacion.setIcon(new ImageIcon("imagenes/fondo-juego.jpg"));//imagen de fondo del juego inicio
        fondoPresentacion.setVisible(true);
        panelPresentacion.add(fondoPresentacion,0);
        ventana.add(panelPresentacion);
        
        //boton de jugar 
        botonJugar = new JLabel();
        botonJugar.setSize(332,75);//ancho y alto  
        botonJugar.setLocation(150, 300); // ubicacion del boton
        botonJugar.setIcon(new ImageIcon("imagenes/boton-jugar.png"));
        botonJugar.setVisible(true);
        panelPresentacion.add(botonJugar,0);
        
        // se construye el panel del juego
        panelJuego = new JPanel();
        panelJuego.setSize(ventana.getWidth(), ventana.getHeight());  
        panelJuego.setLocation(0, 0);
        panelJuego.setLayout(null);
        panelJuego.setVisible(true);
        
        
        //fondo del juego
        fondoJuego = new JLabel();
        fondoJuego.setSize(ventana.getWidth(), ventana.getHeight());
        fondoJuego.setLocation(0, 0);
        fondoJuego.setIcon(new ImageIcon("imagenes/fondo-juego.jpg"));//imagen fondo de juego con cartas
        fondoJuego.setVisible(true);
        panelJuego.add(fondoJuego,0);
        
        
        //nombre del jugador 
        
        nombreJugador = new JLabel();
        nombreJugador.setSize(150,20);
        nombreJugador.setLocation(30, 30);
        nombreJugador.setForeground(Color.white); //color del texto que se presenta
        nombreJugador.setVisible(true);
        panelJuego.add(nombreJugador,0);
        
        // tiempo
        contador = new JLabel();
        contador.setSize(150,20);
        contador.setLocation(ventana.getWidth()-200, 30);
        contador.setForeground(Color.white);
        contador.setVisible(true);
        panelJuego.add(contador,0);
        
        
   
        //matriz logica  
        //20 imagenes para cambiar el tamaño de los tarjetas cambiar numero de columnas 
        mat = new int [4][5];
        matAux = new int [4][5];
        aleatorio = new Random();
        this.numerosAleatorios(); // se invoca el metodo generador de numeros aleatorios
        
        
         //matriz de imagenes 
        matriz = new JLabel[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) { //se construye el arreglo de la matriz de cartas
                matriz [i][j] = new JLabel();
                matriz [i][j].setBounds( 90+(j*100), 90+(i*120) , 80, 100); // se utiliza para definir el rectángulo delimitador de un componente y se define alto 100 ancho 80
                matriz[i][j].setIcon(new ImageIcon("imagenes/"+ matAux[i][j]+".png"));// invoca la imagen que va a cargar de acuerdo al valor aleatorio que tiene la matriz
                matriz[i][j].setVisible(true); //visibiliza la matriz
               panelJuego.add(matriz[i][j],0); 
            }
        }
        
        // tiempo o contador
        
        min = 0;
        seg = 0;
        // el timer contabiliza el tiempo desde el momento que se ejecuta el panel
        tiempo =  new Timer (1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                seg ++;
                if (seg == 60) {
                    min++; // pasados 60seg cuenta un minuto mas
                    seg=0; // resetea el contador
                }
                contador.setText("Tiempo: "+min+":"+seg); //imprime el tiempo desde 
            }
        }
        );
        
        // tiempo de espera entre carta y carta seleccionada
        contSegEspera =0;
        tiempoEspera =  new Timer (1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               contSegEspera ++;
            }
        } 
        );
        tiempoEspera.start();
        tiempoEspera.stop();
        contSegEspera = 0;
        bandera = 0;
        bandera1 = 0;
              
        // click cartas evento para las tarjetas 
        contar = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                matriz [i][j].addMouseListener(new MouseAdapter() { //está atento a escuchar evento de cuando se presione sobre una de las cartas de la matriz
                    public void mousePressed(MouseEvent e) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l < 5; l++) {  
                                if (e.getSource()== matriz[k][l]) {//se aplica un barrido for sobre toda la matriz y se hace validacion celda a celda
                                                                     
                                    if (matAux[k][l]==0 && contar !=2) { // se verifica que no hayan mas de dos cartas volteadas
                                    matAux[k][l]= mat [k][l]; // se alimenta la matriz auxiliar con el valor de la matriz principal  en la misma posicion 
                                    matriz[k][l].setIcon(new ImageIcon("imagenes/"+ matAux[k][l]+".png"));  // Se toma el valor que tenga la matAux, y se construye la imagen invocando con el nombre de la imagen de la carta.
                                    contar ++;
                                    actualNumeroCart = mat[k][l];
                                    actualX = k;
                                    actualY = l;
                                    if (contar == 1) {
                                           numeroCartAnt= mat [k][l];
                                            antX = k;
                                            antY = l ; 
                                        }
                     
                                    tiempoEspera1 =  new Timer (1000, new ActionListener() //se prepara el contador 
                                    {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                         
                                         
                                            if (contar == 2 && bandera1 == 0 ) {
                                               tiempoEspera.restart(); //reinicia el contador si ya hay dos cartas volteadas
                                               bandera1 = 1;
                                            }   
                                        if (contar == 2 && contSegEspera ==2 ) {
                                            tiempoEspera.stop();
                                            contSegEspera = 0;
                                            if (matAux[actualX][actualY]== matAux[antX][antY]) { //Aqui se compara si los valores de las celdas de la matriz en la primera y segunda eleccion son iguales, en caso de haber una pareja igual se cambia el valor por -1
                                                matAux[actualX][actualY]= -1; 
                                                matAux [antX][antY] = -1;
                                                matriz[actualX][actualY].setIcon(new ImageIcon("imagenes/"+ matAux[actualX][actualY]+".png")); //Este codigo busca una imagen de nombre -1.png que no va a encontrar quedando el espacio vacio en el panel, es decir , desapareciendo las parejas. 
                                                matriz[antX][antY].setIcon(new ImageIcon("imagenes/"+ matAux[antX][antY]+".png"));
                                                contar=0; // se reinicia el conteo
                                                // ganer 
                                                int acumulador = 0;
                                                for (int m = 0; m < 4; m++) {
                                                    for (int n = 0; n < 5; n++) {
                                                        if (matAux[m][n]== -1) 
                                                         acumulador ++;   // cumple con la funcion de contar cuantas celdas de la matriz están con valor de -1 . 
                                                        }
                                                }
                                                if(acumulador == 20) // Dará aviso de ganador cuando se hayan completado todos las celdas.
                                                {
                                                    tiempo.stop();
                                                    JOptionPane.showInputDialog(ventana, "FELICIDADES HAS GANADO CON UN TIEMPO DE : "+min+": "+seg);
                                                    
                                                     //fondo del juego cuando ya ha ganado
                                                    
                                                    fondoJuego.setSize(ventana.getWidth(), ventana.getHeight());
                                                    fondoJuego.setLocation(0, 0);
                                                    fondoJuego.setIcon(new ImageIcon("imagenes/victoria.jpg"));//imagen fondo de juego con cartas
                                                    fondoJuego.setVisible(true);
                                                    panelJuego.add(fondoJuego,0);
                                                    
                                                    for (int m = 0; m < 4; m++) {
                                                        for (int n = 0; n < 5; n++) {
                                                             mat[m][n]= 0;
                                                             matAux[m][n]= 0;
                                                             matriz[m][n].setIcon(new ImageIcon("imagenes/"+ matAux[m][n]+".png"));
                                                        }
                                                    }
                                                    numerosAleatorios();
                                                    min=0;
                                                    seg=0;
                                                    tiempo.restart();
                                                    
                                                }
                                                
                                            }
                                            for (int m = 0; m < 4; m++) { //
                                                for (int n = 0; n < 5; n++) {
                                                    if (matAux[m][n]!=0 && matAux[m][n]!=-1) {
                                                       matAux[m][n]= 0; //voltea las cartas de toda la matriz 
                                                       matriz[m][n].setIcon(new ImageIcon("imagenes/"+ matAux[m][n]+".png")); // se proyectan todas las cartas con valor de interrogantes. 
                                                       contar=0;
                                                    }
                                                }
  
                                            }
                                            tiempoEspera1.stop();
                                            bandera1=0;
                                        }
                                       }}); 
                                        if (bandera == 0) {
                                            tiempoEspera1.start();
                                            bandera= 1;
                                        }
                                        if(contar==2)
                                         tiempoEspera1.restart();
                                        }
                                    
                                    
                                }
                            }
                        }
                    }
                });
            }
        }
        
        
        
        
        
        
        
        
        
        // funcionalidad del boton 
        botonJugar.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) //pedira el nombre del jugador
            {
                Jugador = JOptionPane.showInputDialog(ventana,"Nombre del jugador", "Escribe aqui");
                while(Jugador == null || Jugador.compareTo("Escribe aqui")==0 || Jugador.compareTo("")==0){
                    Jugador = JOptionPane.showInputDialog(ventana,"Nombre del jugador", "Escribe aqui");// se continua preguntando hasta que ingrese un nombre para el jugador
                }
                                
               
                nombreJugador.setText("Jugador: " + Jugador );
                tiempo.start();
                panelPresentacion.setVisible(false);
                ventana.add(panelJuego);
                panelJuego.setVisible(true);
            }
        });
               
        
        
        
        
        ventana.add(panelPresentacion);
        ventana.setVisible(true);
    }
    
    public void numerosAleatorios(){
       int acumular = 0 ; 
        
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 5; j++) {
                mat [i][j]= 0;
                matAux [i][j]= 0; 
                }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                mat[i][j] = aleatorio.nextInt(10)+1;
                do{
                    acumular = 0;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 5; l++) {
                            if(mat[i][j]==mat[k][l]){
                                acumular += 1;

                            }
                        } 
                    }
                    if(acumular == 3 )
                    {
                       mat[i][j] = aleatorio.nextInt(10)+1; 
                    }
                }while(acumular == 3);
                
            }
        }
        
    }
    
    
}
