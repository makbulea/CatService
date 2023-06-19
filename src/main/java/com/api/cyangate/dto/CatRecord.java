package com.api.cyangate.dto;

import com.api.cyangate.enums.RecordType;
import lombok.Data;

import java.util.Date;

@Data
public class CatRecord {
    public String name;
    public RecordType recordType;
    public String tagValue;
    public String textValue;
    public int customValueWidth;
    public int customValueHeight;
    public String value;
    public String path;
    public Date creationDate;
    public String id;
}
