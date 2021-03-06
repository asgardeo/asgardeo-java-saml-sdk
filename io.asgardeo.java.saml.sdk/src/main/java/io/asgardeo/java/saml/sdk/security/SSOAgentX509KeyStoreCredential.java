/*
 * Copyright (c) 2012, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 *
 */

package io.asgardeo.java.saml.sdk.security;

import io.asgardeo.java.saml.sdk.exception.SSOAgentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class SSOAgentX509KeyStoreCredential implements SSOAgentX509Credential {

    private static final Log log = LogFactory.getLog(SSOAgentX509KeyStoreCredential.class);
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    private X509Certificate entityCertificate = null;

    public SSOAgentX509KeyStoreCredential(KeyStore keyStore, String publicCertAlias,
                                          String privateKeyAlias, char[] privateKeyPassword)
            throws SSOAgentException {

        this(keyStore, publicCertAlias, null, privateKeyAlias, privateKeyPassword);
    }

    public SSOAgentX509KeyStoreCredential(InputStream keyStoreInputStream, char[] keyStorePassword,
                                          String publicCertAlias, String privateKeyAlias,
                                          char[] privateKeyPassword)
            throws SSOAgentException {

        this(keyStoreInputStream, keyStorePassword, publicCertAlias, null, privateKeyAlias,
                privateKeyPassword);
    }

    public SSOAgentX509KeyStoreCredential(KeyStore keyStore, String publicCertAlias, String publicCertEncoded,
                                          String privateKeyAlias, char[] privateKeyPassword)
            throws SSOAgentException {

        readX509Credentials(keyStore, publicCertAlias, publicCertEncoded, privateKeyAlias, privateKeyPassword);
    }

    public SSOAgentX509KeyStoreCredential(InputStream keyStoreInputStream, char[] keyStorePassword,
                                          String publicCertAlias, String publicCertEncoded, String privateKeyAlias,
                                          char[] privateKeyPassword)
            throws SSOAgentException {

        readX509Credentials(keyStoreInputStream, keyStorePassword, publicCertAlias, publicCertEncoded,
                privateKeyAlias, privateKeyPassword);
    }

    @Override
    public PublicKey getPublicKey() throws SSOAgentException {

        return publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() throws SSOAgentException {

        return privateKey;
    }

    @Override
    public X509Certificate getEntityCertificate() throws SSOAgentException {

        return entityCertificate;
    }

    protected void readX509Credentials(KeyStore keyStore, String publicCertAlias, String publicCertEncoded,
                                       String privateKeyAlias, char[] privateKeyPassword)
            throws SSOAgentException {

        try {
            if (StringUtils.isNotEmpty(publicCertAlias)) {
                entityCertificate = (X509Certificate) keyStore.getCertificate(publicCertAlias);
            } else if (StringUtils.isNotEmpty(publicCertEncoded)) {
                entityCertificate = inferPublicCertFromEncodedString(publicCertEncoded);
            }
        } catch (KeyStoreException e) {
            throw new SSOAgentException(
                    "Error occurred while retrieving public certificate for alias " +
                            publicCertAlias, e);
        } catch (CertificateException e) {
            throw new SSOAgentException(
                    "Error occurred while inferring public certificate from encoded string ", e);
        }
        publicKey = entityCertificate.getPublicKey();
        try {
            privateKey = (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPassword);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SSOAgentException(
                    "Error occurred while retrieving private key for alias " +
                            privateKeyAlias, e);
        }
    }

    private X509Certificate inferPublicCertFromEncodedString(String encodedCert) throws CertificateException {

        byte[] decoded = Base64.getDecoder().decode(encodedCert);
        return (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(decoded));
    }

    protected void readX509Credentials(InputStream keyStoreInputStream, char[] keyStorePassword,
                                       String publicCertAlias, String publicCertEncoded, String privateKeyAlias,
                                       char[] privateKeyPassword)
            throws SSOAgentException {

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(keyStoreInputStream, keyStorePassword);
            readX509Credentials(keyStore, publicCertAlias, publicCertEncoded, privateKeyAlias, privateKeyPassword);
        } catch (Exception e) {
            throw new SSOAgentException("Error while loading key store file", e);
        } finally {
            if (keyStoreInputStream != null) {
                try {
                    keyStoreInputStream.close();
                } catch (IOException ignored) {
                    if (log.isDebugEnabled()) {
                        log.debug("Ignoring IO Exception : ", ignored);
                    }
                    throw new SSOAgentException("Error while closing input stream of key store");
                }
            }
        }
    }
}
