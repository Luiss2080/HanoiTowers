package torreshanoi; // Paquete de la clase Torre

import java.util.Stack; // Importa la clase Stack de Java

public class Torre {
    private Stack<Discos> disks = new Stack<Discos>(); // Pila que almacena los discos de la torre

    public Stack<Discos> getDisks() {
        return disks; // Retorna la pila de discos
    }

    public void addDisk(Discos disk) {
        disks.push(disk); // Agrega un disco a la torre
    }

    public Discos removeDisk() {
        if (!disks.isEmpty()) {
            return disks.pop(); // Remueve y retorna el disco de la cima
        } else {
            return null; // Si está vacía, retorna null
        }
    }

    public boolean isEmpty() {
        return disks.isEmpty(); // Retorna true si la torre está vacía
    }
    
    public Discos peekDisk() {
        if (!disks.isEmpty()) {
            return disks.peek(); // Retorna el disco en la cima sin removerlo
        } else {
            return null; // Si está vacía, retorna null
        }
    }
}
