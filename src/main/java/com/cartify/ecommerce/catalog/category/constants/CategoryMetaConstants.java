package com.cartify.ecommerce.catalog.category.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryMetaConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Generic {
        public static final String NOT_FOUND_MSG = "category_meta.not_found";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Id {
        public static final int MIN_VALUE = 1;

        public static final String MIN_MSG = "category_meta.id.min";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SeoTitle {
        public static final int MAX_LENGTH = 70;

        public static final String MAX_LENGTH_MSG = "category_meta.seo_title.max_length";
    }

    public static final class SeoDescription {
        public static final int MAX_LENGTH = 320;

        public static final String MAX_LENGTH_MSG = "category_meta.seo_description.max_length";
    }

    // Domain resource name used in exception factory methods
    public static final String RESOURCE_NAME = "CategoryMeta";
}
