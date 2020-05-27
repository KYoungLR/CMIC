package com.churchmutual.rest.model;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;

public abstract class CMICObject {

	public String toString() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return jsonSerializer.serialize(this);
	}

	public JSONObject toJSONObject() {
		try {
			return JSONFactoryUtil.createJSONObject(this.toString());
		}
		catch (JSONException jsone){
			return JSONFactoryUtil.createJSONObject();
		}
	}

}
