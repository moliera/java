package com.cpl.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

    public class JDBCUtil {
        static String driverClass = null;
        static String url = null;
        static String name = null;
        static String password = null;

        static {
            try {
                Properties properties = new Properties();// 创建一个属性配置对象
                InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("properties");
                properties.load(is); // 导入输入流
                driverClass = properties.getProperty("driverClass");// 读取属性
                url = properties.getProperty("url");
                name= properties.getProperty("name");
                password = properties.getProperty("password");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        public static void register() {
            try {

                //DriverManager.registerDriver(new com.mysql.jdbc.Driver()); //注册驱动


                Class.forName(driverClass);

            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }

        public static Connection getConn() {
            Connection conn = null;
            try {

                conn = DriverManager.getConnection(url, name, password);

            } catch (Exception e) {

                e.printStackTrace();
            }
            /*
             * 因为diver里面有一个静态代码块，只要一加载类，就会执行静态代码块，所以为了防止二次加载驱动，所以直接用类名去加载就好了
             */
            return conn;
        }

        public static void release(ResultSet re, Statement st, Connection conn) {
            closeRe(re);
            closeSt(st);
            closeConn(conn);
        }
        public static void release( Statement st, Connection conn,PreparedStatement ps) {

            closeSt(st);
            closeConn(conn);
            closePs(ps);
        }
        public static void release(  Connection conn,PreparedStatement ps) {


            closeConn(conn);
            closePs(ps);
        }
        public static void release( Statement st, Connection conn) {

            closeSt(st);
            closeConn(conn);
        }

        private static void closePs(PreparedStatement ps) {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    ps = null;
                }
            }

        }

        private static void closeRe(ResultSet re) {
            if (re != null) {
                try {
                    re.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    re = null;
                }
            }

        }

        private static void closeSt(Statement st) {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    st = null;
                }
            }

        }

        private static void closeConn(Connection conn) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    conn = null;
                }
            }

        }
    }


