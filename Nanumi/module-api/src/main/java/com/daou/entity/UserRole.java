package com.daou.entity;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class UserRole {
    @Embeddable
    public static class Id implements Serializable{
        private static final long serialVersionUID = 1322120000551624359L;

        protected Long userId;

        protected Role role;

        public Id(){}
        public Id(Long userId, Role role){
            this.userId=userId;
            this.role = role;
        }
    }

    @EmbeddedId
    Id id = new Id();

    @Getter
    protected Role role;
}
