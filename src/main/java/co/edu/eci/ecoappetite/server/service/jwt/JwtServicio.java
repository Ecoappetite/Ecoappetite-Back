package co.edu.eci.ecoappetite.server.service.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class JwtServicio implements ValidadorJwt {

    @Value("${ecoappetite.jwt.secret}")
    private String secret;

    @Value("${ecoappetite.jwt.expiration}")
    private Long expiracionToken;

    private SecretKey key;

    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        
    }

    public String crearToken(){

        return Jwts.builder()
            .subject("usuario123")
            .issuer("ecoappetite")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiracionToken)) // 1 hora
            .signWith(key, Jwts.SIG.HS256)
            .compact();
    }

    @Override
    public Boolean validarToken(String token) throws DataValidationException{
        try{
            return obtenerClaims(token).getIssuer().equals("ecoappetite");



        }catch( JwtException e){
            throw new DataValidationException("Token no válido");

        }
        
    }

    private Claims obtenerClaims (String token){

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }


    
}
