package com.myservice.common.dto.pagination;

import lombok.Getter;
import org.springframework.data.domain.ExampleMatcher;

@Getter
public enum FilterTypeEnum {
    EQ {
        @Override
        public ExampleMatcher.GenericPropertyMatcher get() {
            return ExampleMatcher.GenericPropertyMatchers.exact();
        }
    },

    LIKE {
        @Override
        public ExampleMatcher.GenericPropertyMatcher get() {
            return ExampleMatcher.GenericPropertyMatchers.contains();
        }
    },

    ILIKE {
        @Override
        public ExampleMatcher.GenericPropertyMatcher get() {
            return ExampleMatcher.GenericPropertyMatchers.startsWith();
        }
    };

    public abstract ExampleMatcher.GenericPropertyMatcher get();

}