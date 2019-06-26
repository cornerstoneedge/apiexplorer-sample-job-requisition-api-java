package com.csod.edge.api.samples.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class EdgeApiODataPayload {
	@SerializedName("@odata.context")
	public String context;

	@SerializedName("@odata.nextLink")
	public String nextLink;

	@SerializedName("@odata.count")
	public Integer count;

	@SerializedName("value")
	public JsonObject[] value;

	public EdgeApiErrorValue errorValue;
}
