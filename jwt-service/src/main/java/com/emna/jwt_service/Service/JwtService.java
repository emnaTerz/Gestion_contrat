package com.emna.jwt_service.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String getTokenFromRequest(HttpServletRequest request); // Ajout de cette méthode
    Date extractExpiration(String token);

    boolean isTokenExpired(String token);


}