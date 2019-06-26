Dev Guide
--------------------------------------------
ConsoleApp.java - This class requires following fields to call the REST API.

		API_BASE_ADDRESS :  User/developer should provide domain URL for example (https://[CORPNAME].csod.com) for Production, (https://[CORPNAME]-Pilot.csod.com) for Pilot & (https://[CORPNAME]-stg.csod.com) for Stage
		CLIENT_ID : This can be get/generated from login to Cornerstone. 
		CLIENT_SECRET : This can be get/generated from login to Cornerstone. 
		GRANT_TYPE : The default value should be "client_credentials".
		SCOPE : The default value should be "all".

After providing relevant values to above fields "AccessToken" is generated which is used to call the REST API.


EdgeApiClient.java - This is a helper class which sending HTTP requests and receiving HTTP responses from a resource identified by a URI.

HttpHeaders.java - This class contains the properties for Request Headers like Accept & Authorization.

Note: Do not change values for GRANT_TYPE & SCOPE.

