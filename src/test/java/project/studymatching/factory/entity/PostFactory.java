package project.studymatching.factory.entity;

import project.studymatching.entity.category.Category;
import project.studymatching.entity.member.Member;
import project.studymatching.entity.post.Image;
import project.studymatching.entity.post.Post;

import java.util.List;

import static project.studymatching.factory.entity.CategoryFactory.createCategory;
import static project.studymatching.factory.entity.MemberFactory.createMember;

public class PostFactory {
    public static Post createPost() {
        return createPost(createMember(), createCategory());
    }

    public static Post createPost(Member member, Category category) {
        return new Post("title", "content", member, category, List.of());
    }

    public static Post createPostWithImages(Member member, Category category, List<Image> images) {
        return new Post("title", "content", member, category, images);
    }

    public static Post createPostWithImages(List<Image> images) {
        return new Post("title", "content", createMember(), createCategory(), images);
    }
}