package com.cartify.ecommerce.catalog.category.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Generic {
        public static final String NOT_FOUND_MSG = "category.not_found";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {
        public static final int MAX_LENGTH = 100;

        public static final String NOT_BLANK_MSG = "category.name.not_blank";
        public static final String MAX_LENGTH_MSG = "category.name.max_length";
        public static final String ALREADY_EXISTS_MSG = "category.name.already_exists";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Slug {
        public static final int MAX_LENGTH = 150;

        public static final String NOT_BLANK_MSG = "category.slug.not_blank";
        public static final String MAX_LENGTH_MSG = "category.slug.max_length";
        public static final String ALREADY_EXISTS_MSG = "category.slug.already_exists";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ImageUrl {
        public static final int MAX_LENGTH = 200;

        public static final String NOT_BLANK_MSG = "category.url.not_blank";
        public static final String MAX_LENGTH_MSG = "category.url.max_length";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class DisplayOrder {
        public static final int DEFAULT_VALUE = 0;

        public static final String DEFAULT_MSG = "category.display_order.default";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ParentId {
        public static final long MIN_VALUE = 1L;

        public static final String MIN_MSG = "category.parent_id.min";
    }

    // Domain resource name used in exception factory methods
    public static final String RESOURCE_NAME = "Category";
}
