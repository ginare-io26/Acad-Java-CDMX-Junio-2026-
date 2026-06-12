import java.util.*;

// =====================================
// ENUM PRIORITY
// =====================================
enum Priority {

    LOW(1, 48),
    MEDIUM(2, 24),
    HIGH(3, 8),
    CRITICAL(4, 1);

    private final int level;
    private final int responseTimeHours;

    Priority(int level, int responseTimeHours) {
        this.level = level;
        this.responseTimeHours = responseTimeHours;
    }

    public int getLevel() {
        return level;
    }

    public int getResponseTimeHours() {
        return responseTimeHours;
    }

    public String getLabel() {
        return String.format(
                "%s (Nivel %d, Respuesta: %dh)",
                name(),
                level,
                responseTimeHours
        );
    }
}

// =====================================
// ENUM STATUS
// =====================================
enum TicketStatus {

    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean canTransitionTo(TicketStatus target) {

        return switch (this) {

            case OPEN ->
                    target == IN_PROGRESS;

            case IN_PROGRESS ->
                    target == RESOLVED ||
                            target == OPEN;

            case RESOLVED ->
                    target == CLOSED ||
                            target == IN_PROGRESS;

            case CLOSED ->
                    false;
        };
    }
}

// =====================================
// TICKET
// =====================================
class Ticket {

    private final int id;
    private final String description;
    private final Priority priority;
    private TicketStatus status;

    public Ticket(int id,
                  String description,
                  Priority priority) {

        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = TicketStatus.OPEN;
    }

    public void transitionTo(TicketStatus newStatus) {

        if (status.canTransitionTo(newStatus)) {

            System.out.printf(
                    "Ticket %d: %s -> %s%n",
                    id,
                    status,
                    newStatus
            );

            status = newStatus;

        } else {

            System.out.printf(
                    "Error: No se puede transicionar de %s a %s%n",
                    status,
                    newStatus
            );
        }
    }

    public int getId() {
        return id;
    }

    public Priority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {

        return String.format(
                "Ticket{id=%d, desc='%s', priority=%s, status=%s}",
                id,
                description,
                priority.getLabel(),
                status
        );
    }
}

// =====================================
// MAIN
// =====================================
public class TicketSystem {

    public static void main(String[] args) {

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(
                new Ticket(
                        1,
                        "Login falla",
                        Priority.CRITICAL));

        tickets.add(
                new Ticket(
                        2,
                        "Boton desalineado",
                        Priority.LOW));

        tickets.add(
                new Ticket(
                        3,
                        "Error en pagos",
                        Priority.HIGH));

        tickets.add(
                new Ticket(
                        4,
                        "Mejorar docs",
                        Priority.MEDIUM));

        // =====================================
        // TODOS LOS TICKETS
        // =====================================

        System.out.println("=== Todos los Tickets ===");

        tickets.forEach(System.out::println);

        // =====================================
        // TRANSICIONES
        // =====================================

        System.out.println("\n=== Transiciones ===");

        tickets.get(0)
                .transitionTo(TicketStatus.IN_PROGRESS);

        tickets.get(2)
                .transitionTo(TicketStatus.IN_PROGRESS);

        tickets.get(2)
                .transitionTo(TicketStatus.RESOLVED);

        // inválida
        tickets.get(2)
                .transitionTo(TicketStatus.OPEN);

        // =====================================
        // ESTADO ACTUALIZADO
        // =====================================

        System.out.println("\n=== Estado Actualizado ===");

        tickets.forEach(System.out::println);

        // =====================================
        // ENUMMAP
        // =====================================

        System.out.println("\n=== Dashboard (EnumMap) ===");

        EnumMap<TicketStatus, Integer> conteo =
                new EnumMap<>(TicketStatus.class);

        for (TicketStatus s : TicketStatus.values()) {
            conteo.put(s, 0);
        }

        for (Ticket ticket : tickets) {

            TicketStatus estado =
                    ticket.getStatus();

            conteo.put(
                    estado,
                    conteo.get(estado) + 1
            );
        }

        conteo.forEach((status, count) ->
                System.out.printf(
                        "  %s: %d%n",
                        status,
                        count
                ));

        // =====================================
        // ENUMSET
        // =====================================

        System.out.println(
                "\n=== Tickets Urgentes (EnumSet) ===");

        EnumSet<Priority> urgentes =
                EnumSet.of(
                        Priority.HIGH,
                        Priority.CRITICAL
                );

        for (Ticket ticket : tickets) {

            if (urgentes.contains(
                    ticket.getPriority())) {

                System.out.println(ticket);
            }
        }
    }
}