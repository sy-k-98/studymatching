package project.studymatching.service.sign;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.studymatching.config.token.TokenHelper;
import project.studymatching.dto.sign.RefreshTokenResponse;
import project.studymatching.dto.sign.SignInRequest;
import project.studymatching.dto.sign.SignInResponse;
import project.studymatching.dto.sign.SignUpRequest;
import project.studymatching.entity.member.Member;
import project.studymatching.entity.member.Role;
import project.studymatching.entity.member.RoleType;
import project.studymatching.exception.*;
import project.studymatching.repository.member.MemberRepository;
import project.studymatching.repository.role.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;

    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        List<Role> roles = List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new));
        memberRepository.save(
                new Member(req.getEmail(), encodedPassword, req.getUsername(), req.getNickname(), roles)
        );
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req) {
        Member member = memberRepository.findWithRolesByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        TokenHelper.PrivateClaims privateClaims = createPrivateClaims(member);
        String accessToken = accessTokenHelper.createToken(privateClaims);
        String refreshToken = refreshTokenHelper.createToken(privateClaims);
        return new SignInResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(String rToken) {
        TokenHelper.PrivateClaims privateClaims = refreshTokenHelper.parse(rToken).orElseThrow(RefreshTokenFailureException::new);
        String accessToken = accessTokenHelper.createToken(privateClaims);
        return new RefreshTokenResponse(accessToken);
    }

    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private TokenHelper.PrivateClaims createPrivateClaims(Member member) {
        return new TokenHelper.PrivateClaims(
                String.valueOf(member.getId()),
                member.getRoles().stream()
                        .map(memberRole -> memberRole.getRole())
                        .map(role -> role.getRoleType())
                        .map(roleType -> roleType.toString())
                        .collect(Collectors.toList()));
    }


}
