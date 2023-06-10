package com.example.backend.model.user.security;

import com.example.backend.application.dto.TokenInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JWT {

    private static final String SECRET_KEY = "015f189b-7075-4fea-a87b-adb3f5e88a3d015f189b-7075-4fea-a87b-adb3f5e88a3d";
    private static final long EXPIRATION_TIME = 3_600_000;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String createToken(TokenInfo info) throws Exception {
        try {
            JwtBuilder builder = Jwts.builder()
                    .setSubject(MAPPER.writeValueAsString(info)).setExpiration(new Date(new Date().getTime() + EXPIRATION_TIME)).
                    signWith(SignatureAlgorithm.HS256,
                            new SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName()));
            return "Bearer " + builder.compact();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public static TokenInfo verifyToken(String token) throws JsonProcessingException {
        String info = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build().parseClaimsJws(token).getBody().getSubject();
        return MAPPER.readValue(info, TokenInfo.class);
    }

}
