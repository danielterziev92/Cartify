package com.cartify.ecommerce.catalog.category.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CategoryConstants {

    public static final int NAME_MAX_LENGTH = 100;
    public static final int SLUG_MAX_LENGTH = 150;
    public static final int URL_MAX_LENGTH = 200;
    public static final long PARENT_ID_MIN_VALUE = 1L;

    // Validation messages
    public static final String NAME_NOT_BLANK = "Name cannot be blank.";
    public static final String NAME_MAX_LENGTH_MSG = "Name cannot exceed " + NAME_MAX_LENGTH + " characters.";
    public static final String SLUG_NOT_BLANK = "Slug cannot be blank.";
    public static final String SLUG_MAX_LENGTH_MSG = "Slug cannot exceed " + SLUG_MAX_LENGTH + " characters.";
    public static final String URL_NOT_BLANK = "URL cannot be blank.";
    public static final String URL_MAX_LENGTH_MSG = "URL cannot exceed " + URL_MAX_LENGTH + " characters.";
    public static final String PARENT_ID_MIN_MSG = "Parent id must be greater than " + PARENT_ID_MIN_VALUE + ".";

    // Domain resource name used in exception factory methods
    public static final String RESOURCE_NAME = "Category";
}
