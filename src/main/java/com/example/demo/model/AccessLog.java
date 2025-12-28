@Entity
@Getter @Setter
public class AccessLog {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne private DigitalKey digitalKey;
    @ManyToOne private Guest guest;

    private Instant accessTime;
    private String result;
}
