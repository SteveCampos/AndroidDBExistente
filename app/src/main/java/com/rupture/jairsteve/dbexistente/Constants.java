package com.rupture.jairsteve.dbexistente;

/**
 * Created by JairSteve on 30/11/2014.
 */
public class Constants {
    public static final String DATABASE_NAME = "scan.db";
    public static final int DATABASE_VERSION = 1;


    public static final String CREATE_STRUCTURE_DATABASE = "CREATE TABLE vendor(_id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_vendor    TEXT NOT NULL, vendor_name TEXT NOT NULL)";

    public static final String _ID ="_id";
    public static final String _ID_VENDOR ="id_vendor";
    public static final String _VENDOR_NAME ="vendor_name";
    public static final String TABLE_NAME = "vendor";



}
