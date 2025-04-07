package com.mods.profileservice.mapper;

import com.mods.profileservice.dto.request.ProfileCreationRequest;
import com.mods.profileservice.dto.response.UserProfileResponse;
import com.mods.profileservice.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile entity);

}
