package com.ica.technicalValidation;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@Component
@Order(value = 1)
public class Sample extends BaseJsonSchemaValidatorTest implements ApplicationRunner {

//    public static void main(String[] args) throws IOException {
//        test();
//    }
@Override
public void run(ApplicationArguments args) throws Exception {
    System.out.println("执行方法");
    test();
}



    public void test() throws IOException {
//        JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
//        JsonNode node = getJsonNodeFromStringContent("7");
//        Set<ValidationMessage> errors = schema.validate(node);
//        assertThat(errors.size(), is(1));

        // With automatic version detection
        JsonNode schemaNode = getJsonNodeFromStringContent(
                "{\"$schema\": \"http://json-schema.org/draft-06/schema#\", \"properties\": { \"id\": {\"type\": \"number\"}}}");
        JsonSchema schema = getJsonSchemaFromJsonNodeAutomaticVersion(schemaNode);

        schema.initializeValidators(); // by default all schemas are loaded lazily. You can load them eagerly via
        // initializeValidators()

        JsonNode node = getJsonNodeFromStringContent("{\"id\": \"2\"}");
        Set<ValidationMessage> errors = schema.validate(node);
        assertThat(errors.size(), is(1));
        if(!CollectionUtils.isEmpty(errors)){
            for(ValidationMessage validationMessage : errors){
                throw new IllegalArgumentException("参数不合法:"+validationMessage.getMessage());
            }
        }
    }

}
