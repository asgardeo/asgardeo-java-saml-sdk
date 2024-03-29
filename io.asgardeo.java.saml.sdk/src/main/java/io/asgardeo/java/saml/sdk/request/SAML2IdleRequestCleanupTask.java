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

import io.asgardeo.java.saml.sdk.util.SSOAgentConstants;

import java.util.logging.Logger;

/**
 * SAML2 idle request cleanup task.
 */
public class SAML2IdleRequestCleanupTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SSOAgentConstants.LOGGER_NAME);
    // Default idle time is one day.
    private int idleTimeOutInMinutes = 1440;

    public SAML2IdleRequestCleanupTask(int idleTimeOutInMinutes) {

        if (idleTimeOutInMinutes > 0) {
            this.idleTimeOutInMinutes = idleTimeOutInMinutes;
        }
    }

    @Override
    public void run() {

        SAML2RequestDataHolder.getInstance().cleanIdleRequests(idleTimeOutInMinutes);
        LOGGER.info("SAML2 idle request cleanup task executed successfully.");
    }
}
