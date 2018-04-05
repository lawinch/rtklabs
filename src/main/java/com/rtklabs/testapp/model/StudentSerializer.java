package com.rtklabs.testapp.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StudentSerializer  extends JsonSerializer<Student> {

	@Override
	public void serialize(Student value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartObject(value);
		jsonGenerator.writeNumberField("id", value.getId());
		jsonGenerator.writeStringField("name", value.getName());
		jsonGenerator.writeStringField("surname", value.getSurname());
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
		jsonGenerator.writeObjectFieldStart("courses");
		if(value.getCourses() != null)
			for (Course i : value.getCourses()) {
				jsonGenerator.writeObjectFieldStart("course");
				jsonGenerator.writeNumberField("id", i.getId());
				jsonGenerator.writeStringField("name", i.getName());
				jsonGenerator.writeObjectFieldStart("instructors");
				jsonGenerator.writeEndObject();
				jsonGenerator.writeObjectFieldStart("students");
				jsonGenerator.writeEndObject();
				jsonGenerator.writeEndObject();
			}
		jsonGenerator.writeEndObject();
		jsonGenerator.writeEndObject();
	}

}
