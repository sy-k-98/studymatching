package project.studymatching.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.studymatching.dto.comment.CommentDto;
import project.studymatching.exception.CommentNotFoundException;
import project.studymatching.exception.MemberNotFoundException;
import project.studymatching.exception.PostNotFoundException;
import project.studymatching.repository.comment.CommentRepository;
import project.studymatching.repository.member.MemberRepository;
import project.studymatching.repository.post.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static project.studymatching.factory.dto.CommentCreateRequestFactory.createCommentCreateRequest;
import static project.studymatching.factory.dto.CommentCreateRequestFactory.createCommentCreateRequestWithParentId;
import static project.studymatching.factory.dto.CommentReadConditionFactory.createCommentReadCondition;
import static project.studymatching.factory.entity.CommentFactory.createComment;
import static project.studymatching.factory.entity.CommentFactory.createDeletedComment;
import static project.studymatching.factory.entity.MemberFactory.createMember;
import static project.studymatching.factory.entity.PostFactory.createPost;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PostRepository postRepository;

    @Test
    void readAllTest() {
        // given
        given(commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(anyLong()))
                .willReturn(
                        List.of(createComment(null),
                                createComment(null)
                        )
                );

        // when
        List<CommentDto> result = commentService.readAll(createCommentReadCondition());

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void readAllDeletedCommentTest() {
        // given
        given(commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(anyLong()))
                .willReturn(
                        List.of(createDeletedComment(null),
                                createDeletedComment(null)
                        )
                );

        // when
        List<CommentDto> result = commentService.readAll(createCommentReadCondition());

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getContent()).isNull();
        assertThat(result.get(0).getMember()).isNull();
    }

    @Test
    void createTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(createMember()));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(createPost()));

        // when
        commentService.create(createCommentCreateRequest());

        // then
        verify(commentRepository).save(any());
    }

    @Test
    void createExceptionByMemberNotFoundTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequest()))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void createExceptionByPostNotFoundTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(createMember()));
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequest()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void createExceptionByCommentNotFoundTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(createMember()));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(createPost()));
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequestWithParentId(1L)))
                .isInstanceOf(CommentNotFoundException.class);
    }

}