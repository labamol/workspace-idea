package com.apimsa.labs.address.domain.model.mongoutil;

import com.apimsa.labs.address.domain.model.Address;
import com.apimsa.labs.address.domain.model.mongoutil.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class AddressModelListener extends AbstractMongoEventListener<Address> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AddressModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    
    // Every time we save a new Address, the id will be set automatically.
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Address> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Address.SEQUENCE_NAME));
        }
    }


}

