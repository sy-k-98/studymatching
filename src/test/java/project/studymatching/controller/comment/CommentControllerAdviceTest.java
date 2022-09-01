package project.studymatching.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.studymatching.advice.ExceptionAdvice;
import project.studymatching.dto.comment.CommentCreateRequest;
import project.studymatching.exception.CommentNotFoundException;
import project.studymatching.exception.MemberNotFoundException;
import project.studymatching.exception.PostNotFoundException;
import project.studymatching.service.comment.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.studymatching.factory.dto.CommentCreateRequestFactory.createCommentCreateRequestWithMemberId;

@ExtendWith(MockitoExtension.class)
class CommentControllerAdviceTest {
    @InjectMocks
    CommentController commentController;
    @Mock
    CommentService commentService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).setControllerAdvice(new ExceptionAdvice()).build();
    }

    @Test
    void createExceptionByMemberNotFoundTest() throws Exception {
        // given
        doThrow(MemberNotFoundException.class).when(commentService).create(any());
        CommentCreateRequest req = createCommentCreateRequestWithMemberId(null);

        // when, then
        mockMvc.perform(
                        post("/api/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(-1007));
    }

    @Test
    void createExceptionByPostNotFoundTest() throws Exception {
        // given
        doThrow(PostNotFoundException.class).when(commentService).create(any());
        CommentCreateRequest req = createCommentCreateRequestWithMemberId(null);

        // when, then
        mockMvc.perform(
                        post("/api/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(-1012));
    }

    @Test
    void createExceptionByCommentNotFoundTest() throws Exception {
        // given
        doThrow(CommentNotFoundException.class).when(commentService).create(any());
        CommentCreateRequest req = createCommentCreateRequestWithMemberId(null);

        // when, then
        mockMvc.perform(
                        post("/api/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(-1015));
    }

    @Test
    void deleteExceptionByCommentNotFoundTest() throws Exception {
        // given
        doThrow(CommentNotFoundException.class).when(commentService).delete(anyLong());
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        delete("/api/comments/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(-1015));
    }
}