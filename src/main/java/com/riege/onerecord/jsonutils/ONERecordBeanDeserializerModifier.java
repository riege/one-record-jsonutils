package com.riege.onerecord.jsonutils;

import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

final class ONERecordBeanDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public List<BeanPropertyDefinition> updateProperties(DeserializationConfig config,
        BeanDescription beanDesc, List<BeanPropertyDefinition> propDefs)
    {
        for (int i = 0; i < propDefs.size(); i++) {
            BeanPropertyDefinition definition = propDefs.get(i);
            if (definition.getName().equals(ONERecordBeanSerializerModifier.TYPES_ONTOLOGY)) {
                propDefs.set(i, definition
                    .withName(PropertyName.construct(ONERecordBeanSerializerModifier.TYPES_ONTOLOGY))
                    .withSimpleName(ONERecordBeanSerializerModifier.TYPES_JSON));
            }
        }
        return propDefs;
    }

}
