public interface KeyShareRequestRepository extends JpaRepository<KeyShareRequest, Long> {
    List<KeyShareRequest> findBySharedById(Long id);
    List<KeyShareRequest> findBySharedWithId(Long id);
}
