package com.epolsoft.wtr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer userId;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Integer titleId;

    private Integer departmentId;

    private Set<Integer> roleId;

}
