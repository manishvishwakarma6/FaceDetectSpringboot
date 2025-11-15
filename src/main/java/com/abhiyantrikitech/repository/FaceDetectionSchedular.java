package com.abhiyantrikitech.repository;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.abhiyantrikitech.model.FaceMaster;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FaceDetectionSchedular {

    @Autowired
    FaceDao faceDao;
    
    @Autowired
    ConnectionBean connBean; 
    
    @Scheduled(fixedRate = 60000)
    public void processPendingFaces() {
        
        try (Connection conn = connBean.getConnectionObj()) {
            List<FaceMaster> pendingList = faceDao.getPendingFaces();
            
            if (pendingList.isEmpty()) {
                System.out.println("No pending faces to process at this time.");
                return;
            }
            
            for (FaceMaster face : pendingList) {
                File file = new File(face.getFd_filename());
               // File file = new File("C:/uploads/" + face.getFd_filename());
              
                if (!file.exists()) {
                    System.out.println("File not found: " + file.getAbsolutePath());
                    continue;
                }
                
                byte[] fileBytes = new FileInputStream(file).readAllBytes();
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
               
                try {
                	// Flask API calling 
                	Map<String, Object> result = detectFace(base64Image);
                	
                	// duplicate image check by base64Image 
                	boolean isDuplicate = faceDao.duplicateImageCheck(base64Image, face.getFd_id() + "");
                     if (isDuplicate) {
                         System.out.println("Duplicate image detected for ID: " + face.getFd_id() + ". Skipping.");
                         continue;
                     }
                     
                	String detectedImgBase64 = (String) result.get("image");  
                	String faceCount = String.valueOf(result.get("face_count"));
                	
                	faceDao.updateFaceInfo(face.getFd_id(), base64Image, detectedImgBase64, faceCount, conn);
                	
                    System.out.println("original image String: " + face.getFd_id() + " | original image: " + base64Image);
                    System.out.println("detected image String: " + face.getFd_id() + " | detected images: " + detectedImgBase64);
                    System.out.println("Updated for ID: " + face.getFd_id() + " | Faces detected: " + faceCount);
                    
                } catch (Exception ex) {
                    System.err.println("Error processing face ID: " + face.getFd_id());
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Call Flask API with base64 image
    public Map<String, Object> detectFace(String base64Image) throws Exception {
        String jsonPayload = new ObjectMapper().writeValueAsString(Map.of("image", base64Image));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8085/detect_faces"))
        		//.uri(URI.create("https://facedetectapp-2oy9.onrender.com/detect_faces"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(response.body(), Map.class);
    }
    
}
