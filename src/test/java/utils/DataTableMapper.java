package utils;

import io.cucumber.datatable.DataTable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DataTableMapper {

  public static <T> List<T> fromDataTable(DataTable dataTable, Class<T> clazz) {
    return dataTable.asMaps(String.class, String.class)
        .stream()
        .map(row -> {
          try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
              field.setAccessible(true);

              String value = row.get(field.getName());

              if (value != null) {
                if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                  field.set(instance, Integer.parseInt(value));
                } else if (field.getType().equals(Double.class) || field.getType()
                    .equals(double.class)) {
                  field.set(instance, Double.parseDouble(value));
                }else if (field.getType().equals(BigDecimal.class)) {
                  field.set(instance, new BigDecimal(value));
                } else {
                  field.set(instance, value);
                }
              }
            }
            return instance;
          } catch (Exception e) {
            throw new RuntimeException(
                "Error al mapear el DataTable a la clase " + clazz.getSimpleName(), e);
          }
        })
        .collect(Collectors.toList());
  }

}
