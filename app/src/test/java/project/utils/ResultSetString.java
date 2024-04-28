package project.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ResultSetString {

    private ResultSetString() {}

    public String getResultSetString(final ResultSet resultSet) {
        final StringBuilder builder = new StringBuilder();
        try {
            final ResultSetMetaData resultMetaData = resultSet.getMetaData();
            final int columnCount = resultMetaData.getColumnCount();
            final List<Integer> columnSize = new ArrayList<>(columnCount);
            List<String> valuesRow = new ArrayList<>();
            final List<List<String>> valuesCol = new ArrayList<>();

            for(int i = 0; i < columnCount; i++) {
                final String resColName = resultMetaData.getColumnName(i+1);
                columnSize.add(resColName.length());
                valuesRow.add(resColName);
            }

            valuesCol.add(valuesRow);
            int rowCount = 0;
            resultSet.beforeFirst();

            while (resultSet.next()) {
                rowCount += 1;
                valuesRow = new ArrayList<>();

                for(int i = 0; i < columnCount; i++) {
                    final String value = resultSet.getString(i + 1);
                    columnSize.set(i,Math.max(value.length(), columnSize.get(i)));
                    valuesRow.add(value);
                }
                valuesCol.add(valuesRow);
            }

            builder.append("\n");
            for(int i = 0; i < rowCount; i++)
                for(int j = 0; j < columnCount; j++)
                    builder.append(String.format("%-"+columnSize.get(j)+"s", valuesCol.get(i).get(j)));
            builder.append("\n");
            }
        catch (SQLException e) {return "Error:- " + e.getMessage();}
        return builder.toString();
    }
}
