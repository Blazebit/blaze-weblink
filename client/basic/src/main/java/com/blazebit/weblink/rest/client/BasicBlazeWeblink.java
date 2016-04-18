package com.blazebit.weblink.rest.client;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.client.BlazeWeblinkClient;

public class BasicBlazeWeblink {

	public static BlazeWeblink getInstance(String serverUrl, String username, String password) {
		return BlazeWeblinkClient.getInstance(serverUrl, new BasicAuthFilter(username, password));
	}
}
