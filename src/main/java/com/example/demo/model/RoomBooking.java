@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean active = true;

    @ManyToOne
    private Guest guest;

    @ManyToMany
    private Set<Guest> roommates = new HashSet<>();

    // getters + setters
}
