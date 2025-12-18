@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    private final RoomBookingService service;

    public RoomBookingController(RoomBookingService service) {
        this.service = service;
    }

    @PostMapping
    public RoomBooking create(@RequestBody RoomBooking booking) {
        return service.createBooking(booking);
    }
}
