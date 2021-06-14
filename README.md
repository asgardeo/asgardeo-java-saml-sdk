# Asgardeo SAML SDK for Java

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fwso2.org%2Fjenkins%2Fjob%2Fasgardeo%2Fjob%2Fasgardeo-java-saml-sdk%2F&style=flat)](https://wso2.org/jenkins/job/asgardeo/job/asgardeo-java-saml-sdk/)
[![Stackoverflow](https://img.shields.io/badge/Ask%20for%20help%20on-Stackoverflow-orange)](https://stackoverflow.com/questions/tagged/wso2is)
[![Join the chat at https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE](https://img.shields.io/badge/Join%20us%20on-Slack-%23e01563.svg)](https://join.slack.com/t/wso2is/shared_invite/enQtNzk0MTI1OTg5NjM1LTllODZiMTYzMmY0YzljYjdhZGExZWVkZDUxOWVjZDJkZGIzNTE1NDllYWFhM2MyOGFjMDlkYzJjODJhOWQ4YjE)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/wso2/product-is/blob/master/LICENSE)
[![Twitter](https://img.shields.io/twitter/follow/wso2.svg?style=social&label=Follow)](https://twitter.com/intent/follow?screen_name=wso2)
---

The Asgardeo SAML SDK for Java enables software developers to integrate SAML based SSO authentication with Java Web
 applications. The SDK is built on top of the OpenSAML library which allows Java developers to develop cross-domain
  single sign-on and federated access control solutions with minimum hassle.

## Table of Contents
- [Installing the SDK](#installing-the-sdk)
  * [Github](#github)
  * [Building from the source](#building-from-the-source)
  * [Maven](#maven)
- [Contributing](#contributing)
  * [Reporting Issues](#reporting-issues)
- [Versioning](#versioning)
- [License](#license)

## Installing the SDK

### Github
The SDK is hosted on github. You can download it from:
- Latest release: https://github.com/asgardeo/asgardeo-java-saml-sdk/releases/latest
- Master repo: https://github.com/asgardeo/asgardeo-java-saml-sdk/tree/master/

### Building from the source

If you want to build **identity-agent-sso** from the source code:

1. Install Java 8
2. Install Apache Maven 3.x.x (https://maven.apache.org/download.cgi#)
3. Get a clone or download the source from this repository (https://github.com/asgardeo/asgardeo-java-saml-sdk.git)
4. Run the Maven command ``mvn clean install`` from the ``asgardeo-java-saml-sdk`` directory.

### Maven

Install it as a maven dependency:
```
<dependency>
    <groupId>io.asgardeo.java.saml.sdk</groupId>
    <artifactId>io.asgardeo.java.saml.sdk</artifactId>
    <version>0.1.12</version>
</dependency>
```
The SDK is hosted at the WSO2 Internal Repository. Point to the repository as follows:


```
<repositories>
    <repository>
        <id>wso2.releases</id>
        <name>WSO2 internal Repository</name>
        <url>http://maven.wso2.org/nexus/content/repositories/releases/</url>
        <releases>
            <enabled>true</enabled>
            <updatePolicy>daily</updatePolicy>
            <checksumPolicy>ignore</checksumPolicy>
        </releases>
    </repository>
</repositories>
```

## Contributing

Please read [Contributing to the Code Base](http://wso2.github.io/) for details on our code of conduct, and the
 process for submitting pull requests to us.
 
### Reporting Issues
We encourage you to report issues, improvements, and feature requests creating [git Issues](https://github.com/wso2-extensions/identity-samples-dotnet/issues).

Important: And please be advised that security issues must be reported to security@wso2.com, not as GitHub issues, 
in order to reach the proper audience. We strongly advise following the WSO2 Security Vulnerability Reporting Guidelines
 when reporting the security issues.

## Versioning

For the versions available, see the [tags on this repository](https://github.com/asgardeo/asgardeo-java-saml-sdk/tags). 

## License

This project is licensed under the Apache License 2.0 under which WSO2 Carbon is distributed. See the [LICENSE
](LICENSE) file for details.

