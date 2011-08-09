package com.xsis.security.service.impl;

import com.xsis.security.util.Md5Token;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:26:50 PM
 */
public class PasswordEncoderImpl implements PasswordEncoder, Serializable {
    public PasswordEncoderImpl() {
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object token) {

        if (encPass == null) {
            return false;
        }

        if (token == null) {
            return encPass.equals(rawPass);
        }

        if (rawPass.length() <= Md5Token.TOKEN_LENGTH) {
            return false;
        }

        String newRawPass = StringUtils.left(rawPass, rawPass.length() - Md5Token.TOKEN_LENGTH);
        if (encPass.equals(newRawPass)) {
            String rawToken = StringUtils.right(rawPass, Md5Token.TOKEN_LENGTH);
            Md5Token md5Token = (Md5Token) token;
            return md5Token.isEqualsToken(rawToken);
        }
        return false;
    }

    @Override
    public String encodePassword(String rawPass, Object token) throws DataAccessException {
        throw new RuntimeException("Methode wird nicht unterstatzt!");
    }
}
