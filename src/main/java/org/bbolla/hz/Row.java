package org.bbolla.hz;

import lombok.*;

@Value
public class Row { //make it immutable once created.
    private long rowId;
    private String completePayload;
    private String dataAffinityPartitionKey; //In Future this will be useful
}
