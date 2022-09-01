package project.studymatching.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.studymatching.dto.comment.CommentCreateRequest;
import project.studymatching.dto.comment.CommentDto;
import project.studymatching.dto.comment.CommentReadCondition;
import project.studymatching.entity.comment.Comment;
import project.studymatching.exception.CommentNotFoundException;
import project.studymatching.repository.comment.CommentRepository;
import project.studymatching.repository.member.MemberRepository;
import project.studymatching.repository.post.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public List<CommentDto> readAll(CommentReadCondition cond) {
        return CommentDto.toDtoList(
                commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(cond.getPostId())
        );
    }

    @Transactional
    public void create(CommentCreateRequest req) {
        commentRepository.save(CommentCreateRequest.toEntity(req, memberRepository, postRepository, commentRepository));
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }
}