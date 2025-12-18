@RestController
@RequestMapping("/api/keys")
public class DigitalKeyController {

    private final DigitalKeyService service;

    public DigitalKeyController(DigitalKeyService service) {
        this.service = service;
    }

    @PostMapping("/{bookingId}")
    public DigitalKey generate(@PathVariable Long bookingId) {
        return service.generateKey(bookingId);
    }
}
