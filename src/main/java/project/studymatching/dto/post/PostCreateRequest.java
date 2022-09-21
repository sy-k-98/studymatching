package project.studymatching.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.studymatching.entity.post.Image;
import project.studymatching.entity.post.Post;
import project.studymatching.exception.CategoryNotFoundException;
import project.studymatching.exception.MemberNotFoundException;
import project.studymatching.repository.category.CategoryRepository;
import project.studymatching.repository.member.MemberRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApiModel(value = "게시글 생성 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "{postCreateRequest.title.notBlank}")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "{postCreateRequest.content.notBlank}")
    private String content;

    @ApiModelProperty(value = "게시글 조건", notes = "게시글 조건을 입력해주세요", required = true, example = "my requirement")
    @NotBlank(message = "{postCreateRequest.requirement.notBlank}")
    private String requirement;

    @ApiModelProperty(hidden = true)
    @Null
    private Long memberId;

    @ApiModelProperty(value = "카테고리 아이디", notes = "카테고리 아이디를 입력해주세요", required = true, example = "3")
    @NotNull(message = "{postCreateRequest.categoryId.notNull}")
    @PositiveOrZero(message = "{postCreateRequest.categoryId.positiveOrZero}")
    private Long categoryId;

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> images = new ArrayList<>();
}