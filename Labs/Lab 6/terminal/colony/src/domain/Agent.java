package domain;

/**
 * La clase abstracta Agent representa una entidad en un estado desconocido, viva o muerta.
 * Los agentes tienen un estado, una edad y pueden moverse en el espacio.
 */
public abstract class Agent {
    public final static char UNKNOWN = 'u', ALIVE = 'a', DEAD = 'd';
    protected char state;
    private int age;

    /**
     * Crea un nuevo agente con un estado desconocido y edad cero.
     */
    public Agent() {
        state = UNKNOWN;
        age = 0;
    }

    /**
     * El agente envejece un ciclo de vida.
     */
    protected void turn() {
        age++;
    }

    /**
     * El agente se mueve en el espacio. Debe ser implementado por las subclases.
     */
    public abstract void move();

    /**
     * Obtiene la edad del agente.
     * @return Valor entero que representa la edad del agente.
     */
    public final int getAge() {
        return age;
    }

    /**
     * Comprueba si el agente está vivo.
     * @return `true` si el agente está vivo (en estado ALIVE), `false` en caso contrario.
     */
    public final boolean isAlive() {
        return (state == Agent.ALIVE);
    }
}
