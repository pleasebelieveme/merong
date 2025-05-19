package org.example.merong.common.filter.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterConstants {

    public static final List<String> WHITE_LIST = List.of(
            "/resources",
            "/api/auth/**"
    );

    public static final String USER_CRUD = "/api/users";

    public static final String REISSUE = "/api/auth/reissue";
}

