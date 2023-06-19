package com.api.cyangate.service;

import com.api.cyangate.dto.CatRecord;
import com.api.cyangate.enums.RecordType;
import com.api.cyangate.model.CatRecordModel;
import com.api.cyangate.repository.CatRecordRepostiory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatRecordService {
    @Autowired
    private final CatRecordRepostiory catRecordRepostiory;
    @Autowired
    private ModelMapper modelMapper;

    public CatRecord createCatRecord(CatRecord catRecord) {
        CatRecordModel catRecordModel = modelMapper.map(catRecord, CatRecordModel.class);
        catRecordModel = catRecordRepostiory.save(catRecordModel);
        return modelMapper.map(catRecordModel, CatRecord.class);
    }

    public CatRecord FormatValue(CatRecord record) {
        if (record.getRecordType().getDisplayValue() == RecordType.Tag.name())
            record.setValue(record.getTagValue());
        else if (record.getRecordType().getDisplayValue() == RecordType.Text.name()) {
            record.setValue(record.getTextValue());
        } else {
            record.setValue(String.valueOf(record.getCustomValueWidth()) + String.valueOf(record.getCustomValueHeight()));
        }
        return record;
    }

    public List<CatRecord> getAllCatRecord() {
        return catRecordRepostiory.findAll().stream().map(e -> (modelMapper.map(e, CatRecord.class))).collect(Collectors.toList());
    }

    public CatRecord findById(String id) {
        CatRecordModel model = catRecordRepostiory.findById(id).orElseThrow(()
                -> new RuntimeException("Cat Record couldn't be found! Id:" + id));

        return modelMapper.map(model, CatRecord.class);
    }

    public void deleteCatRecordById (String id) {
        catRecordRepostiory.deleteById(id);
    }
}
