package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.service.FileStoreService;
import org.springframework.stereotype.Service;

@Service
public class FileStore implements FileStoreService {
    @Override
    public String upload(String file) {
        return file;
    }

    @Override
    public String download(String file) {
        return file;
    }

    @Override
    public void delete(String file) {

    }
}
