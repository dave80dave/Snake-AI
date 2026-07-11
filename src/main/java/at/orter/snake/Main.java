package at.orter.snake;


public class Main {
    static void main(String[] args) {
        Playground playground = new Playground(26,25);
        System.out.println("Zeilen: " + playground.getPlayGround().length);
        System.out.println("Spalten: " + playground.getPlayGround()[0].length);
    }
}
