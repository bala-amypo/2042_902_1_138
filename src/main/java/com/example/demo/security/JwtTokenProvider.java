public String generateToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
    // ✅ Convert Instant to Date for JWT library
    Instant now = Instant.now();
    Instant expiryInstant = now.plusMillis(jwtExpirationMs);
    
    return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(Date.from(now))  // ✅ Convert Instant to Date
            .expiration(Date.from(expiryInstant))  // ✅ Convert Instant to Date
            .signWith(jwtSecret)
            .compact();
}