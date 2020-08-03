package org.routes.mibo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.google.protobuf.util.JsonFormat;

import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.FeatureDatabase;

public class RouteGuideUtil {

	public static URL getDefaultFeaturesFiles() {
		// TODO Auto-generated method stub
		
		return RouteGuideServer.class.getResource("/route_guide_db.json");
	}

	public static List<Feature> parseFeatures(URL featureFile) throws IOException {
		InputStream inputStream = featureFile.openStream();

		try {

			InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

			try {
				FeatureDatabase.Builder database = FeatureDatabase.newBuilder();

				System.out.println(database.getFeatureCount());

				
				JsonFormat.parser().merge(reader, database);

				System.out.println(database.getFeatureCount());
				
				return database.getFeatureList();
			} finally {
				reader.close();
			}

		} finally {
			inputStream.close();
		}
	}

}
