package com.myservice.common.dto.auth;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class AppModuleDTO extends MyDTO {
    private String name;
    private String description;
    private String webUrl;

    /**
     * Used in User CRUD. To create permission.
     */
    private List<AuthorityDTO> authorities;
    private List<AuthorityDTO> selectedAuthorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppModuleDTO that = (AppModuleDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
