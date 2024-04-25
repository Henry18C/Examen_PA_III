package org.example.programacion.avanzada;


import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import java.util.HashMap;
import java.util.Map;

import static io.r2dbc.postgresql.PostgresqlConnectionFactoryProvider.OPTIONS;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

public class DataBaseConfig {

        public static ConnectionFactory createConnection() {

            Map<String, String> options = new HashMap<>();
            options.put("lock_timeout", "60s");
            options.put("statement_timeout", "60s");
            try {
                ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(PORT, 5432)
                        .option(USER, "postgres")
                        .option(PASSWORD, "12345")
                        .option(DATABASE, "Libreria")
                        .option(OPTIONS, options) // optional
                        .build());


              /*  ConnectionFactory connectionFactory=
                        ConnectionFactories.get("r2dbc:postgresql://postgres:12345@localhost:5432/Libreria");*/
                System.out.println("Conexion Exitosa!!!");
                return connectionFactory;
            } catch (Exception e) {
                throw new RuntimeException("Error al crear la fábrica de conexiones", e);
            }
        }
    }



