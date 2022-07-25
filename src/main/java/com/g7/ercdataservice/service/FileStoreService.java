package com.g7.ercdataservice.service;

public interface FileStoreService {

    String upload(String file);
    String download(String file);
    void delete(String file);
}
