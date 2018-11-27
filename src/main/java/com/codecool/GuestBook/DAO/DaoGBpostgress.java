package com.codecool.GuestBook.DAO;

import com.codecool.GuestBook.DBconnector.DBconnector;
import com.codecool.GuestBook.DBconnector.DBconnectorPostgress;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoGBpostgress implements DaoGB {

    private DBconnector dBconnector;

    public DaoGBpostgress() {
        this.dBconnector = new DBconnectorPostgress();

    }

    public List<List<String>> getListOfRecordsFromDB() {
        Connection connection = dBconnector.openDataBase();
        Statement statement = getStatement(connection);

        String query = "SELECT * FROM guests;";

        ResultSet result = askDataBaseForData(query, connection, statement);
        List<List<String>> listOfRecords = getListOfRecordsFromResultSet(result);
        closeStatementAndConnection(connection, statement);
        return listOfRecords;
    }

    private Statement getStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    private void closeStatementAndConnection(Connection connection, Statement statement) {
        try{
            connection.close();
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet askDataBaseForData(String query, Connection connection, Statement statement) {

        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<List<String>> getListOfRecordsFromResultSet(ResultSet result) {

        List<List<String>> answer = new ArrayList<>();
        try {
            while(result.next()) {
                List<String> recordFromDB = new ArrayList<>();
                recordFromDB.add(result.getString(1));
                recordFromDB.add(result.getDate(2).toString());
                recordFromDB.add(result.getString(3));
                answer.add(recordFromDB);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error! Check Your internet connection or try again later!");
        }
        return answer;
    }
}
