package com.example.fileuploadserver.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileuploadserver.models.FileData;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@CrossOrigin("*")
@RequestMapping(path="/upload")
public class FileUploadController {
    
    @Autowired JdbcTemplate template;

    private static final String BASE64_PREFIX_DECODER = "data:image/png;base64,";


    private static final String INSERT_FILES_SQL = "INSERT INTO FILES (filename, media_type, content) VALUES (?, ?, ?)";

    @PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postUpload(@RequestPart MultipartFile file
     ,@RequestPart String name
    // @RequestPart String email
    )
     throws IOException{

        String filename = name;
        // String originalName = file.getOriginalFilename();

        String mediaType = file.getContentType();
        InputStream is = file.getInputStream();

        template.update(INSERT_FILES_SQL, filename, mediaType, is);

        return ResponseEntity.ok("OK done");
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getImage(@PathVariable Integer id) {
        // Optional<Post> opt = fileUploadSvc.getPostById(id);
        // Post p = opt.get();
        // String encoded

        Optional<FileData> opt = template.query("select * from files where id = ?", 
            (ResultSet rs) -> {
            if (!rs.next())
                return Optional.empty();
            FileData file = new FileData();
            file.setName(rs.getString("filename"));
            file.setContentType(rs.getString("media_type"));
            file.setContent(rs.getBytes("content"));

            return Optional.of(file);
    }, id);

    if (opt == null) {
        return new ResponseEntity<>(null);
    } else {}
        String encodedString = Base64.getEncoder().encodeToString(opt.get().getContent());
        JsonObject payload = Json.createObjectBuilder()
            .add("image", BASE64_PREFIX_DECODER + encodedString)
            .build();
        return ResponseEntity.ok(payload.toString());
    }
}
