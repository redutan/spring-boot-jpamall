package io.redutan.springboot.jpamall.code;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author myeongju.jung
 */
@Entity
@Data
@ToString(exclude = {"parentCode"})
@EqualsAndHashCode(exclude = {"parentCode"})
public class Code {
    @Id
    @GeneratedValue
    private Long codeId;

    @JoinColumn(name = "PARENT_CODE_ID")
    @ManyToOne(optional = true)
    private Code parentCode;

    private String name;

}
