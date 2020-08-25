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

package io.asgardio.java.saml.sdk.util;

import org.opensaml.saml.common.xml.SAMLConstants;
import io.asgardio.java.saml.sdk.bean.SSOAgentConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOAgentRequestResolver {

    SSOAgentConfig ssoAgentConfig = null;
    HttpServletRequest request = null;

    public SSOAgentRequestResolver(HttpServletRequest request, HttpServletResponse response,
                                   SSOAgentConfig ssoAgentConfig) {

        this.request = request;
        this.ssoAgentConfig = ssoAgentConfig;
    }

    public boolean isSLORequest() {

        return ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                request.getParameter(SSOAgentConstants.SAML2SSO.HTTP_POST_PARAM_SAML2_AUTH_REQ) != null;
    }

    // This could be either SAML Response for a SSO SAML Request by the client application
    // or a SAML Response for a SLO SAML Request from a SP
    public boolean isSAML2SSOResponse() {

        return ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                request.getParameter(SSOAgentConstants.SAML2SSO.HTTP_POST_PARAM_SAML2_RESP) != null;
    }

    public boolean isSAML2ArtifactResponse() {

        return ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                request.getParameter(SSOAgentConstants.SAML2SSO.SAML2_ARTIFACT_RESP) != null;
    }

    public boolean isSLOURL() {

        return ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                ssoAgentConfig.getSAML2().isSLOEnabled() &&
                request.getRequestURI().endsWith(ssoAgentConfig.getSAML2().getSLOURL());
    }

    public boolean isHttpPostBinding() {

        return ssoAgentConfig.getSAML2().getHttpBinding() != null &&
                SAMLConstants.SAML2_POST_BINDING_URI.equals(
                        ssoAgentConfig.getSAML2().getHttpBinding());
    }

    public boolean isSAML2SSOURL() {

        return ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                request.getRequestURI().endsWith(ssoAgentConfig.getSAML2SSOURL());
    }

    public boolean isPassiveAuthnRequest() {

        return (ssoAgentConfig.isSAML2SSOLoginEnabled() &&
                (request.getSession(false) == null ||
                        request.getSession(false).getAttribute(SSOAgentConstants.SESSION_BEAN_NAME) == null));
    }

    public boolean isURLToSkip() {

        return ssoAgentConfig.getSkipURIs().contains(request.getRequestURI());
    }
}
