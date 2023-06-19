package com.api.cyangate.controller;

import com.api.cyangate.client.Http;
import com.api.cyangate.common.FileProcessing;
import com.api.cyangate.common.Variable;
import com.api.cyangate.dto.CatRecord;
import com.api.cyangate.enums.RecordType;
import com.api.cyangate.service.CatRecordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class CatController {
    @Autowired
    private final CatRecordService catRecordService;

    HashMap<String, List<String>> hashMap = new HashMap<>();

    @GetMapping("/cat")
    public String viewCatRecordPage(Model model) {
        CatRecord catRecord = new CatRecord();
        model.addAttribute("catRecord", catRecord);
        return "save";
    }

    @PostMapping("/cat/save")
    public String saveCatRecord(@Valid @ModelAttribute("catRecord") CatRecord catRecord,
                                BindingResult result,
                                Model model, RedirectAttributes redirectAttributes) throws IOException {
        Path path = null;

        StringBuilder newUrl = new StringBuilder();
        newUrl.append(Variable.catExternalClientUrl);

        if (catRecord.getRecordType().getDisplayValue() == RecordType.Tag.name()) {  //Ex. https://cataas.com/cat/cute
            newUrl.append("/");
            newUrl.append(catRecord.getTagValue());
            path = Paths.get(Variable.PUBLIC_DIR.toString(), catRecord.getPath(), "Tag");
        } else if (catRecord.getRecordType().getDisplayValue() == RecordType.Text.name()) {  //https://cataas.com/cat/says/hello
            newUrl.append("/says/");
            newUrl.append(catRecord.getTextValue());
            path = Paths.get(Variable.PUBLIC_DIR.toString(), catRecord.getPath(), "Text");   //https://cataas.com/cat?width=100&height=100
        } else {
            newUrl.append("?width=");
            newUrl.append(catRecord.getCustomValueWidth());
            newUrl.append("&height=");
            newUrl.append(catRecord.getCustomValueHeight());

            path = Paths.get(Variable.PUBLIC_DIR.toString(), catRecord.getPath(), "Custom");
        }


        try {
            //Get Image
            ResponseEntity<byte[]> response = Http.get(newUrl.toString());

            byte[] imageBytes = response.getBody();
            String imageExtention = response.getHeaders().getContentType().getSubtype();
            String imageName = catRecord.getName() + "." + imageExtention;

            //Write Directory
            FileProcessing.downloadImage(imageBytes, imageName, path);

            //Save to Mongo Database
            catRecord.setPath(path.toString());
            catRecord.setName(imageName);

            if (catRecord.getRecordType().getDisplayValue() == RecordType.Tag.name())
                catRecord.setValue(catRecord.getTagValue());
            else if (catRecord.getRecordType().getDisplayValue() == RecordType.Text.name()) {
                catRecord.setValue(catRecord.getTextValue());
            } else {
                catRecord.setValue("Width:" + String.valueOf(catRecord.getCustomValueWidth()) + "; Height:" + String.valueOf(catRecord.getCustomValueHeight()));
            }

            catRecord = catRecordService.createCatRecord(catRecord);

        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("timestamp", new Date());
            return "/error";
        }

        return "redirect:/cat/list";
    }

    @GetMapping("/cat/list")
    public String getAllCatRecords(Model model) {
        List<CatRecord> records = catRecordService.getAllCatRecord();

        model.addAttribute("catRecords", records);
        return "list";
    }

    @GetMapping("/cat/delete/{id}")
    public String deleteCatRecord(@PathVariable("id") String id, Model model) {
        //Get Record By Id
        CatRecord catRecord = catRecordService.findById(id);
        if (catRecord == null)
            return "list";

        //Delete record
        catRecordService.deleteCatRecordById(id);

        try {
            //Delete path
            FileProcessing.deleteFile(catRecord.getPath());
        } catch (IOException e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("timestamp", new Date());
            return "/error";
        }
        return "redirect:/cat/list";
    }
}


