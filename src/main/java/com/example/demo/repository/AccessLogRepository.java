public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findByGuestId(Long id);
    List<AccessLog> findByDigitalKeyId(Long id);
}
