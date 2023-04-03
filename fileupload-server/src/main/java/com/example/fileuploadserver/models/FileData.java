package com.example.fileuploadserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
    
    private String name;
    private String contentType;
    private byte[] content;

}
