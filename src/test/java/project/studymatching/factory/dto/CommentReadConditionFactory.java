package project.studymatching.factory.dto;

import project.studymatching.dto.comment.CommentReadCondition;

public class CommentReadConditionFactory {
    public static CommentReadCondition createCommentReadCondition() {
        return new CommentReadCondition(1L);
    }

    public static CommentReadCondition createCommentReadCondition(Long postId) {
        return new CommentReadCondition(postId);
    }
}