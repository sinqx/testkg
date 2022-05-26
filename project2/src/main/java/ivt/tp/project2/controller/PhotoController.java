package ivt.tp.project2.controller;

import ivt.tp.project2.entity.Photo;
import ivt.tp.project2.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @PatchMapping("/addPhoto")
    public String save(@RequestParam(name = "file") MultipartFile multipartFile, Model model) {
        Photo photo = photoService.save(multipartFile);
        model.addAttribute("Photo", photo);
        return "userSettings";
    }

    @GetMapping("/photo/{photoId}")
    public Photo FindById(@PathVariable Long photoId) {
        return photoService.findById(photoId);
    }

    @DeleteMapping("/deletePhoto/{photoId}")
    public ResponseEntity deleteById(@PathVariable Long photoId) {
        try {
            String answer = photoService.deleteById(photoId);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/allPhotos")
    public ResponseEntity getAllPhotos() {
        try {
            List<Photo> photos = photoService.getAllPhotos();
            return new ResponseEntity<>(photos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
