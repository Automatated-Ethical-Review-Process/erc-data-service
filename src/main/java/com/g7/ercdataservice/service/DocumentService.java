package com.g7.ercdataservice.service;

import com.g7.ercdataservice.enums.DocumentType;

public interface DocumentService {

    String add(String file, DocumentType type);
    void remove(long file);

}
