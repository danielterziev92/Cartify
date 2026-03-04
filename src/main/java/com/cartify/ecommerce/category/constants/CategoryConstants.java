package com.cartify.ecommerce.category.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CategoryConstants {
    public static final int NAME_MAX_LENGTH = 100;
    public static final int PARENT_ID_MIN_VALUE = 1;
    public static final int SLUG_MAX_LENGTH = 150;

    public static final String NAME_NOT_BLANK_MESSAGE = "Name cannot be blank.";
    public static final String NAME_MAX_LENGTH_MESSAGE = "Name cannot exceed " + NAME_MAX_LENGTH + " characters.";

    public static final String SLUG_NOT_BLANK_MESSAGE = "Slug cannot be blank.";
    public static final String SLUG_MAX_LENGTH_MESSAGE = "Slug cannot exceed " + SLUG_MAX_LENGTH + " characters.";

    public static final String PARENT_ID_POSITIVE_MESSAGE = "Parent id must be greater then " + PARENT_ID_MIN_VALUE + ".";


    public static final String CATEGORY_NOT_FOUND = "Category with id: %d was not found.";
    public static final String CATEGORY_ALREADY_EXISTS = "Category with name: %s already exists.";
    public static final String CATEGORY_OWN_PARENT = "Category cannot be its own parent.";

}
