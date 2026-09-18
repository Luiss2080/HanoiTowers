package torreshanoi;

/**
 * Punto de entrada del juego (main.class del proyecto y Main-Class del JAR).
 *
 * TorresHannoi extiende javafx.application.Application; cuando la clase con
 * main() extiende Application y JavaFX va en el classpath (no como modulo), el
 * JDK aborta con "faltan los componentes de JavaFX". Esta clase, que no extiende
 * Application, delega en ella y permite arrancar tambien con "java -jar".
 */
public class Launcher {
    public static void main(String[] args) {
        TorresHannoi.main(args);
    }
}
