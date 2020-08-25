/*
 * Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package io.asgardio.java.saml.sdk.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.asgardio.java.saml.sdk.util.SSOAgentUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import io.asgardio.java.saml.sdk.exception.SSOAgentException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

public class LoggedInSessionBean implements Serializable {

    private static final long serialVersionUID = 7762835859870143767L;

    private SAML2SSO saml2SSO;

    public SAML2SSO getSAML2SSO() {

        return saml2SSO;
    }

    public void setSAML2SSO(SAML2SSO saml2SSO) {

        this.saml2SSO = saml2SSO;
    }

    public static class AccessTokenResponseBean implements Serializable {

        @XmlAttribute(name = "access_token")
        @SerializedName("access_token")
        private String accessToken;

        @XmlAttribute(name = "refresh_token")
        @SerializedName("refresh_token")
        private String refreshToken;

        @XmlAttribute(name = "token_type")
        @SerializedName("token_type")
        private String tokenType;

        @XmlAttribute(name = "expires_in")
        @SerializedName("expires_in")
        private String expiresIn;

        public String getAccessToken() {

            return accessToken;
        }

        public void setAccessToken(String accessToken) {

            this.accessToken = accessToken;
        }

        public String getRefreshToken() {

            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {

            this.refreshToken = refreshToken;
        }

        public String getTokenType() {

            return tokenType;
        }

        public void setTokenType(String tokenType) {

            this.tokenType = tokenType;
        }

        public String getExpiresIn() {

            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {

            this.expiresIn = expiresIn;
        }

        @Override
        public String toString() {

            Gson gson = new Gson();
            return gson.toJson(this);
        }

        public AccessTokenResponseBean deSerialize(String accessTokenResponseBeanString) {

            Gson gson = new Gson();
            return gson.fromJson(accessTokenResponseBeanString, AccessTokenResponseBean.class);
        }
    }

    public class SAML2SSO implements Serializable {

        public static final String EMPTY_STRING = "";
        private String subjectId;

        private Response response;

        private String responseString;

        private Assertion assertion;

        private String assertionString;

        private AccessTokenResponseBean accessTokenResponseBean;

        private String sessionIndex;

        private Map<String, String> subjectAttributes;

        private void writeObject(java.io.ObjectOutputStream stream)
                throws IOException {

            stream.writeObject(subjectId);

            stream.writeObject(responseString);

            stream.writeObject(assertionString);

            stream.writeObject(sessionIndex);
            if (accessTokenResponseBean != null) {
                stream.writeObject(accessTokenResponseBean.toString());
            } else {
                stream.writeObject(EMPTY_STRING);
            }
            stream.writeObject(subjectAttributes);
        }

        private void readObject(java.io.ObjectInputStream stream)
                throws IOException, ClassNotFoundException, SSOAgentException {

            subjectId = (String) stream.readObject();

            responseString = (String) stream.readObject();
            if (responseString != null && !EMPTY_STRING.equals(responseString)) {
                response = (Response) SSOAgentUtils.unmarshall(responseString);
            }

            assertionString = (String) stream.readObject();
            if (responseString != null && !EMPTY_STRING.equals(assertionString)) {
                assertion = (Assertion) SSOAgentUtils.unmarshall(assertionString);
            }

            sessionIndex = (String) stream.readObject();
            String accessTokenResponseBeanString = (String) stream.readObject();
            if (!EMPTY_STRING.equals(accessTokenResponseBeanString)) {
                accessTokenResponseBean = accessTokenResponseBean.deSerialize(accessTokenResponseBeanString);
            } else {
                accessTokenResponseBean = null;
            }
            subjectAttributes = (Map) stream.readObject();
        }

        public String getSubjectId() {

            return subjectId;
        }

        public void setSubjectId(String subjectId) {

            this.subjectId = subjectId;
        }

        public Map<String, String> getSubjectAttributes() {

            return subjectAttributes;
        }

        public void setSubjectAttributes(Map<String, String> samlSSOAttributes) {

            this.subjectAttributes = samlSSOAttributes;
        }

        public String getSessionIndex() {

            return sessionIndex;
        }

        public void setSessionIndex(String sessionIndex) {

            this.sessionIndex = sessionIndex;
        }

        public Response getSAMLResponse() {

            return response;
        }

        public void setSAMLResponse(Response samlResponse) {

            this.response = samlResponse;
        }

        public String getResponseString() {

            return responseString;
        }

        public void setResponseString(String responseString) {

            this.responseString = responseString;
        }

        public Assertion getAssertion() {

            return assertion;
        }

        public void setAssertion(Assertion samlAssertion) {

            this.assertion = samlAssertion;
        }

        public String getAssertionString() {

            return assertionString;
        }

        public void setAssertionString(String samlAssertionString) {

            this.assertionString = samlAssertionString;
        }

        public AccessTokenResponseBean getAccessTokenResponseBean() {

            return accessTokenResponseBean;
        }

        public void setAccessTokenResponseBean(AccessTokenResponseBean accessTokenResponseBean) {

            this.accessTokenResponseBean = accessTokenResponseBean;
        }
    }
}
