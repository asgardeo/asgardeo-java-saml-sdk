/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 */

package io.asgardeo.java.saml.sdk.request;

import java.util.HashMap;
import java.util.Map;

/**
 * SAML2 request data holder.
 */
public class SAML2RequestDataHolder {

    private static final SAML2RequestDataHolder INSTANCE = new SAML2RequestDataHolder();

    private final Map<String, SAML2RequestInfo> requestInfoMap = new HashMap<>();

    private SAML2RequestDataHolder() {

    }

    /**
     * Get request data holder instance.
     *
     * @return instance of SAML2RequestDataHolder
     */
    public static SAML2RequestDataHolder getInstance() {

        return INSTANCE;
    }

    /**
     * Add login request to the holder.
     *
     * @param requestId request id
     */
    public void addLoginRequest(String requestId) {

        SAML2RequestInfo saml2RequestInfo = new SAML2RequestInfo(true, System.currentTimeMillis());
        requestInfoMap.put(requestId, saml2RequestInfo);
    }

    /**
     * Add logout request to the holder.
     *
     * @param requestId request id
     */
    public void addLogoutRequest(String requestId) {

        SAML2RequestInfo saml2RequestInfo = new SAML2RequestInfo(false, System.currentTimeMillis());
        requestInfoMap.put(requestId, saml2RequestInfo);
    }

    /**
     * Check 'InResponseTo' value is valid.
     *
     * @param inResponseId InResponseTo value
     * @param isLoginRequest is this a login request
     * @return true if 'InResponseTo' value is valid
     */
    public boolean isValidInResponseToId(String inResponseId, boolean isLoginRequest) {

        return requestInfoMap.containsKey(inResponseId) && (isLoginRequest == requestInfoMap.get(inResponseId)
                .isLoginRequest());
    }

    /**
     * Remove request from holder.
     *
     * @param requestId request id
     */
    public void removeRequestId(String requestId) {

        requestInfoMap.remove(requestId);
    }

    /**
     * Clean idle requests from the holder.
     *
     * @param idleTimeOutInMinutes idle time out in minutes
     */
    public void cleanIdleRequests(int idleTimeOutInMinutes) {

        long margin = System.currentTimeMillis() - ((long) idleTimeOutInMinutes * 60 * 1000);
        requestInfoMap.entrySet().removeIf(entry -> entry.getValue().getInitiatedTime() < margin);
    }
}
