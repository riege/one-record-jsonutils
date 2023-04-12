package com.riege.onerecord.jsonutils;

import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.util.NameTransformer;

/*
 * Inspired by https://www.baeldung.com/jackson-json-view-annotation
 */
final class ONERecordBeanSerializerModifier extends BeanSerializerModifier {

    private static final String TYPES_ONTOLOGY = "types";
    private static final String TYPES_JSON = "@type";

    private static NameTransformer PROPERTY_TYPES_TRANSFORMER = new NameTransformer() {

        @Override
        public String transform(String name) {
            return name.equals(TYPES_ONTOLOGY) ? TYPES_JSON : name;
        }

        @Override
        public String reverse(String transformed) {
            return transformed.equals(TYPES_JSON) ? TYPES_ONTOLOGY : transformed;
        }
    };

    @Override
    public List<BeanPropertyWriter> changeProperties(
        SerializationConfig config, BeanDescription beanDesc,
        List<BeanPropertyWriter> beanProperties)
    {
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            if (writer.getName().equals(TYPES_ONTOLOGY)) {
                beanProperties.set(i, writer.rename(PROPERTY_TYPES_TRANSFORMER));
            }
        }
        return beanProperties;
    }

}
