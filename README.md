# Example CSOD Reporting API code

## Introduction

This project provides sample code for authenticating and consuming Cornerstone OnDemand's (CSOD) [Job Requisition API](https://apiexplorer.csod.com/apiconnectorweb/apiexplorer#/apidoc/7f1beda8-ec8a-41ad-a615-417d27d8e568).

It utilizes CSOD's OAuth 2.0 authentication to obtain an access token via provided ClientId and ClientSecret.

It also provides an example of the paging functionality of CSOD APIs.

## Provisioning Access and Configuring the Project

1. Follow directions in the API documentation to [register an application with OAuth 2.0](https://apiexplorer.csod.com/apiconnectorweb/apiexplorer#/info).
    1. You will be provided with a *ClientId* and *ClientSecret*.
1. Next, modify `src/main/java/com/csod/edge/api/samples/ConsoleApp.java` file and replace:
    1. *CLIENT_ID* value with the value from step 1
    1. *CLIENT_SECRET* value with the value from Step 1
    1. The `DOMAIN` section of *API_BASE_ADDRESS* value with your client's portal URL

As an example, your modified file would appear as follows:

```java
    private static final String API_BASE_ADDRESS = "http://acme.csod.com:81";
    private static final String CLIENT_ID = "test";
    private static final String CLIENT_SECRET = "asasdf3241234va";
```

## Building the Project

Open the repo with your Java IDE of choice and build the project.  `src/main/java/com/csod/edge/api/samples/ConsoleApp.java` is the entry point of the solution, and the solution is configured to generate a simple console application.

Running the project will then authenticate with CSOD's OAuth framework, call the API, and call the Job Requisition details endpoint.

## Source file descriptions

* `ConsoleApp.java` - This class requires following fields to call the Job Requisition API.
* `EdgeApiClient.java` - This is a helper class which sending HTTP requests and receiving HTTP responses from a resource identified by a URI.
* `HttpHeaders.java` - This class contains the properties for Request Headers like Accept, Authorization & preference
