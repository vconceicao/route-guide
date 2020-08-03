package org.routes.mibo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class TestFile {

	public static void main(String[] args) throws MalformedURLException, URISyntaxException {

		
		URL resource = RouteGuideServer.class.getResource("/file1.txt");
		
		System.out.println(resource);
	}
}
