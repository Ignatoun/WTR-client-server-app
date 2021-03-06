package com.epolsoft.wtr.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailedTaskDTO {

    private Integer detailedTaskId;

    private String detailedTaskName;

    private Integer taskId;

}
