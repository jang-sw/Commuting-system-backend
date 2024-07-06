package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.example.demo.config.Constant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class CryptoUtil {

	
	private final String alg = "AES/CBC/PKCS5Padding";
    private final String key = "11111111111111111111111111111111";
    private final String iv = key.substring(0, 16);

    public String AESEncrypt(String text)  {
    	try {
    		Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
    	} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }

    public String AESDecrypt(String cipherText){
    	try {
			Cipher cipher = Cipher.getInstance(alg);
	        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
	        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
	        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
	        byte[] decrypted = cipher.doFinal(decodedBytes);
	        return new String(decrypted, "UTF-8");
	    } catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	    	
    }
    public String encodeSHA512(String planeText) {
    	try {
    		planeText = planeText + "salt";

    		String encodingText = "";
    		MessageDigest md = MessageDigest.getInstance("SHA-512");
    		md.update(planeText.getBytes(StandardCharsets.UTF_8));
    		encodingText = DatatypeConverter.printBase64Binary(md.digest());
    		return encodingText;
    	}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    public String getToken(String key) {
		String subject = UUID.randomUUID().toString();
		Map< String, Object> jti = new HashMap<>();
		try {
			jti.put("d", AESEncrypt(key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "Bearer " + Jwts.builder()
			.setClaims(jti)
	        .setSubject(subject)
	        .setExpiration(new Date(System.currentTimeMillis() + 1800000)) 
	        .signWith(SignatureAlgorithm.HS512, Constant.SECRET_KEY)
	        .compact();
	}
}
