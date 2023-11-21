package com.learning.personal_expense_management.services;

import com.learning.personal_expense_management.model.UserProfile;

import java.util.List;

public interface UserProfileListener {
    void onExist(boolean isExist);
    void onUserProfilesLoaded(List<UserProfile> userProfiles);
    void onError(String errorMessage);
}
