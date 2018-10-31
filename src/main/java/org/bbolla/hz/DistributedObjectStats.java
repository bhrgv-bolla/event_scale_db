package org.bbolla.hz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bbolla on 10/31/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributedObjectStats {
    private String name;
    private Object stats;
}
