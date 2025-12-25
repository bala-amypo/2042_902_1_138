@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management")
public class GuestController {

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @PostMapping
    public Guest create(@RequestBody Guest g) {
        return service.createGuest(g);
    }

    @GetMapping("/{id}")
    public Guest get(@PathVariable Long id) {
        return service.getGuestById(id);
    }
}
