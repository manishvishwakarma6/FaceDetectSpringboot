
package com.abhiyantrikitech.controller;

import com.abhiyantrikitech.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/face")
public class FaceController {

    @Autowired
    private FaceService faceService;

    // -------------------------
    // Upload Image API
    // -------------------------
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFaceImage(@RequestParam("file") MultipartFile file) {
        try {
            faceService.saveUploadFile(file);
            
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Image uploaded successfully"));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new ApiResponse(false, "Image upload failed"));
        }
    }

    // -------------------------
    // Response Wrapper
    // -------------------------
    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}

