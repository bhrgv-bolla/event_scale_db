package org.bbolla.hz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Row {
    private long rowId;
    private String completePayload;
    private String dataAffinityPartitionKey; //In Future this will be useful
}
