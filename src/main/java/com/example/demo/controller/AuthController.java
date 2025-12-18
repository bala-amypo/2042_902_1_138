@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public String register() {
        return "registered";
    }

    @PostMapping("/login")
    public String login() {
        return "logged in";
    }
}
