package Undertaker.HospiBook.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 84000000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValideToken(String token, UserDetails userDetails){
        boolean isValid =  (extractUsername(token).equals(userDetails.getUsername())) && !isTokenEspired(token);
        if (isValid) {
            onlineUsers.add(userDetails.getUsername());
        }
        return isValid;
    }

    public boolean isTokenEspired(String token){
        boolean expired = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        if (expired){
            String username = extractUsername(token);
            onlineUsers.remove(username);
        }
        return expired;
    }

    public boolean isUserOnline(String username) {
        return onlineUsers.contains(username);
    }

    // Méthode pour obtenir la liste des utilisateurs en ligne
    public Set<String> getOnlineUsers() {
        return new HashSet<>(onlineUsers);
    }

    // Méthode pour forcer la déconnexion d'un utilisateur
    public void forceLogout(String username) {
        onlineUsers.remove(username);
    }
}
