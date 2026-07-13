package com.coworking.util;

public class ApiPaths {

	public static final String API_V1 = "/api/v1";

	public static final String AUTH = API_V1 + "/auth";

	public static final String SPACES = API_V1 + "/spaces";

	public static final String RESERVATIONS = API_V1 + "/reservations";
	
	public static final String REPORTS = API_V1 + "/reports";

	private ApiPaths() {
		throw new IllegalStateException("Creation not allowed");
	}

}
