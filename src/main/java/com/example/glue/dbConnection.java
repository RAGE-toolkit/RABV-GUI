package com.example.glue;

import java.sql.*;

public class dbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/GLUE_TOOLS";
    private static final String USER = "gluetools";
    private static final String PASSWORD = "Password123#@!";


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet fetchAllSequences() throws SQLException {
        Connection conn = dbConnection.connect();
        Statement stmt = conn.createStatement();
        String query = "SELECT sequence_id, gb_create_date, gb_update_date, gb_length, isolate, gb_country, host, collection_year, gb_pubmed_id, maddog_lineage, major_clade, minor_clade FROM rabv_sequence";
        return stmt.executeQuery(query);
    }

    public static ResultSet fetchDistinctClades() throws SQLException {
        Connection con = dbConnection.connect();
        Statement stmt = con.createStatement();
        String query = "SELECT DISTINCT display_name FROM rabv_alignment";
        return stmt.executeQuery(query);
    }

    public static ResultSet fetchByClades(String alignment) throws SQLException {
        Connection con = dbConnection.connect();
        Statement stmt = con.createStatement();
        String query = "select a.sequence_id,b.gb_create_date, b.gb_update_date, b.gb_length, b.isolate, b.gb_country, b.host, b.collection_year, b.gb_pubmed_id, b.maddog_lineage, b.major_clade, b.minor_clade from rabv_aligned_segment a inner join rabv_sequence b on a.sequence_id = b.gb_primary_accession and a.alignment_name =" + "'" + alignment + "'";
        System.out.print(query);
        return stmt.executeQuery(query);
    }

    public static ResultSet filterAllSequences(String filters) throws SQLException {
        Connection con = dbConnection.connect();
        Statement stmt = con.createStatement();
        String query = "SELECT sequence_id, gb_create_date, gb_update_date, gb_length, isolate, gb_country, host, collection_year, gb_pubmed_id, maddog_lineage, major_clade, minor_clade FROM rabv_sequence WHERE " + filters;
        System.out.print(query);
        return stmt.executeQuery(query);
    }

    public static ResultSet filterAllForGlobalRegion(String filters) throws SQLException {
        Connection con = dbConnection.connect();
        Statement stmt = con.createStatement();
        //"americas" AND m49_sub_region_id = "latin_america_and_the_caribbean"
        String query = "SELECT sequence_id, gb_create_date, gb_update_date, gb_length, isolate, gb_country, host, collection_year, gb_pubmed_id, maddog_lineage, major_clade, minor_clade FROM rabv_sequence WHERE gb_country IN (SELECT display_name from rabv_m49_country WHERE " + filters;
        System.out.print(query);
        return stmt.executeQuery(query);
    }
}
