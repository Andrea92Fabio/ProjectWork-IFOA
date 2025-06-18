package it.ifts.ifoa.teletubbies.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private static final int SIZE = 5;
    private final Queue<Connection> pool;

    private ConnectionPool() {
        pool = new LinkedList<>();

        for(int i = 0; i < SIZE; i++){
            try {
                 Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/san_martino_friendship_day?user=root");
                 pool.offer(con);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Inner static helper class - thread-safe lazy init
    private static class Holder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    // Pool management methods, synchronized for thread safety
    public synchronized Connection borrowConnection() throws InterruptedException {
        while (pool.isEmpty()){
            wait();
        }
        return pool.poll();
    }

    public synchronized void releaseConnection(Connection con) {
        pool.offer(con);
        notify();
    }
}
