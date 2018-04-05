package com.rtklabs.testapp.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CourseSerializer extends JsonSerializer<Course> {


	@Override
	public void serialize(Course value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject(value);
		jsonGenerator.writeNumberField("id", value.getId());
		jsonGenerator.writeStringField("name", value.getName());
		jsonGenerator.writeObjectFieldStart("instructors");
		//write instructors
		if(value.getInstructors() != null)
			for (Instructor i : value.getInstructors()) {
				jsonGenerator.writeObjectFieldStart("instructor");
				jsonGenerator.writeNumberField("id", i.getId());
				jsonGenerator.writeStringField("name", i.getName());
				jsonGenerator.writeStringField("surname", i.getSurname());
				jsonGenerator.writeObjectFieldStart("students");
				jsonGenerator.writeEndObject();
				jsonGenerator.writeObjectFieldStart("courses");
				jsonGenerator.writeEndObject();
				jsonGenerator.writeEndObject();
			}
		jsonGenerator.writeEndObject();
		//write courses
		jsonGenerator.writeObjectFieldStart("students");
		if(value.getStudents() != null)
		for (Student i : value.getStudents()) {
			jsonGenerator.writeObjectFieldStart("student");
			jsonGenerator.writeNumberField("id", i.getId());
			jsonGenerator.writeStringField("name", i.getName());
			jsonGenerator.writeStringField("surname", i.getSurname());
			jsonGenerator.writeObjectFieldStart("instructors");
			jsonGenerator.writeEndObject();
			jsonGenerator.writeObjectFieldStart("courses");
			jsonGenerator.writeEndObject();
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.writeEndObject();
		jsonGenerator.writeEndObject();
	}

}
