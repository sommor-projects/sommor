package com.sommor.bundles.user.model;

import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-15
 */
public class UserRoles {

    public static final String USER = "user";

    public static final String ADMIN = "admin";

    private static final Map<String, Set<String>> inheritedRolesMap = new HashMap<>();
    static {
        inherit(ADMIN, USER);
    }

    public static void inherit(String role, String... inheritedRoles) {
        Set<String> set = inheritedRolesMap.computeIfAbsent(role, p -> new HashSet<>());
        set.addAll(Arrays.asList(inheritedRoles));
    }

    public static Set<String> getInheritedRoles(String role) {
        Set<String> set = inheritedRolesMap.get(role);
        return set == null ? Collections.emptySet() : set;
    }
}
