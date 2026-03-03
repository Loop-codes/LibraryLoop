package com.example.LibraryLoop.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadLinkDTO {

    private boolean readOnline;
    private String url;
}