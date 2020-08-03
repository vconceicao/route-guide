package org.routes.mibo;

import static io.grpc.examples.routeguide.RouteGuideGrpc.newBlockingStub;

import java.util.logging.Logger;
import java.util.Iterator;
import java.util.logging.Level;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideBlockingStub;

public class RouteGuideClient {

	private static final Logger LOGGER = Logger.getLogger(RouteGuideClient.class.getName());

	private final RouteGuideBlockingStub blockingStub;
	
	
	//constructs a client with the existing channel
	public RouteGuideClient(Channel channel) {
		blockingStub = newBlockingStub(channel);
	}
	
	
	public static void main(String[] args) {
		
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8980").usePlaintext().build();
		
		RouteGuideClient client = new RouteGuideClient(channel);
		
		
//		client.getFeature(1,2);
		
		
		client.listFeature(1,10,30,20);
		
	}



	public void listFeature(int lowLat, int lowLon, int hiLat, int hiLon) {
	
		
		Rectangle request = Rectangle.newBuilder().setLo(Point.newBuilder().setLongitude(lowLon).setLatitude(lowLat).build()).
		setHi(Point.newBuilder().setLongitude(hiLon).setLatitude(hiLat).build()).build();
		
		Iterator<Feature> features;
		
		features = blockingStub.listFeatures(request);
		
		for(int i = 1; features.hasNext(); i++){
			Feature feature = features.next();
			
			LOGGER.info("Result: " + i +" " + feature.getName() );
			
			
		}
		
	}


	public void getFeature(int lat, int lon) {
		LOGGER.info("**** Get feature: lat=" + lat+" lon="+lon);
		
		Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();
		
		Feature feature;
		
		feature = blockingStub.getFeature(request);
		
		System.out.println(feature.getName());
		
		
		
	}
}
