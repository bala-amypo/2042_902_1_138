@Entity
@Getter @Setter
public class KeyShareRequest {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne private DigitalKey digitalKey;
    @ManyToOne private Guest sharedBy;
    @ManyToOne private Guest sharedWith;

    private Instant shareStart;
    private Instant shareEnd;
}
