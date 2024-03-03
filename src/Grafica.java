import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Grafica extends JPanel implements ActionListener {
    static final int width = 1000;
    static final int heigth = 500;
    static final int tick_size = 50;
    static final int board_size = (width*heigth)/(tick_size*tick_size);

    final Font font = new Font("Arial",Font.BOLD,30);

    int[] snakePozitieX = new int[board_size];
    int[] snakePozitieY = new int[board_size];
    int snakeLength;

    Food food;
    int foodEaten;


    char directie = 'R';
    boolean isMoving = false; //nu este in miscare
    final Timer timer = new Timer(150,this);
public Grafica(){
    this.setPreferredSize (new Dimension (width,heigth));
    this.setBackground ( (Color.WHITE) );
    this.setFocusable ( true );
    this.addKeyListener ( new KeyAdapter ( ) {
        @Override
        public void keyPressed ( KeyEvent e ) {
            //super.keyPressed ( e );
            if(isMoving){
               //modificarea directiei
               switch (e.getKeyCode ()) {
                   case KeyEvent.VK_LEFT:
                       if ( directie != 'R' ) {
                           directie = 'L';
                       }
                       break;
                   case KeyEvent.VK_RIGHT:
                       if ( directie != 'L' ) {
                           directie = 'R';
                       }
                       break;
                   case KeyEvent.VK_UP:
                       if ( directie != 'D' ) {
                           directie = 'U';
                       }
                       break;
                   case KeyEvent.VK_DOWN:
                       if ( directie != 'U' ) {
                           directie = 'D';
                       }
                       break;
               }
            } else{
                start();
            }
        }
    });

    start();
}

protected void start(){
    snakePozitieX = new int[board_size];
    snakePozitieY = new int[board_size];
    snakeLength = 5;

    foodEaten = 0;

    directie = 'R';
    isMoving = true;

    spawnFood();

    timer.start();
}

    @Override
    protected void paintComponent ( Graphics g ) {
        super.paintComponent ( g );

        if(isMoving){
            g.setColor ( new Color ( 160,32,240 ) );
            g.fillOval (food.getPozitieX (), food.getPozitieY ( ) , tick_size, tick_size);

            g.setColor(Color.BLACK);
            for(int i=0;i<snakeLength;i++){
                g.fillRect ( snakePozitieX[i],snakePozitieY[i],tick_size,tick_size);
            }
        } else {
            String scoreText = String.format("Sfarsit... Scor: %d ... Apasati orice tasta pentru a reda din nou!",foodEaten);
            g.setColor ( (Color.BLACK) );
            g.setFont (font);
            g.drawString(scoreText,(width-getFontMetrics(g.getFont()).stringWidth ( scoreText ))/2, heigth/2);
        }
    }

    protected void move(){
    for (int i = snakeLength; i>0;i--){
        snakePozitieX[i] = snakePozitieX[i-1];
        snakePozitieY[i] = snakePozitieY[i-1];
    }

    switch(directie){
        case 'U' -> snakePozitieY[0] -= tick_size;
        case 'D' -> snakePozitieY[0] += tick_size;
        case 'L' -> snakePozitieX[0] -= tick_size;
        case 'R' -> snakePozitieX[0] += tick_size;
    }
    }
    protected void spawnFood(){
    food = new Food();
    }

    protected void eatFood(){
    if((snakePozitieX[0] == food.getPozitieX ()) && (snakePozitieY[0] == food.getPozitieY ())){
       snakeLength++;
       foodEaten++;
       spawnFood ();
        }

    }
    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ( ( snakePozitieX[0] == snakePozitieX[i] ) && ( snakePozitieY[0] == snakePozitieY[i] ) ) {
                isMoving = false;
                break;
            }
        }

        if ( snakePozitieX[0] < 0 || snakePozitieX[0] > width * tick_size || snakePozitieY[0] < 0 || snakePozitieY[0] > width * tick_size ) {
            isMoving = false;
        }

        if(!isMoving){
            timer.stop();
        }
        }




    @Override
    public void actionPerformed ( ActionEvent e ) {
       if(isMoving){
        move();
        collisionTest ();
        eatFood ();
       }

        repaint (  );
    }
}
