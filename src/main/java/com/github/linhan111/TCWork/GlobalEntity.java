package com.github.linhan111.TCWork;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * entity
 * @author snail
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GlobalEntity {

    private String field1;

    private String field2;

    private Integer field3;
}
