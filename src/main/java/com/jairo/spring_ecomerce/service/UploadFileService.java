package com.jairo.spring_ecomerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService {

    private String folder = "images//";

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // ✅ Crear carpeta si no existe
            Files.createDirectories(Paths.get(folder));

            String uniqueName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + uniqueName);
            Files.write(path, bytes);
            return uniqueName;
        }
        return "default.jpg";
    }

    public void deleteImage(String nombre) {
        try {
            String ruta = folder;
            Path path = Paths.get(ruta + nombre);
            Files.deleteIfExists(path); // Más seguro que file.delete()
        } catch (IOException e) {
            System.out.println("Error al eliminar imagen: " + nombre + " → " + e.getMessage());
        }
    }
}
