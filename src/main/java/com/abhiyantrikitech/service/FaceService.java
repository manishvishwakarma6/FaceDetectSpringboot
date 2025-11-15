    package com.abhiyantrikitech.service;

import com.abhiyantrikitech.model.FaceMaster;
import com.abhiyantrikitech.repository.FaceDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class FaceService {

    @Autowired
    FaceDao faceDao;
    
public void saveUploadFile(MultipartFile uploadedImage) throws Exception {
    String uploadDir = "D:/uploads/";
    File dir = new File(uploadDir);
    if (!dir.exists()) 
        dir.mkdirs();  

    // Get original file 
    String originalFilename = uploadedImage.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    // new  file name
    String timeStamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS").format(LocalDateTime.now());
    String newFileName = "face_" + timeStamp + extension;

    String filePath = Paths.get(uploadDir, newFileName).toString();

    // Save the file to disk
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
        fos.write(uploadedImage.getBytes());
    }

    // Save file info in DB
    FaceMaster faceImage = new FaceMaster();
    faceImage.setFd_filename(filePath);   
    faceImage.setFd_status("Pending");  

    faceDao.saveUploadFile(faceImage, null);
}


}
