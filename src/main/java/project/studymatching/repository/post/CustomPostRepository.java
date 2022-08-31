package project.studymatching.repository.post;

import org.springframework.data.domain.Page;
import project.studymatching.dto.post.PostReadCondition;
import project.studymatching.dto.post.PostSimpleDto;

public interface CustomPostRepository {
    Page<PostSimpleDto> findAllByCondition(PostReadCondition cond);
}