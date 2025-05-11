package org.terning.terningserver.auth.application.social.apple;

import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.config.ValueConfig;
import org.terning.terningserver.common.exception.CustomException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.INVALID_KEY;

@Component
@RequiredArgsConstructor
public class AppleAuthTokenValidator {
    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final String BEARER_HEADER = "Bearer ";
    private static final String BLANK = "";
    private static final String MODULUS = "n";
    private static final String EXPONENT = "e";
    private static final String KID_HEADER_KEY = "kid";
    private static final String ALG_HEADER_KEY = "alg";
    private static final String RSA = "RSA";
    private static final String KEY = "keys";
    private static final String ID = "sub";
    private static final int QUOTES = 1;
    private static final int POSITIVE_NUMBER = 1;

    private final ValueConfig valueConfig;

    public String extractAppleId(String authAccessToken) {
        JsonArray publicKeyList = getApplePublicKeys();
        PublicKey publicKey = makePublicKey(authAccessToken, publicKeyList);

        Claims userInfo = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(getTokenFromBearerString(authAccessToken))
                .getBody();

        JsonObject userInfoObject = (JsonObject) JsonParser.parseString(new Gson().toJson(userInfo));
        return userInfoObject.get(ID).getAsString();
    }

    private JsonArray getApplePublicKeys() {
        HttpURLConnection connection = sendHttpRequest();
        StringBuilder result = getHttpResponse(connection);
        JsonObject keys = (JsonObject) JsonParser.parseString(result.toString());
        return (JsonArray) keys.get(KEY);
    }

    private HttpURLConnection sendHttpRequest() {
        try {
            URL url = new URL(valueConfig.getAppleUri());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder getHttpResponse(HttpURLConnection connection) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return splitResponse(bufferedReader);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private StringBuilder splitResponse(BufferedReader bufferedReader) {
        try {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey makePublicKey(String accessToken, JsonArray publicKeyList) {
        String[] decodeArray = accessToken.split(TOKEN_VALUE_DELIMITER);
        String header = new String(Base64.getDecoder().decode(getTokenFromBearerString(decodeArray[0])));
        JsonObject headerObject = (JsonObject) JsonParser.parseString(header);

        JsonElement kid = headerObject.get(KID_HEADER_KEY);
        JsonElement alg = headerObject.get(ALG_HEADER_KEY);

        JsonObject matchingPublicKey = findMatchingPublicKey(publicKeyList, kid, alg);
        if (Objects.isNull(matchingPublicKey)) {
            throw new CustomException(INVALID_KEY);
        }
        return getPublicKey(matchingPublicKey);
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(BEARER_HEADER, BLANK);
    }

    private JsonObject findMatchingPublicKey(JsonArray publicKeyList, JsonElement kid, JsonElement alg) {
        for (JsonElement publicKey : publicKeyList) {
            JsonObject publicKeyObject = publicKey.getAsJsonObject();
            JsonElement publicKid = publicKeyObject.get(KID_HEADER_KEY);
            JsonElement publicAlg = publicKeyObject.get(ALG_HEADER_KEY);

            if (Objects.equals(kid, publicKid) && Objects.equals(alg, publicAlg)) {
                return publicKeyObject;
            }
        }
        return null;
    }

    private PublicKey getPublicKey(JsonObject object) {
        try {
            String modulus = object.get(MODULUS).getAsString();
            String exponent = object.get(EXPONENT).getAsString();

//            byte[] modulusBytes = Base64.getUrlDecoder().decode(modulus.substring(QUOTES, modulus.length() - QUOTES));
            byte[] modulusBytes = Base64.getUrlDecoder().decode(modulus);
//            byte[] exponentBytes = Base64.getUrlDecoder().decode(exponent.substring(QUOTES, exponent.length() - QUOTES));
            byte[] exponentBytes = Base64.getUrlDecoder().decode(exponent);

            BigInteger modulusValue = new BigInteger(POSITIVE_NUMBER, modulusBytes);
            BigInteger exponentValue = new BigInteger(POSITIVE_NUMBER, exponentBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulusValue, exponentValue);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);

            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
            throw new CustomException(INVALID_KEY);
        }
    }
}
