package com.truphone.poc.usage.core;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthSaying {

    @Length
    private String content;

    public AuthSaying() {
        // Jackson deserialization
    }

    public AuthSaying(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

}