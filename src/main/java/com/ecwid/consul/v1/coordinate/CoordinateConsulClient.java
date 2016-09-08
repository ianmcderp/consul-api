package com.ecwid.consul.v1.coordinate;

import com.ecwid.consul.json.GsonFactory;
import com.ecwid.consul.transport.RawResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.coordinate.model.Node;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CoordinateConsulClient implements CoordinateClient {

	private final ConsulRawClient rawClient;

	public CoordinateConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	@Override
	public Response<List<Datacenter>> getDatacenters() {
		RawResponse rawResponse = rawClient.makeGetRequest("/v1/coordinate/datacenters");

		if (rawResponse.getStatusCode() == 200) {
			List<Datacenter> value = GsonFactory.getGson().fromJson(rawResponse.getContent(), new TypeToken<List<Datacenter>>() {
			}.getType());
			return new Response<List<Datacenter>>(value, rawResponse);
		} else {
			throw new OperationException(rawResponse);
		}
	}

	@Override
	public Response<List<Node>> getNodes(QueryParams queryParams) {
		RawResponse rawResponse = rawClient.makeGetRequest("/v1/coordinate/nodes", queryParams);

		if (rawResponse.getStatusCode() == 200) {
			List<Node> value = GsonFactory.getGson().fromJson(rawResponse.getContent(), new TypeToken<List<Node>>() {
			}.getType());
			return new Response<List<Node>>(value, rawResponse);
		} else {
			throw new OperationException(rawResponse);
		}
	}
}
