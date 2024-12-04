package Algoritmo;

public class Algoritmo {
    public static void main(String[] args) {
        // Resolver las Torres de Hanoi con 3 discos
        solveTowers1(3, 1, 2, 3);
    }

    // Método recursivo para resolver el problema de las Torres de Hanoi
    public static void solveTowers(int disk, int sourcePeg, int tempPeg, int destinationPeg) {
        // Caso base: si solo hay un disco, se mueve directamente al destino
        if (disk == 1) {
            System.out.printf("%n%d --> %d", sourcePeg, destinationPeg);
            return;
        }

        // Mover los discos superiores al pino temporal
        solveTowers(disk - 1, sourcePeg, destinationPeg, tempPeg);

        // Mover el disco restante al pino de destino
        System.out.printf("%n%d --> %d", sourcePeg, destinationPeg);

        // Mover los discos desde el pino temporal al destino
        solveTowers(disk - 1, tempPeg, sourcePeg, destinationPeg);
    }

    // Otra versión del método para resolver el problema de las Torres de Hanoi
    public static void solveTowers1(int disk, int source, int temp, int destination) {
        // Si hay discos para mover
        if (disk > 0) {
            // Mover discos superiores al pino temporal
            solveTowers1(disk - 1, source, destination, temp);

            // Imprimir el movimiento del disco actual
            System.out.printf("Moviendo del Pino %d al Pino %d%n", source, destination);

            // Mover discos restantes al pino de destino
            solveTowers1(disk - 1, temp, source, destination);
        }
    }
}
