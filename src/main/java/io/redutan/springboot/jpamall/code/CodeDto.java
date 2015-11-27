package io.redutan.springboot.jpamall.code;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author myeongju.jung
 */
public class CodeDto {
    @Data
    public static class Create {
        private Long parentCodeId;
        @NotEmpty
        private String name;
    }
}
