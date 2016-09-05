package com.blazebit.weblink.core.model.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.*;

@Converter
public class StringMapListConverter implements AttributeConverter<List/*<Map<String, String>>*/, String> {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, mapper.getTypeFactory().constructMapType(TreeMap.class, String.class, String.class));

	@Override
	@SuppressWarnings("unchecked")
	public String convertToDatabaseColumn(List/*<Map<String, String>>*/ attribute) {
		if (attribute == null) {
			return null;
		}
		
		if (attribute.isEmpty()) {
			return "";
		}

		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			// Should never happen
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public List/*<Map<String, String>>*/ convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		
		if (dbData.isEmpty()) {
			return new ArrayList();
		}
		
		try {
			return mapper.readValue(dbData, collectionType);
		} catch (IOException e) {
			// Should never happen
			throw new IllegalArgumentException(e);
		}
	}

}
