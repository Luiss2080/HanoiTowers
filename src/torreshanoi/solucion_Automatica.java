package torreshanoi;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la solución automática para las Torres de Hanoi
 * utilizando recursividad.
 */
public class solucion_Automatica {
    private List<Movimiento> movimientos;
    private int totalMovimientos;
    
    // Clase interna para representar un movimiento
    public static class Movimiento {
        private int origen;
        private int destino;
        private int disco;
        
        public Movimiento(int origen, int destino, int disco) {
            this.origen = origen;
            this.destino = destino;
            this.disco = disco;
        }
        
        public int getOrigen() { return origen; }
        public int getDestino() { return destino; }
        public int getDisco() { return disco; }
        
        @Override
        public String toString() {
            return "Mover disco " + disco + " desde Torre " + 
                   (origen + 1) + " hacia Torre " + (destino + 1);
        }
    }
    
    public solucion_Automatica() {
        movimientos = new ArrayList<>();
    }
    
    //Inicia el proceso de solución para un número específico de discos
    public List<Movimiento> resolverTorres(int numDiscos) {
        movimientos.clear();
        totalMovimientos = 0;
        resolverTorresHanoi(numDiscos, 0, 2, 1);
        return movimientos;
    }
    
    //Método recursivo para resolver las Torres de Hanoi
    private void resolverTorresHanoi(int n, int origen, int destino, int auxiliar) {
        if (n == 1) {
            // Caso base: mover un disco directamente al destino
            movimientos.add(new Movimiento(origen, destino, n));
            totalMovimientos++;
        } else {
            // Caso recursivo:
            // 1. Mover n-1 discos a la torre auxiliar
            resolverTorresHanoi(n - 1, origen, auxiliar, destino);
            
            // 2. Mover el disco n al destino
            movimientos.add(new Movimiento(origen, destino, n));
            totalMovimientos++;
            
            // 3. Mover los n-1 discos desde la torre auxiliar al destino
            resolverTorresHanoi(n - 1, auxiliar, destino, origen);
        }
    }
    
    // Obtiene el número total de movimientos necesarios
    public int getTotalMovimientos() {
        return totalMovimientos;
    }
    
    // Calcula el número mínimo de movimientos necesarios para n discos
    public static int calcularMovimientosMinimos(int numDiscos) {
        return (int) (Math.pow(2, numDiscos) - 1);
    }
    
    // Obtiene una representación textual de la solución
    public String obtenerSolucionTexto(int numDiscos) {
        resolverTorres(numDiscos);
        StringBuilder solucion = new StringBuilder();
        solucion.append("Solución para ").append(numDiscos).append(" discos:\n");
        solucion.append("Número mínimo de movimientos necesarios: ")
               .append(calcularMovimientosMinimos(numDiscos)).append("\n\n");
        
        for (int i = 0; i < movimientos.size(); i++) {
            solucion.append("Paso ").append(i + 1).append(": ")
                   .append(movimientos.get(i).toString()).append("\n");
        }
        
        return solucion.toString();
    }
}
