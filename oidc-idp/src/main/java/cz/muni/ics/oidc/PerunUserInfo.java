package cz.muni.ics.oidc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implements UserInfo by inheriting from DefaultUserInfo and adding more claims.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class PerunUserInfo extends DefaultUserInfo {

	private final static Logger log = LoggerFactory.getLogger(PerunUserInfo.class);

	private Map<String, JsonNode> customClaims = new LinkedHashMap<>();

	Map<String, JsonNode> getCustomClaims() {
		return customClaims;
	}

	private JsonObject obj;

	@Override
	public JsonObject toJson() {
		if(obj==null) {
			//delegate standard claims to DefaultUserInfo
			obj = super.toJson();
			//add custom claims
			for (Map.Entry<String, JsonNode> entry : customClaims.entrySet()) {
				String key = entry.getKey();
				JsonNode value = entry.getValue();
				if (value == null || value.isNull()) {
					obj.addProperty(key, (String) null);
					log.debug("adding null claim {}=null", key);
				} else if (value.isTextual()) {
					obj.addProperty(key, value.asText());
					log.debug("adding string claim {}={}", key, value.asText());
				} else if (value.isNumber()) {
					obj.addProperty(key, value.asLong());
					log.debug("adding long claim {}={}", key, value.asText());
				} else if (value.isContainerNode()) {
					try {
						//convert from Jackson to GSon
						String rawJson = new ObjectMapper().writeValueAsString(value);
						obj.add(key, new JsonParser().parse(rawJson));
						log.debug("adding JSON claim {}={}", key, rawJson);
					} catch (JsonProcessingException | JsonSyntaxException e) {
						log.error("cannot convert Jackson/Gson value " + value, e);
					}
				} else {
					log.warn("claim {} is of unknown type {}, skipping", key, value.getNodeType().toString());
				}
			}
		} else {
			log.debug("already rendered to JSON");
		}
		return obj;
	}

}
