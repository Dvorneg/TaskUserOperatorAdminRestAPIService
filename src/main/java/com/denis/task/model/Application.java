package com.denis.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "application")
public class Application extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    //@JsonIgnore
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "message")
    private String message;

    @Column(name = "application_date_time")
    private LocalDateTime applicationDateTime;

/*    public Application(User user, Status status, String message, LocalDateTime applicationDateTime) {
        this.user = user;
        this.status = status;
        this.message = message;
        this.applicationDateTime = applicationDateTime;
    }*/
}
