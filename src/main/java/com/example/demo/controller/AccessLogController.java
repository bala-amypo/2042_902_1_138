@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {

    private final AccessLogService service;

    public AccessLogController(AccessLogService service) {
        this.service = service;
    }

    @PostMapping
    public AccessLog log(@RequestBody AccessLog log) {
        return service.saveLog(log);
    }
}
