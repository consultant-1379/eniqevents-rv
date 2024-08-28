package com.ericsson.eni.predicates;

import com.ericsson.cifwk.taf.datasource.DataRecord;

public interface User extends DataRecord {
    String getUsername();
    String getPassword();
    Integer getNumber();
}
