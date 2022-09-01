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
import project.studymatching.dto.comment.CommentCreateRequest;
import project.studymatching.dto.comment.CommentReadCondition;
import project.studymatching.service.comment.CommentService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.studymatching.factory.dto.CommentCreateRequestFactory.createCommentCreateRequestWithMemberId;
import static project.studymatching.factory.dto.CommentReadConditionFactory.createCommentReadCondition;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @InjectMocks
    CommentController commentController;
    @Mock
    CommentService commentService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void readAllTest() throws Exception {
        // given
        CommentReadCondition cond = createCommentReadCondition();

        // when, then
        mockMvc.perform(
                        get("/api/comments")
                                .param("postId", String.valueOf(cond.getPostId())))
                .andExpect(status().isOk());

        verify(commentService).readAll(cond);
    }

    @Test
    void createTest() throws Exception {
        // given
        CommentCreateRequest req = createCommentCreateRequestWithMemberId(null);

        // when, then
        mockMvc.perform(
                        post("/api/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(commentService).create(req);
    }

    @Test
    void deleteTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        delete("/api/comments/{id}", id))
                .andExpect(status().isOk());
        verify(commentService).delete(id);
    }
}