@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository repo;
    private final PasswordEncoder encoder;

    public GuestServiceImpl(GuestRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Guest createGuest(Guest g) {
        if (repo.existsByEmail(g.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        g.setPassword(encoder.encode(g.getPassword()));
        return repo.save(g);
    }

    public Guest getGuestById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest " + id));
    }

    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
        repo.save(g);
    }
}
