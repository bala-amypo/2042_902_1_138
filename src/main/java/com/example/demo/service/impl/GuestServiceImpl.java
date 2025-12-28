@Service
public class GuestServiceImpl {

    private final GuestRepository repo;
    private final PasswordEncoder encoder;

    public GuestServiceImpl(GuestRepository r, PasswordEncoder e) {
        this.repo = r;
        this.encoder = e;
    }

    public Guest createGuest(Guest g) {
        if (repo.existsByEmail(g.getEmail()))
            throw new IllegalArgumentException("Email already");
        g.setPassword(encoder.encode(g.getPassword()));
        return repo.save(g);
    }

    public Guest getGuestById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("" + id));
    }

    public Guest updateGuest(Long id, Guest d) {
        Guest g = getGuestById(id);
        g.setFullName(d.getFullName());
        g.setPhoneNumber(d.getPhoneNumber());
        g.setVerified(d.getVerified());
        g.setActive(d.getActive());
        g.setRole(d.getRole());
        return repo.save(g);
    }

    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
        repo.save(g);
    }

    public List<Guest> getAllGuests() {
        return repo.findAll();
    }
}
