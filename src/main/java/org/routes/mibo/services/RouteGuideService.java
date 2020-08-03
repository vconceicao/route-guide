package org.routes.mibo.services;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideImplBase;
import io.grpc.examples.routeguide.RouteNote;
import io.grpc.stub.StreamObserver;

public class RouteGuideService extends RouteGuideImplBase{

	private static final Logger logger = Logger.getLogger(RouteGuideService.class.getName());
	private final Collection<Feature> features;
	private final ConcurrentMap<Point, List<RouteNote>> routeNotes = new ConcurrentHashMap<Point, List<RouteNote>>();

	
	public RouteGuideService(Collection<Feature> features) {
		this.features = features;
	}
	
	
	@Override
	public void getFeature(Point request, StreamObserver<Feature> responseObserver) {

		responseObserver.onNext(checkFeature(request));
		responseObserver.onCompleted();
	
	}
	
	@Override
	public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
		int left = Math.min(request.getLo().getLongitude(), request.getHi().getLongitude());
		int right = Math.max(request.getLo().getLongitude(), request.getHi().getLongitude());
		int bottom = Math.min(request.getLo().getLatitude(), request.getLo().getLatitude());
		int top = Math.max(request.getLo().getLatitude(), request.getHi().getLatitude());

		for (Feature feature : features) {
			
			
			int lat = feature.getLocation().getLatitude();
			int lon = feature.getLocation().getLongitude();
			
			if (lon>=left && lon<=right && lat>=bottom && lat<=top) {
				logger.info("Feature found in the area:" + "lat= " +lat+ "lon="+lon );
				responseObserver.onNext(feature);
			}
			
			
		}
		responseObserver.onCompleted();

	
	}


	private Feature checkFeature(Point location) {
		for (Feature feature : features) {
			if (feature.getLocation().getLatitude()==location.getLatitude() && feature.getLocation().getLongitude() == location.getLongitude()) {
				return feature;
			}
		}
		
		
		return Feature.newBuilder().setName("").setLocation(location).build();
	}
	
	
}
