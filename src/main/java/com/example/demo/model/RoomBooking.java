@Entity
@Getter @Setter
public class RoomBooking {
    @Id @GeneratedValue
    private Long id;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean active = true;

    @ManyToOne
    private Guest guest;

    @ManyToMany
    private Set<Guest> roommates = new HashSet<>();
}
