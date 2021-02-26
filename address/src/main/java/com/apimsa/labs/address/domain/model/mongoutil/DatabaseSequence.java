package com.apimsa.labs.address.domain.model.mongoutil;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {
	
	// Below is the _id in database_sequences collection in mongo and its value must 
	// be set to Sequence name e.g. address_sequence as mentioned in Address entity POJO
	// Used in SequenceGeneratorService: mongoOperations.findAndModify(query(where("_id").is(seqName))
	@Id
    private String id;

    private long seq;

    
	/*
	 *  e.g. initialize this collection with : 
	 * { "_id":"address_sequence", 
	 *   "seq": 100 }
	 */
    
    public DatabaseSequence() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
	
}
