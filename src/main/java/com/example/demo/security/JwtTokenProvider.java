import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;

public String generateToken(Authentication authentication) {

    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

    // ⚠️ TEMP SIMPLE TOKEN (for compilation & testing)
    // Replace with real JWT logic later
    return userPrincipal.getUsername() + "_token";
}
