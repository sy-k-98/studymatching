package project.studymatching.controller.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.studymatching.dto.category.CategoryCreateRequest;
import project.studymatching.dto.response.Response;
import project.studymatching.service.category.CategoryService;

import javax.validation.Valid;

@Api(value = "Category Controller", tags = "Category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/hello")
    public String hello() {
        return "안녕하세요 리액트와 스프링부트를 Proxy 설정을통해연결하고 있습니다";
    }

    @ApiOperation(value = "모든 카테고리 조회", notes = "모든 카테고리를 조회한다.")
    @GetMapping("/api/categories")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll() {

        return Response.success(categoryService.readAll());
    }

    @ApiOperation(value = "카테고리 생성", notes = "카테고리를 생성한다.")
    @PostMapping("/api/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody CategoryCreateRequest req) {
        categoryService.create(req);
        return Response.success();
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리를 삭제한다.")
    @DeleteMapping("/api/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@ApiParam(value = "카테고리 id", required = true) @PathVariable Long id) {
        categoryService.delete(id);
        return Response.success();
    }
}