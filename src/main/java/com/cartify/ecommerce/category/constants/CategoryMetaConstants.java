package com.cartify.ecommerce.category.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CategoryMetaConstants {
    public static final int SEO_TITLE_MAX_LENGTH = 70;
    public static final int SEO_DESCRIPTION_MAX_LENGTH = 320;

    public static final String SEO_TITLE_MAX_LENGTH_MESSAGE = "SEO title cannot exceed " + SEO_TITLE_MAX_LENGTH + " characters.";
    public static final String SEO_DESCRIPTION_MAX_LENGTH_MESSAGE = "SEO description cannot exceed " + SEO_DESCRIPTION_MAX_LENGTH + " characters.";

    public static final String CATEGORY_META_NOT_FOUND = "Meta for category with id: %d was not found.";
    public static final String CATEGORY_META_ALREADY_EXISTS = "Meta for category with id: %d already exists.";
}
