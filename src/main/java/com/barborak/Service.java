package com.barborak;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.glassfish.grizzly.http.CompressionConfig.CompressionMode;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Service {
	public static final URL BASE_URL;
	static {
		try {
			BASE_URL = new URL("http://0.0.0.0:8080/com.barborak/");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	static public void main(String[] args) {
		ResourceConfig rc = new ResourceConfig().packages("com.barborak");
		rc.register(CORSResponseFilter.class)
		;
		HttpServer server;
		try {
			server = GrizzlyHttpServerFactory.createHttpServer(BASE_URL.toURI(), rc, false);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		StaticHttpHandler handler = new StaticHttpHandler("client/www");
		handler.setFileCacheEnabled(false);
		server.getServerConfiguration().addHttpHandler(handler, "/");

		NetworkListener listener = server.getListener("grizzly");
		listener.getCompressionConfig().setCompressionMode(CompressionMode.ON);
		listener.getCompressionConfig().setCompressableMimeTypes("text/html", "text/plain", "application/json");

		try {
			server.start();
			System.out.println("Hit enter to stop it...");
			System.in.read();
			server.shutdownNow();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
