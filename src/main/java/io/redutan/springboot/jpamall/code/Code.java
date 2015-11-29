package io.redutan.springboot.jpamall.code;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myeongju.jung
 */
@Entity
@Data
@ToString(exclude = {"parentCode", "children"})
@EqualsAndHashCode(exclude = {"parentCode", "children"})
public class Code {
    @Id
    @GeneratedValue
    private Long codeId;

    @JoinColumn(name = "PARENT_CODE_ID")
    @ManyToOne(optional = true)
    private Code parent;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Code> children = new ArrayList<>();

    public void addChild(Code child) {
        this.children.add(child);
        child.setParent(this);
    }
}
