package project.studymatching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.studymatching.entity.category.Category;
import project.studymatching.entity.comment.Comment;
import project.studymatching.entity.member.Member;
import project.studymatching.entity.member.Role;
import project.studymatching.entity.member.RoleType;
import project.studymatching.entity.post.Post;
import project.studymatching.exception.RoleNotFoundException;
import project.studymatching.repository.category.CategoryRepository;
import project.studymatching.repository.comment.CommentRepository;
import project.studymatching.repository.member.MemberRepository;
import project.studymatching.repository.post.PostRepository;
import project.studymatching.repository.role.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class InitDB {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {

        log.info("initialize database");

        initRole();
        initTestAdmin();
        initTestMember();
        initCategory();
        initPost();
        initComment();

        log.info("initialize database");
    }

    private void initRole() {
        roleRepository.saveAll(
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }

    private void initTestAdmin() {
        memberRepository.save(
                new Member("admin@admin.com", passwordEncoder.encode("123456a!"), "admin", "admin",
                        List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
    }

    private void initTestMember() {
        memberRepository.saveAll(
                List.of(
                        new Member("member1@member.com", passwordEncoder.encode("123456a!"), "member1", "member1",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))),
                        new Member("member2@member.com", passwordEncoder.encode("123456a!"), "member2", "member2",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))))
        );
    }

    private void initCategory() {
        Category c1 = categoryRepository.save(new Category("어학", null));
        Category c2 = categoryRepository.save(new Category("토익", c1));
        Category c3 = categoryRepository.save(new Category("토익스피킹", c1));
        Category c4 = categoryRepository.save(new Category("컴퓨터", null));
        Category c5 = categoryRepository.save(new Category("백엔드", c4));
        Category c6 = categoryRepository.save(new Category("프론트엔드", c4));
        Category c7 = categoryRepository.save(new Category("기타", null));
        Category c8 = categoryRepository.save(new Category("여행", c7));

    }

    private void initPost() {
        Member member = memberRepository.findAll().get(0);
        Category category = categoryRepository.findAll().get(0);
        IntStream.range(0, 100)
                .forEach(i -> postRepository.save(
                        new Post("title" + i, "content" + i, "requirement" + i, member, category, List.of())
                ));
    }

    private void initComment() {
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.findAll().get(0);
        Comment c1 = commentRepository.save(new Comment("content", member, post, null));
        Comment c2 = commentRepository.save(new Comment("content", member, post, c1));
        Comment c3 = commentRepository.save(new Comment("content", member, post, c1));
        Comment c4 = commentRepository.save(new Comment("content", member, post, c2));
        Comment c5 = commentRepository.save(new Comment("content", member, post, c2));
        Comment c6 = commentRepository.save(new Comment("content", member, post, c4));
        Comment c7 = commentRepository.save(new Comment("content", member, post, c3));
        Comment c8 = commentRepository.save(new Comment("content", member, post, null));
    }
}