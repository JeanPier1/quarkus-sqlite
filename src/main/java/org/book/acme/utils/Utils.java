package org.book.acme.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class Utils {

    private Utils() {}

    public static String roundDecimals(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        return df.format(number);
    }

    public static String generateNextAssetCode(String lastCode) {
        int nextNumber = 1;
        if (lastCode != null) {
            try {
                nextNumber = Integer.parseInt(lastCode) + 1;
            } catch (NumberFormatException e) {
                // Manejar la excepción de acuerdo a tus requisitos
            }
        }
        // Formatear el número como una cadena de 5 dígitos
        return String.format("%05d", nextNumber);
    }

    public static void addConditionEqualString(StringBuilder query, Map<String, Object> params, String field, String value) {
        if (value != null && !value.isEmpty()) {
            query.append(" and ").append(field).append(" = :").append(field);
            params.put(field, value);
        }
    }

    public static void addConditionEqualInteger(StringBuilder query, Map<String, Object> params, String field, Integer value) {
        if (value != null && value != 0) {
            query.append(" and ").append(field).append(" = :").append(field);
            params.put(field, value);
        }
    }

    public static void addConditionLike(StringBuilder query, Map<String, Object> params, String field, String value) {
        if (value != null && !value.isEmpty()) {
            String paramName = field.replace(".", "_"); // Reemplaza puntos por guiones bajos
            query.append(" AND ").append(field).append(" LIKE :").append(paramName);
            params.put(paramName, "%" + value + "%");
        }
    }

    public static void addConditionEqualBoolean(StringBuilder query, Map<String, Object> params, String field, boolean value) {
        String paramName = field.replace(".", "_"); // Reemplaza los puntos para evitar problemas
        query.append(" and ").append(field).append(" = :").append(paramName);
        params.put(paramName, value);
    }

    public static void addConditionLikeEnd(StringBuilder query, Map<String, Object> params, String field, Integer value) {
        if (value != null && value != 0) {
            query.append(" and SUBSTRING(").append(field).append(", LOCATE('-', ").append(field).append(") - 1, 1) = :").append(field);
            params.put(field, value.toString());
        }
    }
    public static void addConditionEqualUUID(StringBuilder query, Map<String, Object> params, String field, UUID value) {
        if (value != null) {
            // Cambia el nombre del parámetro para evitar puntos
            String paramName = field.replace(".", "_"); // Reemplaza "." por "_"
            query.append(" and ").append(field).append(" = :").append(paramName);
            params.put(paramName, value); // Usa el nuevo nombre del parámetro
        }
    }

    public static void addOrderBy(StringBuilder query, Map<String, String> orderByColumns) {
        if (orderByColumns != null && !orderByColumns.isEmpty()) {
            query.append(" ORDER BY ");
            int count = 0;
            for (Map.Entry<String, String> entry : orderByColumns.entrySet()) {
                query.append(entry.getKey()).append(" ").append(entry.getValue());
                if (count < orderByColumns.size() - 1) {
                    query.append(", ");
                }
                count++;
            }
        }
    }


    // Método para detectar referencia circular con DTOs
    private static <T> boolean hasCircularReference(UUID currentId, T parent, Function<T, UUID> getParentIdFunction, Function<UUID, T> findByIdFunction) {
        while (parent != null) {
            UUID parentId = getParentIdFunction.apply(parent);
            if (parentId != null && parentId.equals(currentId)) {
                return true;
            }
            parent = (parentId != null) ? findByIdFunction.apply(parentId) : null;
        }
        return false;
    }
}
