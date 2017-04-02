package com.barborak;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

  public void filter(ContainerRequestContext requestContext,
          ContainerResponseContext responseContext) throws IOException {

    MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    headers.putSingle("Access-Control-Allow-Origin", "*");
    headers.putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    headers.putSingle("Access-Control-Allow-Headers", "Content-Type");
  }

}