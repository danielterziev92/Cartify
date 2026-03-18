package com.cartify.ecommerce.catalog.category.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CategoryMetaConstants {

    public static final int SEO_TITLE_MAX_LENGTH = 70;
    public static final int SEO_DESCRIPTION_MAX_LENGTH = 320;

    // Validation messages
    public static final String SEO_TITLE_MAX_LENGTH_MSG =
            "SEO title cannot exceed " + SEO_TITLE_MAX_LENGTH + " characters.";
    public static final String SEO_DESCRIPTION_MAX_LENGTH_MSG =
            "SEO description cannot exceed " + SEO_DESCRIPTION_MAX_LENGTH + " characters.";

    // Domain resource name used in exception factory methods
    public static final String RESOURCE_NAME = "CategoryMeta";
}
