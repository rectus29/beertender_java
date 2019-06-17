package com.rectus29.beertender.serializers;

import com.google.gson.*;
import com.rectus29.beertender.entities.GenericEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

/*-----------------------------------------------------*/
/*						rectus29					   */
/*                                                     */
/*                Date: 23/09/2016 12:08               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GenericEntitySerializer implements JsonSerializer<GenericEntity>, JsonDeserializer<GenericEntity> {

	private static final Logger log = LogManager.getLogger(GenericEntitySerializer.class);
	private static final String CLASSNAME = "CLAZZ";
	private static final String INSTANCE  = "INSTANCE";

	@Override
	public JsonElement serialize(GenericEntity genericEntity, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject retValue = new JsonObject();
		String className = genericEntity.getClass().getName();
		retValue.addProperty(CLASSNAME, className);
		JsonElement elem = jsonSerializationContext.serialize(genericEntity);
		retValue.add(INSTANCE, elem);
		return retValue;
	}

	@Override
	public GenericEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		JsonObject jsonObject =  jsonElement.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		String className = prim.getAsString();

		Class<?> klass = null;
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error("Error while deserialize Object", e);
			throw new JsonParseException(e.getMessage());
		}
		return jsonDeserializationContext.deserialize(jsonObject.get(INSTANCE), klass);
	}
}
