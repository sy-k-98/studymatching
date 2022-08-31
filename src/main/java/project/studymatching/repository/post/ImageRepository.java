package project.studymatching.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import project.studymatching.entity.post.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
