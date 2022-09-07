package project.studymatching.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.studymatching.dto.comment.CommentCreateRequest;
import project.studymatching.dto.comment.CommentDto;
import project.studymatching.dto.comment.CommentReadCondition;
import project.studymatching.entity.comment.Comment;
import project.studymatching.entity.member.Member;
import project.studymatching.entity.post.Post;
import project.studymatching.exception.CommentNotFoundException;
import project.studymatching.exception.MemberNotFoundException;
import project.studymatching.exception.PostNotFoundException;
import project.studymatching.repository.comment.CommentRepository;
import project.studymatching.repository.member.MemberRepository;
import project.studymatching.repository.post.PostRepository;

import java.util.List;
import java.util.Optional;

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
        Member member = memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(req.getPostId()).orElseThrow(PostNotFoundException::new);
        Comment parent = Optional.ofNullable(req.getParentId())
                .map(id -> commentRepository.findById(id).orElseThrow(CommentNotFoundException::new))
                .orElse(null);

        Comment comment = commentRepository.save(new Comment(req.getContent(), member, post, parent));
    }

    @Transactional
    @PreAuthorize("@commentGuard.check(#id)")
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }
}