package com.example.testysavingsbe.global.redis.serializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class RedisGsonSerializer implements RedisSerializer{

    final Type type;
    final Gson gson;

    public RedisGsonSerializer(Type type) {
        log.info("<< GSON Init >>...type: {}", type);
        this.type = type;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new UtcIsoDateAdapter())
                .create();
    }

    @Override
    public byte[] serialize(Object value) throws SerializationException {
        log.info("<< GSON Serializaion >>...{}", value);
        return gson.toJson(value).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        try {
            String s = new String(bytes, "UTF-8");
            log.info("<< GSON DeSerializaion >>...{} / {}", type, s);
            return gson.fromJson(s, this.type);

        } catch (JsonSyntaxException | UnsupportedEncodingException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    public static Type asList(Class clazz) {
        return TypeToken.getParameterized(List.class, clazz).getType();
    }

    private static class  UtcIsoDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        final String ISO_DATE_FROMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        final SimpleDateFormat dateFormat;

        public UtcIsoDateAdapter() {
            dateFormat = new SimpleDateFormat(ISO_DATE_FROMAT, Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            String ds = dateFormat.format(src);
            log.info("< Serialize Date > {} / {}", src, ds);
            return new JsonPrimitive(ds);
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                String ds = json.getAsString();
                log.info("< Deserialize Date > {}", ds);
                return dateFormat.parse(json.getAsString());

            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }

}
