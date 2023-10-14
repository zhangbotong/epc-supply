package com.cbim.epc.supply.common.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;

@JacksonStdImpl
public class BigDecimalToStringSerializer extends ToStringSerializerBase {
    public final static BigDecimalToStringSerializer instance = new BigDecimalToStringSerializer();

    public BigDecimalToStringSerializer() {
        super(Object.class);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        return super.isEmpty(prov, value);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        super.serialize(value, gen, provider);
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        super.serializeWithType(value, g, provider, typeSer);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return super.getSchema(provider, typeHint);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        super.acceptJsonFormatVisitor(visitor, typeHint);
    }

    @Override
    public String valueToString(Object value) {
        BigDecimal bigDecimal = (BigDecimal) value;
        BigDecimal scale = bigDecimal.setScale(2, RoundingMode.DOWN);
        return scale.toString();
    }
}
