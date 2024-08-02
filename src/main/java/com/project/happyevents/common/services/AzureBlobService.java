package com.project.happyevents.common.services;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AzureBlobService {

    @Value("BlobEndpoint=https://storagetestforblob.blob.core.windows.net/;QueueEndpoint=https://storagetestforblob.queue.core.windows.net/;FileEndpoint=https://storagetestforblob.file.core.windows.net/;TableEndpoint=https://storagetestforblob.table.core.windows.net/;SharedAccessSignature=sv=2022-11-02&ss=bfqt&srt=co&sp=rwdlacupiytfx&se=2025-05-17T13:15:00Z&st=2024-05-17T05:15:00Z&spr=https,http&sig=f7N5qOypWNXUtrjGJCoAJA14M8V995okd%2FTYyqYccpU%3D")
    private String connectionString;

    @Value("blobby")
    private String containerName;

    public String uploadFile(MultipartFile file) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        String blobName = file.getOriginalFilename();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());

        try {
            containerClient.getBlobClient(blobName).upload(file.getInputStream(), file.getSize(), true);
            containerClient.getBlobClient(blobName).setHttpHeaders(headers);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Azure Blob Storage", e);
        }

        return containerClient.getBlobClient(blobName).getBlobUrl();
    }
}
