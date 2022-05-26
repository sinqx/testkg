package ivt.tp.project2.service;

import ivt.tp.project2.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    Photo save(MultipartFile multipartFile);
    Photo findById(Long id);
    List<Photo> getAllPhotos();
    String deleteById(Long id);
}
