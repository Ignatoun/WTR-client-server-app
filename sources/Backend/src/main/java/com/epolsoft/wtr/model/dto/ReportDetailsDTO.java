package com.epolsoft.wtr.model.dto;

import com.epolsoft.wtr.model.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsDTO {

    private Integer reportDetailsId;

    @Temporal(TemporalType.DATE)
    private Date reportDetailsDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer factorId;

    private Integer locationId;

    private Double hours;

    private Double workUnits;

    private Integer detailedTaskId;

    private Integer taskId;

    private Integer featureId;

    private Integer projectId;

    private String comments;

    private Set<Integer> usersId;
}
