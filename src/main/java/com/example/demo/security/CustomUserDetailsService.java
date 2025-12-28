@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private GuestRepository repo;

    public UserDetails loadUserByUsername(String email) {
        Guest g = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new CustomUserDetails(
                g.getId(),
                g.getEmail(),
                g.getPassword(),
                List.of(new SimpleGrantedAuthority(g.getRole()))
        );
    }
}
