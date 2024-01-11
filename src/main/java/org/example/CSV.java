package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private final String filename;
    private List<String[]> data;

    private final String tableName;

    public CSV(String filename, String tableName) {
        this.filename = filename;
        this.tableName = tableName;
    }

    private void readCSV() {
        this.data = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillConstructorTable() {
        for (String[] row : data) {
            String name = row[2];
            String nationality = row[3];

            String SQLRequest = "INSERT INTO constructor(name_constructor, nationality_constructor) VALUES (?, ?)";

            try (Connection connection = DatabaseConnection.getConnection()) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, nationality);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void fillFromFile() {
        this.readCSV();

        switch (this.tableName) {
            case "constructor":
                this.fillConstructorTable();
                break;
            default:
                throw new RuntimeException("Table inconnue");
        }

    }

}
