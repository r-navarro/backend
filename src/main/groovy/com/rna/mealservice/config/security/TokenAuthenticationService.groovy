package com.rna.mealservice.config.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

@Service
class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 864_000_000 // 10 days
    private static final String SECRET = "ThisIsASecret"
    public static final String SESSION_STRING = "token"

    static void addAuthentication(HttpServletRequest request, String username) {
        def jwtToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
        def session = request.getSession()
        session.setAttribute(SESSION_STRING, jwtToken)
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        //println "************************ $request.session.id"
        String token = request.getSession().getAttribute(SESSION_STRING)
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject()

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                    null
        }
        return null
    }
}
