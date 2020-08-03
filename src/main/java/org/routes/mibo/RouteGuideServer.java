package org.routes.mibo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.routes.mibo.services.RouteGuideService;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.routeguide.Feature;

public class RouteGuideServer {

	
	private final int port;
	private final Server server;
	
	private static final Logger LOGGER = Logger.getLogger(RouteGuideServer.class.getName());
	
	
	public RouteGuideServer(int port) throws IOException {
		this(port, RouteGuideUtil.getDefaultFeaturesFiles());
	}


	public RouteGuideServer(int port, URL featureFile) throws IOException {
		this(ServerBuilder.forPort(port), port, RouteGuideUtil.parseFeatures(featureFile));
	}


	public RouteGuideServer(ServerBuilder<?> builder, int port, List<Feature> features) {
		this.port = port;
		this.server = builder.addService(new RouteGuideService(features)).build();
	
	}
	
	public static void main(String[] args) throws Exception {
		RouteGuideServer server2 = new RouteGuideServer(8980);
		server2.start();
		server2.blockUntilShutdown();
	}


	private void blockUntilShutdown() throws InterruptedException {

		if(server!=null)
			server.awaitTermination();
	}


	public void start() throws IOException {

		server.start();
		LOGGER.info("Server started, listening on port " + port);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.err.println("Shutdown grpc server");;
				try {
					RouteGuideServer.this.stop();
				} catch (InterruptedException e) {

					e.printStackTrace(System.err);
				}
				System.err.println("Server shut down");
			}
			
			
		});
		
	}


	public void stop() throws InterruptedException {
		// TODO Auto-generated method stub
		if (server!=null) {
			server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
		}
	}
	
	
	
	
}
