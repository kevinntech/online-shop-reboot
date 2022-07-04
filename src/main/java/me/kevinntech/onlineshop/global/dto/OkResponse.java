package me.kevinntech.onlineshop.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OkResponse<T> {

    private T data;

    private LocalDateTime timestamp;

    public OkResponse(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> OkResponse of(T data) {
        return new OkResponse(data);
    }

}
