package com.mods.profileservice.mapper;

import com.mods.profileservice.dto.request.ProfileCreationRequest;
import com.mods.profileservice.dto.response.UserProfileResponse;
import com.mods.profileservice.entity.UserProfile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile entity);
    List<UserProfileResponse> toUserProfileResponseList(List<UserProfile> all);
}
