@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @PostMapping
    public Guest create(@RequestBody Guest guest) {
        return service.createGuest(guest);
    }

    @GetMapping("/{id}")
    public Guest get(@PathVariable Long id) {
        return service.getGuestById(id);
    }
}
