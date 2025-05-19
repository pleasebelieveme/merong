package org.example.merong.common.filter.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterConstants {

    public static final List<Route> WHITE_LIST = List.of(
            new Route("/api/auth/signin", "POST"),
            new Route("/api/auth/reissue", "POST"),
            new Route("/api/users", "POST")
    );

    public static final String USER_CRUD = "/api/users";
    public static final String REISSUE = "/api/auth/reissue";

    public record Route(String path, String method) {}
}

