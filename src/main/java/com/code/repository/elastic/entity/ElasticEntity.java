package com.code.repository.elastic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticEntity {
    private String id;
    private String source;
    private String indices;
    private String opType;
}
