// Part 1 (for model pkg): User Role Enum
package com.abhayini.model;

// enum is used here for representing different user roles in the application
public enum UserRole
{
    VICTIM,            // role -> users -> victims
    TRUSTED_CONTACT,   // role -> users -> trusted contacts
    AUTHORITY          // role -> users -> authorities
}

//here we have assigned the roles to the users in the application accordingly