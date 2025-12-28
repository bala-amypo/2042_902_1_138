@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String fullName;
    private String phoneNumber;
    private String role = "ROLE_USER";
    private Boolean active = true;
    private Boolean verified = false;

    // getters + setters
}
