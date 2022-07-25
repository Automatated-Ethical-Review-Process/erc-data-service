package com.g7.ercdataservice.service.impl;

import com.g7.ercdataservice.entity.Documents;
import com.g7.ercdataservice.enums.DocumentType;
import com.g7.ercdataservice.repository.DocumentRepository;
import com.g7.ercdataservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private FileStore fileStore;
    @Autowired
    private DocumentRepository documentRepository;
    @Override
    public String add(String file, DocumentType type) {
        Documents documents = new Documents();
        documents.setType(type);
        documents.setFile(fileStore.upload(file));
        return documentRepository.save(documents).getFile();

    }

    @Override
    public void remove(long id) {
       Documents documents = documentRepository.findById(id)
               .orElseThrow(
                       ()-> new EntityNotFoundException("Document not found")
               );
       documentRepository.delete(documents);
       fileStore.delete(documents.getFile());
    }
}
