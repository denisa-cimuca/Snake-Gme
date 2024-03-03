import java.util.Random;

public class Food {
        private final int pozitieX;
        private final int pozitieY;

        public Food(){
            pozitieX = generatePoz(Grafica.width);
            pozitieY = generatePoz (Grafica.heigth);
        }

        private int generatePoz(int size){
            Random random = new Random();
            return random. nextInt(size/Grafica.tick_size)*Grafica.tick_size;
        }

        public int getPozitieX ( ) {
            return pozitieX;
        }

        public int getPozitieY ( ) {
            return pozitieY;
        }
    }

