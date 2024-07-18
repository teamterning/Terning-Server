package org.terning.terningserver.service;

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

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.exception.CustomException;

import static org.terning.terningserver.exception.enums.ErrorMessage.INVALID_KEY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppleServiceImpl implements AppleService{

    private final ValueConfig valueConfig;

    @Override
    public String getAppleData(String authAccessToken) {
        val publicKeyList = getApplePublicKeys();
        val publicKey = makePublicKey(authAccessToken, publicKeyList);

        val userInfo = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(getTokenFromBearerString(authAccessToken))
                .getBody();

        val userInfoObject = (JsonObject)JsonParser.parseString(new Gson().toJson(userInfo));
        return userInfoObject.get(ValueConfig.ID).getAsString();
    }

    private JsonArray getApplePublicKeys() {
        val connection = sendHttpRequest();
        val result = getHttpResponse(connection);
        val keys = (JsonObject)JsonParser.parseString(result.toString());
        return (JsonArray)keys.get(ValueConfig.KEY);
    }

    private HttpURLConnection sendHttpRequest() {
        try{
            val url = new URL(valueConfig.getAppleUri());
            val connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder getHttpResponse(HttpURLConnection connection) {
        try {
            val bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return splitResponse(bufferedReader);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private StringBuilder splitResponse(BufferedReader bufferedReader) {
        try{
            val result = new StringBuilder();

            String line;
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                result.append(line);
            }
            bufferedReader.close();

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey makePublicKey(String accessToken, JsonArray publicKeyList) {
        val decodeArray = accessToken.split(ValueConfig.TOKEN_VALUE_DELIMITER);
        val header = new String(Base64.getDecoder().decode(getTokenFromBearerString(decodeArray[0])));
        val kid = ((JsonObject) JsonParser.parseString(header)).get(ValueConfig.KID_HEADER_KEY);
        val alg = ((JsonObject) JsonParser.parseString(header)).get(ValueConfig.ALG_HEADER_KEY);

        val matchingPublicKey = findMatchingPublicKey(publicKeyList, kid, alg);
        if (Objects.isNull(matchingPublicKey)) {
            throw new CustomException(INVALID_KEY);
        }
        return getPublicKey(matchingPublicKey);
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }

    private JsonObject findMatchingPublicKey(JsonArray publicKeyList, JsonElement kid, JsonElement alg) {
        for (JsonElement publicKey : publicKeyList) {
            val publicKeyObject = publicKey.getAsJsonObject();
            val publicKid = publicKeyObject.get(ValueConfig.KID_HEADER_KEY);
            val publicAlg = publicKeyObject.get(ValueConfig.ALG_HEADER_KEY);

            if (Objects.equals(kid, publicKid) && Objects.equals(alg, publicAlg)) {
                return publicKeyObject;
            }
        }

        return null;
    }

    private PublicKey getPublicKey(JsonObject object) {
        try {
            val modulus = object.get(ValueConfig.MODULUS).toString();
            val exponent = object.get(ValueConfig.EXPONENT).toString();

            val quotes = ValueConfig.QUOTES;
            val modulusBytes = Base64.getUrlDecoder().decode(modulus.substring(quotes, modulus.length() - quotes));
            val exponentBytes = Base64.getUrlDecoder().decode(exponent.substring(quotes, exponent.length() - quotes));

            val positiveNumber = ValueConfig.POSITIVE_NUMBER;
            val modulusValue = new BigInteger(positiveNumber, modulusBytes);
            val exponentValue = new BigInteger(positiveNumber, exponentBytes);

            val publicKeySpec = new RSAPublicKeySpec(modulusValue, exponentValue);
            val keyFactory = KeyFactory.getInstance(ValueConfig.RSA);

            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
            throw new CustomException(INVALID_KEY);
        }
    }

}
