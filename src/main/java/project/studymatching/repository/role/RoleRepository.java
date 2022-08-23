package project.studymatching.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import project.studymatching.entity.member.Role;
import project.studymatching.entity.member.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
