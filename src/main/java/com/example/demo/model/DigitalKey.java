@Entity
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyValue;
    private Instant issuedAt;
    private Instant expiresAt;
    private Boolean active = true;

    @ManyToOne
    private RoomBooking booking;

    // getters + setters
}
