package project.studymatching.config.security.guard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.studymatching.entity.comment.Comment;
import project.studymatching.entity.member.RoleType;
import project.studymatching.repository.comment.CommentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentGuard extends Guard {
    private final CommentRepository commentRepository;
    private List<RoleType> roleTypes = List.of(RoleType.ROLE_ADMIN);

    @Override
    protected List<RoleType> getRoleTypes() {
        return roleTypes;
    }

    @Override
    protected boolean isResourceOwner(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> { throw new AccessDeniedException(""); });
        Long memberId = AuthHelper.extractMemberId();
        return comment.getMember().getId().equals(memberId);
    }
}