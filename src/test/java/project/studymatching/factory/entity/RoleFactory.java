package project.studymatching.factory.entity;

import project.studymatching.entity.member.Role;
import project.studymatching.entity.member.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}