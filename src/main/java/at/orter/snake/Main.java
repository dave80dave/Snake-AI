package at.orter.snake;


public class Main {
    static void main(String[] args) {
        // DE: Kleiner Test, ob das Spielfeld mit Breite und Hoehe richtig erstellt wird.
        // EN: Small test to check whether the playground is created with the correct width and height.
        Playground playground = new Playground(26,25);
        System.out.println("Zeilen: " + playground.getPlayGround().length);
        System.out.println("Spalten: " + playground.getPlayGround()[0].length);
    }
}
