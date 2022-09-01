package project.studymatching.factory.entity;

import project.studymatching.entity.comment.Comment;
import project.studymatching.entity.member.Member;
import project.studymatching.entity.post.Post;

import static project.studymatching.factory.entity.MemberFactory.createMember;
import static project.studymatching.factory.entity.PostFactory.createPost;

public class CommentFactory {

    public static Comment createComment(Comment parent) {
        return new Comment("content", createMember(), createPost(), parent);
    }

    public static Comment createDeletedComment(Comment parent) {
        Comment comment = new Comment("content", createMember(), createPost(), parent);
        comment.delete();
        return comment;
    }

    public static Comment createComment(Member member, Post post, Comment parent) {
        return new Comment("content", member, post, parent);
    }
}