package com.ericsson.eni.predicates;

import com.google.common.base.Predicate;

public class UsersPredicate implements Predicate<User> {

    private int userCount;

    public UsersPredicate(int count) {
        this.userCount = count;
    }

    @Override
    public boolean apply(User user) {
        int count = user.getNumber();
        return count <= userCount;
    }
}

