package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users")
@Setting(settingPath = "/es-settings/user-settings.json")
@Mapping(mappingPath = "/es-settings/user-mappings.json")
public class UserDocument {

    @Id
    private String id;

    private String email;

    private String role;

    private String nickname;

    private Integer birthYear;

    private String birthDay;    // "mm-dd"

    private String sex;

    private String bio;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

}
