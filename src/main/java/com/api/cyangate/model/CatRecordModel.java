package com.api.cyangate.model;

import com.api.cyangate.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Cats")
public class CatRecordModel {
    @Id
    private String id;
    private String name;
    private RecordType recordType;
    @NotNull(message = "Value must not be null")
    private String value;
    private String path;
    private Date creationDate = new Date();
}
