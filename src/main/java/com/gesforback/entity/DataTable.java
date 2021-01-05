package com.gesforback.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author sud
 */
@Builder
@Data
public class DataTable {
    
    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List data;

}
