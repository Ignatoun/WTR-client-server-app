package com.epolsoft.wtr.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Location")
public class Location {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="locationId")
    private Integer locationId;

    @Column(name="locationName")
    private String locationName;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    public Location(Integer locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    @Override
    public String toString(){
        return "Location {"
                + "LocationId=" + locationId
                + ", LocationName=" + locationName
                + "}";
    }
}
