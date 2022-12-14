package project.studymatching.controller.exception;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.studymatching.exception.AccessDeniedException;
import project.studymatching.exception.AuthenticationEntryPointException;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class ExceptionController {
    @GetMapping("/exception/entry-point")
    public void entryPoint() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/exception/access-denied")
    public void accessDenied() {
        throw new AccessDeniedException();
    }
}
