package com.epolsoft.wtr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Factor")
public class Factor
{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="factorId")
    private Integer factorId;

    @Column(name="factorName")
    private String factorName;

    @OneToMany(mappedBy = "factor")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    public Factor (Integer factorId, String factorName)
    {
        this.factorId=factorId;
        this.factorName=factorName;
    }

    @Override
    public String toString()
    {
        return "Factor["
                + "factorId=" + factorId +
                ", factorName=" + factorName + "]";
    }
}
